package agent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class AgentPriorityFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxBest;
	private JComboBox<String> comboBoxWorst;
    private DefaultListModel<String> listModelParticipantsPriority;
    private JList<String> listPriority;
    private DefaultListModel<String> listModelParticipantsPriorityTransitive;
    private JList<String> listPriorityTransitive;

	private DataManager data;

	/**
	 * Create the frame.
	 */
	public AgentPriorityFrame(DataManager data) {
		this.data = data;
		
		setTitle("Prioridades entre agentes");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Defina la prioridad que hay entre los participantes.");
		panel_3.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_4.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel(" - La prioridad sera tenida en cuenta al momento de determinar cual es la mejor alternativa.");
		panel_4.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel(" - Recuerde que el participante X no puede tener mayor prioridad que X");
		panel_4.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel(" - El participante X no puede tener mayor prioridad que el participante Y y al mismo tiempo menor prioridad que Y");
		panel_4.add(lblNewLabel_5);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_2 = new JLabel("El participante");
		panel_1.add(lblNewLabel_2);
		
		comboBoxBest = new JComboBox<String>(data.getParticipantsArrayString());
		panel_1.add(comboBoxBest);
		
		JLabel lblNewLabel_3 = new JLabel("tiene mayor prioridad que el participante");
		panel_1.add(lblNewLabel_3);
		
		comboBoxWorst = new JComboBox<String>(data.getParticipantsArrayString());
		panel_1.add(comboBoxWorst);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnAddPriority = new JButton("Agregar prioridad");
		btnAddPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int bestIndexBox = comboBoxBest.getSelectedIndex();
				int worstIndexBox = comboBoxWorst.getSelectedIndex();
				
				if(bestIndexBox!=-1 && worstIndexBox!=-1) {
					String bestAgent = (String) comboBoxBest.getSelectedItem();
					String worstAgent = (String) comboBoxWorst.getSelectedItem();
					if(!bestAgent.equals(worstAgent)) {
						AgentPriority pprior = new AgentPriority(bestAgent, worstAgent);
						String validation = pprior.isValid(AgentPriorityFrame.this.data);
						if(validation.equals("OK")) {
							AgentPriorityFrame.this.data.addParticipantsPriority(pprior);
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
		panel_2.add(btnAddPriority, BorderLayout.NORTH);
		
		JButton btnDeletePriority = new JButton("Eliminar prioridad seleccionada");
		btnDeletePriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listPriority.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	AgentPriority participantPriorityToRemove = AgentPriorityFrame.this.data.getParticipantsPriority().get(selectedIndex);
		        	int option = JOptionPane.showConfirmDialog(AgentPriorityFrame.this,
		                    "¿Seguro que desea eliminar la regla seleccionada: ( "+participantPriorityToRemove.getMorePriority()+" > "+participantPriorityToRemove.getLessPriority()+" )?",
		                    "Confirmar Eliminación",
		                    JOptionPane.YES_NO_OPTION);
		            if (option == JOptionPane.YES_OPTION) {
		            	listModelParticipantsPriority.remove(selectedIndex);
		            	AgentPriorityFrame.this.data.getParticipantsPriority().remove(selectedIndex);
		            	updateParticipantsPriorityTransitiveList();
		            }
		        }
			}
		});
		panel_2.add(btnDeletePriority, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		listModelParticipantsPriority = new DefaultListModel<>();
		for(AgentPriority pprior: data.getParticipantsPriority()) {
			listModelParticipantsPriority.addElement("El participante "+pprior.getMorePriority()+" tiene mayor prioridad que el participante "+pprior.getLessPriority());
		}
		listPriority = new JList<String>(listModelParticipantsPriority);
		scrollPane.setViewportView(listPriority);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JPanel panel_5 = new JPanel();
		contentPane.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1);
		
		listModelParticipantsPriorityTransitive = new DefaultListModel<>();
		for(AgentPriority pprior: data.getParticipantsPriorityTransitive()) {
			listModelParticipantsPriorityTransitive.addElement("El participante "+pprior.getMorePriority()+" tiene mayor prioridad que el participante "+pprior.getLessPriority());
		}
		listPriorityTransitive = new JList<String>(listModelParticipantsPriorityTransitive);
		scrollPane_1.setViewportView(listPriorityTransitive);
		
		JLabel lblNewLabel_6 = new JLabel("A continuacion puede ver las relaciones transitivas implicitas.");
		scrollPane_1.setColumnHeaderView(lblNewLabel_6);
	}

	private void updateParticipantsPriorityTransitiveList() {
		data.checkParticipantsPriorityTransitivity();
		
		listModelParticipantsPriorityTransitive = new DefaultListModel<>();
		for(AgentPriority pprior: data.getParticipantsPriorityTransitive()) {
			listModelParticipantsPriorityTransitive.addElement("El participante "+pprior.getMorePriority()+" tiene mayor prioridad que el participante "+pprior.getLessPriority());
		}
		listPriorityTransitive.setModel(listModelParticipantsPriorityTransitive);
	}
}
