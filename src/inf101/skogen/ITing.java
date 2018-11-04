package inf101.skogen;

import inf101.math.Utregning;

/**
 * Grensesnitt for alle objekter som er fysisk tilstede i verden. Dette
 * inkluderer for eksempel: dyr, planter, ikke-organiske ting, ting som er døde,
 * ting som er levende.
 * 
 * En ting som "er i bruk" (i henhold til erIBruk(), er fremdeles tilstede i
 * verden; de som ikke er i bruk er/skal fjernes.
 * 
 * Noen av metodene er hovedsaklig beregnet på å gi informasjon til GUI-en, for
 * visuell presentasjon.
 * 
 * @author anya
 * @see Utregning, ISpill
 */
public interface ITing {
	/**
	 * Bli spist. 'mengde' spisbar næringsverdi blir fjernet fra tingen.
	 * 
	 * Dersom det ikke er vekt igjen, settes tingen til "ikke i bruk". Dyr vil
	 * som regel bli "ikke levende" hvis de blir spist av; for en del planter er
	 * det normalt å bli spist litt på.
	 * 
	 * <pre>
	 *   Forkrav:
	 *     mengde <= næringsVerdi()
	 * </pre>
	 * 
	 * @param spiser
	 *            Tingen som spiser oss
	 * @param mengde
	 *            Mengde næringsverdi som blir spist
	 */
	public abstract void bliSpist(ITing spiser, double mengde);

	/**
	 * @return True hvis objektet har gyldig datastruktur.
	 */
	public abstract boolean datainvariant();

	/**
	 * Ting som skal fjernes fra spillet (f.eks. når de er spist opp), settes
	 * til å være 'ikke i bruk'. De skal så fjernes fra listen over aktive
	 * objekter ved neste anledning.
	 * 
	 * En del metoder forutsetter (eller kan forutsette) at erIBruk() er true.
	 * 
	 * @return True hvis objektet fremdeles er i bruk.
	 */
	public abstract boolean erIBruk();

	/**
	 * Ting som ikke er levende er ikke lenger aktive, men de kan fremdeles
	 * finnes i systemet og f.eks. spises.
	 * 
	 * @return True hvis objektet er 'levende'
	 */
	public abstract boolean erLevende();

	/**
	 * Kollisjon kan lettvint sjekkes med om avstanden mellom sentrum av to
	 * objekter er mindre enn summen av størrelsene. Utregning-klassen kan
	 * brukes til å kalkulere avstander.
	 * 
	 * @param t
	 *            En annen ting vi skal sjekke kollisjon med.
	 * 
	 * @return True hvis tingene overlapper / har kollidert.
	 * 
	 * @see Utregning
	 */
	public abstract boolean erKollidertMed(ITing t);

	/**
	 * Brukes av GUI: Bilde som vises for tingen i spillområdet. Kalles på nytt
	 * for hvert steg, så man kan lage enkle animasjoner eller indikere status
	 * ved å returnere forskjellig navn.
	 * 
	 * Bildet bør helst være kvadratisk.
	 * 
	 * (GUI-klassen skalerer automatisk bildet etter størrelsen på tingen, og
	 * innlastede bilder blir mellomlagret.)
	 * 
	 * @return Navn på bildefilen, relativt til 'images'-katalogen
	 */
	public abstract String hentBildeNavn(int stegNummer);

	/**
	 * Brukes av GUI.
	 * 
	 * @return Ett tegn som identifiserer arten. Bør være unikt for hver klasse
	 *         (f.eks. 'k' for Kanin).
	 */
	public abstract char hentId();

	/**
	 * Brukes av GUI: Et bilde som representerer klassen, til bruk på
	 * knapperaden. Kan gjerne være det samme bildet som hentBildeNavn().
	 * 
	 * Bildet bør være kvadratisk.
	 * 
	 * (Knappene er normalt 32x32 piksler store, men bildet skaleres
	 * automatisk.)
	 * 
	 * @return Navn på bildefilen, relativt til 'images'-katalogen
	 */
	public abstract String hentIkonNavn();

	/**
	 * Brukes av GUI: Denne kan brukes til å vise mer informasjon om hver ting
	 * på skjermen, f.eks. vekt, status (levende/død), osv.
	 * 
	 * @return En kort streng med informasjon.
	 */
	public abstract String hentInfo();

	/**
	 * Næringsverdi er som regel avhengig av vekten; f.eks 10% av vekt.
	 * 
	 * @return Den mengden næring man får om man spiser hele tingen. 0.0 hvis
	 *         tingen ikke er i bruk.
	 */
	public abstract double hentNæringsVerdi();

	/**
	 * 
	 * @return Et navn som identifiserer klassen av ting.
	 */
	public abstract String hentNavn();

