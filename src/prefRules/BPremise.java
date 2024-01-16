package prefRules;

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
		String description = "Si X es mejor que Y en el criterio -"+criteria.getName()+"-";
		if(minDistBetweenXY!=0)
			description += ", con una distancia minima entre las alternativas de al menos ("+minDistBetweenXY+")";
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
