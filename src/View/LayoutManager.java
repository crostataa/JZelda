package View;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra principale dell'applicazione (JFrame) e gestore delle schermate grafiche (CardLayout).
 * Gestisce l'aggregazione di tutti i pannelli (Menu, Gioco, HUD, Game Over, Vittoria, Statistiche, Skin),
 * applica il pattern Singleton e coordina la transizione tra le diverse schermate (switchScreen).
 */
public class LayoutManager extends JFrame{

  public static LayoutManager instance;

  CardLayout cardLayout;
  JPanel mainPanel;


  GamePanel game;
  MenuPanel menu;
  HUDPanel hudPanel;
  GameOverPanel gameover;
  VictoryPanel victoryPanel;
  StatistichePanel statistiche;
  SkinPanel skin;



  //la finestra è 756 x 528

  /**
   * Costruttore privato del Singleton: inizializza il JFrame, configura i pannelli e imposta la schermata iniziale.
   */
  private LayoutManager() {
      // 1. Inizializzo i pannelli principali
      this.mainPanel = new JPanel();
      this.cardLayout = new CardLayout();
      this.mainPanel.setLayout(cardLayout); // Il mainPanel usa SOLO il CardLayout

      this.hudPanel = new HUDPanel();
      this.game = new GamePanel();
      this.menu = new MenuPanel();
      this.gameover = new GameOverPanel();
      this.victoryPanel = new VictoryPanel();
      this.skin = new SkinPanel();

      // 2. Imposto le dimensioni corrette
      this.hudPanel.setPreferredSize(new Dimension(768, 192));
      this.game.setPreferredSize(new Dimension(768, 512));
    //totale: 768 x 704
      // 3. WRAPPER PER IL GIOCO (Unisce HUD e MAPPA)
      JPanel gameContainer = new JPanel();
      gameContainer.setLayout(new BorderLayout());
      gameContainer.add(hudPanel, BorderLayout.NORTH);
      gameContainer.add(game, BorderLayout.CENTER);

      // 4. Aggiungo le "Carte" al mainPanel
      mainPanel.add(menu, Window.MENU.name());
      mainPanel.add(gameContainer, Window.GIOCO.name());
      mainPanel.add(gameover, Window.GAMEOVER.name());
      mainPanel.add(victoryPanel, Window.VITTORIA.name());
      mainPanel.add(skin, Window.SKIN.name());

     statistiche = new StatistichePanel();
     mainPanel.add(statistiche, Window.STATISTICHE.name());



      // 5. Configuro la Finestra (JFrame)
      this.setTitle("The Legend of Zelda");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(mainPanel);
      this.setResizable(false);
      this.pack(); // Ora calcolerà (768 x (192+512)) = 768x704 pixel

      this.setLocationRelativeTo(null); // Centra la finestra
      this.getContentPane().setBackground(Color.BLACK);

      // Icona di gioco (con controllo di sicurezza per evitare null pointer)
      String icon = "/assets/logo.jpeg";
      java.net.URL imgURL = getClass().getResource(icon);
      if (imgURL != null) {
          ImageIcon image = new ImageIcon(imgURL);
          this.setIconImage(image.getImage());
      }

      this.setVisible(true);
      switchScreen(Window.MENU);
  }

      /**
       * Cambia la schermata visibile visualizzando la carta associata alla costante Window indicata.
       * @param finestra l'identificatore della schermata da visualizzare.
       */
      public void switchScreen(Window finestra) {

      cardLayout.show(mainPanel, finestra.name());

      if (finestra == Window.GIOCO) {
          game.requestFocusInWindow();
      }
    }

    /**
     * Restituisce l'istanza unica di LayoutManager (Singleton).
     * @return l'istanza del LayoutManager.
     */
    public static LayoutManager getInstance() {

      if (instance == null)  {
          instance = new LayoutManager();
      }
      return instance;
    }

    // -- getter --
    public GamePanel getGamePanel() { return game; }
    public MenuPanel getMenuPanel() { return menu; }
    public HUDPanel getHUDPanel() { return hudPanel; }
    public GameOverPanel getGameOverPanel() { return gameover; }
    public VictoryPanel getVictoryPanel() { return victoryPanel; }
    public StatistichePanel getStatistichePanel() { return statistiche; }

    public SkinPanel getSkinPanel() {
      return skin;
    }
}
