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

	/*
	 * (non-Javadoc)
	 * 
	 * @see ProjetoESII.ProjetoESII.Cliente#sendValues(int, int)
	 * 
	 * Aspetos a melhorar: Enviar os valores apenas numa mensagem, não implementei
	 * porque fui preguiçoso
	 */
	@Override
	public void sendValues(int valorTemperatura, int valorLuminosidade) {
		if(!super.getClient().isConnected())
			super.connectToServer();
		String topic = "iscte_sid_grupo_05";
		String content1 = "Valor Temperatura: " + valorTemperatura;
		String content2 = "Valor Luminosidade: " + valorLuminosidade;
		MqttMessage message1 = new MqttMessage(content1.getBytes());
		MqttMessage message2 = new MqttMessage(content2.getBytes());
		message1.setQos(super.qos);
		message2.setQos(super.qos);
		try {
			super.getClient().publish(topic, message1);
			super.getClient().publish(topic, message2);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao enviar dados pelo sensor");
			e.printStackTrace();
		}
	}
	
	
}