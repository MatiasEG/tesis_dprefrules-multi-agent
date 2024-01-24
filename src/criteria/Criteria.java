package criteria;

import javax.swing.JComboBox;

public class Criteria {
	
	protected String name;
	protected String[] values;
	protected boolean isNumeric;
	protected JComboBox<String> comboValues;
	protected String noInformationValue;
	
	public Criteria(String name, String[] values, boolean isNumeric) {
		this.name = name;
		this.values = values;
		this.isNumeric = isNumeric;
		this.noInformationValue = "";
		
		if(!isNumeric) {
			String[] comboBoxValues = new String[values.length + 1];
			comboBoxValues[0] = "-";
	        System.arraycopy(values, 0, comboBoxValues, 1, values.length);
			comboValues = new JComboBox<String>(comboBoxValues);
			comboValues.setSelectedIndex(0);
		}else {
			comboValues = null;
		}
	}
	
	public void setNoInformationValue(String noInformationValue) {
		this.noInformationValue = noInformationValue;
	}
	
	public String getNoInformationValue() {
		return this.noInformationValue;
	}
	
	public void updateCriteria(String name, String[] values, boolean isNumeric) {
		this.name = name;
		this.values = values;
		this.isNumeric = isNumeric;
		
		if(!isNumeric) {
			String[] comboBoxValues = new String[values.length + 1];
			comboBoxValues[0] = "-";
	        System.arraycopy(values, 0, comboBoxValues, 1, values.length);
			comboValues = new JComboBox<String>(values);
			comboValues.setSelectedIndex(0);
		}else {
			comboValues = null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public boolean isNumeric() {
		return isNumeric;
	}

	public void setNumeric(boolean isNumeric) {
		this.isNumeric = isNumeric;
	}

	public JComboBox<String> getComboValues() {
		return comboValues;
	}

	public void setComboValues(JComboBox<String> comboValues) {
		this.comboValues = comboValues;
	}
	
	public String getCriteriaValuesString() {
		String valuesFormatted = "";
		for(String myStr: values) {
			valuesFormatted += myStr+",";
	    }
		valuesFormatted = valuesFormatted.substring(0, valuesFormatted.length()-1);
		
		return valuesFormatted;
	}
	
	public boolean valueIsValid(String value) {
		if(!value.equals("-")) {
			if(isNumeric) {
	    		try {
	    			int x = Integer.parseInt(value);
	    			int max = Math.max(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
	    			int min = Math.min(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
	    			
	    			if(x>max || x<min) return false;
	    			
	    		}catch(NumberFormatException e) {
	    			return false;
	    		}
	    	}else {
	    		for(int i=0; i<values.length; i++) {
	    			if(values[i].equals(value)) {
	    				return true;
	    			}
	    		}
	    		return false;
	    	}
		}
		return true;
	}
}
