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
import dataManager.DataValidations;
import errors.SintacticStringError;
import evidence.Alternative;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EvidenceTable extends JFrame {

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
					EvidenceTable frame = new EvidenceTable(null);
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
	public EvidenceTable(DataManager data) {		
		setTitle("Datos del problema - evidencia");
		setBounds(100, 100, 760, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
		
		JButton btnNewAlternative = new JButton("Nueva alternativa");
		btnNewAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAlternative(data);
			}
		});
		panel.add(btnNewAlternative);
		
		JButton btnDeleteAlternative = new JButton("Eliminar alternativa");
		panel.add(btnDeleteAlternative);
		
		JButton btnAcept = new JButton("Aceptar y guardar");
		panel.add(btnAcept);
		
		JButton btnCancel = new JButton("Cancelar y descartar");
		panel.add(btnCancel);
		
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

	private void addAlternative(DataManager data) {
		String name = JOptionPane.showInputDialog(this, "Ingrese el nombre de la alternativa que desea agregar:");
        SintacticStringError validation = DataValidations.validateStringWithOnlyLettersAndNumbers(name);
        if(validation==null) {
        	if(DataValidations.validateStringListNotContainNewElement(data.getAlternativesNames(), name)) {
        		DefaultTableModel model = (DefaultTableModel) table.getModel();
        		model.addRow(new Object[] {name});
    			for (int j = 1; j < model.getColumnCount(); j++) {
    		        model.setValueAt("-", model.getRowCount()-1, j);
    		    }
    			data.addAlternative(new Alternative(name));
    		}else {
    			JOptionPane.showMessageDialog(null, "Error, la alternativa \""+name+"\" ya se encuentra en la lista de alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    		}
        }else {
        	JOptionPane.showMessageDialog(null, validation.getMsg(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
	}
}
