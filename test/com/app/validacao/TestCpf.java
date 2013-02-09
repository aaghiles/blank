package com.app.validacao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.app.util.CustomValidator;


public class TestCpf {

	@Test
	public void testValidaCpf() {
		assertFalse(CustomValidator.validaCpf("12345678910"));
		assertFalse(CustomValidator.validaCpf("11111111111"));
		assertFalse(CustomValidator.validaCpf("52632541251"));		
		assertTrue(CustomValidator.validaCpf("14457588490"));
		assertTrue(CustomValidator.validaCpf("25668116280"));
		assertTrue(CustomValidator.validaCpf("80374160970"));
		assertTrue(CustomValidator.validaCpf("01750228360"));
		assertTrue(CustomValidator.validaCpf("94678073353"));
		assertTrue(CustomValidator.validaCpf("00357556364"));
		assertTrue(CustomValidator.validaCpf("01341198359"));
		assertTrue(CustomValidator.validaCpf("64556784786"));
		assertTrue(CustomValidator.validaCpf("34993568053"));
		assertTrue(CustomValidator.validaCpf("21722564474"));
		assertTrue(CustomValidator.validaCpf("82548283186"));
		assertTrue(CustomValidator.validaCpf("14268386386"));				
		assertTrue(CustomValidator.validaCpf("01234567890"));
	}
	
	@Test
	public void testValidaCnpj() {
		assertFalse(CustomValidator.validaCnpj("12345678910111"));
		assertFalse(CustomValidator.validaCnpj("52632541254125"));
		assertTrue(CustomValidator.validaCnpj("48878754000175"));				
	}	

}
