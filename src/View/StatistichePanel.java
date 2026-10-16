package View;
import Model.PlayerStats;
import Model.StatsManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello per la consultazione della classifica e delle statistiche storiche dei giocatori.
 * Visualizza in una tabella formattata i dati letti dal file persistente e include il pulsante di ritorno.
 */
public class StatistichePanel extends JPanel {

    private JTextArea statsArea;
    private JButton backButton;

    /**
     * Costruisce il pannello delle statistiche configurando area di testo e pulsante di ritorno.
     */
    public StatistichePanel() {
            this.setLayout(new BorderLayout());
            this.setBackground(Color.BLACK);

            // 1. TITOLO
            JLabel titleLabel = new JLabel("LEADERBOARD");
            titleLabel.setForeground(Color.YELLOW);
            titleLabel.setFont(FontManager.getFont(36f));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
            this.add(titleLabel, BorderLayout.NORTH);

            // 2. AREA DI TESTO PER LA CLASSIFICA
            statsArea = new JTextArea();
            statsArea.setBackground(Color.BLACK);
            statsArea.setForeground(Color.WHITE);
            statsArea.setFont(FontManager.getFont(16f)); // Un po' più piccolo per farci stare le colonne
            statsArea.setEditable(false);
            statsArea.setFocusable(false);
            statsArea.setMargin(new Insets(10, 40, 10, 40));

            // Inseriamo l'area in uno ScrollPane (con barre invisibili) per sicurezza
            JScrollPane scrollPane = new JScrollPane(statsArea);
            scrollPane.getViewport().setBackground(Color.BLACK);
            scrollPane.setBorder(null);
            this.add(scrollPane, BorderLayout.CENTER);

            // 3. BOTTONE INDIETRO
            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.BLACK);
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

            backButton = new JButton("BACK TO MENU");
            backButton.setFont(FontManager.getFont(24f));
            backButton.setBackground(Color.DARK_GRAY);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            bottomPanel.add(backButton);

            this.add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * Ricarica e formatta a schermo la classifica dei giocatori leggendo i dati aggiornati tramite {@link StatsManager}.
         */
        public void updateStats() {
            statsArea.setText(""); // Pulisce il testo precedente
            List<PlayerStats> leaderboard = StatsManager.getInstance().getLeaderboard();

            if (leaderboard.isEmpty()) {
                statsArea.append("\nNessuna statistica trovata.\nGioca una partita per iniziare!");
                return;
            }

            // Intestazione delle colonne
            statsArea.append(String.format("%-10s | %-4s | %-6s | %-6s | %-6s | %-5s | %-5s\n",
                    "NOME", "VINT", "RUPIE", "FRECCE", "MOBLIN", "OCTO", "BOMBE"));
            statsArea.append("-------------------------------------------------------------\n");

            // Stampa ogni giocatore
            for (PlayerStats p : leaderboard) {
                statsArea.append(String.format("%-10s | %-4d | %-6d | %-6d | %-6d | %-5d | %-5d\n",
                        p.getUsername(),
                        p.getGamesWon(),
                        p.getTotalRupees(),
                        p.getArrowsShot(),
                        p.getMoblinsKilled(),
                        p.getOctoroksKilled(),
                        p.getBombsUsed()));
            }
        }

        public JButton getBackButton() { return backButton; }
    }


