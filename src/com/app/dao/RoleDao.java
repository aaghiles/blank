package com.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Role;


@Repository("roleDao")
@Transactional
public class RoleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> listAll() {
		return entityManager.createQuery("from Role", Role.class)
				.getResultList();
	}

	public void save(Role role) {
		entityManager.persist(role);
	}

	public void update(Role role) {
		entityManager.merge(role);
	}

	public void delete(Role role) {
		entityManager.remove(entityManager.merge(role));
	}
	
	public List<Role> getListaPerfis(){
		return listAll();
	}
	

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Role load(String id) {
		return entityManager.find(Role.class, id);
	}	

}
