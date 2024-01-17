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
	
	public String getDescription() {
		String description = "X debe ser mejor que Y en el criterio <"+criteria.getName()+">";
		if(minDistBetweenXY!=0)
			description += ", con una distancia minima entre los valores de las alternativas de al menos ("+minDistBetweenXY+")";
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
			premise += ", min_dist(X,Y,"+minDistBetweenXY+")";
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
