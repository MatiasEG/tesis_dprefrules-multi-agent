package premises;

import criteria.Criteria;

public class BPremise extends Premise{

	private int minDistBetweenXY;
	
	public BPremise(Criteria criteria) {
		super(criteria);
		minDistBetweenXY = 0;
	}

	public int getMinDist() {
		return minDistBetweenXY;
	}

	public void setMinDist(int minDist) {
		this.minDistBetweenXY = minDist;
	}
	
	public void setMinDist(String minDist) {
		if(!minDist.equals("-")) {
			this.minDistBetweenXY = Integer.parseInt(minDist);
		}
	}
	
	public boolean validMinDistValue(String minDistString) {
		if(!minDistString.equals("-")) {
			int minDist = 0;
			try {
				minDist = Integer.parseInt(minDistString);
			}catch(NumberFormatException e) {
				return false;
			}
			
			if(!criteria.isNumeric()) {
				if(minDist>criteria.getValues().length || minDist<0) {
					return false;
				}
			}else {
				if(minDist>(Integer.parseInt(criteria.getValues()[1])-Integer.parseInt(criteria.getValues()[0])) || minDist<0) {
					return false;
				}
			}
		}
		return true;
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
			
			if(minDistBetweenXY!=0) {
				if(!(minXIndex > minDistBetweenXY)) return false;
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
			
			if(maxYIndex!=-1 && minValueForX!=-1 && minValueForX<maxYIndex) return false;
			
			if(minDistBetweenXY!=0) {
				// Defined: minDistBetweenXY & minValueX & maxValueY
				if(minValueForX!=-1) {
					if(!(minValueForX-maxYIndex >= minDistBetweenXY)) {
						return false;
					}
					// Defined: minDistBetweenXY & maxValueY
				}else if(!criteria.isNumeric()) {
					if(!(criteria.getValues().length-maxYIndex > minDistBetweenXY)) {
						return false;
					}
				}else {
					if(!(Integer.parseInt(criteria.getValues()[1])-maxYIndex > minDistBetweenXY)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public String getDescription() {
		String description = "X debe ser mejor que Y en el criterio <"+criteria.getName()+">";
		if(minDistBetweenXY!=0)
			description += ", con una distancia minima entre los valores de las alternativas de al menos "+minDistBetweenXY;
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
		String premise = "better(X,Y,"+criteria.getName()+")";
		if(minDistBetweenXY!=0)
			premise += ", min_dist(X,Y,"+criteria.getName()+","+minDistBetweenXY+")";
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
	
	public BPremise clone() {
		BPremise bpClone = new BPremise(criteria);
		bpClone.setMinDist(minDistBetweenXY);
		bpClone.setMinValueForX(minValueForX);
		bpClone.setMaxValueForY(maxValueForY);
		return bpClone;
	}
}
