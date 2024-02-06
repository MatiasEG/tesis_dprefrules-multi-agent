package prefRules;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IOmanager.CSVreader;
import IOmanager.CSVwriter;
import IOmanager.FileChooser;
import dataManager.DataManager;
import dataManager.Priority;
import exceptions.RulePriorityException;
import participant.Participant;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class PrefRulePreferencesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private DefaultListModel<String> listModelRulePriority;
    private JList<String> listPriority;
    private DefaultListModel<String> listModelParticipantsPriorityTransitive;
    private JList<String> listPriorityTransitive;
    
    private JComboBox<String> comboBoxParticipant;
	private JComboBox<String> comboBoxBest;
	private JComboBox<String> comboBoxWorst;
	
    private JButton btnLoadRulePreferencesFromFiles;
    private JButton btnSaveRulePreferenes;
    private JButton btnAddPriority;
    private JButton btnDeletePriority;
    private JButton btnSave;

	private DataManager data;

	/**
	 * Create the frame.
	 */
	public PrefRulePreferencesFrame(DataManager data, boolean viewOnly) {
		this.data = data;
		
		setTitle("Prioridades entre agentes");
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_7 = new JLabel("Defina la prioridad que hay entre las reglas para cada participante");
		lblNewLabel_7.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_7);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel(" - La prioridad sera tenida en cuenta al momento de determinar cual es la mejor alternativa -");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel(" - Recuerde que la regla X no puede tener mayor prioridad que X -");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel(" - La regla X no puede tener mayor prioridad que la regla Y y al mismo tiempo menor prioridad que la regla Y -");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_5);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_5);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		btnLoadRulePreferencesFromFiles = new JButton("Cargar archivo de preferencias");
		panel.add(btnLoadRulePreferencesFromFiles);
		btnLoadRulePreferencesFromFiles.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnSaveRulePreferenes = new JButton("Guardar archivo de preferencias");
		panel.add(btnSaveRulePreferenes);
		btnSaveRulePreferenes.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panelPreference = new JPanel();
		contentPane.add(panelPreference);
		panelPreference.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_2 = new JLabel("El participante");
		panelPreference.add(lblNewLabel_2);
		
		comboBoxParticipant = new JComboBox<String>(data.getDataManagerParticipant().getParticipantsArrayString());
		panelPreference.add(comboBoxParticipant);
		
		JLabel lblNewLabel_3 = new JLabel("prefiere la regla");
		panelPreference.add(lblNewLabel_3);
		
		comboBoxBest = new JComboBox<String>(data.getDataManagerRule().getRulesNames());
		panelPreference.add(comboBoxBest);
		
		JLabel lblNewLabel = new JLabel("por sobre la regla");
		panelPreference.add(lblNewLabel);
		
		comboBoxWorst = new JComboBox<String>(data.getDataManagerRule().getRulesNames());
		panelPreference.add(comboBoxWorst);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		btnAddPriority = new JButton("Agregar prioridad");
		btnAddPriority.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnAddPriority, BorderLayout.NORTH);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		listModelRulePriority = new DefaultListModel<>();
		listPriority = new JList<String>(listModelRulePriority);
		scrollPane.setViewportView(listPriority);
		
		btnDeletePriority = new JButton("Eliminar prioridad seleccionada");
		btnDeletePriority.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnDeletePriority, BorderLayout.SOUTH);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1);
		listModelParticipantsPriorityTransitive = new DefaultListModel<>();
		listPriorityTransitive = new JList<String>(listModelParticipantsPriorityTransitive);
		scrollPane_1.setViewportView(listPriorityTransitive);
		
		JLabel lblNewLabel_6 = new JLabel("A continuacion puede ver las relaciones transitivas implicitas.");
		scrollPane_1.setColumnHeaderView(lblNewLabel_6);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_6);
		
		btnSave = new JButton("Guardar cambios");
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSave);
		
		updateIndexRulePriority();
		updateRulePriorityTransitiveList();
		actionListeners();
		viewOnlyMod(viewOnly);
	}

	private void actionListeners() {
		btnAddPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int participant = comboBoxParticipant.getSelectedIndex();
				int bestIndexBox = comboBoxBest.getSelectedIndex();
				int worstIndexBox = comboBoxWorst.getSelectedIndex();
				
				if(participant!=-1 && bestIndexBox!=-1 && worstIndexBox!=-1) {
					String participantName = (String)comboBoxParticipant.getSelectedItem();
					String bestRule = (String) comboBoxBest.getSelectedItem();
					String worstRule = (String) comboBoxWorst.getSelectedItem();
					if(!bestRule.equals(worstRule)) {
						Priority prior = new Priority(bestRule, worstRule);
						String validation = prior.isValid(PrefRulePreferencesFrame.this.data.getDataManagerParticipant().getParticipantByName(participantName));
						if(validation.equals("OK")) {
							PrefRulePreferencesFrame.this.data.getDataManagerParticipant().getParticipantByName(participantName).addPreference(prior);
							int index = PrefRulePreferencesFrame.this.data.getDataManagerParticipant().getParticipantByName(participantName).getPreferences().size()-1;
							listModelRulePriority.addElement("El participante <"+participantName+"("+index+")> considera que la regla "+bestRule+" tiene mayor prioridad que la regla "+worstRule);
							updateRulePriorityTransitiveList();
						}else {
							JOptionPane.showMessageDialog(null, validation, "Advertencia", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Error, no se puede realizar una comparacion con la misma regla.", "Advertencia (X>X)", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnDeletePriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listPriority.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	String[] participantAndIndex =  extractNameAndPosition(listPriority.getSelectedValue());
		        	Participant participant = PrefRulePreferencesFrame.this.data.getDataManagerParticipant().getParticipantByName(participantAndIndex[0]);
		        	Priority prior = participant.getPreferences().get(Integer.parseInt(participantAndIndex[1]));
		        	
		        	int option = JOptionPane.showConfirmDialog(PrefRulePreferencesFrame.this,
		                    "¿Seguro que desea eliminar la regla seleccionada: ( "+prior.getMorePriority()+" > "+prior.getLessPriority()+" ) definida por "+participant.getName()+"?",
		                    "Confirmar Eliminación",
		                    JOptionPane.YES_NO_OPTION);
		            if (option == JOptionPane.YES_OPTION) {
		            	listModelRulePriority.remove(selectedIndex);
		            	participant.getPreferences().remove(Integer.parseInt(participantAndIndex[1]));
		            	updateIndexRulePriority();
		            	updateRulePriorityTransitiveList();
		            }
		        }
			}
		});
		btnLoadRulePreferencesFromFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = FileChooser.showFileChooser();
				try {
					CSVreader.readRulePriorityCSV(path, PrefRulePreferencesFrame.this.data);
					PrefRulePreferencesFrame.this.updateIndexRulePriority();
					PrefRulePreferencesFrame.this.updateRulePriorityTransitiveList();;
				} catch (RulePriorityException e1) {
	    			JOptionPane.showMessageDialog(null, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
				PrefRulePreferencesFrame.this.data.setDataValidated();
				viewOnlyMod(true);
			}
		});
		btnSaveRulePreferenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CSVwriter.saveRulePriorityToCSV(PrefRulePreferencesFrame.this.data);
				JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		if(viewOnly) {
			comboBoxParticipant.setEnabled(false);
			comboBoxBest.setEnabled(false);
			comboBoxWorst.setEnabled(false);
			
		    btnLoadRulePreferencesFromFiles.setEnabled(false);
		    btnSaveRulePreferenes.setEnabled(false);
		    btnAddPriority.setEnabled(false);
		    btnDeletePriority.setEnabled(false);
		}
	}
	
	private void updateIndexRulePriority() {
		listModelRulePriority.removeAllElements();
		for(int i=0; i<data.getDataManagerParticipant().getParticipants().size(); i++) {
			Participant participant = data.getDataManagerParticipant().getParticipants().get(i);
			for(int index=0; index<participant.getPreferences().size(); index++) {
				listModelRulePriority.addElement("El participante <"+participant.getName()+"("+index+")> considera que la regla "+participant.getPreferences().get(index).getMorePriority()+" tiene mayor prioridad que la regla "+participant.getPreferences().get(index).getLessPriority());
			}
		}
	}
	
	private void updateRulePriorityTransitiveList() {
		listModelParticipantsPriorityTransitive.removeAllElements();
		for(Participant participant : data.getDataManagerParticipant().getParticipants()) {
			participant.checkRulePriorityTransitivity();
			for(Priority prior : participant.getPreferencesTransitive()) {
				listModelParticipantsPriorityTransitive.addElement("El participante "+participant.getName()+" considera que la regla "+prior.getMorePriority()+" tiene mayor prioridad que la regla "+prior.getLessPriority());
			}
		}
	}
	
	private static String[] extractNameAndPosition(String texto) {
        // Find the content between '<' y '>'
        Pattern mainPattern = Pattern.compile("<(.*?)>");
        Matcher mainMatcher = mainPattern.matcher(texto);

        // Verify if there is a match
        if (mainMatcher.find()) {
            // Get the content between '<' y '>'
            String fullString = mainMatcher.group(1);

            // Find name and position
            Pattern secondaryPattern = Pattern.compile("(\\w+)\\((\\d+)\\)");
            Matcher secondaryMatcher = secondaryPattern.matcher(fullString);

         // Verify if there is a match
            if (secondaryMatcher.find()) {
                // Get name and position
                String name = secondaryMatcher.group(1);
                String position = secondaryMatcher.group(2);

                // Values to return
                return new String[]{name, position};
            }
        }

        // If there is no match, return empty value
        return new String[]{"", ""};
    }
}
