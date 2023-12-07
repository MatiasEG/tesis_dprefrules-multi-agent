package evidence;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataManager.DataManager;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Component;
import javax.swing.Box;

public class ParticipantsPriorityFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DataManager data;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParticipantsPriorityFrame frame = new ParticipantsPriorityFrame(null);
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
	public ParticipantsPriorityFrame(DataManager data) {
		this.data = data;
		
		setTitle("Prioridades entre agentes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Defina la prioridad que hay entre los participantes.");
		panel_3.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_4.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel(" - La prioridad sera tenida en cuenta al momento de determinar cual es la mejor alternativa.");
		panel_4.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel(" - Recuerde que el participante X no puede tener mayor prioridad que X");
		panel_4.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel(" - El participante X no puede tener mayor prioridad que el participante Y y al mismo tiempo menor prioridad que Y");
		panel_4.add(lblNewLabel_5);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_2 = new JLabel("El participante");
		panel_1.add(lblNewLabel_2);
		
		JComboBox<String> comboBox = new JComboBox<String>(data.getParticipantsArrayString());
		panel_1.add(comboBox);
		
		JLabel lblNewLabel_3 = new JLabel("tiene mayor prioridad que el participante");
		panel_1.add(lblNewLabel_3);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>(data.getParticipantsArrayString());
		panel_1.add(comboBox_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Agregar prioridad");
		panel_2.add(btnNewButton, BorderLayout.NORTH);
		
		JList list = new JList();
		panel_2.add(list, BorderLayout.CENTER);
		
		JButton btnNewButton_1 = new JButton("Eliminar prioridad seleccionada");
		panel_2.add(btnNewButton_1, BorderLayout.SOUTH);
	}

}
