package alternative;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import dataManager.CriteriaManager;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class AlternativeTable extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlternativeTable frame = new AlternativeTable();
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
	public AlternativeTable() {		
		setTitle("Datos del problema - evidencia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnDeleteAlternative = new JButton("Eliminar alternativa");
		panel.add(btnDeleteAlternative, BorderLayout.WEST);
		
		JButton btnCancel = new JButton("Cancelar y descartar");
		panel.add(btnCancel, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewAlternative = new JButton("Nueva alternativa");
		panel_1.add(btnNewAlternative, BorderLayout.WEST);
		
		JButton btnAcept = new JButton("Aceptar y guardar");
		panel_1.add(btnAcept, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("A continuacion puede ver una tabla con los datos propuestos hasta ahora.");
		panel_2.add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("Si lo desea puede modificarla haciendo click directamente en los casilleros de la misma.");
		panel_2.add(lblNewLabel_1, BorderLayout.SOUTH);
		
		String[] criterias = criteriaManager.getCriteriasNames();
		String[] columnNames = new String[criterias.length + 1];
		columnNames[0] = "Alternativas";
		System.arraycopy(criterias, 0, columnNames, 1, criterias.length);
		
		Object[][] data = {
				//{"ExampleName", "WorstValue, MediumValue, BestValue"}
		};
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		table = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane.setViewportView(table);
		
		String[][] options = new String[criterias.length][];
		for(int i = 0; i < criterias.length; i++) {
			options[i] = criteriaManager.getCriteriaValuesArray(criterias[i]);
		}
		
		
		for(int i=1; i<criterias.length; i++) {
			
		}
		
		JComboBox<String> combo = new JComboBox<String>();
		
	}

}
