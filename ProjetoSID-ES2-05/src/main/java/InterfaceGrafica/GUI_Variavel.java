package InterfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUI_Variavel {

	private JFrame frame;
	private Administrador admin;
	private JTextField nomeVariavelField;
	private JTextField limiteSuperiorField;
	private JTextField limiteInferiorField;
	private JComboBox<String> culturaBox;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI_Variavel window = new GUI_Variavel();
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
	public GUI_Variavel(Administrador admin) {
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
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblLimitesuperior = new JLabel("LimiteSuperior");
		splitPane_3.setLeftComponent(lblLimitesuperior);
		
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
		
		JButton addVariavelBtn = new JButton("Add");
		splitPane.setRightComponent(addVariavelBtn);
		addVariavelBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String cultura = (String) culturaBox.getSelectedItem();
					String nomeVariavel = nomeVariavelField.getText();
					int limiteSuperior = Integer.parseInt(limiteSuperiorField.getText());
					int limiteInferior = Integer.parseInt(limiteInferiorField.getText());
					System.out.println("Cultura seleccionada: " + cultura);
					admin.addVariavel(cultura, nomeVariavel, limiteSuperior, limiteInferior);
					frame.setVisible(false);
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos!");
				}
				
			}
		});
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
