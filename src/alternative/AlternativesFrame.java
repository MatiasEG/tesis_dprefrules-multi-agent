package alternative;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import IOmanager.CSVreader;
import IOmanager.FileChooser;
import criteria.Criteria;
import dataManager.DataManager;
import exceptions.EvidenceFileException;
import participant.Participant;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.Box;

public class AlternativesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnSave;
	private JButton btnDeleteAlternative;
	private JButton btnLoadFile;
	private JButton btnNewAlternative;
	
	private DataManager data;
	private Component verticalStrut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataManager data = new DataManager("evidenceTest","C:\\Users\\Matia\\Desktop\\Archivos");
					data.getDataManagerParticipant().addParticipant(new Participant("Matias"));
					
					Criteria days = new Criteria("days", new String[]{"1","30"}, true);
					Criteria entrmnt = new Criteria("entrmnt", new String[]{"vbad","bad","reg","good","vgood"}, false);
					Criteria service = new Criteria("service", new String[]{"vbad","bad","reg","good","vgood"}, false);
					data.getDataManagerCriteria().addCriteria(days);
					data.getDataManagerCriteria().addCriteria(entrmnt);
					data.getDataManagerCriteria().addCriteria(service);
					
					AlternativesFrame frame = new AlternativesFrame(data, false);
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
	public AlternativesFrame(DataManager data, boolean viewOnly) {
		this.data = data;
		
		setTitle("Datos del problema - evidencia");
		setBounds(100, 100, 760, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("- A continuacion puede ver una tabla con los datos propuestos hasta ahora -");
		contentPane.add(lblNewLabel);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblNewLabel_1 = new JLabel("- Puede modificar la evidencia haciendo click directamente en los casilleros -");
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblNewLabel_2 = new JLabel("- Recuerde que los nombres de las alternativas no puede contener espacios ni caracteres especiales -");
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		List<Criteria> criterias = data.getDataManagerCriteria().getCriterias();
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
		model = (DefaultTableModel) table.getModel();
		
		for(int i=0; i<data.getDataManagerEvidence().getAlternatives().size(); i++) {
			model.addRow(new Object[] {data.getDataManagerEvidence().getAlternatives().get(i).getName()});
			for (int j = 1; j <= criteriaNames.length; j++) {
		        model.setValueAt("-", i, j);
		    }
		}
		
		verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnLoadFile = new JButton("Cargar archivo");
		panel.add(btnLoadFile);
		
		btnNewAlternative = new JButton("Nueva alternativa");
		panel.add(btnNewAlternative);
		
		btnDeleteAlternative = new JButton("Eliminar alternativa");
		panel.add(btnDeleteAlternative);
		
		btnSave = new JButton("Guardar cambios");
		panel.add(btnSave);
		
		for(int i=0; i<criterias.size(); i++) {
			if(!criterias.get(i).isNumeric()) {
				TableColumn comboBoxColumn = table.getColumnModel().getColumn(i+1);
	            comboBoxColumn.setCellEditor(new DefaultCellEditor(criterias.get(i).getComboValues()));
			}
		}
		
		table.repaint();
		checkData(data);
		actionListeners();
		viewOnlyMod(viewOnly);
	}

	private void actionListeners() {
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readEvidenceCSV(path, AlternativesFrame.this.data);
				} catch (EvidenceFileException e1) {
					JOptionPane.showMessageDialog(null, "Error. "+e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
				checkData(AlternativesFrame.this.data);
			}
		});
		btnNewAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAlternative(AlternativesFrame.this.data);
			}
		});
		btnDeleteAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = table.getSelectedRow();
                 if (selectedRow != -1) {
                	 DefaultTableModel model = (DefaultTableModel) table.getModel();
                	 String alternativeName = (String) model.getValueAt(table.getSelectedRow(), 0);
                	 int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar la alternativa seleccionada ("+alternativeName+")?", "Confirmación", JOptionPane.YES_NO_OPTION);
                	 
                	 if (option == JOptionPane.YES_OPTION) {
                		 // user want to delete selected alternative
                		 model.removeRow(selectedRow);
                		 AlternativesFrame.this.data.getDataManagerEvidence().removeAlternative(alternativeName);
                		 JOptionPane.showMessageDialog(null, "La alternativa seleccionada fue correctamente removido");
                	 }else {
                		 // user do not want to delete selected alternative
                	 }
                 } else {
                     JOptionPane.showMessageDialog(null, "Por favor, selecciona una fila para eliminar.",
                             "Sin selección", JOptionPane.INFORMATION_MESSAGE);
                 }
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.isEditing()) {
		            table.getCellEditor().stopCellEditing();
		        }
				
				if(table.getRowCount()>1) {
					if(validateEvidence(AlternativesFrame.this.data)) {
						JOptionPane.showMessageDialog(null, "Alternativas guardadas correctamente, ya puede cerrar esta ventana.","Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
						AlternativesFrame.this.data.setDataValidated();
						viewOnlyMod(true);
					}
				}else {
                	JOptionPane.showMessageDialog(null, "Debe definir al menos dos alternativas para poder realizar una comparacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		if(viewOnly) {
			btnSave.setEnabled(false);
			btnDeleteAlternative.setEnabled(false);
			btnLoadFile.setEnabled(false);
			btnNewAlternative.setEnabled(false);
			
			table.setEnabled(false);
		}
	}
	
	private void addAlternative(DataManager data) {
		String nameAux = JOptionPane.showInputDialog(this, "Ingrese el nombre de la alternativa que desea agregar:");
		String name = nameAux.toLowerCase();
        String validation = DataManager.validateStringWithOnlyLetters(name);
        if(validation==null) {
        	if(DataManager.validateStringListNotContainNewElement(data.getDataManagerEvidence().getAlternativesNames(), name)) {
        		DefaultTableModel model = (DefaultTableModel) table.getModel();
        		model.addRow(new Object[] {name});
    			for (int j = 1; j < model.getColumnCount(); j++) {
    		        model.setValueAt("-", model.getRowCount()-1, j);
    		    }
    			data.getDataManagerEvidence().addAlternative(new Alternative(name));
    		}else {
    			JOptionPane.showMessageDialog(null, "Error, la alternativa \""+name+"\" ya se encuentra en la lista de alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    		}
        }else {
        	JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	private boolean validateEvidence(DataManager data) {
		String value;
		for (int row = 0; row < model.getRowCount(); row++) {
		    for (int col = 1; col < model.getColumnCount(); col++) {
		    	value = (String) model.getValueAt(row, col);
		    	if(data.getDataManagerCriteria().getCriterias().get(col-1).valueIsValid(value)) {
		    		data.getDataManagerEvidence().getAlternatives().get(row).updateOrAddCriteriaValue(data.getDataManagerCriteria().getCriterias().get(col-1), value);
		    	}else {
		    		JOptionPane.showMessageDialog(null, "Error, ingreso un valor no valido para la alternativa ("+data.getDataManagerEvidence().getAlternatives().get(row).getName()+") en el criterio ("+data.getDataManagerCriteria().getCriterias().get(col-1).getName()+")", "Advertencia", JOptionPane.WARNING_MESSAGE);
		    		data.getDataManagerEvidence().getAlternatives().clear();
		    		return false;
		    	}
		    }
		}
		return true;
	}
	
	private void checkData(DataManager data) {
		model.setRowCount(0);
		for(int i=0; i<data.getDataManagerCriteria().getCriterias().size(); i++) {
			if(!data.getDataManagerCriteria().getCriterias().get(i).isNumeric()) {
				TableColumn comboBoxColumn = table.getColumnModel().getColumn(i+1);
	            comboBoxColumn.setCellEditor(new DefaultCellEditor(data.getDataManagerCriteria().getCriterias().get(i).getComboValues()));
			}
		}
		for (Alternative alt : data.getDataManagerEvidence().getAlternatives()) {
	        Object[] rowData = new Object[model.getColumnCount()];
	        rowData[0] = alt.getName();
	        for(int col=1; col<model.getColumnCount(); col++) {
	            rowData[col] = alt.getValues().get(col-1);
	        }
	        model.addRow(rowData);
	    }
		table.repaint();
	}
}
