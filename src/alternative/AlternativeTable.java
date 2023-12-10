package alternative;

import java.awt.EventQueue;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import criteria.Criteria;
import dataManager.DataManager;
import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
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
					AlternativeTable frame = new AlternativeTable(null);
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
	public AlternativeTable(DataManager data) {		
		setTitle("Datos del problema - evidencia");
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
		
		
		List<Criteria> criterias = data.getCriterias();
		String[] criteriaNames = new String[criterias.size()];
		for(int i=0; i<criterias.size(); i++) {
			criteriaNames[i] = criterias.get(i).getName();
		}
		
		String[] columnNames = new String[criteriaNames.length + 1];
		columnNames[0] = "Alternativas";
		System.arraycopy(criteriaNames, 0, columnNames, 1, criteriaNames.length);
		
		String[] noInfo = new String[criteriaNames.length];
		Arrays.fill(noInfo, "-");
		for(String s: noInfo) {
			System.out.println(s);
		}
		
		Object[][] tableData = new Object[0][0];
		
		table = new JTable(new DefaultTableModel(tableData, columnNames));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for(int i=0; i<data.getAlternatives().size(); i++) {
			//model.addRow(new Object[] {data.getAlternatives().get(i).getName(), Arrays.copyOf(noInfo, noInfo.length)});
			model.addRow(new Object[] {data.getAlternatives().get(i).getName()});
			for (int j = 1; j <= criteriaNames.length; j++) {
		        model.setValueAt("-", i, j);
		    }
		}
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
		
		for(int i=0; i<criterias.size(); i++) {
			if(!criterias.get(i).isNumeric()) {
				TableColumn comboBoxColumn = table.getColumnModel().getColumn(1);
	            comboBoxColumn.setCellEditor(new DefaultCellEditor(criterias.get(i).getComboValues()));
			}
		}
		table.repaint();
	}

}
