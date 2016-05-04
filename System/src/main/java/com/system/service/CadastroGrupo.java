package com.system.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.system.model.Grupo;
import com.system.repository.Grupos;
import com.system.service.NegocioException;
import com.system.util.jpa.Transactional;

public class CadastroGrupo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Grupos grupos;
	
	@Transactional
	public void salvar(Grupo grupo) throws Exception {
		if (grupos.pesquisaPorNome(grupo) && (grupo.getCodigo() == null || grupo.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de usuário: " + grupo.getNome());
		}
		
		this.grupos.adicionar(grupo);
	}

	@Transactional
	public void excluir(Grupo grupo) {
		grupos.excluir(grupo);
		
	}
	
}
