package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;




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
		System.out.println("Mongo recebeu uma mensagem:  " + message.toString());

		JSONObject jsonObj = new JSONObject(message.toString());                                          //tornar a mensagem recebida num json object para em baixo verificar quais campos exeitem nesse json

		MongoWrite2 mw2 = new MongoWrite2("Dados_sensoresBD", "dados_luminosidade");                      //ligação à BD
		
		if(jsonObj.has("tmp") && jsonObj.has("cell") && jsonObj.has("dat") && jsonObj.has("tim")) {

			System.out.println("tem |tmp|  , |cell|  , |tim|   ,   |dat|");
			System.out.println("Inserindo na bd");

			ObjectMapper mapper = new ObjectMapper();

			//JSON from String to Object
			MainParser mp = mapper.readValue(message.toString(), MainParser.class);



			mw2.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());

			mw2.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
		}
		if(jsonObj.has("tmp") && !jsonObj.has("cell") && jsonObj.has("dat") && jsonObj.has("tim")) {
			System.out.println("tem tudo menos a cell(luminosidade)");
			System.out.println("Inserindo dados apenas da temperatura na bd");

			ObjectMapper mapper = new ObjectMapper();

			//JSON from String to Object
			MainParser mp = mapper.readValue(message.toString(), MainParser.class);

			mw2.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());

		}
		if(!jsonObj.has("tmp") && jsonObj.has("cell") && jsonObj.has("dat") && jsonObj.has("tim")) {
			System.out.println("tem tudo menos a tmp(temperatura)");
			System.out.println("Inserindo dados apenas da temperatura na bd");

			ObjectMapper mapper = new ObjectMapper();

			//JSON from String to Object
			MainParser mp = mapper.readValue(message.toString(), MainParser.class);

			mw2.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());


		}

	}


	public static void main(String[] args) {
		new Mongo("1");
	}

}
