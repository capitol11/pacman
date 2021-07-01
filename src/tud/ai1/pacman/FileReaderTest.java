package tud.ai1.pacman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderTest {
	static String mapStr = "";

	public static void main(String[] args) {
		try {
			File file = new File("C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/Level_3.txt");
			FileReader filereader = new FileReader(file);
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
		System.out.println(parsedMap_Str.length);  // height
		System.out.println(parsedMap_Str[0].length());  // width
	
	}

}
