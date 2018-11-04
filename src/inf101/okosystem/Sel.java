package inf101.okosystem;

import java.util.Collection;

import inf101.math.Vektor;
import inf101.skogen.IAktiv;
import inf101.skogen.ISimulator;
import inf101.skogen.ITing;

public class Sel extends LeverIHavet implements IAktiv {

	public Sel(double x, double y) {
		super("Sel", x, y, 75, 75, 6, 7, 2, 12, 's',
				"Vitenskapelige navnet er : Pinniped", "sel.png","selV.png", "medium","sel.png");
	}

	@Override
	public ITing lagKopi() {
		return new Sel(this.hentX(), this.hentY());
	}

	@Override
	public ITing lagNy(int x, int y) {
		return new Sel(x, y);
	}

	@Override
	public boolean erSpiselig(ITing t) {
		return (t instanceof Fisk);
	}

	@Override
	public boolean erFarlig(ITing t) {
		return (t instanceof Spekkhogger);
	}
}