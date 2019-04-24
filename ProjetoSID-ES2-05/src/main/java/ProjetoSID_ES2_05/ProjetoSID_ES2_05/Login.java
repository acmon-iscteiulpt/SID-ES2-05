package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Login {
	
	private static Login login = new Login();
	

	private Login() {	
	}
	
	public static Login getInstance() {
		return login;
	}
	
	public void connectToMainBase(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nossabd_origem_melhorada", username, password);
			System.out.println("MySql Database is connected !");
			typeOfUser(username, password);
			conn.close();
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Connection Failed", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void typeOfUser(String username, String password) throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost/nossabd_origem_melhorada", "root", "teste123");
		Statement stmt = conn.createStatement();
		String querySelectTypeOfUser = "SELECT TipoUtilizador FROM utilizador WHERE NomeUtilizador=\"" + username + "\";";
		System.out.println("Query: " + querySelectTypeOfUser);
		ResultSet rs = stmt.executeQuery(querySelectTypeOfUser);
		rs.next();
		String tipoUtilizador = rs.getString("TipoUtilizador");
		System.out.println("TipoUtilizador: " + tipoUtilizador);
		if(tipoUtilizador.toLowerCase().equals("administrador")) {
			System.out.println("É um administrador");
			//Cria-se o objeto administrador --> dentro do construtor do objeto Administrador, instancia-se o GUI_Administrador
		} else if (tipoUtilizador.toLowerCase().equals("investigador")) {
			System.out.println("É um investigador");
			//Cria-se o objeto investigador --> dentro do construtor do objeto Investigador, instancia-se o GUI_Investigador
		}
		conn.close();
	}
	


}
