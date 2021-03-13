package br.com.coldigogeladeiras.bd;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	private Connection conexao;


	public Connection abrirConexao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			this.conexao = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/bdcoldigo?user=root&password=root&useTimezone=true&serverTimezone=UTC");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return conexao;
	}

	public void fecharConexao() {
		try {
			this.conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
