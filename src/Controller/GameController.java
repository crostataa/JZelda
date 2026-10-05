package Controller;

import Model.GameModel;
import Model.GameState;
import Model.Player;
import View.AudioManager;
import View.GamePanel;
import View.LayoutManager;
import View.Window;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.*;

public class GameController {

    private GameModel model;
    private LayoutManager view;
    private KeyController keyController;
    private Timer gameloop;



    public GameController(GameModel model, LayoutManager view) {

        this.model = model;
        this.view = view;
        this.keyController = new KeyController();

        initGameloop();
    }


    //Inizializza il timer principale che gestisce gli update a 60 FPS (circa 16ms).
    private void initGameloop() {

        gameloop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
                model.update();

//                }
                //la view si aggiorna in automatico con observer/observable, non c'è bisogno di usare setChanged() etc.
                if (model.getCurrentState() == GameState.GAME_OVER) {
                    AudioManager.getInstance().playMusic(6);
                    stopGame();
                    view.switchScreen(Window.GAMEOVER);
                }

                if (model.getCurrentState() == GameState.VICTORY) {
                    stopGame();
                    view.getVictoryPanel().startAnimation();
                    view.switchScreen(Window.VITTORIA);
                }
            }
        });
    }

    // Avvia l'esecuzione continua del game loop.
    public void startGame() {
        gameloop.start();
    }

    // Ferma l'esecuzione del game loop.
    public void stopGame() {
        gameloop.stop();
    }

    // Traduce l'input utente intercettato in azioni nel GameModel.
    private void processInput() {
        if (model.getCurrentState() != GameState.PLAYING) return;

        Player player = model.getPlayer();

        // calcolo del movimento a 4 direzioni (Stile Zelda classico)
        double dx = 0;
        double dy = 0;

        if (keyController.up) {
            dy = -1;
        } else if (keyController.down) {
            dy = 1;
        } else if (keyController.left) {
            dx = -1; // Corretto da dy a dx!
        } else if (keyController.right) {
            dx = 1;  // Corretto da dy a dx!
        }

        // impostazione direzione (niente diagonali, quindi niente normalizzazione)
        player.setDirection(dx, dy);

        if (keyController.keyBow) {
            AudioManager.getInstance().playSE(7);
            model.shootArrow();
            keyController.keyBow = false; // Forza il rilascio (no mitragliatrice)
        }

        if (keyController.attack) {
            AudioManager.getInstance().playSE(10);
            model.playerAttack();
            keyController.attack = false; // si resetta in automatico per evitare spam
        }

        if (keyController.keyPotion) {
            AudioManager.getInstance().playSE(12);
            model.getPlayer().usePotion();
            model.getCurrentStats().addPotion();
            keyController.keyPotion = false;
        }

        if (keyController.keyBomb) {
            model.placeBomb();
            keyController.keyBomb = false;
        }
    }

    // -- getter --


    public KeyController getKeyController() {
        return keyController;
    }
}
