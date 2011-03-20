package org.c3sl.ufpr.br.tests.correction;

import junit.framework.Assert;

import org.c3sl.ufpr.br.correction.AbstractCorrection;
import org.c3sl.ufpr.br.correction.CorrectionOne;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class CorrectionOneTest {

	private static AbstractCorrection c;
	private static int n;
	
	@org.junit.BeforeClass
	public static void setup(){
		c =  createMockBuilder(CorrectionOne.class).
				addMockedMethods("saveHit", "saveState").createMock();
		
		n = 2455;
		c.isCorrect(n+"", 0, 2);
	}

	@Test
	public void should_test_correction_true(){
		Assert.assertTrue(c.isCorrect(n+"", 0, 2));
		
		Assert.assertTrue(c.isCorrect(n+"/1.99999999999999", 1, 2));
		
		Assert.assertTrue(c.isCorrect(n+"/2", 1, 2));
		Assert.assertTrue(c.isCorrect(n+"/4", 2, 2));
		Assert.assertTrue(c.isCorrect(n+"/4.00000001", 2, 2));
		
		Assert.assertTrue(c.isCorrect(n+"/7.9999999", 3, 2));
		Assert.assertTrue(c.isCorrect(n+"/8", 3, 2));
	}
	
	@Test
	public void should_test_correction_false(){
		Assert.assertFalse(c.isCorrect(n+"/1.9", 1, 2));
		Assert.assertFalse(c.isCorrect(n+"/3", 1, 2));
		Assert.assertFalse(c.isCorrect(n+"/5", 2, 2));
		Assert.assertFalse(c.isCorrect(n+"/7", 3, 2));
		Assert.assertFalse(c.isCorrect(n+"/9", 3, 2));
		Assert.assertFalse(c.isCorrect(n+"/8.5", 3, 2));
	}
	
	@Test
	public void should_be_great_then(){
		Assert.assertEquals(-2, c.compareDouble(10.0, 10.001, 0.001));
	}
	
	@Test
	public void should_be_less_then(){
		Assert.assertEquals(c.compareDouble(10.0, 9.001, 0.001), -1);
	}
	
	@Test
	public void should_be_formula_correct(){
		Assert.assertTrue(c.isCorrect(n+"/2^n", 4, 2));
	}
}
