package com.app.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.MaskFormatter;

public class Util {

	public static String formataCpf(String cpf) throws ParseException {
		MaskFormatter mf = new MaskFormatter("###.###.###-##");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(cpf);
	}
	
	public static String formataCnpj(String cnpj) throws ParseException {
		MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(cnpj);
	}
	
	public static String formataTelefone(String telefone) throws ParseException {
		MaskFormatter mf = new MaskFormatter("(##) ####-####");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(telefone);
	}
	
	public static String formataCep(String cep) throws ParseException {
		MaskFormatter mf = new MaskFormatter("#####-###");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(cep);
	}

	public static String normalize(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");

		return s;
	}
	
	public static Double formataMoeda(String valor) {
		return new Double(valor.replaceAll("\\.", "").replace(",", "."));
	}			

	public static boolean estaDentroDoPeriodo(Date hoje, Date inicio, Date fim) {
		return (hoje.compareTo(inicio) >= 0 && hoje.compareTo(fim) <= 0);
	}

	public static String formataMoeda(Double valor) {
		return NumberFormat.getCurrencyInstance().format(valor);
	}

	public static Integer getIdade(Date data) {
		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(data);
		Calendar dataAtual = Calendar.getInstance();

		Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);
		Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);
		Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));

		if (diferencaMes < 0 || (diferencaMes == 0 && diferencaDia < 0)) {
			idade--;
		}

		return idade;
	}	
	
	public static String setMD5Password(String password) {
		String senhaCriptografada = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
		senhaCriptografada = hash.toString(16);
		return senhaCriptografada;
	}
}
