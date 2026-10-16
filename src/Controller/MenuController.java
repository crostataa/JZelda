package Controller;

import Model.GameModel;
import View.AudioManager;
import View.LayoutManager;
import View.Window;
import javax.swing.JOptionPane;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller per il menu principale di avvio.
 * Gestisce l'inserimento del nickname utente, l'avvio della partita,
 * la navigazione verso il pannello delle statistiche e verso la selezione delle skin.
 */
public class MenuController {

    private GameModel model;
    private LayoutManager viewManager;
    private GameController gameController;

    public String playerName;

    /**
     * Costruisce il MenuController collegando i listener ai pulsanti di Start, Statistiche e Skin.
     * @param model il modello logico del gioco.
     * @param viewManager il gestore delle schermate della vista.
     * @param gameController il controller del gioco.
     */
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

                //stop audio menu e start audio gioco
                AudioManager.getInstance().stopMusic();
                AudioManager.getInstance().playMusic(1);

                //cambio schermata - passiamo al gioco
                viewManager.switchScreen(Window.GIOCO);
            }
        });

        // Listener per il bottone Leaderboard
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

        //listener per bottone skin
        this.viewManager.getMenuPanel().getSkinButton().addActionListener(e -> {
            // Cambia schermata passando al pannello delle skin
            viewManager.switchScreen(Window.SKIN);
        });
    }

    /**
     * Restituisce il nickname inserito dal giocatore.
     * @return il nome del giocatore.
     */
    public String getPlayerName() { return playerName ;}
}