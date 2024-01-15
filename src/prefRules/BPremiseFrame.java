package prefRules;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import criteria.Criteria;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class BPremiseFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String[] opciones = {"","Malo", "Bueno", "Exelente"};
					BPremiseFrame frame = new BPremiseFrame(new Criteria("Entretenimiento", opciones,false));
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
	public BPremiseFrame(Criteria criteria) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_5 = new JLabel("- Seleccione el criterio con el cual usted desea comparar las alternativas X e Y -");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_5);
		
		JComboBox comboBoxAvailableCiterias = new JComboBox();
		contentPane.add(comboBoxAvailableCiterias);
		
		JLabel lblNewLabel_6 = new JLabel("- Si el criterio no aparece, es porque ya esta siendo comparado en esta regla -");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_6);
		
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
		
		JLabel lblNewLabel_2 = new JLabel("La distancia minima entre los valores posibles para el criterio debe ser:");
		panel_2.add(lblNewLabel_2);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("La alternativa X debe valer al menos");
		panel_3.add(lblNewLabel_3);
		
		JComboBox<String> comboBox = new JComboBox<String>(criteria.getValues());
		panel_3.add(comboBox);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_4);
		
		JLabel lblNewLabel_4 = new JLabel("La alternativa Y debe valer como maximo");
		panel_4.add(lblNewLabel_4);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>(criteria.getValues());
		panel_4.add(comboBox_1);
		
		Dimension panelDimensions = new Dimension(600, 30);
		panel_1.setPreferredSize(panelDimensions);
		panel_1.setMaximumSize(panelDimensions);
		panel_2.setPreferredSize(panelDimensions);
		panel_2.setMaximumSize(panelDimensions);
		
		JLabel lblCriteria1 = new JLabel("en el criterio "+criteria.getName()+".");
		panel_2.add(lblCriteria1);
		panel_3.setPreferredSize(panelDimensions);
		panel_3.setMaximumSize(panelDimensions);
		
		JLabel lblCriteria2 = new JLabel("en el criterio "+criteria.getName()+".");
		panel_3.add(lblCriteria2);
		panel_4.setPreferredSize(panelDimensions);
		panel_4.setMaximumSize(panelDimensions);
		
		JLabel lblCriteria3 = new JLabel("en el criterio "+criteria.getName()+".");
		panel_4.add(lblCriteria3);
	}
}
