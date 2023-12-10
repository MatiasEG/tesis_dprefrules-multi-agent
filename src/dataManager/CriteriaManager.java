package dataManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import criteria.Criteria;
import errors.SintacticStringError;

public class CriteriaManager {

	public static SintacticStringError checkValidCriteriaName(String newCriteriaName, Criteria criteriaUpdate, List<Criteria> criterias) {
		DataValidations.validateStringWithOnlyLettersAndNumbers(newCriteriaName);
		
		SintacticStringError syntaxError = DataValidations.validateStringWithOnlyLettersAndNumbers(newCriteriaName);
		if(syntaxError==null) {
			if(criteriaUpdate!=null) {
				if(criteriaUpdate.getName().equals(newCriteriaName)) {
					return null;
				}else {
					for(Criteria cr: criterias) {
						if(cr.getName().equals(newCriteriaName)) {
							return new SintacticStringError("newCriteriaName", "", "Nombre de criterio duplicado");
						}
					}
				}
			}else {
				for(Criteria cr: criterias) {
					if(cr.getName().equals(newCriteriaName)) {
						return new SintacticStringError("newCriteriaName", "", "Nombre de criterio duplicado");
					}
				}
			}
		}else {
			return syntaxError;
		}
		return null;
	}
	
	// TODO debo considerar las mayusculas? VALOR == valor == VaLoR?
	public static SintacticStringError checkValidCriteriaValues(String values, boolean isNumeric) {
		if(isNumeric) {
			return checkValidCriteriaNumericValues(values);
		}else {
			return checkValidCriteriaSimbolicValues(values);
		}
	}
	
	public static SintacticStringError checkValidCriteriaNumericValues(String values) {
		String[] splittedValues = values.trim().split("\\s*,\\s*");
		try {
			if(splittedValues.length == 2) {
				Integer.parseInt(splittedValues[0]);
				Integer.parseInt(splittedValues[1]);
			}else {
				// There is a missing number, but that is checked before accepting to save the criteria
			}
		}catch (NumberFormatException e) {
			return new SintacticStringError(splittedValues[0]+","+splittedValues[1], "", "Uno de los siguientes campos no es un numero valido: ("+splittedValues[0]+")-("+splittedValues[1]+")");
		}
		
		return null;
	}
	
	public static SintacticStringError checkValidCriteriaSimbolicValues(String values) {
		String[] splittedValues = values.trim().split("\\s*,\\s*");
		
		//Create Set with all the elements in the array
        Set<String> set = new HashSet<String>(Arrays.asList(splittedValues));

        //since Set cannot contain duplicates, so if array size and
        // HashSet size then it can be concluded that array has all
        //distinct or unique elements otherwise its not
        if(!(splittedValues.length==set.size())){
        	// Given array does not contains all unique elements, and contains duplicate elements 
            return new SintacticStringError(values, "", "La lista de valores para el criterio contiene un valor duplicado.");
        }
        
        for(String myStr: splittedValues) {
        	SintacticStringError syntaxError = DataValidations.validateStringWithOnlyLettersAndNumbers(myStr);
        	if(syntaxError!=null) return syntaxError;
	    }

		return null;
	}

}
