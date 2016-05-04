package com.system.service;



import java.io.Serializable;

import javax.inject.Inject;

import com.system.model.Estado;
import com.system.repository.Estados;
import com.system.util.jpa.Transactional;;


public class CadastroEstados  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private Estados  estados;

	@Inject
	public CadastroEstados(Estados estados) {
		this.estados = estados;
	}

	@Transactional
	public void salvar(Estado estado) throws NegocioException {
	
		this.estados.adicionar(estado);
	}
	
	@Transactional
	public void excluir(Estado estado) throws NegocioException {
		estado= this.estados.porId(estado.getCodigo());
		this.estados.excluir(estado);
	}
}
