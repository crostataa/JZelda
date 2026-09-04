package Controller;

import Model.GameModel;
import View.GameOverPanel;
import View.LayoutManager;
import View.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverController {

    private GameModel model;
    private LayoutManager viewManager;
    private GameController gameController;


    public GameOverController(GameModel model, LayoutManager viewManager) {

        this.model = model;
        this.viewManager = viewManager;
        this.gameController = new GameController(model, viewManager);


        //listener per bottone retry
        this.viewManager.getGameOverPanel().getContinueButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //metodo per salvare statistiche su file
                model.initGame();
                gameController.startGame();
                //non funziona keyListener però, risolvere*
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
