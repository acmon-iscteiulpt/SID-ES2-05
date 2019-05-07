package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class Subscriber extends Cliente {

	private static final String bd = "bd_dados_sensores";
	private static final String collection = "collection_dados_sensores";
	private static final String topic = "/sid_lab_2019";
	private static final int discrepanciaTemperatura = 10;
	private static final int discrepanciaLuminosidade = 10;
	
	private MongoWrite mw;
	private MongoRead mr;
	private boolean primeiroDadoTemperaturaRecebido = false;
	private boolean primeiroDadoLuminosidadeRecebido = false;
	private double lastResultadoLuminosidade;
	private double lastResultadoTemperatura;
	private int limiteSuperiorLuminosidade;
	private int limiteInferiorLuminosidade;
	private int limiteSuperiorTemperatura;
	private int limiteInferiorTemperatura;
	private double percentagemLuminosidade;
	private double percentagemTemperatura;

	public Subscriber(String clientID) {
		super(clientID);
		subscribe();
		this.mw = new MongoWrite(bd, collection);
		this.mr = new MongoRead(bd, collection);
	}

	@Override
	public void subscribe() {
		try {
			super.getClient().subscribe(topic, 2);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			System.out.println("Ocorreu um erro no subscribe da class Mongo");
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Chegou ao topico" + topic + " a seguinte mensagem:  " + message.toString());
		// tornar a mensagem recebida num json object para em baixo verificar quais
		// campos exeitem nesse json
		JSONObject jsonObj = new JSONObject(message.toString());
		
		ObjectMapper mapper = new ObjectMapper();

		// JSON from String to Object
		MainParser mp = mapper.readValue(message.toString(), MainParser.class);
		
		
		
		
		Date data_hora = new Date();                                                 // ir buscar data e hora atual, ou seja, do momento da mediçao do sensor
		SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/Y");
		SimpleDateFormat formato_hora = new SimpleDateFormat("HH:mm:ss");
				
		mp.setDat(formato_data.format(data_hora).toString());
		mp.setTim(formato_hora.format(data_hora).toString());

		
		
		
		if (jsonObj.has("tmp") && jsonObj.has("cell")) {
			System.out.println("Inserindo na mongoDB");

			if(!descartarValorTemperatura(mp.getTmp())) {
				mw.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());
			}
			if(!descartarValorLuminosidade(mp.getCell())) {
				mw.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
			}
			
		}
		if (jsonObj.has("tmp") && !jsonObj.has("cell")) {
			System.out.println("tem tudo menos a cell(luminosidade)");
			System.out.println("Inserindo na mongoDB dados apenas da temperatura na bd");


			if(!descartarValorTemperatura(mp.getTmp())) {
				mw.write("temperatura", mp.getTmp(), mp.getDat(), mp.getTim());
			}


		}
		if (!jsonObj.has("tmp") && jsonObj.has("cell")) {
			System.out.println("tem tudo menos a tmp(temperatura)");
			System.out.println("Inserindo na mongoDB dados apenas da luminosidade na bd");
			
			if(!descartarValorLuminosidade(mp.getCell())) {
				mw.write("luminosidade", mp.getCell(), mp.getDat(), mp.getTim());
			}
			

		}
		
	}
	
	/*
	 * Método que retorna um boolean a indicar se descarta o valor ou não
	 * 	--> Só descarta o valor quando há modificação no atributo lastResultadoTemperatura;
	 */
	public boolean descartarValorTemperatura(double valorTemperatura) {
		boolean temperaturaValida;
		boolean descarta = false;;
		//verificar se o primeiro resultado recebido está dentro dos limites se sim --> primeiroResultadoRecebido true
		if(!primeiroDadoTemperaturaRecebido) {
			System.out.println("Subscriber já recebeu o primeiro valor de Temperatura do sensor");
			temperaturaValida = valorDentroDosLimites_Temperatura(valorTemperatura);
			lastResultadoTemperatura = (temperaturaValida) ? valorTemperatura: 0;
			descarta = (temperaturaValida) ? false: true;
			primeiroDadoTemperaturaRecebido = (temperaturaValida) ? true: false;
			System.out.println("Descarta valor: " + descarta);
			return descarta;
		} else {
			//comparar o valor do lastResultado com o valor atual do sensor
			double difValorTemperatura = lastResultadoTemperatura - valorTemperatura;
			if(difValorTemperatura>=-discrepanciaTemperatura && difValorTemperatura<=discrepanciaTemperatura) {
				temperaturaValida = valorDentroDosLimites_Temperatura(valorTemperatura);
				lastResultadoTemperatura = (temperaturaValida) ? valorTemperatura: lastResultadoTemperatura;
				descarta = (temperaturaValida) ? false: true;
				System.out.println("Descarta valor: " + descarta);
				return descarta;
			} else {
				System.out.println("Descarta valor: " + true + " discrepância muito elevada!");
				return true;
			}
			
		}
	}
	
	public boolean descartarValorLuminosidade(double valorLuminosidade) {
		boolean luminosidadeValida;
		boolean descarta = false;
		if(!primeiroDadoLuminosidadeRecebido) {
			System.out.println("Subscriber já recebeu o primeiro valor de Luminosidade do sensor");
			luminosidadeValida = valorDentroDosLimites_Luminosidade(valorLuminosidade);
			lastResultadoLuminosidade = (luminosidadeValida) ? valorLuminosidade: 0;
			primeiroDadoLuminosidadeRecebido = (luminosidadeValida) ? true: false;
			descarta = (luminosidadeValida) ? false: true;
			System.out.println("Descarta o valor: " + descarta);
			return descarta;
		} else {
			double difValorLuminosidade = lastResultadoLuminosidade - valorLuminosidade;
			if(difValorLuminosidade>=-discrepanciaLuminosidade && difValorLuminosidade<=discrepanciaLuminosidade) {
				luminosidadeValida = valorDentroDosLimites_Luminosidade(valorLuminosidade);
				lastResultadoLuminosidade = (luminosidadeValida) ? valorLuminosidade: lastResultadoLuminosidade;
				descarta = (luminosidadeValida) ? false: true;
				System.out.println("Descarta valor: " + descarta);
				return descarta;
			} else {
				//Existe uma discrepância muito elevada e por isso descarta-se o valor
				System.out.println("Descarta valor: " + true + " discrepância muito elevada!");
				return true;
			}
		}
	}
	
	/*
	 * Se o valor da temperatura for igual ao limiarSuperior ou ao limiarInferior --> envia notificação
	 */
	public boolean valorDentroDosLimites_Temperatura(double valorTemperatura) {
		int difLimites = limiteSuperiorTemperatura - limiteInferiorTemperatura;
		double difPercentagem = 100 - percentagemTemperatura;
		double limiarSuperior = limiteInferiorTemperatura + (difLimites * (percentagemTemperatura/100));
		double limiarInferior = limiteInferiorTemperatura + (difLimites * (difPercentagem/100));
		System.out.println("LimiarSuperior: " + limiarSuperior);
		System.out.println("LimiarInferior: " + limiarInferior);
		if(valorTemperatura >= limiteSuperiorTemperatura || valorTemperatura <= limiteInferiorTemperatura) {
			enviarNotificação();
			System.out.println("Valor Temperatura: " + valorTemperatura + " ultrapassou os limites");
			return false;
		}
		if(valorTemperatura >= limiarSuperior || valorTemperatura <= limiarInferior) {
			enviarNotificação();
			System.out.println("Valor Temperatura: " + valorTemperatura + " ultrapassou os limiares mas está dentro dos limites");
			return true;
		}
		System.out.println("Valor Temperatura: " + valorTemperatura + " está dentro dos limiares");
		return true;
	}
	
	/*
	 * Se o valor da luminosidade for igual ao limiarSuperior ou ao limiarInferior --> envia notificação
	 */
	public boolean valorDentroDosLimites_Luminosidade(double valorLuminosidade) {
		int difLimites = limiteSuperiorLuminosidade - limiteInferiorLuminosidade;
		double difPercentagem = 100 - percentagemLuminosidade;
		double limiarSuperior = limiteInferiorLuminosidade + (difLimites * (percentagemLuminosidade/100));
		double limiarInferior = limiteInferiorLuminosidade + (difLimites * (difPercentagem/100));
		System.out.println("Limiar Superior: " + limiarSuperior);
		System.out.println("Limiar Inferior: " + limiarInferior);
		if(valorLuminosidade >= limiteSuperiorLuminosidade || valorLuminosidade <= limiteInferiorLuminosidade) {
			enviarNotificação();
			System.out.println("Valor Temperatura: " + valorLuminosidade + " ultrapassou os limites");
			return false;
		}
		if(valorLuminosidade >= limiarSuperior || valorLuminosidade <= limiarInferior) {
			enviarNotificação();
			System.out.println("Valor Luminosidade: " + valorLuminosidade + " ultrapassou os limiares mas está dentro dos limites");
			return true;
		}
		System.out.println("Valor Luminosidade: " + valorLuminosidade + " está dentro dos limiares");
		return true;
	}
	
	//Falta definir
	public void enviarNotificação() {
		
	}

	public static void main(String[] args) {
		new Subscriber("1");
	}

}
