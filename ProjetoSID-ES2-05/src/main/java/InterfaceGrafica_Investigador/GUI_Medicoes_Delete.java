package InterfaceGrafica_Investigador;


import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;

public class GUI_Medicoes_Delete {

	private JFrame frame;
	private Investigador investigador;
	private JComboBox<String> medicaoBox;
	private GUI_Investigador gui_investigador;


	/**
	 * Create the application.
	 */
	public GUI_Medicoes_Delete(Investigador investigador, GUI_Investigador gui_investigador) {
		this.gui_investigador = gui_investigador;
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
		
		JLabel idMedicaoLabel = new JLabel("IDMedicao");
		splitPane.setLeftComponent(idMedicaoLabel);
		
		medicaoBox = new JComboBox<String>();
		splitPane.setRightComponent(medicaoBox);
		
		JButton deleteBtn = new JButton("Delete");
		frame.getContentPane().add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = medicaoBox.getSelectedIndex();
				String idMedicao = medicaoBox.getItemAt(index);
				investigador.deleteMedicaoTable(idMedicao);
				frame.setVisible(false);
				gui_investigador.refreshMedicoes();
			}
		});
	}
	
	public void turnOnVisible() {
		this.frame.setVisible(true);
	}
	
	public void resetMedicaoBox() {
		medicaoBox.removeAllItems();
		DefaultComboBoxModel<String> box = investigador.getID_MedicaoTable();
		medicaoBox.setModel(box);
	}

}
