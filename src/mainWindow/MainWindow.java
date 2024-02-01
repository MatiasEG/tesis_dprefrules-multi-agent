package mainWindow;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.CSVwriter;
import IOmanager.NameAndFolderFrame;
import alternative.AlternativesFrame;
import criteria.CriteriaFrame;
import dataManager.DataManager;
import participant.ParticipantFrame;
import prefRules.PrefRulesFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    public JButton btnEditNameAndFolder;
    public JButton btnViewNameAndFolder;
    public JButton btnEditParticipants;
    public JButton btnViewParticipants;
    public JButton btnEditCriteria;
    public JButton btnViewCriteria;
    public JButton btnEditEvidence;
    public JButton btnViewEvidence;
    public JButton btnEditRules;
    public JButton btnViewRules;
    public JButton btnSaveFiles;

	private DataManager data;
	private DataManager dataClone;
	private boolean modifyingData;
	private Scout scout;
	private BtnStates state;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(new Scout());
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
	public MainWindow(Scout scout) {
		setTitle("Sistema de Decision Multi-Agente");
		this.scout = scout;
		data = new DataManager("", "");
		state = new BtnStates(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_6);
		
		JButton btnManual = new JButton("Manual de usuario");
		btnManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openManual();
			}
		});
		btnManual.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnManual);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_5);
		
		JPanel panelSaveFolder = new JPanel();
		contentPane.add(panelSaveFolder);
		panelSaveFolder.setLayout(new BoxLayout(panelSaveFolder, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_5 = new JLabel("- Ingrese un nombre para el problema y selecciones una carpeta donde desea guardar los archivos resultantes -");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSaveFolder.add(lblNewLabel_5);
		
		JPanel panelBtnSaveFolder = new JPanel();
		panelSaveFolder.add(panelBtnSaveFolder);
		panelBtnSaveFolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditNameAndFolder = new JButton("Definir nombre y carpeta");
		panelBtnSaveFolder.add(btnEditNameAndFolder);
		btnEditNameAndFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataClone = data.clone();
				
				NameAndFolderFrame frame = new NameAndFolderFrame(dataClone, false);
				frame.setVisible(true);
				
				if(state.getState()<0) state.setState(0);
				
				MainWindow.this.modifyingData = false;
				if(!data.getProjectName().equals("") && !data.getSaveFolder().equals("")) modifyingData = true; 
				
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
		            	
	            		if(!modifyingData) {
		            		if(!data.getProjectName().equals("") && !data.getSaveFolder().equals("")) {
			                	state.setState1(false);
			                }else {
			                	state.setState0(false);
			                	JOptionPane.showMessageDialog(null, "Debe indicar nombre y carpeta destino de los archivos para poder iniciar la definicion del problema.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
		            	}else {
		            		state.checkState(false);
		            	}
		            }
		        });
				
				state.checkState(true);
			}
		});
		btnEditNameAndFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewNameAndFolder = new JButton("Ver nombre y carpeta");
		btnViewNameAndFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NameAndFolderFrame frame = new NameAndFolderFrame(data, true);
				frame.setVisible(true);
			}
		});
		panelBtnSaveFolder.add(btnViewNameAndFolder);
		btnViewNameAndFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelAgents = new JPanel();
		contentPane.add(panelAgents);
		panelAgents.setLayout(new BoxLayout(panelAgents, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel = new JLabel("- Defina los participantes involucrados en el problema de eleccion -");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgents.add(lblNewLabel);
		
		JPanel panelBtnAgents = new JPanel();
		panelAgents.add(panelBtnAgents);
		panelBtnAgents.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditParticipants = new JButton("Definir participantes y prioridades");
		panelBtnAgents.add(btnEditParticipants);
		btnEditParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataClone = data.clone();
				
				ParticipantFrame frame = new ParticipantFrame(dataClone, false);
		        frame.setVisible(true);
		        
		        if(state.getState()<1) state.setState(1);
		        MainWindow.this.modifyingData = false;
		        if(data.getDataManagerParticipant().getParticipants().size()>0) modifyingData = true;
		        
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
		            	
		            	if(!modifyingData) {
			                if(data.getDataManagerParticipant().getParticipants().size()>0) {
			                	state.setState2(false);
			                }else {
			                	state.setState1(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un participante para poder seguir con el problema y definir los criterios de comparacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
			            }else {
			            	state.checkState(false);
			            }
		            }
		        });
		        
		        state.checkState(true);
			}
		});
		btnEditParticipants.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewParticipants = new JButton("Ver participantes y prioridades");
		btnViewParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantFrame frame = new ParticipantFrame(data, true);
		        frame.setVisible(true);
			}
		});
		panelBtnAgents.add(btnViewParticipants);
		btnViewParticipants.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panelCriterias = new JPanel();
		contentPane.add(panelCriterias);
		panelCriterias.setLayout(new BoxLayout(panelCriterias, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4 = new JLabel("- A continuacion defina los criterios con los que usted desea comparar las alternativas -");
		panelCriterias.add(lblNewLabel_4);
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panelBtnCriterias = new JPanel();
		panelCriterias.add(panelBtnCriterias);
		panelBtnCriterias.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditCriteria = new JButton("Establecer criterios");
		panelBtnCriterias.add(btnEditCriteria);
		btnEditCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewCriteria = new JButton("Ver criterios");
		btnViewCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaFrame frame = new CriteriaFrame(data, true);
				frame.setVisible(true);
			}
		});
		panelBtnCriterias.add(btnViewCriteria);
		btnViewCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEditCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataClone = data.clone();
				
				CriteriaFrame frame = new CriteriaFrame(dataClone, false);
				frame.setVisible(true);
				
				if(state.getState()<2) state.setState(2);
				MainWindow.this.modifyingData = false;
				if(data.getDataManagerCriteria().getCriterias().size()>0) modifyingData = true;
				
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
		            	
		            	if(!modifyingData) {
			                if(data.getDataManagerCriteria().getCriterias().size()>0) {
			                	state.setState3(false);
			                }else {
			                	state.setState2(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un criterio de comparacion para poder seguir con el problema y definir las alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
			            }else {
			            	if(data.getDataManagerCriteria().getCriterias().size()>0) {
			            		state.checkState(false);
			            	}else {
			            		state.setState2(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un criterio de comparacion para poder seguir con el problema y definir las alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			            	}
			            }
		            }
		        });
				
				state.checkState(true);
			}
		});
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JPanel panelAlternatives = new JPanel();
		contentPane.add(panelAlternatives);
		panelAlternatives.setLayout(new BoxLayout(panelAlternatives, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("- Determine cuales son las alternativas posibles -");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(lblNewLabel_3);
		
		JPanel panelBtnAlternatives = new JPanel();
		panelAlternatives.add(panelBtnAlternatives);
		panelBtnAlternatives.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditEvidence = new JButton("Definir evidencia");
		panelBtnAlternatives.add(btnEditEvidence);
		btnEditEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewEvidence = new JButton("Ver evidencia");
		btnViewEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlternativesFrame frame = new AlternativesFrame(data, true);
				frame.setVisible(true);
			}
		});
		panelBtnAlternatives.add(btnViewEvidence);
		btnViewEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEditEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataClone = data.clone();
				
				AlternativesFrame frame = new AlternativesFrame(dataClone, false);
				frame.setVisible(true);
				
				if(state.getState()<3) state.setState(3);
				MainWindow.this.modifyingData = false;
				if(data.getDataManagerEvidence().getAlternatives().size()>1) modifyingData = true;
					
				// WindowListener for detect when the frame is closed
				frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(MainWindow.this.dataClone!=null && MainWindow.this.dataClone.getDataValidated()) {
		            		data.updateData(MainWindow.this.dataClone);
		            	}else {
		            		// The user close the window without save.
		            	}
		            	MainWindow.this.dataClone = null;
		            	
		            	if(!modifyingData) {
			            	if(data.getDataManagerEvidence().getAlternatives().size()>1) {
			            		state.setState4(false);
			                }else {
			                	state.setState3(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos dos alternativas para poder realizar una comparacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
		            	}else {
		            		state.checkState(false);
		            	}
		            }
		        });
				
				state.checkState(true);
			}
		});
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panelRules = new JPanel();
		contentPane.add(panelRules);
		panelRules.setLayout(new BoxLayout(panelRules, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("- Defina las reglas que utilizara para comparar las alternativas en base a los criterios definidos -");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRules.add(lblNewLabel_1);
		
		JPanel panelBtnRules = new JPanel();
		panelRules.add(panelBtnRules);
		
		btnEditRules = new JButton("Definir reglas");
		btnEditRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataClone = data.clone();
				
				PrefRulesFrame frame = new PrefRulesFrame(dataClone, false);
				frame.setVisible(true);
				
				if(state.getState()<4) state.setState(4);
				MainWindow.this.modifyingData = false;
				if(data.getDataManagerRule().getRules().size()>0) modifyingData = true;
				
				// WindowListener for detect when the frame is closed
				frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(MainWindow.this.dataClone.getDataValidated()) {
		            		data.updateData(MainWindow.this.dataClone);
		            	}else {
		            		// The user close the window without save.
		            	}
		            	MainWindow.this.dataClone = null;
		            	
		            	if(!modifyingData) {
			                if(data.getDataManagerRule().getRules().size()>0) {
			                	state.setState5(false);
			                }else {
			                	state.setState4(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un criterio de comparacion para poder seguir con el problema y definir las alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
			            }else {
			            	if(data.getDataManagerRule().getRules().size()>0) {
			            		state.checkState(false);
			            	}else {
			            		state.setState4(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un criterio de comparacion para poder seguir con el problema y definir las alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			            	}
			            }
		            }
		        });
				
				state.checkState(true);
			}
		});
		panelBtnRules.add(btnEditRules);
		
		btnViewRules = new JButton("Ver reglas");
		btnViewRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrefRulesFrame frame = new PrefRulesFrame(data, true);
				frame.setVisible(true);
			}
		});
		panelBtnRules.add(btnViewRules);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		Dimension panelDimensions = new Dimension(700, 40);
		panelBtnSaveFolder.setPreferredSize(panelDimensions);
		panelBtnSaveFolder.setMaximumSize(panelDimensions);
		panelBtnAgents.setPreferredSize(panelDimensions);
		panelBtnAgents.setMaximumSize(panelDimensions);
		panelBtnCriterias.setPreferredSize(panelDimensions);
		panelBtnCriterias.setMaximumSize(panelDimensions);
		panelBtnAlternatives.setPreferredSize(panelDimensions);
		panelBtnAlternatives.setMaximumSize(panelDimensions);
		panelBtnRules.setPreferredSize(panelDimensions);
		panelBtnRules.setMaximumSize(panelDimensions);
		
		btnSaveFiles = new JButton("Guardar archivos");
		btnSaveFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CSVwriter.saveAgentPriorityToCSV(data);
				CSVwriter.saveCriteriasToCSV(data);
				CSVwriter.saveEvidenceToCSV(data);
				CSVwriter.saveRulesToCSV(data);
				CSVwriter.saveRulePriorityToCSV(data);
				MainWindow.this.scout.setDataSaved(true);
			}
		});
		btnSaveFiles.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSaveFiles);
		
		state.setState0(false);
	}
	
	private void openManual() {
		try {
            System.out.println("Start..");
            File file = new java.io.File("src/manual.html").getAbsoluteFile();
            Desktop.getDesktop().open(file);                    
            System.out.println("End..");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
	}
}
