package prefRules;

import criteria.Criteria;

public class BPremise {

	private Criteria criteria;
	private int minDistBetweenXY;
	private int minValueForX;
	private int maxValueForY;
	
	public BPremise(Criteria criteria) {
		this.criteria = criteria;
		minDistBetweenXY = 0;
		minValueForX = -1;
		maxValueForY = -1;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public int getMinDist() {
		return minDistBetweenXY;
	}

	public void setMinDist(int minDist) {
		this.minDistBetweenXY = minDist;
	}

	public int getMinValueForX() {
		return minValueForX;
	}

	public void setMinValueForX(int minValueForX) {
		this.minValueForX = minValueForX;
	}

	public int getMaxValueForY() {
		return maxValueForY;
	}

	public void setMaxValueForY(int maxValueForY) {
		this.maxValueForY = maxValueForY;
	}
	
	
}
