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
	
	public DefaultTableModel getCulturaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM cultura";
			System.out.println("Query: " + querySelectCultura);
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
	
	
	public static void main(String[] args) {
		new Investigador("root", "teste123");
	}

}
