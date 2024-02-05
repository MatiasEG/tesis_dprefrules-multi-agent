package prefRules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import criteria.Criteria;
import dataManager.DataManager;
import participant.Participant;
import premises.BPremiseFrame;
import premises.EPremiseFrame;
import premises.WPremiseFrame;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.JTextPane;

public class PrefRuleCreationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRuleName;
	private JLabel lblRuleName;
	private JTextPane textPaneDescription;
	
	private JButton btnDeletePremise;
	private JButton btnNewBPremise;
	private JButton btnNewWPremise;
	private JButton btnNewEPremise;
	private JButton btnSaveRuleName;
	private JButton btnSave;
	
	private Rule rule;
	private DefaultListModel<String> listModelRuleContent;
	private JList<String> listRuleConditions;
	private DataManager data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					DataManager data = new DataManager("ruleTest","C:\\Users\\Matia\\Desktop\\tesis_dprefrules-multi-agent\\src\\files");
					data.getDataManagerParticipant().addParticipant(new Participant("Matias"));
					
					Criteria entretenimiento = new Criteria("Entretenimiento", new String[]{"pesimo", "malo", "bueno", "exelente"}, false);
					Criteria clima = new Criteria("Clima", new String[]{"pesimo", "malo", "bueno", "exelente"}, false);
					Criteria costo = new Criteria("Costo", new String[]{"caro", "medio", "normal", "economico"}, false);
					Criteria dias = new Criteria("Dias", new String[]{"1", "30"}, true);
					data.getDataManagerCriteria().addCriteria(entretenimiento);
					data.getDataManagerCriteria().addCriteria(clima);
					data.getDataManagerCriteria().addCriteria(costo);
					data.getDataManagerCriteria().addCriteria(dias);
					
					
					
					PrefRuleCreationFrame frame = new PrefRuleCreationFrame(data, null);
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
	public PrefRuleCreationFrame(DataManager data, Rule rule) {
		this.data = data;
		this.rule = rule;
		
		setTitle("Especificacion de la regla");
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Consideraciones");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("- Toda regla debe tener al menos una condicion en la que se prefiera a la alternativa X por sobre la alternativa Y bajo algun criterio -");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JLabel lblNewLabel_3 = new JLabel("Defina un nombre descriptivo para la regla");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_3);
		
		Dimension textFieldDimension = new Dimension(250, 25);
		textFieldRuleName = new JTextField();
		textFieldRuleName.setPreferredSize(textFieldDimension);
        textFieldRuleName.setMaximumSize(textFieldDimension);
		contentPane.add(textFieldRuleName);
		textFieldRuleName.setColumns(10);
		
		btnSaveRuleName = new JButton("Guardar nombre");
		btnSaveRuleName.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSaveRuleName);
		
		lblRuleName = new JLabel("Nombre establecido:");
		lblRuleName.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblRuleName);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JLabel lblNewLabel_4 = new JLabel("Condiciones");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Prefiero la alternativa X por sobre la alternativa Y si ...");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_5);
		
		JScrollPane scrollPane = new JScrollPane();
		Dimension scrollPaneDimensions = new Dimension(850, 150);
		scrollPane.setPreferredSize(scrollPaneDimensions);
		scrollPane.setMaximumSize(scrollPaneDimensions);
		contentPane.add(scrollPane);
		
		listModelRuleContent = new DefaultListModel<>();
		listRuleConditions = new JList<String>(listModelRuleContent);
		scrollPane.setViewportView(listRuleConditions);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JPanel panelButtons1 = new JPanel();
		contentPane.add(panelButtons1);
		panelButtons1.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNewBPremise = new JButton("Agregar X mejor que Y");
		btnNewBPremise.setEnabled(false);
		panelButtons1.add(btnNewBPremise);
		
		btnNewWPremise = new JButton("Agregar X peor que Y");
		btnNewWPremise.setEnabled(false);
		panelButtons1.add(btnNewWPremise);
		
		btnNewEPremise = new JButton("Agregar X igual a Y");
		btnNewEPremise.setEnabled(false);
		panelButtons1.add(btnNewEPremise);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		Dimension panelButtonsDimensions = new Dimension(800, 25);
		panelButtons1.setPreferredSize(panelButtonsDimensions);
		panelButtons1.setMaximumSize(panelButtonsDimensions);
		
		btnDeletePremise = new JButton("Eliminar condicion");
		panelButtons1.add(btnDeletePremise);
		btnDeletePremise.setEnabled(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1);
		
		textPaneDescription = new JTextPane();
		scrollPane_1.setViewportView(textPaneDescription);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		btnSave = new JButton("Guardar cambios");
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSave);
		
		actionListeners();
		editMode(rule);
	}
	
	private void actionListeners() {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrefRuleCreationFrame.this.data.setDataValidated();
				viewOnlyMod(true);
				JOptionPane.showMessageDialog(null, "Regla guardada correctamente, ya puede cerrar esta ventana.","Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewBPremise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrefRuleCreationFrame.this.viewOnlyMod(true);
				BPremiseFrame frame = new BPremiseFrame(PrefRuleCreationFrame.this.data, PrefRuleCreationFrame.this.rule);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	updateRuleDescription();
		            	PrefRuleCreationFrame.this.viewOnlyMod(false);
		            }
		        });
			}
		});
		btnNewWPremise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WPremiseFrame frame = new WPremiseFrame(PrefRuleCreationFrame.this.data, PrefRuleCreationFrame.this.rule);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	updateRuleDescription();
		            }
		        });
			}
		});
		btnNewEPremise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EPremiseFrame frame = new EPremiseFrame(PrefRuleCreationFrame.this.data, PrefRuleCreationFrame.this.rule);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	updateRuleDescription();
		            }
		        });
			}
		});
		btnDeletePremise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = listRuleConditions.getSelectedIndex();
		        if (selectedIndex != -1) {
		        	int option = JOptionPane.showConfirmDialog(PrefRuleCreationFrame.this,
		                    "¿Seguro que desea eliminar la condicion de la regla seleccionada?",
		                    "Confirmar Eliminación",
		                    JOptionPane.YES_NO_OPTION);
		            if (option == JOptionPane.YES_OPTION) {
		            	String listContent = listRuleConditions.getSelectedValue();
		            	Pattern pattern = Pattern.compile("<([^\\s]+)>");
		                Matcher matcher = pattern.matcher(listContent);
		                if (matcher.find()) {
		                	PrefRuleCreationFrame.this.rule.removeCondition(matcher.group(1));
		                } else {
		                	JOptionPane.showMessageDialog(PrefRuleCreationFrame.this, "Error: No se pudo eliminar el contenido de la regla.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		                updateRuleDescription();
		            }
		        } else {
		            JOptionPane.showMessageDialog(PrefRuleCreationFrame.this, "Debe seleccionar a una condicion de la regla para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnSaveRuleName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String validation = DataManager.validateStringWithOnlyLettersAndNumbers(textFieldRuleName.getText());
				if(validation == null) {
					if(DataManager.validateStringListNotContainNewElement(PrefRuleCreationFrame.this.data.getDataManagerRule().getRuleNames(), textFieldRuleName.getText())) {
						lblRuleName.setText("Nombre establecido: "+textFieldRuleName.getText());
						if(PrefRuleCreationFrame.this.rule==null) {
							PrefRuleCreationFrame.this.rule = new Rule(textFieldRuleName.getText());
							PrefRuleCreationFrame.this.data.getDataManagerRule().addRule(PrefRuleCreationFrame.this.rule);
							btnNewBPremise.setEnabled(true);
							btnNewWPremise.setEnabled(true);
							btnNewEPremise.setEnabled(true);
							btnDeletePremise.setEnabled(true);
						}else {
							PrefRuleCreationFrame.this.rule.setName(textFieldRuleName.getText());
						}
						textFieldRuleName.setText("");
					}else {
						JOptionPane.showMessageDialog(null, "Por favor, revise que el nombre ya que este esta siendo utilizado por otra regla.", "Nombre repetido", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Por favor, revise que el nombre solo contenga letras y/o digitos.", validation, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	public void editMode(Rule rule) {
		if(rule!=null) {
			textFieldRuleName.setText(this.rule.getName());
			lblRuleName.setText("Nombre establecido: "+this.rule.getName());
			updateRuleDescription();
			btnNewBPremise.setEnabled(true);
			btnNewWPremise.setEnabled(true);
			btnNewEPremise.setEnabled(true);
			btnDeletePremise.setEnabled(true);
			btnSave.setEnabled(true);
		}
	}
	
	private void viewOnlyMod(boolean viewOnly) {
		textFieldRuleName.setEnabled(!viewOnly);
		btnDeletePremise.setEnabled(!viewOnly);
		btnNewBPremise.setEnabled(!viewOnly);
		btnNewWPremise.setEnabled(!viewOnly);
		btnNewEPremise.setEnabled(!viewOnly);
		btnSaveRuleName.setEnabled(!viewOnly);
		btnSave.setEnabled(!viewOnly);
	}
	
	private void updateRuleDescription() {
		listModelRuleContent.removeAllElements();
		String description = "Para preferir la alternativa X por sobre la alternativa Y, ";
		
		for(int i=0; i<rule.getBetterP().size(); i++) {
			listModelRuleContent.addElement(rule.getBetterP().get(i).getPremise());
			description +=  rule.getBetterP().get(i).getDescription();
			if(i<rule.getBetterP().size()-1) description += ", ademas ";
		}
		for(int i=0; i<rule.getEqualP().size(); i++) {
			if(i==0) description += ". Por otro lado, ";
			listModelRuleContent.addElement(rule.getEqualP().get(i).getPremise());
			description += rule.getEqualP().get(i).getDescription();
			if(i<rule.getEqualP().size()-1) description += ", tambien ";
		}
		for(int i=0; i<rule.getWorstP().size(); i++) {
			if(i==0) description += ". Por ultimo, estoy dispuesto a sacrificar ";
			listModelRuleContent.addElement(rule.getWorstP().get(i).getPremise());
			description += rule.getWorstP().get(i).getDescription();
			if(i<rule.getWorstP().size()-1) description += ", ademas puedo sacrificar ";
		}
		textPaneDescription.setText(description);
	}

}
