package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Login {
	
	private static String nomeBaseDados = "nossabd_origem";
	
	private static Login login = new Login();
	

	private Login() {	
	}
	
	public static Login getInstance() {
		return login;
	}
	
	
	/**
	 * Cliente conecta-se introduzindo as credenciais. Se este não pertencer a base de dados então vai ser lançado uma janela de aviso.
	 * Ao conectar-se com sucesso, vai se chamar o método typeOfUser para saber que GUI abrir - investigador ou administrador
	 * @param username
	 * @param password
	 */
	public boolean connectToMainBase(String username, String password) {
		boolean connected;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/"+nomeBaseDados, username, password);
			System.out.println("MySql Database is connected !");
			typeOfUser(username, password);
			connected = true;
			conn.close();
			return connected;
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Connection Failed", "Warning", JOptionPane.WARNING_MESSAGE);
			connected = false;
			return connected;
		}
	}
	
	/**
	 * Este método só é chamado quando o utilizador acede com sucesso a base de dados. Vai-se abrir uma interface gráfica nova de 
	 * acordo com o tipo de utilizador que é.
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	//Está-se a usar as credenciais do root para poder aceder a tabela de utilizadores
	public void typeOfUser(String username, String password) throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost/"+nomeBaseDados, username, password);
		Statement stmt = conn.createStatement();
		String querySelectTypeOfUser = "SELECT TipoUtilizador FROM utilizador WHERE NomeUtilizador=\"" + username + "\";";
		System.out.println("Query: " + querySelectTypeOfUser);
		ResultSet rs = stmt.executeQuery(querySelectTypeOfUser);
		rs.next();
		String tipoUtilizador = rs.getString("TipoUtilizador");
		System.out.println("TipoUtilizador: " + tipoUtilizador);
		if(tipoUtilizador.toLowerCase().equals("administrador")) {
			System.out.println("É um administrador");
			new Administrador(username, password);
		} else if (tipoUtilizador.toLowerCase().equals("investigador")) {
			System.out.println("É um investigador");
			new Investigador(username, password);
		}
		conn.close();
	}
	


}
