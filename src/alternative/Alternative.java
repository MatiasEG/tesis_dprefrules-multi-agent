package alternative;

import java.util.ArrayList;
import java.util.List;

import criteria.Criteria;

public class Alternative {

	protected String name;
	protected List<CriteriaValue> valuesObject;
	
	public Alternative(String name) {
		this.name = name;
		valuesObject = new ArrayList<CriteriaValue>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getValues(){
		List<String> values = new ArrayList<String>();
		for(CriteriaValue cv : valuesObject) {
			values.add(cv.getValue());
		}
		return values;
	}
	
	public void removeCriteria(String criteriaName) {
		for(int i=0; i<valuesObject.size(); i++) {
			if(valuesObject.get(i).getCriteria().getName().equals(criteriaName)) {
				valuesObject.remove(i);
				break;
			}
		}
	}
	
	public void addCriteriaValue(Criteria criteria, String value) {
		valuesObject.add(new CriteriaValue(criteria, value));
	}
	
	public String toString() {
		String toString = name;
		List<String> values = getValues();
		for(String s: values) {
			toString += ";"+s;
		}
		
		return toString;
	}
	
}
