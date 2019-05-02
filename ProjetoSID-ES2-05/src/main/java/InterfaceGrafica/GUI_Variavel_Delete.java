package InterfaceGrafica;


import javax.swing.JFrame;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class GUI_Variavel_Delete {

	private JFrame frame;
	private Administrador admin;
	private JComboBox<String> variavelBox;
	private GUI_Administrador gui_admin;

	/**
	 * Create the application.
	 */
	public GUI_Variavel_Delete(Administrador admin, GUI_Administrador gui_admin) {
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
		
		JLabel lblNomevariavel = new JLabel("NomeVariavel");
		splitPane.setLeftComponent(lblNomevariavel);
		
		variavelBox = new JComboBox<String>();
		splitPane.setRightComponent(variavelBox);
		
		JButton btnNewButton = new JButton("Delete");
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = variavelBox.getSelectedIndex();
				String nomeVariavel = variavelBox.getItemAt(index);
				admin.deleteVariavel(nomeVariavel);
				frame.setVisible(false);
				gui_admin.refreshVariavel();
			}
		});
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}
	
	
	public void resetVariavelBox() {
		variavelBox.removeAllItems();
		DefaultComboBoxModel<String> box = admin.getNomeVariavel();
		variavelBox.setModel(box);
		
	}

}
