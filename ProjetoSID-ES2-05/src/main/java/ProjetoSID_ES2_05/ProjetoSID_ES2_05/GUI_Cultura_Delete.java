package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI_Cultura_Delete {

	private JFrame frame;
	private JTextField idCulturaField;
	private Investigador investigador;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI_Cultura_Delete window = new GUI_Cultura_Delete();
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
	public GUI_Cultura_Delete(Investigador investigador) {
		this.investigador = investigador;
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
		
		JLabel lblIdcultura = new JLabel("IDCultura");
		splitPane.setLeftComponent(lblIdcultura);
		
		idCulturaField = new JTextField();
		splitPane.setRightComponent(idCulturaField);
		idCulturaField.setColumns(10);
		
		JButton deleteBtn = new JButton("delete");
		frame.getContentPane().add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int idCultura = Integer.parseInt(idCulturaField.getText());
				investigador.deleteCultura(idCultura);
				frame.setVisible(false);
				idCulturaField.setText("");
			}
		});
	}
	
	public void turnOnVisible() {
		frame.setVisible(true);
	}

}
