package tud.ai1.pacman;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import tud.ai1.pacman.model.level.Coordinate;
import tud.ai1.pacman.model.level.Field;
import tud.ai1.pacman.model.level.MapModule;
import tud.ai1.pacman.util.Consts;

public class FileReaderTest {
	static String mapStr = "";
	static Field[][] map = null;
	static int height = 0;
	static int width = 0;
	
	static Point[] ghosts = new Point[Consts.NUM_GHOSTS];
	static Point[] pacmans = new Point[4];
	 
	static int ghostCnt = 0;
	static int pacmanCnt = 0;
	public static void main(String[] args) {
		try {
			String path = "C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/Level_3.txt";
			//File file = new File(path);
			FileReader filereader = new FileReader(path);
			int singleCh = 0;
			
			while ((singleCh = filereader.read()) != -1) {
				if((char)singleCh==10) {
					mapStr+="\n";
				}
				else
					mapStr+=(char)singleCh;
				
			}
			
			filereader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println(mapStr);
		String[] parsedMap_Str = mapStr.split("\n");
		height = parsedMap_Str.length;
		width = parsedMap_Str[0].length();
		map = new Field[height][width];
		System.out.println(parsedMap_Str.length);  // height
		System.out.println(parsedMap_Str[0].length());  // width
	
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
		
		for (int i = 0; i < ghosts.length; i++) {
			System.out.println(ghosts[i]);
		}
		
	}

}
