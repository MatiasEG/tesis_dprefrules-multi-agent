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
	
	public void updateOrAddCriteriaValue(Criteria criteria, String value) {
		boolean finish = false;
		for(CriteriaValue cv : valuesObject) {
			if(cv.getCriteria().getName().equals(criteria.getName())) {
				cv.setValue(value);
				finish = true;
				break;
			}
		}
		
		if(!finish) {
			valuesObject.add(new CriteriaValue(criteria, value));
		}
	}
	
	public String[] evidenceFileContent() {
		String[] toString = new String[valuesObject.size()+1];
		check(toString);
		checkCriterias();
		toString[0] = name;
		List<String> values = getValues();
		for(int i=1; i<=values.size(); i++) {
			toString[i] = values.get(i-1);
		}
		
		return toString;
	}
	
	//TODO borrar --------------------------------------------------------------------------
	private void check(String[] str) {
		System.out.println("CHECK");
		for(int i=0; i<str.length; i++) {
			System.out.print(" - "+str[i]);
		}
		System.out.println();
	}
	
	private void checkCriterias() {
		System.out.println("CHECK CRITERIAS");
		for(int i=0; i<valuesObject.size(); i++) {
			System.out.print(" - ("+valuesObject.get(i).getCriteria().getName()+","+valuesObject.get(i).getValue()+")");
		}
		System.out.println();
	}
	
}
