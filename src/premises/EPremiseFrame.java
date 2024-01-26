package premises;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import criteria.Criteria;
import dataManager.DataManager;
import prefRules.Rule;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class EPremiseFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnConfirmCriteria;
	private JComboBox<String> comboBoxAvailableCriterias;
	private JComboBox<String> comboBoxXMinValue;
	private JComboBox<String> comboBoxYMaxValue;
	private DefaultComboBoxModel<String> comboBoxModelXMinValue;
	private DefaultComboBoxModel<String> comboBoxModelYMaxValue;
	private JLabel lblCriteria1;
	private JLabel lblCriteria2;
	private JButton btnValidateDataAndSave;
	private JPanel panelNumeric1;
	private JPanel panelNumeric2;
	private JTextField textFieldXMinValue;
	private JTextField textFieldYMaxValue;
	private JLabel lblSelectedCriteriaValues;
	private JRadioButton rdbtnMinValueX;
	private JRadioButton rdbtnMaxValueY;
	
	private Criteria criteria;
	private DataManager data;
	private Rule rule;
	private boolean minXValueDefined;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EPremiseFrame frame = new EPremiseFrame(null, null);
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
	public EPremiseFrame(DataManager data, Rule rule) {
		this.data = data;
		this.rule = rule;
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_5 = new JLabel("- Seleccione el criterio con el cual usted prefiera a X cuando es igual que Y -");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_5);
		
		// TODO ver si definir las opciones asi esta bien o si debo agregar algun elemento o algo
		comboBoxAvailableCriterias = new JComboBox<String>(rule.getAvailableCriterias(data));
		contentPane.add(comboBoxAvailableCriterias);
		
		JLabel lblNewLabel_6 = new JLabel("- Si el criterio no aparece, es porque ya esta siendo comparado en esta regla -");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_6);
		
		btnConfirmCriteria = new JButton("Confirmar criterio seleccionado");
		btnConfirmCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				criteria = EPremiseFrame.this.data.getCriteria((String)EPremiseFrame.this.comboBoxAvailableCriterias.getSelectedItem());
				if(criteria != null) {
					lblCriteria1.setText("en el criterio "+criteria.getName()+".");
					lblCriteria2.setText("en el criterio "+criteria.getName()+".");
					btnValidateDataAndSave.setEnabled(true);
					btnConfirmCriteria.setEnabled(false);
					
					if(!criteria.isNumeric()) {
						lblSelectedCriteriaValues.setText("["+criteria.getCriteriaValuesString()+"]");
						
						comboBoxXMinValue = new JComboBox<String>(comboBoxModelXMinValue);
						comboBoxXMinValue.setEnabled(false);
						panelNumeric1.add(comboBoxXMinValue);
						
						comboBoxYMaxValue = new JComboBox<String>(comboBoxModelYMaxValue);
						comboBoxYMaxValue.setEnabled(false);
						panelNumeric2.add(comboBoxYMaxValue);
						
						comboBoxModelXMinValue.removeAllElements();
						comboBoxModelYMaxValue.removeAllElements();
						
						comboBoxModelXMinValue.addElement("-");
						comboBoxModelYMaxValue.addElement("-");
						
						for(String value : criteria.getValues()) {
							comboBoxModelXMinValue.addElement(value);
							comboBoxModelYMaxValue.addElement(value);
						}
						
						comboBoxAvailableCriterias.setEnabled(false);
						btnConfirmCriteria.setEnabled(false);
					}else {
						lblSelectedCriteriaValues.setText("between("+criteria.getCriteriaValuesString()+")");
						
						textFieldXMinValue = new JTextField();
						textFieldXMinValue.setText("-");
						textFieldXMinValue.setColumns(10);
						textFieldXMinValue.setEnabled(false);
						panelNumeric1.add(textFieldXMinValue);
						
						textFieldYMaxValue = new JTextField();
						textFieldYMaxValue.setText("-");
						textFieldYMaxValue.setColumns(10);
						textFieldYMaxValue.setEnabled(false);
						panelNumeric2.add(textFieldYMaxValue);
					}
					btnValidateDataAndSave.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(null, "Error: El criterio seleccionado no se encuentra disponible", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnConfirmCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnConfirmCriteria);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_6);
		
		lblSelectedCriteriaValues = new JLabel("");
		lblSelectedCriteriaValues.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblSelectedCriteriaValues);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_4);
		
		JLabel lblNewLabel = new JLabel("- A continuacion definir las condiciones con las que se deben comparar X e Y -");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("La alternativa X debe ser igual que la alternativa Y bajo el criterio seleccionado.");
		panel_1.add(lblNewLabel_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_3);
		
		rdbtnMinValueX = new JRadioButton("");
		panel_3.add(rdbtnMinValueX);
		
		JLabel lblNewLabel_3 = new JLabel("La alternativa X debe valer al menos");
		panel_3.add(lblNewLabel_3);
		
		comboBoxModelXMinValue = new DefaultComboBoxModel<>();
		
		panelNumeric1 = new JPanel();
		panel_3.add(panelNumeric1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_4);
		
		rdbtnMaxValueY = new JRadioButton("");
		panel_4.add(rdbtnMaxValueY);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnMinValueX);
		buttonGroup.add(rdbtnMaxValueY);
		rdbtnMinValueX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!criteria.isNumeric()) {
					comboBoxXMinValue.setEnabled(true);
					comboBoxYMaxValue.setEnabled(false);
				}else {
					textFieldXMinValue.setEnabled(true);
					textFieldYMaxValue.setEnabled(false);
				}
				minXValueDefined = true;
			}
		});
		rdbtnMaxValueY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!criteria.isNumeric()) {
					comboBoxXMinValue.setEnabled(false);
					comboBoxYMaxValue.setEnabled(true);
				}else {
					textFieldXMinValue.setEnabled(false);
					textFieldYMaxValue.setEnabled(true);
				}
				minXValueDefined = false;
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel("La alternativa Y debe valer como maximo");
		panel_4.add(lblNewLabel_4);
		
		comboBoxModelYMaxValue = new DefaultComboBoxModel<>();
		
		panelNumeric2 = new JPanel();
		panel_4.add(panelNumeric2);
		
		Dimension panelDimensions = new Dimension(800, 40);
		comboBoxAvailableCriterias.setPreferredSize(panelDimensions);
		comboBoxAvailableCriterias.setMaximumSize(panelDimensions);
		panel_1.setPreferredSize(panelDimensions);
		panel_1.setMaximumSize(panelDimensions);
		panel_3.setPreferredSize(panelDimensions);
		panel_3.setMaximumSize(panelDimensions);
		
		lblCriteria1 = new JLabel("en el criterio ... .");
		panel_3.add(lblCriteria1);
		panel_4.setPreferredSize(panelDimensions);
		panel_4.setMaximumSize(panelDimensions);
		
		lblCriteria2 = new JLabel("en el criterio ... .");
		panel_4.add(lblCriteria2);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_5);
		
		btnValidateDataAndSave = new JButton("Validar datos y guardar");
		btnValidateDataAndSave.setEnabled(false);
		btnValidateDataAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int minXIndex = -1;
				int maxYIndex = -1;
				
				String valueDefined = "";
				int auxIndexDefined = -1;
				
				if(!criteria.isNumeric()) {
					valueDefined = (String)comboBoxXMinValue.getSelectedItem();
					
					for(int i=0; i<criteria.getValues().length; i++) {
						if(criteria.getValues()[i].equals(valueDefined)) {
							auxIndexDefined = i;
						}
						if(auxIndexDefined!=-1) break;
					}
				}else {
					valueDefined = textFieldXMinValue.getText();
					
					if(!valueDefined.equals("-")) {
						try {
							auxIndexDefined = Integer.parseInt(valueDefined);
							
							if((auxIndexDefined<Integer.parseInt(criteria.getValues()[0]) || auxIndexDefined>Integer.parseInt(criteria.getValues()[1]))) {
								JOptionPane.showMessageDialog(null, "Error: El valor definido no esta dentro del rango permitido para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}
							
						}catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Error: Los valores definidos son incorrectos, solo pueden definirse numeros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
				
				if(minXValueDefined) {
					minXIndex = auxIndexDefined;
				}else {
					maxYIndex = auxIndexDefined;
				}
				saveData(minXIndex, maxYIndex);
			}
		});
		btnValidateDataAndSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnValidateDataAndSave);
	}
	
	private void saveData(int minXIndex, int maxYIndex) {
		EPremise ePremise = new EPremise(criteria);
		ePremise.setMinValueForX(minXIndex);
		ePremise.setMaxValueForY(maxYIndex);
		EPremiseFrame.this.rule.addEqualP(ePremise);
		btnValidateDataAndSave.setEnabled(false);
		JOptionPane.showMessageDialog(null, "Exito: Los datos se han guardado correctamente, ya puede cerrar esta ventana.", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
		disableEdit();
	}
	
	private void disableEdit() {
		comboBoxAvailableCriterias.setEnabled(false);
		rdbtnMinValueX.setEnabled(false);
		rdbtnMaxValueY.setEnabled(false);
		if(!criteria.isNumeric()) {
			comboBoxXMinValue.setEnabled(false);
			comboBoxYMaxValue.setEnabled(false);
		}else {
			textFieldXMinValue.setEnabled(false);
			textFieldYMaxValue.setEnabled(false);
		}
	}
}
