package alternative;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import IOmanager.CSVreader;
import IOmanager.CSVwriter;
import IOmanager.FileChooser;
import criteria.Criteria;
import dataManager.DataManager;
import dataManager.StringValidations;
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

public class AlternativesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnAcept;
	private JButton btnDeleteAlternative;
	private JButton btnLoadFile;
	private JButton btnNewAlternative;
	
	private DataManager data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataManager data = new DataManager("evidenceTest","C:\\Users\\Matia\\Desktop\\Archivos");
					data.addParticipant(new Participant("Matias"));
					
					//Criteria entretenimiento = new Criteria("Entretenimiento", new String[]{"pesimo", "malo", "bueno", "exelente"}, false);
					//Criteria clima = new Criteria("Clima", new String[]{"pesimo", "malo", "bueno", "exelente"}, false);
					//Criteria costo = new Criteria("Costo", new String[]{"caro", "medio", "normal", "economico"}, false);
					//Criteria dias = new Criteria("Dias", new String[]{"1", "30"}, true);
					//data.addCriteria(entretenimiento);
					//data.addCriteria(clima);
					//data.addCriteria(costo);
					//data.addCriteria(dias);
					
					Criteria days = new Criteria("days", new String[]{"1","30"}, true);
					Criteria entrmnt = new Criteria("entrmnt", new String[]{"vbad","bad","reg","good","vgood"}, false);
					Criteria service = new Criteria("service", new String[]{"vbad","bad","reg","good","vgood"}, false);
					data.addCriteria(days);
					data.addCriteria(entrmnt);
					data.addCriteria(service);
					
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
	public AlternativesFrame(DataManager data, boolean onlyView) {
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
		
		JLabel lblNewLabel_1 = new JLabel("- Si lo desea puede modificarla haciendo click directamente en los casilleros de la misma -");
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblNewLabel_2 = new JLabel("- Recuerde que el nombre no puede contener espacios ni caracteres especiales -");
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
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
		model = (DefaultTableModel) table.getModel();
		
		for(int i=0; i<data.getAlternatives().size(); i++) {
			//model.addRow(new Object[] {data.getAlternatives().get(i).getName(), Arrays.copyOf(noInfo, noInfo.length)});
			model.addRow(new Object[] {data.getAlternatives().get(i).getName()});
			for (int j = 1; j <= criteriaNames.length; j++) {
		        model.setValueAt("-", i, j);
		    }
		}
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnLoadFile = new JButton("Cargar archivo");
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
		panel.add(btnLoadFile);
		
		btnNewAlternative = new JButton("Nueva alternativa");
		btnNewAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAlternative(AlternativesFrame.this.data);
			}
		});
		panel.add(btnNewAlternative);
		
		btnDeleteAlternative = new JButton("Eliminar alternativa");
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
                		 AlternativesFrame.this.data.removeAlternative(alternativeName);
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
		panel.add(btnDeleteAlternative);
		
		btnAcept = new JButton("Guardar archivo");
		btnAcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.isEditing()) {
		            table.getCellEditor().stopCellEditing();
		        }
				
				if(table.getRowCount()>1) {
					if(validateEvidence(AlternativesFrame.this.data)) {
						CSVwriter.saveEvidenceToCSV(AlternativesFrame.this.data);
						JOptionPane.showMessageDialog(null, "Alternativas guardadas correctamente, ya puede cerrar esta ventana.","Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
						onlyViewMod(true);
					}
				}else {
                	JOptionPane.showMessageDialog(null, "Debe definir al menos dos alternativas para poder realizar una comparacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panel.add(btnAcept);
		
		for(int i=0; i<criterias.size(); i++) {
			if(!criterias.get(i).isNumeric()) {
				TableColumn comboBoxColumn = table.getColumnModel().getColumn(i+1);
	            comboBoxColumn.setCellEditor(new DefaultCellEditor(criterias.get(i).getComboValues()));
			}
		}
		table.repaint();
		checkData(data);
		onlyViewMod(onlyView);
	}

	private void onlyViewMod(boolean onlyView) {
		if(onlyView) {
			btnAcept.setEnabled(false);
			btnDeleteAlternative.setEnabled(false);
			btnLoadFile.setEnabled(false);
			btnNewAlternative.setEnabled(false);
			
			table.setEnabled(false);
		}
	}
	
	private void addAlternative(DataManager data) {
		String name = JOptionPane.showInputDialog(this, "Ingrese el nombre de la alternativa que desea agregar:");
        String validation = StringValidations.validateStringWithOnlyLettersAndNumbers(name);
        if(validation==null) {
        	if(StringValidations.validateStringListNotContainNewElement(data.getAlternativesNames(), name)) {
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
        	JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	private boolean validateEvidence(DataManager data) {
		String value;
		for (int row = 0; row < model.getRowCount(); row++) {
		    for (int col = 1; col < model.getColumnCount(); col++) {
		    	value = (String) model.getValueAt(row, col);
		    	if(data.getCriterias().get(col-1).valueIsValid(value)) {
		    		data.getAlternatives().get(row).updateOrAddCriteriaValue(data.getCriterias().get(col-1), value);
		    	}else {
		    		JOptionPane.showMessageDialog(null, "Error, ingreso un valor no valido para la alternativa ("+data.getAlternatives().get(row).getName()+") en el criterio ("+data.getCriterias().get(col-1).getName()+")", "Advertencia", JOptionPane.WARNING_MESSAGE);
		    		data.getAlternatives().clear();
		    		return false;
		    	}
		    }
		}
		return true;
	}
	
	private void checkData(DataManager data) {
		model.setRowCount(0);
		for(int i=0; i<data.getCriterias().size(); i++) {
			if(!data.getCriterias().get(i).isNumeric()) {
				TableColumn comboBoxColumn = table.getColumnModel().getColumn(i+1);
	            comboBoxColumn.setCellEditor(new DefaultCellEditor(data.getCriterias().get(i).getComboValues()));
			}
		}
		for (Alternative alt : data.getAlternatives()) {
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
