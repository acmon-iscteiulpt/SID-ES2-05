package InterfaceGrafica;

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
import javax.swing.JTextField;

public class GUI_Cultura_Update {

	private JFrame frame;
	private Investigador investigador;
	private JTextField nomeCulturaField;
	private JTextField descricaoCulturaField;
	private JComboBox<String> culturaBox;

	/**
	 * Create the application.
	 */
	public GUI_Cultura_Update(Investigador investigador) {
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
		panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblCultura = new JLabel("Cultura");
		splitPane_1.setLeftComponent(lblCultura);
		
		this.culturaBox = new JComboBox<String>();
		splitPane_1.setRightComponent(culturaBox);
		culturaBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setFields();
			}
		});
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblNomecultura = new JLabel("NomeCultura");
		splitPane_2.setLeftComponent(lblNomecultura);
		
		nomeCulturaField = new JTextField();
		splitPane_2.setRightComponent(nomeCulturaField);
		nomeCulturaField.setColumns(10);
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblDescriocultura = new JLabel("DescriçãoCultura");
		splitPane_3.setLeftComponent(lblDescriocultura);
		
		descricaoCulturaField = new JTextField();
		splitPane_3.setRightComponent(descricaoCulturaField);
		descricaoCulturaField.setColumns(10);
		
		JButton updateBtn = new JButton("Update");
		splitPane.setRightComponent(updateBtn);
		updateBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomeCultura = (String) culturaBox.getSelectedItem();
				String newNomeCultura = nomeCulturaField.getText();
				String descricaoCultura = descricaoCulturaField.getText();
				investigador.updateCultura(nomeCultura, newNomeCultura, descricaoCultura);
				frame.setVisible(false);
				resetField();
			}
		});
	}
	
	public void resetField() {
		nomeCulturaField.setText("");
		descricaoCulturaField.setText("");
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}
	
	public void resetCulturaBox() {
		culturaBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getNomeCultura();
		culturaBox.setModel(box);
	}
	
	public void setFields() {
		String cultura = (String) culturaBox.getSelectedItem();
		System.out.println("Cultura:" + cultura);
		if(cultura != null) {
			String[] v = investigador.searchCultura(cultura);
			nomeCulturaField.setText(cultura);
			descricaoCulturaField.setText(v[0]);
		}
	}
	
	

}
