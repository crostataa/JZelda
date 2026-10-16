package Model;

import Controller.GameController;
import View.AudioManager;



import java.awt.*;
import java.util.*;
import java.util.List;

/**
*Gestisce la logica del gioco, contiene tutti i metodi che permettono il funzionamento
 *di ogni partita, specialmente grazie al metodo update che contiene al suo interno tutti
 *i controlli da fare ad ogni frame.
 *Segue il design pattern MVC.
*/

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
    private List <HealthPotionEntity> groundPotion;
    private GmailEntity gmailEntity;
    private LinkSkin selectedSkin = LinkSkin.VERDE;


    //animazione eliminazione entità
    private List<DeathAnimation> deathAnimations = new ArrayList<>();
    private int playerDeathTimer;

    //shop
    private ShopModel shop;

    //limiti di schermo
    private final Rectangle screenBounds = new Rectangle(0,0, 800, 600); //da modificare dopo sprite

    //mappa
    private Level currentLevelMap;

    //timer vittoria
    private int victoryTimer;

    //statistiche
    private PlayerStats currentStats;




    /**
     * Costruisce il modello logico del gioco impostando lo stato iniziale su MENU.
     */
    public GameModel() {

        initGame("Stefano"); //placeholder
        this.currentState = GameState.MENU;

    }


     /**
      * Inizializza o resetta una partita, impostando lo stato iniziale,
      * il livello e le entità associate al giocatore specificato.
      * @param username il nickname dell'utente corrente.
      */
    public void initGame(String username) {

        this.enemies = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.rupees = new ArrayList<>();
        this.shop = new ShopModel();
        this.activeBombs = new ArrayList<>();
        this.currentState = GameState.PLAYING;
        this.currentLevel = 1;
        this.currentLevelMap = new Level(currentLevel);
        this.score = 0;
        this.groundPotion = new ArrayList<>();
        deathAnimations.clear();
        this.currentStats = StatsManager.getInstance().statsPlayer(username);


        //posizione iniziale del giocatore, al centro dello schermo *
        this.player = new Player(currentLevelMap.getPlayerStartX(), currentLevelMap.getPlayerStartY());
        this.player.setSkin(selectedSkin);
        loadLevelEntities(currentLevel);

        //notifica alla View in ascolto che il gioco è stato resettato
        setChanged();
        notifyObservers("GAME_RESET");

    }

    /**
     * Cambia mappa aggiornando la posizione del giocatore e ricaricando le entità.
     * @param newLevelId identificativo numerico della nuova stanza o livello.
     * @param startX coordinata X di riposizionamento del giocatore.
     * @param startY coordinata Y di riposizionamento del giocatore.
     */
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


     /**
      * Viene invocato dal Controller quando il giocatore preme il tasto per
      * piazzare una bomba, aggiornando contatori e coordinate.
      */
    public void placeBomb() {
        if (currentState != GameState.PLAYING) return;

        if (player.getBombs() > 0) {
            player.consumeBomb();
            currentStats.addBomb();
            // Piazza la bomba esattamente al centro del giocatore
            activeBombs.add(new BombEntity(player.getX() + 8, player.getY() + 8));

            setChanged();
            notifyObservers("BOMB_PLACED");
        }
    }


    /**
     * Aggiunge un proiettile alla scena per la simulazione fisica.
     * @param p il proiettile da inserire.
     */
    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    /**
     * Fa sparare una freccia al giocatore calcolando direzione e velocità.
     */
    public void shootArrow() {

        if (currentState != GameState.PLAYING) return;

        if (player.hasBow() && player.getArrows() > 0) {

            player.consumeArrow();
            currentStats.addArrow();

            double arrowSpeed = 5.0;
            double projDx = 0;
            double projDy = 0;
            int arrowDamage = 3; ///

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

            setChanged();
            notifyObservers("ARROW_SHOT");


        }


    }

    // Carica nemici e oggetti specifici in base all'id del livello.
    private void loadLevelEntities(int levelId) {

        enemies.clear();
        rupees.clear();
        projectiles.clear();
        groundPotion.clear();

        //spawn dei nemici x ogni livello

        switch (levelId) {
            case 1:
                enemies.add(new MoblinEnemy(300, 200));;
                break;

            case 2:
                enemies.add(new MoblinEnemy(600, 110));
                enemies.add(new MoblinEnemy(600, 210));
                enemies.add(new MoblinEnemy(600, 310));
                break;

            case 3:
                enemies.add(new OctorokEnemy(600, 380));
                enemies.add(new OctorokEnemy(500,380));
                break;
            case 4:
                enemies.add(new MoblinEnemy(350, 50));
                enemies.add(new MoblinEnemy(440, 50));
                enemies.add(new MoblinEnemy(100, 90));
                enemies.add(new OctorokEnemy(600, 200));
                enemies.add(new OctorokEnemy(600, 250));
                break;
            case 5:
                enemies.add(new MoblinEnemy(673, 260));
                enemies.add(new MoblinEnemy(550, 75));
                enemies.add(new OctorokEnemy(200, 65));
                enemies.add(new OctorokEnemy(250, 65));
                enemies.add(new MoblinEnemy(630 , 420));
                break;
            case 6:
                enemies.add(new OctorokEnemy(300, 430));
                enemies.add(new OctorokEnemy(340, 430));
                enemies.add(new MoblinEnemy(25, 190));
                enemies.add(new MoblinEnemy(25, 240));
                enemies.add(new MoblinEnemy(25, 290));
                enemies.add(new OctorokEnemy(245, 60));
                break;
            case 7:
                enemies.add(new OctorokEnemy(500, 50));
                enemies.add(new OctorokEnemy(530, 50));
                enemies.add(new OctorokEnemy(500, 430));
                enemies.add(new OctorokEnemy(530, 430));
                enemies.add(new OctorokEnemy(25, 200));
                enemies.add(new OctorokEnemy(25, 240));
                enemies.add(new OctorokEnemy(25, 280));
                break;
            case 8:
                enemies.add(new MoblinEnemy(215, 125));
                enemies.add(new MoblinEnemy(245, 125));
                enemies.add(new MoblinEnemy(295, 125));
                enemies.add(new MoblinEnemy(325, 125));
                enemies.add(new OctorokEnemy(550, 60));
                enemies.add(new OctorokEnemy(590, 60));
                enemies.add(new OctorokEnemy(550, 380));
                enemies.add(new OctorokEnemy(590, 380));
                enemies.add(new OctorokEnemy(120, 220));
                enemies.add(new OctorokEnemy(120, 260));
                enemies.add(new OctorokEnemy(120, 300));
                break;
            case 9:
                groundPotion.add(new HealthPotionEntity(384, 256));
                break;
            case 10:
                break;
            case 11:
                gmailEntity = new GmailEntity(368, 240);

            default:
                break;
        }

    }




    // -- metodi x controller --
    /**
     * Motore principale: aggiorna logiche, movimenti, timer e risolve tutte le collisioni.
     */
    public void update() {

        // --- 1. GESTIONE STATI SPECIALI ---
        // Se il gioco è in fase di vittoria, scala il timer dell'animazione finale
        // e notifica il termine salvando le statistiche.
        if (currentState == GameState.VICTORY_ANIMATION) {
            victoryTimer--;
            setChanged();
            notifyObservers("GAME_UPDATE");

            if (victoryTimer <= 0) {
                currentState = GameState.VICTORY;
                currentStats.addWin();
                StatsManager.getInstance().saveStats();
                setChanged();
                notifyObservers("VICTORY");
            }
            return;
        }

        // Se il giocatore è morto, scala il timer dell'animazione di morte
        // prima di passare alla schermata effettiva di Game Over.
        if (currentState == GameState.PLAYER_DEATH) {
            playerDeathTimer--;
            StatsManager.getInstance().saveStats();
            setChanged();
            notifyObservers("GAME_UPDATE"); // notiifca view per disegnare animazione

            if (playerDeathTimer <= 0) {
                currentState = GameState.GAME_OVER;
                setChanged();
                notifyObservers("GAME_OVER");
            }
            return;
        }

        // Interrompi l'aggiornamento se non siamo nella schermata di gioco
        if (currentState != GameState.PLAYING) return;


        // --- 2. AGGIORNAMENTO BOMBE E ESPLOSIONI ---
        // Scala il timer di accensione di ogni bomba a terra.
        activeBombs.forEach(BombEntity::update);

        // Identifica le bombe appena esplose, calcola i danni ad area per i nemici (e per il giocatore)
        // e infine riproduce l'effetto sonoro dell'esplosione.
        activeBombs.stream()
                        .filter(BombEntity::hasExploded)
                                .forEach(bomb -> {
                                    AudioManager.getInstance().playSE(8);
                                    Rectangle blastRadius = bomb.getExplosionHitbox();
                                    //nemici coinvolti nell'esplosione
                                    enemies.stream()
                                            .filter(enemy -> enemy.getHitbox().intersects(blastRadius))
                                            .forEach(enemy -> enemy.takeDamage(bomb.getDamage()));
                                    //le bombe danneggiano anche link
                                    if (player.getHitbox().intersects(blastRadius)) {
                                        int oldHp = player.getHp();
                                        player.takeDamage(1);
                                        if (player.getHp() < oldHp) {
                                            AudioManager.getInstance().playSE(11); // Suono Link Ferito
                                        }
                                    }
                                });
        // Rimuove le bombe dalla memoria una volta esaurita l'animazione dell'esplosione.
        activeBombs.removeIf(bomb -> !bomb.isAlive());



        // --- 3. GESTIONE MOVIMENTO E COLLISIONI MURI ---
        // Controlla preventivamente se la posizione futura di Link si sovrappone a un muro solido.
        // Se la via è libera, muove Link e verifica eventuali cambi mappa (porte/bordi).
        boolean playerHitWall = currentLevelMap.getSolidTiles()
                .anyMatch(tile -> tile.getHitbox().intersects(player.getPredictedHitbox()));

        if(!playerHitWall) {
            player.update(); // Muove Link
            checkLevelTransitions();
        } else {
            player.setDirection(0,0);
        }

        // Fa eseguire le routine di IA ai nemici e li fa avanzare solo se non sbattono sui muri.
        enemies.forEach(enemy -> {
            enemy.updateBehavior(this);
            boolean enemyHitWall = currentLevelMap.getSolidTiles()
                    .anyMatch(tile -> tile.getHitbox().intersects(enemy.getPredictedHitbox()));
            if (!enemyHitWall) {
                enemy.update();
            }
        });

        // Muove i proiettili (frecce o sassi). Se colpiscono un muro, vengono distrutti.
        projectiles.forEach(p -> {
            boolean projHitWall = currentLevelMap.getSolidTiles()
                    .anyMatch(tile -> tile.getHitbox().intersects(p.getPredictedHitbox()));
            if (!projHitWall) {
                p.update();
            } else {
                p.setAlive(false); //il colpo si infrange sull'entità colpita
            }
        });


        // --- 4. GESTIONE COLLISIONI DI COMBATTIMENTO ---
        // Verifica se un nemico tocca fisicamente Link infliggendogli danno da impatto.
        enemies.stream()
                .filter(enemy -> enemy.getHitbox().intersects(player.getHitbox()))
                .forEach(enemy -> {
                    int oldHp = player.getHp();
                    player.takeDamage(enemy.getDamage());
                    if (player.getHp() < oldHp) {
                        AudioManager.getInstance().playSE(11); // Link Hurt
                    }
                });


        // Verifica se i proiettili sparati dai nemici (es. sassi degli Octorok) colpiscono Link.
        projectiles.stream()
                        .filter(p -> !p.isPlayerProjectile())
                        .filter(p -> p.getHitbox().intersects(player.getHitbox()))
                        .forEach(p->{
                            int oldHp = player.getHp();
                            player.takeDamage(p.getDamage());
                            if (player.getHp() < oldHp) {
                                AudioManager.getInstance().playSE(11);
                            }
                            p.setAlive(false);
                        });


        // Verifica se i proiettili sparati da Link (frecce) colpiscono i nemici, infliggendo danni.
        projectiles.stream()
                .filter(Projectile::isPlayerProjectile)
                .forEach(p -> {
                    enemies.stream()
                            .filter(enemy -> p.getHitbox().intersects(enemy.getHitbox()))
                            .forEach(enemy -> {
                                enemy.takeDamage(p.getDamage());
                                p.setAlive(false);

                                if (!enemy.isAlive()) {
                                    score += 100;
                                }
                            });
                });


        // --- 5. ANIMAZIONI, COLLEZIONABILI E TRIGGER SPECIALI ---
        // Fa avanzare le animazioni di morte dei nemici e le elimina quando concluse.
        deathAnimations.forEach(DeathAnimation::update);
        deathAnimations.removeIf(DeathAnimation::isFinished);

        // Permette a Link di raccogliere rupie passandoci sopra (aggiornando soldi e statistiche).
        rupees.stream()
                .filter(r -> r.getHitbox().intersects(player.getHitbox()))
                .forEach(r-> {
                    AudioManager.getInstance().playSE(3);
                    player.addRupees(r.getValue());
                    currentStats.addRupees(r.getValue());
                    r.setAlive(false);
                });

        // Permette a Link di raccogliere pozioni curative sparse a terra.
        groundPotion.stream()
                .filter( p -> p.getHitbox().intersects(player.getHitbox()))
                        .forEach( p -> {
                            AudioManager.getInstance().playSE(3);
                            player.addPotions(1);
                            p.setAlive(false);
                        });
        groundPotion.removeIf(potion -> !potion.isAlive());

        // Controlla la condizione di Vittoria: Link tocca l'icona finale (Gmail) nel livello 11.
        if (currentLevel == 11 && gmailEntity != null && gmailEntity.isAlive()) {
            if (player.getHitbox().intersects(gmailEntity.getHitbox())) {
                AudioManager.getInstance().stopMusic();
                AudioManager.getInstance().playMusic(9);
                gmailEntity.setAlive(false);
                currentState = GameState.VICTORY_ANIMATION;
                victoryTimer = 300;
                player.setX(gmailEntity.getX());
                player.setY(gmailEntity.getY());
            }
        }


        // Rimuove i nemici morti dalla lista, facendo spawnare rupie come loot occasionale.
        enemies.removeIf(enemy -> {
            if (!enemy.isAlive()) {
                AudioManager.getInstance().playSE(4);
                deathAnimations.add(new DeathAnimation(enemy.getX(), enemy.getY()));

                if (enemy instanceof MoblinEnemy) currentStats.addMoblin();
                else if (enemy instanceof OctorokEnemy) currentStats.addOctorok();

                if (Math.random() < 0.80) {
                    int rupeeValue = Math.random() < 0.40 ? 5 : 1;
                    rupees.add(new Rupee(enemy.getX() + 8, enemy.getY() + 8, rupeeValue));
                }
                return true;
            }
            return false;
        });
        
        // Rimuove rupie raccolte e proiettili esauriti o usciti fuori dallo schermo.
        rupees.removeIf(rupee -> !rupee.isAlive());
        projectiles.removeIf(p -> !p.isAlive() || !screenBounds.intersects(p.getHitbox()));

        // Gestisce gli acquisti nello shop (Livello 10): tocca l'oggetto per provare a comprarlo.
        if (currentLevel == 10 && shop != null) {
            shop.getItemsForSale().stream()
                    .filter(ShopEntry::isAvailable)
                    .filter(entry -> entry.getHitbox().intersects(player.getHitbox()))
                    .forEach(entry -> {
                        boolean success = shop.buyItem(player, entry.getItem().getName());
                        if (success) {
                            AudioManager.getInstance().playSE(3);
                            entry.setAvailable(false);
                            System.out.println("Hai comprato: " + entry.getItem().getName());
                        } else {
                            player.setY(player.getY() + 10); // Respinge indietro se non hai rupie/spazio
                        }
                    });
        }

        // Verifica finale: se dopo tutte le collisioni Link ha 0 HP, avvia la sequenza di morte.
        if (player != null && player.getHp() <= 0) {
            AudioManager.getInstance().stopMusic();
            AudioManager.getInstance().playSE(5);
            currentState = GameState.PLAYER_DEATH;
            playerDeathTimer = 180;
        }

        // --- 6. NOTIFICA ALLA VIEW ---
        // Avverte gli observer (GamePanel/HUDPanel) che tutti i calcoli fisici sono conclusi
        // e che possono procedere a disegnare il nuovo frame.
        setChanged();
        notifyObservers("GAME_UPDATE");
    }

    /**
     * esegue un attacco corpo a corpo calcolando l'hitbox della spada e danneggiando i nemici colpiti.
     */
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

    // Controlla se il giocatore ha attraversato una porta o i confini della mappa per cambiare livello.
    private void checkLevelTransitions() {
        int mapWidth = 768;
        int mapHeight = 512;
        int playerSize = 32;

        // 1. TRANSIZIONI MAPPA (Tutte e 4 le direzioni)
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
                    else if (currentLevel == 9) changeLevel(1, 512, 60); // in ogni mappa la porta è in alto
                    else if (currentLevel == 10) changeLevel(2, 256, 60);
                    else if (currentLevel == 11) changeLevel(8, 512, 60);
                });
    }

    // Restituisce l'id del livello adiacente in base all'attuale e alla direzione
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
    public List<DeathAnimation> getDeathAnimations() { return deathAnimations; }
    public ShopModel getShop() { return shop; }
    public List<BombEntity> getActiveBombs() { return activeBombs; }
    public List<HealthPotionEntity> getGroundPotion() { return groundPotion; }
    public int getPlayerDeathTimer() { return playerDeathTimer; }
    public void setCurrentState(GameState gameState) {currentState = gameState;}
    public GmailEntity getGmailEntity() { return gmailEntity; }
    public PlayerStats getCurrentStats() { return currentStats; }
    /**
     * Restituisce la skin di Link attualmente selezionata.
     * @return la LinkSkin selezionata.
     */
    public LinkSkin getSelectedSkin() { return selectedSkin; }

    /**
     * Imposta la skin attiva per Link.
     * @param selectedSkin la nuova LinkSkin da utilizzare.
     */
    public void setSelectedSkin(LinkSkin selectedSkin) { this.selectedSkin = selectedSkin; }
}

