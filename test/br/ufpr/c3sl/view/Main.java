package br.ufpr.c3sl.view;

import br.ufpr.c3sl.connection.Internet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("--> " + Internet.isReachable());
	}

}
