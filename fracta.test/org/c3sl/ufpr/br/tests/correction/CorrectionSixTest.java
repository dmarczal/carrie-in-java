package org.c3sl.ufpr.br.tests.correction;

import static org.easymock.EasyMock.createMockBuilder;
import junit.framework.Assert;

import org.c3sl.ufpr.br.correction.Correction;
import org.c3sl.ufpr.br.correction.CorrectionSix;
import org.junit.Test;

public class CorrectionSixTest {
	private static Correction c;

	@org.junit.BeforeClass
	public static void setup(){
		c =  createMockBuilder(CorrectionSix.class).
		addMockedMethods("saveHit", "saveState").createMock();
	}

	@Test
	public void should_test_correction_true(){
		Assert.assertTrue(c.isCorrect("ℓ^(2) * sqrt(3)/4" , 0, 3));
		Assert.assertTrue(c.isCorrect("1" , 0, 4));
		Assert.assertTrue(c.isCorrect("ℓ^(2) * sqrt(3)/4" , 0, 5));
		
		Assert.assertTrue(c.isCorrect("(ℓ/3)^(2) * sqrt(3)/4" , 1, 3));
		Assert.assertTrue(c.isCorrect("3" , 1, 4));
		Assert.assertTrue(c.isCorrect("(ℓ/3)^(2) * sqrt(3)/4 * 3" , 1, 5));
		
		Assert.assertTrue(c.isCorrect("(ℓ/9)^(2) * sqrt(3)/4" , 2, 3));
		Assert.assertTrue(c.isCorrect("12" , 2, 4));
		Assert.assertTrue(c.isCorrect("(ℓ/9)^(2) * sqrt(3)/4 * 12" , 2, 5));
		
		Assert.assertTrue(c.isCorrect("(ℓ/27)^(2) * sqrt(3)/4" , 3, 3));
		Assert.assertTrue(c.isCorrect("48" , 3, 4));
		Assert.assertTrue(c.isCorrect("(ℓ/27)^(2) * sqrt(3)/4 * 48" , 3, 5));
		
		Assert.assertTrue(c.isCorrect("(ℓ/81)^(2) * sqrt(3)/4" , 4, 3));
		Assert.assertTrue(c.isCorrect("4^(3)*3" , 4, 4));
		Assert.assertTrue(c.isCorrect("(ℓ/81)^(2) * sqrt(3)/4 * 192" , 4, 5));
		
	}

	@Test
	public void should_be_formula_correct(){
		Assert.assertTrue(c.isCorrect("(ℓ/3^(n))^(2) * sqrt(3)/4" , 5, 3));
		Assert.assertTrue(c.isCorrect("4^(n-1)*3", 5, 4));
		Assert.assertTrue(c.isCorrect("(ℓ/3^(n))^(2) * sqrt(3)/4 * 4^(n-1)*3" , 5, 5));
	}

	@Test
	public void should_be_wrong_formula(){
		Assert.assertFalse(c.isCorrect("(ℓ/3^(n))^(1) * sqrt(3)/4" , 5, 3));
		Assert.assertFalse(c.isCorrect("4^(n-1)", 5, 4));
		Assert.assertFalse(c.isCorrect("(ℓ/3^(n))^(2) * sqrt(3)/4 * 4^(n)*3" , 5, 5));
	}
}
