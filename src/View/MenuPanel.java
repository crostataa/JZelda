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
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel, gbc);

        // Label Nome Utente
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Inserisci il tuo nome:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameLabel, gbc);

        // Campo di testo per il nome
        gbc.gridy++;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Monospaced", Font.PLAIN, 18));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameField, gbc);

        // Bottone Start
        gbc.gridy++;
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        this.add(startButton, gbc);

        // Bottone Classifica (Leaderboard)
        gbc.gridy++;
        leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.setFont(new Font("Monospaced", Font.BOLD, 16));
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