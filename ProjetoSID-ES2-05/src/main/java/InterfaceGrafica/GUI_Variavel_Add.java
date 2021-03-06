package InterfaceGrafica;


import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUI_Variavel_Add {

	private JFrame frame;
	private Administrador admin;
	private JTextField nomeVariavelField;
	private JComboBox<String> culturaBox;
	private GUI_Administrador gui_admin;


	/**
	 * Create the application.
	 */
	public GUI_Variavel_Add(Administrador admin, GUI_Administrador gui_admin) {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblCultura = new JLabel("Cultura");
		splitPane_1.setLeftComponent(lblCultura);
		
		this.culturaBox = new JComboBox<String>();
		splitPane_1.setRightComponent(culturaBox);
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblNomevariavel = new JLabel("NomeVariavel");
		splitPane_2.setLeftComponent(lblNomevariavel);
		
		nomeVariavelField = new JTextField();
		splitPane_2.setRightComponent(nomeVariavelField);
		nomeVariavelField.setColumns(10);
		
		JButton addVariavelBtn = new JButton("Add");
		splitPane.setRightComponent(addVariavelBtn);
		addVariavelBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String cultura = (String) culturaBox.getSelectedItem();
					String nomeVariavel = nomeVariavelField.getText();
					System.out.println("Cultura seleccionada: " + cultura);
					admin.addVariavel(cultura, nomeVariavel);
					frame.setVisible(false);
					resetFields();
					gui_admin.refreshVariavel();
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos!");
				}
				
			}
		});
	}
	
	public void resetFields() {
		nomeVariavelField.setText("");
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}
	
	public void resetCulturaBox() {
		culturaBox.removeAllItems();
		DefaultComboBoxModel<String> box = admin.getNomeCultura();
		culturaBox.setModel(box);
	}

}
