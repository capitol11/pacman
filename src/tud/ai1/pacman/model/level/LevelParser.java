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
    public static Level fromFile(String file) throws IllegalArgumentException,InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
        //TODO Aufgabe 4.2a
    	
    	Level level = null;
    	try {
    		String pfad = file;
        	level = fromString(getLevelName(pfad));	
        	//System.out.println(level.getName());
		} catch (IllegalArgumentException e) {
			System.out.println("Filnename is not empty or null."); 
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
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
    public static Level fromString(String content) throws IllegalArgumentException,InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
    	//TODO Aufgabe 4.2b
    	Field[][] map = null;
    	
		int singleCh;
		FileReader fileReader = null;
    	int i = 0;
    	int j = 0;
    	int totalCnt = 0;
    	
    	File file = new File("C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/AI2.txt");
    	try {
			fileReader = new FileReader(file);
			
			while((singleCh = fileReader.read())!=-1){
				System.out.print((char)singleCh);
				//map[i][j] = new Field(new Coordinate(i, j), map[i][j].findByValue((char)singleCh));
				
				totalCnt++;
				if(singleCh==10) {
					j++;
					i=0;
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println(totalCnt+","+j);
    	
    	totalCnt-=j;
    	i = totalCnt/(j+1);
    	//j +=1;
    	map = new Field[j][i];   // j = y, i = x 
    	
    	System.out.println(i + ", " + j);
    	Level level = initLevel(map, file, i, content);
    	return level;
    }
    
    
    public static Level initLevel(Field[][] map, File file, int numOfCol,String content) {
    	Point[] ghosts = new Point[Consts.NUM_GHOSTS];
    	Point[] players = new Point[2];
    	int i=0;
    	int j=0;
    	int playerCnt = 0;
    	int ghostCnt = 0;
    	FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			int singleCh = 0;
			
			while((singleCh = fileReader.read())!=-1){
				
				if(i==numOfCol) {
					j++;
					i=-1;
				}else {
					if((char)singleCh==MapModule.PLAYER_SPAWN.getValue()&&playerCnt<2)
						players[playerCnt++] = new Point(j, i);
					else if((char)singleCh==MapModule.GHOST_SPAWN.getValue()&&ghostCnt<Consts.NUM_GHOSTS)
						ghosts[ghostCnt++] = new Point(j, i);
					map[j][i] = new Field(new Coordinate(j, i), map[j][i].findByValue((char)singleCh));
					
				}
				i++;	
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Level level = new Level(content, map, ghosts, players);
    	System.out.println(level.getRandomSpaceField());
    	Point point = new Point(2, 2);
    	Point[] results = level.getBranches(ghosts[1]);
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
		fromFile("C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/AI2.txt");
		
	} 
}
