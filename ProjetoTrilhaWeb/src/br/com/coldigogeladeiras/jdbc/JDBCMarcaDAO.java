package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.coldigogeladeiras.jdbcinterface.MarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;

public class JDBCMarcaDAO implements MarcaDAO {

	private Connection conexao;

	public JDBCMarcaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public List<Marca> buscar() {

		// Criação da instrução SQL para busca de todas as marcas
		String comando = "SELECT * FROM marcas";

		// Criação de uma lista para armazenar cada marca encontrada
		List<Marca> listMarcas = new ArrayList<Marca>();

		// Criação do objeto marca com valor null (ou seja, sem instanciá-lo)
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

				// Recebimento dos dois dados retornados do BD para cada linha encontrada
				int id = rs.getInt("id");
				String nome = rs.getString("nome");

				// Setando no objeto marca os valores encontrados
				marca.setId(id);
				marca.setNome(nome);

				// Adição da instância contida no objeto Marca na lista de marcas
				listMarcas.add(marca);
			}

			// Caso alguma Exception seja gerada no try, recebe-a no objeto "ex"
		} catch (Exception ex) {

			// Exibe a exceção do console
			ex.printStackTrace();
		}

		return listMarcas;
	}

}
