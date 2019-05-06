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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI_VariaveisMedidas_Add {

	private JFrame frame;
	private Investigador investigador;
	private GUI_Investigador gui_investigador;
	private JComboBox<String> culturaBox;
	private JComboBox<String> variavelBox;
	private JTextField limiteSuperiorField;
	private JTextField limiteInferiorField;


	/**
	 * Create the application.
	 */
	public GUI_VariaveisMedidas_Add(Investigador investigador, GUI_Investigador gui_investigador) {
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
		
		JLabel lblNewLabel = new JLabel("NomeCultura");
		splitPane_1.setLeftComponent(lblNewLabel);
		
		this.culturaBox = new JComboBox<String>();
		splitPane_1.setRightComponent(culturaBox);
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblNewLabel_1 = new JLabel("NomeVariavel");
		splitPane_2.setLeftComponent(lblNewLabel_1);
		
		this.variavelBox = new JComboBox<String>();
		splitPane_2.setRightComponent(variavelBox);
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblNewLabel_2 = new JLabel("LimiteSuperior");
		splitPane_3.setLeftComponent(lblNewLabel_2);
		
		limiteSuperiorField = new JTextField();
		splitPane_3.setRightComponent(limiteSuperiorField);
		limiteSuperiorField.setColumns(10);
		
		JSplitPane splitPane_4 = new JSplitPane();
		panel.add(splitPane_4);
		
		JLabel lblLimiteinferior = new JLabel("LimiteInferior");
		splitPane_4.setLeftComponent(lblLimiteinferior);
		
		limiteInferiorField = new JTextField();
		splitPane_4.setRightComponent(limiteInferiorField);
		limiteInferiorField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		splitPane.setRightComponent(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String nomeCultura = (String) culturaBox.getSelectedItem();
					String nomeVariavel = (String) variavelBox.getSelectedItem();
					String limiteSuperior = limiteSuperiorField.getText();
					String limiteInferior = limiteInferiorField.getText();
					int limiteSuperior2 = Integer.parseInt(limiteSuperior);
					int limiteInferior2 = Integer.parseInt(limiteInferior);
					investigador.addVariavelMedida(nomeCultura, nomeVariavel, limiteSuperior2, limiteInferior2);
					frame.setVisible(false);
					resetFields();
					gui_investigador.refreshVariaveisMedidas();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Os limites têm que ser numéricos");
				}

			}
		});
	}
	
	public void turnOnVisible() {
		frame.setVisible(true);
	}
	
	public void resetFields() {
		limiteSuperiorField.setText("");
		limiteInferiorField.setText("");
	}
	
	public void resetCulturaBox() {
		culturaBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getNomeCultura();
		culturaBox.setModel(box);
	}
	
	public void resetVariavelBox() {
		variavelBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getNomeVariavel();
		variavelBox.setModel(box);
	}

}
