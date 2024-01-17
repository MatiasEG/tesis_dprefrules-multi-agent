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
				premise += ", min(X, "+criteria.getName()+", "+criteria.getValues()[minValueForX]+")";
			else
				premise += ", min(X, "+criteria.getName()+", "+minValueForX+")";
		if(maxValueForY!=-1)
			if(!criteria.isNumeric())
				premise += ", max(Y, "+criteria.getName()+", "+criteria.getValues()[maxValueForY]+")";
			else
				premise += ", max(Y, "+criteria.getName()+", "+maxValueForY+")";
		return premise;
	}
}
