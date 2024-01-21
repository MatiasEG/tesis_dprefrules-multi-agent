package dataManager;

import java.util.List;

public class StringValidations {
	
	public static String validateStringWithOnlyLettersAndNumbers(String s) {
		if(s == null || s.equals("")) return "Cadena de caracteres vacia";
		
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isLetterOrDigit(s.charAt(i))) {
				return "Caracter invalido ("+s.charAt(i)+") en "+s+".";
			}
		}
		return null;
	}
	
	public static boolean validateStringListNotContainNewElement(List<String> list, String newElement) {
		for(String str: list) {
			if(str.equals(newElement)) {
				return false;
			}
		}
		return true;
	}
	
}
