package br.ufpr.c3sl.keyboard;

import javax.swing.JPanel;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

public class KeyBoardMainTest {

	private DialogFixture window;
	
	@Before
	public void setUp(){
		 VirtualKeyBoardMain frame = GuiActionRunner.execute(new GuiQuery<VirtualKeyBoardMain>() {
		      protected VirtualKeyBoardMain executeInEDT() {
		        return new VirtualKeyBoardMain(new JPanel(), true, true, true, true);  
		      }
		  });
		 
		  window = new DialogFixture(frame);
		  window.show();	
	}
	
	
	@Test // (pi/18) * t
	public void shouldNotShowErrorMessageWhenUseTheSequenceOfComands(){
		window.button("4").click();
		window.button("÷").click();
		window.button("π").click();
		window.button("Return").click();
		window.button("x").click();
		window.button("t").click();
		window.label("jlError").requireText("");
	}
	
	@Test
	public void shouldShowErrorMessageWhenUseTheSequenceOfComands(){
		window.button("4").click();
		window.button("÷").click();
		window.button("π").click();
		window.button("Return").click();
		window.button("x").click();
		window.button("8").click();
		window.button("t").click();
		window.label("jlError").requireText("Operação Inválida");
	}
	
	@Test // 3^n + 3^n
	public void shouldNotShowErrorMessageWhenUseTheSequenceOfComandsPow(){
		window.button("3").click();
		window.button("^").click();
		window.button("n").click();
		window.button("Return").click();
		window.button("+").click();
		window.button("3").click();
		window.button("^").click();
		window.button("n").click();
		window.label("jlError").requireText("");
	}
	
	@After
	public void tearDown(){
		window.cleanUp();
	}
}
