package Model;


import java.awt.Rectangle;

/**
 * Classe astratta di base per tutte le entità del mondo di gioco.
 * Gestisce coordinate, dimensioni, velocità, vettori di direzione, stato vitale
 * e calcolo delle hitbox di collisione.
 */
public abstract class Entity {
    // Coordinate e dimensioni
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    // Attributi fisici
    protected double speed;
    protected boolean isAlive;

    //animazione spostamento
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    //tutte le entità devono avere una direzione
    protected double dx, dy;

    /**
     * Costruisce una nuova entità specificandone coordinate iniziali, dimensioni e velocità.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     * @param width larghezza in pixel.
     * @param height altezza in pixel.
     * @param speed velocità di movimento per tick.
     */
    public Entity(double x, double y, int width, int height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isAlive = true;
        this.dx = 0;
        this.dy = 0;
    }



    /**
     * Viene chiamato dal Model a ogni frame per aggiornare logiche specifiche (es. animazioni o posizioni).
     */
    public abstract void update();

    /**
     * Restituisce l'area di collisione attuale dell'entità per i calcoli fisici.
     * @return il rettangolo dell'hitbox corrente.
     */
    public Rectangle getHitbox() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    /**
     * Calcola la posizione futura in base a velocità e direzione per prevenire compenetrazioni (muri).
     * @return il rettangolo dell'hitbox prevista nel prossimo frame.
     */
    public Rectangle getPredictedHitbox() {
        int nextX = (int) (x + dx * speed);
        int nextY = (int) (y + dy * speed);
        return new Rectangle(nextX, nextY, width, height);

    }

    /**
     * Cambia i vettori di direzione corrente.
     * @param dx componente orizzontale della direzione (-1, 0, 1).
     * @param dy componente verticale della direzione (-1, 0, 1).
     */
    public void setDirection(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }


    // -- getter e setter --
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { this.isAlive = alive; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }

    public int getSpriteNum() {return spriteNum;}
}