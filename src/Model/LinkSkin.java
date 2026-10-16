package Model;

/**
 * Enumerazione per le skin grafiche di Link (Verde, Blu, Rosa).
 * Fornisce i percorsi delle risorse grafiche (.png) corrispondenti a ciascuna direzione
 * e animazione di attacco per la skin attiva.
 */
public enum LinkSkin {
    /** Skin classica Verde (predefinita). */
    VERDE(""),     // Basic, senza suffisso
    /** Skin Blu. */
    BLU("Blue"),   // basicLinkBlue...
    /** Skin Rosa. */
    ROSA("Pink");  // basicLinkPink...

    private final String suffix;

    LinkSkin(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Restituisce il suffisso utilizzato nei nomi dei file degli asset grafici.
     * @return la stringa di suffisso (es. "Blue", "Pink" o vuota).
     */
    public String getSuffix() { return suffix; }

    /**
     * Restituisce il percorso dello sprite rivolto verso il basso.
     * @param frame numero di frame dell'animazione (1 o 2).
     * @return percorso dell'immagine di risorsa.
     */
    public String getSpriteDown(int frame) {
        return "/assets/basicLink" + suffix + frame + "New.png";
    }

    // basicLink 3 e 4 (Guarda di Lato)
    public String getSpriteSide(int frame) {
        // Se il GamePanel passa frame 1 o 2, calcoliamo 3 o 4
        int actualFrame = frame + 2;
        return "/assets/basicLink" + suffix + actualFrame + "New.png";
    }

    // basicLink 5 e 6 (Guarda in Alto)
    public String getSpriteUp(int frame) {
        // Se il GamePanel passa frame 1 o 2, calcoliamo 5 o 6
        int actualFrame = frame + 4;
        return "/assets/basicLink" + suffix + actualFrame + "New.png";
    }

    // spadaLink 1 (Attacco Laterale)
    public String getAttackSide() {
        return "/assets/spadaLink" + suffix + "1New.png";
    }

    // spadaLink 2 (Attacco in Basso)
    public String getAttackDown() {
        return "/assets/spadaLink" + suffix + "2New.png";
    }

    // spadaLink 3 (Attacco in Alto)
    public String getAttackUp() {
        return "/assets/spadaLink" + suffix + "3New.png";
    }
}