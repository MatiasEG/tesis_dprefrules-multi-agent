package IOmanager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {

    public static String showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();

        // filter for only show .csv files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filter);
        
        // open the selection file window
        int result = fileChooser.showSaveDialog(null);

        // verify if the user select a file
        if (result == JFileChooser.APPROVE_OPTION) {
            // obtain the path and the name of the selected file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Ruta de archivo: "+filePath);
            return filePath;
        }
        
        return null;
    }
    
    public static String showFolderChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // open the selection file window
        int result = fileChooser.showDialog(null, "Seleccionar carpeta");

        // verify if the user select a folder
        if (result == JFileChooser.APPROVE_OPTION) {
            // obtain the path of the selected folder
            String carpetaPath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Carpeta seleccionada: " + carpetaPath);
            return carpetaPath;
        }

        return null;
    }
    
    public static boolean hasCSVExtension(String filePath) {
        // obtain the path from parameter
        Path path = Paths.get(filePath);

        // verify if the extension is .csv
        String fileExtension = path.getFileName().toString();
        return fileExtension.toLowerCase().endsWith(".csv");
    }
    
    public static boolean isValidFolder(String folderPath) {
    	// Obtén una instancia de la ruta utilizando Paths
        Path path = Paths.get(folderPath);

        // Verifica si la ruta es un directorio
        return Files.isDirectory(path);
    }
}

