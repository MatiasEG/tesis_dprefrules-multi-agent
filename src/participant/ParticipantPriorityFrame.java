package participant;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;
import dataManager.Priority;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ParticipantPriorityFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxBest;
	private JComboBox<String> comboBoxWorst;
    private DefaultListModel<String> listModelParticipantsPriority;
    private JList<String> listPriority;
    private DefaultListModel<String> listModelParticipantsPriorityTransitive;
    private JList<String> listPriorityTransitive;
    
    private JButton btnAddPriority;
    private JButton btnDeletePriority;
    private JButton btnSave;

	private DataManager data;

	/**
	 * Create the frame.
	 */
	public ParticipantPriorityFrame(DataManager data, boolean viewOnly) {
		this.data = data;
		
		setTitle("Prioridades entre agentes");
		setBounds(100, 100, 699, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Defina la prioridad que hay entre los participantes");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel(" - La prioridad sera tenida en cuenta al momento de determinar cual es la mejor alternativa -");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel(" - Recuerde que el participante X no puede tener mayor prioridad que X -");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel(" - El participante X no puede tener mayor prioridad que el participante Y y al mismo tiempo menor prioridad que Y -");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_5);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JPanel panelPriorityConfiguration = new JPanel();
		contentPane.add(panelPriorityConfiguration);
		panelPriorityConfiguration.setLayout(new BoxLayout(panelPriorityConfiguration, BoxLayout.X_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JLabel lblNewLabel_2 = new JLabel("El participante");
		panelPriorityConfiguration.add(lblNewLabel_2);
		
		comboBoxBest = new JComboBox<String>(data.getDataManagerParticipant().getParticipantsArrayString());
		panelPriorityConfiguration.add(comboBoxBest);
		
		JLabel lblNewLabel_3 = new JLabel("tiene mayor prioridad que el participante");
		panelPriorityConfiguration.add(lblNewLabel_3);
		
		comboBoxWorst = new JComboBox<String>(data.getDataManagerParticipant().getParticipantsArrayString());
		panelPriorityConfiguration.add(comboBoxWorst);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		listModelParticipantsPriority = new DefaultListModel<>();
		listPriority = new JList<String>(listModelParticipantsPriority);
		scrollPane.setViewportView(listPriority);
		
		btnDeletePriority = new JButton("Eliminar prioridad seleccionada");
		btnDeletePriority.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		JPanel panelButtons = new JPanel();
		contentPane.add(panelButtons);
		panelButtons.add(btnDeletePriority);
		
		btnAddPriority = new JButton("Agregar prioridad");
		panelButtons.add(btnAddPriority);
		btnAddPriority.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1);
		
		listModelParticipantsPriorityTransitive = new DefaultListModel<>();
		listPriorityTransitive = new JList<String>(listModelParticipantsPriorityTransitive);
		scrollPane_1.setViewportView(listPriorityTransitive);
		
		JLabel lblNewLabel_6 = new JLabel("A continuacion puede ver las relaciones transitivas implicitas.");
		scrollPane_1.setColumnHeaderView(lblNewLabel_6);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_5);
		
		btnSave = new JButton("Guardar cambios");
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSave);
		
		updatePriorityLists();
		updateParticipantsPriorityTransitiveList();
		actionListeners();
		viewOnlyMod(viewOnly);
	}

	private void updatePriorityLists() {
		for(Priority prior: data.getDataManagerParticipant().getParticipantsPriority()) {
			listModelParticipantsPriority.addElement("El participante "+prior.getMorePriority()+" tiene mayor prioridad que el participante "+prior.getLessPriority());
		}
		
		for(Priority prior: data.getDataManagerParticipant().getParticipantsPriorityTransitive()) {
			listModelParticipantsPriorityTransitive.addElement("El participante "+prior.getMorePriority()+" tiene mayor prioridad que el participante "+prior.getLessPriority());
		}
	}
	
	private void actionListeners() {
		btnAddPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int bestIndexBox = comboBoxBest.getSelectedIndex();
				int worstIndexBox = comboBoxWorst.getSelectedIndex();
				
				if(bestIndexBox!=-1 && worstIndexBox!=-1) {
					String bestAgent = (String) comboBoxBest.getSelectedItem();
					String worstAgent = (String) comboBoxWorst.getSelectedItem();
					if(!bestAgent.equals(worstAgent)) {
						Priority pprior = new Priority(bestAgent, worstAgent);
						String validation = pprior.isValid(ParticipantPriorityFrame.this.data);
						if(validation.equals("OK")) {
							ParticipantPriorityFrame.this.data.getDataManagerParticipant().addParticipantsPriority(pprior);
							listModelParticipantsPriority.addElement("El participante "+bestAgent+" tiene mayor prioridad que el participante "+worstAgent);
							updateParticipantsPriorityTransitiveList();
						}else {
							JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Error, una preferencia entre participantes no puede ser con el mismo participante.", "Advertencia (X>X)", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnDeletePriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listPriority.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	Priority participantPriorityToRemove = ParticipantPriorityFrame.this.data.getDataManagerParticipant().getParticipantsPriority().get(selectedIndex);
		        	int option = JOptionPane.showConfirmDialog(ParticipantPriorityFrame.this,
		                    "¿Seguro que desea eliminar la regla seleccionada: ( "+participantPriorityToRemove.getMorePriority()+" > "+participantPriorityToRemove.getLessPriority()+" )?",
		                    "Confirmar Eliminación",
		                    JOptionPane.YES_NO_OPTION);
		            if (option == JOptionPane.YES_OPTION) {
		            	listModelParticipantsPriority.remove(selectedIndex);
		            	ParticipantPriorityFrame.this.data.getDataManagerParticipant().getParticipantsPriority().remove(selectedIndex);
		            	updateParticipantsPriorityTransitiveList();
		            }
		        }
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
				ParticipantPriorityFrame.this.data.setDataValidated();
				viewOnlyMod(true);
			}
		});
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		if(viewOnly) {
			comboBoxBest.setEnabled(false);
			comboBoxWorst.setEnabled(false);
			btnAddPriority.setEnabled(false);
			btnDeletePriority.setEnabled(false);
			btnSave.setEnabled(false);
		}
	}
	
	private void updateParticipantsPriorityTransitiveList() {
		data.getDataManagerParticipant().checkParticipantsPriorityTransitivity();
		
		listModelParticipantsPriorityTransitive = new DefaultListModel<>();
		for(Priority prior: data.getDataManagerParticipant().getParticipantsPriorityTransitive()) {
			listModelParticipantsPriorityTransitive.addElement("El participante "+prior.getMorePriority()+" tiene mayor prioridad que el participante "+prior.getLessPriority());
		}
		listPriorityTransitive.setModel(listModelParticipantsPriorityTransitive);
	}
}
