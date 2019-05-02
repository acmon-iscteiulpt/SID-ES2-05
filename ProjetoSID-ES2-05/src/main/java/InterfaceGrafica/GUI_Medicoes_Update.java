package InterfaceGrafica;

import javax.swing.JFrame;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JDateChooser;

public class GUI_Medicoes_Update {

	private JFrame frame;
	private Investigador investigador;
	private JTextField valorMedicaoField;
	private JComboBox<String> idMedicaoBox;
	private JDateChooser dataChooser;
	private JSpinner spinner;

	/**
	 * Create the application.
	 */
	public GUI_Medicoes_Update(Investigador investigador) {
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
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel lblIdmedio = new JLabel("IDMedição");
		splitPane_1.setLeftComponent(lblIdmedio);
		
		this.idMedicaoBox = new JComboBox<String>();
		splitPane_1.setRightComponent(idMedicaoBox);
		idMedicaoBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setFields();
			}
		});
		
		JSplitPane splitPane_2 = new JSplitPane();
		panel.add(splitPane_2);
		
		JLabel lblData = new JLabel("Data");
		splitPane_2.setLeftComponent(lblData);
		
		this.dataChooser = new JDateChooser();
		dataChooser.setDateFormatString("yyyy-MM-dd");
		splitPane_2.setRightComponent(dataChooser);
		
		JSplitPane splitPane_3 = new JSplitPane();
		panel.add(splitPane_3);
		
		JLabel lblHora = new JLabel("Hora");
		splitPane_3.setLeftComponent(lblHora);
		
		this.spinner = new JSpinner();
        spinner.setModel(new SpinnerDateModel());
        spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm:ss"));
		splitPane_3.setRightComponent(spinner);
		
		JSplitPane splitPane_4 = new JSplitPane();
		panel.add(splitPane_4);
		
		JLabel lblValormedio = new JLabel("ValorMedição");
		splitPane_4.setLeftComponent(lblValormedio);
		
		valorMedicaoField = new JTextField();
		splitPane_4.setRightComponent(valorMedicaoField);
		valorMedicaoField.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		splitPane.setRightComponent(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				resetFields();
			}
		});
	}
	
	public void resetFields() {
		valorMedicaoField.setText("");
	}
	
	
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}
	
	public void resetVariavelBox() {
		idMedicaoBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getID_MedicaoTable();
		idMedicaoBox.setModel(box);
	}
	
	public void setFields() {
		try {
			String idMedicao = (String) idMedicaoBox.getSelectedItem();
			if(idMedicao != null) {
				String[] v = investigador.searchMedicao(idMedicao);
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				Date data = s.parse(v[0]);
				dataChooser.setDate(data);
				SimpleDateFormat s2 = new SimpleDateFormat("HH:mm:ss");
				spinner.setValue(s2.parseObject(v[1]));;
				valorMedicaoField.setText(v[2]);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Catch setFields class GUI_Medicoes_Update");
		}
	}

}
