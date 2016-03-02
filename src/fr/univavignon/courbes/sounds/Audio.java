package fr.univavignon.courbes.sounds;

import  java.io.*;
import javax.sound.sampled.*;
 
 
/** @author Quentin Castillo **/
public class Audio extends Thread implements AudioHandle{
     
     
    AudioInputStream audioInputStream = null;
    SourceDataLine line;
    String name;
    
    public Audio(){
    	// null
    }
    

    
    public void CollisionSound()
    {
    	this.name = "res/sounds/collision.wav";
    	start();
    	
    }
    
    public void MusicInGame()
    {
    	this.name = "res/sounds/music.wav";
    	start();

    }
    
    public void ItemCreated()
    {
    	this.name = "res/sounds/pop.wav";
    	start();
    }
    
    public void ItemCollision()
    {
    	this.name = "res/sounds/item.wav";
    	start();
    }
    
    public void IntroSong()
    {
    	this.name = "res/sounds/intro.wav";
    	start();
    }
    
    public void Button()
    {
    	this.name = "res/sounds/button.wav";
    	start();
    }
    
    public void run(){
        File fichier = new File(name);
        try {
        AudioFileFormat format = AudioSystem.getAudioFileFormat(fichier);
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
         
        try {
            audioInputStream = AudioSystem.getAudioInputStream(fichier);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
         AudioFormat audioFormat = audioInputStream.getFormat();
         DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
          
         try {
             line = (SourceDataLine) AudioSystem.getLine(info);
                        
             } catch (LineUnavailableException e) {
               e.printStackTrace();
               return;
             }
         
         
          
        try {
                line.open(audioFormat);
        } catch (LineUnavailableException e) {
                    e.printStackTrace();
                    return;
        }
        line.start();
        try {
            byte bytes[] = new byte[1024];
            int bytesRead=0;
            while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
                 line.write(bytes, 0, bytesRead);
                }
        } catch (IOException io) {
            io.printStackTrace();
            return;
        }
    }
}