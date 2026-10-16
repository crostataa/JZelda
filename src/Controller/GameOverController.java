package Controller;

import Model.GameModel;
import View.*;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.MenuController;
import View.LayoutManager;
import View.Window;

/**
 * Controller per la schermata di Game Over.
 * Gestisce i pulsanti per riprovare la partita o tornare al menu principale.
 */
public class GameOverController {

    private GameModel model;
    private LayoutManager viewManager;
    private GameController gameController;
    private MenuController menuController;

    /**
     * Costruisce il controller di Game Over configurando i listener sui pulsanti del pannello.
     * @param model il modello logico del gioco.
     * @param viewManager il gestore delle schermate.
     * @param gameController il controller del gioco.
     * @param menuController il controller del menu per recuperare il nome del giocatore.
     */
    public GameOverController(GameModel model, LayoutManager viewManager, GameController gameController, MenuController menuController) {

        this.model = model;
        this.viewManager = viewManager;
        this.gameController = gameController;
        this.menuController = menuController;


        //listener per bottone retry
        this.viewManager.getGameOverPanel().getContinueButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.initGame(menuController.getPlayerName());
                gameController.startGame();

                //la musica deve cominciare da capo
                AudioManager.getInstance().stopMusic();
                AudioManager.getInstance().playMusic(1);

                viewManager.switchScreen(Window.GIOCO);
            }
        });


    //listener per bottone exit
    this.viewManager.getGameOverPanel().getMenuButton().addActionListener( new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

        AudioManager.getInstance().stopMusic();
        AudioManager.getInstance().playMusic(0);
        viewManager.switchScreen(Window.MENU);
    }
    });
    }
}
