package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Sensor extends Cliente {

	public Sensor(String clientID) {
		super(clientID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		System.out.println("Sensor enviou mensagem!");
	}


	@Override
	public void sendValues(String json) {
		if(!super.getClient().isConnected())
			super.connectToServer();
		String topic = "iscte_sid_grupo_05";
		MqttMessage message1 = new MqttMessage(json.getBytes());
		message1.setQos(super.qos);
		try {
			super.getClient().publish(topic, message1);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao enviar dados pelo sensor");
			e.printStackTrace();
		}
	}
	
	
}