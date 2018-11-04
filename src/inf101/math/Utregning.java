package inf101.math;

import inf101.skogen.ITing;

public class Utregning {

	/**
	 * Kalkulerer avstanden mellom to midten av to objekter. Returnerer alltid
	 * et positivt tall (eller 0.0, hvis objektene er på samme sted).
	 * 
	 * @param a
	 *            Det ene objektet
	 * @param b
	 *            Det andre objektet
	 * @return Avstanden mellom midten av objektene
	 */
	public static double senterAvstand(ITing a, ITing b) {
		return Math.sqrt(Math.pow(a.hentX() - b.hentX(), 2)
				+ Math.pow(a.hentY() - b.hentY(), 2));
	}

	/**
	 * Kalkulerer avstanden fra midten av et objekt til et punkt.
	 * 
	 * @param a
	 *            Det ene objektet
	 * @param x
	 *            X-posisjon til punktet
	 * @param y
	 *            Y-posisjon til punktet
	 * @return Avstanden
	 */
	public static double senterAvstand(ITing a, double x, double y) {
		return Math.sqrt(Math.pow(a.hentX() - x, 2)
				+ Math.pow(a.hentY() - y, 2));
	}

	/**
	 * Vektoren mellom posisjonene til a og b
	 * 
	 * @param a
	 * @param b
	 * @return Vektor mellom midten av objektene
	 */
	public static Vektor avstandsVektor(ITing a, ITing b) {
		return new Vektor(b.hentX(), b.hentY()).sub(a.hentX(), a.hentY());
	}

	/**
	 * Vektoren mellom posisjonen til a og (x,y)
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @return Vektor fra midten av a til (x,y)
	 */
	public static Vektor avstandsVektor(ITing a, double x, double y) {
		return new Vektor(x, y).sub(a.hentX(), a.hentY());
	}

	/**
	 * Kalkulerer avstanden mellom ytterkantene av to objekter. Returnerer et
	 * negativt tall dersom de overlapper og 0.0 dersom de berører hverandre.
	 * 
	 * @param a
	 *            Det ene objektet
	 * @param b
	 *            Det andre objektet
	 * @return Avstanden mellom ytterkantene av objektene; negativt hvis
	 *         objektene overlapper
	 */
	public static double mellomAvstand(ITing a, ITing b) {
		return senterAvstand(a, b) - (a.hentStørrelse() + b.hentStørrelse());
	}

	public static double xRetningTil(ITing fra, ITing til) {
		double avs = senterAvstand(fra, til);
		if (avs > 0.0)
			return (til.hentX() - fra.hentX()) / avs;
		else
			return 0.0;
	}

	public static double yRetningTil(ITing fra, ITing til) {
		double avs = senterAvstand(fra, til);
		if (avs > 0.0)
			return (til.hentY() - fra.hentY()) / avs;
		else
			return 0.0;
	}

	public static double xRetningTil(ITing fra, double x, double y) {
		double avs = senterAvstand(fra, x, y);
		if (avs > 0.0)
			return (x - fra.hentX()) / avs;
		else
			return 0.0;
	}

	public static double yRetningTil(ITing fra, double x, double y) {
		double avs = senterAvstand(fra, x, y);
		if (avs > 0.0)
			return (y - fra.hentY()) / avs;
		else
			return 0.0;
	}

	/**
	 * En normalisert vektor (lengde 1) som uttrykker retningen fra et sted til
	 * et annet
	 * 
	 * @param fra
	 * @param til
	 * @return
	 */
	public static Vektor retningTil(ITing fra, ITing til) {
		return avstandsVektor(fra, til).normaliser();
	}

	/**
	 * En normalisert vektor (lengde 1) som uttrykker retningen fra et sted til
	 * et annet
	 * 
	 * @param fra
	 * @param x
	 * @param y
	 * @return
	 */
	public static Vektor retningTil(ITing fra, double x, double y) {
		return avstandsVektor(fra, x, y).normaliser();
	}
}
