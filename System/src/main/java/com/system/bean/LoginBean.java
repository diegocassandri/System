package com.system.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.system.model.Usuario;
import com.system.security.UsuarioLogado;
import com.system.security.UsuarioSistema;
import com.system.service.CadastroUsuario;
import com.system.service.NegocioException;
import com.system.util.jsf.FacesMessages;
import com.system.util.jsf.FacesUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroUsuario cadastrosUsuario;
	
	/*@Inject
	private Usuarios usuarios;*/

	
	private String senha;
	private String senha2;
	private Usuario usuarioEdicao;
	
	
	private String email;

	public void preRender() {
		if ("true".equals(request.getParameter("invalid"))) {
			FacesUtil.addErrorMessage("Usuário ou senha inválidos!");
		}
	}
	
	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(request, response);
		
		facesContext.responseComplete();
	}
	
	public String mudarSenha() throws NoSuchAlgorithmException, NegocioException{
		if(senha.equals(senha2)){
			UsuarioSistema usuarioLogado = getUsuarioLogado();
			this.usuarioEdicao = usuarioLogado.getUsuario();
			this.usuarioEdicao.setMudarSenha(false);
			this.usuarioEdicao.setSenha(senha);
			cadastrosUsuario.salvar(this.usuarioEdicao);
			messages.info("Senha alterada com sucesso!");
			RequestContext.getCurrentInstance().execute("PF('mudarSenhaDialog').hide()");
			return "/Home.xhtml";
		}else{
			messages.error("As senhas devem ser iguais!");
			RequestContext.getCurrentInstance().update(Arrays.asList("senha-dialog:frmsenha:msgs-dialog"));
			return "";
		}
	}
	
	public void VerificaMudarSenhaUsuario() {
		boolean mudarSenha = false;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if (usuarioLogado != null) {
			mudarSenha= usuarioLogado.getUsuario().getMudarSenha();
			if(mudarSenha == true){
				RequestContext.getCurrentInstance().execute("PF('mudarSenhaDialog').show()");
			}
		}
	
	}
	
	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) 
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuario;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
}