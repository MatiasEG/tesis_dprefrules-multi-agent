package dataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import criteria.Criteria;

public class DataManagerCriteria {

	protected List<Criteria> criterias;
	
	protected DataManager data;
	
	public DataManagerCriteria(DataManager data) {
		this.data = data;
		
		criterias = new ArrayList<Criteria>();
	}
	
	// add --------------------------------------------------------------------------------------------------------------
	public void addCriteria(Criteria criteria) {
		criterias.add(criteria);
	}
	
	// set --------------------------------------------------------------------------------------------------------------
	public void setCriterias(List<Criteria> newCriterias) {
		criterias = newCriterias;
	}
	
	// get --------------------------------------------------------------------------------------------------------------
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
	
	// delete --------------------------------------------------------------------------------------------------------------
	public void removeCriteria(String name) {
		for(int i=0; i<criterias.size(); i++) {
			if(criterias.get(i).getName().equals(name)) {
				criterias.remove(i);
				break;
			}
		}
	}
	
	// check --------------------------------------------------------------------------------------------------------------
	public boolean validCriteriaName(String name) {
		if(name == null || name.equals("")) return false;
		
		for(int i = 0; i < name.length(); i++) {
			if(!Character.isLetterOrDigit(name.charAt(i))) {
				return false;
			}
		}
		
		for(Criteria criteria : criterias) {
			if(criteria.getName().equals(name)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validCriteriaValues(String[] values, boolean isNumeric) {
		if(!isNumeric) {
			//Create Set with all the elements in the array
	        Set<String> set = new HashSet<String>(Arrays.asList(values));

	        // since Set cannot contain duplicates, so if array size and
	        // HashSet size then it can be concluded that array has all
	        // distinct or unique elements otherwise its not
	        if(!(values.length==set.size())){
	        	// Given array does not contains all unique elements, and contains duplicate elements 
	            return false;
	        }
	        
	        for(String myStr: values) {
	        	myStr = myStr.toLowerCase();
	        	String validation = DataManager.validateStringWithOnlyLetters(myStr);
	        	if(validation!=null) return false;
		    }
		}else {
			try {
				if(values.length == 2) {
					Integer.parseInt(values[0]);
					Integer.parseInt(values[1]);
				}else {
					return false;
				}
			}catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	// update --------------------------------------------------------------------------------------------------------------
	public void updateData(DataManager newData) {
		this.data = newData;
		
		criterias = new ArrayList<Criteria>();
		
		for(Criteria c : newData.getDataManagerCriteria().getCriterias()) {
			criterias.add(c);
		}
	}
}
