package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Investigador {

	private JFrame frame;
	private JTable culturaTable;
	private Investigador investigador;
	private JTabbedPane tabbedPane;
	private GUI_Cultura gui_cultura;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI_Investigador window = new GUI_Investigador();
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
	public GUI_Investigador(Investigador investigador) {
		this.investigador = investigador;
		this.gui_cultura = new GUI_Cultura();
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel culturaPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) culturaPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Cultura", null, culturaPanel, null);
		
		culturaTable = new JTable();
		culturaTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"IDCultura", "NomeCultura", "DescricaoCultura", "IDUtilizador_fk"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		culturaTable.getColumnModel().getColumn(0).setResizable(false);
		culturaTable.getColumnModel().getColumn(0).setPreferredWidth(62);
		culturaTable.getColumnModel().getColumn(1).setResizable(false);
		culturaTable.getColumnModel().getColumn(2).setResizable(false);
		culturaTable.getColumnModel().getColumn(2).setPreferredWidth(91);
		culturaTable.getColumnModel().getColumn(3).setResizable(false);
		culturaTable.getColumnModel().getColumn(3).setPreferredWidth(87);
		culturaPanel.add(culturaTable);
		
		JScrollPane scrollPaneCultura = new JScrollPane(culturaTable);
		culturaPanel.add(scrollPaneCultura);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(3, 0, 2, 4));
		JButton addCulturaBtn = new JButton("Add Cultura");
		JButton refreshCulturaBtn = new JButton("Refresh");
		JButton deleteCulturaBtn = new JButton("Delete");
		panel2.add(refreshCulturaBtn);
		panel2.add(addCulturaBtn);
		panel2.add(deleteCulturaBtn);
		culturaPanel.add(panel2);
		
		addCulturaBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_cultura.turnOnVisible();
			}
		});
		
		
		JPanel medicoesPanel = new JPanel();
		tabbedPane.addTab("Medições", null, medicoesPanel, null);
		
		tabbedPane.getModel().addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int index = tabbedPane.getSelectedIndex();
				System.out.println("Tab: " + tabbedPane.getSelectedIndex());
				DefaultTableModel model;
				String title = tabbedPane.getTitleAt(index);
				System.out.println("Tab: " + title);
				if(title.equals("Cultura")) {
					model = investigador.getCulturaTable(culturaTable);
					culturaTable.setModel(model);
				}
			}
		});
	}

}
