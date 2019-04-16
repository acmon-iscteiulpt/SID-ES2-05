package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;




public class Mongo extends Cliente{  //mudar a puta do nome

	public Mongo(String clientID) {
		super(clientID);
		subscribe();
	}
	
	@Override
	public void subscribe() {
		try {
			super.getClient().subscribe("iscte_sid_grupo_05", 2);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			System.out.println("Ocorreu um erro no subscribe da class Mongo");
			e.printStackTrace();
		}
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Mongo recebeu uma mensagem:");
		System.out.println(message.toString());
		
		System.out.println("Dados a guardar na BD em JSON(string):     " + converteDadosSensorEmStringJSON_luminosidade(message.toString()));
        
       
        
        if(converteDadosSensorEmStringJSON_luminosidade(message.toString()) != null){
        	 System.out.println("Inserindo na bd");
        	
        	ObjectMapper mapper = new ObjectMapper();

        	//JSON from String to Object
        	MainParser mp = mapper.readValue(message.toString(), MainParser.class);

        	MongoWrite2 mw2 = new MongoWrite2("Dados_sensoresBD", "dados_luminosidade");

        	mw2.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());

        	mw2.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
        }
		
		
	}
	
	
	public static String converteDadosSensorEmStringJSON_luminosidade(String dadosSensor) {
		
		String nova_luminosidade = null;
		
		ObjectMapper mapper = new ObjectMapper();

		//JSON from String to Object
		MainParser mp;
		try {
			mp = mapper.readValue(dadosSensor, MainParser.class);
			nova_luminosidade = "{\"nomeSensor\":" + "\"luminosidade\"," + "\"valor\":" + mp.getCell() + "," + "\"info\":" + "{\"data\":" + "\"" + mp.getDat() + "\"," +  "\"hora\":" + "\"" + mp.getTim() + "\"" + "}" + "}";
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			nova_luminosidade = null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			nova_luminosidade = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			nova_luminosidade = null;
		}

	
		return nova_luminosidade;
		
	}

}
