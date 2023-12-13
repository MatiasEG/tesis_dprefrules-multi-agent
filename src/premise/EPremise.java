package premise;

import criteria.Criteria;

public class EPremise extends Premise{

	public EPremise(Criteria evaluatedCriteria) {
		super(evaluatedCriteria);
	}

	public String toString() {
		return "la alternativa X es igual que Y en el criterio "+evaluatedCriteria.getName()+",";
	}
}
