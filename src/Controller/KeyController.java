package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestore degli eventi della tastiera (KeyListener).
 * Mappa i tasti fisici premuti (WASD/frecce per il movimento, barra spaziatrice per attacco,
 * B per le bombe, P per le pozioni, X per l'arco) in flag booleani consumati dal GameController.
 */
public class KeyController implements KeyListener {

    public boolean up, down, left, right;

    public boolean attack, pause;

    //public boolean useItem;
    public boolean keyBomb, keyPotion, keyBow;

    /**
     * Intercetta la pressione di un tasto e imposta a true il corrispondente stato di azione/movimento.
     * @param e l'evento KeyEvent associato.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //per spostarsi
        // modificare con case switch* ???
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = true;
        // per le azioni
        if (code == KeyEvent.VK_SPACE) attack = true;
        if (code == KeyEvent.VK_B) keyBomb = true;
        if (code == KeyEvent.VK_P) keyPotion = true;
        if (code == KeyEvent.VK_X) keyBow = true;
    }

    /**
     * Intercetta il rilascio di un tasto e reimposta a false il corrispondente flag.
     * @param e l'evento KeyEvent associato.
     */
    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = false;
        if (code == KeyEvent.VK_B) keyBomb = false;
        if (code == KeyEvent.VK_P) keyPotion = false;
        if (code == KeyEvent.VK_X) keyBow = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //non usato
    }
}
