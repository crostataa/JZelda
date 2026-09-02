package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioManager {

    static Clip clip;
    static URL[] soundURL = new URL[30];
    float previousVolume = 0;
    float currentVolume = 0;
    static FloatControl fc;
    boolean mute = false;
    static AudioManager instance;


    private AudioManager() {

        soundURL[0] = getClass().getResource("");
        soundURL[1] = getClass().getResource("");
    }

    public static void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch(Exception e) {}
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();

    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {
        clip.stop();
    }

    public void volumeUp() {
        currentVolume += 1.0f;
        if (currentVolume > 6.0f) {
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeDown() {
        currentVolume -= 1.0f;
        if (currentVolume < -80.0f) {
            currentVolume = -80.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeMute() {
        if (!mute) {
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
        }
        else if(mute) {
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            mute = false;
        }

    }

    public static AudioManager getInstance() {

        if (instance == null)  {
            instance = new AudioManager();
        }
        return instance;
    }

}


