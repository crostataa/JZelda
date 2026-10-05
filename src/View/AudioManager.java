package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioManager {

    static Clip clip;
    static URL[] soundURL = new URL[30];
    static FloatControl fc;
    static AudioManager instance;


    private AudioManager() {

        soundURL[0] = getClass().getResource("/assets/titleScreen.wav");
        soundURL[1] = getClass().getResource("/assets/overworld.wav");
        soundURL[2] = getClass().getResource("/assets/linkAttack.wav");
        soundURL[3] = getClass().getResource("/assets/getItem.wav");
        soundURL[4] = getClass().getResource("/assets/enemyDeath.wav");
        soundURL[5] = getClass().getResource("/assets/linkDie.wav");
        soundURL[6] = getClass().getResource("/assets/gameOver.wav");
        soundURL[7] = getClass().getResource("/assets/arrow 2.wav");
        soundURL[8] = getClass().getResource("/assets/bomb explode.wav");
        soundURL[9] = getClass().getResource("/assets/ending.wav");
        soundURL[10] = getClass().getResource("/assets/fighter sword 1.wav");
        soundURL[11] = getClass().getResource("/assets/link hurt.wav");
        soundURL[12] = getClass().getResource("/assets/life refill.wav");
    }

    public static void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic(int i) {
        setFile(i);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void playSE(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            Clip seClip = AudioSystem.getClip();
            seClip.open(ais);
            seClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static AudioManager getInstance() {

        if (instance == null)  {
            instance = new AudioManager();
        }
        return instance;
    }

}


