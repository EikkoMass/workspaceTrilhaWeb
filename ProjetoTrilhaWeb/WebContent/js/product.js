COLDIGO.produto = new Object();

$(document).ready(() => {
	alert('tentando buscar marcas');
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

				COLDIGO.exibirAviso('success');

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
		debugger;

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
				},
				error: info => {
					COLDIGO.exibirAviso(`Erro ao cadastrar um novo produto: ${info.status} - ${info.statusText}`);
				}
			});

		}

	}

});