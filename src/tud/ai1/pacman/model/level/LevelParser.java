package tud.ai1.pacman.model.level;

import tud.ai1.pacman.exceptions.InvalidLevelCharacterException;
import tud.ai1.pacman.exceptions.InvalidLevelFormatException;
import tud.ai1.pacman.exceptions.NoPacmanSpawnPointException;
import tud.ai1.pacman.util.Consts;
import tud.ai1.pacman.util.FileOperations;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Laedt einen Level aus einer Datei oder einem String.
 *
 * @author Simon Breitfelder
 * @author Dominik Puellen
 * @author Robert Cieslinski
 * @author Kurt Cieslinski
 */

public class LevelParser {

    /**
     * Laedt den Level aus einer Datei.
     *
     * @throws InvalidLevelCharacterException falls der eingelesene Level ein unbekanntes Zeichen enthaelt
     * @throws InvalidLevelFormatException falls der eingelesene Level nicht rechteckig ist
     * @throws NoPacmanSpawnPointException falls der eingelesene Level keinen Pac-Spawner enthaelt
     */

	static String levelName = "";
	
    public static Level fromFile(String file) throws IllegalArgumentException,InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
        //TODO Aufgabe 4.2a
    	
    	Level level = null;
    	String mapStr = "";

    	levelName = getLevelName(file);
    	 
    	try {
			File fileName = new File(file);
			FileReader filereader = new FileReader(fileName);

			int singleCh = 0;
			
			while ((singleCh = filereader.read()) != -1) {
				if((char)singleCh==10) {
					mapStr+="\n";
				}
				else
					mapStr+=(char)singleCh;
				
			}
        	level = fromString(mapStr);	
	    	level.setName(getLevelName(file));

			filereader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getCause());
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		return level;
    }

    /**
     * Laedt den Level aus einem String.
     *
     * @throws InvalidLevelCharacterException falls der eingelesene Level ein unbekanntes Zeichen enthaelt
     * @throws InvalidLevelFormatException falls der eingelesene Level nicht rechteckig ist
     * @throws NoPacmanSpawnPointException falls der eingelesene Level keinen Pac-Spawner enthaelt
     */
    public static Level fromString(String mapStr) throws IllegalArgumentException,InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
    	//TODO Aufgabe 4.2b
    
    	Level level = parseLevel(mapStr);
    	System.out.println(level.getRandomSpaceField()+ "Test");
    	level.setName(levelName);
    	return level;
    }
    
    public static Level parseLevel(String mapStr) {
    	 Field[][] map = null;
    	 Point[] ghosts = new Point[Consts.NUM_GHOSTS];
    	 Point[] pacmans = new Point[2];
    	 
    	 int ghostCnt = 0;
    	 int pacmanCnt = 0;
    	 
     	 int height = 0;
    	 int width = 0;
    	
    	
		String[] parsedMap_Str = mapStr.split("\n");
		height = parsedMap_Str.length;
		width = parsedMap_Str[0].length();
		map = new Field[height][width];
		//System.out.println(parsedMap_Str.length);  // height
		//System.out.println(parsedMap_Str[0].length());  // width
	
		for (int i = 0; i < parsedMap_Str.length; i++) {
			for (int j = 0; j < parsedMap_Str[0].length(); j++) {
				char chr = parsedMap_Str[i].charAt(j);
				map[i][j] = new Field(new Coordinate(i,j), map[i][j].findByValue(chr));
				if(chr==MapModule.GHOST_SPAWN.getValue()&& ghostCnt < ghosts.length) {
					ghosts[ghostCnt++] = new Point(j, i); 
				}else if(chr==MapModule.PLAYER_SPAWN.getValue()&& pacmanCnt < pacmans.length) {
					pacmans[pacmanCnt++] = new Point(j, i);
				}
			}
		}
		
		
		// check map. 
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		
		for (int i = 0; i < pacmans.length; i++) {
			System.out.println(pacmans[i]);
		}
		
		Level level = new Level(map, pacmans, ghosts);
 //   	System.out.println("random solid check: " + level.isSolid((int)level.getRandomSpaceField().getX(), (int)level.getRandomSpaceField().getY()));
//    	Point point = new Point(2, 2);
//    	Point[] results = level.getBranches(ghosts[1]);
//    	System.out.println("random: "  + results[0]);
//    	
		return level;
	
    }
    
   

    /**
     * @param file Dateipfad des Levels
     * @return eine lesbare Version des Levelnamens
     */
    public static String getLevelName(String file) throws IllegalArgumentException {
    	if(file==null)throw new IllegalArgumentException();
        String fname = file;
        // dateinamen filtern
        if (fname.contains("/"))
            fname = fname.substring(fname.lastIndexOf("/") + 1);
        if (fname.contains("\\"))
            fname = fname.substring(fname.lastIndexOf("\\") + 1);
        // dateiendung entfernen
        if (fname.contains("."))
            fname = fname.substring(0, fname.indexOf("."));

        return fname.replace("_", " ");
    }
    
    
    public static void main(String[] args) throws IllegalArgumentException, InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
		LevelParser l =  new LevelParser();
		fromFile("C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/Level_2.txt");
		String levelName = l.getLevelName("C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/Level_2.txt");
		System.out.println(levelName);
	} 
}
