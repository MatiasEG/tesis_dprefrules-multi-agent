package premise;

import criteria.Criteria;
import errors.NoDefinedValueException;

public class BPremise extends Premise{
	
	protected boolean minDistBetweenXYIsDefined;
	protected int minDistBetweenXY;
	
	public BPremise(Criteria evaluatedCriteria) {
		super(evaluatedCriteria);
		minDistBetweenXYIsDefined = false;
	}

	public String toString() {
		return "la alternativa X es mejor que Y en el criterio "+evaluatedCriteria.getName()+",";
	}
	
	public void setMinDistBetweenXY(int dist) {
		minDistBetweenXY = dist;
		minDistBetweenXYIsDefined = true;
	}
	
	public int getMinDistBetweenXY() throws NoDefinedValueException {
		if(minDistBetweenXYIsDefined) {
			return minDistBetweenXY;
		}else {
			throw new NoDefinedValueException("La distancia minima entre X e Y en el criterio "+evaluatedCriteria.getName()+" no ha sido definida.");
		}
	}
	
	public void removeMinDistBetweenXY() {
		minDistBetweenXY = 0;
		minDistBetweenXYIsDefined = false;
	}
}
