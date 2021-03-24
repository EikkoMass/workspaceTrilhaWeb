package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.jdbcinterface.ProdutoDAO;
import br.com.coldigogeladeiras.modelo.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {

	private Connection conexao;

	public JDBCProdutoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public boolean inserir(Produto produto) {

		String sql = "INSERT INTO produtos (id, categoria, modelo, capacidade, valor, marcas_id)"
				+ "VALUES (?,?,?,?,?,?)";

		PreparedStatement p;
		try {

			p = this.conexao.prepareStatement(sql);

			p.setInt(1, produto.getId());
			p.setString(2, produto.getCategoria());
			p.setString(3, produto.getModelo());
			p.setInt(4, produto.getCapacidade());
			p.setFloat(5, produto.getValor());
			p.setInt(6, produto.getMarcaId());

			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public List<JsonObject> buscarPorNome(String nome) {

		// Inicia criação do comando SQL de busca
		String comando = "SELECT produtos.*, marcas.nome as marca FROM produtos "
				+ "INNER JOIN marcas ON  produtos.marcas_id = marcas.id ";

		if (!"".equals(nome)) {

			// concatena no comando o WHERE buscando no nome do produto
			// o texto da variável nome

			comando += "WHERE modelo LIKE '%"+nome+"%' ";
		}

		// Finaliza o comando ordenando alfabeticamente por categoria,
		// marca e depois modelo

		comando += "ORDER BY categoria ASC, marcas.nome ASC, modelo ASC";

		List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
		JsonObject produto = null;

		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				int id = rs.getInt("id");
				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				String marcaNome = rs.getString("marca");

				if ("1".equals(categoria))
					categoria = "Geladeira";
				else if ("2".equals(categoria))
					categoria = "Freezer";
				
				
				produto = new JsonObject();
				produto.addProperty("id", id);
				produto.addProperty("categoria", categoria);
				produto.addProperty("modelo", modelo);
				produto.addProperty("capacidade", capacidade);
				produto.addProperty("valor", valor);
				produto.addProperty("marcaNome", marcaNome);
				
				listaProdutos.add(produto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaProdutos;
	}
	
	public boolean deletar(int id) {

		String comando = "DELETE FROM produtos WHERE id= ?";
		PreparedStatement p;
		
		try {
			
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			return false;
		
		}
				
		return true;
	
	}
	
	public Produto buscarPorId(int id) {
		
		String comando = "SELECT p.* FROM produtos as p where p.id = ?";
		
		Produto produto = new Produto();
		
		try {
			
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				int marcaId = rs.getInt("marcas_id");
				
				
				produto.setId(id);
				produto.setCapacidade(capacidade);
				produto.setMarcaId(marcaId);
				produto.setValor(valor);
				produto.setCategoria(categoria);
				produto.setModelo(modelo);
			}
			
		}catch(Exception e) {
		
		}
		
		
		return produto;
	}
}
