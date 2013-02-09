package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import com.app.dao.RoleDao;
import com.app.model.Role;
import com.app.util.FacesUtils;


@ManagedBean
public class RoleBean {

	private Role role = new Role();
	private List<Role> roles = new ArrayList<Role>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{roleDao}")
	private RoleDao roleDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;

	public void lista() {
		roles = roleDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void preparaParaAdicionar() {
		this.role = new Role();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_NOVO);
	}

	/*
	 * Todo usuário criado pelo sistema inicia com o PERFIL USUÁRIO
	 */
	public void adiciona() {

		roleDao.save(role);
		facesUtils
				.adicionaMensagemDeInformacao("Perfil adicionado com sucesso!");
		lista();
	}

	public void remove() {
		roleDao.delete(role);
		facesUtils.adicionaMensagemDeInformacao("Perfil removido com sucesso!");
		lista();
	}

	public void preparaParaAlterar(Role role) {
		this.role = roleDao.load(role.getName()); // evita
													// LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void altera() {

		roleDao.update(role);
		facesUtils
				.adicionaMensagemDeInformacao("Perfil atualizado com sucesso!");
		lista();
	}

	public void voltar() {
		this.role = new Role();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		lista();
	}
	
	public List<Role> getListaRoles() {
		setState(ESTADO_DE_PESQUISA);
		return roleDao.listAll();
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

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
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

	public UIForm getForm() {
		return form;
	}

	public void setForm(UIForm form) {
		this.form = form;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
