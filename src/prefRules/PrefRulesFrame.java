package prefRules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.CSVreader;
import IOmanager.FileChooser;
import criteria.Criteria;
import dataManager.DataManager;
import dataManager.Priority;
import exceptions.RuleFileErrorException;
import participant.Participant;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class PrefRulesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JButton btnEditRule;
	private JButton btnDeleteRule;
	private JButton btnNewRule;
	private JButton btnLoadFile;
	private JButton btnSave;
	private JButton btnEditRulePreferences;
	private JButton btnViewRulePreferences;
	private JButton btnDescriptionRule;
	
	private DefaultListModel<String> listModelRules;
	private JList<String> listRules;
	private DataManager data;
	private DataManager dataClone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataManager data = new DataManager("evidenceTest","C:\\Users\\Matia\\Desktop\\Archivos");
					data.getDataManagerParticipant().addParticipant(new Participant("melisa"));
					data.getDataManagerParticipant().addParticipant(new Participant("matias"));
					data.getDataManagerParticipant().addParticipant(new Participant("mama"));
					data.getDataManagerParticipant().addParticipant(new Participant("papa"));
					
					data.getDataManagerParticipant().addParticipantsPriority(new Priority("melisa","mama"));
					data.getDataManagerParticipant().addParticipantsPriority(new Priority("melisa","papa"));
					data.getDataManagerParticipant().addParticipantsPriority(new Priority("matias","mama"));
					data.getDataManagerParticipant().addParticipantsPriority(new Priority("matias","papa"));
					
					Criteria complejidad = new Criteria("complejidad", new String[]{"alta","media","baja"}, false);
					Criteria tiempo = new Criteria("tiempo", new String[]{"6","1"}, true);
					Criteria gradodeaceptacion = new Criteria("gradodeaceptacion", new String[]{"bajo","medio","alto"}, false);
					Criteria porciones = new Criteria("porciones", new String[]{"8","15"}, true);
					
					data.getDataManagerCriteria().addCriteria(complejidad);
					data.getDataManagerCriteria().addCriteria(tiempo);
					data.getDataManagerCriteria().addCriteria(gradodeaceptacion);
					data.getDataManagerCriteria().addCriteria(porciones);
					
					PrefRulesFrame frame = new PrefRulesFrame(data, false);
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
	public PrefRulesFrame(DataManager data, boolean viewOnly) {
		this.data = data;
		setTitle("Reglas de preferencia definidas");
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Reglas definidas");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		listModelRules = new DefaultListModel<>();
		listRules = new JList<String>(listModelRules);
		scrollPane.setViewportView(listRules);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		JPanel panelFileButtons = new JPanel();
		contentPane.add(panelFileButtons);
		panelFileButtons.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnLoadFile = new JButton("Cargar desde archivo");
		panelFileButtons.add(btnLoadFile);
		btnLoadFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnSave = new JButton("Guardar cambios");
		panelFileButtons.add(btnSave);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelButtons = new JPanel();
		contentPane.add(panelButtons);
		panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNewRule = new JButton("Nueva regla");
		panelButtons.add(btnNewRule);
		
		btnDeleteRule = new JButton("Eliminar");
		panelButtons.add(btnDeleteRule);
		
		btnEditRule = new JButton("Editar");
		panelButtons.add(btnEditRule);
		
		btnDescriptionRule = new JButton("Descripcion");
		panelButtons.add(btnDescriptionRule);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel_1 = new JLabel("A continuacion puede definir las preferencias");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("que tienen los participantes sobre las reglas");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("que comparan las alternativas en base a los criterios");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_3);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JPanel panelPreferences = new JPanel();
		contentPane.add(panelPreferences);
		panelPreferences.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnEditRulePreferences = new JButton("Definir preferencias");
		panelPreferences.add(btnEditRulePreferences);
		btnEditRulePreferences.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewRulePreferences = new JButton("Ver preferencias");
		panelPreferences.add(btnViewRulePreferences);
		
		Dimension panelDimensionsScrollPane = new Dimension(450, 200);
		scrollPane.setPreferredSize(panelDimensionsScrollPane);
		scrollPane.setMaximumSize(panelDimensionsScrollPane);
		
		Dimension panelBtnDimensions = new Dimension(450, 40);
		panelFileButtons.setPreferredSize(panelBtnDimensions);
		panelFileButtons.setMaximumSize(panelBtnDimensions);
		panelButtons.setPreferredSize(panelBtnDimensions);
		panelButtons.setMaximumSize(panelBtnDimensions);
		panelPreferences.setPreferredSize(panelBtnDimensions);
		panelPreferences.setMaximumSize(panelBtnDimensions);
		
		actionListeners();
		viewOnlyMod(viewOnly);
		updateRules(data);
	}
	
	private void actionListeners() {
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readRulesCSV(path, PrefRulesFrame.this.data);
					PrefRulesFrame.this.updateRules();
				} catch (RuleFileErrorException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnNewRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dataClone = data.clone();
				
				PrefRulesFrame.this.viewOnlyMod(true);
				PrefRuleCreationFrame frame = new PrefRuleCreationFrame(dataClone, null);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(dataClone.getDataValidated()) {
		            		data.updateData(dataClone);
		            	}else {
		            		// The user close the window without save.
		            	}
		            	dataClone = null;
		            	
		            	updateRules();
		            	PrefRulesFrame.this.viewOnlyMod(false);
		            }
		        });
			}
		});
		btnEditRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listRules.getSelectedIndex();
				Rule rule = null;
		        if (selectedIndex != -1) {
		        	String ruleName = listRules.getSelectedValue();
	            	for(int i=0; i<PrefRulesFrame.this.data.getDataManagerRule().getRules().size(); i++) {
	            		if(PrefRulesFrame.this.data.getDataManagerRule().getRules().get(i).getName().equals(ruleName)) {
	            			rule = PrefRulesFrame.this.data.getDataManagerRule().getRules().get(i);
	            			break;
	            		}
	            	}
	                updateRules();
		        } else {
		            JOptionPane.showMessageDialog(PrefRulesFrame.this, "Debe seleccionar a un regla para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				
				
				PrefRuleCreationFrame frame = new PrefRuleCreationFrame(PrefRulesFrame.this.data, rule);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	updateRules();
		            }
		        });
			}
		});
		btnDescriptionRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listRules.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	String ruleName = listRules.getSelectedValue();
	            	for(int i=0; i<PrefRulesFrame.this.data.getDataManagerRule().getRules().size(); i++) {
	            		if(PrefRulesFrame.this.data.getDataManagerRule().getRules().get(i).getName().equals(ruleName)) {
	            			JOptionPane.showMessageDialog(null, PrefRulesFrame.this.data.getDataManagerRule().getRules().get(i).getRuleDescription(), "Descripcion de la regla", JOptionPane.INFORMATION_MESSAGE);
	            			break;
	            		}
	            	}
		        } else {
		            JOptionPane.showMessageDialog(PrefRulesFrame.this, "Debe seleccionar a un regla para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnDeleteRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listRules.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	String ruleName = listRules.getSelectedValue();
		        	int option = JOptionPane.showConfirmDialog(PrefRulesFrame.this,
		                    "¿Seguro que desea eliminar la regla seleccionada ("+ruleName+")?",
		                    "Confirmar Eliminación",
		                    JOptionPane.YES_NO_OPTION);
		            if (option == JOptionPane.YES_OPTION) {
		            	PrefRulesFrame.this.data.getDataManagerRule().removeRule(ruleName);
		            	for(int i=0; i<PrefRulesFrame.this.data.getDataManagerRule().getRules().size(); i++) {
		            		if(PrefRulesFrame.this.data.getDataManagerRule().getRules().get(i).getName().equals(ruleName)) {
		            			PrefRulesFrame.this.data.getDataManagerRule().getRules().remove(i);
		            			break;
		            		}
		            	}
		                updateRules();
		            }
		        } else {
		            JOptionPane.showMessageDialog(PrefRulesFrame.this, "Debe seleccionar a un regla para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnEditRulePreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PrefRulesFrame.this.data.getDataManagerRule().getRules().size() > 1) {
					dataClone = PrefRulesFrame.this.data.clone();
					PrefRulesFrame.this.viewOnlyMod(true);
					
					PrefRulePreferencesFrame frame = new PrefRulePreferencesFrame(dataClone, false);
					frame.setVisible(true);
					
					// WindowListener for detect when the frame is closed
					frame.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			            	if(dataClone.getDataValidated()) {
			            		PrefRulesFrame.this.data.updateData(dataClone);
			            	}else {
			            		// The user close the window without save.
			            	}
			            	dataClone = null;
			            	
			            	PrefRulesFrame.this.viewOnlyMod(false);
			            }
			        });
				}else {
		            JOptionPane.showMessageDialog(PrefRulesFrame.this, "Debe crear al menos dos reglas de preferencia, para poder establecer un orden de prioridad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnViewRulePreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrefRulePreferencesFrame frame = new PrefRulePreferencesFrame(PrefRulesFrame.this.data, true);
				frame.setVisible(true);
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Alternativas guardadas correctamente, ya puede cerrar esta ventana.","Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
				PrefRulesFrame.this.data.setDataValidated();
				viewOnlyMod(true);
			}
		});
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		btnEditRule.setEnabled(!viewOnly);
		btnDeleteRule.setEnabled(!viewOnly);
		btnNewRule.setEnabled(!viewOnly);
		btnLoadFile.setEnabled(!viewOnly);
		btnSave.setEnabled(!viewOnly);
		btnEditRulePreferences.setEnabled(!viewOnly);
	}
	
	private void updateRules(DataManager data) {
		for(Rule rule : data.getDataManagerRule().getRules()) {
			listModelRules.addElement(rule.getName());
		}
	}

	private void updateRules() {
		listModelRules.removeAllElements();
		for(Rule rule : data.getDataManagerRule().getRules()) {
			listModelRules.addElement(rule.getName());
		}
	}
}
