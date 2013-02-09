package com.app.controller.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.app.util.CustomValidator;


@FacesValidator("cnpjValidator")
public class CNPJValidator implements Validator {

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value)
			throws ValidatorException {		

		String cnpj = (String) value;

		if (!CustomValidator.validaCnpj(cnpj.replaceAll("\\D", ""))) {			
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "CNPJ Inválido!", ""));
		}

	}
}
