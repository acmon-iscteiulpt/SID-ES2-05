package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class Cliente implements MqttCallback{
	
//	private static final String clientID = "1";
	private static final String broker = "tcp://iot.eclipse.org:1883";
	protected static final int qos = 2;
	
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
			MemoryPersistence persistence = new MemoryPersistence();
			client = new MqttClient(broker, clientID, persistence);
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
	
	protected void disconnectServer() {
		try {
			client.disconnect();
			System.out.println("Disconnected");
		} catch (Exception e) {
			System.out.println("Catch método disconnectServer");
			e.printStackTrace();
		}
	}
	
	public void sendValues(int valor1, int valor2) {
		
	}
	
	public void subscribe() {
		
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		System.out.println("Connection Lost!");
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

