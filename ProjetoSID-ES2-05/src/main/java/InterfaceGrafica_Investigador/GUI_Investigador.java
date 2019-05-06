package InterfaceGrafica_Investigador;

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

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Investigador;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Investigador {

	private JFrame frame;
	private JTable culturaTable;
	private Investigador investigador;
	private JTabbedPane tabbedPane;
	private GUI_Cultura_Add gui_cultura;
	private GUI_Cultura_Delete gui_cultura_delete;
	private GUI_Cultura_Update gui_cultura_update;
	private GUI_Medicoes_Add gui_medicoes;
	private GUI_Medicoes_Delete gui_medicoes_delete;
	private GUI_Medicoes_Update gui_medicoes_update;
	private GUI_VariaveisMedidas_Add gui_variaveismedidas_add;
	private GUI_VariaveisMedidas_Update gui_variaveismedidas_update;
	private GUI_VariaveisMedidas_Delete gui_variaveismedidas_delete;
	private JTable medicoesTable;
	private JTable variaveisMedidasTable;


	/**
	 * Create the application.
	 */
	public GUI_Investigador(Investigador investigador) {
		this.investigador = investigador;
		this.gui_cultura = new GUI_Cultura_Add(investigador, this);
		this.gui_cultura_delete = new GUI_Cultura_Delete(investigador, this);
		this.gui_cultura_update = new GUI_Cultura_Update(investigador, this);
		this.gui_medicoes = new GUI_Medicoes_Add(investigador, this);
		this.gui_medicoes_delete = new GUI_Medicoes_Delete(investigador, this);
		this.gui_medicoes_update = new GUI_Medicoes_Update(investigador, this);
		this.gui_variaveismedidas_add = new GUI_VariaveisMedidas_Add(investigador, this);
		this.gui_variaveismedidas_update = new GUI_VariaveisMedidas_Update(investigador, this);
		this.gui_variaveismedidas_delete = new GUI_VariaveisMedidas_Delete(this, investigador);
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
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
		panel2.setLayout(new GridLayout(4, 0, 2, 4));
		JButton addCulturaBtn = new JButton("Add Cultura");
		JButton updateCulturaBtn = new JButton("Update");
		JButton refreshCulturaBtn = new JButton("Refresh");
		JButton deleteCulturaBtn = new JButton("Delete");
		panel2.add(refreshCulturaBtn);
		panel2.add(addCulturaBtn);
		panel2.add(updateCulturaBtn);
		panel2.add(deleteCulturaBtn);
		culturaPanel.add(panel2);
		
		addCulturaBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_cultura.turnOnVisible();
			}
		});
		
		refreshCulturaBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refreshCultura();
			}
		});
		
		deleteCulturaBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_cultura_delete.turnOnVisible();
			}
		});
		
		updateCulturaBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_cultura_update.resetCulturaBox();
				gui_cultura_update.setFields();
				gui_cultura_update.turnOnVisible();
			}
		});
		
		
		JPanel medicoesPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) medicoesPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Medições", null, medicoesPanel, null);
		
		medicoesTable = new JTable();
		medicoesTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"IDMedicoes", "IDCultura_FK", "IDVariavel_FK", "DataHoraMedicao", "ValorMedicao"
			}
		));
		medicoesTable.getColumnModel().getColumn(2).setPreferredWidth(88);
		medicoesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		medicoesPanel.add(medicoesTable);
		
		JScrollPane scrollPane = new JScrollPane(medicoesTable);
		medicoesPanel.add(scrollPane);
		
		JPanel panel = new JPanel();
		medicoesPanel.add(panel);
		panel.setLayout(new GridLayout(4, 0, 2, 4));
		
		JButton refreshBtn = new JButton("Refresh");
		panel.add(refreshBtn);
		refreshBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refreshMedicoes();
			}
		});
		
		JButton addMedicaoBtn = new JButton("Add Medicao");
		panel.add(addMedicaoBtn);
		addMedicaoBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Passar a lista de medicoes apenas do utilizador
				gui_medicoes.resetCulturaBox();
				gui_medicoes.resetVariavelBox();
				gui_medicoes.turnOnVisible();
			}
		});
		
		JButton deleteMedicaoBtn = new JButton("Delete");
		panel.add(deleteMedicaoBtn);
		deleteMedicaoBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_medicoes_delete.resetMedicaoBox();
				gui_medicoes_delete.turnOnVisible();
			}
		});
		
		JButton updateMedicaoBtn = new JButton("Update");
		panel.add(updateMedicaoBtn);
		updateMedicaoBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_medicoes_update.resetVariavelBox();
				gui_medicoes_update.setFields();
				gui_medicoes_update.turnOnVisible();
			}
		});
		
		JPanel variaveisMedidasPane = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) variaveisMedidasPane.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("VariaveisMedidas", null, variaveisMedidasPane, null);
		
		variaveisMedidasTable = new JTable();
		variaveisMedidasTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"VariaveisMedidas_ID", "IDCultura_FK", "IDVariavel_FK", "LimiteSuperior", "LimiteInferior"
			}
		));
		JScrollPane scrollPane1 = new JScrollPane(variaveisMedidasTable);
		variaveisMedidasPane.add(scrollPane1);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(4, 0, 2, 4));
		JButton addVariavelMedida = new JButton("Add");
		JButton refreshVariavelMedida = new JButton("Refresh");
		JButton deleteVariavelMedida = new JButton("Delete");
		JButton updateVariavelMedida = new JButton("Update");
		panel3.add(refreshVariavelMedida);
		panel3.add(addVariavelMedida);
		panel3.add(deleteVariavelMedida);
		panel3.add(updateVariavelMedida);
		variaveisMedidasPane.add(panel3);
		
		addVariavelMedida.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_variaveismedidas_add.resetCulturaBox();
				gui_variaveismedidas_add.resetVariavelBox();
				gui_variaveismedidas_add.turnOnVisible();
			}
		});
		
		refreshVariavelMedida.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refreshVariaveisMedidas();
			}
		});
		
		updateVariavelMedida.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_variaveismedidas_update.resetIdMedicaoBox();
				gui_variaveismedidas_update.setFields();
				gui_variaveismedidas_update.turnOnVisible();
			}
		});
		
		deleteVariavelMedida.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_variaveismedidas_delete.resetIdVariavelMedidaBox();
				gui_variaveismedidas_delete.turnOnVisible();
			}
		});
		

		
		tabbedPane.getModel().addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int index = tabbedPane.getSelectedIndex();
				DefaultTableModel model;
				String title = tabbedPane.getTitleAt(index);
				if(title.equals("Cultura")) {
					refreshCultura();
				} else if (title.equals("Medições")) {
					refreshMedicoes();
				} else if (title.equals("VariaveisMedidas")) {
					refreshVariaveisMedidas();
				}
			}
		});
		refreshCultura();
	}
	
	public void refreshCultura() {
		DefaultTableModel model = investigador.getCulturaTable(culturaTable);
		culturaTable.setModel(model);
	}
	
	public void refreshMedicoes() {
		DefaultTableModel model = investigador.getMedicaoTable(medicoesTable);
		medicoesTable.setModel(model);
	}

	public void refreshVariaveisMedidas() {
		DefaultTableModel model = investigador.getVariaveisMedidasTable(variaveisMedidasTable);
		variaveisMedidasTable.setModel(model);
	}
}
