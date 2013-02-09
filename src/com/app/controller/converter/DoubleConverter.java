package com.app.controller.converter;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "doubleConverter")
public class DoubleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String valorTela) throws ConverterException {
		
		if (valorTela != null) {
			int specialChar = 0;
			for (int i = 0; i < valorTela.length(); i++) {
				if (valorTela.substring(i, i + 1).equals(".") || valorTela.substring(i, i + 1).equals(",")) {
					specialChar++;
				}
			}
			if (specialChar > 1) {
				valorTela = valorTela.replace(".", "");
			}
			return (valorTela.equals("")) ? new Double(0.0d) : Double
					.valueOf(valorTela.replaceAll("\\D", "."));
		} else {
			return new Double(0.0d);
		}
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object valorTela) throws ConverterException {

		String result = "";

		if (valorTela == null || valorTela.toString().trim().equals("")) {
			result = "0.00";

		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			result = df.format(Double.valueOf((Double) valorTela));
		}

		return result;
	}

}
