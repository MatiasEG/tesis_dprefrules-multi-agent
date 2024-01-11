package IOmanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;
import dataManager.DataValidations;
import errors.SintacticStringError;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NameAndFolderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFolderName;

	private DataManager data;
	private String folderPath;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NameAndFolderFrame frame = new NameAndFolderFrame(null);
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
	public NameAndFolderFrame(DataManager data) {
		this.data = data;
		
		setTitle("Nombre y Carpeta destino");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Ingrese un nombre para el problema.");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		textFieldFolderName = new JTextField();
		contentPane.add(textFieldFolderName);
		textFieldFolderName.setColumns(10);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione la carpeta donde se guardaran los archivos asociados al problema.");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JButton btnSelectFolder = new JButton("Seleccionar carpeta");
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				folderPath = FileChooser.showFolderChooser();
			}
		});
		btnSelectFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSelectFolder);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateInput()) {
					NameAndFolderFrame.this.dispose();
				}
			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnNewButton);
	}
	
	private boolean validateInput() {
		if(FileChooser.isValidFolder(folderPath)) {
			SintacticStringError error = DataValidations.validateStringWithOnlyLettersAndNumbers(textFieldFolderName.getText());
			if(error == null){
				NameAndFolderFrame.this.data.setSaveFolder(folderPath);
				NameAndFolderFrame.this.data.setProjectName(textFieldFolderName.getText());
				return true;
			}else {
				JOptionPane.showMessageDialog(null, error.getMsg(), "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null, "La ruta seleccionada no es una ruta valida a una carpeta del sistema.", "Advertencia", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}

}
