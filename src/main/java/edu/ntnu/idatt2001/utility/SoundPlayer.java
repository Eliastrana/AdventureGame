package edu.ntnu.idatt2001.utility;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.logging.Logger;

public class SoundPlayer {

    public static void play(String filename) {
        try {
            File soundFile = new File(filename);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            Logger.getGlobal().warning("Could not play sound");
        }
    }

    public static void playOnLoop(String filename) {
        try {
            File soundFile = new File(filename);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not play sound");
        }
    }
}







