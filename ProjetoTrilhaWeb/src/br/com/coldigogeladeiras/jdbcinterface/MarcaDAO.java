package br.com.coldigogeladeiras.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.modelo.Marca;

public interface MarcaDAO {
	public Marca buscarPorId(int id);
	public List<Marca> buscar();
	public List<Marca> buscarMarcasAtivadas();
	public List<JsonObject> buscarPorNome(String nome);
	public boolean seriaUmaMarcaExistente(Marca marca);
	public boolean deletar(int id);
	public boolean editar(Marca marca);
	public boolean inserir(Marca marca);
	public boolean editarStatus(Marca marca);
	public boolean marcaPossuiRelacoes(int id);

}
