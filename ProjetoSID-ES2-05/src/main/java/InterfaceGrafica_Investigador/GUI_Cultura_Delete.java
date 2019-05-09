package InterfaceGrafica_Investigador;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;

public class GUI_Cultura_Delete {

	private JFrame frame;
	private JTextField idCulturaField;
	private JComboBox<String> idCulturaBox;
	private Investigador investigador;
	private GUI_Investigador gui_investigador;


	/**
	 * Create the application.
	 */
	public GUI_Cultura_Delete(Investigador investigador, GUI_Investigador gui_investigador) {
		this.gui_investigador = gui_investigador;
		this.investigador = investigador;
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
		
		JLabel lblIdcultura = new JLabel("IDCultura");
		splitPane.setLeftComponent(lblIdcultura);
		
//		idCulturaField = new JTextField();
//		splitPane.setRightComponent(idCulturaField);
//		idCulturaField.setColumns(10);
		
		this.idCulturaBox = new JComboBox<String>();
		splitPane.setRightComponent(idCulturaBox);
		
		JButton deleteBtn = new JButton("delete");
		frame.getContentPane().add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				int idCultura = Integer.parseInt(idCulturaField.getText());
				String idCultura = (String) idCulturaBox.getSelectedItem();
				int idCultura2 = Integer.parseInt(idCultura);
				investigador.deleteCultura(idCultura2);
				frame.setVisible(false);
				gui_investigador.refreshCultura();
			}
		});
	}
	
	public void turnOnVisible() {
		frame.setVisible(true);
	}
	
	public void resetIdCulturaBox() {
		idCulturaBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getIDCultura();
		idCulturaBox.setModel(box);
	}
	

}
