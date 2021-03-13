package br.com.gcontato.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdicionaContatoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public AdicionaContatoServlet() {
		super();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String nome = request.getParameter("nome");
		String endereco = request.getParameter("endereco");
		String telefone = request.getParameter("telefone");
		
		
		out.println(
				"<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"UTF-8\">"
				+ "<title>Welcome "+nome+"</title>"
				+ "<link rel='stylesheet' href='/exemploServlet/stylesheet.css'>"
				+ "</head>"
				+ "<body class=\"internalBody\">"
				+  "<div class=\"classe\">Seja Bem-Vindo, <b>"+nome+"</b> !</div><br><br>"
				+  "<div class='dados'>Nome: "+nome+"; <br>"
				+  		"Telefone: "+telefone+"; <br>"
				+ 		"Endereço: "+ endereco + ";" 
				+ "</div>"
				+"</body>"
				+"</html>"
				);
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
	}
	
}
