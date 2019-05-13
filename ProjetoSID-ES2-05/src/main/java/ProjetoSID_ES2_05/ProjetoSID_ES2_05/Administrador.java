package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import InterfaceGrafica.GUI_Administrador;

public class Administrador {
	
	private Connection conn;
	
	
	public Administrador(String username, String password) {
		connectToMainBase(username, password);
		new GUI_Administrador(this);
	}
	
	
	private void connectToMainBase(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + "5.249.51.0" + "/nossabd_origem", username, password);
			System.out.println("Administrador conectou-se a base de dados MySQL");
		} catch (Exception e) {
			System.out.println("Administrador não se conseguiu conectar a base de dados MySQL!");
			e.printStackTrace();
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Connection Failed", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void disconnectFromMainBase() {
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public DefaultTableModel getCulturaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM cultura";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[4];
			while(rs.next()) {
				row[0] = rs.getString("IDCultura");
				row[1] = rs.getString("NomeCultura");
				row[2] = rs.getString("DescricaoCultura");
				row[3] = rs.getString("IDUtilizador_fk");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getMedicoesTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM medicoes";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[6];
			while(rs.next()) {
				row[0] = rs.getString("IDMedicoes");
				row[1] = rs.getString("IDCultura_fk");
				row[2] = rs.getString("IDVariavel_fk");
				row[3] = rs.getString("DataHoraMedicao");
				row[4] = rs.getString("ValorMedicao");
				row[5] = rs.getString("IDUtilizador_fk");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getMedicoesLuminosidadeTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM medicoesluminosidade";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[3];
			while(rs.next()) {
				row[0] = rs.getString("IDMedicao");
				row[1] = rs.getString("DataHoraMedicao");
				row[2] = rs.getString("ValorMedicaoLuminosidade");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public DefaultTableModel getMedicoesTemperaturaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM medicoestemperatura";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[3];
			while(rs.next()) {
				row[0] = rs.getString("IDMedicao");
				row[1] = rs.getString("DataHoraMedicao");
				row[2] = rs.getString("ValorMedicaoTemperatura");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getSistemaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM sistema";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[5];
			while(rs.next()) {
				row[0] = rs.getString("Sistema_ID");
				row[1] = rs.getString("LimiteSuperiorTemperatura");
				row[2] = rs.getString("LimiteInferiorTemperatura");
				row[3] = rs.getString("LimiteSuperiorLuminosidade");
				row[4] = rs.getString("LimiteInferiorLuminosidade");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public DefaultTableModel getUtilizadorTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM utilizador";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[4];
			while(rs.next()) {
				row[0] = rs.getString("IDUtilizador");
				row[1] = rs.getString("NomeUtilizador");
				row[2] = rs.getString("TipoUtilizador");
				row[3] = rs.getString("Email");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getNomeUtilizadorTable() {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM utilizador";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			String idUtilizador;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				idUtilizador = rs.getString("NomeUtilizador");
				box.addElement(idUtilizador);
			}
			return box;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteUtilizador(String nomeUtilizador) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call RemoverUtilizador(?)}");
			cStmt.setString(1, nomeUtilizador);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel eliminar o utilizador");
		}
	}
	
	public DefaultTableModel getVariaveisTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM variavel";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[3];
			while(rs.next()) {
				row[0] = rs.getString("IDVariavel");
				row[1] = rs.getString("NomeVariavel");
				row[2] = rs.getString("IDCultura_fk");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getVariaveisMedidasTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM variaveismedidas";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[5];
			while(rs.next()) {
				row[0] = rs.getString("VariaveisMedidas_ID");
				row[1] = rs.getString("IDCultura_fk");
				row[2] = rs.getString("IDVariavel_fk");
				row[3] = rs.getString("LimiteSuperior");
				row[4] = rs.getString("LimiteInferior");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void addSistema(int sistema_id, int limiteSuperiorTemperatura, int limiteInferiorTemperatura, int limiteSuperiorLuminosidade, int limiteInferiorLuminosidade) {
		try {
			String queryAddSistema = "INSERT INTO sistema(Sistema_ID, LimiteSuperiorTemperatura, LimiteInferiorTemperatura, LimiteInferiorLuminosidade, LimiteSuperiorLuminosidade) VALUES (" +
					sistema_id + ", " + limiteSuperiorTemperatura + ", " + limiteInferiorTemperatura + ", " + limiteSuperiorLuminosidade + ", " + limiteInferiorLuminosidade + ");";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(queryAddSistema);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Add Sistema Failed!");
			e.printStackTrace();
		}
	}
	
	
	public void addUtilizador(String nomeUtilizador, String tipoUtilizador, String email, String password) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call CriarUtilizador(?, ?, ?, ?)}");
			cStmt.setString(1, nomeUtilizador);
			cStmt.setString(2, tipoUtilizador);
			cStmt.setString(3, email);
			cStmt.setString(4, password);
			cStmt.execute();
			cStmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addVariavel(String nomeCultura, String nomeVariavel) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call insere_variaveis(?, ?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, nomeVariavel);
			cStmt.execute();
			cStmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DefaultComboBoxModel<String> getNomeCultura() {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT NomeCultura FROM cultura";
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			String nomeCultura;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				nomeCultura = rs.getString("NomeCultura");
				box.addElement(nomeCultura);
			}
			return box;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao procurar pelo nome das culturas");
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getNomeVariavel() {
		// TODO Auto-generated method stub
		try {
			String querySelectVariavel = "SELECT * FROM variavel";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySelectVariavel);
			String nomeVariavel;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				nomeVariavel = rs.getString("NomeVariavel");
				box.addElement(nomeVariavel);
			}
			rs.close();
			return box;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteVariavel(String nomeVariavel) {
		try {
			String deleteQuery = "DELETE FROM variavel WHERE NomeVariavel=" +"\"" +nomeVariavel + "\";";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(deleteQuery);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível eliminar a variável");
		}
	}
	
	public void updateUtilizador(String nomeUtilizador, String newNomeUtilizador, String tipoUtilizador, String email, String password) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call AlterarUtilizador(?, ?, ?, ?, ?)}");
			cStmt.setString(1, nomeUtilizador);
			cStmt.setString(2, newNomeUtilizador);
			cStmt.setString(3, tipoUtilizador);
			cStmt.setString(4, email);
			cStmt.setString(5, password);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível modificar o utilizador");
		}
	}
	
	public String[] searchUtilizador(String nomeUtilizador) {
		try {
			String[] v = new String[3];
			Statement stmt = conn.createStatement();
			String querySelect = "SELECT * FROM utilizador WHERE NomeUtilizador=" + "\"" + nomeUtilizador + "\";";
			ResultSet rs = stmt.executeQuery(querySelect);
			rs.next();
			v[0] = rs.getString("NomeUtilizador");
			v[1] = rs.getString("TipoUtilizador");
			v[2] = rs.getString("Email");
			return v;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	
	
	
	public static void main(String[] args) {
		new Administrador("root", "teste123");
	}



}
