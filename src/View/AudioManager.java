package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * Gestore Singleton per la riproduzione di musica di sottofondo ed effetti sonori (SE) in formato .wav.
 * Implementa {@link Observer} per reagire direttamente alle azioni notificate dal GameModel
 * (es. sparo di freccia, colpo di spada).
 */
public class AudioManager implements Observer {

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

    /**
     * Carica il file audio corrispondente all'indice specificato nella clip principale.
     * @param i indice del file audio.
     */
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

    /**
     * Avvia la riproduzione in loop continuo della traccia musicale specificata.
     * @param i indice della traccia da riprodurre.
     */
    public void playMusic(int i) {
        setFile(i);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Ferma la musica di sottofondo attualmente in riproduzione.
     */
    public void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Riproduce una volta sola un effetto sonoro (Sound Effect).
     * @param i indice dell'effetto sonoro da riprodurre.
     */
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


    /**
     * Restituisce l'istanza unica di AudioManager (Singleton).
     * @return l'istanza di AudioManager.
     */
    public static AudioManager getInstance() {

        if (instance == null)  {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Riceve notifiche dal modello per la riproduzione di effetti sonori specifici.
     * @param o l'oggetto osservabile (GameModel).
     * @param arg il messaggio di evento (es. "ARROW_SHOT", "PLAYER_ATTACK").
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String message = (String) arg;
            if (message.equals("ARROW_SHOT")) {
                playSE(7);
            } else if (message.equals("PLAYER_ATTACK")) {
                playSE(10);
            }
        }
    }
}


