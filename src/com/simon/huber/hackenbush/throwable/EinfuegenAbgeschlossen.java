package com.simon.huber.hackenbush.throwable;

/**
 * R�ckgabe Nachricht f�r das erfolgreiche Erstellen einer Kante
 * @author Simon Huber
 *
 */
public class EinfuegenAbgeschlossen extends Throwable {

    private static final long serialVersionUID = 1L;

    public EinfuegenAbgeschlossen() {
	super("Einf�gen der Kante ist abgeschlossen");
    }
}
