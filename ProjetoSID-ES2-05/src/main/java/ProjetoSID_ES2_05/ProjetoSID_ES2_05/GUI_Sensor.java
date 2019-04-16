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
	private Sensor sensor;
	private JTextField textField;

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
		frame.getContentPane().add(splitPane);
		
		JLabel lblJSON = new JLabel("JSON");
		splitPane.setLeftComponent(lblJSON);
		
		textField = new JTextField();
		splitPane.setRightComponent(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String json = textField.getText();
				try {
					System.out.println("Mensagem JSON: " +  json);
					//Enviar os dados
					sensor.sendValues(json);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Os valores têm que ser númericos");
					e.printStackTrace();
				} finally {
					textField.setText("");
				}
				
				
			}
		});
		panel.add(btnEnviar);
	}
	
	public void show() {
		frame.setVisible(true);
	}

}
