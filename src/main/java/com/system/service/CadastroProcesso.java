package com.system.service;

import java.io.Serializable;

import javax.inject.Inject;


import com.system.model.Processo;
import com.system.repository.Processos;
import com.system.util.jpa.Transactional;

public class CadastroProcesso implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Processos processos;
	
	@Transactional
	public void salvar(Processo processo) throws Exception {
		if (processos.pesquisaPorNome(processo) && (processo.getCodigo() == null || processo.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Processo com este nome: " + processo.getDescricao());
		}
		
		this.processos.adicionar(processo);
	}

	@Transactional
	public void excluir(Processo processo) {
		processos.excluir(processo);
		
	}
}
