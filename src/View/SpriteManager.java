package View;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

/**
 * Gestore Singleton per il caricamento, il caching e il ritaglio degli sprite grafici.
 * Memorizza in una mappa le immagini già lette dal disco per evitare accessi I/O ripetuti.
 */
public class SpriteManager {

    // singleton
    private static SpriteManager instance;

    //percorso dell'immmagine mappata
    private Map<String, BufferedImage> mapSprite;

    //costruttore privato x singleton
    private SpriteManager() {
        mapSprite = new HashMap<>();

    }



    // -- getter --

    /**
     * Restituisce l'istanza unica dello SpriteManager (Singleton).
     * @return l'istanza di SpriteManager.
     */
    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }

    /**
     * Carica e memorizza in cache un'immagine dal percorso delle risorse specificato.
     * @param path percorso relativo nel classpath (es. "/assets/immagine.png").
     * @return la {@link BufferedImage} caricata o null in caso di errore.
     */
    public BufferedImage getSprite(String path) {

        //se abbiamo già caricato una volta l'immagine
        if (mapSprite.containsKey(path)) {
            return mapSprite.get(path);
        }

        BufferedImage image = null;
        try {
            InputStream is = getClass().getResourceAsStream(path);

            if (is != null) {
                image = ImageIO.read(is);
                mapSprite.put(path, image);
            } else {
                System.err.print("Errore 404: Immagine non trovata nel percorso '" + path + "'");
            }
        } catch (IOException e) {
            System.err.println("Errore di I/O nel caricamento di '" + path + "'");
            e.printStackTrace();
        }
        return image;
    }


    /**
     * Estrae un sotto-sprite (ritaglio) da un foglio sprite (spritesheet).
     * @param sheetPath percorso dello spritesheet.
     * @param col indice della colonna del frame.
     * @param row indice della riga del frame.
     * @param width larghezza del singolo frame in pixel.
     * @param height altezza del singolo frame in pixel.
     * @return la sotto-immagine estratta.
     */
    public BufferedImage getSubSprite(String sheetPath, int col, int row, int width, int height) {

        BufferedImage sheet = getSprite(sheetPath);

        if (sheet != null) {
            //calcola coordinate per ritaglio
            int x = col * width;
            int y = row * height;
            return sheet.getSubimage(x, y, width, height);
        }
        return null;
    }
}




