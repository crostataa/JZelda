package Model;

public enum LinkSkin {
    VERDE(""),     // Basic, senza suffisso
    BLU("Blue"),   // basicLinkBlue...
    ROSA("Pink");  // basicLinkPink...

    private final String suffix;

    LinkSkin(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() { return suffix; }

    // basicLink 1 e 2 (Guarda in Basso)
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