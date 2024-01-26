package dataManager;

import java.util.ArrayList;
import java.util.List;

import alternative.Alternative;

public class DataManagerEvidence {

	protected List<Alternative> alternatives;
	
	protected DataManager data;
	
	public DataManagerEvidence(DataManager data) {
		this.data = data;
		
		alternatives = new ArrayList<Alternative>();
	}
	
	// add --------------------------------------------------------------------------------------------------------------
	public void addAlternative(Alternative newAlternative) {
		alternatives.add(newAlternative);
	}
	
	// set --------------------------------------------------------------------------------------------------------------
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}
	
	// get --------------------------------------------------------------------------------------------------------------
	public List<Alternative> getAlternatives(){
		return alternatives;
	}
	
	public List<String> getAlternativesNames(){
		List<String> names = new ArrayList<String>();
		for(Alternative alt: alternatives) {
			names.add(alt.getName());
		}
		return names;
	}
	
	// delete --------------------------------------------------------------------------------------------------------------
	public void removeAlternative(String alternative) {
		for(int i=0; i<alternatives.size(); i++) {
			if(alternatives.get(i).getName().equals(alternative)) {
				alternatives.remove(i);
				break;
			}
		}
	}
	
	public void removeEvidence(String criteriaName) {
		for(Alternative alt : alternatives) {
			alt.removeCriteria(criteriaName);
		}
	}
	
	// check --------------------------------------------------------------------------------------------------------------
	
	// update --------------------------------------------------------------------------------------------------------------
	public void updateData(DataManager newData) {
		this.data = newData;
		
		alternatives = new ArrayList<Alternative>();
		
		for(Alternative a : newData.getDataManagerEvidence().getAlternatives()) {
			alternatives.add(a.clone());
		}
	}
}
