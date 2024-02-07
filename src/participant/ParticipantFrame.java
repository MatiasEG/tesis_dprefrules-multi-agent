package participant;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.CSVreader;
import IOmanager.FileChooser;
import dataManager.DataManager;
import exceptions.AgentPriorityException;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ParticipantFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JButton btnLoadFile;
	private JButton btnAddUser;
	private JButton btnDeleteUser;
	private JButton btnEditParticipantsPriority;
	private JButton btnSave;
	private JButton btnViewParticipantsPriority;
	
	private DefaultListModel<String> listModelParticipants;
    private JList<String> listParticipants;
    private DataManager data;
    private DataManager dataClone;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParticipantFrame frame = new ParticipantFrame(new DataManager("",""), false);
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
	public ParticipantFrame(DataManager data, boolean viewOnly) {
		this.data = data;
		
		setTitle("Definir participantes");
		setBounds(100, 100, 800, 350);
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
		
		btnLoadFile = new JButton("Cargar archivo");
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
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		btnDeleteUser = new JButton("Eliminar participante");
		contentPane.add(btnDeleteUser);
		btnDeleteUser.setAlignmentX(0.5f);
		
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
		
		btnAddUser = new JButton("Agregar participante");
		btnAddUser.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnAddUser);
		
		btnEditParticipantsPriority = new JButton("Editar prioridad entre participantes");
		btnEditParticipantsPriority.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnEditParticipantsPriority);
		
		btnViewParticipantsPriority = new JButton("Ver prioridad entre participantes");
		btnViewParticipantsPriority.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnViewParticipantsPriority);
		
		btnSave = new JButton("Guardar cambios");
		btnSave.setAlignmentX(0.5f);
		panelAgentsButtons.add(btnSave);
		
		updateVisualComponents(data);
		actionListeners();
		viewOnlyMod(viewOnly);
	}
	
	private void actionListeners() {
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addParticipantName(ParticipantFrame.this.data);
			}
		});
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedParticipant();
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ParticipantFrame.this.data.getDataManagerParticipant().getParticipants().size()>0) {
					JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
					ParticipantFrame.this.data.setDataValidated();
					viewOnlyMod(true);
				}
			}
		});
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readAgentPriorityCSV(path, ParticipantFrame.this.data);
					ParticipantFrame.this.updateVisualComponents(ParticipantFrame.this.data);
				} catch (AgentPriorityException e1) {
	    			JOptionPane.showMessageDialog(null, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEditParticipantsPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editParticipantsPriority();
			}
		});
		btnViewParticipantsPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantPriorityFrame frame = new ParticipantPriorityFrame(ParticipantFrame.this.data, true);
				frame.setVisible(true);
			}
		});
	}
	
	private void editParticipantsPriority() {
		String validation = canDefineAgentPriority(ParticipantFrame.this.data);
		if(validation.equals("OK")) {
			dataClone = ParticipantFrame.this.data.clone();
			ParticipantFrame.this.viewOnlyMod(true);
			
			ParticipantPriorityFrame frame = new ParticipantPriorityFrame(ParticipantFrame.this.dataClone, false);
			frame.setVisible(true);
			
			// WindowListener for detect when the frame is closed
			frame.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	            	if(dataClone.getDataValidated()) {
	            		ParticipantFrame.this.data.updateData(dataClone);
	            	}else {
	            		// The user close the window without save.
	            	}
	            	dataClone = null;
	            	
	            	ParticipantFrame.this.viewOnlyMod(false);
	            }
	        });
			
		}else {
			JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		btnLoadFile.setEnabled(!viewOnly);
		btnAddUser.setEnabled(!viewOnly);;
		btnDeleteUser.setEnabled(!viewOnly);;
		btnEditParticipantsPriority.setEnabled(!viewOnly);;
		btnSave.setEnabled(!viewOnly);
	}
	
	private void deleteSelectedParticipant() {
        int selectedIndex = listParticipants.getSelectedIndex();

        if (selectedIndex != -1) {
        	int option = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea eliminar el participante seleccionado ("+listModelParticipants.getElementAt(selectedIndex)+")?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                data.getDataManagerParticipant().removeParticipant(listModelParticipants.remove(selectedIndex));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar a un participante para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void addParticipantName(DataManager data) {
        String name = JOptionPane.showInputDialog(this, "Ingrese el nombre del participante que desea agregar:");
        name = name.toLowerCase();
        if(data.getDataManagerParticipant().validParticipantName(name)) {
        	listModelParticipants.addElement(name);
    		data.getDataManagerParticipant().addParticipant(new Participant(name));
        }else {
        	JOptionPane.showMessageDialog(null, "El nombre del participante "+name+" no es valido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	private void updateVisualComponents(DataManager data) {		
		listModelParticipants.clear();
		for(String agent: data.getDataManagerParticipant().getParticipantsNames()) {
			listModelParticipants.addElement(agent);
		}
	}
	
	private String canDefineAgentPriority(DataManager data) {
		if(data.getDataManagerParticipant().getParticipants().size()>1) {
			return "OK";
		}else {
			return "Advertencia. Debe definir al menos a dos participante del problema para poder compararlos.";
		}
	}
}
