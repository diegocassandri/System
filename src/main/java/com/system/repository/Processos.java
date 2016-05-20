package com.system.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.system.model.Processo;

public class Processos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(Processo processo) throws Exception {
		manager.merge(processo);
	}
	
	public Processo pesquisaPorId(Long id) {
		return manager.find(Processo.class, id);
	}
	
	public Processo porId(Long id) {
		return manager.find(Processo.class, id);
	}
	
	public boolean pesquisaPorNome(Processo processo) {
		Query query = manager.createQuery("From Processo where descricao = :descricao", Processo.class);
		query.setParameter("descricao", processo.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public void excluir(Processo processo) {
		processo = pesquisaPorId(processo.getCodigo());
		manager.remove(processo);
	}

	public List<Processo> todos() {
		return manager.createQuery("from Processo", Processo.class).getResultList();
	}

}
