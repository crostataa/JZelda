package Model;

import View.AudioManager;

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
    private List<BombEntity> activeBombs;

    //animazione eliminazione entitò
    private List<DeathAnimation> deathAnimations = new ArrayList<>();
    public List<DeathAnimation> getDeathAnimations() { return deathAnimations; }

    //shop
    private ShopModel shop;

    //limiti di schermo
    private final Rectangle screenBounds = new Rectangle(0,0, 800, 600); //da modificare dopo sprite

    //mappa
    private Level currentLevelMap;



    public GameModel() {

        initGame();

    }

    //inizializza o resetta una partita

    public void initGame() {

        this.enemies = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.rupees = new ArrayList<>();
        this.shop = new ShopModel();
        activeBombs = new ArrayList<>();
        this.currentState = GameState.PLAYING;
        this.currentLevel = 1;
        this.currentLevelMap = new Level(currentLevel);
        this.score = 0;
        deathAnimations.clear();


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
        this.currentLevel = newLevelId;
        this.currentLevelMap = new Level(currentLevel);
        //riposiziona giocatore all'ingresso della nuova zona
        this.player.setX(startX);
        this.player.setY(startY);

        //svuota proiettili e carica i nuovi nemici specifici del livello
        this.projectiles.clear();
        loadLevelEntities(newLevelId);
    }

    // Metodo chiamato dal Controller quando il giocatore preme il tasto della bomba
    public void placeBomb() {
        if (currentState != GameState.PLAYING) return;

        if (player.getBombs() > 0) {
            player.consumeBomb();
            // Piazza la bomba esattamente al centro del giocatore
            activeBombs.add(new BombEntity(player.getX() + 8, player.getY() + 8));

            setChanged();
            notifyObservers("BOMB_PLACED");
        }
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

    public void shootArrow() {

        if (currentState != GameState.PLAYING) return;

        if (player.hasBow() && player.getArrows() > 0) {
            player.consumeArrow();

            double arrowSpeed = 5.0;
            double projDx = 0;
            double projDy = 0;
            int arrowDamage = 3; /**/

            // Se sta guardando a destra o sinistra
            if (player.getLastDx() > 0) projDx = arrowSpeed;
            else if (player.getLastDx() < 0) projDx = -arrowSpeed;

                // Se sta guardando in basso o in alto
            else if (player.getLastDy() > 0) projDy = arrowSpeed;
            else if (player.getLastDy() < 0) projDy = -arrowSpeed;

            // Fallback di sicurezza: se per qualche motivo è fermo e non ha direzione
            if (projDx == 0 && projDy == 0) projDy = arrowSpeed; // Spara in basso di default

            // Presumibilmente: Projectile(x, y, dx, dy, isPlayerProjectile)
            Projectile arrow = new Projectile(
                    player.getX() + 8, // Centrato rispetto a Link
                    player.getY() + 8,
                    projDx,
                    projDy,
                    arrowSpeed,
                    arrowDamage,
                    true
            );
            projectiles.add(arrow);
            System.out.println("Freccia scoccata! Frecce rimanenti: " + player.getArrows());

            setChanged();
            notifyObservers("ARROW_SHOT");
        } else if (!player.hasBow()) {
            System.out.println("Non hai ancora un arco!");
        } else {
            System.out.println("Frecce esaurite!");
        }





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

        // aggiorna timer bombe
        activeBombs.forEach(BombEntity::update);

        //calcola danni bombe appena esplose
        activeBombs.stream()
                        .filter(BombEntity::hasExploded)
                                .forEach(bomb -> {
                                    Rectangle blastRadius = bomb.getExplosionHitbox();

                                    //nemici coinvolti nell'esplosione
                                    enemies.stream()
                                            .filter(enemy -> enemy.getHitbox().intersects(blastRadius))
                                            .forEach(enemy -> enemy.takeDamage(bomb.getDamage()));
                                    //le bombe danneggiano anche link
                                    if (player.getHitbox().intersects(blastRadius)) {
                                        player.takeDamage(1);
                                    }
                                    //AudioManager.getInstance().play(/**/);
                                });
        activeBombs.removeIf(bomb -> !bomb.isAlive());



        //controllo collisioni player vs muri
        boolean playerHitWall = currentLevelMap.getSolidTiles()
                .anyMatch(tile -> tile.getHitbox().intersects(player.getPredictedHitbox()));

        if(!playerHitWall) {
            player.update(); // Muove Link
            checkLevelTransitions(); // <--- AGGIUNGI QUESTO QUI! Controlla se è uscito dalla mappa o ha toccato una porta
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


        // gestione animazione morti
        deathAnimations.forEach(DeathAnimation::update);
        deathAnimations.removeIf(DeathAnimation::isFinished);


        //6. raccolta rupie

        rupees.stream()
                .filter(r -> r.getHitbox().intersects(player.getHitbox()))
                .forEach(r-> {
                    player.addRupees(r.getValue());
                    r.setAlive(false); //la rupia viene raccolta e rimossa
                    //AudioManager.getInstance().play(suono rupia)
                });



        // 7. pulizia entità morte e drop rupie
        enemies.removeIf(enemy -> {
            if (!enemy.isAlive()) {
                // SPAWNA L'ANIMAZIONE DI MORTE ALLE COORDINATE DEL NEMICO
                deathAnimations.add(new DeathAnimation(enemy.getX(), enemy.getY()));

                // 80% di probabilità di droppare una rupia... (il tuo codice esistente)
                if (Math.random() < 0.80) {
                    int rupeeValue = Math.random() < 0.40 ? 5 : 1;
                    rupees.add(new Rupee(enemy.getX() + 8, enemy.getY() + 8, rupeeValue));
                }
                return true;
            }
            return false;
        });

        rupees.removeIf(rupee -> !rupee.isAlive());
        projectiles.removeIf(p -> !p.isAlive() || !screenBounds.intersects(p.getHitbox()));

        // 8. LOGICA SHOPPING FISICO (Livello 10)
        if (currentLevel == 10 && shop != null) {
            shop.getItemsForSale().stream()
                    .filter(ShopEntry::isAvailable)
                    .filter(entry -> entry.getHitbox().intersects(player.getHitbox()))
                    .forEach(entry -> {
                        // Tenta di acquistare usando la TUA logica (scala rupie, mette in inventario se c'è spazio)
                        boolean success = shop.buyItem(player, entry.getItem().getName());
                        if (success) {
                            entry.setAvailable(false); // Nasconde l'oggetto dal bancone
                            System.out.println("Hai comprato: " + entry.getItem().getName());
                        } else {
                            // Se inventario pieno o mancano rupie, Link viene "respinto" leggermente per non incastrarsi
                            player.setY(player.getY() + 10);
                        }
                    });
        }

        //6. controllo gameover
        if (player != null && player.getHp() <= 0) {
            currentState = GameState.GAME_OVER;
            setChanged();
            notifyObservers("GAME_OVER");
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

                player.startAttack();
                setChanged();
                //il messaggio serve la view che deve disegnare l'animazione della spada per qualche frame
                notifyObservers("PLAYER_ATTACK");
            }

    // Metodo per controllare se Link ha calpestato una porta o superato i bordi
    private void checkLevelTransitions() {
        int mapWidth = 768;
        int mapHeight = 512;
        int playerSize = 32;

        // 1. TRANSIZIONI OVERWORLD (Tutte e 4 le direzioni)
        if (player.getX() < 0) {
            int nextLevel = getAdjacentLevel(currentLevel, "LEFT");
            if (nextLevel != -1) changeLevel(nextLevel, mapWidth - playerSize, (int)player.getY());
        }
        else if (player.getX() + playerSize > mapWidth) {
            int nextLevel = getAdjacentLevel(currentLevel, "RIGHT");
            if (nextLevel != -1) changeLevel(nextLevel, 0, (int)player.getY());
        }
        else if (player.getY() < 0) {
            int nextLevel = getAdjacentLevel(currentLevel, "UP");
            if (nextLevel != -1) changeLevel(nextLevel, (int)player.getX(), mapHeight - playerSize);
        }
        else if (player.getY() + playerSize > mapHeight) {
            int nextLevel = getAdjacentLevel(currentLevel, "DOWN");
            if (nextLevel != -1) changeLevel(nextLevel, (int)player.getX(), 0);
        }

        // 2. CONTROLLO PORTE DEI DUNGEON
        currentLevelMap.getTiles().stream()
                .filter(tile -> tile.getType() == TileType.DOOR)
                .filter(tile -> tile.getHitbox().intersects(player.getHitbox()))
                .findFirst()
                .ifPresent(door -> {
                    // Overworld -> Dungeon (entra dal basso al centro)
                    if (currentLevel == 1) changeLevel(9, 368, 400);
                    else if (currentLevel == 2) changeLevel(10, 368, 400);
                    else if (currentLevel == 8) changeLevel(11, 368, 400);

                        // Dungeon -> Overworld (esce in punti specifici a Y = 40)
                    else if (currentLevel == 9) changeLevel(1, 512, 60); // La porta nella mappa 1 è a destra
                    else if (currentLevel == 10) changeLevel(2, 256, 60); // La porta nella mappa 2 è a sinistra
                    else if (currentLevel == 11) changeLevel(8, 512, 60); // La porta nella mappa 8 è a destra
                });
    }

    // Navigatore esplicito della griglia del mondo (SOLO OVERWORLD)
    private int getAdjacentLevel(int current, String direction) {
        switch (current) {
            case 1:
                if (direction.equals("RIGHT")) return 2;
                break;
            case 2:
                if (direction.equals("LEFT")) return 1;
                if (direction.equals("RIGHT")) return 3;

                break;
            case 3:
                if (direction.equals("LEFT")) return 2;
                if (direction.equals("RIGHT")) return 4;
                break;
            case 4:
                if (direction.equals("LEFT")) return 3;
                if (direction.equals("UP")) return 5;
                break;
            case 5:
                if (direction.equals("DOWN")) return 4;
                if (direction.equals("LEFT")) return 6;
                break;
            case 6:
                if (direction.equals("RIGHT")) return 5;
                if (direction.equals("LEFT")) return 7;
                break;
            case 7:
                if (direction.equals("LEFT")) return 8;
                if (direction.equals("RIGHT")) return 6;
                break;
            case 8:
                if (direction.equals("RIGHT")) return 7;
                break;
        }
        return -1;
    }



    // --  getter e setter --
    public List<Projectile> getProjectiles() { return projectiles; }
    public List<Rupee> getRupees() { return rupees; }
    public Player getPlayer() {return player; }
    public List<Enemy> getEnemies() {return enemies;}
    public GameState getCurrentState() {return currentState;}
    public int getScore() { return score; }
    public int getCurrentLevel() { return currentLevel;}
    public Level getCurrentLevelMap() { return currentLevelMap; }
    public ShopModel getShop() { return shop; }
    public List<BombEntity> getActiveBombs() { return activeBombs; }

    public void setCurrentState(GameState gameState) {
        currentState = gameState;
    }
}

