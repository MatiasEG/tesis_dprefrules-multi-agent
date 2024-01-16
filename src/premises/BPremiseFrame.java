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
	private JButton btnValidateDataAndSave;
	private JPanel panelNumeric1;
	private JPanel panelNumeric2;
	private JTextField textFieldXMinValue;
	private JTextField textFieldYMaxValue;
	private JLabel lblSelectedCriteriaValues;
	
	private Criteria criteria;
	private DataManager data;
	private Rule rule;

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
		
		// TODO ver si definir las opciones asi esta bien o si debo agregar algun elemento o algo
		comboBoxAvailableCriterias = new JComboBox<String>(rule.getAvailableCriterias(data));
		contentPane.add(comboBoxAvailableCriterias);
		
		JLabel lblNewLabel_6 = new JLabel("- Si el criterio no aparece, es porque ya esta siendo comparado en esta regla -");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_6);
		
		btnConfirmCriteria = new JButton("Confirmar criterio seleccionado");
		btnConfirmCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				criteria = BPremiseFrame.this.data.getCriteria((String)BPremiseFrame.this.comboBoxAvailableCriterias.getSelectedItem());
				if(criteria != null) {
					textFieldMinDist.setEnabled(true);
					lblMinDist.setText("La distancia minima entre los valores posibles para el criterio "+criteria.getName()+" debe ser:");
					lblCriteria1.setText("en el criterio "+criteria.getName()+".");
					lblCriteria2.setText("en el criterio "+criteria.getName()+".");
					
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
		
		btnValidateDataAndSave = new JButton("Validar datos y guardar");
		btnValidateDataAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int minDist = 0;
				String minDistString = textFieldMinDist.getText();
				
				int minXIndex = -1;
				int maxYIndex = -1;
				String minX = "";
				String maxY = "";
				
				if(!criteria.isNumeric()) {
					minX = (String)comboBoxXMinValue.getSelectedItem();
					maxY = (String)comboBoxYMaxValue.getSelectedItem();
					
					for(int i=0; i<criteria.getValues().length; i++) {
						if(criteria.getValues()[i].equals(minX)) {
							minXIndex = i;
						}
						if(criteria.getValues()[i].equals(maxY)) {
							maxYIndex = i;
						}
						if(minXIndex!=-1 && maxYIndex!=-1) break;
					}
				}else {
					minX = textFieldXMinValue.getText();
					maxY = textFieldYMaxValue.getText();
					
					if(!minX.equals("-") && !maxY.equals("-")) {
						try {
							minXIndex = Integer.parseInt(minX);
							maxYIndex = Integer.parseInt(maxY);
							
							if((minXIndex<Integer.parseInt(criteria.getValues()[0]) || minXIndex>Integer.parseInt(criteria.getValues()[1])) 
									|| (maxYIndex<Integer.parseInt(criteria.getValues()[0]) || maxYIndex>Integer.parseInt(criteria.getValues()[1]))) {
								JOptionPane.showMessageDialog(null, "Error: Los valores definidos no estan dentro del rango permitido para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}else if(maxYIndex > minXIndex) {
								JOptionPane.showMessageDialog(null, "Error: El valor maximo de X no puede ser inverior al valor minimo de Y, ya que en este caso estamos evaluando suponiendo que X es mejor que Y.", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}
							
						}catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Error: Los valores definidos son incorrectos, solo pueden definirse numeros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}else if(minX.equals("-") && maxY.equals("-")) {
						// Nothing to do
					}else if(!minX.equals("-") && maxY.equals("-")) {
						try {
							minXIndex = Integer.parseInt(minX);
							
							if(minXIndex<Integer.parseInt(criteria.getValues()[0]) || minXIndex>Integer.parseInt(criteria.getValues()[1])) {
								JOptionPane.showMessageDialog(null, "Error: Los valores definidos no estan dentro del rango permitido para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}
							
						}catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Error: Los valores definidos son incorrectos, solo pueden definirse numeros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}else if(minX.equals("-") && !maxY.equals("-")) {
						try {
							maxYIndex = Integer.parseInt(maxY);
							
							if(maxYIndex<Integer.parseInt(criteria.getValues()[0]) || maxYIndex>Integer.parseInt(criteria.getValues()[1])) {
								JOptionPane.showMessageDialog(null, "Error: Los valores definidos no estan dentro del rango permitido para el criterio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}
							
						}catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Error: Los valores definidos son incorrectos, solo pueden definirse numeros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
				
				if(!minDistString.equals("-")) {
					try {
						minDist = Integer.parseInt(minDistString);
					}catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Error: La distancia minima entre los valores que pueden tomar X e Y en el criterio "+criteria.getName()+" es invalida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(!criteria.isNumeric()) {
						if(minDist>criteria.getValues().length || minDist<0) {
							JOptionPane.showMessageDialog(null, "Error: La distancia minima para los valores de X e Y no es correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}else {
						if(minDist>(Integer.parseInt(criteria.getValues()[1])-Integer.parseInt(criteria.getValues()[0])) || minDist<0) {
							JOptionPane.showMessageDialog(null, "Error: La distancia minima para los valores de X e Y no es correcta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
					if(minDist!=-1) {
						// Defined: minDist & minValueX & maxValueY
						if(minXIndex!=-1 && maxYIndex!=-1) {
							if((minXIndex-maxYIndex)>=minDist) {	// If min(X)<max(Y) this condition will work correctly
								saveData(minDist, minXIndex, maxYIndex);
							}else {
								JOptionPane.showMessageDialog(null, "Error: Los valores min(X)="+minX+" y max(Y)="+maxY+" en el criterio "+criteria.getName()+" no respetan la minima distancia indicada (minDist = "+minDist+").", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}
						// Defined: minDist
						}else if(minXIndex==-1 && maxYIndex==-1) {
							saveData(minDist, minXIndex, maxYIndex);
						// Defined: minDist & minValueX
						}else if(minXIndex!=-1 && maxYIndex==-1) {
							if(minXIndex > minDist) {
								saveData(minDist, minXIndex, maxYIndex);
							}else {
								JOptionPane.showMessageDialog(null, "Error: El valor min(X)="+minX+" en el criterio "+criteria.getName()+" no respetan la minima distancia indicada (minDist = "+minDist+").", "Advertencia", JOptionPane.WARNING_MESSAGE);
								return;
							}
						// Defined: minDist & maxValueY
						}else if(minXIndex==-1 && maxYIndex!=-1) {
							if(!criteria.isNumeric()) {
								if((criteria.getValues().length-maxYIndex) > minDist) {
									saveData(minDist, minXIndex, maxYIndex);
								}else {
									JOptionPane.showMessageDialog(null, "Error: El valor max(Y)="+maxY+" en el criterio "+criteria.getName()+" no respetan la minima distancia indicada (minDist = "+minDist+").", "Advertencia", JOptionPane.WARNING_MESSAGE);
									return;
								}
							}else {
								if(Integer.parseInt(criteria.getValues()[1])-maxYIndex > minDist) {
									saveData(minDist, minXIndex, maxYIndex);
								}else {
									JOptionPane.showMessageDialog(null, "Error: El valor max(Y)="+maxY+" en el criterio "+criteria.getName()+" no respetan la minima distancia indicada (minDist = "+minDist+").", "Advertencia", JOptionPane.WARNING_MESSAGE);
									return;
								}
							}
						}else {
							JOptionPane.showMessageDialog(null, "Error: Situacion inesperada, no se puede proceder con el proceso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}else {
						JOptionPane.showMessageDialog(null, "Error: El valor asignado a distancia minima entre X e Y es invalido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}else if(minDistString.equals("-")) {
					if(minXIndex!=-1 && maxYIndex!=-1) {
						// TODO si minX = maxY no seria como una premisa equals?
						if(minXIndex<maxYIndex) {
							JOptionPane.showMessageDialog(null, "Error: El valor para X en en el criterio evaluado no puede ser menor que el valor de Y en este caso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					saveData(minDist, minXIndex, maxYIndex);
				}
			}
		});
		btnValidateDataAndSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnValidateDataAndSave);
	}
	
	private void saveData(int minDist, int minXIndex, int maxYIndex) {
		BPremise bPremise = new BPremise(criteria);
		bPremise.setMinDist(minDist);
		bPremise.setMinValueForX(minXIndex);
		bPremise.setMaxValueForY(maxYIndex);
		BPremiseFrame.this.rule.addBetterP(bPremise);
		btnValidateDataAndSave.setEnabled(false);
		JOptionPane.showMessageDialog(null, "Exito: Los datos se han guardado correctamente, ya puede cerrar esta ventana.", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
	}
}
