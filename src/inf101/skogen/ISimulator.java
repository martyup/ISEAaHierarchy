package inf101.skogen;

import java.util.Collection;

/**
 * Interface for spillet. GUI-klassen tar et objekt som implementerer ISpill, og
 * kaller metodene etter behov.
 * 
 * @author anya
 * 
 */
public interface ISimulator {
	/**
	 * Kalles hver gang et nytt steg skal utføres (normalt hvert tiendedels
	 * sekund).
	 * 
	 * Stegnummeret kan brukes til f.eks. å gjøre noe for hvert tiende steg
	 * (stegNummer % 10 == 0).
	 * 
	 * @param stegNummer
	 *            Nummeret på steget, telt fra
	 */
	public abstract void nyttSteg(int stegNummer);

	/**
	 * Kalles for å finne ut hvilke ting som skal tegnes på skjermen.
	 * 
	 * @return Alle ting i systemet som er i bruk og skal tegnes på skjermen.
	 */
	public abstract Collection<ITing> alleTing();

	/**
	 * @return Navn på et bakgrunnsbildet, relativt til GUI.bildeSti
	 */
	public abstract String bakgrunn();

	/**
	 * @return En status-streng, som vises i statusfeltet nederst på skjermen
	 */
	public abstract String status();

	/**
	 * Legger til en ny ting i spillet -- dette skjer når brukeren har trykket
	 * på en knapp for å opprette en ny ting. Tingen vil ha en tilfeldig
	 * posisjon (denne kan evt. justeres om man vil).
	 * 
	 * @param ting
	 *            Tingen som skal legges til
	 */
	public abstract void leggTil(ITing ting);

	/**
	 * Restart spillet -- fjern alle tingene, og sett ut eventuelle start-ting,
	 * som om man hadde lukket programmet og startet på nytt.
	 * 
	 */
	public abstract void startPåNytt();

}
