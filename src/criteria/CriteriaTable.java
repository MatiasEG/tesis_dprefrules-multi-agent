package criteria;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dataManager.DataManager;
import dataManager.IOManager;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CriteriaTable extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JPanel panelLabels_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JPanel panelButtons_1;
	private JButton btnAcept;
	private JButton btnAddCriteria;
	private JButton btnCancel;
	private JPanel panelButtons_2;
	private JButton btnDeleteCriteria;
	private JScrollPane scrollPaneCriteria;
	
	private JButton btnEditCriteria;
	private DataManager data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriteriaTable frame = new CriteriaTable(null);
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
	public CriteriaTable(DataManager data) {
		this.data = data;
		
		setTitle("Criterios a evaluar");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(10, 10));
		
		JPanel panelLabels_1 = new JPanel();
		panelLabels_1.setPreferredSize(new Dimension(100,100));
		contentPane.add(panelLabels_1, BorderLayout.NORTH);
		panelLabels_1.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel_1 = new JLabel("A continuacion ingrese o modifique los criterios que desea utilizar.");
		panelLabels_1.add(lblNewLabel_1, BorderLayout.NORTH);
		
		lblNewLabel_2 = new JLabel("Recuerde que:");
		panelLabels_1.add(lblNewLabel_2, BorderLayout.CENTER);
		
		panelLabels_2 = new JPanel();
		panelLabels_2.setPreferredSize(new Dimension(50,50));
		panelLabels_1.add(panelLabels_2, BorderLayout.SOUTH);
		panelLabels_2.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel(" > Los criterios no pueden repetirse");
		panelLabels_2.add(lblNewLabel, BorderLayout.NORTH);
		
		lblNewLabel_3 = new JLabel(" > Los valores posibles para los criterios deben estar separados por coma");
		panelLabels_2.add(lblNewLabel_3, BorderLayout.CENTER);
		
		lblNewLabel_4 = new JLabel(" > El valor de mas a la izquierda sera considerado el peor valor posible, mientras que el de mas a la derecha el mejor");
		panelLabels_2.add(lblNewLabel_4, BorderLayout.SOUTH);
		
		panelButtons_1 = new JPanel();
		contentPane.add(panelButtons_1, BorderLayout.SOUTH);
		panelButtons_1.setPreferredSize(new Dimension(50,50));
		panelButtons_1.setLayout(new BorderLayout(0, 0));
		
		btnAcept = new JButton("Aceptar");
		btnAcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO deberia consultar el proyect path
				
				// project path
		        String projectPath = System.getProperty("user.dir");
		        // complete path
		        String filePath = projectPath + "\\src\\files\\criteria.csv";
		        
				IOManager.saveTableToCSV(table, filePath);
			}
		});
		panelButtons_1.add(btnAcept, BorderLayout.WEST);
		
		btnCancel = new JButton("Cancelar");
		panelButtons_1.add(btnCancel, BorderLayout.EAST);
		
		panelButtons_2 = new JPanel();
		panelButtons_1.add(panelButtons_2, BorderLayout.CENTER);
		panelButtons_2.setLayout(new BorderLayout(0, 0));
		
		btnAddCriteria = new JButton("Agregar nuevo criterio");
		btnAddCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewCriteria frame = new NewCriteria(CriteriaTable.this, data, null);
				frame.setVisible(true);
				//DefaultTableModel model = (DefaultTableModel) table.getModel();
				//model.addRow(new Object[] {});
			}
		});
		panelButtons_2.add(btnAddCriteria, BorderLayout.WEST);
		
		btnDeleteCriteria = new JButton("Eliminar criterio seleccionado");
		btnDeleteCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// check for selected row first
				if(table.getSelectedRow() != -1) {
					// remove selected row from the model
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String criteriaName = (String) model.getDataVector().elementAt(table.getSelectedRow()).elementAt(0);
					int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar el criterio seleccionado ("+criteriaName+")?", "Confirmación", JOptionPane.YES_NO_OPTION);
					
					if (option == JOptionPane.YES_OPTION) {
						// user want to delete selected criteria
						model.removeRow(table.getSelectedRow());
						JOptionPane.showMessageDialog(null, "El criterio seleccionado fue correctamente removido");
					}else {
						// user do not want to delete selected criteria
					}
				}else {
					// no criteria selected
					JOptionPane.showMessageDialog(null, "Por favor, selecciona un criterio para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panelButtons_2.add(btnDeleteCriteria, BorderLayout.EAST);
		
		btnEditCriteria = new JButton("Editar criterio seleccionado");
		btnEditCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String criteriaName = (String) model.getDataVector().elementAt(table.getSelectedRow()).elementAt(0);
				
				Criteria criteriaSelected = null;
				for(Criteria auxCriteria: data.getCriterias()) {
					if(auxCriteria.getName().equals(criteriaName)) {
						criteriaSelected = auxCriteria;
						break;
					}
				}
				
				NewCriteria frame = new NewCriteria(CriteriaTable.this, data, criteriaSelected);//TODO actualizar criteriaSelected
				frame.setVisible(true);
			}
		});
		panelButtons_2.add(btnEditCriteria, BorderLayout.CENTER);
		
		scrollPaneCriteria = new JScrollPane();
		contentPane.add(scrollPaneCriteria, BorderLayout.CENTER);
		
		String[] columnNames = {"Criterio", "Rango de valores"};
		Object[][] tableData = {
				//{"ExampleName", "[WorstValue, MediumValue, BestValue]"}
		};
		
		table = new JTable(new DefaultTableModel(tableData, columnNames));
		// Deshabilitar la edición de celdas
		table.setDefaultEditor(Object.class, null);
        // Configurar para que solo se pueda seleccionar una fila a la vez
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneCriteria.setViewportView(table);
		
	}
	
	public void checkData(List<Criteria> criterias) {
		for(Criteria cr: criterias) {
			addCriteria(cr.name, String.join(", ",cr.getValues()), cr.isNumeric, null);
		}
	}
	
	public void addCriteria(String criteriaName, String rangeValue, boolean isNumeric, Criteria criteriaUpdate) {
		if(isNumeric) {
			addNumericCriteria(criteriaName, rangeValue, isNumeric, criteriaUpdate);
		}else {
			addSimbolicCriteria(criteriaName, rangeValue, isNumeric, criteriaUpdate);
		}
	}
	
	private void addNumericCriteria(String criteriaName, String rangeValue, boolean isNumeric, Criteria criteriaUpdate) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] splittedValues = rangeValue.trim().split("\\s*,\\s*");
		
		if(criteriaUpdate==null) {
			model.addRow(new Object[] {criteriaName, "between("+splittedValues[0]+","+splittedValues[1]+")"});
			data.addCriteria(new Criteria(criteriaName, splittedValues, isNumeric));
		}else {
			int rowIndex = findRow(table, criteriaUpdate);
	        if (rowIndex != -1) {
	        	// modify existent values on table row
	            model.setValueAt(criteriaName, rowIndex, 0);
	            model.setValueAt("between("+splittedValues[0]+","+splittedValues[1]+")", rowIndex, 1);
	            // update the original criteria
	            criteriaUpdate.updateCriteria(criteriaName, splittedValues, isNumeric);
	        }
		}
	}

	private void addSimbolicCriteria(String criteriaName, String rangeValue, boolean isNumeric, Criteria criteriaUpdate) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] splittedValues = rangeValue.trim().split("\\s*,\\s*");
		String valueFormatted = "[";
		for(String myStr: splittedValues) {
			valueFormatted += myStr+",";
	    }
		valueFormatted = valueFormatted.substring(0, valueFormatted.length()-1) + "]";
		
		
		if(criteriaUpdate==null) {
			model.addRow(new Object[] {criteriaName, valueFormatted});
			data.addCriteria(new Criteria(criteriaName, splittedValues, isNumeric));
		}else {
			int rowIndex = findRow(table, criteriaUpdate);
	        if (rowIndex != -1) {
	            // modify existent values on table row
	            model.setValueAt(criteriaName, rowIndex, 0);
	            model.setValueAt(valueFormatted, rowIndex, 1);
	            // update the original criteria
	            criteriaUpdate.updateCriteria(criteriaName, splittedValues, isNumeric);
	        }
		}
	}
	
	// function to find a row index on a table by name  
	private int findRow(JTable table, Criteria criteria) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    for (int i = 0; i < model.getRowCount(); i++) {
	        String criteriaName = (String) model.getValueAt(i, 0);
	        if (criteriaName.equals(criteria.getName())) {
	            return i;
	        }
	    }
	    return -1; // row no founded
	}
	
}
