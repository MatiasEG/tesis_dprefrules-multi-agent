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
		String description = "Pero X es peor que Y en el criterio -"+criteria.getName()+"-";
		if(maxDistBetweenXY!=0)
			description += ", con una distancia maxima entre las alternativas de como maximo ("+maxDistBetweenXY+")";
		if(minValueForX!=-1)
			if(!criteria.isNumeric())
				description += ", donde X vale al menos "+criteria.getValues()[minValueForX]+"";
			else
				description += ", donde X vale al menos "+minValueForX+"";
		if(maxValueForY!=-1)
			if(!criteria.isNumeric())
				description += ", donde Y vale como maximo "+criteria.getValues()[maxValueForY]+"";
			else
				description += ", donde Y vale como maximo "+maxValueForY+"";
		description += ".";
		return description;
	}
}
