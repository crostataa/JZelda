package View;

import javax.swing.*;
import java.awt.*;

public class LayoutManager extends JFrame{

  public static LayoutManager instance;

  CardLayout cardLayout;
  JPanel mainPanel;

  ShopPanel shop;
  GamePanel game;
  MenuPanel menu;
  /*

  GameOverPanel gameover;
  StatistichePanel statistiche;
  OpzioniPanel opzioni;


  //la finestra è 756 x 528

   */
  private LayoutManager() {
        
      this.mainPanel = new JPanel();
      this.cardLayout = new CardLayout();
      
      this.mainPanel.setLayout(cardLayout);
      this.add(mainPanel);
      
      game = new GamePanel();
      mainPanel.add(game, Window.GIOCO.name());

      shop = new ShopPanel();
      mainPanel.add(shop, Window.SHOP.name());

      menu = new MenuPanel();
      mainPanel.add(menu, Window.MENU.name());


      /*

      gameover = new GameOverPanel();
      mainpanel.add(gameover, Window.GAMEOVER.name());

      statistiche = new StatistichePanel();
      mainpanel.add(statistiche, Window.STATISTICHE.name());

      opzioni = new OpzioniPanel();
      mainpanel.add(opzioni, Window.OPZIONI.name());

       */

      //inizializzo controller ??


      this.setTitle("The Legend of Zelda");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.mainPanel.setPreferredSize(new Dimension(768, 512));
      this.pack();
      this.setLocationRelativeTo(null); //per centrare la finestra
      this.getContentPane().setBackground(Color.BLACK);

      String icon = "/assets/logo.jpeg";
      ImageIcon image = new ImageIcon(getClass().getResource(icon));
      this.setIconImage(image.getImage());

      setVisible(true);

      switchScreen(Window.MENU);

      }

      public void switchScreen(Window finestra) {

      cardLayout.show(mainPanel, finestra.name());

      if (finestra == Window.GIOCO) {
          game.requestFocusInWindow();
      }
    }

    public static LayoutManager getInstance() {

      if (instance == null)  {
          instance = new LayoutManager();
      }
      return instance;
    }

    // -- getter --
    public GamePanel getGamePanel() { return game; }
    public MenuPanel getMenuPanel() { return menu; }
    public ShopPanel getShopPanel() { return shop; }




}
