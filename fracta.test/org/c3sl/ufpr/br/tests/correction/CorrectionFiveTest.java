package org.c3sl.ufpr.br.tests.correction;

import static org.easymock.EasyMock.createMockBuilder;
import junit.framework.Assert;

import org.c3sl.ufpr.br.correction.Correction;
import org.c3sl.ufpr.br.correction.CorrectionFive;
import org.junit.Test;

public class CorrectionFiveTest {

	private static Correction c;

	@org.junit.BeforeClass
	public static void setup(){
		c =  createMockBuilder(CorrectionFive.class).
		addMockedMethods("saveHit", "saveState").createMock();
	}

	@Test
	public void should_test_correction_true(){
		Assert.assertTrue(c.isCorrect("3*ℓ" , 0, 3));
		
		Assert.assertTrue(c.isCorrect("ℓ/3", 1, 2));
		Assert.assertTrue(c.isCorrect("12*ℓ/3", 1, 3));
		
		Assert.assertTrue(c.isCorrect("ℓ/9", 2, 2));
		Assert.assertTrue(c.isCorrect("48*ℓ/9", 2, 3));

		Assert.assertTrue(c.isCorrect("ℓ/27", 3, 2));
		
		Assert.assertTrue(c.isCorrect("192*ℓ/27", 3, 3));
		Assert.assertTrue(c.isCorrect("ℓ/27*192", 3, 3));
		
		Assert.assertTrue(c.isCorrect("ℓ/81*4^(4)*3", 4, 3));
		Assert.assertTrue(c.isCorrect("ℓ/81*768", 4, 3));	
	}

	@Test
	public void should_be_formula_correct(){
		Assert.assertTrue(c.isCorrect("ℓ/3^(n)", 5, 2));
		Assert.assertTrue(c.isCorrect("(ℓ/3^(n)) * 4^(n) * 3" , 5, 3));
	}

	@Test
	public void should_be_wrong_formula(){
		Assert.assertFalse(c.isCorrect("ℓ/3^(n-1)", 5, 2));
		Assert.assertFalse(c.isCorrect("(ℓ/3^(n)) * 4^(n)" , 5, 3));	
	}
}
