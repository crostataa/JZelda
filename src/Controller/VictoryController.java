package Controller;

import Model.GameModel;
import View.AudioManager;
import View.LayoutManager;
import View.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VictoryController {

    private GameModel model;
    private LayoutManager viewManager;

    public VictoryController(GameModel model, LayoutManager viewManager) {
        this.model = model;
        this.viewManager = viewManager;

        // Listener per il bottone Menu della schermata Vittoria
        this.viewManager.getVictoryPanel().getMenuButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ferma l'audio del finale e fa ripartire il tema del Menu
                AudioManager.getInstance().stopMusic();
                AudioManager.getInstance().playMusic(0);

                // Torna al Menu Principale
                viewManager.switchScreen(Window.MENU);
            }
        });
    }
}