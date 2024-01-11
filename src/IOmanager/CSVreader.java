package IOmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import agent.AgentPriority;
import agent.AgentPriorityValidations;
import alternative.Alternative;
import criteria.Criteria;
import dataManager.CriteriaManager;
import dataManager.DataManager;
import errors.AgentPriorityError;
import errors.CriteriaFileError;
import errors.EvidenceFileError;

public class CSVreader {

	public static void readCriteriasCSV(String csvFile, DataManager oldData) throws CriteriaFileError{
		DataManager newData = new DataManager(oldData.getProyectName(), oldData.getSaveFolder());
		newData.setParticipants(oldData.getParticipants());
		newData.setParticipantsPriority(oldData.getParticipantsPriority());
		
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
						//newData.addCriteria(criteria);
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
		newData.setCriterias(criterias);
		oldData.updateData(newData);
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
		DataManager newData = new DataManager(oldData.getProyectName(), oldData.getSaveFolder());
		newData.setCriterias(oldData.getCriterias());
		newData.setAlternatives(oldData.getAlternatives());
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	if(br.readLine().equals("priority_order")) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(">");
					if (parts.length == 2) {
					    String morePriorAgent = parts[0].trim();
					    String lessPriorAgent = parts[1].trim();
					    
					    String nameValidations1 = AgentPriorityValidations.validateAgentName(morePriorAgent, newData);
					    if(!nameValidations1.equals("OK")) throw new AgentPriorityError(nameValidations1);
					    
					    String nameValidations2 = AgentPriorityValidations.validateAgentName(lessPriorAgent, newData);
					    if(!nameValidations2.equals("OK")) throw new AgentPriorityError(nameValidations2);
					    
						AgentPriority newParticipantsPriority = new AgentPriority(morePriorAgent, lessPriorAgent);
						String validPrior = newParticipantsPriority.isValid(newData);
					    
					    if(validPrior.equals("OK")) {
					    	newData.addParticipantsPriority(newParticipantsPriority);
					    	AgentPriorityValidations.ifNotExistAddNewAgent(morePriorAgent, newData);
					    	AgentPriorityValidations.ifNotExistAddNewAgent(lessPriorAgent, newData);
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
	
	public static void readEvidenceCSV(String csvFile, DataManager oldData) throws EvidenceFileError {
		DataManager newData = new DataManager(oldData.getProyectName(), oldData.getSaveFolder());
		newData.setCriterias(oldData.getCriterias());
		newData.setParticipants(oldData.getParticipants());
		newData.setParticipantsPriority(oldData.getParticipantsPriority());
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String header = "alternative";
			for(Criteria criteria: newData.getCriterias()) {
				header += ";"+criteria.getName();
			}
			
	    	if(br.readLine().equals(header)) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(";");
					if (parts.length == (newData.getCriterias().size()+1)) {
						String altName = parts[0].trim();
						Alternative alt = new Alternative(altName);
						newData.addAlternative(alt);
						int altIndex = newData.getAlternatives().size()-1;
						
						for(int i=1; i<parts.length; i++) {
							if(newData.getCriterias().get(i-1).valueIsValid(parts[i].trim())) {
								newData.getAlternatives().get(altIndex).addValue(parts[i].trim());
							}else {
				            	throw new EvidenceFileError("El criterio ("+newData.getCriterias().get(i-1).getName()+") contiene una valor no valido ("+parts[i].trim()+") en el archivo de evidencia.");
							}
						}
		            }else {
		            	throw new EvidenceFileError("El archivo no contiene la sintaxis correspondiente.");
		            }
				}
		        oldData.updateData(newData);
	    	}else {
	    		throw new EvidenceFileError("El archivo no contiene los criterios definidos anteriormente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}

