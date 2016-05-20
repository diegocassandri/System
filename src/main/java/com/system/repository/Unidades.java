package com.system.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.system.model.Processo;
import com.system.model.Unidade;



public class Unidades implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(Unidade unidade) {
		manager.merge(unidade);
	}

	public Unidade pesquisaPorId(Long id) {
		return manager.find(Unidade.class, id);
	}
	
	public Unidade pesquisaPorN(String usuario) {
		return manager.find(Unidade.class, usuario);
	}
	
	public boolean pesquisaPorNome(Unidade unidade) {
		Query query = manager.createQuery("From Unidade where descricao = :unidade", Unidade.class);
		query.setParameter("unidade", unidade.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(Unidade unidade) {
		unidade = pesquisaPorId(unidade.getCodigo());
		manager.remove(unidade);

	}

	public List<Unidade> todos() {
		return manager.createQuery("from Unidade", Unidade.class).getResultList();
	}
	
	public List<Processo> processosSelecionados(Unidade unidade) {
		return  unidade.getProcessos();
	}

}
