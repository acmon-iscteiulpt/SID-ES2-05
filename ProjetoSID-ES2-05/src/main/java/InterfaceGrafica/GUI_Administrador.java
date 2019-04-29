package InterfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import ProjetoSID_ES2_05.ProjetoSID_ES2_05.Administrador;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class GUI_Administrador {

	private JFrame frame;
	private JTable table;
	private JTable table_1;
	private JTable variaveisMedidasTable;
	private JTable variaveisTable;
	private JTable culturaTable;
	private JTable medicoesTable;
	private JTable medicoesLuminosidadeTable;
	private JTable medicoesTemperaturaTable;
	private JTable sistemaTable;
	private JTable utilizadorTable;
	private Administrador admin;
	private GUI_Sistema gui_sistema;
	private GUI_Utilizador gui_utilizador;
	private GUI_Utilizador_Delete gui_utilizador_delete;
	private GUI_Variavel gui_variavel;
	private GUI_Variavel_Delete gui_variavel_delete;


	/**
	 * Create the application.
	 */
	public GUI_Administrador(Administrador admin) {
		this.admin = admin;
		this.gui_sistema = new GUI_Sistema(admin);
		this.gui_utilizador = new GUI_Utilizador(admin);
		this.gui_utilizador_delete = new GUI_Utilizador_Delete(admin);
		this.gui_variavel = new GUI_Variavel(admin);
		this.gui_variavel_delete = new GUI_Variavel_Delete(admin);
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
		
		//Configuração da Tab Cultura
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
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
		
		JScrollPane scrollPaneCultura = new JScrollPane(culturaTable);
		scrollPaneCultura.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		culturaPanel.add(scrollPaneCultura);

		
		//Configuração da Tab Medições
		JPanel medicoesPanel = new JPanel();
		((FlowLayout)medicoesPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Medições", null, medicoesPanel, null);
		
		medicoesTable = new JTable();
		medicoesTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"IDMedicoes", "IDCultura_fk", "IDVariavel_fk", "DataHoraMedicao", "ValorMedicao", "IDUtilizador_fk"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		medicoesTable.getColumnModel().getColumn(0).setResizable(false);
		medicoesTable.getColumnModel().getColumn(1).setResizable(false);
		medicoesTable.getColumnModel().getColumn(2).setResizable(false);
		medicoesTable.getColumnModel().getColumn(3).setResizable(false);
		medicoesTable.getColumnModel().getColumn(4).setResizable(false);
		medicoesPanel.add(medicoesTable);
		
		JScrollPane scrollPaneMedicoes = new JScrollPane(medicoesTable);
		scrollPaneMedicoes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		medicoesPanel.add(scrollPaneMedicoes);
		
		
		//Configuração da Tab Medições Luminosidade
		JPanel medicoesLuminosidadePanel = new JPanel();
		((FlowLayout)medicoesLuminosidadePanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Medições Luminosidade", null, medicoesLuminosidadePanel, null);
		
		medicoesLuminosidadeTable = new JTable();
		medicoesLuminosidadeTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"IDMedicao", "DataHoraMedicao", "ValorMedicaoLuminosidade"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		medicoesLuminosidadeTable.getColumnModel().getColumn(0).setResizable(false);
		medicoesLuminosidadeTable.getColumnModel().getColumn(1).setResizable(false);
		medicoesLuminosidadeTable.getColumnModel().getColumn(2).setResizable(false);
		medicoesLuminosidadePanel.add(medicoesLuminosidadeTable);
		
		JScrollPane scrollPaneMedicoesLuminosidade = new JScrollPane(medicoesLuminosidadeTable);
		scrollPaneMedicoesLuminosidade.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		medicoesLuminosidadePanel.add(scrollPaneMedicoesLuminosidade);
		
		
		//Configuração da Tab Medições Temperatura
		JPanel medicoesTemperaturaPanel = new JPanel();
		((FlowLayout)medicoesTemperaturaPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Medições Temperatura", null, medicoesTemperaturaPanel, null);
		
		medicoesTemperaturaTable = new JTable();
		medicoesTemperaturaTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"IDMedicao", "DataHoraMedicao", "ValorMedicaoTemperatura"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		medicoesTemperaturaTable.getColumnModel().getColumn(0).setResizable(false);
		medicoesTemperaturaTable.getColumnModel().getColumn(1).setResizable(false);
		medicoesTemperaturaTable.getColumnModel().getColumn(2).setResizable(false);
		medicoesTemperaturaPanel.add(medicoesTemperaturaTable);
		
		JScrollPane scrollPaneMedicoesTemperatura = new JScrollPane(medicoesTemperaturaTable);
		scrollPaneMedicoesTemperatura.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		medicoesTemperaturaPanel.add(scrollPaneMedicoesTemperatura);
		
		//Configuração do Tab Sistema 
		JPanel sistemaPanel = new JPanel();
		((FlowLayout)sistemaPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Sistema", null, sistemaPanel, null);
		
		sistemaTable = new JTable();
		sistemaTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Sistema_ID", "LimiteSuperiorTemperatura", "LimiteInferiorTemperatura", "LimiteSuperiorLuminosidade", "LimiteInferiorLuminosidade"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		sistemaTable.getColumnModel().getColumn(0).setResizable(false);
		sistemaTable.getColumnModel().getColumn(1).setResizable(false);
		sistemaTable.getColumnModel().getColumn(2).setResizable(false);
		sistemaTable.getColumnModel().getColumn(1).setResizable(false);
		sistemaTable.getColumnModel().getColumn(2).setResizable(false);
		sistemaPanel.add(sistemaTable);
		
		JScrollPane scrollPaneSistema = new JScrollPane(sistemaTable);
		scrollPaneSistema.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sistemaPanel.add(scrollPaneSistema);
		
		JButton addSistemaButton = new JButton("Add Sistema");
		sistemaPanel.add(addSistemaButton);
		addSistemaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_sistema.setVisible();
			}
		});
		
		
		//Configuração do Tab Utilizador
		JPanel utilizadorPanel = new JPanel();
		((FlowLayout)utilizadorPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Utilizador", null, utilizadorPanel, null);
		
		utilizadorTable = new JTable();
		utilizadorTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"IDUtilizador", "NomeUtilizador", "TipoUtilizador", "Email"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		utilizadorTable.getColumnModel().getColumn(0).setResizable(false);
		utilizadorTable.getColumnModel().getColumn(1).setResizable(false);
		utilizadorTable.getColumnModel().getColumn(2).setResizable(false);
		utilizadorTable.getColumnModel().getColumn(3).setResizable(false);
		utilizadorPanel.add(utilizadorTable);
		
		JScrollPane scrollPaneUtilizador = new JScrollPane(utilizadorTable);
		scrollPaneUtilizador.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		utilizadorPanel.add(scrollPaneUtilizador);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(3,1,4,4));
		utilizadorPanel.add(buttonsPanel);
		
		JButton refreshUtilizadorBtn = new JButton("Refresh");
		buttonsPanel.add(refreshUtilizadorBtn);
		refreshUtilizadorBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model = admin.getUtilizadorTable(utilizadorTable);
				utilizadorTable.setModel(model);
			}
		});
		
		JButton addUtilizadorBtn = new JButton("Add Utilizador");
		buttonsPanel.add(addUtilizadorBtn);
		addUtilizadorBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_utilizador.turnOnVisible();
			}
		});
		
		JButton deleteUtilizadorBtn = new JButton("Delete");
		buttonsPanel.add(deleteUtilizadorBtn);
		deleteUtilizadorBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_utilizador_delete.resetUtilizadorBox();
				gui_utilizador_delete.turnOnVisible();
			}
		});
		
		
		//Configuração do Tab variaveis
		JPanel variaveisPanel = new JPanel();
		((FlowLayout)variaveisPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Variaveis", null, variaveisPanel, null);
		
		variaveisTable = new JTable();
		variaveisTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"IDVariavel", "NomeVariavel"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		variaveisTable.getColumnModel().getColumn(0).setResizable(false);
		variaveisTable.getColumnModel().getColumn(1).setResizable(false);
		variaveisPanel.add(variaveisTable);
		
		JScrollPane scrollPaneVariaveis = new JScrollPane(variaveisTable);
		scrollPaneVariaveis.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		variaveisPanel.add(scrollPaneVariaveis);
		
		JPanel variavelPanel = new JPanel();
		variavelPanel.setLayout(new GridLayout(3, 1, 4, 4));
		variaveisPanel.add(variavelPanel);
		
		JButton refreshVariavelBtn = new JButton("Refresh");
		variavelPanel.add(refreshVariavelBtn);
		refreshVariavelBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model = admin.getVariaveisTable(variaveisTable);
				variaveisTable.setModel(model);
			}
		});
		
		JButton addVariavelBtn = new JButton("Add Variavel");
		variavelPanel.add(addVariavelBtn);
		addVariavelBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Definir JComboBox
				gui_variavel.resetCulturaBox();
				gui_variavel.turnOnVisible();
			}
		});
		
		JButton deleteVariavelBtn = new JButton("Delete");
		variavelPanel.add(deleteVariavelBtn);
		deleteVariavelBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui_variavel_delete.resetVariavelBox();
				gui_variavel_delete.turnOnVisible();
			}
		});
		
		
		
		//Configuração do Tab Variaveis Medidas
		JPanel variaveisMedidasPanel = new JPanel();
		((FlowLayout)variaveisMedidasPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Variaveis Medidas", null, variaveisMedidasPanel, null);
		
		variaveisMedidasTable = new JTable();
		variaveisMedidasTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"VariaveisMedidas_ID", "IDCultura_fk", "IDVariavel_fk", "LimiteSuperior", "LimiteInferior"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		variaveisMedidasTable.getColumnModel().getColumn(0).setResizable(false);
		variaveisMedidasTable.getColumnModel().getColumn(1).setResizable(false);
		variaveisMedidasTable.getColumnModel().getColumn(2).setResizable(false);
		variaveisMedidasTable.getColumnModel().getColumn(3).setResizable(false);
		variaveisMedidasTable.getColumnModel().getColumn(4).setResizable(false);
		variaveisMedidasPanel.add(variaveisMedidasTable);
		
		JScrollPane scrollPaneVariaveisMedidas = new JScrollPane(variaveisMedidasTable);
		scrollPaneVariaveisMedidas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		variaveisMedidasPanel.add(scrollPaneVariaveisMedidas);
		
		
		//Configuração do Tab Alertas Sensor
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
				int index = tabbedPane.getSelectedIndex();
				System.out.println("Tab: " + tabbedPane.getSelectedIndex());
				DefaultTableModel model;
				String title = tabbedPane.getTitleAt(index);
				System.out.println("Tab: " + title);
				if(title.equals("Cultura")) {
					model = admin.getCulturaTable(culturaTable);
					culturaTable.setModel(model);
				} else if (title.equals("Medições")) {
//					((DefaultTableModel)medicoesTable.getModel()).setRowCount(0);
					model = admin.getMedicoesTable(medicoesTable);
					medicoesTable.setModel(model);
				} else if (title.equals("Medições Luminosidade")) {
					model = admin.getMedicoesLuminosidadeTable(medicoesLuminosidadeTable);
					medicoesLuminosidadeTable.setModel(model);
				} else if (title.equals("Medições Temperatura")) {
					model = admin.getMedicoesTemperaturaTable(medicoesTemperaturaTable);
					medicoesTemperaturaTable.setModel(model);
				} else if (title.equals("Utilizador")) {
					model = admin.getUtilizadorTable(utilizadorTable);
					utilizadorTable.setModel(model);
				} else if (title.equals("Sistema")) {
					model = admin.getSistemaTable(sistemaTable);
//					((DefaultTableModel)sistemaTable.getModel()).setRowCount(0);
					sistemaTable.setModel(model);
				} else if (title.equals("Variaveis")) {
					model = admin.getVariaveisTable(variaveisTable);
					variaveisTable.setModel(model);
				} else if (title.equals("Variaveis Medidas")) {
					model = admin.getVariaveisMedidasTable(variaveisMedidasTable);
					variaveisMedidasTable.setModel(model);
				}
				
				
			}
			
		});
		

	}

}
