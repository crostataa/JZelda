import Controller.GameOverController;
import Controller.MenuController;
import Controller.VictoryController;
import Model.GameModel;
import View.LayoutManager;
import Controller.GameController;

public class JZelda {

    public static void main (String[] args) {

        //inizializziamo model
        GameModel model = new GameModel();

        //inizializziamo view
        LayoutManager viewManager = LayoutManager.getInstance();

        //uniamo model a view
        viewManager.getGamePanel().setModel(model);
        model.addObserver(viewManager.getGamePanel());

        viewManager.getHUDPanel().setModel(model);
        model.addObserver(viewManager.getHUDPanel());


        //inizializziamo controller
        GameController gameController = new GameController(model, viewManager);
        
        //aggiungiamo i nuovi observer
        model.addObserver(gameController);
        model.addObserver(View.AudioManager.getInstance());

        MenuController menuController = new MenuController(model, viewManager, gameController);

        GameOverController gameOverController = new GameOverController(model, viewManager, gameController, menuController);

        VictoryController victoryController = new VictoryController(model, viewManager);

        //uniamo keyListener a gamePanel
        viewManager.getGamePanel().addKeyListener(gameController.getKeyController());

        //avvio gameloop
        gameController.startGame();

        View.AudioManager.getInstance().playMusic(0);

    }

}
