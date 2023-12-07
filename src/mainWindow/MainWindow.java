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
import errors.CriteriaFileError;
import evidence.ParticipantsPriorityFrame;

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
		setBounds(100, 100, 650, 394);
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
		
		JButton btnUpdateParticipantsFile = new JButton("Actualizar archivo de participantes");
		panel.add(btnUpdateParticipantsFile, BorderLayout.SOUTH);
		
		JButton btnEditParticipantsPriority = new JButton("Editar prioridad entre participantes");
		btnEditParticipantsPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantsPriorityFrame frame = new ParticipantsPriorityFrame(data);
				frame.setVisible(true);
			}
		});
		panel.add(btnEditParticipantsPriority, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panelUsers.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Personas que participan del problema de eleccion:");
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(" - Una vez que todos los participantes esten definidos, puede establecer la prioridad que hay entre ellos");
		panel_1.add(lblNewLabel_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panelAlternatives = new JPanel();
		contentPane.add(panelAlternatives);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panelCriterias = new JPanel();
		contentPane.add(panelCriterias);
		panelCriterias.setLayout(new BorderLayout(0, 0));
		
		JButton btnCriteria = new JButton("Establecer criterios");
		panelCriterias.add(btnCriteria, BorderLayout.SOUTH);
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
        int indiceSeleccionado = listParticipants.getSelectedIndex();

        if (indiceSeleccionado != -1) {
        	int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea eliminar el elemento seleccionado?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                data.removeParticipant(listModelParticipants.remove(indiceSeleccionado));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar a un participante para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void addParticipantName(DataManager data) {
        String name = JOptionPane.showInputDialog(this, "Ingrese el nombre del participante que desea agregar:");
        if (name != null && !name.trim().isEmpty()) {
        	if(DataValidations.validateStringWithOnlyLettersAndNumbers(name)==null) {
        		if(DataValidations.validateListNotContainNewElement(data.getParticipants(), name)) {
        			listModelParticipants.addElement(name);
        			data.addParticipant(name);
        		}else {
        			JOptionPane.showMessageDialog(null, "Error, el nombre \""+name+"\" ya se encuentra en la lista de participantes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        		}
        	}else {
        		JOptionPane.showMessageDialog(null, "Error, el nombre \""+name+"\" contiene caracteres no validos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        	}
        }else {
        	JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre para el nuevo participante en el campo de texto", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

}
