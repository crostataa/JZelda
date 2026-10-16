package View;

import java.awt.*;
import java.io.*;

/**
 * Gestore centralizzato per il caricamento e il caching del font retrò ("joystix monospace").
 */
public class FontManager {

    private static Font zeldaFont;

    public FontManager() {

    }

    /**
     * Restituisce il font personalizzato alla dimensione specificata, con fallback su Monospaced.
     * @param size la dimensione del font (in punti).
     * @return l'oggetto {@link Font} scalato alla dimensione richiesta.
     */
    public static Font getFont(float size) {
        if (zeldaFont == null) {
            try {
                //carica font
                InputStream is = FontManager.class.getResourceAsStream("/assets/joystix monospace.otf");

                if (is != null) {
                    zeldaFont = Font.createFont(Font.TRUETYPE_FONT, is);
                    //registra font in ambiente grafico
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(zeldaFont);
                } else {
                    System.err.println("errore 404 font non trovato");
                    zeldaFont = new Font("Monospaced", Font.BOLD, 12);
                }
            } catch (Exception e) {
                System.err.println("errore caricamento font");
                e.printStackTrace();
                zeldaFont = new Font("Monospaced", Font.BOLD, 12);

            }
        }
        return zeldaFont.deriveFont(size);



    }
}