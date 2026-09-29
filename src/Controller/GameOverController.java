package Controller;

import Model.GameModel;
import View.GameOverPanel;
import View.LayoutManager;
import View.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.MenuController;

public class GameOverController {

    private GameModel model;
    private LayoutManager viewManager;
    private GameController gameController;
    private MenuController menuController;


    public GameOverController(GameModel model, LayoutManager viewManager, GameController gameController) {

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
                viewManager.switchScreen(Window.GIOCO);
            }
        });


    //listener per bottone exit
    this.viewManager.getGameOverPanel().getMenuButton().addActionListener( new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        viewManager.switchScreen(Window.MENU);
    }
    });
    }
}
