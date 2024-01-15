package prefRules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataValidations;
import errors.SintacticStringError;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrefRuleCreation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRuleName;
	private JLabel lblRuleName;
	
	private Rule rule;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrefRuleCreation frame = new PrefRuleCreation();
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
	public PrefRuleCreation() {
		rule = null;
		setTitle("Especificacion de la regla");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
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

}
