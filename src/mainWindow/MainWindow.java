package mainWindow;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alternative.EvidenceTable;
import criteria.Criteria;
import criteria.CriteriaTable;
import dataManager.CSVreader;
import dataManager.DataManager;
import dataManager.DataValidations;
import dataManager.FileChooser;
import dataManager.CSVwriter;
import errors.AgentPriorityError;
import evidence.ParticipantsPriorityFrame;
import evidence.ParticipantsPriorityValidations;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private DefaultListModel<String> listModelParticipants;
    private JList<String> listParticipants;
	
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
		data = new DataManager();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panelAgents = new JPanel();
		contentPane.add(panelAgents);
		panelAgents.setLayout(new BoxLayout(panelAgents, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		listModelParticipants = new DefaultListModel<>();
		
		JPanel panel_1 = new JPanel();
		panelAgents.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Personas que participan del problema de eleccion");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("- Una vez que todos los participantes esten definidos, puede establecer la prioridad que hay entre ellos -");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Cargar archivo");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readAgentPriorityCSV(path, data);
					MainWindow.this.updateVisualComponents(data);
				} catch (AgentPriorityError e1) {
	    			JOptionPane.showMessageDialog(null, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panel_1.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		listParticipants = new JList<String>(listModelParticipants);
		listParticipants.setValueIsAdjusting(true);
		scrollPane.setViewportView(listParticipants);
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2);
		
		JLabel lblNewLabel_2 = new JLabel("Recuerde guardar los cambios para que estos se vean reflejados");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAddUser = new JButton("Agregar participante");
		panel_2.add(btnAddUser);
		btnAddUser.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnDeleteUser = new JButton("Eliminar participante seleccionado");
		panel_2.add(btnDeleteUser);
		btnDeleteUser.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnUpdateParticipantsFile = new JButton("Guardar archivo");
		panel_2.add(btnUpdateParticipantsFile);
		btnUpdateParticipantsFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnEditParticipantsPriority = new JButton("Editar prioridad entre participantes");
		panel_2.add(btnEditParticipantsPriority);
		btnEditParticipantsPriority.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEditParticipantsPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantsPriorityFrame frame = new ParticipantsPriorityFrame(data);
				frame.setVisible(true);
			}
		});
		btnUpdateParticipantsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(data.getParticipants().size()>0) {
					String path = FileChooser.showFileChooser();
					CSVwriter.saveAgentPriorityToCSV(path, data);
				}
			}
		});
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedParticipant();
			}
		});
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addParticipantName(data);
			}
		});
		
		JPanel panelCriterias = new JPanel();
		contentPane.add(panelCriterias);
		panelCriterias.setLayout(new BoxLayout(panelCriterias, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4 = new JLabel("- A continuacion defina los criterios con los que usted desea comparar las alternativas -");
		panelCriterias.add(lblNewLabel_4);
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnCriteria = new JButton("Establecer criterios");
		btnCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCriterias.add(btnCriteria);
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaTable criteriaTable = new CriteriaTable(data);
				criteriaTable.setVisible(true);
				criteriaTable.checkData(data);
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
		
		JButton btnEvidence = new JButton("Definir evidencia");
		btnEvidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(btnEvidence);
		btnEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EvidenceTable alternativeTable = new EvidenceTable(data);
				alternativeTable.setVisible(true);
				alternativeTable.checkData(data);
			}
		});
	}
	
	private void deleteSelectedParticipant() {
        int selectedIndex = listParticipants.getSelectedIndex();

        if (selectedIndex != -1) {
        	int option = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea eliminar el participante seleccionado ("+listModelParticipants.getElementAt(selectedIndex)+")?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                data.removeParticipant(listModelParticipants.remove(selectedIndex));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar a un participante para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void addParticipantName(DataManager data) {
        String name = JOptionPane.showInputDialog(this, "Ingrese el nombre del participante que desea agregar:");
        String validation = ParticipantsPriorityValidations.validateAgentName(name, data);
        if(validation.equals("OK")) {
        	if(DataValidations.validateStringListNotContainNewElement(data.getParticipants(), name)) {
    			listModelParticipants.addElement(name);
    			data.addParticipant(name);
    		}else {
    			JOptionPane.showMessageDialog(null, "Error, el nombre \""+name+"\" ya se encuentra en la lista de participantes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    		}
        }else {
        	JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	private void updateVisualComponents(DataManager data) {		
		listModelParticipants.clear();
		for(String agent: data.getParticipants()) {
			listModelParticipants.addElement(agent);
		}
	}

}
