package com.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Usuario;


@Repository("usuarioDao")
@Transactional
public class UsuarioDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Usuario> listAll() {
		return (List<Usuario>) createCriteria()
				.add(Restrictions.eq("enabled", true))
				.add(Restrictions.eq("mainUser", false))
				.list();
	}

	public void save(Usuario usuario) {
		entityManager.persist(usuario);
	}

	public void update(Usuario usuario) {
		entityManager.merge(usuario);
	}

	public void delete(Usuario usuario) {
		entityManager.remove(entityManager.merge(usuario));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Usuario findBy(String login) {
		return (Usuario) createCriteria()
				.add(Restrictions.eq("username", login))
				.uniqueResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Usuario findBy(String login, String senha) {
		return (Usuario) createCriteria().add(Restrictions.eq("username", login))
				.add(Restrictions.eq("password", senha)).uniqueResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Usuario load(Long id) {
		return entityManager.find(Usuario.class, id);
	}

	private Criteria createCriteria() {
		Session session = ((Session) entityManager.getDelegate());
		return session.createCriteria(Usuario.class);
	}

}
