package com.system.bean;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.system.model.Estado;
import com.system.repository.Estados;
import com.system.service.CadastroEstados;

import com.system.util.jsf.FacesMessages;





@Named
@javax.faces.view.ViewScoped
public class CadastroEstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroEstados cadastroEstado;

	@Inject
	private Estados estados;

	private Estado estadoEdicao = new Estado();
	private Estado estadoSelecionado;
	private List<Estado> todosEstados;
	private List<Estado> filtroEstados;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		estadoEdicao = new Estado();
	}

	public void salvar() {
		try {
			this.cadastroEstado.salvar(estadoEdicao);
			estadoEdicao = new Estado();
			consultar();
			messages.info("Estado salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:estado-table"));
			prepararNovoCadastro();

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar estado! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:estado-table"));

		}

	}


	public void excluir() {
		try {
			System.out.println("exlcuir");
			this.cadastroEstado.excluir(estadoSelecionado);
			estadoSelecionado = null;

			consultar();

			messages.info("Estado excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir estado! \n Motivo:" + mensagem);
		}

	}
	
	public void excluir(Estado estado) {
		try {
			System.out.println("exlcuir");
			this.cadastroEstado.excluir(estado);
			estadoSelecionado = null;

			consultar();

			messages.info("Estado excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir estado! \n Motivo:" + mensagem);
		}

	}
	

	public void consultar() {
		todosEstados = estados.todos();
	}

	public void estadoSelecionado(SelectEvent event) {
		Estado estado = (Estado) event.getObject();
		estadoSelecionado = estado;
		estadoEdicao = estado;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}

	public Estado getEstadoEdicao() {
		return estadoEdicao;
	}

	public void setEstadoEdicao(Estado estadoEdicao) {
		this.estadoEdicao = estadoEdicao;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}

	public List<Estado> getFiltroEstados() {
		return filtroEstados;
	}

	public void setFiltroEstados(List<Estado> filtroEstados) {
		this.filtroEstados = filtroEstados;
	}
	
	

	
	
	

}
