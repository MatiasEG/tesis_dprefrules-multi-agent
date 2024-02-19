package criteria;

import java.util.Random;

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
		
		if(!isNumeric) {
			String[] comboBoxValues = new String[values.length + 1];
			comboBoxValues[0] = "-";
	        System.arraycopy(values, 0, comboBoxValues, 1, values.length);
			comboValues = new JComboBox<String>(comboBoxValues);
			comboValues.setSelectedIndex(0);
		}else {
			comboValues = null;
		}
		
		this.noInformationValue =  addNoInformationValue();
	}
	
	private String addNoInformationValue() {
		if(isNumeric) {
			int minValue = Math.min(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
			boolean ascendent;
			if(minValue == (Integer.parseInt(values[0]))){
				ascendent = true;
			}else {
				ascendent = false;
			}
			
			if(ascendent) {
				setNoInformationValue(""+((Integer.parseInt(values[0]))-1));
			}else {
				setNoInformationValue(""+((Integer.parseInt(values[0]))+1));
			}
			
			return getNoInformationValue();
		}else {
			boolean finish = false;
			while(!finish) {
				String random = rndString();
				boolean valid = true;
				for(String str : values) {
					if(str.equals(random)) {
						valid = false;
						break;
					}
				}
				if(valid) {
					finish = true;
					setNoInformationValue(random);
					return getNoInformationValue();
				}
			}
		}
		return "";
    }
	
	private static String rndString() {
		int size = 4;
        String caracteres = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder cadenaRandom = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int indice = random.nextInt(caracteres.length());
            char caracter = caracteres.charAt(indice);
            cadenaRandom.append(caracter);
        }

        return cadenaRandom.toString();
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
		
		this.noInformationValue =  addNoInformationValue();
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

	public boolean isNumeric() {
		return isNumeric;
	}

	public void setNumeric(boolean isNumeric) {
		this.isNumeric = isNumeric;
	}

	public JComboBox<String> getComboValues() {
		return comboValues;
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
