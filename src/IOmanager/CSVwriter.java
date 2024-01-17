package IOmanager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import alternative.Alternative;
import criteria.Criteria;
import dataManager.DataManager;
import dataManager.Priority;

public class CSVwriter {

	public static void saveCriteriaTableToCSV(DataManager data) {
		String filePath = data.getSaveFolder()+"\\"+data.getProjectName()+"_criterias.csv";
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
		for(String value: criteria.getValues()) {
			values += value+",";
		}
		return values.substring(0, values.length() - 1);
	}
	
	public static void saveAgentPriorityToCSV(DataManager data) {
		String filePath = data.getSaveFolder()+"\\"+data.getProjectName()+"_participantPriority.csv";
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("priority_order");
            writer.newLine();

            // write data
            for(Priority prior: data.getParticipantsPriority()) {
            	writer.write(prior.getPriorityFormatted());
            	writer.newLine();
            }
            
            for(Priority prior: data.getParticipantsPriorityTransitive()) {
            	writer.write(prior.getPriorityFormatted());
            	writer.newLine();
            }

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
			for(Criteria criteria: data.getCriterias()) {
				header += ";"+criteria.getName();
			}
			
            writer.write(header);
            writer.newLine();

            // write data
            for(Alternative alt: data.getAlternatives()) {
            	writer.write(alt.toString());
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
}
