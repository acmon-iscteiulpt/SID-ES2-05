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

public class GUI_Utilizador_Delete {

	private JFrame frame;
	private Administrador admin;
	private JComboBox<String> utilizadorBox;


	/**
	 * Create the application.
	 */
	public GUI_Utilizador_Delete(Administrador admin) {
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
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		
		JLabel lblIdutilizador = new JLabel("IDUtilizador");
		splitPane.setLeftComponent(lblIdutilizador);
		
		utilizadorBox = new JComboBox<String>();
		splitPane.setRightComponent(utilizadorBox);
		
		JButton btnNewButton = new JButton("Delete");
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = utilizadorBox.getSelectedIndex();
				String nomeUtilizador = utilizadorBox.getItemAt(index);
				admin.deleteUtilizador(nomeUtilizador);
				frame.setVisible(false);
			}
		});
	}
	
	public void turnOnVisible() {
		frame.setVisible(true);
	}
	
	public void resetUtilizadorBox() {
		utilizadorBox.removeAllItems();
		DefaultComboBoxModel<String> box = admin.getNomeUtilizadorTable();
		utilizadorBox.setModel(box);
	}

}
