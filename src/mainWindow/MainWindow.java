package mainWindow;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alternative.AlternativeTable;
import criteria.Criteria;
import criteria.CriteriaTable;
import dataManager.CSVreader;
import dataManager.DataManager;
import dataManager.DataValidations;
import dataManager.FileChooser;
import dataManager.IOManager;
import errors.AgentPriorityError;
import errors.CriteriaFileError;
import errors.SintacticStringError;
import evidence.Alternative;
import evidence.ParticipantsPriorityFrame;
import evidence.ParticipantsPriorityValidations;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private DefaultListModel<String> listModelAlternatives;
    private JList<String> listAlternatives;
	
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
		data = new DataManager();
		// TODO borrar esto y consultarselo al usuario
		String csvFile = "C:/Users/Matia/Desktop/tesis_dprefrules-multi-agent/src/files/criteria.csv";  // .CSV path to file
		criterias = new ArrayList<Criteria>();
		try {
			criterias = CSVreader.reacCriteriasCSV(csvFile, data);
		} catch (CriteriaFileError e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panelUsers = new JPanel();
		contentPane.add(panelUsers);
		panelUsers.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelUsers.add(scrollPane);
		
		listModelParticipants = new DefaultListModel<>();
		listParticipants = new JList<String>(listModelParticipants);
		listParticipants.setValueIsAdjusting(true);
		scrollPane.setViewportView(listParticipants);
		
		JPanel panel = new JPanel();
		panelUsers.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnAddUser = new JButton("Agregar participante");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addParticipantName(data);
			}
		});
		panel.add(btnAddUser, BorderLayout.EAST);
		
		JButton btnDeleteUser = new JButton("Eliminar participante seleccionado");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedParticipant();
			}
		});
		panel.add(btnDeleteUser, BorderLayout.WEST);
		
		JButton btnUpdateParticipantsFile = new JButton("Guardar/Sobreescribir archivo de participantes");
		btnUpdateParticipantsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(data.getParticipants().size()>0) {
					String path = FileChooser.showFileChooser();
					IOManager.saveAgentPriorityToCSV(path, data);
				}
			}
		});
		panel.add(btnUpdateParticipantsFile, BorderLayout.SOUTH);
		
		JButton btnEditParticipantsPriority = new JButton("Editar prioridad entre participantes");
		btnEditParticipantsPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantsPriorityFrame frame = new ParticipantsPriorityFrame(data);
				frame.setVisible(true);
			}
		});
		panel.add(btnEditParticipantsPriority, BorderLayout.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("Recuerde guardar los cambios para que estos se vean reflejados");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panelUsers.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Personas que participan del problema de eleccion:");
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
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panelAlternatives = new JPanel();
		contentPane.add(panelAlternatives);
		panelAlternatives.setLayout(new BoxLayout(panelAlternatives, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("Determine cuales son las alternativas posibles");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAlternatives.add(lblNewLabel_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panelAlternatives.add(scrollPane_1);
		
		listModelAlternatives = new DefaultListModel<>();
		listAlternatives = new JList<String>(listModelAlternatives);
		scrollPane_1.setViewportView(listAlternatives);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewAlternative = new JButton("Agregar alternativa");
		btnNewAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAlternative(data);
			}
		});
		panel_2.add(btnNewAlternative);
		
		JButton btnDeleteAlternative = new JButton("Eliminar alternativa");
		btnDeleteAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedAlternative();
			}
		});
		panel_2.add(btnDeleteAlternative);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelCriterias = new JPanel();
		contentPane.add(panelCriterias);
		panelCriterias.setLayout(new BorderLayout(0, 0));
		
		JButton btnCriteria = new JButton("Establecer criterios");
		panelCriterias.add(btnCriteria, BorderLayout.CENTER);
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaTable criteriaTable = new CriteriaTable(data);
				criteriaTable.setVisible(true);
				criteriaTable.checkData(criterias);
			}
		});
		
		JButton btnEvidence = new JButton("Definir evidencia");
		btnEvidence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlternativeTable alternativeTable = new AlternativeTable(data);
				alternativeTable.setVisible(true);
			}
		});
		contentPane.add(btnEvidence);
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
	
	private void deleteSelectedAlternative() {
        int selectedIndex = listAlternatives.getSelectedIndex();

        if (selectedIndex != -1) {
        	int option = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea eliminar la alternativa seleccionada ("+listModelAlternatives.getElementAt(selectedIndex)+")?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                data.removeAlternative(listModelAlternatives.remove(selectedIndex));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar a un participante para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void addAlternative(DataManager data) {
        String name = JOptionPane.showInputDialog(this, "Ingrese el nombre de la alternativa que desea agregar:");
        SintacticStringError validation = DataValidations.validateStringWithOnlyLettersAndNumbers(name);
        if(validation==null) {
        	if(DataValidations.validateStringListNotContainNewElement(data.getAlternativesNames(), name)) {
				listModelAlternatives.addElement(name);
				data.addAlternative(new Alternative(name));
			}else {
				JOptionPane.showMessageDialog(null, "Error, el nombre \""+name+"\" ya se encuentra en la lista de participantes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
        }else {
        	JOptionPane.showMessageDialog(null, "Error."+validation.getMsg(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
	    	
    }
	
	private void updateVisualComponents(DataManager data) {		
		listModelParticipants.clear();
		for(String agent: data.getParticipants()) {
			listModelParticipants.addElement(agent);
		}
	}

}
