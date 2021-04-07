package br.com.coldigogeladeiras.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.coldigogeladeiras.bd.Conexao;
import br.com.coldigogeladeiras.jdbc.JDBCMarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;

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

@Path("marca")
public class MarcaRest extends UtilRest {

	@GET
	@Path("/buscarTodasAsMarcas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {

		try {
			List<Marca> listaDeMarcas = new ArrayList<Marca>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaDeMarcas = jdbcMarca.buscar();
			conec.fecharConexao();

			return this.buildResponse(listaDeMarcas);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@GET
	@Path("/buscarPorId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {

		try {
			Marca marca = new Marca();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			marca = jdbcMarca.buscarPorId(id);
			conec.fecharConexao();

			return this.buildResponse(marca);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String marcasParam) {

		try {
			Marca marca = new Gson().fromJson(marcasParam, Marca.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			String mensagem;

			if(!jdbcMarca.seriaUmaMarcaExistente(marca)) {
				boolean retorno = jdbcMarca.inserir(marca);
				mensagem = retorno ? "Marca registrada com sucesso!" : "Erro ao registrar marca.";
			}else {
				mensagem = "Você não pode adicionar uma marca com nome já registrado!";
			}

			conec.fecharConexao();


			return this.buildResponse(mensagem);
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
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);

			String mensagem;
			
			if(!jdbcMarca.marcaPossuiRelacoes(id)) {
				boolean retorno = jdbcMarca.deletar(id);
				mensagem = retorno ? "Marca deletada com sucesso!"
						: "Houve um problema ao deletar a marca selecionada.";
			}else {
				mensagem = "VocÃª nÃ£o pode excluir uma marca com produtos vinculados, "
						+ "exclua-os e tente novamente mais tarde!";
			}

			conec.fecharConexao();

			return this.buildResponse(mensagem);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String produtoParam) {
		
		try {
			Marca marca = new Gson().fromJson(produtoParam, Marca.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			boolean retorno = jdbcMarca.editar(marca);
			conec.fecharConexao();
			
			String mensagem = 
					retorno ? "Marca editada com sucesso!" : "Houve algum problema ao editar a marca selecionada.";
			
			return this.buildResponse(mensagem);
		} catch (Exception e) {
			
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@PUT
	@Path("/alterarStatus")
	@Consumes("application/*")
	public Response alterarStatus(String id) {
		
		try {
			int idMarca = Integer.parseInt(id);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			Marca marca = jdbcMarca.buscarPorId(idMarca);
			
			boolean retorno = jdbcMarca.editarStatus(marca);
			conec.fecharConexao();
			
			String mensagem = 
					retorno ? "Status da marca editada com sucesso!" : "Houve algum problema ao editar o status da marca selecionada.";
			
			return this.buildResponse(mensagem);
		} catch (Exception e) {
			
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("busca") String nome) {

		try {

			List<JsonObject> listaMarcas = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscarPorNome(nome);
			conec.fecharConexao();

			String json = new Gson().toJson(listaMarcas);

			return this.buildResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
