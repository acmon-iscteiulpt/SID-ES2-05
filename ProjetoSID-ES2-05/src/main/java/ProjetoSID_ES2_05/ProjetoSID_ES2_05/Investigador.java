package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import InterfaceGrafica_Investigador.GUI_Investigador;

public class Investigador {
	
	private static final String username = "engenheiroses1@gmail.com";
	private static final String password = "omiaoegay";
	private static final String ip = "5.249.51.0";

	
	private Connection conn;
	private Authenticator auth;
	private Properties prop;
	private Session session;

	
	public Investigador(String username, String password) {
		connectToMainBase(username, password);
		autenticarCliente();
		new GUI_Investigador(this);
		
	}
	
	private void connectToMainBase(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/nossabd_origem", username, password);
			System.out.println("Investigador conectou-se a base de dados MySQL");
		} catch (Exception e) {
			System.out.println("Investigador não se conseguiu conectar a base de dados MySQL!");
			e.printStackTrace();
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Connection Failed", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void disconnectFromMainBase() {
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void addCultura(String nomeCultura, String descricaoCultura) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call insere_cultura(?, ?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, descricaoCultura);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	public void deleteCultura(int idCultura) {
		try {
			Statement stmt = conn.createStatement();
			String queryDeleteCultura = "DELETE FROM cultura WHERE IDCultura=" + idCultura + ";";
			stmt.executeUpdate(queryDeleteCultura);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não pode eliminar tabelas de outros investigadores!");
		}
	}
	
	public DefaultTableModel getCulturaTable(JTable table) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_culturas_utilizador2()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[3];
			while(rs.next()) {
				row[0] = rs.getString("IDCultura");
				row[1] = rs.getString("NomeCultura");
				row[2] = rs.getString("DescricaoCultura");
				model.addRow(row);
			}
			rs.close();
			cStmt.close();
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getMedicaoTable(JTable table) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_medicoes_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[6];
			while(rs.next()) {
				row[0] = rs.getString("IDMedicoes");
				row[1] = rs.getString("IDCultura_fk");
				row[2] = rs.getString("IDVariavel_fk");
				row[3] = rs.getString("DataHoraMedicao");
				row[4] = rs.getString("ValorMedicao");
				row[5] = rs.getString("IDUtilizador_fk");
				model.addRow(row);
			}
			rs.close();
			cStmt.close();
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getVariaveisMedidasTable(JTable table) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_variaveismedidas_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[6];
			while(rs.next()) {
				row[0] = rs.getString("VariaveisMedidas_ID");
				row[1] = rs.getString("IDCultura_fk");
				row[2] = rs.getString("IDVariavel_fk");
				row[3] = rs.getString("LimiteSuperior");
				row[4] = rs.getString("LimiteInferior");
				row[5] = rs.getString("PercentagemAlerta");
				model.addRow(row);
			}
			rs.close();
			cStmt.close();
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getIDVariaveisMedidasTable() {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_variaveismedidas_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			String idMedicao;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				idMedicao = rs.getString("VariaveisMedidas_ID");
				box.addElement(idMedicao);
			}
			rs.close();
			cStmt.close();
			return box;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public DefaultComboBoxModel<String> getNomeCultura() {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_culturas_utilizador2()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			String nomeCultura;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				nomeCultura = rs.getString("NomeCultura");
				box.addElement(nomeCultura);
			}
			cStmt.close();
			return box;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao procurar pelo nome das culturas");
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getIDCultura() {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_culturas_utilizador2()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			String nomeCultura;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				nomeCultura = rs.getString("IDCultura");
				box.addElement(nomeCultura);
			}
			cStmt.close();
			return box;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao procurar pelo ID das culturas");
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getNomeVariavel() {
		try {
			String querySelectVariavel = "SELECT * FROM variavel";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySelectVariavel);
			String nomeVariavel;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				nomeVariavel = rs.getString("NomeVariavel");
				box.addElement(nomeVariavel);
			}
			rs.close();
			return box;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public DefaultComboBoxModel<String> getID_MedicaoTable() {
		try {
			CallableStatement cStmt = conn.prepareCall("{call mostra_medicoes_utilizador()}");
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			String id;
			DefaultComboBoxModel<String> box = new DefaultComboBoxModel<String>();
			while(rs.next()) {
				id = rs.getString("IDMedicoes");
				box.addElement(id);
			}
			rs.close();
			cStmt.close();
			return box;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void addMedicaoTable(String nomeCultura,String nomeVariavel ,String data, String time, String valorMedicao) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call insere_medicoes(?, ?, ?, ?)}");
			String dataHora = data + " " + time;
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, nomeVariavel);
			cStmt.setString(3, dataHora);
			cStmt.setString(4, valorMedicao);
			cStmt.execute();
			cStmt.close();
			verificarMedicao(nomeCultura, nomeVariavel, valorMedicao);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void verificarMedicao(String nomeCultura, String nomeVariavel, String valorMedicao) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call medicao_ultrapassou(?,?,?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, nomeVariavel);
			cStmt.setString(3, valorMedicao);
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			rs.next();
			String mensagem = rs.getString("mensagem");
			verificarMensagem(mensagem, nomeCultura, nomeVariavel, valorMedicao);
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao correr o sp medicao_ultrapassou");
		}
	}
	
	public void verificarMensagem(String mensagem, String nomeCultura, String nomeVariavel, String valorMedicao) {
		System.out.println("Mensagem medicao ultrapassou: " + mensagem);
		if(!mensagem.isEmpty()) {
			
			notificar(mensagem, nomeCultura, nomeVariavel, valorMedicao);
		} else {
			System.out.println("Conteudo mensagem (verificarMensagem - Classe Investigador): " + mensagem);
		}
	}
	
	public void notificar(String mensagem, String nomeCultura, String nomeVariavel, String valorMedicao) {
		String email = getEmail(nomeCultura);
		enviarEmail(email, mensagem, "O valor da medição " + nomeVariavel + " foi: " + valorMedicao);
		
	}

	
	public String getEmail(String nomeCultura) {
		String email = new String();
		System.out.println("Vou notificar");
		try {
			CallableStatement cStmt = conn.prepareCall("{call getEmail_NomeCultura(?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.execute();
			ResultSet rs = cStmt.getResultSet();
			rs.next();
			email = rs.getString("Email");
			return email;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao correr o sp --> getEmail_NomeCultura");
		}
		return email;
	}
	
	public void deleteMedicaoTable(String idMedicao) {
		try {
			int id = Integer.parseInt(idMedicao);
			String queryDelete = "DELETE FROM medicoes WHERE IDMedicoes=" + id + ";";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(queryDelete);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "ID tem que ser um valor numérico!");
		}
	}
	
	public String[] searchCultura(String nomeCultura) {
		try {
			String[] v = new String[1];
			Statement stmt = conn.createStatement();
			String querySelect = "SELECT * FROM cultura WHERE NomeCultura=" + "\"" + nomeCultura + "\";";
			ResultSet rs = stmt.executeQuery(querySelect);
			rs.next();
			v[0] = rs.getString("DescricaoCultura");
			return v;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	
	public String[] searchMedicao(String idMedicao) {
		try {
			String[] v = new String[3];
			Statement stmt = conn.createStatement();
			String querySelect = "SELECT * FROM medicoes WHERE IDMedicoes=" + "\"" + idMedicao + "\";";
			String dataHoraMedicao;
			String[] dataHoraMedicaoV;
			ResultSet rs = stmt.executeQuery(querySelect);
			rs.next();
			dataHoraMedicao = rs.getString("DataHoraMedicao");
			dataHoraMedicaoV = dataHoraMedicao.split(" ");
			v[0] = dataHoraMedicaoV[0];
			v[1] = dataHoraMedicaoV[1];
			v[2] = rs.getString("ValorMedicao");
			return v;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public String[] searchVariavelMedida(String idVariavelMedida) {
		try {
			String[] v = new String[3];
			Statement stmt = conn.createStatement();
			String querySelect = "SELECT * FROM variaveismedidas WHERE VariaveisMedidas_ID=" + "\"" + idVariavelMedida + "\";";
			ResultSet rs = stmt.executeQuery(querySelect);
			rs.next();
			v[0] = rs.getString("LimiteSuperior");
			v[1] = rs.getString("LimiteInferior");
			v[2] = rs.getString("PercentagemAlerta");
			return v;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public void updateMedicao(String idMedicao, String data, String hora, String valorMedicao) {
		System.out.println("Update Medicao");
		try {
			CallableStatement cStmt = conn.prepareCall("{call update_medicao(?, ?, ?)}");
			String dataHoraMedicao = data + " " + hora;
			cStmt.setString(1, idMedicao);
			cStmt.setString(2, dataHoraMedicao);
			cStmt.setString(3, valorMedicao);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao executar o SP --> update_medicao");
		}
	}
	
	public void updateCultura(String nomeCultura, String newNomeCultura, String descricaoCultura) {
		System.out.println("UpdateCultura");
		try {
			CallableStatement cStmt = conn.prepareCall("{call update_cultura(?, ?, ?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, newNomeCultura);
			cStmt.setString(3, descricaoCultura);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu um erro ao executar o SP --> update_cultura");
		}
	}
	
	public void updateVariavelMedida(String idVariavelMedida, int limiteSuperior, int limiteInferior, int percentagem) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call alterar_variaveis_medidas(?, ?, ?, ?)}");
			int idMedicao2 = Integer.parseInt(idVariavelMedida);
			cStmt.setLong(1, idMedicao2);
			cStmt.setLong(2, limiteSuperior);
			cStmt.setLong(3, limiteInferior);
			cStmt.setLong(4, percentagem);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteVariavelMedida(String idVariavelMedida) {
		try {
			int id = Integer.parseInt(idVariavelMedida);
			String queryDelete = "DELETE FROM variaveismedidas WHERE VariaveisMedidas_ID=" + idVariavelMedida + ";";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(queryDelete);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "ID tem que ser um valor numérico!");
		}
	}
	
	public void addVariavelMedida(String nomeCultura, String nomeVariavel, int limiteSuperior, int limiteInferior) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call insere_variaveismedidas(?, ?, ?, ?, ?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, nomeVariavel);
			cStmt.setLong(3, limiteSuperior);
			cStmt.setLong(4, limiteInferior);
			cStmt.setLong(5, 70);
			cStmt.execute();
			cStmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void autenticarCliente() {
		auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			} 
		};
		this.prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        this.session = Session.getInstance(prop, auth);
	}
	
	public void enviarEmail(String emailTo, String assunto, String mensagem) {
	

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
	
	public static void main(String[] args) {
		new Investigador("root", "teste123");
	}



}
