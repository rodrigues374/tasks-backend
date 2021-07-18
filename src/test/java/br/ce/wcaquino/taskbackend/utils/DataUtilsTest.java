package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DataUtilsTest {

	@Test
	public void deveRetornaeTrueParaDatasFuturas() {
		LocalDate date = LocalDate.of(2030, 01 ,01);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetornaeFalseParaDatasFuturas() {
		LocalDate date = LocalDate.of(2010, 01 ,01);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetornaeTrueParaDatasAtual() {
		LocalDate date = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
}
