package br.com.coldigogeladeiras.modelo;

import java.io.Serializable;

public class Marca implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private boolean status;
	
	public boolean isActiveStatus() {
		return this.status;
	}
	public void setStatus(boolean ativo) {
		this.status = ativo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


}
