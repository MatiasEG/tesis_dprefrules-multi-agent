package dataManager;

import errors.CriteriaError;

public class DataValidations {
	
	// TODO consultar si puede haber simbolos, no deberia afectar en nada si los hay (puede afectar con las llaves)
		public static CriteriaError validateStringWithOnlyLettersAndNumbers(String s) {
			if(s == null || s.equals("")) return new CriteriaError(s, "", "Cadena de caracteres vacia.");
			
			for(int i = 0; i < s.length(); i++) {
				if(!Character.isLetterOrDigit(s.charAt(i))) {
					return new CriteriaError(s, ""+s.charAt(i), "Caracter invalido ("+s.charAt(i)+") en "+s+".");
				}
			}
			return null;
		}
}
