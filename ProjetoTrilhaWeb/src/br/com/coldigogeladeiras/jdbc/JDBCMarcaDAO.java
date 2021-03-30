package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.jdbcinterface.MarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;

public class JDBCMarcaDAO implements MarcaDAO {

	private Connection conexao;

	public JDBCMarcaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public Marca buscarPorId(int id) {

		// Criação da instrução SQL para busca de todas as marcas
		String comando = "SELECT * FROM marcas WHERE id = " + id;

		Marca marca = null;

		// Abertura do try/catch
		try {

			// Uso da conexão do banco para prepará-lo para uma instrução SQL
			Statement stmt = conexao.createStatement();

			// Execução da instrução criada previamente
			// e armazenamento do resultado no objeto rs (ResultSet)
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {

				// Criação da instância da classe Marca
				marca = new Marca();
				String nome = rs.getString("nome");

				// Setando no objeto marca os valores encontrados
				marca.setId(id);
				marca.setNome(nome);

			}

			// Caso alguma Exception seja gerada no try, recebe-a no objeto "ex"
		} catch (SQLException ex) {

			// Exibe a exceção do console
			ex.printStackTrace();
		}

		return marca;
	}
	

	public boolean inserir(Marca marca) {

		String comando = "INSERT INTO marcas (nome) VALUES (?)";

		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);

			p.setString(1, marca.getNome());
			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public List<JsonObject> buscarPorNome(String nome) {

		// Inicia criação do comando SQL de busca
		String comando = "SELECT * FROM marcas ";

		if (!"".equals(nome)) {

			// concatena no comando o WHERE buscando no nome do produto
			// o texto da variável nome

			comando += "WHERE nome LIKE '%"+nome+"%' ";
		}

		// Finaliza o comando ordenando alfabeticamente por categoria,
		// marca e depois modelo

		comando += "ORDER BY nome ASC";

		List<JsonObject> listaMarcas = new ArrayList<JsonObject>();
		JsonObject marca = null;

		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				int id = rs.getInt("id");
				String nomeMarca = rs.getString("nome");
				
				marca = new JsonObject();
				marca.addProperty("id", id);
				marca.addProperty("nome", nomeMarca);
				
				listaMarcas.add(marca);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaMarcas;
	}

	public boolean deletar(int id) {

		String comando = "DELETE FROM marcas WHERE id = ?";

		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public boolean editar(Marca marca) {

		String comando = "UPDATE marcas SET nome = ? WHERE id = ?";

		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);

			p.setString(1, marca.getNome());
			p.setInt(2, marca.getId());

			p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
