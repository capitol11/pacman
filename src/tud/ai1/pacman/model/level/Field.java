package tud.ai1.pacman.model.level;

import java.util.ArrayList;

/**
 * Klasse der Feldeinheit. Das {@link Level} besteht nur aus diesen
 * Feldeinheiten. Alle wichtigen Informationen sind hier gespeichert.
 * 
 * @author Artur Seitz
 * @author Dennis Schirmer
 * @author Mahmoud El-Hindi
 * @author Darjush Siahdohoni
 * @author Igor Cherepanov
 * @author Hermann Berket
 * @author Maximilian Kratz
 * @author Devin Balian
 * 
 * @version 2021-05-25
 */
public class Field extends AbstractField {
    /*
    * In dieser Klasse darf keine eigene Variable vom Typ {@link Coordinate} oder {@link ICoordinate}
    * erstellt werden. Fuer diese Vorkomnisse soll immer die abstrakte Superklasse benutzt werden.
    */

    /**
     * {@link MapModule} des {@link Field}.
     */
    private MapModule module;

    /**
     * Initialisiert ein {@link Field} mit uebergebenen {@link ICoordinate} und {@link MapModule}.
     * @param coordinate {@link ICoordinate} fuer das neu zu erstellende {@link Field}.
     * @param module {@link MapModule} fuer das neu zu erstellende {@link Field}.
     * @throws IllegalArgumentException
     */
    public Field(final ICoordinate coordinate, MapModule module) throws IllegalArgumentException {
        //TODO Aufgabe 3a
    	super(coordinate);
    	this.module = module; 
    }

    /**
     * Gibt einen String zurueck, welcher das {@link Field} repraesentiert.
     * @return Zum {@link Field} passenden String. Dieser String repraesentiert das Modul des
     *         {@link Field}.
     */
    @Override
    public String toString() {
        return Character.toString(module.getValue());
    }

    /**
     * Gibt die {@link ICoordinate} des {@link Field} zurueck.
     * 
     * @return Gibt die {@link ICoordinate} des {@link Field} zurueck.
     * @see tud.ai1.minesweeper.impl.model.AbstractField#getCoordinates()
     */
    @Override
    public ICoordinate getCoordinates() {
        //TODO Aufgabe 3b
    	try {
			super.validateCoordinate(coordinates);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+ e.getMessage());
		} 
    	return coordinates;
    }

    /**
     * Setzt die {@link ICoordinate} des {@link Field} auf den neuen, uebergebenen Wert.
     * 
     * @param coordinate Zu setzende {@link ICoordinate}.
     * @see tud.ai1.minesweeper.impl.model.AbstractField#setCoordinates(ICoordinate)
     */
    @Override
    public void setCoordinates(final ICoordinate coordinate) {
        //TODO Aufgabe 3b
    	try {
			super.validateCoordinate(coordinate);
	    	this.coordinates = coordinate;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+ e.getMessage()); 
		}
    }

    /**
     * @return Das {@link MapModule} des {@link Field}.
     */
    public MapModule getMapModule() {
        return this.module;
    }

    /**
     * Setzt das {@link MapModule} dieses {@link Field} auf den uebergebenen Wert.
     * @param module Das {@link MapModule}
     * @throws IllegalArgumentException
     */
    public void setMapModule(MapModule module) throws IllegalArgumentException {
        if(module == null) throw new IllegalArgumentException("module must not be null!");
        this.module = module;
    }

    /**
     * @param c Gesuchtes Zeichen
     * @return Das zum Zeichen c passende {@link MapModule}. Wenn kein passendes {@link MapModule} existiert,
     * wird null zurück gegeben.
     */
    public static MapModule findByValue(char c) {
        //TODO Aufgabe 3d
    	switch (c) {
		case 'X': 
			return MapModule.WALL;
		case ' ': 
			return MapModule.DOT;
		case '_':
			return MapModule.SPACE;
		case 'B':
			return MapModule.BACKGROUND;
		case 'P':
			return MapModule.PLAYER_SPAWN;
		case 'G':
			return MapModule.GHOST_SPAWN;
		case 'U':
			return MapModule.POWERUP;
		case 'S':
			return MapModule.SPEEDUP;
		case 'T':
			return MapModule.TELEPORT;
		default:
			return null;
			//default:
			//throw new IllegalArgumentException("Unexpected value: " + c);
		}
    }

    /**
     * @param c Zu pruefendes Zeichen des {@link MapModule}.
     * @return True, wenn das Zeichen gültig ist.
     */
    public static boolean isValid(char c) {
        //TODO Aufgabe 3e
    	if(findByValue(c)!=null)
    		return true;
    	else 
    		return false;
    
     }
 
     

    /**
     * @return True, wenn das Feld entweder Wand oder Hintergrund ist. Ansonsten false.
     */
    public boolean isSolid() {
        //TODO Aufgabe 3c
    	if(module==MapModule.BACKGROUND||module==MapModule.WALL)
    		return true;
    	else
    		return false;
    }

    /**
     * @return True, wenn das Feld eine Wand ist. Ansonsten false.
     */
    public boolean isWall() {
    	//TODO Aufgabe 3c
    	if(module==MapModule.WALL)
    		return true;
    	else
    		return false;
    }

    /**
     * @return True, wenn das Feld Freiraum oder Dot ist. Ansonsten false.
     */
    public boolean isFree() {
    	//TODO Aufgabe 3c
    	if(module==MapModule.SPACE||module==MapModule.DOT)
    		return true;
    	else
    		return false;
    }

    /**
     * @return True, wenn das Feld ein Dot ist. Ansonsten false.
     */
    public boolean isDot() {
    	//TODO Aufgabe 3c
    	if(module==MapModule.DOT)
    		return true;
    	else 
    		return false;
    }

}

