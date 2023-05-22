package edu.ntnu.idatt2001.utility;

import java.io.File;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Class for playing sounds.
 */
public class SoundPlayer {

  /**
   * Method for playing a sound.
   *
   * @param filename path to the sound file
   */
  public static void play(String filename) {
    try {
      File audioFile = new File(filename);
      Clip audioClip = AudioSystem.getClip();
      audioClip.open(AudioSystem.getAudioInputStream(audioFile));
      audioClip.start();
    } catch (Exception e) {
      Logger.getGlobal().warning("Could not play sound");
    }
  }

  /**
   * Method for playing a sound on loop.
   *
   * @param filename path to the sound file
   */
  public static void playOnLoop(String filename) throws Exception {
    try {
      File audioFile = new File(filename);
      Clip audioClip = AudioSystem.getClip();
      audioClip.open(AudioSystem.getAudioInputStream(audioFile));
      audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      throw new Exception("Could not play sound" + e.getMessage());
    }
  }
}







