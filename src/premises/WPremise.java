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
	
	public String getDescription() {
		String description = "el criterio <"+criteria.getName()+"> de X si es peor que Y";
		if(maxDistBetweenXY!=0)
			description += ", con una distancia maxima entre las alternativas de como maximo ("+maxDistBetweenXY+")";
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
		String premise = "worst(X,Y,"+criteria.getName()+")";
		if(maxDistBetweenXY!=0)
			premise += ", max_dist(X,Y,"+maxDistBetweenXY+")";
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
