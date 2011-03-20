package org.c3sl.ufpr.br.tests.correction;

import static org.easymock.EasyMock.createMockBuilder;
import junit.framework.Assert;

import org.c3sl.ufpr.br.correction.Correction;
import org.c3sl.ufpr.br.correction.CorrectionTwo;
import org.junit.Test;

public class CorrectionTwoTest {

	private static Correction c;

	@org.junit.BeforeClass
	public static void setup(){
		c =  createMockBuilder(CorrectionTwo.class).
		addMockedMethods("saveHit", "saveState").createMock();
	}

	@Test
	public void should_test_correction_true(){
		Assert.assertTrue(c.isCorrect("ℓ/2" , 1, 2));
		Assert.assertTrue(c.isCorrect("ℓ/1.999999" , 1, 2));
		Assert.assertTrue(c.isCorrect("ℓ/2.0000001", 1, 2));

		Assert.assertTrue(c.isCorrect("ℓ/4" , 2, 2));
		Assert.assertTrue(c.isCorrect("ℓ/3.999999" , 2, 2));

		Assert.assertTrue(c.isCorrect("ℓ/8" , 3, 2));
		Assert.assertTrue(c.isCorrect("ℓ/7.999999" , 3, 2));

		Assert.assertTrue(c.isCorrect("ℓ/16" , 4, 2));
		Assert.assertTrue(c.isCorrect("ℓ/15.999999" , 4, 2));
	}

	@Test
	public void should_be_formula_correct(){
		Assert.assertTrue(c.isCorrect("ℓ/2^n", 5, 2));
	}

	@Test
	public void should_be_wrong_formula(){
		Assert.assertFalse(c.isCorrect("ℓ/2^n+1", 5, 2));

		Assert.assertFalse(c.isCorrect("ℓ/1^n", 5, 2));
	}

	@Test
	public void should_test_correction_false(){
		Assert.assertFalse(c.isCorrect("ℓ/1.9", 1, 2));
		Assert.assertFalse(c.isCorrect("ℓ/2.1", 1, 2));

		Assert.assertFalse(c.isCorrect("ℓ/3.9", 2, 2));
		Assert.assertFalse(c.isCorrect("ℓ/4.1", 2, 2));

		Assert.assertFalse(c.isCorrect("ℓ/7.9", 2, 2));
		Assert.assertFalse(c.isCorrect("ℓ/8.1", 2, 2));

		Assert.assertFalse(c.isCorrect("ℓ/16.9", 2, 2));
		Assert.assertFalse(c.isCorrect("ℓ/15.1", 2, 2));
	}
}