	/**
	 * Fornuftige størrelser er fra 1 og oppover.
	 * 
	 * (GUI-en forstørrer normalt x6.)
	 * 
	 * @return Radius til tingen. 0.0 hvis tingen ikke er i bruk.
	 */
	public abstract double hentStørrelse();

	/**
	 * Vekten, i gram. Vekt er relatert til næringsverdi, og antakelig også
	 * relatert til størrelse på et eller annet vis.
	 * 
	 * @return Vekt i gram. 0.0 hvis tingen ikke er i bruk.
	 */
	public abstract double hentVekt();

	/**
	 * <pre>
	 * Forkrav:  erIBruk()
	 * 
	 * Garanterer:  
	 *   assert 0 <= hentY() < Spill.høyde
	 * </pre>
	 * 
	 * @return X-posisjonen til tingen.
	 */
	public abstract double hentX();

	/**
	 * <pre>
	 * Forkrav:  erIBruk()
	 * 
	 * Garanterer:  
	 *   assert 0 <= hentY() < Spill.høyde
	 * </pre>
	 * 
	 * @return Y-posisjonen til tingen.
	 */
	public abstract double hentY();

	/**
	 * Lager en ny kopi, med samme vekt, størrelse, posisjon etc.
	 * 
	 * <pre>
	 * Garanterer: 
	 *    assert t.lagKopi().equals(t)
	 *    assert t.lagKopi() != t
	 *    t er uendret
	 * </pre>
	 * 
	 * @return En eksakt kopi av tingen.
	 */
	public abstract ITing lagKopi();

	/**
	 * Lager et nytt objekt av samme type, med standard vekt, størrelse etc. Bør
	 * gi samme resultat som å kalle konstruktøren med bare posisjon som
	 * parametre; eksakter verdier er uspesifisert.
	 * 
	 * <pre>
	 * Garanterer:
	 *   objektet er uendret
	 * </pre>
	 * 
	 * @param x
	 *            X-Posisjon
	 * @param y
	 *            Y-posisjon
	 * 
	 * @return Et nytt objekt av samme type som tingen.
	 */
	public abstract ITing lagNy(int x, int y);

	/**
	 * Utfør ett steg av tingens oppførsel.
	 * 
	 * <pre>
	 * Forkrav: erIBruk()
	 * 
	 * Effekt: Objektet kan endres på alle mulig måter, inkludert å settes til å ikke lenger være i bruk.
	 * </pre>
	 * 
	 * @param stegNummer
	 * @param spill
	 *            Spillet -- slik at man f.eks. kan legge til nye ting hvis man
	 *            formerer seg, osv.
	 * 
	 */
	public abstract void nesteSteg(int stegNummer, ISimulator spill);

	/**
	 * Sett posisjonen til tingen.
	 * 
	 * <pre>
	 * Forkrav:  
	 *   erIBruk()
	 *   0 <= x < Spill.bredde
	 *   0 <= y < Spill.høyde
	 * 
	 * Effekt:
	 *   o.settPosisjon(x,y) ==> o.hentX() == x
	 *   o.settPosisjon(x,y) ==> o.hentY() == y
	 *   Objektet er ellers uendret.
	 * </pre>
	 * 
	 * Merk at dersom x og y ikke tilfredstiller forkravene, trenger ikke
	 * garantien oppfylles; man kan da f.eks. lage en smultringverden med å
	 * sette x = x % bredde. En annen god løsning er å gjøre f.eks. o.x-x i
	 * stedet for o.x+x hvis o.x+x > Spill.bredde (og tilsvarende for y) -- da
	 * "spretter" ting tilbake hvis de prøver å gå utenfor brettet.
	 * 
	 * @param x
	 *            Ny X-posisjon
	 * @param y
	 *            Ny Y-posisjon
	 */
	public abstract void settPosisjon(double x, double y);

	/**
	 * Sett ny posisjon relativt til den gamle.
	 * 
	 * <pre>
	 * Forkrav:  
	 *   erIBruk()
	 *   0 <= hentX()+dx < Spill.bredde
	 *   0 <= hentY()+dy < Spill.høyde
	 * 
	 * Effekt:
	 *   Med gammelX == o.hentX() og gammelY == o.hentY(): 
	 *     o.settPosisjonRelativ(dx,dy) ==> o.hentX() == gammelX+dx
	 *     o.settPosisjonRelativ(dx,dy) ==> o.hentY() == gammelY+dy
	 *   Objektet er ellers uendret.
	 * </pre>
	 * 
	 * Merk at dersom dx og dy ikke tilfredstiller forkravene, trenger ikke
	 * garantien oppfylles; man kan da f.eks. lage en smultringverden med å
	 * sette x = (x+dx) % bredde.
	 * 
	 * @param dx
	 *            Verdi som skal legges til X-posisjonen
	 * @param dy
	 *            Verdi som skal legges til Y-posisjonen
	 */
	public abstract void settPosisjonRelativ(double dx, double dy);

}
