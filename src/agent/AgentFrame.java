package agent;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.CSVreader;
import IOmanager.CSVwriter;
import IOmanager.FileChooser;
import dataManager.DataManager;
import dataManager.DataValidations;
import errors.AgentPriorityError;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgentFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DefaultListModel<String> listModelParticipants;
    private JList<String> listParticipants;
    private DataManager data;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgentFrame frame = new AgentFrame(new DataManager("",""));
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
	public AgentFrame(DataManager data) {
		this.data = data;
		
		setTitle("Definir participantes");
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Personas que participan del problema de eleccion");
		lblNewLabel.setAlignmentX(0.5f);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("- Una vez que todos los participantes esten definidos, puede establecer la prioridad que hay entre ellos -");
		lblNewLabel_1.setAlignmentX(0.5f);
		contentPane.add(lblNewLabel_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JButton btnLoadFile = new JButton("Cargar archivo");
		btnLoadFile.setAlignmentX(0.5f);
		contentPane.add(btnLoadFile);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		listModelParticipants = new DefaultListModel<>();
		listParticipants = new JList<String>(listModelParticipants);
		listParticipants.setValueIsAdjusting(true);
		scrollPane.setViewportView(listParticipants);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel_2 = new JLabel("Recuerde guardar los cambios para que estos se vean reflejados");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setAlignmentX(0.5f);
		contentPane.add(lblNewLabel_2);
		
		JPanel panelAgentsButtons = new JPanel();
		contentPane.add(panelAgentsButtons);
		FlowLayout fl_panelAgentsButtons = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panelAgentsButtons.setLayout(fl_panelAgentsButtons);
		
		JButton btnAddUser = new JButton("Agregar participante");
		btnAddUser.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("Eliminar participante seleccionado");
		btnDeleteUser.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnDeleteUser);
		
		JButton btnEditParticipantsPriority = new JButton("Editar prioridad entre participantes");
		btnEditParticipantsPriority.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnEditParticipantsPriority);
		
		JButton btnSaveParticipantsFile = new JButton("Guardar cambios");
		btnSaveParticipantsFile.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnSaveParticipantsFile);
		btnSaveParticipantsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AgentFrame.this.data.getParticipants().size()>0) {
					CSVwriter.saveAgentPriorityToCSV(AgentFrame.this.data);
					JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readAgentPriorityCSV(path, AgentFrame.this.data);
					AgentFrame.this.updateVisualComponents(AgentFrame.this.data);
				} catch (AgentPriorityError e1) {
	    			JOptionPane.showMessageDialog(null, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEditParticipantsPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String validation = canDefineAgentPriority(AgentFrame.this.data);
				if(validation.equals("OK")) {
					AgentPriorityFrame frame = new AgentPriorityFrame(AgentFrame.this.data);
					frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
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
				addParticipantName(AgentFrame.this.data);
			}
		});
		this.updateVisualComponents(data);
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
        String validation = AgentPriorityValidations.validateAgentName(name, data);
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
	
	private String canDefineAgentPriority(DataManager data) {
		if(data.getParticipants().size()>1) {
			return "OK";
		}else {
			return "Advertencia. Debe definir al menos a dos participante del problema para poder compararlos.";
		}
	}
}
