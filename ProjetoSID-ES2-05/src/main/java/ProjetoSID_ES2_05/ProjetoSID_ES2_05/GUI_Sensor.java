package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class GUI_Sensor {

	private JFrame frame;
	private JTextField textField_temperatura;
	private JTextField textField_luminosidade;
	private Sensor sensor;

	/**
	 * Create the application.
	 */
	public GUI_Sensor() {
		initialize();
		new Mongo("1");
		this.sensor = new Sensor("2");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
//		frame.addWindowListener(new WindowAdapter() {
//			
//			public void windowClosing(WindowEvent we) {
//				sensor.disconnectServer();
//			}
//			
//		});
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane);
		
		JLabel lblTemperatura = new JLabel("Temperatura");
		splitPane.setLeftComponent(lblTemperatura);
		
		JLabel lblLuminosidade = new JLabel("Luminosidade");
		splitPane.setRightComponent(lblLuminosidade);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane_1);
		
		textField_temperatura = new JTextField();
		splitPane_1.setLeftComponent(textField_temperatura);
		textField_temperatura.setColumns(10);
		
		textField_luminosidade = new JTextField();
		splitPane_1.setRightComponent(textField_luminosidade);
		textField_luminosidade.setColumns(10);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String luminosidade = textField_luminosidade.getText();
				String temperatura = textField_temperatura.getText();
				int valorLuminosidade = 0;
				int valorTemperatura = 0;
				try {
					valorLuminosidade = Integer.parseInt(luminosidade);
					valorTemperatura = Integer.parseInt(temperatura);
					
					System.out.println("Valor Luminosidade: " +  valorLuminosidade);
					System.out.println("Valor Temperatura: " + valorTemperatura);
					
					//Enviar os dados
					sensor.sendValues(valorTemperatura, valorLuminosidade);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Os valores têm que ser númericos");
					e.printStackTrace();
				} finally {
					textField_luminosidade.setText("");
					textField_temperatura.setText("");
				}
				
				
			}
		});
		panel.add(btnEnviar);
	}
	
	public void show() {
		frame.setVisible(true);
	}

}
