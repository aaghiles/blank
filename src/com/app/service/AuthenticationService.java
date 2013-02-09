package com.app.service;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	public boolean login(String username, String password) {

		boolean result = false;

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = authenticationManager.authenticate(token);

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				result = true;
			}
		} catch (AuthenticationException e) {
			result = false;
		}

		return result;
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		invalidateSession();
	}

	private void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
	}

}
