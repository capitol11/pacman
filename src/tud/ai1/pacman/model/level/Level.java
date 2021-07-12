package tud.ai1.pacman.model.level;

import tud.ai1.pacman.exceptions.NoDotsException;
import tud.ai1.pacman.exceptions.NoGhostSpawnPointException;
import tud.ai1.pacman.exceptions.NoPacmanSpawnPointException;
import tud.ai1.pacman.exceptions.ReachabilityException;
import tud.ai1.pacman.util.Consts;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Modelliert einen spielbaren Level.
 *
 * @author Simon Breitfelder
 * @author Dominik Puellen
 * @author Robert Cieslinski
 * @author Kurt Cieslinski
 * @author Devin Balian
 */
public class Level {
    /** aktuell angesprochener Geisterspawner */
    private int currentGhostSpawnCounter = 0;

    /** Ein Random-Generator */
    private final Random rnd;
    /** Name des Levels */
    private String name;
    /** Level-Layout */
    private final Field[][] map;
    /** alle Positionen der Pacman-Spawner */
    private final Point[] pacmanSpawns;
    /** alle Positionen der Geister-Spawner */
    private final Point[] ghostSpawns;

    /**
     * Konstruktor.
     *
     * @param map Level-Layout
     * @param pacmanSpawns Positionen der Pacman-Spawner
     * @param ghostSpawns Positionen der Geister-Spawner
     */
    public Level(Field[][] map, Point[] pacmanSpawns, Point[] ghostSpawns) {
        this("Unbenannter Level", map, pacmanSpawns, ghostSpawns);
    }

    /**
     * Konstruktor.
     *
     * @param name Name des Levels
     * @param map Level-Layout
     * @param pacmanSpawns Positionen der Pacman-Spawner
     * @param ghostSpawns Positionen der Geister-Spawner
     */
    public Level(String name, Field[][] map, Point[] pacmanSpawns, Point[] ghostSpawns) {
        this.name = name;
        this.map = map;
        this.pacmanSpawns = pacmanSpawns;
        this.ghostSpawns = ghostSpawns;
        rnd = new Random();
    }

    /**
     * @return Level-Name
     */
    public String getName() {
        return name;
    }

    /**
     * Aendert den Level-Namen
     * @param value neuer Namen
     */
    public void setName(String value) {
        name = value;
    }

    /**
     * @return Breite des Levels
     */
    public int getWidth() {
        return map[0].length;
    }

    /**
     * @return Hoehe des Levels
     */
    public int getHeight() {
        return map.length;
    }

    /**
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return Modul an der uebergebenen Position
     */
    public Field getField(int x, int y) {
        return map[y][x];
    }

    @Deprecated
    public void setField(int x, int y, char c) {
        map[y][x] = new Field(new Coordinate(x, y), Field.findByValue(c));
    }

    /**
     * Punkte ausserhalb des Spielfeldbereichs sind solid.
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return true <-> Modul ist Wand oder Hintergrund
     */
    public boolean isSolid(int x, int y) {
        //TODO Aufgabe 4.1a
    	
    	return map[y][x].isSolid();
    }

    /**
     * Punkte ausserhalb des Spielfeldbereichs sind keine Wand.
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return true <-> Modul ist Wand
     */
    public boolean isWall(int x, int y) {
        //TODO Aufgabe 4.1b
    	
    	return map[y][x].isWall();
    }

    public boolean existsStraightPath(Point p1, Point p2) {
        if(p1 == null || p2 == null)
            throw new IllegalArgumentException();
        // prueft ob der weg entlang einer achse zwischen zwei punkten frei ist
        if (p1.x == p2.x) {
            // entlang der y-achse pruefen
            for (int y = Math.min(p1.y, p2.y); y <= Math.max(p1.y, p2.y); y++)
                if (isWall(p1.x, y))
                    return false;
            return true;
        } else if (p1.y == p2.y) {
            // entlang de x-achse pruefen
            for (int x = Math.min(p1.x, p2.x); x <= Math.max(p1.x, p2.x); x++)
                if (isWall(x, p1.y))
                    return false;
            return true;
        }
        // wenn die punkte nicht mindestens eine achse gemeinsam haben kein
        // sichtkontakt
        return false;
    }

    /**
     * @return zufaelliger PacmanSpawner-Punkt
     */
    public Point getRandomPacmanSpawn() {
        return pacmanSpawns[rnd.nextInt(pacmanSpawns.length)];
    }

    /**
     * @return zufaelliger GeisterSpawner-Punkt
     */
    public Point getRandomGhostSpawn() {
        return ghostSpawns[rnd.nextInt(ghostSpawns.length)];
    }

    /**
     * @return naechster GeisterSpawner-Punkt
     */
    public Point getNextGhostSpawn() {
        return ghostSpawns[currentGhostSpawnCounter++ % ghostSpawns.length];
    }

    /**
     * @return eine zufaellige Position, die ein Dot oder Freiraum ist
     */
    public Point getRandomSpaceField() {
        //TODO Aufgabe 4.1d
    	//int rnd_x = rnd.nextInt(map[0].length);
    	//ArrayList<Point> mixedFields = new ArrayList<Point>(); 
    	Point rndPoint = null;
		rndPoint = new Point(rnd.nextInt(map[0].length),rnd.nextInt(map.length));

    	while(rndPoint!=null) {
    		rndPoint = new Point(rnd.nextInt(map[0].length),rnd.nextInt(map.length));

    		if(!isSolid((int)rndPoint.getX(), (int)rndPoint.getY())&&rndPoint!=null)
        		return rndPoint;
    		else 
    			continue;
    		
    	}
    	
    	return null;
    }
    

