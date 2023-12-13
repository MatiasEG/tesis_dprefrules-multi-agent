package premise;

import criteria.Criteria;
import errors.NoDefinedValueException;

public class WPremise extends Premise{

	protected boolean maxDistBetweenXYIsDefined;
	protected int maxDistBetweenXY;
	
	public WPremise(Criteria evaluatedCriteria) {
		super(evaluatedCriteria);
		maxDistBetweenXYIsDefined = false;
	}

	public String toString() {
		return "la alternativa X es peor que Y en el criterio "+evaluatedCriteria.getName()+",";
	}
	
	public void setMaxDistBetweenXY(int dist) {
		maxDistBetweenXY = dist;
		maxDistBetweenXYIsDefined = true;
	}
	
	public int getMaxDistBetweenXY() throws NoDefinedValueException {
		if(maxDistBetweenXYIsDefined) {
			return maxDistBetweenXY;
		}else {
			throw new NoDefinedValueException("La distancia maxima entre X e Y en el criterio "+evaluatedCriteria.getName()+" no ha sido definida.");
		}
	}
	
	public void removeMaxDistBetweenXY() {
		maxDistBetweenXY = 0;
		maxDistBetweenXYIsDefined = false;
	}
}
