package prefRules;

import criteria.Criteria;

public class EPremise {

	private Criteria criteria;
	private int minValueForX;
	private int maxValueForY;
	
	public EPremise(Criteria criteria) {
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
		if(maxValueForY == -1) {
			this.minValueForX = minValueForX;
		}
	}

	public int getMaxValueForY() {
		return maxValueForY;
	}

	public void setMaxValueForY(int maxValueForY) {
		if(minValueForX == -1) {
			this.maxValueForY = maxValueForY;
		}
	}
	
	
}
