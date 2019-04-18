package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import com.mongodb.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.json.JSONObject;



public class MongoRead  {

	private static final String bd = "sid_db";
	private static final String collection = "dados";
	
	private Connection conn;
	private MongoClient mongoClient;
	private DB db;
	DBCollection table;
	
	public MongoRead(String bd, String collection) {
		this.mongoClient = new MongoClient();
		db = mongoClient.getDB(bd);
		table = db.getCollection(collection);
	}


	public void mostraMongoDB() {
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
	
	//Tenho que ir buscar o ID maximo
	// 2019-04-10 13:24:17 -->TimeStamp
	public void introduzirInformacao(String dataHoraMedicao, String valorMedicao, String tipoSensor) {
		try {
			Statement stmt = conn.createStatement();
			String dataHoraMedicao2 = "\'22:11:05\'";
			String valorMedicaoTemperatura = "10";
			int idMedicao = 1;
			String query = "INSERT INTO medicoestemperatura (DataHoraMedicao, ValorMedicaoTemperatura, IDMedicao) " + "VALUES(" + dataHoraMedicao2 + ", " 
								+ valorMedicaoTemperatura + ", " + idMedicao +");";
			if(tipoSensor.equals("temperatura")) {
				query = "INSERT INTO medicoestemperatura (DataHoraMedicao, ValorMedicaoTemperatura, IDMedicao) " + "VALUES(" + dataHoraMedicao2 + ", " 
						+ valorMedicaoTemperatura + ", " + idMedicao +");";
			} else if(tipoSensor.equals("luminosidade")) {
				query = "INSERT INTO medicoesluminosidade (DataHoraMedicao, ValorMedicaoLuminosidade, IDMedicao) " + "VALUES(" + dataHoraMedicao2 + ", " 
						+ valorMedicaoTemperatura + ", " + idMedicao +");";
			}
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
	
	//Vai ter que ler o mongo db e enviar para a base de dados principal de 1 em 1 minuto
	public void Timer() {
		Timer timer = new Timer();
		final long segundos = (1000 * 30);
		TimerTask tarefa = new TimerTask() {

			@Override
			public void run() {
				System.out.println("Vou migrar os dados: " + new Date());
				//lê o mongodb com os valores que ainda nao foram exportados --> guardar lista??
				DBCursor cursor = table.find();
				while(cursor.hasNext()) {
					BasicDBObject obj = (BasicDBObject) cursor.next();
					String data;
					String hora;
					String dataHoraMedicao;
					String valorMedicao;
					System.out.println(obj.toJson().toString());
					if(obj.containsValue("temperatura")) {
						System.out.println("Sensor Temperatura");
						data = obj.getString("info.data");
						hora = obj.getString("info.hora");
						dataHoraMedicao = new String("\'" + data + " " + hora +"\'");
						valorMedicao = obj.getString("valor");
						System.out.println("DataHoraMedicao: " + dataHoraMedicao);
						System.out.println("Valor: " + valorMedicao);
					} else if (obj.containsValue("luminosidade")) {
						System.out.println("Sensor Luminosidade!");
						data = obj.getString("info.data");
						hora = obj.getString("info.hora");
						valorMedicao = obj.getString("valor");
						dataHoraMedicao = new String("\'" + data + " " + hora +"\'");
						System.out.println("Hora: " + dataHoraMedicao);
						System.out.println("Valor: " + valorMedicao);
					}
				}
			}
			
		};
		timer.scheduleAtFixedRate(tarefa, segundos, segundos);
	}
	



	public static void main(String[] args) {
		MongoRead m = new MongoRead(MongoRead.bd, MongoRead.collection);
		m.Timer();
	}



}