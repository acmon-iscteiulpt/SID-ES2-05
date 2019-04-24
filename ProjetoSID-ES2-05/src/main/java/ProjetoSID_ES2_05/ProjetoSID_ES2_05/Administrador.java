package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Administrador {
	
	private Connection conn;
	
	
	public Administrador(String username, String password) {
		connectToMainBase(username, password);
		new GUI_Administrador(this);
	}
	
	
	private void connectToMainBase(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nossabd_origem_melhorada", username, password);
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
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
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
	
	public void getMedicoesTable() {
		
	}
	
	public void getMedicoesLuminosidadeTable() {
		
	}
	
	public void getMedicoesTemperaturaTable() {
		
	}
	
	public void getSistemaTable() {
		
	}
	
	public void getUtilizadorTable() {
		
	}
	
	public void getVariaveisTable() {
		
	}
	
	public void getVariaveisMedidasTable() {
		
	}
	
	public void getAlertasSensorTable() {
		
	}
	
	public void getAlertasMedicoesTable() {
		
	}
	
	public static void main(String[] args) {
		new Administrador("root", "teste123");
	}

}
