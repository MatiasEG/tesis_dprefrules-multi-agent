package dataManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IOManager {

	public static void saveTableToCSV(JTable table, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Obtener el modelo de la tabla
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            writer.write("criterion;values");
            writer.newLine();

            // Escribir datos
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
}
