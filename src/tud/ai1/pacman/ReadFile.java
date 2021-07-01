package tud.ai1.pacman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    public static void main(String[] args){
        try{
            File file = new File("C:/Users/Soohyun/Desktop/Coderahmen_Eclipse/assets/levels/Level_2.txt");
            FileReader filereader = new FileReader(file);
            int singleCh = 0;
            while((singleCh = filereader.read()) != -1){
                System.out.print((char)singleCh);
            }
            filereader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
        
        String st = "8888888888888888888888888888888888108880323232323284888432323232328088108832888832888888888888883288883288108832883232323232883232323232883288108832323288888832883288888832323288108888883288858832883288858832888888108832323232323232883232323232323288108832888888888888888888888888883288108832323232327171887171323232323288108832888888328888888888328888883288108885323232323232323232323232328588108888888888888888888888888888888888";
		st = st.replace("10", "    ");
		System.out.println(st);
		
		String[] tmp = st.split("   ");
		
		System.out.println("LF is called " + (tmp.length-1) + " times");
		
    }
}


