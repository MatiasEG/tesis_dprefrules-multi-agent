package IOmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alternative.Alternative;
import criteria.Criteria;
import dataManager.DataManager;
import dataManager.Priority;
import exceptions.AgentPriorityException;
import exceptions.CriteriaFileException;
import exceptions.EvidenceFileException;
import exceptions.RuleFileErrorException;
import exceptions.RulePriorityException;
import participant.Participant;
import prefRules.Rule;

public class CSVreader {

	public static void readCriteriasCSV(String csvFile, DataManager oldData) throws CriteriaFileException{
		DataManager newData = new DataManager(oldData.getProjectName(), oldData.getSaveFolder());
		newData.updateData(oldData);
		
		List<Criteria> criterias = new ArrayList<Criteria>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	if(br.readLine().equals("criterion;values")) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(";");
					if (parts.length == 2) {
					    String name = parts[0].trim();
					    
					    if(newData.getDataManagerCriteria().validCriteriaName(name)) {
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
					    }else {
					    	throw new CriteriaFileException("El archivo posee un nombre de criterio que es invalido ("+name+").");
					    }
		            }else{
		            	throw new CriteriaFileException("El archivo no contiene la sintaxis correspondiente.");
		            }
				}
	    	}else {
	    		throw new CriteriaFileException("El archivo no contiene la sintaxis correspondiente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
		newData.getDataManagerCriteria().setCriterias(criterias);
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
	
	
	public static void readAgentPriorityCSV(String csvFile, DataManager oldData) throws AgentPriorityException {
		DataManager newData = new DataManager(oldData.getProjectName(), oldData.getSaveFolder());
		newData.updateData(oldData);
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	if(br.readLine().equals("priority_order")) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(">");
					if (parts.length == 2) {
					    String morePriorAgent = parts[0].trim();
					    String lessPriorAgent = parts[1].trim();
					    
					    Participant morePriorParticipant = newData.getDataManagerParticipant().getParticipantByName(morePriorAgent);
					    Participant lessPriorParticipant = newData.getDataManagerParticipant().getParticipantByName(lessPriorAgent);
					    
					    if(morePriorParticipant==null && !newData.getDataManagerParticipant().validParticipantName(morePriorAgent)) throw new AgentPriorityException("El nombre "+morePriorAgent+" no es valido.");
					    
					    if(lessPriorParticipant==null && !newData.getDataManagerParticipant().validParticipantName(morePriorAgent)) throw new AgentPriorityException("El nombre "+lessPriorAgent+" no es valido.");
					    
						Priority newParticipantsPriority = new Priority(morePriorAgent, lessPriorAgent);
						String validPrior = newParticipantsPriority.isValid(newData);
					    
					    if(validPrior.equals("OK")) {
					    	newData.getDataManagerParticipant().addParticipantsPriority(newParticipantsPriority);
					    	if(morePriorParticipant==null) newData.getDataManagerParticipant().addParticipant(new Participant(morePriorAgent));
					    	if(lessPriorParticipant==null) newData.getDataManagerParticipant().addParticipant(new Participant(lessPriorAgent));
					    }else {
					    	throw new AgentPriorityException(validPrior);
					    }
		            }else {
		            	throw new AgentPriorityException("El archivo no contiene la sintaxis correspondiente.");
		            }
				}
		        oldData.updateData(newData);
	    	}else {
	    		throw new AgentPriorityException("El archivo no contiene la sintaxis correspondiente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void readEvidenceCSV(String csvFile, DataManager oldData) throws EvidenceFileException {
		DataManager newData = new DataManager(oldData.getProjectName(), oldData.getSaveFolder());
		newData.updateData(oldData);
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String header = "alternative";
			for(Criteria criteria: newData.getDataManagerCriteria().getCriterias()) {
				header += ";"+criteria.getName();
			}
			
	    	if(br.readLine().equals(header)) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(";");
					if (parts.length == (newData.getDataManagerCriteria().getCriterias().size()+1)) {
						String altName = parts[0].trim();
						Alternative alt = new Alternative(altName);
						newData.getDataManagerEvidence().addAlternative(alt);
						int altIndex = newData.getDataManagerEvidence().getAlternatives().size()-1;
						
						for(int i=1; i<parts.length; i++) {
							if(newData.getDataManagerCriteria().getCriterias().get(i-1).valueIsValid(parts[i].trim())) {
								newData.getDataManagerEvidence().getAlternatives().get(altIndex).updateOrAddCriteriaValue(newData.getDataManagerCriteria().getCriterias().get(i-1), parts[i].trim());
							}else {
				            	throw new EvidenceFileException("El criterio ("+newData.getDataManagerCriteria().getCriterias().get(i-1).getName()+") contiene una valor no valido ("+parts[i].trim()+") en el archivo de evidencia.");
							}
						}
		            }else {
		            	throw new EvidenceFileException("El archivo no contiene la sintaxis correspondiente.");
		            }
				}
		        oldData.updateData(newData);
	    	}else {
	    		throw new EvidenceFileException("El archivo no contiene los criterios definidos anteriormente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void readRulesCSV(String csvFile, DataManager oldData) throws RuleFileErrorException {
		DataManager newData = new DataManager(oldData.getProjectName(), oldData.getSaveFolder());
		newData.updateData(oldData);
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String header = "id;rule";
			
	    	if(br.readLine().equals(header)) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		            String[] parts = line.split(";");
					if (parts.length == 2) {
						String ruleName = parts[0].trim();
						Rule auxRule = new Rule(ruleName);
						
						String[] premises = parts[1].split("\\s*==>\\s*");
						if(premises.length==2 && premises[1].equals("pref(X,Y)")) {
							boolean validation = RuleParser.analizeRule(premises[0], auxRule, newData);
							if(validation) {
								if(auxRule.getBetterP().size()>0) {
									newData.getDataManagerRule().addRule(auxRule);
								}else {
									throw new RuleFileErrorException("La regla "+auxRule.getName()+" no contiene al menos una premisa better.");
								}
							}else {
								throw new RuleFileErrorException("El archivo no contiene la sintaxis correspondiente.");
							}
						}else {
							throw new RuleFileErrorException("(No se respeta el ==>) El archivo no contiene la sintaxis correspondiente.");
						}
		            }else {
		            	throw new RuleFileErrorException("(Contiene mas de un ;) El archivo no contiene la sintaxis correspondiente.");
		            }
				}
	    	}else {
	    		throw new RuleFileErrorException("(Header) El archivo no contiene la sintaxis correspondiente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		oldData.updateData(newData);
	}
	
	public static void readRulePriorityCSV(String csvFile, DataManager oldData) throws RulePriorityException {
		DataManager newData = new DataManager(oldData.getProjectName(), oldData.getSaveFolder());
		newData.updateData(oldData);
		for(Participant participant : newData.getDataManagerParticipant().getParticipants()) {
			participant.setRulePriority(new ArrayList<Priority>());
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	if(br.readLine().equals("agent;importance_order")) {
	    		String line;
		        while ((line = br.readLine()) != null) {
		        	String[] namePref = line.split(";");
		        	if(namePref.length==2) {
		        		String participantName = namePref[0].trim();
						String rulePreferences = namePref[1].trim();
						
						Participant participant = newData.getDataManagerParticipant().getParticipantByName(participantName);
						if(participant!=null) {
							String[] preferences = rulePreferences.split(",");
							for(String preference : preferences) {
								String[] parts = preference.split(">");
								if(parts.length==2) {
									 String morePriorRuleString = parts[0].trim();
									 String lessPriorRuleString = parts[1].trim();
									 
									 Rule morePriorRule = newData.getDataManagerRule().getRule(morePriorRuleString);
									 Rule lessPriorRule = newData.getDataManagerRule().getRule(lessPriorRuleString);
									 
									 if(morePriorRule!=null && lessPriorRule!=null) {
										 participant.addPreference(new Priority(morePriorRuleString, lessPriorRuleString));
									 }
								}else {
									throw new RulePriorityException("Las reglas de preferencia no contienen la sintaxis correspondiente.");
								}
							}
						}else {
							throw new RulePriorityException("El archivo contiene participantes que no estan definidos en el problema actual.");
						}
		        	}else {
		        		throw new RulePriorityException("El archivo no contiene la sintaxis correspondiente.");
		        	}
		        }
	    	}else {
	    		throw new RulePriorityException("El archivo no contiene el header correspondiente.");
	    	}
	    }catch (IOException e) {
	    	e.printStackTrace();
	    }
		oldData.updateData(newData);
	}
}

