package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

    public boolean up, down, left, right;

    public boolean attack, pause;

    //public boolean useItem;
    public boolean keyBomb, keyPotion, keyBow;

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
        if (code == KeyEvent.VK_P) pause = true;
        if (code == KeyEvent.VK_B) keyBomb = true;
        if (code == KeyEvent.VK_P) keyPotion = true;
        if (code == KeyEvent.VK_X) keyBow = true;
    }

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
