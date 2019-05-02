package InterfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;

import javax.swing.JPasswordField;

public class GUI_Utilizador_Update {

	private JFrame frame;
	private JTextField novoNomeField;
	private JTextField tipoUtilizadorField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JComboBox<String> utilizadorBox;
	private Administrador admin;
	private GUI_Administrador gui_admin;

	/**
	 * Create the application.
	 */
	public GUI_Utilizador_Update(Administrador admin, GUI_Administrador gui_admin) {
		this.gui_admin = gui_admin;
		this.admin = admin;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane);
		
		JLabel lblNomeutilizador = new JLabel("NomeUtilizador");
		splitPane.setLeftComponent(lblNomeutilizador);
		
		utilizadorBox = new JComboBox<String>();
		splitPane.setRightComponent(utilizadorBox);
		utilizadorBox.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				setFields();
			}
		});
		
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblNovonomeutilizador = new JLabel("NovoNomeUtilizador");
		splitPane_1.setLeftComponent(lblNovonomeutilizador);
		
		novoNomeField = new JTextField();
		splitPane_1.setRightComponent(novoNomeField);
		novoNomeField.setColumns(10);
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblNewLabel = new JLabel("TipoUtilizador");
		splitPane_2.setLeftComponent(lblNewLabel);
		
		tipoUtilizadorField = new JTextField();
		splitPane_2.setRightComponent(tipoUtilizadorField);
		tipoUtilizadorField.setColumns(10);
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblEmail = new JLabel("Email");
		splitPane_3.setLeftComponent(lblEmail);
		
		emailField = new JTextField();
		splitPane_3.setRightComponent(emailField);
		emailField.setColumns(10);
		
		JSplitPane splitPane_4 = new JSplitPane();
		panel.add(splitPane_4);
		
		JLabel lblPassword = new JLabel("Password");
		splitPane_4.setLeftComponent(lblPassword);
		
		passwordField = new JPasswordField();
		splitPane_4.setRightComponent(passwordField);
		
		JButton btnNewButton = new JButton("Update");
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomeUtilizador = (String) utilizadorBox.getSelectedItem();
				String newNomeUtilizador = novoNomeField.getText();
				String tipoUtilizador = tipoUtilizadorField.getText();
				String email = emailField.getText();
				String password = passwordField.getText();
				admin.updateUtilizador(nomeUtilizador, newNomeUtilizador, tipoUtilizador, email, password);
				frame.setVisible(false);
				resetFields();
				gui_admin.refreshUtilizador();
			}
		});
		

	}
	
	public void resetFields() {
		novoNomeField.setText("");
		tipoUtilizadorField.setText("");
		emailField.setText("");
		passwordField.setText("");
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}
	
	public void resetUtilizadorBox() {
		utilizadorBox.removeAllItems();
		DefaultComboBoxModel<String> box = admin.getNomeUtilizadorTable();
		utilizadorBox.setModel(box);
		
	}
	
	public void setFields() {
		String nomeUtilizador = (String) utilizadorBox.getSelectedItem();
		System.out.println("Utilizador:" + nomeUtilizador);
		if(nomeUtilizador != null) {
			String[] v = admin.searchUtilizador(nomeUtilizador);
			novoNomeField.setText(nomeUtilizador);
			tipoUtilizadorField.setText(v[1]);
			emailField.setText(v[2]);
		}
		
	}
	
	


}
