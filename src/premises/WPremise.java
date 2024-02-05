package premises;

import criteria.Criteria;

public class WPremise extends Premise{

	private int maxDistBetweenXY;
	
	public WPremise(Criteria criteria) {
		super(criteria);
		maxDistBetweenXY = 0;
	}

	public int getMaxDist() {
		return maxDistBetweenXY;
	}

	public void setMaxDist(int maxDistBetweenXY) {
		this.maxDistBetweenXY = maxDistBetweenXY;
	}
	
	public void setMaxDist(String maxDist) {
		if(!maxDist.equals("-")) {
			this.maxDistBetweenXY = Integer.parseInt(maxDist);
		}
	}
	
	public boolean validMaxDistValue(String maxDistString) {
		if(!maxDistString.equals("-")) {
			int maxDist = 0;
			try {
				maxDist = Integer.parseInt(maxDistString);
			}catch(NumberFormatException e) {
				return false;
			}
		
			if(!criteria.isNumeric()) {
				if(maxDist>criteria.getValues().length || maxDist<0) {
					return false;
				}
			}else {
				if(maxDist>(Integer.parseInt(criteria.getValues()[1])-Integer.parseInt(criteria.getValues()[0])) || maxDist<0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public String getDescription() {
		String description = "el criterio <"+criteria.getName()+"> de X si es peor que Y";
		if(maxDistBetweenXY!=0)
			description += ", con una distancia maxima entre las alternativas de como maximo "+maxDistBetweenXY;
		if(minValueForX!=-1)
			if(!criteria.isNumeric())
				description += ", donde X vale como minimo "+criteria.getValues()[minValueForX]+"";
			else
				description += ", donde X vale como minimo "+minValueForX+"";
		if(maxValueForY!=-1)
			if(!criteria.isNumeric())
				description += ", donde Y vale como maximo "+criteria.getValues()[maxValueForY]+"";
			else
				description += ", donde Y vale como maximo "+maxValueForY+"";
		//description += ".";
		return description;
	}
	
	public String getPremise() {
		String premise = "worse(X,Y,"+criteria.getName()+")";
		if(maxDistBetweenXY!=0)
			premise += ", max_dist(X,Y,"+criteria.getName()+","+maxDistBetweenXY+")";
		if(minValueForX!=-1)
			if(!criteria.isNumeric())
				premise += ", min(X,"+criteria.getName()+","+criteria.getValues()[minValueForX]+")";
			else
				premise += ", min(X,"+criteria.getName()+","+minValueForX+")";
		if(maxValueForY!=-1)
			if(!criteria.isNumeric())
				premise += ", max(Y,"+criteria.getName()+","+criteria.getValues()[maxValueForY]+")";
			else
				premise += ", max(Y,"+criteria.getName()+","+maxValueForY+")";
		return premise;
	}

	public boolean validMinXValue(String minX) {
		int minXIndex = -1;
		
		if(!minX.equals("-")) {
			if(!criteria.isNumeric()) {
				for(int i=0; i<criteria.getValues().length; i++) {
					if(criteria.getValues()[i].equals(minX)) {
						minXIndex = i;
					}
					if(minXIndex!=-1) break;
				}
				if(minXIndex==-1) return false;
			}else {
				try {
					minXIndex = Integer.parseInt(minX);
					
					if((minXIndex<Integer.parseInt(criteria.getValues()[0]) || minXIndex>Integer.parseInt(criteria.getValues()[1]))) {
						return false;
					}
				}catch(NumberFormatException e) {
					return false;
				}
			}
			
			if(maxDistBetweenXY!=0) {
				if(!criteria.isNumeric()) {
					if(!(criteria.getValues().length-minXIndex > maxDistBetweenXY)) {
						return false;
					}
				}else if(!(Integer.parseInt(criteria.getValues()[1])-minXIndex > maxDistBetweenXY)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean validMaxYValue(String maxY) {
		int maxYIndex = -1;
		
		if(!maxY.equals("-")) {
			if(!criteria.isNumeric()) {
				for(int i=0; i<criteria.getValues().length; i++) {
					if(criteria.getValues()[i].equals(maxY)) {
						maxYIndex = i;
					}
					if(maxYIndex!=-1) break;
				}
				if(maxYIndex==-1) return false;
			}else {
				try {
					maxYIndex = Integer.parseInt(maxY);
					
					if((maxYIndex<Integer.parseInt(criteria.getValues()[0]) || maxYIndex>Integer.parseInt(criteria.getValues()[1]))) {
						return false;
					}
				}catch(NumberFormatException e) {
					return false;
				}
			}
			
			if(maxYIndex!=-1 && minValueForX!=-1 && maxYIndex<minValueForX) return false;
			
			if(maxDistBetweenXY!=0) {
				// Defined: minDistBetweenXY & minValueX & maxValueY
				if(minValueForX!=-1) {
					if(!(maxYIndex-minValueForX >= maxDistBetweenXY)) {	// If min(X)<max(Y) this condition will work correctly
						return false;
					}
					// Defined: minDistBetweenXY & maxValueY
				}else if(!(maxYIndex > maxDistBetweenXY)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public WPremise clone() {
		WPremise wpClone = new WPremise(criteria);
		wpClone.setMaxDist(maxDistBetweenXY);
		wpClone.setMinValueForX(minValueForX);
		wpClone.setMaxValueForY(maxValueForY);
		return wpClone;
	}
}
