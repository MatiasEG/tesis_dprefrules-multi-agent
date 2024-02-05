package criteria;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;

public class CriteriaCreationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCriteriaName;
	private JTextField textFieldNumericValue1;
	private JTextField textFieldNumericValue2;
	private JTextField textFieldSimbolicValues;
	private boolean isNumericEnabled;
	private JRadioButton rdbtnNumericValues;
	private JRadioButton rdbtnCategoricValues;
	
	private JButton btnAcept;
	
	private CriteriaFrame criteriaFrame;
	private Criteria criteriaToUpdate;
	private DataManager data;

	/**
	 * Create the frame.
	 */
	public CriteriaCreationFrame(CriteriaFrame criteriaTable, DataManager data, Criteria criteria) {
		this.data = data;
		criteriaFrame = criteriaTable;
		criteriaToUpdate = criteria;
		
		setTitle("Nuevo criterio");
		setBounds(100, 100, 550, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_0 = new JLabel("Ingrese el nombre del criterio (recuerde no repetir)");
		lblNewLabel_0.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_0);
		
		textFieldCriteriaName = new JTextField();
		contentPane.add(textFieldCriteriaName);
		textFieldCriteriaName.setColumns(10);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JLabel lblNewLabel_6 = new JLabel("Seleccione de que tipo va a ser el nuevo criterio en base a sus valores");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_6);
		
		JPanel panelTypeValues = new JPanel();
		FlowLayout f2_panelButtons = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panelTypeValues.setLayout(f2_panelButtons);
		contentPane.add(panelTypeValues);
		
		rdbtnNumericValues = new JRadioButton("Criterio numerico");
		panelTypeValues.add(rdbtnNumericValues);
		
		rdbtnCategoricValues = new JRadioButton("Criterio categorico");
		panelTypeValues.add(rdbtnCategoricValues);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnNumericValues);
		buttonGroup.add(rdbtnCategoricValues);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese el rango de valores del nuevo criterio");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblElCriterioNumerico = new JLabel("El criterio numerico puede valer:");
		lblElCriterioNumerico.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblElCriterioNumerico);
		
		JPanel panelNumericValues = new JPanel();
		FlowLayout fl_panelButtons = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panelNumericValues.setLayout(fl_panelButtons);
		contentPane.add(panelNumericValues);
		
		JLabel lblNewLabel = new JLabel("entre (");
		panelNumericValues.add(lblNewLabel);
		
		textFieldNumericValue1 = new JFormattedTextField();
		textFieldNumericValue1.setEnabled(false);
		panelNumericValues.add(textFieldNumericValue1);
		textFieldNumericValue1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("-");
		panelNumericValues.add(lblNewLabel_2);
		
		textFieldNumericValue2 = new JFormattedTextField();
		textFieldNumericValue2.setEnabled(false);
		panelNumericValues.add(textFieldNumericValue2);
		textFieldNumericValue2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(")");
		panelNumericValues.add(lblNewLabel_3);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_3);
		
		JLabel lblNewLabel_4_1 = new JLabel("El criterio categorico puede tomar los valores");
		lblNewLabel_4_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_4_1);
		
		JPanel panelCategoricalValues = new JPanel();
		contentPane.add(panelCategoricalValues);
		panelCategoricalValues.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_4 = new JLabel("[");
		panelCategoricalValues.add(lblNewLabel_4);
		
		textFieldSimbolicValues = new JTextField();
		textFieldSimbolicValues.setEnabled(false);
		panelCategoricalValues.add(textFieldSimbolicValues);
		
		JLabel lblNewLabel_5 = new JLabel("]");
		panelCategoricalValues.add(lblNewLabel_5);
		
		btnAcept = new JButton("Aceptar y Guardar criterio");
		btnAcept.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		contentPane.add(btnAcept);
		
		Dimension panelDimensions = new Dimension(550, 30);
		panelCategoricalValues.setPreferredSize(panelDimensions);
		panelCategoricalValues.setMaximumSize(panelDimensions);
		panelNumericValues.setPreferredSize(panelDimensions);
		panelNumericValues.setMaximumSize(panelDimensions);
		panelTypeValues.setPreferredSize(panelDimensions);
		panelTypeValues.setMaximumSize(panelDimensions);
		
		Dimension textFieldCriteriaNameDimensions = new Dimension(325, 20);
		textFieldCriteriaName.setPreferredSize(textFieldCriteriaNameDimensions);
		textFieldCriteriaName.setMaximumSize(textFieldCriteriaNameDimensions);
		textFieldSimbolicValues.setPreferredSize(textFieldCriteriaNameDimensions);
		textFieldSimbolicValues.setMaximumSize(textFieldCriteriaNameDimensions);
		
		actionListeners();
		editMode(criteria);
	}
	
	private void actionListeners() {
		rdbtnNumericValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumericValue1.setEnabled(true);
				textFieldNumericValue2.setEnabled(true);
				isNumericEnabled = true;
				textFieldSimbolicValues.setEnabled(false);
			}
		});
		rdbtnCategoricValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumericValue1.setEnabled(false);
				textFieldNumericValue2.setEnabled(false);
				isNumericEnabled = false;
				textFieldSimbolicValues.setEnabled(true);
			}
		});
		btnAcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((criteriaToUpdate!=null && criteriaToUpdate.getName().equals(textFieldCriteriaName.getText())) || CriteriaCreationFrame.this.data.getDataManagerCriteria().validCriteriaName(textFieldCriteriaName.getText())) {
					if(!isNumericEnabled && !textFieldSimbolicValues.getText().equals("")) {
						String[] simbolicSplittedValues = textFieldSimbolicValues.getText().trim().split("\\s*,\\s*");
						
						if(CriteriaCreationFrame.this.data.getDataManagerCriteria().validCriteriaValues(simbolicSplittedValues, isNumericEnabled)) {
							criteriaFrame.addCriteria(textFieldCriteriaName.getText(), textFieldSimbolicValues.getText(), isNumericEnabled, criteriaToUpdate);
							CriteriaCreationFrame.this.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Por favor, revise los valores simbolicos del criterio y y siga las instrucciones solicitadas", "Advertencia", JOptionPane.WARNING_MESSAGE);
						}
					}else if(isNumericEnabled && !textFieldNumericValue1.getText().equals("") && !textFieldNumericValue2.getText().equals("")) {
						String[] numericSplittedValues = new String[] {textFieldNumericValue1.getText(), textFieldNumericValue2.getText()};
						
						if(CriteriaCreationFrame.this.data.getDataManagerCriteria().validCriteriaValues(numericSplittedValues, isNumericEnabled)){
							criteriaFrame.addCriteria(textFieldCriteriaName.getText(), textFieldNumericValue1.getText()+","+textFieldNumericValue2.getText(), isNumericEnabled, criteriaToUpdate);
							CriteriaCreationFrame.this.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Por favor, revise los valores numericos del criterio y y siga las instrucciones solicitadas", "Advertencia", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Por favor, recuerde que debe definir un rango de valores para el criterio", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Error: El nombre de criterio ("+textFieldCriteriaName.getText()+") no es valido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	public void editMode(Criteria criteria) {
		if(criteria!=null) {
			textFieldCriteriaName.setText(criteria.getName());
			if(criteria.isNumeric) {
				rdbtnNumericValues.doClick();
				textFieldNumericValue1.setText(criteria.getValues()[0]);
				textFieldNumericValue2.setText(criteria.getValues()[1]);
			}else {
				rdbtnCategoricValues.doClick();
				textFieldSimbolicValues.setText(criteria.getCriteriaValuesString());
			}
		}
	}
	
}
