package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Mongo extends Cliente{

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
		System.out.println(new String(message.getPayload()));
		
	}

}
