package org.c3sl.ufpr.br.tests.correction;

import static org.easymock.EasyMock.createMockBuilder;
import junit.framework.Assert;

import org.c3sl.ufpr.br.correction.Correction;
import org.c3sl.ufpr.br.correction.CorrectionThree;
import org.junit.Test;

public class CorrectionThreeTest {

	private static Correction c;
	
	@org.junit.BeforeClass
	public static void setup(){
		c =  createMockBuilder(CorrectionThree.class).
		addMockedMethods("saveHit", "saveState").createMock();
	}

	@Test
	public void should_test_correction_true(){
		Assert.assertTrue(c.isCorrect("3*ℓ" , 0, 3));
		Assert.assertTrue(c.isCorrect("9*ℓ/2", 1, 3));
		Assert.assertTrue(c.isCorrect("27*ℓ/4", 2, 3));
		Assert.assertTrue(c.isCorrect("81*ℓ/8", 3, 3));
		
		Assert.assertTrue(c.isCorrect("243*ℓ/16", 4, 3));
		Assert.assertTrue(c.isCorrect("ℓ/16*243", 4, 3));
	}

	@Test
	public void should_be_formula_correct(){
		Assert.assertTrue(c.isCorrect("ℓ/2^n * 3^(n+1) ", 5, 2));
		Assert.assertTrue(c.isCorrect("3^(n+1) * ℓ/2^n ", 5, 2));
		
		Assert.assertTrue(c.isCorrect("ℓ/(2^(n))*3^(n+1) ", 5, 2));
		
	}

	@Test
	public void should_be_wrong_formula(){
		Assert.assertFalse(c.isCorrect("ℓ/2^n+1", 5, 2));

		Assert.assertFalse(c.isCorrect("ℓ/1^n", 5, 2));
	}

	@Test
	public void should_test_correction_false(){
		Assert.assertFalse(c.isCorrect("3.1*ℓ", 0, 3));
		
		Assert.assertFalse(c.isCorrect("ℓ/(2^(n+1))*3^(n+1) ", 5, 2));
		Assert.assertFalse(c.isCorrect("ℓ/(1/2^(n+1))*3^(n+1) ", 5, 2));
	}
}
