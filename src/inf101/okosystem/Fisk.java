package inf101.okosystem;

import java.util.Collection;

import inf101.math.Vektor;
import inf101.skogen.IAktiv;
import inf101.skogen.ISimulator;
import inf101.skogen.ITing;

public class Fisk extends LeverIHavet implements IAktiv {

	public Fisk(double x, double y) {
		super("Fisk", x, y, 20, 20, 4, 2, 2.5, 15, 'f',
				"Vitenskapelige navnet er : Salmo salar", "fisk.png", "fiskV.png", "liten","fisk.png");
	}

	@Override
	public ITing lagKopi() {
		return new Fisk(this.hentX(), this.hentY());
	}

	@Override
	public ITing lagNy(int x, int y) {
		return new Fisk(x, y);
	}

	@Override
	public boolean erSpiselig(ITing t) {
		return (t instanceof Plankton);
	}

	@Override
	public boolean erFarlig(ITing t) {
		return (t instanceof Spekkhogger || t instanceof Sel);
	}
}