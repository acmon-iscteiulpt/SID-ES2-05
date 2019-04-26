package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
			CallableStatement cStmt = conn.prepareCall("{call mostra_culturas_utilizador()}");
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
	
	
	
	public static void main(String[] args) {
		new Investigador("root", "teste123");
	}

}
