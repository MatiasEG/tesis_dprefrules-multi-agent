package IOmanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NameAndFolderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldProjectName;
	private JLabel lblFolderPath;
	private JButton btnSaveData;
	private JButton btnSelectFolder;
	
	private DataManager data;
	private String folderPath;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NameAndFolderFrame frame = new NameAndFolderFrame(new DataManager("",""), false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NameAndFolderFrame(DataManager data, boolean viewOnly) {
		this.data = data;
		
		setTitle("Nombre y Carpeta destino");
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Ingrese un nombre que caracterice al problema");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Por favor no utilice espacios");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		textFieldProjectName = new JTextField();
		textFieldProjectName.setText(data.getProjectName());
		contentPane.add(textFieldProjectName);
		textFieldProjectName.setColumns(10);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione la carpeta donde se guardaran los archivos asociados al problema");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		btnSelectFolder = new JButton("Seleccionar carpeta");
		btnSelectFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSelectFolder);
		
		lblFolderPath = new JLabel("Ruta actual:"+data.getSaveFolder());
		lblFolderPath.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblFolderPath);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		btnSaveData = new JButton("Guardar");
		btnSaveData.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSaveData);
		
		Dimension textFieldDimensions = new Dimension(200, 20);
		textFieldProjectName.setPreferredSize(textFieldDimensions);
		textFieldProjectName.setMaximumSize(textFieldDimensions);
		
		actionListeners();
		viewOnlyMod(viewOnly);
	}
	
	private void actionListeners() {
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				folderPath = FileChooser.showFolderChooser();
				lblFolderPath.setText("Ruta actual: "+folderPath);
			}
		});
		btnSaveData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateInput()) {
					JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
					NameAndFolderFrame.this.data.setDataValidated();
					viewOnlyMod(true);
				}
			}
		});
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		if(viewOnly) {
			textFieldProjectName.setText(data.getProjectName());
			textFieldProjectName.setEnabled(false);
			
			btnSelectFolder.setEnabled(false);
			lblFolderPath.setText("Ruta actual:"+data.getSaveFolder());
			btnSaveData.setEnabled(false);
		}
	}
	
	private boolean validateInput() {
		if(FileChooser.isValidFolder(folderPath)) {
			String error = DataManager.validateStringWithOnlyLettersAndNumbers(textFieldProjectName.getText());
			if(error == null){
				NameAndFolderFrame.this.data.setSaveFolder(folderPath);
				NameAndFolderFrame.this.data.setProjectName(textFieldProjectName.getText());
				return true;
			}else {
				JOptionPane.showMessageDialog(null, error, "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null, "La ruta seleccionada no es una ruta valida a una carpeta del sistema.", "Advertencia", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}

}
