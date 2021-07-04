package tud.ai1.pacman.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import tud.ai1.pacman.util.Consts;

/**
 * Modelliert einen einzelnen Highscore-Eintrag.
 *
 * @author Simon Breitfelder
 * @author Dominik Puellen
 * @author Kurt Cieslinski
 * @author Tim Lukas Kessel
 * 
 * @version 2021-05-23
 */
public class HighscoreEntry implements Comparable<HighscoreEntry> {
	/**
	 * Datum des gespielten Spiels.
	 */
	private final LocalDateTime date;

	/**
	 * Formatter um String in LocalDateTime zu formatieren. Verwendet wird das
	 * Format Jahr-Monat-Tag Stunde:Minute
	 * 
	 */
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Consts.HS_DATE_PATTERN);

	/**
	 * Nickname des Eintrags
	 */
	private final String name;

	/**
	 * Punktzahl des Eintrags
	 */
	private final int points;

	/**
	 * Konstruktor, der ein {@link HighscoreEntry_ML} aus einem String mit
	 * Informationen erstellt. Dieser Konstruktor kann benutzt werden, um Eintraege
	 * aus Zeilen der HighScore Datei zu erstellen.
	 * 
	 * @param line String mit den Werten fuer den {@link HighscoreEntry_ML} im
	 *             Format: datum;name;punkte
	 */
	public HighscoreEntry(final String data) {
		// TODO 5.1a
		// Dummy-Implementierung (bitte ersetzen):
		// this.date = LocalDateTime.now(); this.name = ""; this.points = 0;
		// data.replace(";", ",");
		String tmp = data.replace(";", ",");
		String[] scoreInfos = tmp.split(",");
				
		try {
			if (scoreInfos.length !=3 )
				throw new IllegalArgumentException();
			for (int i = 0; i < scoreInfos.length; i++) {
				if (scoreInfos[i] == null || scoreInfos[i] == "") {
					throw new IllegalArgumentException();
				}  // validate()
				System.out.print(scoreInfos[i] + " ");
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid data format. Please give the right format for the highscore." + e.getCause());
		}

		
		LocalDateTime date_ = LocalDateTime.parse(scoreInfos[0], FORMATTER);
		String name_ = scoreInfos[1];
		int points_ = Integer.parseInt(scoreInfos[2]);

		validate(date_, name_, points_);
		this.date = date_;
		this.name = name_;
		this.points = points_;
	}

	public static void main(String[] args) {
		HighscoreEntry hi = new HighscoreEntry("2021-05-21 10:55,PACM,80");
		//HighscoreEntry test = new HighscoreEntry("2020-02-03 12:12,name,20");
		//System.out.println(hi.equals(test));

	}

	/**
	 * Konstruktor der ein {@link HighscoreEntry_ML} aus den uebergebenen Parametern
	 * erstellt Bei fehlerhaften Parametern wird eine
	 * {@link IllegalArgumentException} geworfen
	 * 
	 * @param date   Spieldatum
	 * @param name   Name
	 * @param points Punktzahl
	 */
	public HighscoreEntry(final LocalDateTime date, final String name, final int points) {
		// TODO 5.1b
		// Dummy-Implementierung (bitte ersetzen):
		// this.date = LocalDateTime.now(); this.name = ""; this.points = 0;
		validate(date, name, points);
		this.date = date;
		this.name = name;
		this.points = points;

	}

	/**
	 * Hilfsmethode die ueberprueft, ob uebergebene Werte fuer einen HighScore
	 * gueltig sind.
	 * 
	 * @param date   Datum an dem der Highscore erspielt wurde
	 * @param name   Der Name des Spielers muss angegeben sein und ist auf 4 Zeichen
	 *               beschraenkt
	 * @param points Punktzahl die der Spieler erspielt hat
	 */
	public void validate(final LocalDateTime date, final String name, final int points) {
		// TODO Aufgabe 5.1a

		try {
			if (date == null)
				throw new IllegalArgumentException();
			if (name == null || name == "" || (name.length() > 4 && name.length() <= 0))
				throw new IllegalArgumentException();
			if (points < 0)
				throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			System.out.println("Input value is not valid.");
		}
	}

	/**
	 * Vergleichsmethode, die true zurueck gibt, falls das aktuelle Objekt
	 * inhaltlich gleich dem uebergebenen Objekt ist. In jedem anderen Fall wird
	 * false zurueckgegeben
	 *
	 * @param obj Objekt mit dem verglichen wird.
	 * @return True wenn Objekte gleich sind
	 */
	@Override
	public boolean equals(final Object obj) {
		// TODO Aufgabe 5.1d
		// Dummy-Implementierung (bitte ersetzen):

		if (obj instanceof HighscoreEntry && obj != null) {
			HighscoreEntry obj_ = (HighscoreEntry) obj;
			return (obj_.getDate().equals(getDate()) && obj_.getName().equals(getName())
					&& obj_.getPoints() == getPoints());
		}

		return super.equals(obj); 
	}

	/**
	 * Vergleicht zwei {@link HighscoreEntry_ML} anhand der erspielten Punkte. Haben
	 * beide gleich viele Punkte wird nach Datum entschieden, das fruehere Datum
	 * kommt zuerst.
	 *
	 * @param other {@link HighscoreEntry_ML} mit dem dieser Eintrag verglichen
	 *              werden soll.
	 * @return Einen Wert kleiner als 0 falls dieser Eintrag mehr Punkte hat als der
	 *         andere und einen Wert groesser 0 falls dieser Eintrag weniger Punkte
	 *         hat. Sind beide Punktzahlen gleich wird eine Zahl kleiner 0
	 *         zurueckgegeben, falls dieser Eintrag zeitlich frueher kommt als der
	 *         andere. Kommt der andere frueher ist die Zahl groesser als 0. Sind
	 *         beide auch zeitlich gleich wird 0 zurueckgegeben.
	 * 
	 */
	@Override
	public int compareTo(final HighscoreEntry other) {
		// TODO Aufgabe 5.1e
		// Dummy-Implementierung (bitte ersetzen):
		if(this.getPoints()>other.getPoints())
			return -1;
		else if(this.getPoints()<other.getPoints())
			return 1;
		else
			return 0;
	}

	/**
	 * Diese Methode gibt die String-Repraesentation des Objektes zurueck.
	 *
	 * @return String-Repraesentation des Objektes im Format: datum;name;punkte.
	 */
	@Override
	public String toString() {
		return this.getDate() + Consts.HS_DELIMITER + this.name + Consts.HS_DELIMITER + this.points;
	}

	/**
	 * Getter fuer date als String.
	 *
	 * @return Datum des Eintrags
	 */
	public String getDate() {
		return this.date.format(FORMATTER);
	}

	/**
	 * @return Nickname des Spielers
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return Punktzahl des Eintrags
	 */
	public int getPoints() {
		return this.points;
	}

}
