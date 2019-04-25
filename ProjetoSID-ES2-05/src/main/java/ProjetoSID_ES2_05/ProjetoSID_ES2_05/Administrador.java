package ProjetoSID_ES2_05.ProjetoSID_ES2_05;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Administrador {
	
	private Connection conn;
	
	
	public Administrador(String username, String password) {
		connectToMainBase(username, password);
		new GUI_Administrador(this);
	}
	
	
	private void connectToMainBase(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nossabd_origem", username, password);
			System.out.println("Administrador conectou-se a base de dados MySQL");
		} catch (Exception e) {
			System.out.println("Administrador não se conseguiu conectar a base de dados MySQL!");
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
	
	
	public DefaultTableModel getCulturaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM cultura";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[4];
			while(rs.next()) {
				row[0] = rs.getString("IDCultura");
				row[1] = rs.getString("NomeCultura");
				row[2] = rs.getString("DescricaoCultura");
				row[3] = rs.getString("IDUtilizador_fk");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getMedicoesTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM medicoes";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
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
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getMedicoesLuminosidadeTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM medicoesluminosidade";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[3];
			while(rs.next()) {
				row[0] = rs.getString("IDMedicao");
				row[1] = rs.getString("DataHoraMedicao");
				row[2] = rs.getString("ValorMedicaoLuminosidade");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public DefaultTableModel getMedicoesTemperaturaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM medicoestemperatura";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[3];
			while(rs.next()) {
				row[0] = rs.getString("IDMedicao");
				row[1] = rs.getString("DataHoraMedicao");
				row[2] = rs.getString("ValorMedicaoTemperatura");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getSistemaTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM sistema";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[5];
			while(rs.next()) {
				row[0] = rs.getString("Sistema_ID");
				row[1] = rs.getString("LimiteSuperiorTemperatura");
				row[2] = rs.getString("LimiteInferiorTemperatura");
				row[3] = rs.getString("LimiteSuperiorLuminosidade");
				row[4] = rs.getString("LimiteInferiorLuminosidade");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public DefaultTableModel getUtilizadorTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM utilizador";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[4];
			while(rs.next()) {
				row[0] = rs.getString("IDUtilizador");
				row[1] = rs.getString("NomeUtilizador");
				row[2] = rs.getString("TipoUtilizador");
				row[3] = rs.getString("Email");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getVariaveisTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM variavel";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[2];
			while(rs.next()) {
				row[0] = rs.getString("IDVariavel");
				row[1] = rs.getString("NomeVariavel");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTableModel getVariaveisMedidasTable(JTable table) {
		try {
			Statement stmt = conn.createStatement();
			String querySelectCultura = "SELECT * FROM variaveismedidas";
			System.out.println("Query: " + querySelectCultura);
			ResultSet rs = stmt.executeQuery(querySelectCultura);
			((DefaultTableModel)table.getModel()).setRowCount(0);
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			Object [] row = new Object[5];
			while(rs.next()) {
				row[0] = rs.getString("VariaveisMedidas_ID");
				row[1] = rs.getString("IDCultura_fk");
				row[2] = rs.getString("IDVariavel_fk");
				row[3] = rs.getString("LimiteSuperior");
				row[4] = rs.getString("LimiteInferior");
				model.addRow(row);
			}
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void getAlertasSensorTable() {
		
	}
	
	public void getAlertasMedicoesTable() {
		
	}
	
	public void addSistema(int sistema_id, int limiteSuperiorTemperatura, int limiteInferiorTemperatura, int limiteSuperiorLuminosidade, int limiteInferiorLuminosidade) {
		try {
			String queryAddSistema = "INSERT INTO sistema(Sistema_ID, LimiteSuperiorTemperatura, LimiteInferiorTemperatura, LimiteInferiorLuminosidade, LimiteSuperiorLuminosidade) VALUES (" +
					sistema_id + ", " + limiteSuperiorTemperatura + ", " + limiteInferiorTemperatura + ", " + limiteSuperiorLuminosidade + ", " + limiteInferiorLuminosidade + ");";
			System.out.println("Query: " + queryAddSistema);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(queryAddSistema);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Add Sistema Failed!");
			e.printStackTrace();
		}
	}
	
	
	public void addUtilizador(String nomeUtilizador, String tipoUtilizador, String email, String password) {
		String callSpString = "CALL CriarUtilizador(" + "\"" + nomeUtilizador + "\", \"" + tipoUtilizador + "\", \"" + email + "\", \"" + password + "\");";
		System.out.println("Query: " + callSpString);
		try {
			CallableStatement cStmt = conn.prepareCall("{call CriarUtilizador(?, ?, ?, ?)}");
			cStmt.setString(1, nomeUtilizador);
			cStmt.setString(2, tipoUtilizador);
			cStmt.setString(3, email);
			cStmt.setString(4, password);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addVariavel(String nomeCultura, String nomeVariavel, int limiteSuperior, int limiteInferior) {
		try {
			CallableStatement cStmt = conn.prepareCall("{call insere_variaveis(?, ?, ?, ?)}");
			cStmt.setString(1, nomeCultura);
			cStmt.setString(2, nomeVariavel);
			cStmt.setLong(3, limiteSuperior);
			cStmt.setLong(4, limiteInferior);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Administrador("root", "teste123");
	}

}