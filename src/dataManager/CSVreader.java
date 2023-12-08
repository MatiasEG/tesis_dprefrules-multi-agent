package dataManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import criteria.Criteria;
import errors.AgentPriorityError;
import errors.CriteriaFileError;
import evidence.ParticipantsPriority;
import evidence.ParticipantsPriorityValidations;

public class CSVreader {

	public static List<Criteria> reacCriteriasCSV(String csvFile, DataManager data) throws CriteriaFileError{
		List<Criteria> criterias = new ArrayList<Criteria>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	if(br.readLine().equals("criterion;values")) {
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
							values = obtainValuesBetween(valoresString);
							
							criteria = new Criteria(name, values, true);
			            }else {
			            	// Remove brackets and spaces and divide by commas
			            	values = valoresString.replaceAll("[\\[\\] ]", "").split(",");
			            	
			            	criteria = new Criteria(name, values, false);
			            }
						criterias.add(criteria);
		            }else{
		            	throw new CriteriaFileError("El archivo no contiene la sintaxis correspondiente.");
		            }
				}
	    	}else {
	    		throw new CriteriaFileError("El archivo no contiene la sintaxis correspondiente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    return criterias;
	}
	
	private static String[] obtainValuesBetween(String cadena) {
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
	
	
	public static void readAgentPriorityCSV(String csvFile, DataManager oldData) throws AgentPriorityError {
		DataManager newData = new DataManager();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	if(br.readLine().equals("priority_order")) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(">");
					if (parts.length == 2) {
					    String morePriorAgent = parts[0].trim();
					    String lessPriorAgent = parts[1].trim();
					    
					    String nameValidations1 = ParticipantsPriorityValidations.validateAgentName(morePriorAgent, newData);
					    if(!nameValidations1.equals("OK")) throw new AgentPriorityError(nameValidations1);
					    
					    String nameValidations2 = ParticipantsPriorityValidations.validateAgentName(lessPriorAgent, newData);
					    if(!nameValidations2.equals("OK")) throw new AgentPriorityError(nameValidations2);
					    
						ParticipantsPriority newParticipantsPriority = new ParticipantsPriority(morePriorAgent, lessPriorAgent);
						String validPrior = newParticipantsPriority.isValid(newData);
					    
					    if(validPrior.equals("OK")) {
					    	newData.addParticipantsPriority(newParticipantsPriority);
					    	ParticipantsPriorityValidations.ifNotExistAddNewAgent(morePriorAgent, newData);
					    	ParticipantsPriorityValidations.ifNotExistAddNewAgent(lessPriorAgent, newData);
					    }else {
					    	throw new AgentPriorityError(validPrior);
					    }
		            }else {
		            	throw new AgentPriorityError("El archivo no contiene la sintaxis correspondiente.");
		            }
				}
		        oldData.updateData(newData);
	    	}else {
	    		throw new AgentPriorityError("El archivo no contiene la sintaxis correspondiente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}

