package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class Cliente implements MqttCallback{
	
	private static final String broker = "tcp://broker.mqtt-dashboard.com:1883";
	
	private String clientID;
	private MqttClient client;
	private MqttConnectOptions connOpts;
	
	public Cliente(String clientID) {
		this.clientID = clientID;
		initialize();
		connectToServer();
	}
	
	protected void initialize() {
		try {
			client = new MqttClient(broker, clientID);
			client.setCallback(this);
			connOpts = new MqttConnectOptions();
			connOpts.setAutomaticReconnect(true);
			connOpts.setCleanSession(true);
			connOpts.setConnectionTimeout(10);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}   
	
	
	
	
	protected void connectToServer() {
		try {
			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println("Catch método connectToServer");
			e.printStackTrace();
		}
	}
	
	public void disconnectServer() {
		try {
			client.disconnect();
			System.out.println("Disconnected");
		} catch (Exception e) {
			System.out.println("Catch método disconnectServer");
			e.printStackTrace();
		}
	}
	
	public void sendValues(String json) {
		
	}
	
	public void subscribe(String topic) {
		try {
			client.subscribe(topic);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			System.out.println("Não foi possível subcrever o topico");
			e.printStackTrace();
		}
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		System.out.println("Connection Lost!");
		System.out.println(cause.toString());
		connectToServer();
		if(client.isConnected()) {
			System.out.println("Cliente está novamente connectado!");
		}
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

	public String getClientID() {
		return clientID;
	}

	public MqttClient getClient() {
		return client;
	}

	public MqttConnectOptions getConnOpts() {
		return connOpts;
	}
	
	
	

}

