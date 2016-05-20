package com.system.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.system.model.Estado;
import com.system.repository.Estados;

@Named
@ViewScoped
public class SelecaoEstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Estados estados;
	
	private String nome;
	
	private List<Estado> estadosFiltrados;

	public void pesquisar() {
		estadosFiltrados = estados.porNomeSemelhante(nome);
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 430);
		opcoes.put("contentWidth", 430);
		
		RequestContext.getCurrentInstance().openDialog("/cadastros/SelecaoEstado.xhtml", opcoes, null);
	}
	
	public void selecionar(Estado estado) {
		RequestContext.getCurrentInstance().closeDialog(estado);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Estado> getestadosFiltrados() {
		return estadosFiltrados;
	}

}