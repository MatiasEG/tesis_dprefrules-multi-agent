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

public class BPremiseFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnConfirmCriteria;
	private JComboBox<String> comboBoxAvailableCriterias;
	private JTextField textFieldMinDist;
	private JLabel lblMinDist;
	private JLabel lblMinDistMaxValue;
	private JComboBox<String> comboBoxXMinValue;
	private JComboBox<String> comboBoxYMaxValue;
	private DefaultComboBoxModel<String> comboBoxModelXMinValue;
	private DefaultComboBoxModel<String> comboBoxModelYMaxValue;
	private JLabel lblCriteria1;
	private JLabel lblCriteria2;
	private JButton btnSave;
	private JPanel panelNumeric1;
	private JPanel panelNumeric2;
	private JTextField textFieldXMinValue;
	private JTextField textFieldYMaxValue;
	private JLabel lblSelectedCriteriaValues;
	
	private Criteria criteria;
	private DataManager data;
	private Rule rule;
	private BPremise bPremise;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BPremiseFrame frame = new BPremiseFrame(null, null);
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
	public BPremiseFrame(DataManager data, Rule rule) {
		this.data = data;
		this.rule = rule;
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_5 = new JLabel("- Seleccione el criterio con el cual usted prefiera a X cuando es mejor que Y -");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_5);
		
		comboBoxAvailableCriterias = new JComboBox<String>(rule.getAvailableCriterias(data));
		contentPane.add(comboBoxAvailableCriterias);
		
		JLabel lblNewLabel_6 = new JLabel("- Si el criterio no aparece, es porque ya esta siendo comparado en esta regla -");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_6);
		
		btnConfirmCriteria = new JButton("Confirmar criterio seleccionado");
		btnConfirmCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				criteria = BPremiseFrame.this.data.getDataManagerCriteria().getCriteria((String)BPremiseFrame.this.comboBoxAvailableCriterias.getSelectedItem());
				if(criteria != null) {
					textFieldMinDist.setEnabled(true);
					lblMinDist.setText("La distancia minima entre los valores posibles para el criterio "+criteria.getName()+" debe ser:");
					lblCriteria1.setText("en el criterio "+criteria.getName()+".");
					lblCriteria2.setText("en el criterio "+criteria.getName()+".");
					btnSave.setEnabled(true);
					btnConfirmCriteria.setEnabled(false);
					bPremise = new BPremise(criteria);
					
					if(!criteria.isNumeric()) {
						lblSelectedCriteriaValues.setText("["+criteria.getCriteriaValuesString()+"]");

						lblMinDistMaxValue.setText("(Valor maximo: "+criteria.getValues().length+")");
						
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
						
						comboBoxXMinValue.setEnabled(true);
						comboBoxYMaxValue.setEnabled(true);
						
						comboBoxAvailableCriterias.setEnabled(false);
						btnConfirmCriteria.setEnabled(false);
					}else {
						lblSelectedCriteriaValues.setText("between("+criteria.getCriteriaValuesString()+")");

						lblMinDistMaxValue.setText("(Valor maximo: "+(Integer.parseInt(criteria.getValues()[1])-Integer.parseInt(criteria.getValues()[0]))+")");
						
						textFieldXMinValue = new JTextField();
						textFieldXMinValue.setText("-");
						textFieldXMinValue.setColumns(10);
						panelNumeric1.add(textFieldXMinValue);
						
						textFieldYMaxValue = new JTextField();
						textFieldYMaxValue.setText("-");
						textFieldYMaxValue.setColumns(10);
						panelNumeric2.add(textFieldYMaxValue);
					}
					btnSave.setEnabled(true);
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
		
		JLabel lblNewLabel_1 = new JLabel("La alternativa X debe ser mejor que la alternativa Y bajo el criterio seleccionado.");
		panel_1.add(lblNewLabel_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblMinDist = new JLabel("La distancia minima entre los valores posibles para el criterio ... debe ser:");
		panel_2.add(lblMinDist);
		
		textFieldMinDist = new JTextField();
		textFieldMinDist.setText("-");
		textFieldMinDist.setEnabled(false);
		panel_2.add(textFieldMinDist);
		textFieldMinDist.setColumns(10);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_3);
		
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
		panel_2.setPreferredSize(panelDimensions);
		panel_2.setMaximumSize(panelDimensions);
		
		lblMinDistMaxValue = new JLabel("(Valor maximo: ...)");
		panel_2.add(lblMinDistMaxValue);
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
		
		btnSave = new JButton("Guardar cambios");
		btnSave.setEnabled(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valid = true;
				
				String minDistString = textFieldMinDist.getText();
				if(bPremise.validMinDistValue(minDistString)) {
					bPremise.setMinDist(minDistString);
				}else {
					JOptionPane.showMessageDialog(null, "Error: La distancia minima entre X e Y ingresada no es valida.", "Advertencia", JOptionPane.ERROR_MESSAGE);
					valid = false;
				}
				
				String minX = "";
				String maxY = "";
				
				if(!criteria.isNumeric()) {
					minX = (String)comboBoxXMinValue.getSelectedItem();
					maxY = (String)comboBoxYMaxValue.getSelectedItem();
				}else {
					minX = textFieldXMinValue.getText();
					maxY = textFieldYMaxValue.getText();
				}
				
				if(bPremise.validMinXValue(minX)) {
					bPremise.setMinValueForX(minX);
				}else {
					JOptionPane.showMessageDialog(null, "Error: El valor minimo para X ingresado no es valido.", "Advertencia", JOptionPane.ERROR_MESSAGE);
					valid = false;
				}
				
				if(bPremise.validMaxYValue(maxY)) {
					bPremise.setMaxValueForY(maxY);
				}else {
					JOptionPane.showMessageDialog(null, "Error: El valor maximo para Y ingresado no es valido.", "Advertencia", JOptionPane.ERROR_MESSAGE);
					valid = false;
				}
				
				if(valid) {
					saveData();
				}
			}
		});
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnSave);
	}
	
	private void saveData() {
		BPremiseFrame.this.rule.addBetterP(bPremise);
		btnSave.setEnabled(false);
		JOptionPane.showMessageDialog(null, "Exito: Los datos se han guardado correctamente, ya puede cerrar esta ventana.", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
		disableEdit();
	}
	
	private void disableEdit() {
		comboBoxAvailableCriterias.setEnabled(false);
		textFieldMinDist.setEnabled(false);
		if(!criteria.isNumeric()) {
			comboBoxXMinValue.setEnabled(false);
			comboBoxYMaxValue.setEnabled(false);
		}else {
			textFieldXMinValue.setEnabled(false);
			textFieldYMaxValue.setEnabled(false);
		}
	}
}
