package premise;

import criteria.Criteria;
import errors.NoDefinedValueException;

public abstract class Premise {

	protected Criteria evaluatedCriteria;
	protected boolean minValueXIsDefined;
	protected int minValueX;
	protected boolean maxValueYIsDefined;
	protected int maxValueY;
	
	public Premise(Criteria evaluatedCriteria) {
		minValueXIsDefined = false;
		maxValueYIsDefined = false;
		minValueX = 0;
		maxValueY = 0;
		this.evaluatedCriteria = evaluatedCriteria;
	}
	
	public abstract String toString();
	
	public Criteria getEvaluatedCriteria() {
		return evaluatedCriteria;
	}
	
	public String getNameOfEvaluatedCriteria() {
		return evaluatedCriteria.getName();
	}
	
	public void setMinValueX(int value) {
		minValueX = value;
		minValueXIsDefined = true;
	}
	
	public int getMinValueX() throws NoDefinedValueException {
		if(minValueXIsDefined) {
			return minValueX;
		}else {
			throw new NoDefinedValueException("El valor minimo para X en el criterio "+evaluatedCriteria.getName()+" no ha sido definido.");
		}
	}
	
	public void removeMinValueX() {
		minValueX = 0;
		minValueXIsDefined = false;
	}
	
	public void setMaxValueY(int value) {
		maxValueY = value;
		maxValueYIsDefined = true;
	}
	
	public int getMaxValueY() throws NoDefinedValueException {
		if(maxValueYIsDefined) {
			return maxValueY;
		}else {
			throw new NoDefinedValueException("El valor maximo para Y en el criterio "+evaluatedCriteria.getName()+" no ha sido definido.");
		}
		
	}
	
	public void removeMaxValueY() {
		maxValueY = 0;
		maxValueYIsDefined = false;
	}
}
