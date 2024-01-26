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

	private DataManager data;

	/**
	 * Create the frame.
	 */
	public PrefRulePreferencesFrame(DataManager data, boolean onlyView) {
		this.data = data;
		
		setTitle("Prioridades entre agentes");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
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
		
		btnLoadRulePreferencesFromFiles = new JButton("Cargar archivo de preferencias");
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
		btnLoadRulePreferencesFromFiles.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnLoadRulePreferencesFromFiles);
		
		btnSaveRulePreferenes = new JButton("Guardar archivo de preferencias");
		btnSaveRulePreferenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CSVwriter.saveRulePriorityToCSV(PrefRulePreferencesFrame.this.data);
				JOptionPane.showMessageDialog(null, "Datos validados y guardados, ya puede cerrar esta ventana", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnSaveRulePreferenes.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSaveRulePreferenes);
		
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
		
		comboBoxBest = new JComboBox<String>(data.getRulesNames());
		panelPreference.add(comboBoxBest);
		
		JLabel lblNewLabel = new JLabel("por sobre la regla");
		panelPreference.add(lblNewLabel);
		
		comboBoxWorst = new JComboBox<String>(data.getRulesNames());
		panelPreference.add(comboBoxWorst);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		btnAddPriority = new JButton("Agregar prioridad");
		btnAddPriority.setAlignmentX(Component.CENTER_ALIGNMENT);
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
		btnDeletePriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listPriority.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	String[] participantAndIndex =  extraerDatos(listPriority.getSelectedValue());
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
		
		updateIndexRulePriority();
		updateRulePriorityTransitiveList();
		onlyViewMod(onlyView);
	}

	private void onlyViewMod(boolean onlyView) {
		if(onlyView) {
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
	
	private static String[] extraerDatos(String texto) {
        // Utilizar expresiones regulares para encontrar la cadena entre '<' y '>'
        Pattern patronPrincipal = Pattern.compile("<(.*?)>");
        Matcher matcherPrincipal = patronPrincipal.matcher(texto);

        // Verificar si hay una coincidencia principal
        if (matcherPrincipal.find()) {
            // Obtener la cadena entre '<' y '>'
            String cadenaCompleta = matcherPrincipal.group(1);

            // Utilizar expresiones regulares para encontrar el nombre y el número
            Pattern patronSecundario = Pattern.compile("(\\w+)\\((\\d+)\\)");
            Matcher matcherSecundario = patronSecundario.matcher(cadenaCompleta);

            // Verificar si hay una coincidencia secundaria
            if (matcherSecundario.find()) {
                // Obtener el nombre y el número
                String nombre = matcherSecundario.group(1);
                String numero = matcherSecundario.group(2);

                // Devolver los resultados en un array de strings
                return new String[]{nombre, numero};
            }
        }

        // Devolver un array vacío si no se encontraron coincidencias
        return new String[]{"", ""};
    }
}
