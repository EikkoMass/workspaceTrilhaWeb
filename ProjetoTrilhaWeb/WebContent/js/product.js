COLDIGO.produto = new Object();

$(document).ready(() => {

	//Carrega as marcas registradas no BD no select do formulário de inserir ou editar
	COLDIGO.produto.carregarMarcas = id => {
	
		let select = 
		id != undefined ? '#selMarcaEdicao' : '#selMarca';
	
		$.ajax({
			type: 'GET',
			url: `${COLDIGO.PATH}marca/buscar`,
			success: marcas => {

				if (marcas != "") {

					$(select).html("");
					let option = document.createElement('option');
					option.setAttribute('value', '');
					option.innerHTML = 'Escolha';
					$(select).append(option);

					marcas.forEach( marca => {

						option = document.createElement('option');
						option.setAttribute('value', marca.id);
						
						if((id != undefined) && (id == marca.id)){
							option.setAttribute('selected', 'selected');
						}
						
						option.innerHTML = (marca.nome);
						$(select).append(option);
					});

				} else {

					$(select).html("");

					var option = document.createElement('option');
					option.setAttribute('value', '');
					option.innerHTML = ("Cadastre um marca primeiro!");
					$(select).append(option);
					$(select).addClass('aviso');
				}

			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao buscar as marcas ${info.status} - ${info.statusText}`);

				$(select).html("");
				var option = document.createElement('option');
				option.setAttribute('value', '');
				option.innerHTML = ("Erro ao carregar marcas!");
				$(select).append(option);
				$(select).addClass('aviso');
			}
		});
	}


	COLDIGO.produto.carregarMarcas();

	COLDIGO.produto.cadastrar = () => {

		let form = document.frmAddProduto;
		let produto = {

			categoria: form.categoria.value,
			marcaId: form.marcaId.value,
			modelo: form.modelo.value,
			capacidade: form.capacidade.value,
			valor: form.valor.value

		};

		if (produto.categoria == "" || produto.marcaId == "" || produto.modelo == ""
			|| produto.capacidade == "" || produto.valor == "") {

			COLDIGO.exibirAviso("Preencha todos os campos");

		} else {


			$.ajax({
				type: 'POST',
				url: `${COLDIGO.PATH}produto/inserir`,
				data: JSON.stringify(produto),
				success: msg => {
					COLDIGO.exibirAviso(msg);
					$('#addProduto').trigger('reset');
					COLDIGO.produto.buscar()
				},
				error: info => {
					COLDIGO.exibirAviso(`Erro ao cadastrar um novo produto: ${info.status} - ${info.statusText}`);
				}
			});

		}

	}


	/* Busca no BD e exibe na página os produtos que atendam a solicitação de usuário */
	COLDIGO.produto.buscar = () => {

		let valorBusca = $("#campoBuscaProduto").val();

		$.ajax({
			type: 'GET',
			url: `${COLDIGO.PATH}produto/buscar`,
			data: `valorBusca=${valorBusca}`,
			success: dados => {

				dados = JSON.parse(dados);


				$('#listaProdutos').html(COLDIGO.produto.exibir(dados));

			},
			error: info => {
				COLDIGO.exibirAviso(
					`Erro ao consultar os contatos: ${info.status} - ${info.statusText}`);
			}
		});
	};

	//Transforma os dados dos produtos recebidos do servidor em uma tabela HTML
	COLDIGO.produto.exibir = listaDeProdutos => {

		let tabela = '<table><tr>' +
			'<th>Categoria</th>' +
			'<th>Marca</th>' +
			'<th>Modelo</th>' +
			'<th>Cap.(1)</th>' +
			'<th>Valor</th>' +
			'<th class="acoes">Ações</th>' +
			'</tr>';

		if (listaDeProdutos != undefined && listaDeProdutos.length > 0) {
			listaDeProdutos.forEach(produto => {

				tabela += `	<tr>
	 					<td>${produto.categoria}</td>
	 					<td>${produto.marcaNome}</td>
	 					<td>${produto.modelo}</td>
	 					<td>${produto.capacidade}</td>
	 					<td>R$ ${COLDIGO.formatarDinheiro(produto.valor)}</td>
	 					<td>
	 						<a onclick="COLDIGO.produto.exibirEdicao(${produto.id})"><img src='../../imgs/edit.png' alt="Editar registro"></a>
	 						<a onclick="COLDIGO.produto.excluir(${produto.id})"><img src='../../imgs/delete.png' alt="Excluir registro"></a>
	 					</td>
	 				</tr>`;
			})
		} else if (listaDeProdutos == "") {

			tabela += '<tr><td colspan="6">Nenhum Registro Encontrado</td></tr>';

		}

		tabela += '</table>';

		return tabela;
	};

	//Executa a função de busca ao carregar a página
	COLDIGO.produto.buscar();

	COLDIGO.produto.excluir = id => {
		$.ajax({
			type: "DELETE",
			url: `${COLDIGO.PATH}produto/excluir/${id}`,
			success: msg => {
				COLDIGO.exibirAviso(msg);
				COLDIGO.produto.buscar();
			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao excluir produto: ${info.status} - ${info.statusText}`);
			}
		});

	}

	COLDIGO.produto.exibirEdicao = id => {
	$.ajax({
		type: 'GET',
		url: `${COLDIGO.PATH}produto/buscarPorId`,
		data: `id=${id}`,
		success: produto => {
		
			let form = document.frmEditaProduto;
			
			form.idProduto.value = produto.id;
			form.modelo.value = produto.modelo;
			form.capacidade.value = produto.capacidade;
			form.valor.value = produto.valor;
			
			let selCategoria = document.getElementById('selCategoriaEdicao');
			for(let i = 0; i < selCategoria.length; i++) {
			
			selCategoria.options[i].value == produto.categoria ?
				selCategoria.options[i].setAttribute('selected', 'selected')
				: selCategoria.options[i].removeAttribute('selected');
									
			}
			
			COLDIGO.produto.carregarMarcas(produto.marcaId);
			
			let modalEditaProduto = 
			{
				title: "Editar Produto",
				height: 400,
				width: 550,
				modal: true,
				buttons: {
					"Salvar": function() {
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
			
			$('#modalEditaProduto').dialog(modalEditaProduto);
		},
		error: info => {
				COLDIGO.exibirAviso(`Erro ao buscar produto para edição ${info.status} - ${info.statusText}`);
		}
		
		});
	}
});