package InterfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class GUI_Utilizador {

	private JFrame frame;
	private Administrador admin;
	private JTextField nomeUtilizadorField;
	private JTextField tipoUtilizadorField;
	private JTextField emailField;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI_Utilizador window = new GUI_Utilizador();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public GUI_Utilizador(Administrador admin) {
		this.admin = admin;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblNomeutilizador = new JLabel("NomeUtilizador");
		splitPane_1.setLeftComponent(lblNomeutilizador);
		
		nomeUtilizadorField = new JTextField();
		splitPane_1.setRightComponent(nomeUtilizadorField);
		nomeUtilizadorField.setColumns(10);
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblPassword = new JLabel("Password");
		splitPane_2.setLeftComponent(lblPassword);
		
		passwordField = new JPasswordField();
		splitPane_2.setRightComponent(passwordField);
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblTipoutilizador = new JLabel("TipoUtilizador");
		splitPane_3.setLeftComponent(lblTipoutilizador);
		
		tipoUtilizadorField = new JTextField();
		splitPane_3.setRightComponent(tipoUtilizadorField);
		tipoUtilizadorField.setColumns(10);
		
		JSplitPane splitPane_4 = new JSplitPane();
		panel.add(splitPane_4);
		
		JLabel lblEmail = new JLabel("Email");
		splitPane_4.setLeftComponent(lblEmail);
		
		emailField = new JTextField();
		splitPane_4.setRightComponent(emailField);
		emailField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		splitPane.setRightComponent(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomeUtilizador = nomeUtilizadorField.getText();
				String tipoUtilizador = tipoUtilizadorField.getText();
				String email = emailField.getText();
				String password = passwordField.getText();
				if(nomeUtilizador.isEmpty() || tipoUtilizador.isEmpty() || email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos!");
				} else {
					admin.addUtilizador(nomeUtilizador, tipoUtilizador, email, password);
					frame.setVisible(false);
				}
				
			}
		});
		
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}

}
