package criteria;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CriteriaCreationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCriteriaName;
	private JTextField textFieldNumericValue1;
	private JTextField textFieldNumericValue2;
	private JTextField textFieldSimbolicValues;
	private boolean isNumericEnabled;
	private JRadioButton rdbtnNumericValues;
	private JRadioButton rdbtnSimbolicValues;
	
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
		setBounds(100, 100, 650, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel_0 = new JLabel("Ingrese el nombre del nuevo criterio (recuerde no repetir)");
		GridBagConstraints gbc_lblNewLabel_0 = new GridBagConstraints();
		gbc_lblNewLabel_0.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_0.gridx = 0;
		gbc_lblNewLabel_0.gridy = 0;
		contentPane.add(lblNewLabel_0, gbc_lblNewLabel_0);
		
		textFieldCriteriaName = new JTextField();
		GridBagConstraints gbc_textFieldCriteriaName = new GridBagConstraints();
		gbc_textFieldCriteriaName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCriteriaName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCriteriaName.gridx = 0;
		gbc_textFieldCriteriaName.gridy = 1;
		contentPane.add(textFieldCriteriaName, gbc_textFieldCriteriaName);
		textFieldCriteriaName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese el rango de valores del nuevo criterio");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		rdbtnNumericValues = new JRadioButton("Valores numericos");
		rdbtnNumericValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumericValue1.setEnabled(true);
				textFieldNumericValue2.setEnabled(true);
				isNumericEnabled = true;
				textFieldSimbolicValues.setEnabled(false);
			}
		});
		panel.add(rdbtnNumericValues, BorderLayout.WEST);
		
		rdbtnSimbolicValues = new JRadioButton("Valores simbolicos");
		rdbtnSimbolicValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumericValue1.setEnabled(false);
				textFieldNumericValue2.setEnabled(false);
				isNumericEnabled = false;
				textFieldSimbolicValues.setEnabled(true);
			}
		});
		panel.add(rdbtnSimbolicValues, BorderLayout.EAST);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnNumericValues);
		buttonGroup.add(rdbtnSimbolicValues);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 4;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("El criterio puede valer entre (");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		textFieldNumericValue1 = new JFormattedTextField();
		textFieldNumericValue1.setEnabled(false);
		GridBagConstraints gbc_textFieldNumericValue1 = new GridBagConstraints();
		gbc_textFieldNumericValue1.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldNumericValue1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNumericValue1.gridx = 1;
		gbc_textFieldNumericValue1.gridy = 0;
		panel_1.add(textFieldNumericValue1, gbc_textFieldNumericValue1);
		textFieldNumericValue1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("-");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 0;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textFieldNumericValue2 = new JFormattedTextField();
		textFieldNumericValue2.setEnabled(false);
		GridBagConstraints gbc_textFieldNumericValue2 = new GridBagConstraints();
		gbc_textFieldNumericValue2.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldNumericValue2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNumericValue2.gridx = 3;
		gbc_textFieldNumericValue2.gridy = 0;
		panel_1.add(textFieldNumericValue2, gbc_textFieldNumericValue2);
		textFieldNumericValue2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(")");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 0;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 5;
		contentPane.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNewLabel_4 = new JLabel("El criterio puede valer entre [");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		panel_2.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textFieldSimbolicValues = new JTextField();
		textFieldSimbolicValues.setEnabled(false);
		GridBagConstraints gbc_textFieldSimbolicValues = new GridBagConstraints();
		gbc_textFieldSimbolicValues.gridwidth = 8;
		gbc_textFieldSimbolicValues.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldSimbolicValues.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSimbolicValues.gridx = 1;
		gbc_textFieldSimbolicValues.gridy = 0;
		panel_2.add(textFieldSimbolicValues, gbc_textFieldSimbolicValues);
		textFieldSimbolicValues.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("]");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.gridx = 9;
		gbc_lblNewLabel_5.gridy = 0;
		panel_2.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 6;
		contentPane.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JButton btnAcept = new JButton("Aceptar y Guardar criterio");
		btnAcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CriteriaCreationFrame.this.data.validCriteriaName(textFieldCriteriaName.getText())) {
					if(!isNumericEnabled && !textFieldSimbolicValues.getText().equals("")) {
						
						String[] simbolicSplittedValues = textFieldSimbolicValues.getText().trim().split("\\s*,\\s*");
						
						if(CriteriaCreationFrame.this.data.validCriteriaValues(simbolicSplittedValues, isNumericEnabled)) {
							criteriaFrame.addCriteria(textFieldCriteriaName.getText(), textFieldSimbolicValues.getText(), isNumericEnabled, criteriaToUpdate);
							CriteriaCreationFrame.this.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Por favor, revise los valores simbolicos del criterio y y siga las instrucciones solicitadas", "Advertencia", JOptionPane.WARNING_MESSAGE);
						}
					}else if(isNumericEnabled && !textFieldNumericValue1.getText().equals("") && !textFieldNumericValue2.getText().equals("")) {
						String[] numericSplittedValues = new String[] {textFieldNumericValue1.getText(), textFieldNumericValue2.getText()};
						
						if(CriteriaCreationFrame.this.data.validCriteriaValues(numericSplittedValues, isNumericEnabled)){
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
		panel_3.add(btnAcept, BorderLayout.WEST);
		
		JButton btnCancel = new JButton("Cancelar y Descartar criterio");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriteriaCreationFrame.this.dispose();
			}
		});
		panel_3.add(btnCancel, BorderLayout.EAST);
		
		if(criteria!=null) {
			textFieldCriteriaName.setText(criteria.getName());
			if(criteria.isNumeric) {
				rdbtnNumericValues.doClick();
				textFieldNumericValue1.setText(criteria.getValues()[0]);
				textFieldNumericValue2.setText(criteria.getValues()[1]);
			}else {
				rdbtnSimbolicValues.doClick();
				textFieldSimbolicValues.setText(criteria.getCriteriaValuesString());
			}
		}
	}
	
}
