package Controller;

import Model.GameModel;
import View.LayoutManager;
import View.Window;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {

    private GameModel model;
    private LayoutManager viewManager;
    private GameController gameController;

    public String playerName;

    public MenuController(GameModel model, LayoutManager viewManager, GameController gameController) {
        this.model = model;
        this.viewManager = viewManager;
        this.gameController = gameController;

        // Listener per il bottone Start
        this.viewManager.getMenuPanel().getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = viewManager.getMenuPanel().getPlayerName().trim();

                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(viewManager, "Inserisci un nome per iniziare!", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //inizializziamo il gioco
                model.initGame(playerName);
                gameController.startGame();

                //cambio schermata - passiamo al gioco
                viewManager.switchScreen(Window.GIOCO);
            }
        });

        // Listener per il bottone Leaderboard (da implementare in seguito)
        this.viewManager.getMenuPanel().getLeaderboardButton().addActionListener(e -> {
            //leggi dati aggiornati da file
            viewManager.getStatistichePanel().updateStats();
            //cambia schermata
            viewManager.switchScreen(Window.STATISTICHE);
        });

        viewManager.getStatistichePanel().getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManager.switchScreen(Window.MENU);
            }
        });
    }

    //getter
    public String getPlayerName() { return playerName ;}
}