package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class Subscriber extends Cliente {

	private static final String bd = "Dados_sensoresBD";
	private static final String collection = "dados_luminosidade";
	
	private MongoWrite mw;
	private MongoRead mr;

	public Subscriber(String clientID) {
		super(clientID);
		subscribe();
		this.mw = new MongoWrite(bd, collection);
		this.mr = new MongoRead(bd, collection);
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
		// tornar a mensagem recebida num json object para em baixo verificar quais
		// campos exeitem nesse json
		JSONObject jsonObj = new JSONObject(message.toString());
		
		ObjectMapper mapper = new ObjectMapper();

		// JSON from String to Object
		MainParser mp = mapper.readValue(message.toString(), MainParser.class);
		
		
		
		
		Date data_hora = new Date();                                                 // ir buscar data e hora atual, ou seja, do momento da mediçao do sensor
		SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/Y");
		SimpleDateFormat formato_hora = new SimpleDateFormat("hh:mm:ss");
				
		mp.setDat(formato_data.format(data_hora).toString());
		mp.setTim(formato_hora.format(data_hora).toString());

		
		
		
		if (jsonObj.has("tmp") && jsonObj.has("cell")) {
			System.out.println("tem |tmp|  , |cell|  , |tim|   ,   |dat|");
			System.out.println("Inserindo na bd");

			
			mw.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());
			mw.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
		}
		if (jsonObj.has("tmp") && !jsonObj.has("cell")) {
			System.out.println("tem tudo menos a cell(luminosidade)");
			System.out.println("Inserindo dados apenas da temperatura na bd");



			mw.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());

		}
		if (!jsonObj.has("tmp") && jsonObj.has("cell")) {
			System.out.println("tem tudo menos a tmp(temperatura)");
			System.out.println("Inserindo dados apenas da temperatura na bd");


			mw.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());

		}
		
	}

	public static void main(String[] args) {
		new Subscriber("1");
	}

}
