package Model;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameModel extends Observable {

    //stato del gioco
    private GameState currentState;
    private int currentLevel;
    private int score;

    //entità
    private Player player;
    private List<Enemy> enemies;
    List<Projectile> projectiles;
    List<Rupee> rupees;

    //shop
    private ShopModel shop;

    //limiti di schermo
    private final Rectangle screenBounds = new Rectangle(0,0, 800, 600); //da modificare dopo sprite

    //mappa
    private Level currentLevelMap;
    private int currentLevelId;


    public GameModel() {

        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        rupees = new ArrayList<>();
        this.shop = new ShopModel();
        initGame();

    }

    //inizializza o resetta una partita

    public void initGame() {
        this.currentState = GameState.PLAYING;
        this.currentLevel = 1;
        this.score = 0;

        //posizione iniziale del giocatore, al centro dello schermo *
        this.player = new Player(100, 100);

        //il primo livello NON HA NEMICI
        enemies.clear();


        // Posiziona il giocatore in base alla mappa
        this.player = new Player(currentLevelMap.getPlayerStartX(), currentLevelMap.getPlayerStartY());

        // Carica i nemici previsti dal livello
        this.enemies = currentLevelMap.getInitialEnemies();

        projectiles.clear();
        rupees.clear();

        //li mettiamo come test
        enemies.add(new MoblinEnemy(300, 400));
        enemies.add(new OctorokEnemy(400,100));
        rupees.add(new Rupee(200, 300,1)); //rupia verde
        rupees.add(new Rupee(250, 350, 5)); //rupia blu




        //notifica alla View in ascolto che il gioco è stato resettato
        setChanged();
        notifyObservers("GAME_RESET");

    }

    public void changeLevel(int newLevelId, int startX, int startY) {
        this.currentLevelId = newLevelId;

        //riposiziona giocatore all'ingresso della nuova zona
        this.player.setX(startX);
        this.player.setY(startY);

        //svuota proiettili e carica i nuovi nemici specifici del livello
        this.projectiles.clear();
        loadLevelEntities(newLevelId);
    }





    //metodo per acquistare da shop
    public void attemptPurchase(String itemName) {
        if (currentState == GameState.SHOP) {
            boolean success = shop.buyItem(player, itemName);

            if (success) {
                //notifica la view
                setChanged();
                notifyObservers("SHOP_PURCHASE_SUCCESS");
            }
            else {
                setChanged();
                notifyObservers("SHOP_PURCHASE_FAILED");
            }
        }
    }



    // metodo x sparare

    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    //nemici nascono in posizioni diverse in base al livello
    private void loadLevelEntities(int levelId) {
        //cancella entità livello precedente
        enemies.clear();
        rupees.clear();
        projectiles.clear();

        //spawna nemici
        if (levelId <= 8) {
            //coordinate da modificare*
            enemies.add(new MoblinEnemy(300,400));
            enemies.add(new OctorokEnemy(400,200));

            rupees.add(new Rupee(150, 150, 1));

        } else if (levelId == 9) {
            currentState = GameState.SHOP;
        } else if (levelId == 10) {
            //moblin pacifico
        } else if (levelId == 11) {
            //cassa con spada dell'eroe
        }
    }




    // -- metodi x controller --
    public void update() {
        if (currentState != GameState.PLAYING) return;

        //1. aggiorna il giocatore
        //player.update();

        currentLevelMap.getTiles().stream()
                .filter(tile -> tile.getType() == TileType.DOOR)
                .filter(tile -> tile.getHitbox().intersects(player.getHitbox()))
                .findFirst()
                .ifPresent(cave -> {
                    //in base al livello da cui si accede, si andrà in una caverna diversa
                    //modificare*
                    if (currentLevelId == 2) changeLevel(6, 350, 400); //shop
                    else if(currentLevelId == 3) changeLevel(7, 350, 400);
                    else if (currentLevelId == 4) changeLevel(8, 350, 400);
                });

        //controllo collisioni player vs muri
        boolean playerHitWall = currentLevelMap.getSolidTiles()
                        .anyMatch(tile -> tile.getHitbox().intersects(player.getPredictedHitbox()));
        if(!playerHitWall) {
            player.update();
        } else {
            player.setDirection(0,0);
        }

        //2. aggiorna i nemici usando gli stream
        enemies.forEach(enemy -> {
            enemy.updateBehavior(this);
            boolean enemyHitWall = currentLevelMap.getSolidTiles()
                    .anyMatch(tile -> tile.getHitbox().intersects(enemy.getPredictedHitbox()));
            if (!enemyHitWall) {
                enemy.update();
            }
        });


        projectiles.forEach(p -> {
            boolean projHitWall = currentLevelMap.getSolidTiles()
                    .anyMatch(tile -> tile.getHitbox().intersects(p.getPredictedHitbox()));
            if (!projHitWall) {
                p.update();
            } else {
                p.setAlive(false); //il colpo si infrange sull'entità colpita
            }
        });

        //3. controllo collisioni (player vs nemici) usando gli stream
        enemies.stream()
                .filter(enemy -> enemy.getHitbox().intersects(player.getHitbox()))
                .forEach(enemy -> player.takeDamage(enemy.getDamage()));



        //4. logica dei proiettili nemici vs player
        projectiles.stream()
                        .filter(p -> !p.isPlayerProjectile()) //prendi in riferimento solo proiettili dai nemici
                        .filter(p -> p.getHitbox().intersects(player.getHitbox()))
                        .forEach(p->{
                            player.takeDamage(p.getDamage());
                            p.setAlive(false); //il proiettile si elimina dopo aver colpito
                        });

        //5. logica dei proiettili player vs nemici
        projectiles.stream()
                .filter(Projectile::isPlayerProjectile)
                .forEach(p -> {
                    enemies.stream()
                            .filter(enemy -> p.getHitbox().intersects(enemy.getHitbox()))
                            .forEach(enemy -> {
                                enemy.takeDamage(p.getDamage());
                                p.setAlive(false);

                                // Logica del punteggio: se il nemico muore, aumenta lo score
                                if (!enemy.isAlive()) {
                                    score += 100;
                                    // In futuro: probabilità di spawnare una rupia alla morte
                                }
                            });
                });


        //6. raccolta rupie

        rupees.stream()
                .filter(r -> r.getHitbox().intersects(player.getHitbox()))
                .forEach(r-> {
                    player.addRupees(r.getValue());
                    r.setAlive(false); //la rupia viene raccolta e rimossa
                    //AudioManager.getInstance().play(suono rupia)
                });



        //7. pulizia entità morte/acquisite/altro tramite stream
        enemies.removeIf(enemy -> !enemy.isAlive());
        rupees.removeIf(rupee -> !rupee.isAlive());
        //i proiettili li rimuoviamo se hanno colpito qualcosa oppure se escono dalla mappa
        projectiles.removeIf(p-> !p.isAlive() || !screenBounds.intersects(p.getHitbox()));

        //6. controllo gameover
        if (!player.isAlive) {
            currentState = GameState.GAME_OVER;
        }

        //5. notifica la view
        //setChanged() imposta il flag interno a true, così notifyObservers sa che deve agire
        setChanged();
        notifyObservers("GAME_UPDATE");
    }

    public void playerAttack() {

        if (currentState != GameState.PLAYING) return;

        //1. calcolo hitbox spada
        int swordLength = 25;
        int playerWidth = player.getHitbox().width;
        int playerHeight = player.getHitbox().height;
        int px = (int) player.getX();
        int py = (int) player.getY();

        Rectangle swordHitbox;

        //troviamo direzione dominante
        if (Math.abs(player.getLastDx()) > Math.abs(player.getLastDy())) {
            // attacco orizzontale
            if (player.getLastDx() > 0) {
                //guarda a destra
                swordHitbox = new Rectangle(px + playerWidth, py, swordLength, playerHeight);
            } else {
                //guarda a sinistra
                swordHitbox = new Rectangle(px - swordLength, py, swordLength, playerHeight);
            }
            } else {
            //attacco verticale
            if (player.getLastDy() > 0) {
                //guarda in basso
                swordHitbox = new Rectangle(px, py + playerHeight, playerWidth, swordLength);
            } else {
                //guarda in alto
                swordHitbox = new Rectangle(px, py - swordLength, playerWidth, swordLength);
            }
        }

        //  2. controllo collisioni
        enemies.stream()
                .filter(enemy -> enemy.getHitbox().intersects(swordHitbox))
                .forEach(enemy -> {
                    int swordDamage = 1; //danno base della spada
                    enemy.takeDamage(swordDamage);

                    //se il nemico muore, aggiorniamo il punteggio
                    if (!enemy.isAlive()) {
                        score += 100;
                    }

                });
                setChanged();
                //il messaggio serve la view che deve disegnare l'animazione della spada per qualche frame
                notifyObservers("PLAYER_ATTACK");
            }


    // --  getter --
    public List<Projectile> getProjectiles() { return projectiles; }
    public List<Rupee> getRupees() { return rupees; }
    public Player getPlayer() {return player; }
    public List<Enemy> getEnemies() {return enemies;}
    public GameState getCurrentState() {return currentState;}
    public int getScore() { return score; }
    public int getCurrentLevel() { return currentLevel;}
    public ShopModel getShop() { return shop; }

}

