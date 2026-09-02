package View;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

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

    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }

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

    //gli sprite sono in formato SpriteSheet, quindi invece di salvare i singoli ritagli in memoria
    // (pesantissimi), ogni volta ritagliamo il pezzo che ci interessa, visto che sono tutti della stessa dimensione
    // e anche perché l'operazione di ritaglio è istantanea


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




