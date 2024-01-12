package mainWindow;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.NameAndFolderFrame;
import agent.AgentFrame;
import alternative.EvidenceFrame;
import criteria.CriteriaFrame;
import dataManager.DataManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {

	// TODO ver que hacer con el estado de los botones cuando se elimina informacion que ya habia sido definida
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JButton btnEditNameAndFolder;
    private JButton btnViewNameAndFolder;
    private JButton btnEditParticipants;
    private JButton btnViewParticipants;
    private JButton btnEditCriteria;
    private JButton btnViewCriteria;
    private JButton btnEditEvidence;
    private JButton btnViewEvidence;

	private DataManager data;
	private boolean modifyingData;
	private int state;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setTitle("Sistema de Decision Multi-Agente");
		data = new DataManager("", "");
		state = -1;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panelSaveFolder = new JPanel();
		contentPane.add(panelSaveFolder);
		panelSaveFolder.setLayout(new BoxLayout(panelSaveFolder, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_5 = new JLabel("A continuacion por favor ingrese un nombre para el problema y selecciones una carpeta donde desea guardar los datos.");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSaveFolder.add(lblNewLabel_5);
		
		JPanel panelBtnSaveFolder = new JPanel();
		panelSaveFolder.add(panelBtnSaveFolder);
		panelBtnSaveFolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditNameAndFolder = new JButton("Definir nombre y carpeta");
		panelBtnSaveFolder.add(btnEditNameAndFolder);
		btnEditNameAndFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NameAndFolderFrame frame = new NameAndFolderFrame(data);
				frame.setVisible(true);
				
				if(state<0) state = 0;
				
				MainWindow.this.modifyingData = false;
				if(!data.getProjectName().equals("") && !data.getSaveFolder().equals("")) modifyingData = true; 
				
				// WindowListener for detect when the frame is closed
				frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(!modifyingData) {
		            		if(!data.getProjectName().equals("") && !data.getSaveFolder().equals("")) {
			                	setState1(false);
			                }else {
			                	setState0(false);
			                	JOptionPane.showMessageDialog(null, "Debe indicar nombre y carpeta destino de los archivos para poder iniciar la definicion del problema.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
		            	}else {
		            		checkState(false);
		            	}
		            }
		        });
				
				checkState(true);
			}
		});
		btnEditNameAndFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewNameAndFolder = new JButton("Ver nombre y carpeta");
		panelBtnSaveFolder.add(btnViewNameAndFolder);
		btnViewNameAndFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelAgents = new JPanel();
		contentPane.add(panelAgents);
		panelAgents.setLayout(new BoxLayout(panelAgents, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel = new JLabel("Defina los participantes involucrados en el problema de eleccion");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgents.add(lblNewLabel);
		
		JPanel panelBtnAgents = new JPanel();
		panelAgents.add(panelBtnAgents);
		panelBtnAgents.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditParticipants = new JButton("Definir participantes y prioridades");
		panelBtnAgents.add(btnEditParticipants);
		btnEditParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgentFrame frame = new AgentFrame(data);
		        frame.setVisible(true);
		        
		        if(state<1) state = 1;
		        MainWindow.this.modifyingData = false;
		        if(data.getParticipants().size()>0) modifyingData = true;
		        
		        // WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(!modifyingData) {
			                if(data.getParticipants().size()>0) {
			                	setState2(false);
			                }else {
			                	setState1(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un participante para poder seguir con el problema y definir los criterios de comparacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
			            }else {
			            	checkState(false);
			            }
		            }
		        });
		        
		        checkState(true);
			}
		});
		btnEditParticipants.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewParticipants = new JButton("Ver participantes y prioridades");
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
		panelBtnCriterias.add(btnViewCriteria);
		btnViewCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEditCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaFrame frame = new CriteriaFrame(data);
				frame.setVisible(true);
				frame.checkData(data);
				
				if(state<2) state = 2;
				MainWindow.this.modifyingData = false;
				if(data.getCriterias().size()>0) modifyingData = true;
				
				// WindowListener for detect when the frame is closed
				frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(!modifyingData) {
			                if(data.getCriterias().size()>0) {
			                	setState3(false);
			                }else {
			                	setState2(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos un criterio de comparacion para poder seguir con el problema y definir las alternativas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
			            }else {
			            	checkState(false);
			            }
		            }
		        });
				
				checkState(true);
			}
		});
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JPanel panelAlternatives = new JPanel();
		contentPane.add(panelAlternatives);
		panelAlternatives.setLayout(new BoxLayout(panelAlternatives, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("Determine cuales son las alternativas posibles");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(lblNewLabel_3);
		
		JPanel panelBtnAlternatives = new JPanel();
		panelAlternatives.add(panelBtnAlternatives);
		panelBtnAlternatives.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEditEvidence = new JButton("Definir evidencia");
		panelBtnAlternatives.add(btnEditEvidence);
		btnEditEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnViewEvidence = new JButton("Ver evidencia");
		panelBtnAlternatives.add(btnViewEvidence);
		btnViewEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEditEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EvidenceFrame frame = new EvidenceFrame(data);
				frame.setVisible(true);
				frame.checkData(data);
				
				if(state<3) state = 3;
				MainWindow.this.modifyingData = false;
				if(data.getAlternatives().size()>1) modifyingData = true;
					
				// WindowListener for detect when the frame is closed
				frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	if(!modifyingData) {
			            	if(data.getAlternatives().size()>1) {
			                	setState4(false);
			                }else {
			                	setState3(false);
			                	JOptionPane.showMessageDialog(null, "Debe definir al menos dos alternativas para poder realizar una comparacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			                }
		            	}else {
		            		checkState(false);
		            	}
		            }
		        });
				
				checkState(true);
			}
		});
		
		setState0(false);
	}
	
	private void checkState(boolean isEditing) {
		switch (state) {
	        case 0:
	            // Must indicate the name and project folder
	            setState0(isEditing);
	            break;
	        case 1:
	            // Must indicate the participants
	        	setState1(isEditing);
	            break;
	        case 2:
	            // Must indicate the criteria
	        	setState2(isEditing);
	            break;
	        case 3:
	            // Must indicate the evidence
	        	setState3(isEditing);
	            break;
	        case 4:
	            // Must indicate the preference rules
	        	setState4(isEditing);
	            break;
	        default:
	            // Error
	        	JOptionPane.showMessageDialog(null, "Estado invalido", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
	}
	
	// The user start the program
	// > Must indicate the name and project folder
	private void setState0(boolean isEditing) {
		state = 0;
		if(isEditing) {
			btnEditNameAndFolder.setEnabled(false);
		}else {
			btnEditNameAndFolder.setEnabled(true);
		}
	    btnViewNameAndFolder.setEnabled(false);
	    
	    btnEditParticipants.setEnabled(false);
	    btnViewParticipants.setEnabled(false);
	    
	    btnEditCriteria.setEnabled(false);
	    btnViewCriteria.setEnabled(false);
	    
	    btnEditEvidence.setEnabled(false);
	    btnViewEvidence.setEnabled(false);
	}
	
	// The user indicate name and project folder
	// > Must indicate the participants
	private void setState1(boolean isEditing) {
		state = 1;
		if(isEditing) {
			btnEditNameAndFolder.setEnabled(false);
			btnEditParticipants.setEnabled(false);
		}else {
			btnEditNameAndFolder.setEnabled(true);
			btnEditParticipants.setEnabled(true);
		}
		
	    btnViewNameAndFolder.setEnabled(true);
	    
	    btnViewParticipants.setEnabled(false);
	    
	    btnEditCriteria.setEnabled(false);
	    btnViewCriteria.setEnabled(false);
	    
	    btnEditEvidence.setEnabled(false);
	    btnViewEvidence.setEnabled(false);
	}
	
	// The user indicate the participants and they priorities
	// > Must indicate the criteria
	// [Here the participants can be edited but never can be 0]
	private void setState2(boolean isEditing) {
		state = 2;
		if(isEditing) {
			btnEditNameAndFolder.setEnabled(false);
			btnEditParticipants.setEnabled(false);
			btnEditCriteria.setEnabled(false);
		}else {
			btnEditNameAndFolder.setEnabled(true);
			btnEditParticipants.setEnabled(true);
			btnEditCriteria.setEnabled(true);
		}
		
	    btnViewNameAndFolder.setEnabled(true);
	    
	    btnViewParticipants.setEnabled(true);
	    
	    btnViewCriteria.setEnabled(false);
	    
	    btnEditEvidence.setEnabled(false);
	    btnViewEvidence.setEnabled(false);
	}
	
	// The user indicate the criteria
	// > Must indicate the evidence
	// [Here the criteria can be edited but never can be 0]
	private void setState3(boolean isEditing) {
		state = 3;
		if(isEditing) {
			btnEditNameAndFolder.setEnabled(false);
			btnEditParticipants.setEnabled(false);
			btnEditCriteria.setEnabled(false);
			btnEditEvidence.setEnabled(false);
		}else {
			btnEditNameAndFolder.setEnabled(true);
			btnEditParticipants.setEnabled(true);
			btnEditCriteria.setEnabled(true);
			btnEditEvidence.setEnabled(true);
		}
		
	    btnViewNameAndFolder.setEnabled(true);
	    
	    btnViewParticipants.setEnabled(true);
	    
	    btnViewCriteria.setEnabled(true);
	    
	    btnViewEvidence.setEnabled(false);
	}
	
	// The user indicate the evidence
	// > Must indicate the preference rules
	// [Here the alternatives can be edited but never can be less than 2]
	private void setState4(boolean isEditing) {
		state = 4;
		if(isEditing) {
			btnEditNameAndFolder.setEnabled(false);
			btnEditParticipants.setEnabled(false);
			btnEditCriteria.setEnabled(false);
			btnEditEvidence.setEnabled(false);
		}else {
			btnEditNameAndFolder.setEnabled(true);
			btnEditParticipants.setEnabled(true);
			btnEditCriteria.setEnabled(true);
			btnEditEvidence.setEnabled(true);
		}
		
	    btnViewNameAndFolder.setEnabled(true);
	    
	    btnViewParticipants.setEnabled(true);
	    
	    btnViewCriteria.setEnabled(true);
	    
	    btnViewEvidence.setEnabled(true);
	}
}
