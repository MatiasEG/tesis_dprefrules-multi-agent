package prefRules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import criteria.Criteria;
import dataManager.DataManager;
import dataManager.DataValidations;
import errors.SintacticStringError;
import premises.BPremise;
import premises.BPremiseFrame;

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

public class PrefRuleCreation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRuleName;
	private JLabel lblRuleName;
	
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
					data.addParticipant("Matias");
					
					Criteria entretenimiento = new Criteria("Entretenimiento", new String[]{"pesimo", "malo", "bueno", "exelente"}, false);
					Criteria clima = new Criteria("Clima", new String[]{"pesimo", "malo", "bueno", "exelente"}, false);
					Criteria costo = new Criteria("Costo", new String[]{"caro", "medio", "normal", "economico"}, false);
					Criteria dias = new Criteria("Dias", new String[]{"1", "30"}, true);
					data.addCriteria(entretenimiento);
					data.addCriteria(clima);
					data.addCriteria(costo);
					data.addCriteria(dias);
					
					
					
					PrefRuleCreation frame = new PrefRuleCreation(data);
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
	public PrefRuleCreation(DataManager data) {
		this.data = data;
		rule = null;
		setTitle("Especificacion de la regla");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JLabel lblNewLabel_2 = new JLabel("- Ademas, toda regla debe tener una condicion en la que la alternativa X es peor o igual que la alternativa Y bajo algun criterio -");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_2);
		
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
		
		JButton btnSaveRuleName = new JButton("Guardar nombre");
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
		
		JButton btnNewBPremise = new JButton("Agregar X mejor que Y");
		btnNewBPremise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BPremiseFrame frame = new BPremiseFrame(PrefRuleCreation.this.data, rule);
				frame.setVisible(true);
				
				// WindowListener for detect when the frame is closed
		        frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		            	updateRuleContent();
		            }
		        });
			}
		});
		panelButtons1.add(btnNewBPremise);
		
		JButton btnNewWPremise = new JButton("Agregar X peor que Y");
		panelButtons1.add(btnNewWPremise);
		
		JButton btnNewEPremise = new JButton("Agregar X igual a Y");
		panelButtons1.add(btnNewEPremise);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JPanel panelButtons2 = new JPanel();
		contentPane.add(panelButtons2);
		panelButtons2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnEditPremise = new JButton("Editar condicion");
		panelButtons2.add(btnEditPremise);
		
		JButton btnDeletePremise = new JButton("Eliminar condicion");
		btnDeletePremise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int selectedIndex = listRuleConditions.getSelectedIndex();
			        if (selectedIndex != -1) {
			        	int option = JOptionPane.showConfirmDialog(PrefRuleCreation.this,
			                    "¿Seguro que desea eliminar la condicion de la regla seleccionada?",
			                    "Confirmar Eliminación",
			                    JOptionPane.YES_NO_OPTION);
			            if (option == JOptionPane.YES_OPTION) {
			            	String listContent = listRuleConditions.getSelectedValue();
			            	Pattern pattern = Pattern.compile("-([^\\s]+)-");
			                Matcher matcher = pattern.matcher(listContent);
			                if (matcher.find()) {
			                	rule.removeCondition(matcher.group(1));
			                } else {
			                	JOptionPane.showMessageDialog(PrefRuleCreation.this, "Error: No se pudo eliminar el contenido de la regla.", "Error", JOptionPane.ERROR_MESSAGE);
			                }
			                updateRuleContent();
			            }
			        } else {
			            JOptionPane.showMessageDialog(PrefRuleCreation.this, "Debe seleccionar a un participante para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			}
		});
		panelButtons2.add(btnDeletePremise);
		
		JButton btnViewDescription = new JButton("Descripcion");
		panelButtons2.add(btnViewDescription);
		
		Dimension panelButtonsDimensions = new Dimension(800, 25);
		panelButtons1.setPreferredSize(panelButtonsDimensions);
		panelButtons1.setMaximumSize(panelButtonsDimensions);
		panelButtons2.setPreferredSize(panelButtonsDimensions);
		panelButtons2.setMaximumSize(panelButtonsDimensions);
		
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1);
		
		JTextPane textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);
		
		btnSaveRuleName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SintacticStringError validation = DataValidations.validateStringWithOnlyLettersAndNumbers(textFieldRuleName.getText());
				if(validation == null) {
					lblRuleName.setText("Nombre establecido: "+textFieldRuleName.getText());
					if(rule==null) {
						rule = new Rule(textFieldRuleName.getText());
					}else {
						rule.setName(textFieldRuleName.getText());
					}
					textFieldRuleName.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Por favor, revise que el nombre solo contenga letras y/o digitos.", validation.getMsg(), JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void updateRuleContent() {
		listModelRuleContent.removeAllElements();
		for(BPremise bPremise : rule.getBetterP()) {
			
			listModelRuleContent.addElement(bPremise.getDescription());
		}
	}

}
