package br.ufpr.c3sl.virtualkeyboard.listeners;

import javax.swing.JPanel;

import br.ufpr.c3sl.virtualkeyboard.events.VirtualKeyBoardEvent;

public interface VirtualKeyBoardListener {
	
	void formulaSended(VirtualKeyBoardEvent<JPanel> formula); 
}
