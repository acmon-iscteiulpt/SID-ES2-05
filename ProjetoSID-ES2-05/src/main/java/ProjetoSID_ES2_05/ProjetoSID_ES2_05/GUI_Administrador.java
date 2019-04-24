package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class GUI_Administrador {

	private JFrame frame;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTable culturaTable;
	private JTable table_5;
	private JTable table_6;
	private JTable table_7;
	private JTable table_8;
	private JTable table_9;
	private Administrador admin;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Administrador admin = new Administrador("root", "teste123");
//					GUI_Administrador window = new GUI_Administrador(admin);
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
	public GUI_Administrador(Administrador admin) {
		this.admin = admin;
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
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel culturaPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) culturaPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Cultura", null, culturaPanel, null);
		
		culturaTable = new JTable();
		culturaTable.setCellSelectionEnabled(true);
		culturaTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"IDCultura", "NomeCultura", "DescricaoCultura", "IDUtilizador_fk"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		culturaTable.getColumnModel().getColumn(0).setResizable(false);
		culturaTable.getColumnModel().getColumn(1).setResizable(false);
		culturaTable.getColumnModel().getColumn(2).setResizable(false);
		culturaTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		culturaTable.getColumnModel().getColumn(3).setResizable(false);
		culturaTable.getColumnModel().getColumn(3).setPreferredWidth(94);
		culturaPanel.add(culturaTable);
		
		JScrollPane scrollPane = new JScrollPane(culturaTable);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		culturaPanel.add(scrollPane);

		
		JPanel medicoesPanel = new JPanel();
		tabbedPane.addTab("Medições", null, medicoesPanel, null);
		
		table_5 = new JTable();
		medicoesPanel.add(table_5);
		
		JPanel medicoesLuminosidadePanel = new JPanel();
		tabbedPane.addTab("Medições Luminosidade", null, medicoesLuminosidadePanel, null);
		
		table_6 = new JTable();
		medicoesLuminosidadePanel.add(table_6);
		
		JPanel medicoesTemperaturaPanel = new JPanel();
		tabbedPane.addTab("Medições Temperatura", null, medicoesTemperaturaPanel, null);
		
		table_7 = new JTable();
		medicoesTemperaturaPanel.add(table_7);
		
		JPanel sistemaPanel = new JPanel();
		tabbedPane.addTab("Sistema", null, sistemaPanel, null);
		
		table_8 = new JTable();
		sistemaPanel.add(table_8);
		
		JPanel utilizadorPanel = new JPanel();
		tabbedPane.addTab("Utilizador", null, utilizadorPanel, null);
		
		table_9 = new JTable();
		utilizadorPanel.add(table_9);
		
		JPanel variaveisPanel = new JPanel();
		tabbedPane.addTab("Variaveis", null, variaveisPanel, null);
		
		table_3 = new JTable();
		variaveisPanel.add(table_3);
		
		JPanel variaveisMedidasPanel = new JPanel();
		tabbedPane.addTab("variaveisMedidas", null, variaveisMedidasPanel, null);
		
		table_2 = new JTable();
		variaveisMedidasPanel.add(table_2);
		
		JPanel alertasSensorPanel = new JPanel();
		tabbedPane.addTab("Alertas Sensor", null, alertasSensorPanel, null);
		
		table_1 = new JTable();
		alertasSensorPanel.add(table_1);
		
		JPanel alertasMedicoesPanel = new JPanel();
		tabbedPane.addTab("Alertas Medições", null, alertasMedicoesPanel, null);
		
		table = new JTable();
		alertasMedicoesPanel.add(table);
		
		tabbedPane.getModel().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
//				JLabel label = ((JLabel) tabbedPane.getSelectedComponent());
				int index = tabbedPane.getSelectedIndex();
				System.out.println("Tab: " + tabbedPane.getSelectedIndex());
				System.out.println("Tab: " + tabbedPane.getTitleAt(index));
				DefaultTableModel model;
				if(tabbedPane.getTitleAt(index).equals("Cultura")) {
					model = admin.getCulturaTable(culturaTable);
					culturaTable.setModel(model);
				}
				
				
			}
			
		});
		

	}

}
