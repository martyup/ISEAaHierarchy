package inf101.okosystem;

import java.util.Collection;

import inf101.math.Utregning;
import inf101.math.Vektor;
import inf101.skogen.IPassiv;
import inf101.skogen.ISimulator;
import inf101.skogen.ITing;
import inf101.skogen.Simulator;

public class Plankton implements IPassiv {
	String navn;
	double posisjonX;
	double posisjonY;
	int vekt;
	int normalVekt;
	int størrelse;
	int næringsVerdi;
	char id;
	String info;
	String bilde;
	String type;
	private Vektor retning = new Vektor(1, 1);
	private boolean erLevende = true;

	public Plankton(double x, double y) {
		navn = "Plankton";
		posisjonX = x;
		posisjonY = y;
		vekt = 2;
		normalVekt = 2;
		størrelse = 1;
		næringsVerdi = 1;
		id = 'p';
		info = "Vitenskapeqlige navnet er : Salmo salar";
		bilde = "plankton.png";
		type = "mikro";
	}

	@Override
	public ITing lagKopi() {
		return new Plankton(this.hentX(), this.hentY());
	}

	@Override
	public ITing lagNy(int x, int y) {
		return new Plankton(x, y);
	}

	@Override
	public void bliSpist(ITing spiser, double mengde) {
		erLevende = false;
	}

	@Override
	public boolean datainvariant() {
		if (posisjonX < 0 || posisjonX >= Simulator.bredde && posisjonY < 0
				|| posisjonY >= Simulator.høyde) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean erIBruk() {
		return erLevende;
	}

	@Override
	public boolean erLevende() {
		return erLevende;
	}

	@Override
	public boolean erKollidertMed(ITing t) {
		return (Utregning.mellomAvstand(this, t) <= 0.0);
	}

	@Override
	public String hentBildeNavn(int stegNummer) {
		return bilde;
	}

	@Override
	public char hentId() {
		return id;
	}

	@Override
	public String hentIkonNavn() {
		return bilde;
	}

	@Override
	public String hentInfo() {
		return info + " av type " + type + " i økosystemet.";
	}

	@Override
	public double hentNæringsVerdi() {
		return næringsVerdi;
	}

	@Override
	public String hentNavn() {
		return navn;
	}

	@Override
	public double hentStørrelse() {
		return størrelse;
	}

	@Override
	public double hentVekt() {
		return vekt;
	}

	@Override
	public double hentX() {
		return posisjonX;
	}

	@Override
	public double hentY() {
		return posisjonY;
	}

	@Override
	public void nesteSteg(int stegNummer, ISimulator spill) {
		if (posisjonX < 0 || posisjonX >= Simulator.bredde) {
			retning = retning.settX(-1 * retning.x());
		}

		if (posisjonY < 0 || posisjonY >= Simulator.høyde) {
			retning = retning.settY(-1 * retning.y());
		}
	}

	@Override
	public void settPosisjon(double x, double y) {
		posisjonX = x;
		posisjonY = y;
	}

	@Override
	public void settPosisjonRelativ(double dx, double dy) {
		settPosisjon(dx + posisjonX, dy + posisjonY);

	}
}