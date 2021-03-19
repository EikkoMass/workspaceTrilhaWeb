//Cria o objeto  COLDIGO, que será usado como identificador do projeto.
var COLDIGO = new Object();



$(document).ready(function() {
	
	//Caminho padrão para todas as resquests para API
	COLDIGO.PATH = `/ProjetoTrilhaWeb/rest/`;
	
	$('header').load('/ProjetoTrilhaWeb/pages/admin/general/header.html');
	$('footer').load('/ProjetoTrilhaWeb/pages/admin/general/footer.html');

	
	/*
	 * Função para carregamento de páginas de conteúdo, que recebe como
	 * parâmetro o nome da pasta com a página a ser carregada
	 */

	COLDIGO.carregaPagina = pagename => {
		// Limpa a tag section, excluindo todo o conteúdo de dentro dela
		$("section").empty();
		
		// Carrega a página solicitada dentro da tag section
		$("section").load(`${pagename}/`, (response, status, info) => {
			if(status === "error"){
				let msg = 
					`<h1 class="errorStatus">${info.status} - ${info.statusText.toUpperCase()}</h1><br>
					<div>
						<h3 class="errorSub">Oops, mais que fria!</h3><br>
						<h4 class="errorDesc">Houve um erro ao encontrar a página</h4>
					</div>`;
				$("section").html(msg);
			}
		});
	}	
	
	//Define as configurações base de uma modal de aviso
	COLDIGO.exibirAviso = aviso => {
		let modal = {
				title: "Mensagem",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"OK": function() {
						$(this).dialog('close');
					}
				}
		};
		
		$('#modalAviso').html(aviso);
		$('#modalAviso').dialog(modal);
	
	}
	
	
	//Exibe os valores financeiros no formato da moeda Real
	COLDIGO.formatarDinheiro = valor => {
		return valor.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+\,)/g, "$1.");	
	}
	
});


