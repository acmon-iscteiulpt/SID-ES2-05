package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import com.mongodb.*;



import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.json.JSONObject;



public class MongoRead  {

	private static final String bd = "Dados_sensoresBD";
	private static final String collection = "dados_luminosidade";


	public void mostraMongoDB() {
		MongoClient mongoClient1 = new MongoClient();

		DB db = mongoClient1.getDB(bd);
		DBCollection table = db.getCollection(collection);
		DBCursor cursor = table.find();
		while(cursor.hasNext()) {

			System.out.println(cursor.next());
		}
	}







}