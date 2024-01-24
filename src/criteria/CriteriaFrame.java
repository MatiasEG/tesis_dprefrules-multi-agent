package criteria;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import IOmanager.CSVreader;
import IOmanager.CSVwriter;
import IOmanager.FileChooser;
import alternative.Alternative;
import dataManager.DataManager;
import exceptions.CriteriaFileException;
import participant.Participant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class CriteriaFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JPanel panelLabels_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JPanel panelButtons_1;
	private JButton btnSaveFile;
	private JButton btnAddCriteria;
	private JButton btnDeleteCriteria;
	private JButton btnEditCriteria;
	private JButton btnLoadFile;
	private JScrollPane scrollPaneCriteria;
	
	private DataManager data;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataManager data = new DataManager("ruleTest","C:\\Users\\Matia\\Desktop\\Archivos");
					data.addParticipant(new Participant("Matias"));
					
					Criteria days = new Criteria("days", new String[]{"1","30"}, true);
					Criteria entrmnt = new Criteria("entrmnt", new String[]{"vbad","bad","reg","good","vgood"}, false);
					Criteria service = new Criteria("service", new String[]{"vbad","bad","reg","good","vgood"}, false);
					data.addCriteria(days);
					data.addCriteria(entrmnt);
					data.addCriteria(service);
					
					CriteriaFrame frame = new CriteriaFrame(data, false);
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
	public CriteriaFrame(DataManager data, boolean onlyView) {
		this.data = data;
		
		setTitle("Criterios a evaluar");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 400);
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
		FlowLayout fl_panelButtons_1 = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panelButtons_1.setLayout(fl_panelButtons_1);
		
		btnLoadFile = new JButton("Cargar archivo");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readCriteriasCSV(path, CriteriaFrame.this.data);
					CriteriaFrame.this.checkData(CriteriaFrame.this.data);
				} catch (CriteriaFileException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		panelButtons_1.add(btnLoadFile);
		
		btnAddCriteria = new JButton("Agregar nuevo criterio");
		panelButtons_1.add(btnAddCriteria);
		btnAddCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaCreationFrame frame = new CriteriaCreationFrame(CriteriaFrame.this, CriteriaFrame.this.data, null);
				frame.setVisible(true);
			}
		});
		
		btnDeleteCriteria = new JButton("Eliminar criterio seleccionado");
		panelButtons_1.add(btnDeleteCriteria);
		btnDeleteCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// check for selected row first
				if(table.getSelectedRow() != -1) {
					// remove selected row from the model
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String criteriaName = (String) model.getValueAt(table.getSelectedRow(), 0);
					int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar el criterio seleccionado ("+criteriaName+")?", "Confirmación", JOptionPane.YES_NO_OPTION);
					
					if (option == JOptionPane.YES_OPTION) {
						// user want to delete selected criteria
						int index = table.getSelectedRow();
						model.removeRow(index);
						CriteriaFrame.this.data.removeCriteria(criteriaName);
						for(Alternative alt : CriteriaFrame.this.data.getAlternatives()) {
							alt.removeCriteria(criteriaName);
						}
						CriteriaFrame.this.data.removeRules(criteriaName);
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
		
		btnEditCriteria = new JButton("Editar criterio seleccionado");
		panelButtons_1.add(btnEditCriteria);
		btnEditCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String criteriaName = (String) model.getValueAt(table.getSelectedRow(), 0);
				
				Criteria criteriaSelected = null;
				for(Criteria auxCriteria: CriteriaFrame.this.data.getCriterias()) {
					if(auxCriteria.getName().equals(criteriaName)) {
						criteriaSelected = auxCriteria;
						break;
					}
				}
				
				CriteriaCreationFrame frame = new CriteriaCreationFrame(CriteriaFrame.this, CriteriaFrame.this.data, criteriaSelected);
				frame.setVisible(true);
			}
		});
		
		btnSaveFile = new JButton("Guardar archivo");
		panelButtons_1.add(btnSaveFile);
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSVwriter.saveCriteriasToCSV(CriteriaFrame.this.data);
				JOptionPane.showMessageDialog(null, "Criterios guardadas correctamente, ya puede cerrar esta ventana.","Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
				onlyViewMod(true);
			}
		});
		
		scrollPaneCriteria = new JScrollPane();
		contentPane.add(scrollPaneCriteria, BorderLayout.CENTER);
		
		String[] columnNames = {"Criterio", "Rango de valores"};
		Object[][] tableData = {};
		
		table = new JTable(new DefaultTableModel(tableData, columnNames));
		// disable cell edition
		table.setDefaultEditor(Object.class, null);
        // one row selected at the same time
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneCriteria.setViewportView(table);
		checkData(data);
		onlyViewMod(onlyView);
	}
	
	private void onlyViewMod(boolean onlyView) {
		if(onlyView) {
			btnSaveFile.setEnabled(false);
			btnLoadFile.setEnabled(false);
			btnAddCriteria.setEnabled(false);
			btnDeleteCriteria.setEnabled(false);
			btnEditCriteria.setEnabled(false);
		}
	}
	
	private void checkData(DataManager data) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		table.repaint();
		for(Criteria cr: data.getCriterias()) {
			if(cr.isNumeric) {
				String[] numericValues = cr.getValues();
				model.addRow(new Object[] {cr.getName(), "between("+numericValues[0]+","+numericValues[1]+")"});
			}else {
				model.addRow(new Object[] {cr.getName(), "["+cr.getCriteriaValuesString()+"]"});
			}
			
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
			Criteria criteria = new Criteria(criteriaName, splittedValues, isNumeric);
			data.addCriteria(criteria);
			for(Alternative alt: data.getAlternatives()) {
				alt.updateOrAddCriteriaValue(criteria, "-");
			}
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
			Criteria criteria = new Criteria(criteriaName, splittedValues, isNumeric);
			data.addCriteria(criteria);
			for(Alternative alt: data.getAlternatives()) {
				alt.updateOrAddCriteriaValue(criteria, "-");
			}
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
