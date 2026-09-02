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

        /*
        mod
        el.addObserver(viewManager.getHUDPanel());
        model.addObserver(viewManager.getShopanel());
         */

        //inizializziamo controller
        GameController controller = new GameController(model, viewManager);

        //uniamo keyListener a gamePanel
        viewManager.getGamePanel().addKeyListener(controller.getKeyController());

        //avvio gameloop
        controller.startGame();

    }

}
