package prefRules;

import criteria.Criteria;

public class WPremise {

	private Criteria criteria;
	private int maxDistBetweenXY;
	private int minValueForX;
	private int maxValueForY;
	
	public WPremise(Criteria criteria) {
		this.criteria = criteria;
		maxDistBetweenXY = 0;
		minValueForX = -1;
		maxValueForY = -1;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public int getMaxDistBetweenXY() {
		return maxDistBetweenXY;
	}

	public void setMaxDistBetweenXY(int maxDistBetweenXY) {
		this.maxDistBetweenXY = maxDistBetweenXY;
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
