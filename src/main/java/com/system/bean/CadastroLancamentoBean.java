package com.system.bean;

import java.io.Serializable;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.system.repository.Lancamentos;
import com.system.model.Lancamento;
import com.system.model.Pessoa;
import com.system.model.TipoLancamento;
import com.system.repository.Pessoas;
import com.system.service.CadastroLancamentos;
import com.system.service.NegocioException;

@Named
@javax.faces.view.ViewScoped
public class CadastroLancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroLancamentos cadastro;

	@Inject
	private Pessoas pessoas;

	@Inject
	private Lancamentos lancamentos;

	private Lancamento lancamento;
	private List<Pessoa> todasPessoas;

	public void prepararCadastro() {
		
		this.todasPessoas = this.pessoas.todas();
		if (this.lancamento == null) {
			System.out.println("Entrou");
			this.lancamento = new Lancamento();
			
		}
	}

	public List<String> pesquisarDescricoes(String descricao) {
		return this.lancamentos.descricoesQueContem(descricao);
	}

	public void dataVencimentoAlterada(AjaxBehaviorEvent event) {
		if (this.lancamento.getDataPagamento() == null) {
			this.lancamento.setDataPagamento(this.lancamento.getDataVencimento());
		}
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			this.cadastro.salvar(this.lancamento);

			this.lancamento = new Lancamento();
			context.addMessage(null, new FacesMessage("Lançamento salvo com sucesso!"));
		} catch (NegocioException e) {

			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
		return "";
	}

	public List<Pessoa> getTodasPessoas() {
		return this.todasPessoas;
	}

	public TipoLancamento[] getTiposLancamentos() {
		return TipoLancamento.values();
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

}
