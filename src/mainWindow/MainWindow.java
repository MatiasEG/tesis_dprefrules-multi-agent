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
		String csvFile = "C:/Users/Matia/Desktop/tesis_dprefrules-multi-agent/src/files/criteria.csv";  // .CSV path to file
		criterias = new ArrayList<Criteria>();
		try {
			criterias = CSVreader.reacCriteriasCSV(csvFile, data);
			
			//for (Criteria criteria : data.getCriterias()) {
			//    System.out.println("Nombre: " + criteria.getName());
			//    System.out.println("Valores: " + String.join(", ", criteria.getValues()));
			//    System.out.println("Numeric: " + criteria.isNumeric());
			//    System.out.println("-------------");
			//}
			
		} catch (CriteriaFileError e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 654);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnCriteria = new JButton("Establecer criterios");
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaTable criteriaTable = new CriteriaTable(data);
				criteriaTable.setVisible(true);
				criteriaTable.checkData(criterias);
			}
		});
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panelUsers = new JPanel();
		contentPane.add(panelUsers);
		panelUsers.setLayout(new BoxLayout(panelUsers, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Personas que participan del problema de eleccion:");
		panelUsers.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		panelUsers.add(scrollPane);
		
		listModelParticipants = new DefaultListModel<>();
		listParticipants = new JList<String>(listModelParticipants);
		scrollPane.setViewportView(listParticipants);
		
		JButton btnAddUser = new JButton("Agregar participante");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addParticipantName(data);
			}
		});
		panelUsers.add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("Eliminar participante seleccionado");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedParticipant();
			}
		});
		panelUsers.add(btnDeleteUser);
		contentPane.add(btnCriteria);
		
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
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
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
