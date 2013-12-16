package com.simon.huber.hackenbush.dataobjects;

public class VerbindungsPunkt extends Punkt {

    private static final long serialVersionUID = 1L;

    public VerbindungsPunkt(int x, int y) {
	super(x, y);
    }

    public VerbindungsPunkt(VerbindungsPunkt p) {
	super(p.x, p.y);
    }
    
    /**
     * Wird �berschrieben, da Verbindungspunkte nicht die Grundfl�che ber�hren k�nnen
     */
    @Override
    public boolean istAmBoden() {
        return false;
    }

}
