package inf101.skogen;

import inf101.math.Utregning;
import inf101.math.Vektor;

import java.util.Collection;

/**
 * @author anya
 * 
 */
public interface IAktiv extends ITing {
	/**
	 * Nåværende retning til tingen, eller en vilkårlig verdi hvis vi ikke har
	 * noen retning.
	 * 
	 * Hvis vi lagret forrige posisjon, kan retning regnes ut som den negative
	 * retningen til forrige posisjon. Dette er hendig hvis verden er
	 * implementert med "strikk-vegger", slik at man spretter tilbake hvis man
	 * prøver å gå utenfor. Retningsberegningen vil da legge merke til dette,
	 * slik at man ikke fortsetter å stange inn i veggen.
	 * 
	 * @return Retning, som en normaliser vektor
	 */
	public abstract Vektor retning();

	/**
	 * Hvor langt vi beveget oss i løpet av forrige steg.
	 * 
	 * @return Avstanden mellom nåværende posisjon og forrige posisjon
	 */
	public abstract double fart();

	/**
	 * @return Hvor langt tingen kan se, målt fra sentrum.
	 */
	public abstract double synsRadius();

	/**
	 * Sjekker om en ting er farlig.
	 * 
	 * En mulig definisjon på farlig er at 'this' er spiselig for 't'.
	 * 
	 * @param t
	 *            En annen ting
	 * @return True hvis 't' er farlig.
	 */
	public abstract boolean erFarlig(ITing t);

	/**
	 * Sjekker om en ting er spiselig.
	 * 
	 * En mulig definisjon på spiselig er at 't' har næringsverdi og tilhører en
	 * klasse som betraktes som spiselig.
	 * 
	 * @param t
	 *            En annen ting
	 * @return True hvis 't' er spiselig for 'this'.
	 */
	public abstract boolean erSpiselig(ITing t);

	/**
	 * Spis en porsjon av en ting. Spising skjer ved at man kaller
	 * bliSpist()-metoden på tingen man spiser, og legger til næringsverdien til
	 * sin egen vekt / energinivå.
	 * 
	 * Det er begrenset hvor mye man kan spise i løpet av ett steg; fornuftige
	 * verdier for det er f.eks. 1% av egenvekt (begrenset oppad til
	 * næringsverdien til maten, selvfølgelig). Selv små matbiter må antakelig
	 * spises over flere runder.
	 * 
	 * <pre>
	 *   Forkrav:
	 *     erLevende()
	 *     erSpiselig(t)
	 *     erSulten()
	 * </pre>
	 * 
	 * @param t
	 *            Tingen som skal spises
	 */
	public abstract void spis(ITing t);

	/**
	 * Sjekker om vi er sultne.
	 * 
	 * En mulig definisjon på sulten er at vekten er under et visst nivå i
	 * forhold til normalvekt. Evt. kan man ha et begrep om 'energinivå'.
	 * 
	 * @return
	 */
	public abstract boolean erSulten();

	/**
	 * Følgende enkle regel kan brukes for å sjekke synlighet: En ting er synlig
	 * hvis avstanden fra sentrum av dette objektet til ytterkanten av det andre
	 * objektet er mindre enn synsradiusen.
	 * 
	 * Utregning-klassen har metoder for avstandsberegning.
	 * 
	 * @param t
	 *            En ting
	 * @return True hvis tingen t er innenfor synsradiusen.
	 * @see Utregning
	 */
	public abstract boolean erSynlig(ITing t);

	/**
	 * Finner farlige ting.
	 * 
	 * @param ts
	 *            En samling ting
	 * @return Alle ting i 'ts' som er farlige, ihht erFarlig().
	 */
	public abstract Collection<ITing> alleFarlige(Collection<ITing> ts);

	/**
	 * Finner farlige ting.
	 * 
	 * @param ts
	 *            En samling ting
	 * @return Alle ting i 'ts' som er spiselig, ihht erSpiselig().
	 */
	public abstract Collection<ITing> alleSpiselige(Collection<ITing> ts);

	/**
	 * Finner synlige ting.
	 * 
	 * @param ts
	 *            En samling ting
	 * @return Alle ting i 'ts' som er synlige, ihht erSynlig().
	 */
	public abstract Collection<ITing> alleSynlige(Collection<ITing> ts);

	/**
	 * Beregn en retning til eventuelle matkilder blant tingene i ts.
	 * 
	 * 'ts' kan inneholde vilkårlige ting fra hele verden, og må filtreres med
	 * alleSynlige og alleSpiselige.
	 * 
	 * Det er tilstrekkelig å velge én mat-ting å gå mot den. Bruk
	 * retningsberegning fra Utregning-klassen for å finne retningen.
	 * 
	 * @param ts
	 *            En samling med ting
	 * @return Retning, eller samme som retning() om man ikke fant noe mat
	 * @see Utregning
	 */
	public abstract Vektor retningTilMat(Collection<ITing> ts);

	/**
	 * Beregn en retning vekk fra eventuelle andre ting i nærheten.
	 * 
	 * 'ts' kan inneholde vilkårlige ting fra hele verden, og bør filtreres med
	 * alleSynlige.
	 * 
	 * Det er tilstrekkelig å velge den nærmeste tingen, og returnere retningen
	 * vekk fra den. Bruk retningsberegning fra Utregning-klassen for å finne
	 * retningen (og neg()-metoden for å få retning vekk fra i stedet for til).
	 * 
	 * @param ts
	 *            En samling med ting
	 * @return Retning, eller samme som retning() om man ikke fant noen
	 *         hindringer
	 * @see Utregning
	 */
	public abstract Vektor retningUnnvik(Collection<ITing> ts);

	
	/**
	 * snur bildet hvis objektet skifter retning
	 * 
	 */
	public void flippBilde();

}
