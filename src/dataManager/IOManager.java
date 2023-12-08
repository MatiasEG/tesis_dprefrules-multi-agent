package dataManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import evidence.ParticipantsPriority;

public class IOManager {

	public static void saveCriteriaTableToCSV(JTable table, String filePath) {
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
}
