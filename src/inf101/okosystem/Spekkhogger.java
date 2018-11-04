package inf101.okosystem;

import java.util.Collection;

import inf101.math.Vektor;
import inf101.skogen.*;

public class Spekkhogger extends LeverIHavet implements IAktiv {

	public Spekkhogger(double x, double y) {
		super("Spekkhogger", x, y, 600, 600, 14, 60, 1, 10, 'k',"Vitenskapelige navnet er : Orcinus orca", "Spekkhogger.png","SpekkhoggerV.png","stor", "Spekkhogger.png");
	}

	@Override
	public ITing lagKopi() {
		return new Spekkhogger(this.hentX(), this.hentY());
	}

	@Override
	public ITing lagNy(int x, int y) {
		return new Spekkhogger(x, y);
	}

	@Override
	public boolean erSpiselig(ITing t) {
		return (t instanceof Sel || t instanceof Fisk);
	}

	@Override
	public boolean erFarlig(ITing t) {
		return false;
	}	
	}