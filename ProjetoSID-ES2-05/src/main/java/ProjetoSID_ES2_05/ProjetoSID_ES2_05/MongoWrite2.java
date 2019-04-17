package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoWrite2 {

	private MongoClient mongoClient1;
	private DB db;
	DBCollection table;
	
	
	public MongoWrite2(String bd, String collection) {
		MongoClient mongoClient1 = new MongoClient();
		db = mongoClient1.getDB(bd);
		table = db.getCollection(collection);	
	}
	
	
	public void write(String nomeSensor, double valor, String data, String hora) {
		BasicDBObject doc = new BasicDBObject("nomeSensor", nomeSensor).
                append("valor", valor).
                append("info", new BasicDBObject("data", data).append("hora", hora));
		try { 
			table.insert(doc);
		} 
		catch (Exception e) {
			System.out.println("Catch método write");
			e.printStackTrace();
		}
	}
	
	public void close() {
		mongoClient1.close();
	}

	

}
