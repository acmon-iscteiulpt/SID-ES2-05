package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

public class ValorSensor {
	
	private double valor;
	private String data;
	private String hora;
	
	public ValorSensor(double valor, String data, String hora) {
		this.valor = valor;
		this.data = data;
		this.hora = hora;
	}

	public double getValor() {
		return valor;
	}

	public String getData() {
		return data;
	}

	public String getHora() {
		return hora;
	}
	
	

}
