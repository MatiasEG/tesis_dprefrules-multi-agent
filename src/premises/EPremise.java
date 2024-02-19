package premises;

import criteria.Criteria;

public class EPremise extends Premise{
	
	public EPremise(Criteria criteria) {
		super(criteria);
	}

	public void setMinValueForX(int minValueForX) {
		if(maxValueForY == -1) {
			this.minValueForX = minValueForX;
		}
	}

	public void setMaxValueForY(int maxValueForY) {
		if(minValueForX == -1) {
			this.maxValueForY = maxValueForY;
		}
	}
	
	public String getDescription() {
		String description = "X debe ser igual que Y en el criterio <"+criteria.getName()+">";
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
		String premise = "equal(X,Y,"+criteria.getName()+")";
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
			if(maxValueForY!=-1) return false;
			
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
					int minValue = Math.min(Integer.parseInt(criteria.getValues()[0]), Integer.parseInt(criteria.getValues()[1]));
					int maxValue = Math.max(Integer.parseInt(criteria.getValues()[0]), Integer.parseInt(criteria.getValues()[1]));
					
					if(minXIndex<minValue || minXIndex>maxValue) {
						return false;
					}
				}catch(NumberFormatException e) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean validMaxYValue(String maxY) {
		int maxYIndex = -1;
		
		if(!maxY.equals("-")) {
			if(minValueForX!=-1) return false;
			
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
					int minValue = Math.min(Integer.parseInt(criteria.getValues()[0]), Integer.parseInt(criteria.getValues()[1]));
					int maxValue = Math.max(Integer.parseInt(criteria.getValues()[0]), Integer.parseInt(criteria.getValues()[1]));
					
					if(maxYIndex<minValue || maxYIndex>maxValue) {
						return false;
					}
				}catch(NumberFormatException e) {
					return false;
				}
			}
		}
		return true;
	}
	
	public EPremise clone() {
		EPremise epClone = new EPremise(criteria);
		epClone.setMinValueForX(minValueForX);
		epClone.setMaxValueForY(maxValueForY);
		return epClone;
	}
}
