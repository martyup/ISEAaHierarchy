package inf101;

import inf101.okosystem.Fisk;
import inf101.okosystem.Plankton;
import inf101.okosystem.Sel;
import inf101.okosystem.Spekkhogger;
import inf101.skogen.GUI;
import inf101.skogen.ITing;
import inf101.skogen.Simulator;

import java.util.ArrayList;
import java.util.Collection;

public class Main101MeterSkogen {
	public static void main(String[] args) {
		Simulator sim = new Simulator();
		Collection<ITing> ting = new ArrayList<>();

		ting.add(new Spekkhogger(0, 0));
		ting.add(new Sel(0, 0));
		ting.add(new Fisk(0, 0));
		ting.add(new Plankton(0, 0));

		// GUI-en overtar kontrollen
		new GUI(sim, ting, Simulator.bredde, Simulator.høyde);
	}

}
