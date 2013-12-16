package com.simon.huber.hackenbush.dataobjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse, die die Verwaltung f�r Kanten �bernimmt, die die gleichen Punkte verbindet
 * @author Simon Huber
 *
 */
public class Verbindung extends ArrayList<Kante> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Verbindung(Verbindung ver) {
	super();
	if (ver == null) {
	    return;
	}
	clear();
	for (Kante eintrag : ver) {
	    add(new Kante(eintrag));
	}
    }

    public Verbindung() {
	super();
    }

    /**
     * Gibt die Referenz einer gespeicherten Kante zur�ck, die der �bergebenen entspricht
     * @param kante
     * @return Referenz auf die Kante
     */
    public Kante getKante(Kante kante) {
	for (Kante eintrag : this) {
	    if (kante.equals(eintrag)) {
		return eintrag;
	    }
	}
	return null;
    }

    /**
     * Pr�ft ob diese Verbindung noch nicht entfernte Kanten hat
     * 
     * @return true, oder false
     */
    public boolean aktiveKanten() {
	for (Kante eintrag : this) {
	    if (!eintrag.isEntfernt()) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Gibt alle aktiven Kanten zur�ck
     * 
     * @return Arraylist mit allen aktiven Kanten
     */
    public ArrayList<Kante> getAktiveKanten() {
	ArrayList<Kante> returnvalue = new ArrayList<>();
	for (Kante eintrag : this) {
	    if (!eintrag.isEntfernt()) {
		returnvalue.add(eintrag);
	    }
	}
	return returnvalue;
    }

    /**
     * Gibt die beste Kante von einem Spieler zur�ck
     * 
     * @param spieler
     * @return Kante mit dem besten Wert f�r den Spieler; wenn keine Kanten von
     *         diesem Spieler vorhanden sind: null
     */
    public Kante getBesteKante(int spieler) {
	Kante beste = null;
	for (Kante eintrag : this) {
	    if (eintrag.getSpieler() != spieler) {
		continue;
	    }
	    if (beste == null && !eintrag.isEntfernt()) {
		beste = eintrag;
		continue;
	    }
	    if (!eintrag.isEntfernt() && beste.getWert().vergleiche(eintrag.getWert()) == (spieler == 1 ? 1 : -1)) {
		beste = eintrag;
	    }
	}
	return beste;
    }

    /**
     * �berpr�ft die Verbindung, ob nur noch Kanten eines Spielers vorhanden
     * sind
     * 
     * @return 0 wenn beide Spieler Kanten haben, 1 wenn nur noch Spieler 1
     *         Kanten hat, 2 wenn nur noch Spieler 2 Kanten hat
     */
    public int beinhaltenNurNochKantenVon() {
	boolean spieler1 = false;
	boolean spieler2 = false;

	for (Kante eintrag : this) {
	    if (!eintrag.isEntfernt()) {
		if (eintrag.getSpieler() == 1 && spieler1 == false) {
		    spieler1 = true;
		}
		if (eintrag.getSpieler() == 2 && spieler2 == false) {
		    spieler2 = true;
		}
	    }
	}
	return (spieler1 == spieler2) ? 0 : (spieler1 ? 1 : 2);
    }

    /**
     * Z�hlt Anzahl der noch nicht entfernten Kanten eines Spielers
     * 
     * @return Array mit Anzahl Spieler 1 / Spieler 2
     */
    public int[] getKantenAnzahl() {
	int[] anzahl = new int[2];
	for (Kante eintrag : this) {
	    if (!eintrag.isEntfernt()) {
		if (eintrag.getSpieler() == 1) {
		    anzahl[0]++;
		}
		if (eintrag.getSpieler() == 2) {
		    anzahl[1]++;
		}
	    }
	}
	return anzahl;
    }

    /**
     * Setzt das Entfernt Attribut einer Kante auf True
     * @param kante
     */
    public void setEntfernt(Kante kante) {
	for (Kante eintrag : this) {
	    if (!eintrag.isEntfernt()) {
		if (eintrag.getSpieler() == kante.getSpieler()) {
		    if (eintrag.getVerbindungspunkt() == null || eintrag.getVerbindungspunkt().equals(kante.getVerbindungspunkt())) {
			eintrag.setEntfernt(true);
		    }
		}
	    }
	}
    }

    /**
     * Entfernt eine Kante von der Liste der Verbindung
     * @param kante
     */
    public void loesche(Kante kante) {
	if(kante != null) {
	    remove(kante);
	}
    }

}
