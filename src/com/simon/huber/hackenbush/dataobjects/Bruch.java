/**
 * 
 */
package com.simon.huber.hackenbush.dataobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Simon Huber
 */
public class Bruch implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger zaehler;
    private BigInteger nenner;

    public Bruch(BigInteger zaehler2, BigInteger nenner2) {
	this.zaehler = new BigInteger(zaehler2.toByteArray());
	this.nenner = new BigInteger(nenner2.toByteArray());
    }

    public Bruch(Bruch b) {
	zaehler = b.zaehler;
	nenner = b.nenner;
    }

    public BigInteger getNenner() {
	return nenner;
    }

    public void setNenner(BigInteger nenner) {
	this.nenner = nenner;
    }

    public void setZaehler(BigInteger zaehler) {
	this.zaehler = zaehler;
    }

    public BigInteger getZaehler() {
	return zaehler;
    }

    public String getAnzeige() {
	return zaehler.toString() + "/" + nenner.toString();
    }

    /**
     * Gibt f�r einen kleineren Bruch -1, f�r einen gleich gro�en 0 und f�r
     * einen gr��eren 1 zur�ck
     * 
     * @param bruch
     * @return Gibt f�r einen kleineren Bruch -1, f�r einen gleich gro�en 0 und
     *         f�r einen gr��eren 1 zur�ck
     */
    public int vergleiche(Bruch bruch) {
	return vergleiche(bruch.getZaehler(), bruch.getNenner());
    }

    /**
     * Gibt f�r einen kleineren Bruch -1, f�r einen gleich gro�en Bruch 0 und
     * f�r einen gr��eren Bruch 1 zur�ck
     * 
     * @param zaehler2 Z�hler des zu vergleichenden Bruches
     * @param nenner2 Nenner des zu vergleichenden Bruches
     * @return Gibt f�r einen kleineren Bruch -1, f�r einen gleich gro�en Bruch
     *         0 und f�r einen gr��eren Bruch 1 zur�ck
     */
    public int vergleiche(BigInteger zaehler2, BigInteger nenner2) {
	if (zaehler.equals(zaehler2) && nenner.equals(nenner2)) {
	    // Gleich gro�e Br�che
	    return 0;
	} else if (zaehler.equals(BigInteger.ZERO) && zaehler2.equals(BigInteger.ZERO)) {
	    // Gleich gro�e Br�che
	    return 0;
	} else if (zaehler.equals(BigInteger.ZERO) && zaehler2.compareTo(BigInteger.ZERO) == 1) {
	    // Bruch 1 == 0 und Bruch 2 > 0, deshalb ist Bruch 2 gr��er
	    return 1;
	} else if (zaehler.equals(BigInteger.ZERO) && zaehler2.compareTo(BigInteger.ZERO) == -1) {
	    // Bruch 1 == 0 und Bruch 2 < 0, deshalb ist Bruch 1 gr��er
	    return -1;
	} else if (zaehler.compareTo(BigInteger.ZERO) == 1 && zaehler2.equals(BigInteger.ZERO)) {
	    // Bruch 1 gr��er 0 und Bruch 2 == 0, deshalb ist Bruch 1 gr��er
	    return -1;
	} else if (zaehler.compareTo(BigInteger.ZERO) == -1 && zaehler2.equals(BigInteger.ZERO)) {
	    // Bruch 1 < 0 und Bruch 2 == 0, deshalb ist Bruch 2 gr��er
	    return 1;
	}

	BigDecimal temp_1 = new BigDecimal(zaehler2);
	temp_1 = temp_1.divide(new BigDecimal(nenner2));

	BigDecimal temp_2 = new BigDecimal(zaehler);
	temp_2 = temp_2.divide(new BigDecimal(nenner));

	int temp = temp_2.compareTo(temp_1);

	if (temp > 0) {
	    return -1;
	} else if (temp == 0) {
	    return 0;
	} else {
	    return 1;
	}
    }

    /**
     * Quelle: http://answers.yahoo.com/question/index?qid=20101104212230AAqKxYa
     */
    public void kuerzen() {
	BigInteger n = zaehler;
	BigInteger d = nenner;

	while (!d.equals(BigInteger.ZERO)) {
	    BigInteger t = d;
	    d = n.mod(d);
	    n = t;
	}

	BigInteger gcd = n;

	zaehler = zaehler.divide(gcd);
	nenner = nenner.divide(gcd);
    }
}
