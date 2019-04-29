package InterfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;

import javax.swing.JButton;

public class GUI_Sistema {

	private JFrame frame;
	private JTextField superiorTemperaturaField;
	private JTextField inferiorTemperaturaField;
	private JTextField superiorLuminosidadeField;
	private JTextField inferiorLuminosidadeField;
	private Administrador admin;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI_Sistema window = new GUI_Sistema();
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
	public GUI_Sistema(Administrador admin) {
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
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);
		
		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_5);
		
		JSplitPane splitPane_6 = new JSplitPane();
		splitPane_6.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_5.setRightComponent(splitPane_6);
		
		JSplitPane splitPane_7 = new JSplitPane();
		splitPane_7.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_6.setRightComponent(splitPane_7);
		
		superiorLuminosidadeField = new JTextField();
		splitPane_7.setLeftComponent(superiorLuminosidadeField);
		superiorLuminosidadeField.setColumns(10);
		
		inferiorLuminosidadeField = new JTextField();
		splitPane_7.setRightComponent(inferiorLuminosidadeField);
		inferiorLuminosidadeField.setColumns(10);
		
		inferiorTemperaturaField = new JTextField();
		splitPane_6.setLeftComponent(inferiorTemperaturaField);
		inferiorTemperaturaField.setColumns(10);
		
		superiorTemperaturaField = new JTextField();
		splitPane_5.setLeftComponent(superiorTemperaturaField);
		superiorTemperaturaField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		splitPane_1.setRightComponent(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Chamar a função do administrador para adicionar o sistema
//				String superiorLuminosidade = superiorLuminosidade.ge
				int superiorLuminosidade = Integer.parseInt(superiorLuminosidadeField.getText());
				int inferiorLuminosidade = Integer.parseInt(inferiorLuminosidadeField.getText());
				int superiorTemperatura = Integer.parseInt(superiorTemperaturaField.getText());
				int inferiorTemperatura = Integer.parseInt(inferiorTemperaturaField.getText());
				admin.addSistema(2, superiorTemperatura, inferiorTemperatura, superiorLuminosidade, inferiorLuminosidade);
				frame.setVisible(false);
			}
		});
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_2);
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_3);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_3.setRightComponent(splitPane_4);
		
		JLabel lblLimitesuperiorluminosidade = new JLabel("LimiteSuperiorLuminosidade");
		splitPane_4.setLeftComponent(lblLimitesuperiorluminosidade);
		
		JLabel lblLimiteinferiorluminosidade = new JLabel("LimiteInferiorLuminosidade");
		splitPane_4.setRightComponent(lblLimiteinferiorluminosidade);
		
		JLabel lblLimiteinferiortemperatura = new JLabel("LimiteInferiorTemperatura");
		splitPane_3.setLeftComponent(lblLimiteinferiortemperatura);
		
		JLabel lblNewLabel = new JLabel("LimiteSuperiorTemperatura");
		splitPane_2.setLeftComponent(lblNewLabel);
	}
	
	public void setVisible() {
		this.frame.setVisible(true);
	}
	
	public void turnOffVisible() {
		this.frame.setVisible(false);
	}

}
