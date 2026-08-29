package Controller;

import Model.GameModel;
import Model.GameState;
import Model.Player;
import View.GameWindow;

import javax.swing.*;
import java.awt.event.*;

public class GameController {

    private GameModel model;
    private GameWindow view;
    private KeyController keyController;
    private Timer gameloop;


    public GameController(GameModel model, GameWindow view) {

        this.model = model;
        this.view = view;
        this.keyController = new KeyController();

        //il keyController andrà collegato alla finestra grafica

        initGameloop();
    }


    private void initGameloop() {

        gameloop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();

                //aggiorna la logica e calcola le collisioni
                model.update();

                //la view si aggiorna in automatico con observer/observable

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

        //calcolo del movimento (supporta le diagonali)
        double dx = 0;
        double dy = 0;

        if (keyController.up) dy = -1;
        if (keyController.down) dy = 1;
        if (keyController.left) dy = -1;
        if (keyController.right) dy = 1;

        //normalizza la diagonale per non muoversi più velocemente
        if (dx != 0 && dy !=0) {
            double lenght = Math.hypot(dx, dy);
            dx /= lenght;
            dy /= lenght;
        }

        //imposta direzione
        player.setDirection(dx, dy);

        //gestione azioni

        if (keyController.attack) {
            //qui si richiama il metodo per attaccare
            System.out.print("spadata");
            keyController.attack = false; //si resetta
        }

        //gestione oggetti

        if (keyController.useItem) {
            if (!player.getInventory().isEmpty()) {
                String itemName = player.getInventory().getFirst().getName();
                player.useItem(itemName);
            }
            keyController.useItem = false;
        }
    }




}
