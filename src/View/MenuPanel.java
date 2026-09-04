package View;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private JTextField nameField;
    private JButton startButton;
    private JButton leaderboardButton;

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;


        // Titolo
        JLabel titleLabel = new JLabel("THE LEGEND OF ZELDA");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(FontManager.getFont(24f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel, gbc);

        // Label Nome Utente
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Inserisci il tuo nome:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(FontManager.getFont(24f));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameLabel, gbc);

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
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        this.add(startButton, gbc);

        // Bottone Classifica (Leaderboard)
        gbc.gridy++;
        leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.setFont(FontManager.getFont(24f));
        leaderboardButton.setBackground(Color.DARK_GRAY);
        leaderboardButton.setForeground(Color.WHITE);
        leaderboardButton.setFocusPainted(false);
        this.add(leaderboardButton, gbc);
    }

    // Getter per permettere al Controller di leggere i dati e aggiungere i Listener
    public String getPlayerName() { return nameField.getText(); }
    public JButton getStartButton() { return startButton; }
    public JButton getLeaderboardButton() { return leaderboardButton; }
}