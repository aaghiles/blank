package com.app.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.app.dao.UsuarioDao;
import com.app.model.Usuario;


@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		UsuarioDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{usuarioDao}", UsuarioDao.class);

		Usuario usuario = dao.load(id);
		return usuario;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Usuario usuario = (Usuario) value;
		return usuario.getId().toString();
	}
}
