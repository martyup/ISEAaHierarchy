package inf101.util.generators;

import inf101.okosystem.Fisk;
import inf101.okosystem.Plankton;
import inf101.okosystem.Sel;
import inf101.okosystem.Spekkhogger;
import inf101.skogen.ITing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ITingGenerator {
	DoubleGenerator doublePos = new DoubleGenerator();
	IntGenerator iting = new IntGenerator(3);

	/**
	 * gererates 50 random position
	 */

	public List<ITing> generateDifferent(Random r) {
		List<ITing> resultat = new ArrayList<ITing>();
		for (int i = 0; i < 50; i++) {
			double x = r.nextInt(102);
			double y = r.nextInt(102);
			int ting = iting.generate();
			ITing Ting = ITingChooser(ting);
			Ting.settPosisjon(x,y);
			resultat.add(Ting);
		}

		return resultat;
	}

	private ITing ITingChooser(int ting) {
		if (ting == 0)
			return(new Spekkhogger(0, 0));
		if (ting == 1)
			return(new Sel(0, 0));
		if (ting == 2)
			return(new Fisk(0, 0));
		else
			return(new Plankton(0, 0));
	}
}
