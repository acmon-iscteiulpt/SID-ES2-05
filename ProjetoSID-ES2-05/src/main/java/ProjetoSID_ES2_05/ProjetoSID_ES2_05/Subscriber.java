package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class Subscriber implements MqttCallback {

	private static final String username = "engenheiroses1@gmail.com";
	private static final String password = "omiaoegay";
	private static final String bd = "bd_dados_sensores";
	private static final String collection = "collection_dados_sensores";
	private static final String broker = "tcp://broker.mqtt-dashboard.com:1883";
	private static final String topic = "/sid_lab_2019_2";
	private static final int discrepanciaTemperatura = 10;
	private static final int discrepanciaLuminosidade = 10;
	
	private Connection conn;
	private MqttClient client;
	private MqttConnectOptions connOpts;
	private MongoWrite mw;
	private MongoRead mr;
	private boolean isFirstValueTemperatura = false;
	private boolean isFirstValueLuminosidade = false;
	private double lastResultadoLuminosidade;
	private double lastResultadoTemperatura;
	private int limiteSuperiorLuminosidade;
	private int limiteInferiorLuminosidade;
	private int limiteSuperiorTemperatura;
	private int limiteInferiorTemperatura;
	private double percentagemLuminosidade;
	private double percentagemTemperatura;
	private int toleranciaTemperatura = 3;
	private int toleranciaLuminosidade = 3;
	private ArrayList<ValorSensor> arrayToleranciaTemperatura = new ArrayList<ValorSensor>(); 
	private ArrayList<ValorSensor> arrayToleranciaLuminosidade = new ArrayList<ValorSensor>();
	private ArrayList<ValorSensor> valoresTemperaturaTransportadosMongo = new ArrayList<ValorSensor>();
	private ArrayList<ValorSensor> valoresLuminosidadeTransportadosMongo = new ArrayList<ValorSensor>();
	private ArrayList<String> emails = new ArrayList<String>();

	public Subscriber() {
		try {
			connectToMainBase();
			setLimites();
			initialize();
			connectToServer();
			client.subscribe(topic);
			this.mw = new MongoWrite(bd, collection);
			this.mr = new MongoRead(bd, collection);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro na construcao do objeto subscriber");
			e.printStackTrace();
		}
		
	}

	
	public void initialize() {
		try {
			client = new MqttClient(broker, "sid_es_05");
			client.setCallback(this);
			connOpts = new MqttConnectOptions();
			connOpts.setAutomaticReconnect(true);
			connOpts.setCleanSession(true);
//			connOpts.setConnectionTimeout(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void connectToServer() {
		try {
			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println("Catch método connectToServer");
		}
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		System.out.println("Connection Lost!");
		try {
			client.reconnect();
			System.out.println("Cliente está reconnectar-se");
			if(client.isConnected()) {
				System.out.println("Cliete está conectado novamente!");
			} else {
				System.out.println("Cliente ainda não se reconnectou");
				System.out.println("Cause: " + cause.toString());
			}
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			System.out.println("Cliente não se conseguiu reconnectar");
			e.printStackTrace();
		}
		
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Chegou ao topico" + topic + " a seguinte mensagem:  " + message.toString());
		String json = message.toString().replace("\"\"", "\",\"");
		System.out.println("JSON CORRETO: " + json);
		// tornar a mensagem recebida num json object para em baixo verificar quais
		// campos exeitem nesse json
		trataJSON(json);

		
		
	}
	
	public void trataJSON(String json) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Vou tratar JSON");
		if(json.contains("{") && json.contains("}")) {
			JSONObject jsonObj = new JSONObject(json);
			
			ObjectMapper mapper = new ObjectMapper();

			// JSON from String to Object
			MainParser mp = mapper.readValue(json, MainParser.class);
			
			
			
			
			Date data_hora = new Date();                                                 // ir buscar data e hora atual, ou seja, do momento da mediçao do sensor
			SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/Y");
			SimpleDateFormat formato_hora = new SimpleDateFormat("HH:mm:ss");
					
			mp.setDat(formato_data.format(data_hora).toString());
			mp.setTim(formato_hora.format(data_hora).toString());

			
			
			
			if (jsonObj.has("tmp") && jsonObj.has("cell")) {
				System.out.println("Inserindo na mongoDB");

				if(!descartarValorTemperatura(mp.getTmp(), mp.getDat(), mp.getTim())) {
					mw.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());
				}
				if(!descartarValorLuminosidade(mp.getCell(), mp.getDat(), mp.getTim())) {
					mw.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
				}
				
			}
			if (jsonObj.has("tmp") && !jsonObj.has("cell")) {
				System.out.println("tem tudo menos a cell(luminosidade)");
				System.out.println("Inserindo na mongoDB dados apenas da temperatura na bd");


				if(!descartarValorTemperatura(mp.getTmp(), mp.getDat(), mp.getTim())) {
					mw.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());
				}


			}
			if (!jsonObj.has("tmp") && jsonObj.has("cell")) {
				System.out.println("tem tudo menos a tmp(temperatura)");
				System.out.println("Inserindo na mongoDB dados apenas da luminosidade na bd");
				
				if(!descartarValorLuminosidade(mp.getCell(), mp.getDat(), mp.getTim())) {
					mw.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
				}
				

			}
			
		}
	}
	

	
	/*
	 * Método que retorna um boolean a indicar se descarta o valor ou não
	 * 	--> Só descarta o valor quando há modificação no atributo lastResultadoTemperatura;
	 */
	public boolean descartarValorTemperatura(double valorTemperatura, String data, String hora) {
		//Recebe o primeiro valor da temperatura --> vai se guardar independentemente se for absurdo
		if(!isFirstValueTemperatura) {
			System.out.println("Subscriber já recebeu o primeiro valor de Temperatura do sensor");
			lastResultadoTemperatura = valorTemperatura;
			isFirstValueTemperatura = true;
			valoresTemperaturaTransportadosMongo.add(new ValorSensor(valorTemperatura, data, hora));
		} else {
			/*
			 * Verificar se o valor está dentro do intervalo de discrepância
			 * Independetemente dos valores não estarem por dentros do limites vamos enviar para o mongo --> desde que sejam constantes faz sentido ir para o mongo
			 */
			if(!valorTemperaturaComDiscrepancia(valorTemperatura)) {
				System.out.println("Valores Temperatura NORMAL");
				lastResultadoTemperatura = valorTemperatura;
				valorDentroDosLimites_Temperatura(valorTemperatura);
				toleranciaTemperatura = 3;
				valoresTemperaturaTransportadosMongo.add(new ValorSensor(valorTemperatura, data, hora));
				return false;
			//Valor da temperatura com grande discrepancia
			} else {
				System.out.println("Valores Temperatura com Discrepancia");
				toleranciaTemperatura--;
				//so adicionamos o valor da nova temperatura se não tiver grande discrepancia
				//Enquanto a tolerancia nao for igual a zero não enviamos para a o mongodb
				if(toleranciaTemperatura != 0) {
					if(arrayToleranciaTemperatura.isEmpty()) {
						System.out.println("Array está vazio, insere-se o primeiro valor com grande discrepância");
						arrayToleranciaTemperatura.add(new ValorSensor(valorTemperatura, data, hora));
					} else {
						if(valorTemperaturaAnormalComDiscrepancia(valorTemperatura)) {
							System.out.println("Valor anormal com discrepância --> ArrayTolerancia vai reinicializar");
							toleranciaTemperatura = 3;
							arrayToleranciaTemperatura.clear();
						} else {
							System.out.println("Valor anormal constante --> foi adicionado o valor no arrayTolerancia");
							arrayToleranciaTemperatura.add(new ValorSensor(valorTemperatura, data, hora));
						}
					}
				//O numero de tolerancias foi atingido
				} else {
					//Se o valor anormal tiver discrepancia --> ignoramos, mas enviamos o array a mesma 
					if(valorTemperaturaAnormalComDiscrepancia(valorTemperatura)) {
						System.out.println("Numero de tolerancias passou a zero");
						System.out.println("Valor anormal com discrepância --> nao foi adicionado o valor no arrayTolerancia");
						toleranciaTemperatura = 3;
						showArrayToleranciaTemperatura();
						addValoresTemperaturaMongo();
						//Enviar os valores do array tolerancia para o mongo --> fazer clear
						sendValoresTemperaturaToMongo();
					//O valor anormal é constante --> adiciona-se no array, enviamos o array
					} else {
						System.out.println("Numero de tolerancias passou a zero");
						System.out.println("Valor anormal constante --> foi adicionado o valor no arrayTolerancia");
						toleranciaTemperatura = 3;
						arrayToleranciaTemperatura.add(new ValorSensor(valorTemperatura, data, hora));
						showArrayToleranciaTemperatura();
						addValoresTemperaturaMongo();
						//enviar os valroes do array tolerancia para o mongo --> fazer clear
						sendValoresTemperaturaToMongo();
					}
				}
				System.out.println("Descarta valor: " + true + " discrepância muito elevada!");
				return true;
			}
		}
		return true;
	}
	
	private void sendValoresTemperaturaToMongo() {
		for(ValorSensor v: arrayToleranciaTemperatura) {
			mw.write("temperatura", v.getValor(), v.getData(), v.getHora());
		}
		arrayToleranciaTemperatura.clear();
	}
	
	private void addValoresTemperaturaMongo() {
		// TODO Auto-generated method stub
		for(ValorSensor d: arrayToleranciaTemperatura) {
			valoresTemperaturaTransportadosMongo.add(d);
		}
	}
	
	private void showValoresTemperaturaMongo() {
		for(ValorSensor d: valoresTemperaturaTransportadosMongo) {
			System.out.println("Valores Temperatura Transportados: " + d.getValor());
		}
	}

	private void showArrayToleranciaTemperatura() {
		// TODO Auto-generated method stub
		for(ValorSensor d: arrayToleranciaTemperatura) {
			System.out.println("Valor temperatura array tolerancia: " + d.getValor());
		}
	}

	public boolean valorTemperaturaComDiscrepancia(double valorTemperatura) {
		if(valorTemperatura <= lastResultadoTemperatura+discrepanciaTemperatura && valorTemperatura >= lastResultadoTemperatura-discrepanciaTemperatura) {
			return false;
		}
		return true;
	}
	
	public boolean valorTemperaturaAnormalComDiscrepancia(double valorTemperatura) {
		double lastResultadoAnormalTemperatura = arrayToleranciaTemperatura.get(arrayToleranciaTemperatura.size()-1).getValor();
		if(valorTemperatura <= lastResultadoAnormalTemperatura+discrepanciaTemperatura && valorTemperatura >= lastResultadoAnormalTemperatura-discrepanciaTemperatura) {
			return false;
		}
		return true;
	}
	
	/*
	 * Se o valor da temperatura for igual ao limiarSuperior ou ao limiarInferior --> envia notificação
	 */
	public void valorDentroDosLimites_Temperatura(double valorTemperatura) {
		int difLimites = limiteSuperiorTemperatura - limiteInferiorTemperatura;
		double difPercentagem = 100 - percentagemTemperatura;
		double limiarSuperior = limiteInferiorTemperatura + (difLimites * (percentagemTemperatura/100));
		double limiarInferior = limiteInferiorTemperatura + (difLimites * (difPercentagem/100));
		System.out.println("LimiarSuperior: " + limiarSuperior);
		System.out.println("LimiarInferior: " + limiarInferior);
		String valorTemperatura2 = String.valueOf(valorTemperatura);
		if(valorTemperatura >= limiteSuperiorTemperatura || valorTemperatura <= limiteInferiorTemperatura) {
			enviarNotificacao("O valor temperatura passou os limites", "O valor da temperatura é " + valorTemperatura2);
			System.out.println("Valor Temperatura: " + valorTemperatura + " ultrapassou os limites");
		}
		if(valorTemperatura >= limiarSuperior || valorTemperatura <= limiarInferior) {
			enviarNotificacao("O valor temperatura atingiu os limiares", "O valor da temperatura é " + valorTemperatura2);
			System.out.println("Valor Temperatura: " + valorTemperatura + " ultrapassou os limiares mas está dentro dos limites");
		}
		System.out.println("Valor Temperatura: " + valorTemperatura + " está dentro dos limiares");
	}
	
	public boolean descartarValorLuminosidade(double valorLuminosidade, String data, String hora) {
		if(!isFirstValueLuminosidade) {
			System.out.println("Subscriber já recebeu o primeiro valor de Luminosidade do sensor");
			lastResultadoLuminosidade = valorLuminosidade;
			isFirstValueLuminosidade = true;
			valoresLuminosidadeTransportadosMongo.add(new ValorSensor(valorLuminosidade, data, hora));
		} else {
			if(!valorLuminosidadeComDiscrepancia(valorLuminosidade)) {
				System.out.println("Valores Luminosidade NORMAL!");
				lastResultadoLuminosidade = valorLuminosidade;
				valorDentroDosLimites_Luminosidade(valorLuminosidade);
				toleranciaLuminosidade = 3;
				valoresLuminosidadeTransportadosMongo.add(new ValorSensor(valorLuminosidade, data, hora));
				return false;
			} else {
				System.out.println("Valores Luminosidade com Discrepancia");
				toleranciaLuminosidade--;
				if(toleranciaLuminosidade!=0) {
					if(arrayToleranciaLuminosidade.isEmpty()) {
						System.out.println("Array tolerancia luminosidade está vazio, insere-se o primeiro valor com grande discrepancia");
						arrayToleranciaLuminosidade.add(new ValorSensor(valorLuminosidade, data, hora));
					} else {
						if(valorLuminosidadeAnormalComDiscrepancia(valorLuminosidade)) {
							System.out.println("Valor luminosidade anormal com discrepância --> array tolerancia luminosidade vai reinicializar");
							toleranciaLuminosidade = 3;
							arrayToleranciaLuminosidade.clear();
						} else {
							System.out.println("Valor luminosidade anormal constante --> foi adicionado o valor no array tolerancia luminosidade");
							arrayToleranciaLuminosidade.add(new ValorSensor(valorLuminosidade, data, hora));
						}
					}
				} else {
					if(valorLuminosidadeAnormalComDiscrepancia(valorLuminosidade)) {
						System.out.println("Numero de tolerancias da luminosidade passou a zero");
						System.out.println("Valor luminosidade anormal com discrepância --> não foi adicionado o valor no array tolerancia luminosidade");
						toleranciaLuminosidade = 3;
						showArrayToleranciaLuminosidade();
						addValoresLuminosidadeMongo();
						//enviar os dados do array de tolerancia para o mongo --> método e depois faz clear

					} else {
						System.out.println("Numero de tolerancias da luminosidade passou a zero");
						System.out.println("Valor luminosidade anormal constate --> foi adicionado o valor no array tolerancia luminosidade");
						toleranciaLuminosidade = 3;
						arrayToleranciaLuminosidade.add(new ValorSensor(valorLuminosidade, data, hora));
						showArrayToleranciaLuminosidade();
						addValoresLuminosidadeMongo();
						//enviar os dados do array de tolerancia para o mongo --> método e depois faz clear
						sendValoresLuminosidadeToMongo();
					}
				}
				System.out.println("Descarta valor luminosidade: " + true + "discrepancia muito elevada!" );
				return true;
			}
		}
		return false;
	}
	
	
	public boolean valorLuminosidadeComDiscrepancia(double valorLuminosidade) {
		if(valorLuminosidade <= lastResultadoLuminosidade+discrepanciaLuminosidade && valorLuminosidade >= lastResultadoLuminosidade-discrepanciaLuminosidade) {
			return false;
		}
		return true;
	}
	
	public boolean valorLuminosidadeAnormalComDiscrepancia(double valorLuminosidade) {
		double lastResultadoAnormalLuminosidade = arrayToleranciaLuminosidade.get(arrayToleranciaLuminosidade.size()-1).getValor();
		if(valorLuminosidade <= lastResultadoAnormalLuminosidade+discrepanciaLuminosidade && valorLuminosidade >= lastResultadoAnormalLuminosidade-discrepanciaLuminosidade)
			return false;
		return true;
	}
	

	
	/*
	 * Se o valor da luminosidade for igual ao limiarSuperior ou ao limiarInferior --> envia notificação
	 */
	public void valorDentroDosLimites_Luminosidade(double valorLuminosidade) {
		int difLimites = limiteSuperiorLuminosidade - limiteInferiorLuminosidade;
		double difPercentagem = 100 - percentagemLuminosidade;
		double limiarSuperior = limiteInferiorLuminosidade + (difLimites * (percentagemLuminosidade/100));
		double limiarInferior = limiteInferiorLuminosidade + (difLimites * (difPercentagem/100));
		System.out.println("Limiar Superior: " + limiarSuperior);
		System.out.println("Limiar Inferior: " + limiarInferior);
		String valorLuminosidade2 = String.valueOf(valorLuminosidade);
		if(valorLuminosidade >= limiteSuperiorLuminosidade || valorLuminosidade <= limiteInferiorLuminosidade) {
			enviarNotificacao("Valor Luminosidade passou dos limites", "O valor da luminosidade é" + valorLuminosidade2);
			System.out.println("Valor Luminosidade: " + valorLuminosidade + " ultrapassou os limites");
		}
		if(valorLuminosidade >= limiarSuperior || valorLuminosidade <= limiarInferior) {
			enviarNotificacao("Valor luminosidade atingiu os limiares", "O valor da luminosidade é" + valorLuminosidade2);
			System.out.println("Valor Luminosidade: " + valorLuminosidade + " ultrapassou os limiares mas está dentro dos limites");
		}
		System.out.println("Valor Luminosidade: " + valorLuminosidade + " está dentro dos limiares");
	}
	
	private void sendValoresLuminosidadeToMongo() {
		for(ValorSensor v: arrayToleranciaLuminosidade) {
			mw.write("luminosidade", v.getValor(), v.getData(), v.getHora());
		}
		arrayToleranciaLuminosidade.clear();
	}
	
	private void addValoresLuminosidadeMongo() {
		// TODO Auto-generated method stub
		for(ValorSensor d: arrayToleranciaLuminosidade) {
			valoresLuminosidadeTransportadosMongo.add(d);
		}
	}
	
	private void showValoresLuminosidadeMongo() {
		for(ValorSensor d: valoresLuminosidadeTransportadosMongo) {
			System.out.println("Valores Luminosidade Transportados: " + d.getValor());
		}
	}

	private void showArrayToleranciaLuminosidade() {
		// TODO Auto-generated method stub
		for(ValorSensor d: arrayToleranciaLuminosidade) {
			System.out.println("Valor array tolerancia luminosidade: " + d.getValor());
		}
	}
	
	public void enviarNotificacao(String assunto, String mensagem) {
		setEmails();
		for(String s: emails) {
			enviarEmail(s, assunto, mensagem);
		}
		System.out.println("As notificações foram mandadas");
	}
	
	//Falta definir
	public void enviarEmail(String emailTo, String assunto, String mensagem) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("engenheiroses1@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailTo)
            );
            message.setSubject(assunto);
            message.setText(mensagem);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	public void connectToMainBase() {
		try {
			System.out.println("Entrei no connectToMainBase");
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://5.249.51.0:3306/nossabd_origem", "root", "teste123");
			System.out.println("MySql Database is connected !");
		} catch (Exception e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
	}
	
	public void setEmails() {
		try {
			emails.clear();
			Statement stmt = conn.createStatement();
			String querySelectEmails = "SELECT Email FROM utilizador WHERE TipoUtilizador=" + "\"Investigador\";";
			ResultSet rs = stmt.executeQuery(querySelectEmails);
			String email;
			while(rs.next()) {
				email = rs.getString("Email");
				emails.add(email);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao dar set dos emails");
		}
	}
	
	public void showEmails() {
		for(String s: emails) {
			System.out.println("Email: " + s);
		}
	}
	
	public void setLimites() {
		try {
			Statement stmt = conn.createStatement();
			String querySelectSistema = "SELECT * FROM sistema";
			ResultSet rs = stmt.executeQuery(querySelectSistema);
			rs.next();
			this.limiteInferiorLuminosidade = rs.getInt("LimiteInferiorLuminosidade");
			this.limiteInferiorTemperatura = rs.getInt("LimiteInferiorTemperatura");
			this.limiteSuperiorLuminosidade = rs.getInt("LimiteSuperiorLuminosidade");
			this.limiteSuperiorTemperatura = rs.getInt("LimiteSuperiorTemperatura");
			this.percentagemLuminosidade = rs.getInt("PercentagemLuminosidade");
			this.percentagemTemperatura = rs.getInt("PercentagemTemperatura");
			System.out.println("LimiteInferiorLuminosidade: " + this.limiteInferiorLuminosidade);
			System.out.println("LimiteSuperiorLuminosidade: " + this.limiteSuperiorLuminosidade);
			System.out.println("LimiteInferiorTemperatura: " + this.limiteInferiorTemperatura);
			System.out.println("LimiteSuperiorTemperatura: " + this.limiteSuperiorTemperatura);
			System.out.println("PercentagemTemperatura: " + percentagemTemperatura);
			System.out.println("PercentagemLuminosidade: " + percentagemLuminosidade);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao ir buscar os limites a tabela mysql");
			System.out.println("Os limites vao ser definidos automaticamente");
			this.limiteInferiorLuminosidade = 200;
			this.limiteInferiorTemperatura = 10;
			this.limiteSuperiorLuminosidade = 300;
			this.limiteSuperiorTemperatura = 40;
			this.percentagemLuminosidade = 75;
			this.percentagemTemperatura = 75;
		}
	}
	
	

	public static void main(String[] args) {
		new Subscriber();
	}



}
