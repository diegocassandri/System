
package com.system.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import com.system.model.Grupo;
import com.system.model.Usuario;
import com.system.repository.Grupos;
import com.system.repository.Usuarios;
import com.system.service.CadastroGrupo;
import com.system.service.CadastroUsuario;
import com.system.util.jsf.FacesMessages;

@Named
@ViewScoped
public class CadastroGrupoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroGrupo gruposService;

	@Inject
	private Grupos grupos;

	@Inject
	private Usuarios usuarios;

	@Inject
	private CadastroUsuario usuarioService;

	private Grupo grupoEdicao = new Grupo();
	private Usuario usuarioEdicao = new Usuario();
	private Grupo grupoSelecionado;
	private List<Grupo> todosGrupos;
	private List<Grupo> filtroGrupos;

	private DualListModel<Usuario> todosUsuarios;

	List<Usuario> usuariosSource = new ArrayList<Usuario>();
	List<Usuario> usuariosTarget = new ArrayList<Usuario>();

	@PostConstruct
	public void prepararNovoCadastro() {
		grupoEdicao = new Grupo();
		todosUsuarios = new DualListModel<Usuario>();
	}

	public void salvar() {
		try {
			this.gruposService.salvar(grupoEdicao);
			consultar();
			messages.info("Grupo salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar grupo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
			e.printStackTrace();
		}

	}

	public void salvaListaUsuarios() {
		System.out.println("Salva Usuarios");
		grupoEdicao.getUsuarios().clear();
		salvar();
		for (Usuario usuario : todosUsuarios.getSource()) {

			try {
				usuario = usuarios.pesquisaPorId(usuario.getCodigo());
				usuario.getGrupos().remove(grupoEdicao);
				usuarioService.salvar(usuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Usuario usuario : todosUsuarios.getTarget()) {

			try {
				usuario = usuarios.pesquisaPorId(usuario.getCodigo());
				usuario.getGrupos().remove(grupoEdicao);
				usuario.getGrupos().add(grupoEdicao);
				usuarioService.salvar(usuario);
				consultar();
			} catch (Exception e) {
				FacesMessage mensagem = new FacesMessage(e.getMessage());
				messages.error("Erro ao salvar grupo! \n Motivo:" + mensagem.getDetail());
				RequestContext.getCurrentInstance()
						.update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
				e.printStackTrace();
			}
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));

	}

	public void excluir() {
		try {
			this.gruposService.excluir(grupoSelecionado);
			grupoSelecionado = null;
			consultar();
			messages.info("Grupo excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir grupo! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosGrupos = grupos.todos();
	}

	public Grupo getGrupoEdicao() {
		return grupoEdicao;
	}

	public void setGrupoEdicao(Grupo grupoEdicao) {
		this.grupoEdicao = grupos.pesquisaPorId(grupoEdicao.getCodigo());
		usuariosSource = grupos.usariosNaoAssociados(grupoEdicao);

		usuariosTarget = grupos.usariosAssociados(this.grupoEdicao);

		todosUsuarios = new DualListModel<Usuario>(usuariosSource, usuariosTarget);
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getTodosGrupos() {
		return todosGrupos;
	}

	public void setTodosGrupos(List<Grupo> todosGrupos) {
		this.todosGrupos = todosGrupos;
	}

	public List<Grupo> getFiltroGrupos() {
		return filtroGrupos;
	}

	public void setFiltroGrupos(List<Grupo> filtroGrupos) {
		this.filtroGrupos = filtroGrupos;
	}

	public DualListModel<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(DualListModel<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

}
