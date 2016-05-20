package com.system.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.system.model.Processo;
import com.system.repository.Processos;
import com.system.service.CadastroProcesso;
import com.system.util.jsf.FacesMessages;

@Named
@ViewScoped
public class CadastroProcessoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroProcesso cadastroProcesso;

	@Inject
	private Processos processos;

	private Processo processoEdicao = new Processo();
	private Processo processoSelecionado;
	private List<Processo> todosProcessos;
	private List<Processo> filtroProcessos;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		processoEdicao = new Processo();
	}
	
	public void salvar() {
		try {
			this.cadastroProcesso.salvar(processoEdicao);
			processoEdicao = new Processo();
			consultar();
			messages.info("Processo salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:processo-table"));
			prepararNovoCadastro();

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Processo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:processo-table"));

		}

	}
	public void excluir() {
		try {
			System.out.println("exlcuir");
			this.cadastroProcesso.excluir(processoSelecionado);
			processoSelecionado = null;

			consultar();

			messages.info("Processo excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Processo! \n Motivo:" + mensagem);
		}

	}
	
	public void excluir(Processo processo) {
		try {
			this.cadastroProcesso.excluir(processo);
			processoSelecionado = null;

			consultar();

			messages.info("Processo excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Processo! \n Motivo:" + mensagem);
		}

	}
	

	public void consultar() {
		todosProcessos = processos.todos();
	}
	
	public void processoSelecionado(SelectEvent event) {
		Processo processo = (Processo) event.getObject();
		processoSelecionado = processo;
		processoEdicao = processo;
	}

	public Processos getProcessos() {
		return processos;
	}

	public void setProcessos(Processos processos) {
		this.processos = processos;
	}

	public Processo getProcessoEdicao() {
		return processoEdicao;
	}

	public void setProcessoEdicao(Processo processoEdicao) {
		this.processoEdicao = processoEdicao;
	}

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}

	public List<Processo> getTodosProcessos() {
		return todosProcessos;
	}

	public void setTodosProcessos(List<Processo> todosProcessos) {
		this.todosProcessos = todosProcessos;
	}

	public List<Processo> getFiltroProcessos() {
		return filtroProcessos;
	}

	public void setFiltroProcessos(List<Processo> filtroProcessos) {
		this.filtroProcessos = filtroProcessos;
	}
	
	

}
