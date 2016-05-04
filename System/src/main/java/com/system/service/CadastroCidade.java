package com.system.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.system.model.Cidade;
import com.system.repository.Cidades;
import com.system.service.NegocioException;
import com.system.util.jpa.Transactional;

public class CadastroCidade  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) throws NegocioException {
		if (cidades.pesquisaPorNome(cidade) && (cidade.getCodigo() == null || cidade.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de Cidade: "+cidade.getNome());
		}
		
		this.cidades.adicionar(cidade);
	}

	@Transactional
	public void excluir(Cidade cidade) {
		cidades.excluir(cidade);
		
	}

}
