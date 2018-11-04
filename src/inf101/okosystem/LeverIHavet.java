package inf101.okosystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import inf101.math.Utregning;
import inf101.math.Vektor;
import inf101.skogen.*;

public abstract class LeverIHavet implements IAktiv {
	Random random = new Random();

	protected String navn;
	protected double posisjonX;
	protected double posisjonY;
	protected double vekt;
	protected double normalVekt;
	protected int størrelse;
	protected double næringsVerdi;
	protected double fart;
	protected double synsRadius;
	protected char id;
	protected String info;
	protected String bilde;
	protected String bildeV;
	protected String type;
	String nåværendeBilde = bilde;
	public Vektor retning = new Vektor(1, 1);
	private boolean erLevende = true;
	public Vektor forrigeRetning= retning;

	public LeverIHavet(String navn, double posisjonX, double posisjonY,
			double vekt, double normalVekt, int størrelse, double næringsVerdi,
			double fart, double synsRadius, char id, String info, String bilde, String bildeV,
			String type, String nåværendeBilde) {
		this.navn = navn;
		this.posisjonX = posisjonX;
		this.posisjonY = posisjonY;
		this.vekt = vekt;
		this.normalVekt = normalVekt;
		this.størrelse = størrelse;
		this.næringsVerdi = næringsVerdi;
		this.fart = fart;
		this.synsRadius = synsRadius;
		this.id = id;
		this.info = info;
		this.bilde = bilde;
		this.bildeV = bildeV;
		this.type = type;
		this.nåværendeBilde = nåværendeBilde;
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
		return nåværendeBilde;
	}

	@Override
	public char hentId() {
		return id;
	}

	@Override
	public String hentIkonNavn() {
		return nåværendeBilde;
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
	public void flippBilde(){ 
		//hvis bildet var til høyre skift til venstre
		if (this.nåværendeBilde == this.bilde){
			nåværendeBilde = bildeV;
		//	System.out.println("venstre");
		}
		
		else {
			nåværendeBilde = bilde;
			//System.out.println("høyre");
		}
	}

	@Override
	public void nesteSteg(int stegNummer, ISimulator spill) {
		// hvis den treffer veggen spretter den og bytter retning
		if (posisjonX < 2 || posisjonX >= 95) {
			retning = retning.settX(-1 * retning.x());
			flippBilde();
		}

		if (posisjonY < 2 || posisjonY >= 95) {
			retning = retning.settY(-1 * retning.y());
		}
		settPosisjonRelativ(retning().x() * fart(), retning().y() * fart());

		// hvis den er sulten går den mot mat
		if (erSulten()) {
			retning = retningTilMat(spill.alleTing());
		}
		boolean erRedd = false;
		for (ITing t : spill.alleTing()) {
			if (erSynlig(t) && erFarlig(t)) {
				//System.out.println("Jeg er redd");
				erRedd = true;
			}
		}

		// hvis de er redde skal de flykte
		if (erRedd) {
			retning = retningUnnvik(spill.alleTing());
		}

		// hvis vekten er for lav dør de
		if (vekt < normalVekt / 5) {
			erLevende = false;
		}

		// hvis den er spiselig, du er sulten og den har kollidert med et
		// objekt, vil den spise det
		for (ITing t : spill.alleTing()) {
			if (erSpiselig(t) && erKollidertMed(t) && erSulten()) {
				spis(t);
			}

			// vekten forminskes etter tid
			vekt = vekt * 0.9995;

//			//høyreretning for bilde flipping
//			if (forrigeRetning.x() > 0 && retning.x() < 0){
//				flippBilde();
//			}
//			
//			//venstreretning
//			else if (forrigeRetning.x() < 0 && retning.x() > 0){
//				flippBilde();
//			}
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

	@Override
	public Vektor retning() {
		return retning;
	}
	
	

	@Override
	public double fart() {
		return fart;
	}

	@Override
	public double synsRadius() {
		return synsRadius;
	}

	@Override
	public void spis(ITing t) {
		if (!erLevende() && !erSpiselig(t) && !erSulten()) {
			throw new IllegalArgumentException();
		} else {
			double mengde = t.hentNæringsVerdi();
			t.bliSpist(this, mengde);
			vekt = vekt + mengde;
		}
		System.out.println(this.hentNavn() +" veier "+ vekt);
	}

	@Override
	public boolean erSulten() {

		if (vekt < normalVekt / 2) {
			//System.out.println(this.hentNavn() + " sulten");
			return true;
		} else {
			System.out.println(this.hentNavn() + " ikke sulten");
			return false;
		}
	}

	@Override
	public boolean erSynlig(ITing t) {
		return Utregning.senterAvstand(this, t) <= synsRadius;
	}

	@Override
	public Collection<ITing> alleFarlige(Collection<ITing> ts) {
		Collection<ITing> liste = new ArrayList<ITing>();
		for (ITing t : ts) {
			if (erFarlig(t) == true) {
				liste.add(t);
			}
		}
		return liste;
	}

	@Override
	public Collection<ITing> alleSpiselige(Collection<ITing> ts) {
		Collection<ITing> liste = new ArrayList<ITing>();
		for (ITing t : ts) {
			if (erSpiselig(t) == true) {
				liste.add(t);
			}
		}
		return liste;
	}

	@Override
	public Collection<ITing> alleSynlige(Collection<ITing> ts) {
		Collection<ITing> liste = new ArrayList<ITing>();
		for (ITing t : ts) {
			if (erSynlig(t) == true) {
				liste.add(t);
			}
		}
		return liste;
	}

	@Override
	public Vektor retningTilMat(Collection<ITing> ts) {
		Vektor nærmesteRetning = retning;
		int avstand = Integer.MAX_VALUE;

		for (ITing l : ts) {
			if (erSynlig(l) && erSpiselig(l)) {
				int midlertidigAvstand = (int) Utregning.senterAvstand(l,
						this.hentX(), this.hentY());
				if (midlertidigAvstand <= avstand) {
					avstand = midlertidigAvstand;
					nærmesteRetning = Utregning.retningTil(this, l);
				}

			}
		}
		return nærmesteRetning;
	}

	@Override
	public Vektor retningUnnvik(Collection<ITing> ts) {
		Vektor unnvikRetning = retning;
		int avstand = Integer.MAX_VALUE;

		for (ITing l : ts) {
			if (erSynlig(l) && erFarlig(l)) {
				int midlertidigAvstand = (int) Utregning.senterAvstand(l,
						this.hentX(), this.hentY());
				if (midlertidigAvstand <= avstand) {
					avstand = midlertidigAvstand;
					unnvikRetning = Utregning.retningTil(this, l).neg();
				}
			}
		}
		return unnvikRetning;
	}
}