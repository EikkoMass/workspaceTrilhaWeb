package br.com.coldigogeladeiras.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.modelo.Produto;

public interface ProdutoDAO {

	public boolean inserir(Produto produto);
	public List<JsonObject> buscarPorNome(String nome);
	public Produto buscarPorId(int id);
	public boolean deletar(int id);
	public boolean alterar(Produto produto);
	public boolean seriaUmaMarcaValida(Produto produto);
	public boolean seriaUmProdutoExistente(Produto produto);
}
