package com.system.service;

import java.io.Serializable;
import javax.inject.Inject;

import com.system.model.Usuario;
import com.system.repository.Usuarios;
import com.system.service.NegocioException;
import com.system.util.jpa.Transactional;


public class CadastroUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) throws NegocioException {
		if (usuarios.pesquisaPorNome(usuario) && (usuario.getCodigo() == null || usuario.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de usuário: "+usuario.getUsuario());
		}
		
		this.usuarios.adicionar(usuario);
	}

	@Transactional
	public void excluir(Usuario usuario) {
		usuarios.excluir(usuario);
		
	}

}
