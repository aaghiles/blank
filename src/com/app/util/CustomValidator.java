package com.app.util;

import java.util.ArrayList;
import java.util.List;

public class CustomValidator {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	public static boolean validaCpf(String cpf) {
		if (verificaCpfPadrao(cpf)) {
			return false;
		}

		return isValidCPF(cpf);
	}

	public static boolean isValidCPF(String cpf) {
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString()	+ digito2.toString());
	}

	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean verificaCpfPadrao(String cpf) {
		List<String> cpfPadrao = new ArrayList<String>();

		cpfPadrao.add("00000000000");
		cpfPadrao.add("11111111111");
		cpfPadrao.add("22222222222");
		cpfPadrao.add("33333333333");
		cpfPadrao.add("44444444444");
		cpfPadrao.add("55555555555");
		cpfPadrao.add("66666666666");
		cpfPadrao.add("77777777777");
		cpfPadrao.add("88888888888");
		cpfPadrao.add("99999999999");

		return cpfPadrao.contains(cpf);
	}
	
	public static boolean verificaCnpjPadrao(String cnpj) {
		List<String> cnpjPadrao = new ArrayList<String>();

		cnpjPadrao.add("00000000000000");
		cnpjPadrao.add("11111111111111");
		cnpjPadrao.add("22222222222222");
		cnpjPadrao.add("33333333333333");
		cnpjPadrao.add("44444444444444");
		cnpjPadrao.add("55555555555555");
		cnpjPadrao.add("66666666666666");
		cnpjPadrao.add("77777777777777");
		cnpjPadrao.add("88888888888888");
		cnpjPadrao.add("99999999999999");

		return cnpjPadrao.contains(cnpj);
	}
	
	public static boolean validaCnpj(String cnpj) {
		if (verificaCnpjPadrao(cnpj)) {
			return false;
		}

		return isValidCNPJ(cnpj);
	}

	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

}
