
function validaFormulario(){
	var nome = document.formBecomeMember.inome.value;
	var expRegNome = new RegExp("^[\\w\\DÀ-ü]{3,}([ ][\\w\\DÀ-ü]{2,})+$");

	if(!expRegNome.test(nome)){
		alert("Preencha o campo 'Nome'.");
		document.formBecomeMember.inome.focus();
		return false;
		
	}
	
	if(document.formBecomeMember.iemail.value.indexOf(".") == -1 && document.formBecomeMember.iemail.value.indexOf("@") == -1){
	
		alert("Preencha o campo 'E-Mail'.");
		document.formBecomeMember.iemail.focus();
		return false;
	
	}
	
	var telefone = document.formBecomeMember.itelefone.value;
	var expRegTelefone = new RegExp("^([1-9]{2})[\\d]{4,5}-[\\d]{4}$");
	
	if(!expRegTelefone.test(telefone)){
	
		alert("Preencha o campo 'Telefone'.");
		document.formBecomeMember.itelefone.focus();
		return false;
	
	}
	
	
	if(document.formBecomeMember.idt.value == ""){
	
		alert("Preencha o campo 'Data de Nascimento'.");
		document.formBecomeMember.idt.focus();
		return false;
	
	}
	
	var radios = formBecomeMember.radioSex;
	var marcado = false;
		
	for(var i=0; i<radios.length; i++){
		 if(radios[i].checked){
		 	marcado = true;
		 }
	}

	if(!marcado){
	
		alert("Preencha o campo 'Sexo'.");
		return false;
	
	}
	
	
return true;

}


function assinaTermos(){

	if(document.formBecomeMember.checkbox.checked){
	
	document.formBecomeMember.submitButton.disabled = false;
	
	
	}else{
	
	document.formBecomeMember.submitButton.disabled = true;
	
	
	}


}

$(document).ready(() => {
	$('header').load('/ProjetoFaClube/pages/general/header.html');
	$('footer').load('/ProjetoFaClube/pages/general/rodape.html');
});