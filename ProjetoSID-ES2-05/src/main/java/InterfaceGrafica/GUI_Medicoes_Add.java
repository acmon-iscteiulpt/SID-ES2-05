package InterfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JDateChooser;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class GUI_Medicoes_Add {

	private JFrame frame;
	private JDateChooser dataChooser;
	private JTextField valorMedicaoField;
	private JComboBox<String> culturaBox;
	private JComboBox<String> variavelBox;
	private JSpinner spinner;
	private Investigador investigador;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI_Medicoes window = new GUI_Medicoes();
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
	public GUI_Medicoes_Add(Investigador investigador) {
		this.investigador = investigador;
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
		panel.setLayout(new GridLayout(5, 2, 0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel.add(splitPane_1);
		
		JLabel culturaLabel = new JLabel("Cultura");
		splitPane_1.setLeftComponent(culturaLabel);
		
		this.culturaBox = new JComboBox<String>();
		splitPane_1.setRightComponent(culturaBox);
		
		JSplitPane splitPane_5 = new JSplitPane();
		panel.add(splitPane_5);
		
		JLabel variavelLabel = new JLabel("Variavel");
		splitPane_5.setLeftComponent(variavelLabel);
		
		this.variavelBox = new JComboBox<String>();
		splitPane_5.setRightComponent(variavelBox);
		
		JPanel dataPanel = new JPanel();
		panel.add(dataPanel);
		dataPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane_2 = new JSplitPane();
		dataPanel.add(splitPane_2);
		
		JLabel dataLabel = new JLabel("Data");
		splitPane_2.setLeftComponent(dataLabel);
		
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
		
		JLabel valorMedicaoLabel = new JLabel("ValorMedição");
		splitPane_4.setLeftComponent(valorMedicaoLabel);
		
		valorMedicaoField = new JTextField();
		splitPane_4.setRightComponent(valorMedicaoField);
		valorMedicaoField.setColumns(10);
		
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomeCultura = (String) culturaBox.getSelectedItem();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
				Date data = dataChooser.getDate();
				String dataString = df.format(data);
				String time = spinner.getValue().toString();
				String[] timeVector = time.split(" ");
				String valorMedicao = valorMedicaoField.getText();
				String nomeVariavel = (String) variavelBox.getSelectedItem();
				investigador.addMedicaoTable(nomeCultura, nomeVariavel, dataString, timeVector[3], valorMedicao);
				frame.setVisible(false);
				
			}
		});
		splitPane.setRightComponent(addBtn);
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
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
