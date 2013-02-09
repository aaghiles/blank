package com.app.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.app.model.Usuario;


@Component("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		return findByUsername(username);
	}

	private Usuario findByUsername(String username) {
		try {
			return entityManager
					.createNamedQuery("Usuario.findByUsername", Usuario.class)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
