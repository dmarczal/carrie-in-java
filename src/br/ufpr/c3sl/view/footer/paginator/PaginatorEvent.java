package br.ufpr.c3sl.view.footer.paginator;

import java.util.EventObject;

public class PaginatorEvent<T> extends EventObject{
	
	private static final long serialVersionUID = 120862905532199024L;

	public PaginatorEvent(final Object source) {
		super(source);
	}
	
	@SuppressWarnings("unchecked")
	public T getSource(){
		return ((T) super.getSource());
	}
	
}