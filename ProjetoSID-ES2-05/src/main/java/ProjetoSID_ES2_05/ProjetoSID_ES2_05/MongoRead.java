package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import com.mongodb.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.json.JSONObject;



public class MongoRead  {

	private static final String bd = "Dados_sensoresBD";
	private static final String collection = "dados_luminosidade";
	
	private Connection conn;


	public void mostraMongoDB() {
		MongoClient mongoClient1 = new MongoClient();

		DB db = mongoClient1.getDB(bd);
		DBCollection table = db.getCollection(collection);
		DBCursor cursor = table.find();
		while(cursor.hasNext()) {

			System.out.println(cursor.next());
		}
	}
	
	
	public void connectToMainBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/bd_origem", "root", "teste123");
			System.out.println("Database is connected !");
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
	}
	
	public void disconnectFromMainBase() {
		try {
			conn.close();
			System.out.println("Disconnected from Main Base!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Disconnect failed!");
			e.printStackTrace();
		}
	}
	
	public void introduzirInformacao() {
		try {
			Statement stmt = conn.createStatement();
			String dataHoraMedicao = "\'22:11:05\'";
			String valorMedicaoTemperatura = "10";
			int idMedicao = 1;
			String query = "INSERT INTO medicoestemperatura (DataHoraMedicao, ValorMedicaoTemperatura, IDMedicao) " + "VALUES(" + dataHoraMedicao + ", " 
								+ valorMedicaoTemperatura + ", " + idMedicao +");";
			stmt.execute(query);
			System.out.println("Introdução na base de dados feita com sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Introduzir informação na base de dados principal falhou !");
			e.printStackTrace();
		}
		
	}
	
	
	public void lerMainBase() {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Cultura";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String nomeCultura = rs.getString("NomeCultura");
				String descricao = rs.getString("DescricaoCultura");
				int id = rs.getInt("Utilizador_IDUtilizador");
				System.out.println(nomeCultura + " // " + descricao + " // --> ID: " + id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static void main(String[] args) {
		MongoRead m = new MongoRead();
		m.connectToMainBase();
		m.introduzirInformacao();
		m.disconnectFromMainBase();
	}



}