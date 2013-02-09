package com.app.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.dao.UsuarioDao;
import com.app.model.Usuario;
import com.app.service.AuthenticationService;
import com.app.util.Bootstrap;
import com.app.util.FacesUtils;
import com.app.util.Util;


@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {

	private static final String INDEX = "/pages/index?faces-redirect=true";
	private static final String LOGIN = "/auth/login?faces-redirect=true";
	private static final String SENHA_PADRAO = "123456";
	
	private static final String ESTADO_PRIMEIRO_ACESSO = "_primeiroLogin";
	private static final String ESTADO_LOGIN = "_login";
	
	private String state = ESTADO_LOGIN;

	private String login;
	private String senha;
	
	private String loginAtual;
	private String senhaAtual;
	private String novoLogin = "";
	private String novaSenha;
	private String confirmacaoDeSenha;
	private boolean senhaPadrao = false;

	@ManagedProperty("#{authenticationService}")
	private AuthenticationService authenticationService;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{usuarioWeb}")
	private UsuarioWeb usuarioWeb;
	@ManagedProperty("#{usuarioDao}")
	private UsuarioDao usuarioDao;
	@ManagedProperty("#{bootstrap}")
	private Bootstrap bootstrap;

	@PostConstruct
	public void prepareBootstrap() {
		if (usuarioDao.listAll().size() == 0) {
			bootstrap.setup();
		}
	}

	public String logar() {
		Usuario usuario = null;
		senhaPadrao = this.senha.equals(Util.setMD5Password(SENHA_PADRAO));
		boolean usuarioAutenticado = authenticationService.login(login, senha);

		if (!usuarioAutenticado) {
			facesUtils.adicionaMensagemDeErro("Usuário ou senha inválida!");
			
			return null;
		}
		
		if(senhaPadrao){
			setState(ESTADO_PRIMEIRO_ACESSO);
			return null;
		}

		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				usuario = (Usuario) authentication.getPrincipal();
				usuarioWeb.loga(usuario);
			}
		}

		return INDEX;
	}

	public String logout() {
		authenticationService.logout();
		setState(ESTADO_LOGIN);
		return LOGIN;
	}
	
	public void alterarCredenciais(){
		Usuario usuario = usuarioDao.findBy(loginAtual, Util.setMD5Password(senhaAtual));
		if(usuario != null) {
			boolean senhaInvalida = !confirmacaoDeSenha.equals(novaSenha);
			if (senhaInvalida) {
				facesUtils.adicionaMensagemDeErro("Senha e confirmação de senha não conferem.");
				return;
			}
			
			if(novaSenha.equalsIgnoreCase(SENHA_PADRAO)){
				facesUtils.adicionaMensagemDeErro("A nova senha deve ser diferente da senha padrão.");
				return;
			}
			
			if(!novoLogin.isEmpty() && !usuario.getUsername().equals(novoLogin)) {
				Usuario verificaLogin = usuarioDao.findBy(novoLogin);
				if(verificaLogin != null) {
					facesUtils.adicionaMensagemDeErro("O novo login informado já está em uso.");
					return;
				}
				else {
					usuario.setUsername(novoLogin);
				}
			}			
			usuario.setPassword(novaSenha);
			usuarioDao.update(usuario);
			facesUtils.adicionaMensagemDeInformacao("Usuário atualizado com sucesso!");	
			logout();
		}
		else {
			facesUtils.adicionaMensagemDeErro("Usuário não encontrado.");
		}
	}
	
	public boolean isPrimeiroAcesso() {
		return ESTADO_PRIMEIRO_ACESSO.equals(state);
	}
	
	public boolean isAcessoNormal() {
		return ESTADO_LOGIN.equals(state);
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = Util.setMD5Password(senha);
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}
	
	public String getLoginAtual() {
		return loginAtual;
	}

	public void setLoginAtual(String loginAtual) {
		this.loginAtual = loginAtual;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovoLogin() {
		return novoLogin;
	}

	public void setNovoLogin(String novoLogin) {
		this.novoLogin = novoLogin;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmacaoDeSenha() {
		return confirmacaoDeSenha;
	}

	public void setConfirmacaoDeSenha(String confirmacaoDeSenha) {
		this.confirmacaoDeSenha = confirmacaoDeSenha;
	}

	public boolean isSenhaPadrao() {
		return senhaPadrao;
	}

	public void setSenhaPadrao(boolean senhaPadrao) {
		this.senhaPadrao = senhaPadrao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}
}
