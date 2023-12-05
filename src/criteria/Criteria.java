package criteria;

import javax.swing.JComboBox;

public class Criteria {
	
	protected String name;
	protected String[] values;
	protected boolean isNumeric;
	protected JComboBox<String> comboValues;
	
	public Criteria(String name, String[] values, boolean isNumeric) {
		this.name = name;
		this.values = values;
		this.isNumeric = isNumeric;
		
		if(!isNumeric) {
			comboValues = new JComboBox<String>(values);
		}else {
			comboValues = null;
		}
	}
	
	public void updateCriteria(String name, String[] values, boolean isNumeric) {
		this.name = name;
		this.values = values;
		this.isNumeric = isNumeric;
		
		if(!isNumeric) {
			comboValues = new JComboBox<String>(values);
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
}
