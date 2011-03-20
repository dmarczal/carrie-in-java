package org.c3sl.ufpr.br.tests.correction;

import static org.easymock.EasyMock.createMockBuilder;
import junit.framework.Assert;

import org.c3sl.ufpr.br.correction.Correction;
import org.c3sl.ufpr.br.correction.CorrectionFour;
import org.junit.Test;

public class CorrectionFourTest {

	private static Correction c;

	@org.junit.BeforeClass
	public static void setup(){
		c =  createMockBuilder(CorrectionFour.class).
		addMockedMethods("saveHit", "saveState").createMock();
	}

	@Test
	public void should_test_correction_true(){
		Assert.assertTrue(c.isCorrect("ℓ^(2)*sqrt(3)/4" , 0, 3));
		Assert.assertTrue(c.isCorrect("1" , 0, 4));
		Assert.assertTrue(c.isCorrect("ℓ^(2)*sqrt(3)/4" , 0, 5));

		Assert.assertTrue(c.isCorrect("(ℓ/2)^(2)*sqrt(3)/4" , 1, 3));
		Assert.assertTrue(c.isCorrect("3" , 1, 4));

		Assert.assertTrue(c.isCorrect("(ℓ/2)^(2)*sqrt(3)/4 * 3" , 1, 5));
		Assert.assertTrue(c.isCorrect("sqrt(3)/4 * 3 * (ℓ/2)^(2)" , 1, 5));
		Assert.assertTrue(c.isCorrect("3 * sqrt(3)/4 * (ℓ/2)^(2)" , 1, 5));

		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/4)^(2)" , 2, 3));
		Assert.assertTrue(c.isCorrect("9" , 2, 4));
		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/4)^(2) * 9" , 2, 5));

		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/8)^(2)" , 3, 3));
		Assert.assertTrue(c.isCorrect("27" , 3, 4));
		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/8)^(2) * 27" , 3, 5));


		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/16)^(2)" , 4, 3));
		Assert.assertTrue(c.isCorrect("81" , 4, 4));
		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/16)^(2) * 81" , 4, 5));	

	}

	@Test
	public void should_be_formula_correct(){

		Assert.assertTrue(c.isCorrect("sqrt(3)/4*(ℓ/2^(n))^(2)" , 5, 3));
		Assert.assertTrue(c.isCorrect("(ℓ/2^(n))^(2)*sqrt(3)/4" , 5, 3));

		Assert.assertTrue(c.isCorrect("3^n", 5, 4));


		Assert.assertTrue(c.isCorrect("sqrt(3)/4 * (ℓ/2^(n))^(2) * 3^(n)" , 5, 5));
		Assert.assertTrue(c.isCorrect("(ℓ/2^(n))^(2) * 3^(n) * sqrt(3)/4" , 5, 5));

	}

	@Test
	public void should_be_wrong_formula(){
		Assert.assertFalse(c.isCorrect("sqrt(3)/4*(ℓ/2)^(2)" , 5, 3));
		Assert.assertFalse(c.isCorrect("3^(n-1)", 5, 4));
		Assert.assertFalse(c.isCorrect("sqrt(3)/4 * (ℓ/2^(n)) * 3^(n)" , 5, 5));
	}
}
