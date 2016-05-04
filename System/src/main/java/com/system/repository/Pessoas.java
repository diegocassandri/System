package com.system.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.system.model.Pessoa;

public class Pessoas implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Class<Pessoa> classe;
	private EntityManager manager;

	@Inject
	public Pessoas(EntityManager manager) {
		
		this.manager = manager;
	}

	public Pessoa porId(Long id) {
		return manager.find(Pessoa.class, id);
	}
	
	public List<Pessoa> todas() {
		TypedQuery<Pessoa> query = manager.createQuery(
				"from Pessoa", Pessoa.class);
		return query.getResultList();
	}

	public Class<Pessoa> getClasse() {
		return classe;
	}

	public void setClasse(Class<Pessoa> classe) {
		this.classe = classe;
	}
	

}