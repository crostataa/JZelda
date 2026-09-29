package Model;

public class PlayerStats {

    private String username;
    private int arrowsShot;
    private int moblinsKilled;
    private int octoroksKilled;
    private int totalRupees;
    private int potionsUsed;
    private int bombsUsed;
    private int gamesWon;


    public PlayerStats(String username) {
        this.username = username;
    }

    //incremento statistiche

    public void addArrow() { arrowsShot++; }
    public void addMoblin() { moblinsKilled++; }
    public void addOctorok() { octoroksKilled++; }
    public void addRupees(int amount) { totalRupees += amount; }
    public void addPotion() { potionsUsed++; }
    public void addBomb() { bombsUsed++; }
    public void addWin() { gamesWon++; }


    //getter e setter

    public String getUsername() { return username; }

    public int getArrowsShot() { return arrowsShot; }
    public void setArrowsShot(int arrowsShot) { this.arrowsShot = arrowsShot; }

    public int getMoblinsKilled() { return moblinsKilled; }
    public void setMoblinsKilled(int moblinsKilled) { this.moblinsKilled = moblinsKilled; }

    public int getOctoroksKilled() { return octoroksKilled; }
    public void setOctoroksKilled(int octoroksKilled) { this.octoroksKilled = octoroksKilled; }

    public int getTotalRupees() { return totalRupees; }
    public void setTotalRupees(int totalRupees) { this.totalRupees = totalRupees; }

    public int getPotionsUsed() { return potionsUsed; }
    public void setPotionsUsed(int potionsUsed) { this.potionsUsed = potionsUsed; }

    public int getBombsUsed() { return bombsUsed; }
    public void setBombsUsed(int bombsUsed) { this.bombsUsed = bombsUsed; }

    public int getGamesWon() { return gamesWon; }
    public void setGamesWon(int gamesWon) { this.gamesWon = gamesWon; }
}
