package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import com.mongodb.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONObject;



public class MongoRead  {

	private static final String bd = "Dados_sensoresBD";
	private static final String collection = "dados_luminosidade";
	
	private Connection conn;
	private MongoClient mongoClient;
	private DB db;
	DBCollection table;
	
	public MongoRead(String bd, String collection) {
		this.mongoClient = new MongoClient();
		db = mongoClient.getDB(bd);
		table = db.getCollection(collection);
		connectToMainBase();
		Timer();
	}
	
	
	public void connectToMainBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nossabd_origem", "root", "teste123");
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
	public void introduzirInformacao(String data, String hora, String valorMedicao, String tipoSensor) {
		try {
			Statement stmt = conn.createStatement();
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format1.parse(data);
			String dataOficial = format2.format(date);
			String dataHoraMedicao = "\'" + dataOficial + " " + hora + "\'";
			String querySelectId = "SELECT COALESCE(MAX(IDMedicao)+1,1) FROM medicoes" + tipoSensor + ";";
			ResultSet rs = stmt.executeQuery(querySelectId);
			rs.next();
			int idMedicao = rs.getInt("COALESCE(MAX(IDMedicao)+1,1)");
			String query = null;
			if(tipoSensor.equals("temperatura")) {
				query =  "INSERT INTO medicoestemperatura (IDMedicao, DataHoraMedicao, ValorMedicaoTemperatura) " + "VALUES(" + idMedicao + ", " 
						+ dataHoraMedicao + ", " + valorMedicao +");";
			} else if(tipoSensor.equals("luminosidade")) {
				query = "INSERT INTO medicoesluminosidade (IDMedicao, DataHoraMedicao, ValorMedicaoLuminosidade) " + "VALUES(" + idMedicao + ", " 
						+ dataHoraMedicao + ", " + valorMedicao +");";
			}
			stmt.execute(query);
			System.out.println("Introdução na base de dados feita com sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Introduzir informação na base de dados principal falhou !");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//Vai ter que ler o mongo db e enviar para a base de dados principal de 1 em 1 minuto
	public void Timer() {
		Timer timer = new Timer();
		final long segundos = (1000 * 60);
		TimerTask tarefa = new TimerTask() {

			@Override
			public void run() {
				
				//lê o mongodb com os valores que ainda nao foram exportados --> guardar lista??
				DBCursor cursor = table.find();
				System.out.println("Vou migrar os dados: " + new Date());
				while(cursor.hasNext()) {
					BasicDBObject obj = (BasicDBObject) cursor.next();
					BasicDBObject info = (BasicDBObject) obj.get("info");
					String data = info.getString("data");
					String hora = info.getString("hora");
					String valorMedicao = obj.getString("valor");
					System.out.println(obj.toJson().toString());
					if(obj.getString("nomeSensor").equals("temperatura")) {
						introduzirInformacao(data, hora, valorMedicao, "temperatura");
					} else if (obj.getString("nomeSensor").equals("luminosidade")) {
						introduzirInformacao(data, hora, valorMedicao, "luminosidade");
					}
					
					
					// passagem do campo exportado de false para true
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.append("$set", new BasicDBObject().append("exportado", true));							
					BasicDBObject searchQuery = new BasicDBObject().append("exportado", false);
					table.update(searchQuery, newDocument);
				}
			}
			
		};
		timer.scheduleAtFixedRate(tarefa, segundos, segundos);
	}
	



}