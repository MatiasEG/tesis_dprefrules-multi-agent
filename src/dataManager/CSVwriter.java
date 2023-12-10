package dataManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import criteria.Criteria;
import evidence.Alternative;
import evidence.ParticipantsPriority;

public class CSVwriter {

	public static void saveCriteriaTableToCSV(JTable table, String filePath) {
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // obtain data model
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            writer.write("criterion;values");
            writer.newLine();

            // write data
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object cellData = model.getValueAt(row, col);
                    
                    writer.write(cellData.toString());
                    
                    if (col < model.getColumnCount() - 1) {
                        writer.write(";");
                    }
                }
                writer.newLine();
            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void saveAgentPriorityToCSV(String filePath, DataManager data) {
		filePath = checkCSVextension(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("priority_order");
            writer.newLine();

            // write data
            for(ParticipantsPriority pprior: data.getParticipantsPriority()) {
            	writer.write(pprior.getPriorityFormatted());
            	writer.newLine();
            }

            System.out.println("Archivo CSV creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void saveEvidenceToCSV(String filePath, DataManager data) {
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
