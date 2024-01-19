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
	
	public void setMinValueForX(String minValueForX) {
		validMinXValue(minValueForX);
		int value = -1;
		if(!criteria.isNumeric()) {
			for(int i=0; i<criteria.getValues().length; i++) {
				if(criteria.getValues()[i].equals(minValueForX)) {
					value = i;
				}
			}
		}else {
			try {
				value = Integer.parseInt(minValueForX);
			}catch(NumberFormatException e) {
				// Nothing to do
			}
		}
		setMinValueForX(value);
	}
	
	public abstract boolean validMinXValue(String minX);
	
	public abstract boolean validMaxYValue(String maxY);

	public int getMaxValueForY() {
		return maxValueForY;
	}

	public void setMaxValueForY(int maxValueForY) {
		this.maxValueForY = maxValueForY;
	}
	
	public void setMaxValueForY(String maxValueForY) {
		validMaxYValue(maxValueForY);
		int value = -1;
		if(!criteria.isNumeric()) {
			for(int i=0; i<criteria.getValues().length; i++) {
				if(criteria.getValues()[i].equals(maxValueForY)) {
					value = i;
				}
			}
		}else {
			try {
				value = Integer.parseInt(maxValueForY);
			}catch(NumberFormatException e) {
				// Nothing to do
			}
		}
		setMaxValueForY(value);
	}
	
	public abstract String getDescription();
	
	public abstract String getPremise();
}
