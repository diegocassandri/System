package com.system.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.system.model.Unidade;
import com.system.repository.Unidades;
import com.system.util.jpa.Transactional;

public class CadastroUnidade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Unidades unidades;
	
	@Transactional
	public void salvar(Unidade unidade) throws Exception {
		if (unidades.pesquisaPorNome(unidade) && (unidade.getCodigo() == null || unidade.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma Unidade com este nome: " + unidade.getDescricao());
		}
		
		this.unidades.adicionar(unidade);
	}

	@Transactional
	public void excluir(Unidade unidade) {
		unidades.excluir(unidade);
	}
}


