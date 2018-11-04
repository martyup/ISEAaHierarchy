package inf101.skogen;

import inf101.okosystem.Fisk;
import inf101.okosystem.Plankton;
import inf101.okosystem.Sel;
import inf101.okosystem.Spekkhogger;

import java.util.*;

public class Simulator implements ISimulator {
	List<ITing> tingListe;

	Random random = new Random();
	public static final int bredde = 101, høyde = 101;

	@Override
	public String bakgrunn() {
		return "bakgrunn.png";
	}

	@Override
	public void nyttSteg(int stegNummer) {
		for (ITing t : tingListe) {
			t.nesteSteg(stegNummer, this);
		}
		ListIterator<ITing> l = tingListe.listIterator();
		while (l.hasNext()) {
			if (!l.next().erIBruk())
				l.remove();
		}
		if (stegNummer == 0){
			//tingListe.add(new Spekkhogger(random.nextInt(bredde), random.nextInt(høyde)));
			tingListe.add(new Fisk(random.nextInt(bredde), random.nextInt(høyde)));
			tingListe.add(new Sel(random.nextInt(bredde), random.nextInt(høyde)));
			for (int i = 0; i < 10; i++)
				tingListe.add(new Plankton(random.nextInt(bredde), random.nextInt(høyde)));
		}

		if (stegNummer % 10 == 0) {
			tingListe.add(new Plankton(random.nextInt(bredde), random.nextInt(høyde)));
		}
		if (stegNummer % 25 == 0) {
			tingListe.add(new Fisk(random.nextInt(bredde), random.nextInt(høyde)));
		}
		if (stegNummer % 60 == 0) {
			tingListe.add(new Sel(random.nextInt(bredde), random.nextInt(høyde)));
		}
		if (stegNummer % 150 == 0) {
			tingListe.add(new Spekkhogger(random.nextInt(bredde), random.nextInt(høyde)));
		}

	}

	@Override
	public Collection<ITing> alleTing() {
		return tingListe;
	}

	@Override
	public String status() {
		return "" + tingListe.size() + " ting";
	}

	@Override
	public void startPåNytt() {
		tingListe = new LinkedList<ITing>();
	}

	public Simulator() {
		startPåNytt();
	}

	@Override
	public void leggTil(ITing nyTing) {
		if (nyTing != null) {
			tingListe.add(nyTing);
		}
	}
}