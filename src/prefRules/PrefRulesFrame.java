package prefRules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.CSVreader;
import IOmanager.CSVwriter;
import IOmanager.FileChooser;
import criteria.Criteria;
import dataManager.DataManager;
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
	private JButton btnSaveFile;
	private JButton btnEditRulePreferences;
	
	private DefaultListModel<String> listModelRules;
	private JList<String> listRules;
	private DataManager data;

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
	public PrefRulesFrame(DataManager data, boolean onlyView) {
		//TODO ver como mejorar esta ventanta
		
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
		Dimension panelDimensionsScrollPane = new Dimension(450, 200);
		scrollPane.setPreferredSize(panelDimensionsScrollPane);
		scrollPane.setMaximumSize(panelDimensionsScrollPane);
		contentPane.add(scrollPane);
		
		listModelRules = new DefaultListModel<>();
		listRules = new JList<String>(listModelRules);
		scrollPane.setViewportView(listRules);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		Dimension panelBtnDimensions = new Dimension(450, 40);
		
		JPanel panelFileButtons = new JPanel();
		contentPane.add(panelFileButtons);
		panelFileButtons.setPreferredSize(panelBtnDimensions);
		panelFileButtons.setMaximumSize(panelBtnDimensions);
		panelFileButtons.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnLoadFile = new JButton("Cargar desde archivo");
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
		panelFileButtons.add(btnLoadFile);
		btnLoadFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnSaveFile = new JButton("Guardar archivo");
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CSVwriter.saveRulesToCSV(PrefRulesFrame.this.data);
				JOptionPane.showMessageDialog(null, "Alternativas guardadas correctamente, ya puede cerrar esta ventana.","Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
				PrefRulesFrame.this.data.setDataValidated();
				onlyViewMod(true);
			}
		});
		panelFileButtons.add(btnSaveFile);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelButtons = new JPanel();
		
		panelButtons.setPreferredSize(panelBtnDimensions);
		panelButtons.setMaximumSize(panelBtnDimensions);
		contentPane.add(panelButtons);
		panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNewRule = new JButton("Nueva regla");
		btnNewRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrefRulesFrame.this.onlyViewMod(true);
				PrefRuleCreationFrame frame = new PrefRuleCreationFrame(PrefRulesFrame.this.data, null);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	updateRules();
		            	PrefRulesFrame.this.onlyViewMod(false);
		            }
		        });
			}
		});
		panelButtons.add(btnNewRule);
		
		btnDeleteRule = new JButton("Eliminar");
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
		            	PrefRulesFrame.this.data.removeRule(ruleName);
		            	for(int i=0; i<PrefRulesFrame.this.data.getRules().size(); i++) {
		            		if(PrefRulesFrame.this.data.getRules().get(i).getName().equals(ruleName)) {
		            			PrefRulesFrame.this.data.getRules().remove(i);
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
		panelButtons.add(btnDeleteRule);
		
		btnEditRule = new JButton("Editar");
		btnEditRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listRules.getSelectedIndex();
				Rule rule = null;
		        if (selectedIndex != -1) {
		        	String ruleName = listRules.getSelectedValue();
	            	for(int i=0; i<PrefRulesFrame.this.data.getRules().size(); i++) {
	            		if(PrefRulesFrame.this.data.getRules().get(i).getName().equals(ruleName)) {
	            			rule = PrefRulesFrame.this.data.getRules().get(i);
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
		panelButtons.add(btnEditRule);
		
		JButton btnDescriptionRule = new JButton("Descripcion");
		btnDescriptionRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listRules.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	String ruleName = listRules.getSelectedValue();
	            	for(int i=0; i<PrefRulesFrame.this.data.getRules().size(); i++) {
	            		if(PrefRulesFrame.this.data.getRules().get(i).getName().equals(ruleName)) {
	            			JOptionPane.showMessageDialog(null, PrefRulesFrame.this.data.getRules().get(i).getRuleDescription(), "Error", JOptionPane.INFORMATION_MESSAGE);
	            			break;
	            		}
	            	}
		        } else {
		            JOptionPane.showMessageDialog(PrefRulesFrame.this, "Debe seleccionar a un regla para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
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
		panelPreferences.setPreferredSize(panelBtnDimensions);
		panelPreferences.setMaximumSize(panelBtnDimensions);
		
		btnEditRulePreferences = new JButton("Definir preferencias");
		panelPreferences.add(btnEditRulePreferences);
		btnEditRulePreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrefRulePreferencesFrame frame = new PrefRulePreferencesFrame(PrefRulesFrame.this.data, false);
				frame.setVisible(true);
			}
		});
		btnEditRulePreferences.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnViewRulePreferences = new JButton("Ver preferencias");
		btnViewRulePreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrefRulePreferencesFrame frame = new PrefRulePreferencesFrame(PrefRulesFrame.this.data, true);
				frame.setVisible(true);
			}
		});
		panelPreferences.add(btnViewRulePreferences);
		onlyViewMod(onlyView);
		updateRules(data);
	}
	
	private void onlyViewMod(boolean onlyView) {
		btnEditRule.setEnabled(!onlyView);
		btnDeleteRule.setEnabled(!onlyView);
		btnNewRule.setEnabled(!onlyView);
		btnLoadFile.setEnabled(!onlyView);
		btnSaveFile.setEnabled(!onlyView);
		btnEditRulePreferences.setEnabled(!onlyView);
	}
	
	private void updateRules(DataManager data) {
		for(Rule rule : data.getRules()) {
			listModelRules.addElement(rule.getName());
		}
	}

	private void updateRules() {
		listModelRules.removeAllElements();
		for(Rule rule : data.getRules()) {
			listModelRules.addElement(rule.getName());
		}
	}
}
