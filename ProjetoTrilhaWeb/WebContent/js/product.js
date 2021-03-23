COLDIGO.produto = new Object();

$(document).ready(() => {
	COLDIGO.produto.carregarMarcas = () => {
		$.ajax({
			type: 'GET',
			url: `${COLDIGO.PATH}marca/buscar`,
			success: marcas => {

				if (marcas != "") {

					$('#selMarca').html("");
					let option = document.createElement('option');
					option.setAttribute('value', '');
					option.innerHTML = 'Escolha';
					$('#selMarca').append(option);

					for (let i = 0; i < marcas.length; i++) {

						option = document.createElement('option');
						option.setAttribute('value', marcas[i].id);
						option.innerHTML = (marcas[i].nome);
						$('#selMarca').append(option);
					};

				} else {

					$('#selMarca').html("");

					var option = document.createElement('option');
					option.setAttribute('value', '');
					option.innerHTML = ("Cadastre um marca primeiro!");
					$('#selMarca').append(option);
					$('#selMarca').addClass('aviso');
				}

			},
			error: info => {
				COLDIGO.exibirAviso(`Erro ao buscar as marcas ${info.status} - ${info.statusText}`);

				$('#selMarca').html("");
				var option = document.createElement('option');
				option.setAttribute('value', '');
				option.innerHTML = ("Erro ao carregar marcas!");
				$('#selMarca').append(option);
				$('#selMarca').addClass('aviso');
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
	 						<a><img src='../../imgs/edit.png' alt="Editar registro"></a>
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

});