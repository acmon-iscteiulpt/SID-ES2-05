package InterfaceGrafica_Investigador;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI_VariaveisMedidas_Update {

	private JFrame frame;
	private Investigador investigador;
	private GUI_Investigador gui_investigador;
	private JTextField limiteSuperiorField;
	private JTextField limiteInferiorField;
	private JComboBox<String> idVariavelMedidaBox;


	/**
	 * Create the application.
	 */
	public GUI_VariaveisMedidas_Update(Investigador investigador, GUI_Investigador gui_investigador) {
		this.investigador = investigador;
		this.gui_investigador = gui_investigador;
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
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblIdmedio = new JLabel("IDMedição");
		splitPane_1.setLeftComponent(lblIdmedio);
		
		this.idVariavelMedidaBox = new JComboBox<String>();
		splitPane_1.setRightComponent(idVariavelMedidaBox);
		idVariavelMedidaBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setFields();
			}
		});
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblLimitesuperior = new JLabel("LimiteSuperior");
		splitPane_2.setLeftComponent(lblLimitesuperior);
		
		limiteSuperiorField = new JTextField();
		splitPane_2.setRightComponent(limiteSuperiorField);
		limiteSuperiorField.setColumns(10);
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblLimiteinferior = new JLabel("LimiteInferior");
		splitPane_3.setLeftComponent(lblLimiteinferior);
		
		limiteInferiorField = new JTextField();
		splitPane_3.setRightComponent(limiteInferiorField);
		limiteInferiorField.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		splitPane.setRightComponent(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String idMedicao = (String) idVariavelMedidaBox.getSelectedItem();
					String limiteSuperior = limiteSuperiorField.getText();
					String limiteInferior = limiteInferiorField.getText();
					int limiteSuperior2 = Integer.parseInt(limiteSuperior);
					int limiteInferior2 = Integer.parseInt(limiteInferior);
					System.out.println("LimiteSuperior: " + limiteSuperior2);
					System.out.println("LimiteInferior: " + limiteInferior2);
					investigador.updateVariavelMedida(idMedicao, limiteSuperior2, limiteInferior2);
					frame.setVisible(false);
					gui_investigador.refreshVariaveisMedidas();
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Preencha os campos corretamente");
				}
				
			}
		});
	}
	
	public void turnOnVisible() {
		frame.setVisible(true);
	}
	
	public void setFields() {
		String idVariavelMedida = (String) idVariavelMedidaBox.getSelectedItem();
		if(idVariavelMedida != null) {
			String[] v = investigador.searchVariavelMedida(idVariavelMedida);
			limiteSuperiorField.setText(v[0]);
			limiteInferiorField.setText(v[1]);
		}
	}
	
	public void resetIdMedicaoBox() {
		idVariavelMedidaBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getIDVariaveisMedidasTable();
		idVariavelMedidaBox.setModel(box);
	}

}
