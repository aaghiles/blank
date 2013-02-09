package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import com.app.dao.RoleDao;
import com.app.dao.UsuarioDao;
import com.app.model.Role;
import com.app.model.Usuario;
import com.app.util.FacesUtils;


@ManagedBean
public class UsuarioBean {

private static final String SENHA_PADRAO = "123456";
	
	private Long id;
	private String loginAtual;
	private String novaSenha;
	
	private Usuario usuario = new Usuario();
	private String confirmacaoDeSenha;
	private Role role = new Role();
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{usuarioDao}")
	private UsuarioDao usuarioDao;
	@ManagedProperty("#{roleDao}")
	private RoleDao roleDao;	
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	
	public void init() {
		if(id != null) {
			usuario = usuarioDao.load(id);
			setState(ESTADO_DE_EDICAO);
			id = null;
		}
	}
	
	public void resetPassword(){
		Usuario usuario = usuarioDao.findBy(loginAtual);
		if(usuario == null) {
			facesUtils.adicionaMensagemDeErro("Usuário não encontrado.");
			return;
		}
		usuario.setPassword(SENHA_PADRAO);
		usuarioDao.update(usuario);
		facesUtils.adicionaMensagemDeInformacao("Senha Padrão redefinida para usuário " + loginAtual);
		
		loginAtual = null;
	}

	public void lista() {
		usuarios = usuarioDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void preparaParaAdicionar() {
		this.usuario = new Usuario();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_NOVO);
	}

	/*
	 * Todo usuário criado pelo sistema inicia com o PERFIL USUÁRIO
	 */
	public void adiciona() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleDao.load("USUARIO"));
		usuario.setRoles(roles);
		usuario.setEnabled(true);
		usuario.setPassword(SENHA_PADRAO);
		usuarioDao.save(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário adicionado com sucesso!");
		lista();
	}

	public void remove() {
		usuario.setEnabled(false);
		usuarioDao.update(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário removido com sucesso!");
		lista();
	}

	public void preparaParaAlterar(Usuario usuario) {
		this.usuario = usuarioDao.load(usuario.getId()); // evita LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void altera() {
		usuarioDao.update(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário atualizado com sucesso!");
		lista();
	}
	
	public void alteraPerfil() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		usuario.setRoles(roles);
		usuarioDao.update(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário atualizado com sucesso!");
		lista();
	}
	
	public void alteraLoginSenha() {				
		usuarioDao.update(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário atualizado com sucesso!");
		lista();
	}

	public void voltar() {
		this.usuario = new Usuario();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		lista();
	}

	public boolean isAdicionando() {
		return ESTADO_DE_NOVO.equals(state);
	}

	public boolean isEditando() {
		return ESTADO_DE_EDICAO.equals(state);
	}

	public boolean isPesquisando() {
		return ESTADO_DE_PESQUISA.equals(state);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmacaoDeSenha() {
		return confirmacaoDeSenha;
	}

	public void setConfirmacaoDeSenha(String confirmacaoDeSenha) {
		this.confirmacaoDeSenha = confirmacaoDeSenha;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UIForm getForm() {
		return form;
	}

	public void setForm(UIForm form) {
		this.form = form;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginAtual() {
		return loginAtual;
	}

	public void setLoginAtual(String loginAtual) {
		this.loginAtual = loginAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}


}
