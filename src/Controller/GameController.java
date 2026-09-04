package Controller;

import Model.GameModel;
import Model.GameState;
import Model.Player;
import View.GamePanel;
import View.LayoutManager;
import View.Window;

import javax.swing.*;
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


    private void initGameloop() {

        gameloop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();

                //aggiorna la logica e calcola le collisioni
                model.update();

                //controllo gameover
                if (model.getPlayer() != null && model.getPlayer().getHp() <= 0) {
                    model.setCurrentState(GameState.GAME_OVER);
                    stopGame();
                    view.switchScreen(Window.GAMEOVER);
                }
                //la view si aggiorna in automatico con observer/observable, non c'è bisogno di usare setChanged() etc.

            }
        });
    }

    public void startGame() {
        gameloop.start();
    }

    public void stopGame() {
        gameloop.stop();
    }

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


        // -- gestione attacchi --

        if (keyController.keyBow) {
            model.shootArrow();
            keyController.keyBow = false; // Forza il rilascio (no mitragliatrice)
        }

        if (keyController.attack) {
            model.playerAttack();
            keyController.attack = false; // si resetta in automatico per evitare spam
        }

        // -- gestione oggetti --

        if (keyController.keyPotion) {
            model.getPlayer().usePotion();
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
