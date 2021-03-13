package br.com.coldigogeladeiras.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.coldigogeladeiras.bd.Conexao;
import br.com.coldigogeladeiras.jdbc.JDBCProdutoDAO;
import br.com.coldigogeladeiras.modelo.Produto;

@Path("produto")
public class ProdutoRest extends UtilRest {

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	/*
	 * @POST Método de requisição POST para dados privativos
	 * 
	 * @Path caminho na API que será usada para puxar os dados
	 * ([...]/produto/inserir)
	 * 
	 * @Consumes necessário algum recebimento de dados para que o método funcione de
	 * acordo, receberá do client-side um dado genérico, no caso JSON
	 * 
	 */
	public Response inserir(String produtoParam) {

		try {

			/*
			 * Conversão De: JSON Para: Produto
			 */
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			Conexao conec = new Conexao();
			
			/** Conexão Iniciada **/
			Connection conexao = conec.abrirConexao();

			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			boolean retorno = jdbcProduto.inserir(produto);

			String msg = retorno ? "Produto cadastrado com sucesso!" : "Erro ao cadastrar produto.";
			
			conec.fecharConexao();
			/** Conexão Finalizada**/
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

}