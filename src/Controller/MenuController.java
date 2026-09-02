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

    public MenuController(GameModel model, LayoutManager viewManager) {
        this.model = model;
        this.viewManager = viewManager;

        // Listener per il bottone Start
        this.viewManager.getMenuPanel().getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = viewManager.getMenuPanel().getPlayerName().trim();

                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(viewManager, "Inserisci un nome per iniziare!", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1. Qui salveremo il nome nel Profilo Utente del Model
                System.out.println("Benvenuto, " + playerName);

                // 2. Inizializziamo il gioco
                model.initGame();

                // 3. Cambiamo schermata usando il tuo Enum
                viewManager.switchScreen(Window.GIOCO);
            }
        });

        // Listener per il bottone Leaderboard (da implementare in seguito)
        this.viewManager.getMenuPanel().getLeaderboardButton().addActionListener(e -> {
            System.out.println("Apertura classifica...");
            // viewManager.switchScreen(Window.STATISTICHE);
        });
    }
}