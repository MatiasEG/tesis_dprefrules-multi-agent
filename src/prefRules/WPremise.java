package prefRules;

import criteria.Criteria;

public class WPremise extends Premise{

	private int maxDistBetweenXY;
	
	public WPremise(Criteria criteria) {
		super(criteria);
		maxDistBetweenXY = 0;
	}

	public int getMaxDistBetweenXY() {
		return maxDistBetweenXY;
	}

	public void setMaxDistBetweenXY(int maxDistBetweenXY) {
		this.maxDistBetweenXY = maxDistBetweenXY;
	}
	
}
