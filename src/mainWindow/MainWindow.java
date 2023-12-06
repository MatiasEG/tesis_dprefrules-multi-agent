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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnCriteria = new JButton("Establecer criterios");
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaTable criteriaTable = new CriteriaTable(data);
				criteriaTable.setVisible(true);
				criteriaTable.checkData(criterias);
			}
		});
		contentPane.add(btnCriteria, BorderLayout.WEST);
		
		JButton btnEvidence = new JButton("Definir evidencia");
		btnEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlternativeTable alternativeTable = new AlternativeTable(data);
				alternativeTable.setVisible(true);
			}
		});
		contentPane.add(btnEvidence, BorderLayout.EAST);
	}

}
