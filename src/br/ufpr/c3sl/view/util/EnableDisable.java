package br.ufpr.c3sl.view.util;

import java.awt.Component;

public class EnableDisable {

	public static void setComponentsEnabled(java.awt.Container c, boolean en) {
	    Component[] components = c.getComponents();
	    for (Component comp: components) {
	        if (comp instanceof java.awt.Container)
	            setComponentsEnabled((java.awt.Container) comp, en);
	        comp.setEnabled(en);
	    }
	}

}
