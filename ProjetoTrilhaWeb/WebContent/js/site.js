function validaFaleConosco(){

	var nome = document.frmfaleconosco.txtnome.value;
	var expRegNome = new RegExp("^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$");

//^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$
//^[A-zÀ-ü]{3,}([ ][A-zÀ-ü]{2,})+$

	if(!expRegNome.test(nome)){
	
		alert("Preencha o campo 'Nome'.");
		document.frmfaleconosco.txtnome.focus();
		return false;
	
	}
	
	var telefone = document.frmfaleconosco.txtfone.value;
	var expRegTelefone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$");
	
	if(!expRegTelefone.test(telefone)){
	
		alert("Preencha o campo 'Telefone'.");
		document.frmfaleconosco.txtfone.focus();
		return false;
	
	}
	
	if(document.frmfaleconosco.txtemail.value.indexOf(".") == -1 && document.frmfaleconosco.txtemail.value.indexOf("@") == -1){
	
		alert("Preencha o campo 'E-Mail'.");
		document.frmfaleconosco.txtemail.focus();
		return false;
	
	}

	if(document.frmfaleconosco.selmotivo.value == ""){
	
		alert("Preencha o campo 'Motivo'.");
		document.frmfaleconosco.selmotivo.focus();
		return false;
	
	}
	
	if(document.frmfaleconosco.selmotivo.value == "PR"){
	
		if(document.getElementById("opcaoProduto").firstChild.value == ""){
		
		alert("Preencha o Produto Correspondente ao Pedido.");
		document.getElementById("opcaoProduto").firstChild.focus();
		return false;
		
		}
	
	}
	
	if(document.frmfaleconosco.txtcomentario.value == ""){
	
		alert("Preencha o campo 'Comentário'.");
		document.frmfaleconosco.txtcomentario.focus();
		return false;
	
	}
	
	
return true;

}


function verificaMotivo(motivo){

	var elemento = document.getElementById("opcaoProduto");
	
	if(motivo == "PR"){
	
	var select = document.createElement("select");
	//<select></select>
	
	select.setAttribute("name", "selproduto");
	//<select name="selproduto"></select>
	
	
	
	var option = document.createElement("option");
	//<option></option>
	
	option.setAttribute("value", "");
	//<option value=""></option>
	
	
	var texto = document.createTextNode("Escolha");
	//Escolha
	
	option.appendChild(texto);
	//<option value="">Escolha</option>
	
	
	select.appendChild(option);
	/*
	<select name="selproduto">
		<option value="">Escolha</option>
	</select>
	*/
	
	
	var option = document.createElement("option");
	//<option></option>
	
	option.setAttribute("value", "FR");
	//<option value="FR"></option>
	
	var texto = document.createTextNode("Freezer");
	//Freezer
	
	option.appendChild(texto);
	//<option value="FR">Freezer</option>
	
	select.appendChild(option);
	/*
	<select name="selproduto">
		<option value="">Escolha</option>
		<option value="FR">Freezer</option>
	</select>
	*/
	
	
	
	var option = document.createElement("option");
	//<option></option>
	
	option.setAttribute("value", "GE");
	//<option value="GE"></option>
	
	var texto = document.createTextNode("Geladeira");
	//Geladeira
	
	option.appendChild(texto);
	//<option value="GE">Geladeira</option>
	
	select.appendChild(option);
	/*
	<select name="selproduto">
		<option value="">Escolha</option>
		<option value="FR">Freezer</option>
		<option value="GE">Geladeira</option>
	</select>
	*/
	
	elemento.appendChild(select);
	//adicionamos na div o select recém criado (adicionamos como filho da div)
	
	}else{
	
		if(elemento.firstChild){
		
		elemento.removeChild(elemento.firstChild);
		}
	
	}

}

$(document).ready(function(){
	$("header").load("/ProjetoTrilhaWeb/pages/site/general/cabecalho.html");
	$("nav").load("/ProjetoTrilhaWeb/pages/site/general/menu.html");
	$("footer").load("/ProjetoTrilhaWeb/pages/site/general/rodape.html");
});
