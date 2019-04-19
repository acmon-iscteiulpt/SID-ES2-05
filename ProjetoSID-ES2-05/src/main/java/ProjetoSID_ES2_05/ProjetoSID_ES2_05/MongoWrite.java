package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoWrite {

	private MongoClient mongoClient1;
	private DB db;
	DBCollection table;
	
	
	public MongoWrite(String bd, String collection) {
		this.mongoClient1 = new MongoClient();
		// usar esta linha em detrimento da anterior, caso tenham criado um cluster de replicas no vosso pc  [Nao apagar estes comments sff ] 
		//this.mongoClient1 = new MongoClient(new MongoClientURI("mongodb://localhost:27018,localhost:25017,localhost:23017/?replicaSet=replicademo"));
		table = db.getCollection(collection);	
	}
	
	
	public void write(String nomeSensor, double valor, String data, String hora) {
		BasicDBObject doc = new BasicDBObject("nomeSensor", nomeSensor).
                append("valor", valor).
                append("info", new BasicDBObject("data", data).append("hora", hora)).append("exportado", false);
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
