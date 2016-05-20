package com.system.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


import com.system.model.Processo;
import com.system.model.Unidade;
import com.system.model.Usuario;
import com.system.repository.Processos;
import com.system.repository.Unidades;
import com.system.repository.Usuarios;
import com.system.service.CadastroUnidade;
import com.system.util.jsf.FacesMessages;

@Named
@ViewScoped
public class CadastroUnidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroUnidade cadastroUnidade;

	@Inject
	private Unidades unidades;

	@Inject
	private Processos processos;

	@Inject
	private Usuarios usuarios;

	private Unidade unidadeEdicao = new Unidade();
	private Unidade unidadeSelecionado;
	private List<Unidade> todosUnidades;
	private List<Unidade> filtroUnidades;
	private List<Usuario> todosUsuarios;
	private List<Processo> todosProcessos;
	private List<Processo> processosSelecionados = new ArrayList<Processo>();

	@PostConstruct
	public void prepararNovoCadastro() {
		unidadeEdicao = new Unidade();
		todosUsuarios = usuarios.todos();
		todosProcessos = processos.todos();
		processosSelecionados = new ArrayList<Processo>();
	}

	public void salvar() {
		try {
			this.cadastroUnidade.salvar(unidadeEdicao);
			unidadeEdicao = new Unidade();
			consultar();
			messages.info("Unidade salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:unidade-table", "frmCidade:tabview"));
			prepararNovoCadastro();

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Unidade! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:unidade-table"));

		}

	}

	public void salvarProcessos() {
		unidadeEdicao.getProcessos().removeAll(processosSelecionados);
		unidadeEdicao.getProcessos().clear();
		unidadeEdicao.getProcessos().addAll(processosSelecionados);
		System.out.println("----> " + processosSelecionados);
		salvar();
	}

	public void excluir() {
		try {
			System.out.println("exlcuir");
			this.cadastroUnidade.excluir(unidadeSelecionado);
			unidadeSelecionado = null;

			consultar();

			messages.info("Unidade excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Unidade! \n Motivo:" + mensagem);
		}

	}

	public void excluir(Unidade unidade) {
		try {
			this.cadastroUnidade.excluir(unidade);
			unidadeSelecionado = null;

			consultar();

			messages.info("Unidade excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Unidade! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosUnidades = unidades.todos();
	}

	public void unidadeSelecionado(SelectEvent event) {
		Unidade unidade = (Unidade) event.getObject();
		unidadeSelecionado = unidade;
		unidadeEdicao = unidade;

		
	}

	public Unidades getUnidades() {
		return unidades;
	}

	public void setUnidades(Unidades unidades) {
		this.unidades = unidades;
	}

	public Unidade getUnidadeEdicao() {
		return unidadeEdicao;
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

	public void setUnidadeEdicao(Unidade unidadeEdicao) {
		this.unidadeEdicao = unidades.pesquisaPorId(unidadeEdicao.getCodigo());
		
		processosSelecionados.clear();
		processosSelecionados.addAll(unidadeEdicao.getProcessos());
		System.out.println("--------------->>>>" + unidadeEdicao.getProcessos());
	

	}

	public Unidade getUnidadeSelecionado() {
		return unidadeSelecionado;
	}

	public void setUnidadeSelecionado(Unidade unidadeSelecionado) {
		this.unidadeSelecionado = unidadeSelecionado;
	}

	public List<Unidade> getTodosUnidades() {
		return todosUnidades;
	}

	public void setTodosUnidades(List<Unidade> todosUnidades) {
		this.todosUnidades = todosUnidades;
	}

	public List<Unidade> getFiltroUnidades() {
		return filtroUnidades;
	}

	public void setFiltroUnidades(List<Unidade> filtroUnidades) {
		this.filtroUnidades = filtroUnidades;
	}

	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public List<Processo> getTodosProcessos() {
		return todosProcessos;
	}

	public void setTodosProcessos(List<Processo> todosProcessos) {
		this.todosProcessos = todosProcessos;
	}

	public List<Processo> getProcessosSelecionados() {
		return processosSelecionados;
	}

	public void setProcessosSelecionados(List<Processo> processosSelecionados) {
		this.processosSelecionados = processosSelecionados;
	}

}
