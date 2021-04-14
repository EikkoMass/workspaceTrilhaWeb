package br.com.coldigogeladeiras.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
			String msg;

			if (jdbcProduto.seriaUmaMarcaValida(produto)) {
				if (!jdbcProduto.seriaUmProdutoExistente(produto)) {
					boolean retorno = jdbcProduto.inserir(produto);
					msg = retorno ? "Produto cadastrado com sucesso!" : "Erro ao cadastrar produto.";
				} else {
					msg = "Voc� n�o pode cadastrar um produto id�ntico a outro!";
				}

			} else {
				msg = "N�o foi encontrado a marca selecionada, " + "por favor, tente novamente!";

			}

			conec.fecharConexao();
			/** Conexão Finalizada **/
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {

		try {

			List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			listaProdutos = jdbcProduto.buscarPorNome(nome);
			conec.fecharConexao();

			String json = new Gson().toJson(listaProdutos);

			return this.buildResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {

		try {
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			
			boolean retorno = jdbcProduto.deletar(id);
			
			String msg = "";
			
			msg = retorno ? "Produto excluído com sucesso!" : "Erro ao excluir produto.";
			
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	
	@GET
	@Path("/buscarPorId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		
		try {
			
			Produto produto = new Produto();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			
			produto = jdbcProduto.buscarPorId(id);
			
			conec.fecharConexao();
			
			return this.buildResponse(produto);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String produtoParam) {
		try {
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			
			boolean retorno = jdbcProduto.alterar(produto);
			
			String msg = retorno ? "Produto alterado com sucesso!" : "Erro ao alterar produto.";

			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}
