package InterfaceGrafica;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;

import javax.swing.JButton;

public class GUI_Cultura_Add {

	private JFrame frame;
	private JTextField culturaField;
	private JTextField descricaoCulturaField;
	private Investigador investigador;


	/**
	 * Create the application.
	 */
	public GUI_Cultura_Add(Investigador investigador) {
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
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblCultura = new JLabel("Cultura");
		splitPane_1.setLeftComponent(lblCultura);
		
		culturaField = new JTextField();
		splitPane_1.setRightComponent(culturaField);
		culturaField.setColumns(10);
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblDescriocultura = new JLabel("DescriçãoCultura");
		splitPane_2.setLeftComponent(lblDescriocultura);
		
		descricaoCulturaField = new JTextField();
		splitPane_2.setRightComponent(descricaoCulturaField);
		descricaoCulturaField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		splitPane.setRightComponent(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String cultura = culturaField.getText();
				String descricaoCultura = descricaoCulturaField.getText();
				investigador.addCultura(cultura, descricaoCultura);
				frame.setVisible(false);
				culturaField.setText("");
				descricaoCulturaField.setText("");
			}
		});
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}

}
