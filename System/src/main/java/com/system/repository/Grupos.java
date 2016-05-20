package com.system.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.system.model.Grupo;
import com.system.model.Usuario;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(Grupo grupo) throws Exception {
		/*manager.joinTransaction();*/
		manager.merge(grupo);
		
	}

	public Grupo pesquisaPorId(Long id) {
		return manager.find(Grupo.class, id);
	}

	public boolean pesquisaPorNome(Grupo grupo) {
		Query query = manager.createQuery("From Grupo where descricao = :descricao", Grupo.class);
		query.setParameter("descricao", grupo.getNome());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public void excluir(Grupo grupo) {
		grupo = pesquisaPorId(grupo.getCodigo());
		manager.remove(grupo);

	}

	public List<Grupo> todos() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> usariosNaoAssociados(Grupo grupo) {

		Query query = manager.createQuery("Select u From Usuario u Where not exists (Select g from u.grupos g Where g = :grupo)", Usuario.class);
		query.setParameter("grupo", grupo);
		return query.getResultList();

	}

	public List<Usuario> usariosAssociados(Grupo grupo) {
		return grupo.getUsuarios();
	}
	
	public Grupo Atualizar(Grupo grupo){
	     manager.refresh(grupo);	
		return grupo;
	}

}
