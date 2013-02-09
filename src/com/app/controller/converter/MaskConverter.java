package com.app.controller.converter;

import java.text.ParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.app.util.LogUtil;
import com.app.util.Util;


@FacesConverter(value = "maskConverter")
public class MaskConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String valorTela) throws ConverterException {

		if (valorTela == null)
			return null;
		
		return valorTela.replaceAll("\\D", "");
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object valorTela) throws ConverterException {

		String result = (String) valorTela;		

		try {
			switch (result.length()) {
			case 11:
				result = Util.formataCpf(result);
				break;
			
			case 10:
				result = Util.formataTelefone(result);
				break;

			case 8:
				result = Util.formataCep(result);
				break;
				
			case 14:
				result = Util.formataCnpj(result);
				break;	
				
			default:
				result = "";
				break;
			}
		} catch (ParseException e) {
			LogUtil.logError(e.getMessage());
		}

		return result;
	}

}
