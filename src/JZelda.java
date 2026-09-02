import Controller.MenuController;
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

//        model.addObserver(viewManager.getHUDPanel());
//        model.addObserver(viewManager.getShopanel());


        //inizializziamo controller
        GameController gameController = new GameController(model, viewManager);

        MenuController menuController = new MenuController(model, viewManager);

        //uniamo keyListener a gamePanel
        viewManager.getGamePanel().addKeyListener(gameController.getKeyController());

        //avvio gameloop
        gameController.startGame();

    }

}
