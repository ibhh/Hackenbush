package com.simon.huber.hackenbush.calculation;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.simon.huber.hackenbush.dataobjects.Bruch;
import com.simon.huber.hackenbush.dataobjects.HackenbushFigur;
import com.simon.huber.hackenbush.dataobjects.Kante;
import com.simon.huber.hackenbush.dataobjects.Punkt;
import com.simon.huber.hackenbush.dataobjects.hilfsobjekte.HilfsKante;
import com.simon.huber.hackenbush.dataobjects.hilfsobjekte.HilfsVerbindung;
import com.simon.huber.hackenbush.gui.FortschrittsPanel;

/**
 * Berechnet Kanten durch Multithreading
 * @author Simon Huber
 */
public class HackenbushArbeitsThread extends Thread {

    private HackenbushFigur figur;
    private FortschrittsPanel frame;

    /**
     * Kante, die einen Wert bekommen soll
     */
    private Kante kante;

    /**
     * Bereits berechnete Hackenbush-Figuren
     */
    private ArrayList<HackenbushFigur> options;

    public HackenbushArbeitsThread(Kante k, HackenbushFigur figur, FortschrittsPanel panel, ArrayList<HackenbushFigur> options) {
	this.figur = figur;
	frame = panel;
	kante = k;
	this.options = options;
    }

    @Override
    public void run() {
	Bruch wert = berechneFigur(figur);
	// Zugriff wird reserviert (threadsafe)
	synchronized (kante) {
	    kante.setWert(wert);
	}
	frame.addFortschritt();
    }

    private Bruch berechneFigur(HackenbushFigur figur) {
	try {
	    // // Suche aus bereits berechneten Figuren Werte
	    // for (HackenbushFigur f : options) {
	    // if (figur.equals(f)) {
	    // return f.getSpielWert();
	    // }
	    // }
	    ArrayList<HilfsVerbindung> pkombi = new ArrayList<>();
	    for (Punkt punkt : figur.getPunkte()) {
		for (Punkt punkt2 : figur.getPunkte()) {
		    HilfsVerbindung ver = null;
		    if ((ver = figur.getVerbindung(punkt, punkt2)) != null && ver.getVerbindung().aktiveKanten()) {
			if (pkombi.contains(ver)) {
			    continue;
			} else {
			    pkombi.add(ver);
			}
			if (!handleVerbindung(figur, ver)) {
			    return null;
			}
		    }
		}
	    }
	    // HackenbushFenster.getLogger().log(Level.INFO,
	    // "Rekursion abgeschlossen!");
	    if (!options.contains(figur)) {
		synchronized (options) {
		    options.add(figur);
		}
	    }

	    return figur.getSpielWert();
	} catch (Exception e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage(),
			"Fehler bei Berechnung", JOptionPane.ERROR_MESSAGE);
	    frame.abbruch();
	    return null;
	}
    }

    /**
     * Startet eine Berechnung f�r eine Verbindung
     * 
     * @param figur die Hackenbush-Figur
     * @param ver Hilfsverbindung, deren Wert berechnet werden soll.
     * @return true, wenn Berechnung erfolgreich war
     * @throws Exception
     */
    private boolean handleVerbindung(HackenbushFigur figur, HilfsVerbindung ver) throws Exception {
	for (Kante k : ver.getVerbindung().getAktiveKanten()) {

	    // Abruch durch Button (f�hrt zu Fehlern in der Berechnung, sie
	    // sollte unbeding neu gestartet werden!!!!)
	    if (frame.isAbbruch()) {
		return false;
	    }

	    HilfsKante kante = new HilfsKante(ver.getPunkt()[0], ver.getPunkt()[1], k);

	    HackenbushFigur neueFigur = new HackenbushFigur(figur);
	    neueFigur.loescheKante(kante);

	    Bruch besondereWerte = neueFigur.checkBesondereSpielWerte();
	    if (besondereWerte != null) {
		k.setWert(besondereWerte);
		// HackenbushFenster.getLogger().log(Level.INFO,
		// "Besonderer Spielwert Kantenberechnung k: "
		// + punkt.x + "/" + punkt2.y + " - " +
		// +punkt2.x + "/" + punkt.y +
		// ", Kantenwert: " +
		// k.getWert().getAnzeige());
		frame.addKantenBerechnung();
		continue;
	    }

	    boolean gefunden = false;
	    synchronized (options) {
		// Suche aus bereits berechneten Figuren Werte
		for (HackenbushFigur f : options) {
		    if (neueFigur.equals(f)) {
			k.setWert(f.getSpielWert());
			gefunden = true;
			break;
		    }
		}
	    }

	    // Nicht gefunden, deshalb wird sie berechnet
	    if (!gefunden || (gefunden && k.getWert() == null)) {
		k.setWert(berechneFigur(neueFigur));
	    }
	    frame.addKantenBerechnung();
	    // HackenbushFenster.getLogger().log(Level.INFO,
	    // "Kantenberechnung k: " + punkt.x + "/" +
	    // punkt2.y + " - " + +punkt2.x + "/" + punkt.y
	    // + ", Kantenwert: " +
	    // k.getWert().getAnzeige());
	}
	return true;
    }

}
