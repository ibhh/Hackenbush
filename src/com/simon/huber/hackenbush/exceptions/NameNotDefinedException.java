package com.simon.huber.hackenbush.exceptions;

public class NameNotDefinedException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameNotDefinedException() {
		super("Es ist ein Name f�r den Spielstand n�tig!");
	}

}
