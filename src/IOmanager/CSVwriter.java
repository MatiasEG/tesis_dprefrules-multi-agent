package IOmanager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import alternative.Alternative;
import criteria.Criteria;
import dataManager.DataManager;
import dataManager.Priority;
import java_ui.DSJavaUI;
import mainWindow.MainWindow;
import participant.Participant;
import prefRules.Rule;

public class CSVwriter {

	private static String criteria_example_path = "criteria_example_6.csv";
	private static String evidence_example_path = "evidence_example_6.csv";
	private static String cpref_rules_example_path = "cpref_rules_example_6.csv";
	private static String importance_example_path = "importance_orders_6.csv";
	private static String agents_priority_example_path = "agents_priority_order_example_6.csv";
	
	private static String criteria_file = DSJavaUI.getExamplesFolderRelativePath()+"/examples/"+criteria_example_path;
	private static String evidence_file = DSJavaUI.getExamplesFolderRelativePath()+"/examples/"+evidence_example_path;
	private static String cpref_rules_file = DSJavaUI.getExamplesFolderRelativePath()+"/examples/"+cpref_rules_example_path;
	private static String importance_file = DSJavaUI.getExamplesFolderRelativePath()+"/examples/"+importance_example_path;
	private static String agents_priority_file = DSJavaUI.getExamplesFolderRelativePath()+"/examples/"+agents_priority_example_path;
	
	public static void saveCriteriasToCSV(DataManager data) {
		String filePath = "";
		if(MainWindow.intoSystemFlag) {
			filePath = criteria_file;
		}else {
			filePath = data.getSaveFolder()+"\\"+data.getProjectName()+"_criteria.csv";

		}
		
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("criterion;values");
            writer.newLine();

            // write data
            for(Criteria criteria: data.getDataManagerCriteria().getCriterias()) {
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
	
	public static void saveAgentPriorityToCSV(DataManager data) {
		String filePath = "";
		if(MainWindow.intoSystemFlag) {
			filePath = agents_priority_file;
		}else {
			filePath = data.getSaveFolder()+"\\"+data.getProjectName()+"_participants_priority_order.csv";
		}
		
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("priority_order");
            writer.newLine();

            // write data
            for(Priority prior: data.getDataManagerParticipant().getParticipantsPriority()) {
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
		String filePath = "";
		if(MainWindow.intoSystemFlag) {
			filePath = evidence_file;
		}else {
			filePath = data.getSaveFolder()+"/"+data.getProjectName()+"_evidence.csv";
		}
		
		filePath = checkCSVextension(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			String header = "alternative";
			for(Criteria criteria: data.getDataManagerCriteria().getCriterias()) {
				header += ";"+criteria.getName();
			}
			
            writer.write(header);
            writer.newLine();

            // write data
            for(Alternative alt: data.getDataManagerEvidence().getAlternatives()) {
            	String values[] = alt.evidenceFileContent();
            	writer.write(values[0]);
            	
//            	for(int i=0; i<values.length; i++) {
//            		System.out.print(" - "+values[i]);
//            	}
            	
            	for(int i=1; i<values.length; i++) {
            		if(values[i].equals("-")) {
            			writer.write(";"+data.getDataManagerCriteria().getCriterias().get(i-1).getNoInformationValue());
            		}else {
            			writer.write(";"+values[i]);
            		}
            	}
            	writer.newLine();
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
		String filePath = "";
		if(MainWindow.intoSystemFlag) {
			filePath = cpref_rules_file;
		}else {
			filePath = data.getSaveFolder()+"/"+data.getProjectName()+"_rules.csv";
		}
		
		filePath = checkCSVextension(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			String header = "id;rule";
			
            writer.write(header);
            writer.newLine();

            // write data
            for(Rule rule: data.getDataManagerRule().getRules()) {
            	writer.write(rule.toString());
            	writer.newLine();
            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void saveRulePriorityToCSV(DataManager data) {
		String filePath = "";
		if(MainWindow.intoSystemFlag) {
			filePath = importance_file;
		}else {
			filePath = data.getSaveFolder()+"/"+data.getProjectName()+"_importance_orders.csv";
		}
		
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
