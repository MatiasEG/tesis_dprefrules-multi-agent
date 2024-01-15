package prefRules;

import criteria.Criteria;

public class EPremise extends Premise{
	
	public EPremise(Criteria criteria) {
		super(criteria);
	}

	public void setMinValueForX(int minValueForX) {
		if(maxValueForY == -1) {
			this.minValueForX = minValueForX;
		}
	}

	public void setMaxValueForY(int maxValueForY) {
		if(minValueForX == -1) {
			this.maxValueForY = maxValueForY;
		}
	}
	
}
