package dataManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import criteria.Criteria;
import errors.CriteriaFileError;

public class CSVreader {

	public static List<Criteria> reacCriteriasCSV(String csvFile, DataManager data) throws CriteriaFileError{
		List<Criteria> criterias = new ArrayList<Criteria>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	br.readLine(); // TODO aca deberia realizar algun chequeo?
	    	String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(";");
				if (parts.length == 2) {
				    String name = parts[0].trim();
				    CriteriaManager.checkValidCriteriaName(name, null, criterias);
				    
				    String valoresString = parts[1].trim();
				    
					String[] values;
					
					// Create a Criterion object and add it to the list
					Criteria criteria;
					if (valoresString.matches("^between\\(\\d+,\\d+\\)$")) {
						values = obtenerValoresBetween(valoresString);
						
						criteria = new Criteria(name, values, true);
		            }else {
		            	// Remove brackets and spaces and divide by commas
		            	values = valoresString.replaceAll("[\\[\\] ]", "").split(",");
		            	
		            	criteria = new Criteria(name, values, false);
		            }
					criterias.add(criteria);
	            }
	        }
	        
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    return criterias;
	}
	
	private static String[] obtenerValoresBetween(String cadena) {
        String[] valores = new String[2];

        // Define un patrón regex para buscar el formato between(x, y)
        Pattern pattern = Pattern.compile("between\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(cadena);

        // Si encuentra el patrón, obtén los valores
        if (matcher.matches()) {
            valores[0] = matcher.group(1);
            valores[1] = matcher.group(2);
        }

        return valores;
    }
	
}

