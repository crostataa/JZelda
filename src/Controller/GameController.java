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
import java.util.Observable;
import java.util.Observer;

/**
 * Controller principale del gioco.
 * Gestisce il game loop (Timer Swing a ~60 FPS), intercetta l'input da tastiera
 * e coordina l'aggiornamento logico del GameModel e le transizioni di schermata (Observer).
 */
public class GameController implements Observer {

    private GameModel model;
    private LayoutManager view;
    private KeyController keyController;
    private Timer gameloop;

    /**
     * Costruisce il controller di gioco associando modello e vista principale.
     * @param model il modello logico del gioco.
     * @param view il gestore del layout grafico.
     */
    public GameController(GameModel model, LayoutManager view) {

        this.model = model;
        this.view = view;
        this.keyController = new KeyController();

        initGameloop();
    }


    /**
     * Inizializza il timer principale che scandisce il game loop a 60 FPS (circa 16ms).
     */
    private void initGameloop() {

        gameloop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
                model.update();
            }
        });
    }

    /**
     * Avvia l'esecuzione continua del game loop.
     */
    public void startGame() {
        gameloop.start();
    }

    /**
     * Ferma l'esecuzione del game loop.
     */
    public void stopGame() {
        gameloop.stop();
    }

    /**
     * Traduce l'input utente intercettato da KeyController in azioni nel GameModel.
     */
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
            model.shootArrow();
            keyController.keyBow = false; // Forza il rilascio (no mitragliatrice)
        }

        if (keyController.attack) {
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


    /**
     * Restituisce il controller dei tasti associato.
     * @return l'istanza di KeyController.
     */
    public KeyController getKeyController() {
        return keyController;
    }

    /**
     * Riceve le notifiche dal modello (Observer).
     * Gestisce gli eventi di Game Over e Vittoria effettuando il cambio di schermata e musica.
     * @param o l'oggetto osservabile (GameModel).
     * @param arg l'argomento notificato (es. "GAME_OVER" o "VICTORY").
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String message = (String) arg;
            if (message.equals("GAME_OVER")) {
                AudioManager.getInstance().playMusic(6);
                stopGame();
                view.switchScreen(Window.GAMEOVER);
            } else if (message.equals("VICTORY")) {
                stopGame();
                view.getVictoryPanel().startAnimation();
                view.switchScreen(Window.VITTORIA);
            }
        }
    }
}
