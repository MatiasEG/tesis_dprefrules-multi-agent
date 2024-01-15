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
	
}
