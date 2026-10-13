package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {

    private JTextField nameField;
    private JButton startButton;
    private JButton leaderboardButton;
    private JButton skinButton;

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(320, 10, 10, 10);

        // Label Nome Utente
        JLabel nameLabel = new JLabel("Inserisci il tuo nome:");
        nameLabel.setForeground(new Color(1, 1, 2));
        nameLabel.setFont(FontManager.getFont(24f));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameLabel, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);

        // Campo di testo per il nome
        gbc.gridy++;
        nameField = new JTextField(15);
        nameField.setFont(FontManager.getFont(24f));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameField, gbc);

        // Bottone Start
        gbc.gridy++;
        startButton = new JButton("START GAME");
        startButton.setFont(FontManager.getFont(24f));
        startButton.setBackground(new Color(1, 1, 2));
        startButton.setForeground(new Color(149, 83, 2));
        startButton.setFocusPainted(false);
        this.add(startButton, gbc);

        // Bottone Classifica (Leaderboard)
        gbc.gridy++;
        leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.setFont(FontManager.getFont(24f));
        leaderboardButton.setBackground(new Color(1, 1, 2));
        leaderboardButton.setForeground(new Color(149, 83, 2));
        leaderboardButton.setFocusPainted(false);
        this.add(leaderboardButton, gbc);

        //bottone per cambiare avatar
        gbc.gridy++;
        skinButton = new JButton("AVATAR");
        skinButton.setFont(FontManager.getFont(24f));
        skinButton.setBackground(new Color(1, 1, 2));
        skinButton.setForeground(new Color(149, 83, 2));
        skinButton.setFocusPainted(false);
        this.add(skinButton, gbc);

    }
        // sfondo
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            BufferedImage bg = SpriteManager.getInstance().getSprite("/assets/menuDef.png");
            if (bg != null) {
                g2d.drawImage(bg, 0,0, getWidth(), getHeight(), null);
            }
    }

    // Getter per permettere al Controller di leggere i dati e aggiungere i Listener
    public String getPlayerName() { return nameField.getText(); }
    public JButton getStartButton() { return startButton; }
    public JButton getLeaderboardButton() { return leaderboardButton; }
    public AbstractButton getSkinButton() {return skinButton;}
}