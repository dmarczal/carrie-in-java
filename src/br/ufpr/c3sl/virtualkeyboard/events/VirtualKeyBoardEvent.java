package br.ufpr.c3sl.virtualkeyboard.events;

import java.util.EventObject;

public class VirtualKeyBoardEvent<T> extends EventObject{
	private static final long serialVersionUID = 1L;

	public VirtualKeyBoardEvent(Object source) {
		super(source);
	}

	@SuppressWarnings("unchecked")
	public T getSource(){
		return ((T) super.getSource());
	}
}
