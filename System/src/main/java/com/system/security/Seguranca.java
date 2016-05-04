package com.system.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {

	@Inject
	private ExternalContext externalContext;
	
	public String getNomeUsuario() {
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if (usuarioLogado != null) {
			nome = usuarioLogado.getUsuario().getNomeCompleto();
		}
		return nome;
	}
	
	public void VerificaMudarSenhaUsuario() {
		boolean mudarSenha = false;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if (usuarioLogado != null) {
			mudarSenha= usuarioLogado.getUsuario().getMudarSenha();
			if(mudarSenha == true){
				System.out.println("----> Vai Mudar a Senha!!");
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
	
	public boolean isEmitirPedidoPermitido() {
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("VENDEDORES");
	}
	
	public boolean isCancelarPedidoPermitido() {
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("VENDEDORES");
	}
	
}
