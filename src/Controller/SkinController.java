package Controller;

import Model.GameModel;
import Model.LinkSkin;
import View.LayoutManager;
import View.SkinPanel;
import View.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkinController {

    private GameModel model;
    private LayoutManager viewManager;

    public SkinController(GameModel model, LayoutManager viewManager) {
        this.model = model;
        this.viewManager = viewManager;

        SkinPanel skinView = this.viewManager.getSkinPanel();

        // Listener per la selezione della Skin VERDE
        skinView.getBtnVerde().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSelectedSkin(LinkSkin.VERDE);
                skinView.updateStatus("VERDE");
            }
        });

        // Listener per la selezione della Skin BLU
        skinView.getBtnBlu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSelectedSkin(LinkSkin.BLU);
                skinView.updateStatus("BLU");
            }
        });

        // Listener per la selezione della Skin ROSA
        skinView.getBtnRosa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSelectedSkin(LinkSkin.ROSA);
                skinView.updateStatus("ROSA");
            }
        });

        // Listener per il pulsante Indietro (torna al Menu)
        skinView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManager.switchScreen(Window.MENU);
            }
        });
    }
}