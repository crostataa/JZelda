package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Pannello grafico mostrato in caso di sconfitta (Game Over).
 * Offre i pulsanti RETRY (per ricominciare una partita con lo stesso profilo) ed EXIT (per tornare al menu).
 */
public class GameOverPanel extends JPanel {

    private JTextField gameOverfield;
    private JButton menuButton;
    private JButton continueButton;



    /**
     * Costruisce il pannello di Game Over inizializzando i componenti e i relativi pulsanti.
     */
    public GameOverPanel() {

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;


        // Titolo
        JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(FontManager.getFont(24f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel, gbc);


        //bottone continua
        gbc.gridy++;
        continueButton = new JButton("RETRY");
        continueButton.setFont(FontManager.getFont(24f));
        continueButton.setBackground(Color.DARK_GRAY);
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        this.add(continueButton, gbc);

        //bottinua esci/menu
        gbc.gridy++;
        menuButton = new JButton("EXIT");
        menuButton.setFont(FontManager.getFont(24f));
        menuButton.setBackground(Color.DARK_GRAY);
        menuButton.setForeground(Color.WHITE);
        menuButton.setFocusPainted(false);
        this.add(menuButton, gbc);

    }



    //-- getter --
    public JButton getMenuButton() { return menuButton; }
    public JButton getContinueButton() { return  continueButton; }

}
