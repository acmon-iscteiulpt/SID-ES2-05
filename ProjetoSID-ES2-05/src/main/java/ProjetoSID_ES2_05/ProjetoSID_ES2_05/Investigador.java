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

import InterfaceGrafica.GUI_Investigador;

public class Investigador {
	
	private Connection conn;
	private String username;
	
	public Investigador(String username, String password) {
		this.username = username;
		connectToMainBase(username, password);
		new GUI_Investigador(this);
		
	}
	
	private void connectToMainBase(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nossabd_origem", username, password);
			System.out.println("Investigador conectou-se a base de dados MySQL");
		} catch (Exception e) {
			System.out.println("Investigador não se conseguiu conectar a base de dados MySQL!");
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
	
	public void addCultura(String nomeCultura, String descricaoCultura) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call insere_cultura(?, ?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, descricaoCultura);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Criar SP que faz delete da cultura 
	//Perguntar se o nome da cultura vai ser única ou não
	
	//Se não for única, temos que eliminar a tabela cultura pelo ID 
	public void deleteCultura(int idCultura) {
		try {
			Statement stmt = conn.createStatement();
			String queryDeleteCultura = "DELETE FROM cultura WHERE IDCultura=" + idCultura + ";";
			stmt.executeUpdate(queryDeleteCultura);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não pode eliminar tabelas de outros investigadores!");
		}
	}
	
	public DefaultTableModel getCulturaTable(JTable table) {
		try {
//			Statement stmt = conn.createStatement();
//			String querySelectCultura = "SELECT * FROM cultura";
//			System.out.println("Query: " + querySelectCultura);
			CallableStatement cStmt = conn.prepareCall("{call mostra_culturas_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
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
			rs.close();
			cStmt.close();
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getMedicaoTable(JTable table) {
		try {
//			Statement stmt = conn.createStatement();
//			String querySelectCultura = "SELECT * FROM cultura";
//			System.out.println("Query: " + querySelectCultura);
			CallableStatement cStmt = conn.prepareCall("{call mostra_medicoes_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
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
			rs.close();
			cStmt.close();
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getNomeCultura() {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_culturas_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			String nomeCultura;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				nomeCultura = rs.getString("NomeCultura");
				box.addElement(nomeCultura);
			}
			cStmt.close();
			return box;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao procurar pelo nome das culturas");
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getNomeVariavel() {
		try {
//			CallableStatement cStmt = conn.prepareCall("{call mostra_variavel_utilizador()}");
//			cStmt.execute();
//			ResultSet rs = cStmt.getResultSet();
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
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getID_MedicaoTable() {
		try {
//			Statement stmt = conn.createStatement();
//			String querySelectCultura = "SELECT * FROM cultura";
//			System.out.println("Query: " + querySelectCultura);
			CallableStatement cStmt = conn.prepareCall("{call mostra_medicoes_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			String id;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				id = rs.getString("IDMedicoes");
				box.addElement(id);
			}
			rs.close();
			cStmt.close();
			return box;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//SP ainda nao funciona
	public void addMedicaoTable(String nomeCultura,String nomeVariavel ,String data, String time, String valorMedicao) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call inserir_medicao(?, ?, ?, ?)}");
			String dataHora = data + " " + time;
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, nomeVariavel);
			cStmt.setString(3, dataHora);
			cStmt.setString(4, valorMedicao);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteMedicaoTable(String idMedicao) {
		try {
			int id = Integer.parseInt(idMedicao);
			String queryDelete = "DELETE FROM medicoes WHERE IDMedicoes=" + id + ";";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(queryDelete);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "ID tem que ser um valor numérico!");
		}
	}
	
	public String[] searchCultura(String nomeCultura) {
		try {
			String[] v = new String[1];
			Statement stmt = conn.createStatement();
			String querySelect = "SELECT * FROM cultura WHERE NomeCultura=" + "\"" + nomeCultura + "\";";
			ResultSet rs = stmt.executeQuery(querySelect);
			rs.next();
			v[0] = rs.getString("DescricaoCultura");
			return v;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public void updateCultura(String nomeCultura, String newNomeCultura, String descricaoCultura) {
		System.out.println("UpdateCultura");
	}
	
	public String[] searchMedicao(String idMedicao) {
		try {
			String[] v = new String[3];
			Statement stmt = conn.createStatement();
			String querySelect = "SELECT * FROM medicoes WHERE IDMedicoes=" + "\"" + idMedicao + "\";";
			String dataHoraMedicao;
			String[] dataHoraMedicaoV;
			ResultSet rs = stmt.executeQuery(querySelect);
			rs.next();
			dataHoraMedicao = rs.getString("DataHoraMedicao");
			dataHoraMedicaoV = dataHoraMedicao.split(" ");
			v[0] = dataHoraMedicaoV[0];
			v[1] = dataHoraMedicaoV[1];
			v[2] = rs.getString("ValorMedicao");
			return v;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public void updateMedicao(String idMedicao, String data, String hora, String valorMedicao) {
		System.out.println("Update Medicao");
	}
	
	
	
	public static void main(String[] args) {
		new Investigador("root", "teste123");
	}



}
