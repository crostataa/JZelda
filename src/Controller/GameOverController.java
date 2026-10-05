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

public class GameOverController {

    private GameModel model;
    private LayoutManager viewManager;
    private GameController gameController;
    private MenuController menuController;


    public GameOverController(GameModel model, LayoutManager viewManager, GameController gameController, MenuController menuController) {

        this.model = model;
        this.viewManager = viewManager;
        this.gameController = gameController;
        this.menuController = menuController;


        //listener per bottone retry
        this.viewManager.getGameOverPanel().getContinueButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //metodo per salvare statistiche su file
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
