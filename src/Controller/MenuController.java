package Controller;

import Model.GameModel;
import View.LayoutManager;
import View.MenuPanel;
import View.Window;

import java.awt.event.*;

public class MenuController {


    private GameModel model;

    public MenuController(GameModel model, MenuPanel menuPanel) {
        this.model = model;

        menuPanel.getBtnStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //il model avvia la partita
                model.initGame();
                //con switchScreen di LayoutManager cambiamo schermata
                LayoutManager.getInstance().switchScreen(Window.GIOCO);

                // il gameController deve poi avviare il timer del gameLoop*
            }

        });
    }

}
