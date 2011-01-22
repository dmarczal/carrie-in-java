package br.ufpr.c3sl.view;
import javax.swing.JApplet;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.junit.Test;


public class TestJAppletCarrie {
	
	private static AppletViewer viewer;
	private static FrameFixture applet;
	private static JApplet jAppletCarrie;
	
	@org.junit.BeforeClass
	public static void setup(){
		jAppletCarrie = new AppletTest();		  
		viewer = AppletLauncher.applet(jAppletCarrie).start();
		applet = new FrameFixture(viewer);
		applet.show();
	}

	@Test
	public void should_load(){
		
	}
}
