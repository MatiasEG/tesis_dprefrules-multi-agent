package mainWindow;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alternative.AlternativeTable;
import criteria.Criteria;
import criteria.CriteriaTable;
import dataManager.CSVreader;
import dataManager.DataManager;
import errors.CriteriaFileError;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Component;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private DataManager data;
	List<Criteria> criterias;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		data = new DataManager();
		String csvFile = "C:/Users/Matia/Desktop/tesis_dprefrules-multi-agent/src/files/criteria.csv";  // .CSV path to file
		criterias = new ArrayList<Criteria>();
		try {
			criterias = CSVreader.reacCriteriasCSV(csvFile, data);
			
			//for (Criteria criteria : data.getCriterias()) {
			//    System.out.println("Nombre: " + criteria.getName());
			//    System.out.println("Valores: " + String.join(", ", criteria.getValues()));
			//    System.out.println("Numeric: " + criteria.isNumeric());
			//    System.out.println("-------------");
			//}
			
		} catch (CriteriaFileError e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 654);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnCriteria = new JButton("Establecer criterios");
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaTable criteriaTable = new CriteriaTable(data);
				criteriaTable.setVisible(true);
				criteriaTable.checkData(criterias);
			}
		});
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panelUsers = new JPanel();
		contentPane.add(panelUsers);
		panelUsers.setLayout(new BoxLayout(panelUsers, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Personas que participan del problema de eleccion:");
		panelUsers.add(lblNewLabel);
		
		JList list = new JList();
		panelUsers.add(list);
		
		JButton btnAddUser = new JButton("New button");
		panelUsers.add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("New button");
		panelUsers.add(btnDeleteUser);
		contentPane.add(btnCriteria);
		
		JButton btnEvidence = new JButton("Definir evidencia");
		btnEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlternativeTable alternativeTable = new AlternativeTable(data);
				alternativeTable.setVisible(true);
			}
		});
		contentPane.add(btnEvidence);
	}

}
