package mainWindow;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.NameAndFolderFrame;
import agent.AgentFrame;
import alternative.EvidenceFrame;
import criteria.Criteria;
import criteria.CriteriaFrame;
import dataManager.DataManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    
	
	private DataManager data;
	List<Criteria> criterias;

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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 375);
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
		
		JButton btnEditNameAndFolder = new JButton("Definir nombre y carpeta");
		btnEditNameAndFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NameAndFolderFrame frame = new NameAndFolderFrame(data);
				frame.setVisible(true);
			}
		});
		btnEditNameAndFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSaveFolder.add(btnEditNameAndFolder);
		
		JButton btnViewNameAndFolder = new JButton("Ver nombre y carpeta");
		btnViewNameAndFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSaveFolder.add(btnViewNameAndFolder);
		
		Component verticalStrut_2_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2_1);
		
		JPanel panelAgents = new JPanel();
		contentPane.add(panelAgents);
		panelAgents.setLayout(new BoxLayout(panelAgents, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JLabel lblNewLabel = new JLabel("Defina los participantes involucrados en el problema de eleccion");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgents.add(lblNewLabel);
		
		JButton btnEditParticipants = new JButton("Definir participantes y prioridades");
		btnEditParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgentFrame frame = new AgentFrame(data);
				frame.setVisible(true);
			}
		});
		btnEditParticipants.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgents.add(btnEditParticipants);
		
		JButton btnViewParticipants = new JButton("Ver participantes y prioridades");
		btnViewParticipants.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgents.add(btnViewParticipants);
		
		JPanel panelCriterias = new JPanel();
		contentPane.add(panelCriterias);
		panelCriterias.setLayout(new BoxLayout(panelCriterias, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4 = new JLabel("- A continuacion defina los criterios con los que usted desea comparar las alternativas -");
		panelCriterias.add(lblNewLabel_4);
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnEditCriteria = new JButton("Establecer criterios");
		btnEditCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCriterias.add(btnEditCriteria);
		
		JButton btnViewCriteria = new JButton("Ver criterios");
		btnViewCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCriterias.add(btnViewCriteria);
		btnEditCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String validation = canDefineCriterias(data);
				if(validation.equals("OK")) {
					CriteriaFrame criteriaTable = new CriteriaFrame(data);
					criteriaTable.setVisible(true);
					criteriaTable.checkData(data);
				}else {
					JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelAlternatives = new JPanel();
		contentPane.add(panelAlternatives);
		panelAlternatives.setLayout(new BoxLayout(panelAlternatives, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("Determine cuales son las alternativas posibles");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(lblNewLabel_3);
		
		JButton btnEditEvidence = new JButton("Definir evidencia");
		btnEditEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(btnEditEvidence);
		
		JButton btnViewEvidence = new JButton("Ver evidencia");
		btnViewEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(btnViewEvidence);
		btnEditEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String validation = canDefineEvidence(data);
				if(validation.equals("OK")) {
					EvidenceFrame alternativeTable = new EvidenceFrame(data);
					alternativeTable.setVisible(true);
					alternativeTable.checkData(data);
				}else {
					JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private String canDefineEvidence(DataManager data) {
		if(data.getParticipants().size()>0) {
			if(data.getCriterias().size()>0) {
				return "OK";
			}else {
				return "Advertencia. Debe definir al menos a un criterio de comparacion de alternativas.";
			}
		}else {
			return "Advertencia. Debe definir al menos a un participante del problema.";
		}
	}

	private String canDefineCriterias(DataManager data) {
		if(data.getParticipants().size()>0) {
			return "OK";
		}else {
			return "Advertencia. Debe definir al menos a un participante del problema.";
		}
	}
	
}
