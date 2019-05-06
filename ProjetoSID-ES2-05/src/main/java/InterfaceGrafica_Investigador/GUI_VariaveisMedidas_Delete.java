package InterfaceGrafica_Investigador;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class GUI_VariaveisMedidas_Delete {

	private JFrame frame;
	private GUI_Investigador gui_investigador;
	private Investigador investigador;
	private JComboBox<String> idVariavelMedidaBox;


	/**
	 * Create the application.
	 */
	public GUI_VariaveisMedidas_Delete(GUI_Investigador gui_investigador, Investigador investigador) {
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
		
		JButton btnNewButton = new JButton("Delete");
		splitPane.setRightComponent(btnNewButton);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblIdmedio = new JLabel("VariavelMedidaID");
		splitPane_1.setLeftComponent(lblIdmedio);
		
		this.idVariavelMedidaBox = new JComboBox<String>();
		splitPane_1.setRightComponent(idVariavelMedidaBox);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String idVariavelMedida = (String) idVariavelMedidaBox.getSelectedItem();
				investigador.deleteVariavelMedida(idVariavelMedida);
				frame.setVisible(false);
				gui_investigador.refreshVariaveisMedidas();
			}
		});
	}
	
	
	public void turnOnVisible() {
		frame.setVisible(true);
	}
	
	public void resetIdVariavelMedidaBox() {
		idVariavelMedidaBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getIDVariaveisMedidasTable();
		idVariavelMedidaBox.setModel(box);
	}

}
