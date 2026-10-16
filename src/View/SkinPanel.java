package View;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello grafico dedicato alla selezione della skin (avatar) di Link.
 * Presenta le anteprime visive per le varianti Verde, Blu e Rosa e una label
 * con lo stato della selezione corrente.
 */
public class SkinPanel extends JPanel {

    private JButton btnVerde, btnBlu, btnRosa, backButton;
    private JLabel statusLabel;

    /**
     * Costruisce il pannello di selezione delle skin, assemblando pulsanti, anteprime e layout.
     */
    public SkinPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        JLabel title = new JLabel("AVATAR SELECTION");
        title.setForeground(new Color(149, 83, 2));
        title.setFont(FontManager.getFont(36f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        this.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        centerPanel.setBackground(new Color(1, 1, 2));

        btnVerde = createSkinButton("/assets/basicLink1New.png", "VERDE");
        btnBlu = createSkinButton("/assets/basicLinkBlue1New.png", "BLU");
        btnRosa = createSkinButton("/assets/basicLinkPink1New.png", "ROSA");


        centerPanel.add(btnVerde);
        centerPanel.add(btnBlu);
        centerPanel.add(btnRosa);
        this.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);

        statusLabel = new JLabel("Skin Selezionata: VERDE");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(FontManager.getFont(18f));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        bottomPanel.add(statusLabel, BorderLayout.NORTH);

        backButton = new JButton("BACK TO MENU");
        backButton.setFont(FontManager.getFont(24f));
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        bottomPanel.add(backButton, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    private JButton createSkinButton(String iconPath, String name) {
        JButton btn = new JButton(name);
        btn.setFont(FontManager.getFont(16f));
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 150));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);

        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
            btn.setIcon(new ImageIcon(img));
        }
        return btn;
    }

    public void updateStatus(String skinName) { statusLabel.setText("Skin Selezionata: " + skinName); }
    public JButton getBtnVerde() { return btnVerde; }
    public JButton getBtnBlu() { return btnBlu; }
    public JButton getBtnRosa() { return btnRosa; }
    public JButton getBackButton() { return backButton; }
}
