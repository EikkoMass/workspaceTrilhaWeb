COLDIGO.marca = new Object();
$(document).ready(() => {
	
	COLDIGO.marca.cadastrar = () => {
	
		let marca = 
		{
			nome: document.frmAddMarca.nome.value
		}
		
		if(marca.nome == ""){
			COLDIGO.exibirAviso("Preencha todos os campos de cadastro");
		}else{
		
		$.ajax({
			type: 'POST',
			url: `${COLDIGO.PATH}marca/inserir`,
			data: JSON.stringify(marca),
			success: msg => {
				COLDIGO.exibirAviso(msg);
				$('#addMarca').trigger('reset');
				COLDIGO.marca.buscar();
			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao cadastrar marca: ${info.status} - ${info.statusText}`);
			}
		});
		
		}
	};
	
	COLDIGO.marca.buscar = () => {
	
		let busca = $('#campoBuscaMarca').val();
		$.ajax({
			type: 'GET',
			url: `${COLDIGO.PATH}marca/buscar`,
			data: `busca=${busca}`,
			success: marcas => {

				marcas = JSON.parse(marcas);
				$('#listaMarcas').html(COLDIGO.marca.exibir(marcas));
			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao buscar marcas: ${info.status} - ${info.statusText}`);
			}
		});
	
	};
	
	COLDIGO.marca.exibir = listaDeMarcas => {

		let tabela = '<table><tr>' +
			'<th>Nome</th>' +
			'<th class="acoes">Ações</th>' +
			'</tr>';

		if (listaDeMarcas != undefined && listaDeMarcas.length > 0) {
			listaDeMarcas.forEach(marca => {

				tabela += `	<tr>
	 					<td>${marca.nome}</td>
	 					<td>
	 						<a onclick="COLDIGO.marca.exibirEdicao(${marca.id})"><img src='../../imgs/edit.png' alt="Editar registro"></a>
	 						<a onclick="COLDIGO.marca.excluir(${marca.id})"><img src='../../imgs/delete.png' alt="Excluir registro"></a>
	 					</td>
	 				</tr>`;
			});
		} else if (listaDeMarcas == "") {

			tabela += '<tr><td colspan="2">Nenhum Registro Encontrado</td></tr>';

		}

		tabela += '</table>';

		return tabela;
	};
	COLDIGO.marca.buscar();
	
	
	COLDIGO.marca.excluir = id => {
		
		$.ajax({
			type: 'DELETE',
			url: `${COLDIGO.PATH}marca/excluir/${id}`,
			success: msg => {
				COLDIGO.exibirAviso(msg);
				COLDIGO.marca.buscar();
			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao excluir marca: ${info.status} - ${info.statusText}`);		
			}
		});
		
	};

	COLDIGO.marca.exibirEdicao = id => {
	
		$.ajax({
			type: 'GET',
			url: `${COLDIGO.PATH}marca/buscarPorId`,
			data: `id=${id}`,
			success: marca => {
			
			let form = document.frmEditaMarca;
			
			form.idMarca.value = marca.id;
			form.nome.value = marca.nome;
			
			let modalEditaMarca = 
			{
				title: "Editar Marca",
				height: 400,
				width: 550,
				modal: true,
				buttons: {
					"Salvar": function() {
						COLDIGO.marca.editar();
					},
					
					"Cancelar": function() {
						$(this).dialog("close");
					}
				},
				close: () => {
					//caso o usuário simplesmente feche a caixa de edição, 
					//não deve acontecer nada
				}
			};
			
			$('#modalEditaMarca').dialog(modalEditaMarca);
			
			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao exibir edição de marca: ${info.status} - ${info.statusText}`);		
			}
		});
	
	};

	COLDIGO.marca.editar = () => {
	
		let form = document.frmEditaMarca;
		
		let marca = {
			id: form.idMarca.value,
			nome: form.nome.value
		};
	
		$.ajax({
			type: 'PUT',
			url: `${COLDIGO.PATH}marca/alterar`,
			data: JSON.stringify(marca),
			success: msg => {
			
				COLDIGO.exibirAviso(msg);
				COLDIGO.marca.buscar();
				$('#modalEditaMarca').dialog('close');
			
			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao editar marca: ${info.text} - ${info.statusText}`);
			}
		});
	};

});