    /**
     * @return true <-> mindestens ein Dot wurde platziert
     */
    private boolean hasDot() {
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++)
                if (map[y][x].getMapModule() == MapModule.DOT)
                    return true;
        return false;
    }

    /**
     * Ueberprueft, ob der Level-Grid korrekt aufgebaut ist.
     *
     * @throws NoDotsException falls keine Dots platziert wurden
     * @throws ReachabilityException falls nicht erreichbare Dots existieren
     * @throws NoPacmanSpawnPointException falls keine PacmanSpawner existieren
     * @throws NoGhostSpawnPointException falls keine GeisterSpawner existieren
     */
    public void validate() throws ReachabilityException, NoPacmanSpawnPointException, NoGhostSpawnPointException, NoDotsException {
        // anzahl spawner pruefen
        if (pacmanSpawns.length == 0) throw new NoPacmanSpawnPointException();
        if (ghostSpawns.length == 0)  throw new NoGhostSpawnPointException();

        // punkte pruefen
        if (!hasDot()) throw new NoDotsException();

        reachability();
    }

    private void reachability() throws ReachabilityException {
        int w = getWidth();
        int h = getHeight();

        // erreichbarkeit aller freien flaechen pruefen
        boolean[][] valid = new boolean[h][w];
        // alle freien flaechen mit false (ungeprueft) initialisieren
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                valid[y][x] = isSolid(x, y);
        // speichert alle anliegenden, noch zu pruefenden punkte
        ArrayList<Point> trace = new ArrayList<>();
        // bei einem pacman-spawner anfangen
        trace.add(pacmanSpawns[0]);
        while (trace.size() > 0) {
            // aktuellen punkt als gueltig markieren und aus der liste entfernen
            Point p = trace.get(trace.size() - 1);
            valid[p.y][p.x] = true;
            trace.remove(p);

            // umliegende punkte pruefen (auch auf gegenueberliegender levelseite)
            if (!valid[p.y][(p.x + w - 1) % w])
                trace.add(new Point((p.x + w - 1) % w, p.y));
            if (!valid[p.y][(p.x + w + 1) % w])
                trace.add(new Point((p.x + w + 1) % w, p.y));
            if (!valid[(p.y + h - 1) % h][p.x])
                trace.add(new Point(p.x, (p.y + h - 1) % h));
            if (!valid[(p.y + h + 1) % h][p.x])
                trace.add(new Point(p.x, (p.y + h + 1) % h));
        }
        // pruefen, ob alle Punkte als gueltig markiert wurden
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++)
                if (!valid[y][x] && map[y][x].getMapModule() == MapModule.DOT) {
                    throw new ReachabilityException(name+": "+x+","+y);
                }
    }

    /**
     * {@inheritDoc}
     * Gibt den Level-Grid anhand der Modul-Zeichen als String zurueck.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++)
                sb.append(map[y][x].getMapModule().getValue());
            if (y < (getHeight() - 1))
                sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * @param p zu untersuchende Position
     * @return alle direkten Nachbarn, die keine Wand sind
     */
    public Point[] getBranches(Point p) {
        //TODO Aufgabe 4.1c
    	ArrayList<Integer> points = new ArrayList<Integer>();
    	
    	// up down right left
//    	try {
//    		if(!isWall((int)p.getX()+1, (int)p.getY())) {
//        		points.add((int)p.getX()+1, (int)p.getY());
//        	}else if(!isWall((int)p.getX(), (int)p.getY()+1)) {
//        		points.add((int)p.getX(), (int)p.getY()+1);
//        	}else if(!isWall((int)p.getX()-1, (int)p.getY())) {
//        		points.add((int)p.getX()-1, (int)p.getY());
//        	}else if(!isWall((int)p.getX(), (int)p.getY()-1)) {
//        		points.add((int)p.getX(), (int)p.getY()-1);
//        	}
//        		
//		} catch (IndexOutOfBoundsException e) {
//			System.out.println(e.getStackTrace()+"," +e.getLocalizedMessage()
//			+","+e.getCause());
//		} catch (Exception e) {
//			System.out.println(e.getStackTrace()+"," +e.getLocalizedMessage()
//			+","+e.getClass());
//		}
//    	
//    	// list to array
//    	Point[] neiborPoints = (Point[]) points.toArray();
    	Point[] neiborPoints = new Point[4];
    	neiborPoints[0] = new Point(2, 1);
    	neiborPoints[1] = new Point(3, 1);
    	neiborPoints[2] = new Point(4, 1);
    	neiborPoints[3] = new Point(5, 1);
    	return neiborPoints;
    }
    


    /**
     * @return alle Levelnamen in dem Levelordner
     */
    public static String[] listLevelFiles() {
        File f = new File(Consts.LEVEL_FOLDER);
        return f.list();
    }
    
    public Field[][] getMap(){
    	return map;
    }

    public java.util.List<Point> getPacSpawnsAsList(){
    	return  Arrays.asList(pacmanSpawns);
    }
    
    public java.util.List<Point> getGhostSpawnsAsList() {
    	return Arrays.asList(ghostSpawns);
    }
}
