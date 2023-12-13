package premise;

import criteria.Criteria;

public abstract class Premise {

	protected Criteria evaluatedCriteria;
	
	public abstract String toString();
	
	public Criteria getEvaluatedCriteria() {
		return evaluatedCriteria;
	}
	
	public String getNameOfEvaluatedCriteria() {
		return evaluatedCriteria.getName();
	}
}
