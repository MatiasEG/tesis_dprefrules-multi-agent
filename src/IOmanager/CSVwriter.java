package IOmanager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import alternative.Alternative;
import criteria.Criteria;
import dataManager.DataManager;
import dataManager.DataManagerCriteria;
import dataManager.DataManagerParticipant;
import dataManager.Priority;
import participant.Participant;
import prefRules.Rule;

public class CSVwriter {

	public static void saveCriteriasToCSV(DataManagerCriteria data) {
		String filePath = data.getDataManager().getSaveFolder()+"\\"+data.getDataManager().getProjectName()+"_criteria.csv";
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("criterion;values");
            writer.newLine();

            // write data
            for(Criteria criteria: data.getCriterias()) {
            	writer.write(criteria.getName()+";");
            	String values = "";
            	if(!criteria.isNumeric()) {
            		values += "[";
            		values += putAllValues(criteria);
            		values += "]";
            	}else {
            		values += "between(";
            		values += putAllValues(criteria);
            		values += ")";
            	}
            	writer.write(values);
            	writer.newLine();
            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static String putAllValues(Criteria criteria) {
		String values = "";
		values += criteria.getNoInformationValue();
		for(int i=0; i<criteria.getValues().length; i++) {
			if(criteria.isNumeric() && i==0) {
				// discard
			}else {
				values += ","+criteria.getValues()[i];
			}
		}
		return values;
	}
	
	public static void saveAgentPriorityToCSV(DataManagerParticipant data) {
		String filePath = data.getDataManager().getSaveFolder()+"\\"+data.getDataManager().getProjectName()+"_participants_priority_order.csv";
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("priority_order");
            writer.newLine();

            // write data
            for(Priority prior: data.getParticipantsPriority()) {
            	writer.write(prior.getPriorityFormatted());
            	writer.newLine();
            }
            
            // TODO estas son implicitas no deberia escribirlas cierto?
//            for(Priority prior: data.getParticipantsPriorityTransitive()) {
//            	writer.write(prior.getPriorityFormatted());
//            	writer.newLine();
//            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void saveEvidenceToCSV(DataManager data) {
		String filePath = data.getSaveFolder()+"/"+data.getProjectName()+"_evidence.csv";
		filePath = checkCSVextension(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			String header = "alternative";
			for(Criteria criteria: data.getDataManagerCriteria().getCriterias()) {
				header += ";"+criteria.getName();
			}
			
            writer.write(header);
            writer.newLine();

            // write data
            for(Alternative alt: data.getAlternatives()) {
            	String values[] = alt.evidenceFileContent();
            	writer.write(values[0]);
            	
//            	for(int i=0; i<values.length; i++) {
//            		System.out.print(" - "+values[i]);
//            	}
            	
            	System.out.println();
            	for(int i=1; i<values.length; i++) {
            		System.out.print(values[i]+" - - ");
            		if(values[i].equals("-")) {
            			writer.write(";"+data.getDataManagerCriteria().getCriterias().get(i-1).getNoInformationValue());
            		}else {
            			writer.write(";"+values[i]);
            		}
            	}
            	writer.newLine();
            	System.out.println();
            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private static String checkCSVextension(String path) {
		if(!path.endsWith(".csv")) {
			path += ".csv";
		}
		return path;
	}
	
	public static void saveRulesToCSV(DataManager data) {
		String filePath = data.getSaveFolder()+"/"+data.getProjectName()+"_rules.csv";
		filePath = checkCSVextension(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			String header = "id;rule";
			
            writer.write(header);
            writer.newLine();

            // write data
            for(Rule rule: data.getRules()) {
            	writer.write(rule.toString());
            	writer.newLine();
            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void saveRulePriorityToCSV(DataManager data) {
		String filePath = data.getSaveFolder()+"/"+data.getProjectName()+"_importance_orders.csv";
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("agent;importance_order");
            writer.newLine();

            // write data
            for(Participant participant : data.getDataManagerParticipant().getParticipants()) {
            	writer.write(participant.getName()+";");
            	for(int i=0; i<participant.getPreferences().size(); i++) {
            		writer.write(participant.getPreferences().get(i).getPriorityFormatted());
            		if(i<participant.getPreferences().size()-1) writer.write(",");
            	}
            	writer.newLine();
            }
            
            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
