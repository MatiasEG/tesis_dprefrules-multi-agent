package premises;

import criteria.Criteria;

public abstract class Premise {

	protected Criteria criteria;
	protected int minValueForX;
	protected int maxValueForY;
	
	public Premise(Criteria criteria) {
		this.criteria = criteria;
		minValueForX = -1;
		maxValueForY = -1;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
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
	
	public abstract String getDescription();
	
	public abstract String getPremise();
}
