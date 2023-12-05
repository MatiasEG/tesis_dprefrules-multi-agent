package dataManager;

import java.util.ArrayList;
import java.util.List;

import criteria.Criteria;

public class DataManager {

	private List<Criteria> criterias;
	
	public DataManager() {
		criterias = new ArrayList<Criteria>();
	}
	
	public void addCriteria(Criteria criteria) {
		criterias.add(criteria);
	}
	
	public List<Criteria> getCriterias(){
		return criterias;
	}
	
	public Criteria getCriteria(String name) {
		for(Criteria cr: criterias) {
			if(cr.getName().equals(name)) {
				return cr;
			}
		}
		return null;
	}
}
