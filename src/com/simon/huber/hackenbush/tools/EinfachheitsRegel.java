/**
 * 
 */
package com.simon.huber.hackenbush.tools;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.simon.huber.hackenbush.dataobjects.Bruch;
import com.simon.huber.hackenbush.exceptions.EinfachheitsRegelException;

/**
 * @author Simon Huber
 */
public class EinfachheitsRegel {

    /**
     * Findet den einfachsten Bruch, der zwischen zwei Br�chen liegt. (Nach der
     * Einfachheitsregel)
     * 
     * @param bruch1
     *            Erster Bruch
     * @param bruch2
     *            Zweiter Bruch
     * @return den einfachsten Bruch zwischen bruch1 und bruch2
     * @throws EinfachheitsRegelException
     *             Wird geworfen, wenn beide Br�che den gleichen Wert haben und
     *             somit keine Berechnung m�glich ist
     */
    public static Bruch einfachsterBruchZwischen(Bruch bruch1, Bruch bruch2) throws ArithmeticException, EinfachheitsRegelException {

	// Lokale Kopie der Br�che, damit die �bergebenen Br�che, auf die noch
	// andere Variablen verwei�en, w�hrend der Berechnung nicht ungewollte
	// Werte annehmen
	Bruch b1 = new Bruch(bruch1);
	Bruch b2 = new Bruch(bruch2);

	boolean negativ = false;
	// Initialisierung
	Bruch einfachsterBruch = new Bruch(BigInteger.valueOf(0), BigInteger.valueOf(1));

	int temp = b1.vergleiche(b2);
	if (temp == 0) {
	    throw new EinfachheitsRegelException("Beide Br�che haben den gleichen Wert");
	} else if (temp == -1) {
	    Bruch temp_bruch = b1;
	    b1 = b2;
	    b2 = temp_bruch;
	}

	// Wenn einer der Br�che negativ ist, werden sie vertauscht und mal
	// -1 genommen, um den Rechenvorgang zu vereinfachen
	if (b2.getZaehler().compareTo(BigInteger.ZERO) == -1 || b1.getZaehler().compareTo(BigInteger.ZERO) == -1) {
	    Bruch temp_bruch = b1;
	    b1 = b2;
	    b2 = temp_bruch;
	    negativ = true;
	    b1.setZaehler(b1.getZaehler().negate());
	    b2.setZaehler(b2.getZaehler().negate());
	}

	// Bring die Br�che auf den kleinsten gemeinsamen Nenner
	// (Hauptnenner) und gibt zugleich diesen Hauptnenner zur�ck
	BigInteger hauptnenner = bringeAufHauptnenner(b1, b2);

	// Spiezialfall Wert = 0: Hier steht schon fest, dass der einfachste
	// Wert zwischen den Br�chen 0 ist
	if (hauptnenner.equals(BigInteger.ZERO)) {
	    return new Bruch(BigInteger.valueOf(0), BigInteger.valueOf(1));
	}

	if (b1.getZaehler().add(BigInteger.valueOf(1)).equals(b2.getZaehler())) {
	    // Bruch2 ist im Z�hler genau eins gr��er als Bruch1

	    // Deshalb ist der gesuchte Bruch die Summe der Z�hler / den
	    // Hauptnenner / 2
	    // --> Beispiel: einfachster Wert zwischen 1/2 und 2/2
	    // == (1 + 2) / (2 * 2) = 3 / 4 (Richtig)
	    einfachsterBruch.setZaehler(b1.getZaehler().add(b2.getZaehler()));
	    einfachsterBruch.setNenner(hauptnenner.multiply(BigInteger.valueOf(2)));
	    if (negativ) {
		einfachsterBruch.setZaehler(einfachsterBruch.getZaehler().negate());
	    }
	    return einfachsterBruch;

	}

	try {
	    BigInteger next = BigInteger.ZERO;
	    while (true) {
		// N�chster Wert des Z�hlers der gepr�ft werden soll
		next = b1.getZaehler().add(hauptnenner).divide(hauptnenner).multiply(hauptnenner);

		if (next.compareTo(b2.getZaehler()) == -1) {
		    einfachsterBruch.setZaehler(next);
		    einfachsterBruch.setNenner(b1.getNenner());

		    // K�rzen des Bruches
		    einfachsterBruch.kuerzen();
		    if (negativ) {
			einfachsterBruch.setZaehler(einfachsterBruch.getZaehler().negate());
		    }
		    return einfachsterBruch;
		}

		// Halbierung des Wertes des Hauptnenners (Durch 2, da der
		// Nenner jedes Hackenbush-Wertes ein vielfaches von 2 sein muss
		hauptnenner = hauptnenner.divide(BigInteger.valueOf(2));
	    }
	} catch (ArithmeticException e) {
	    throw e;
	}
    }



    /**
     * Ver�ndert zwei Br�che so, dass sie den kleinstm�glichen gemeinsamen
     * Nenner besitzen
     * 
     * @param bruch1
     *            erster Bruch
     * @param bruch2
     *            zweiter Bruch
     * @return Hauptnenner
     */
    private static BigInteger bringeAufHauptnenner(Bruch bruch1, Bruch bruch2) {
	if (bruch1.getZaehler().compareTo(BigInteger.ZERO) == -1 && bruch2.getZaehler().compareTo(BigInteger.ZERO) == 1) {
	    return BigInteger.ZERO;
	}
	BigDecimal faktor;
	BigDecimal b1 = new BigDecimal(bruch1.getNenner());
	BigDecimal b2 = new BigDecimal(bruch2.getNenner());
	if (bruch1.getNenner().compareTo(bruch2.getNenner()) == -1) {
	    faktor = b2.divide(b1);
	    bruch1.setZaehler(new BigDecimal(bruch1.getZaehler()).multiply(faktor).toBigInteger());
	    bruch1.setNenner(new BigDecimal(bruch1.getNenner()).multiply(faktor).toBigInteger());
	} else {
	    faktor = b1.divide(b2);
	    bruch2.setZaehler(new BigDecimal(bruch2.getZaehler()).multiply(faktor).toBigInteger());
	    bruch2.setNenner(new BigDecimal(bruch2.getZaehler()).multiply(faktor).toBigInteger());
	}
	return bruch1.getNenner();
    }

}
