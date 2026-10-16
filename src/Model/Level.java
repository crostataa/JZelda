package Model;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.*;

/**
 * Rappresenta la mappa di un livello di gioco.
 * Converte lo schema testuale ASCII fornito da {@link LevelFactory} in una matrice di {@link Tile},
 * determinando la posizione di spawn di Link e dei nemici iniziali.
 */
public class Level {

    private Tile[][] grid;
    public static final int TILE_SIZE = 32;

    //liste temporanee per passare le entità iniziali al gamemodel

    private List<Enemy> initialEnemies;
    private double playerStartX, playerStartY;

    /**
     * Costruisce il livello caricando i dati della mappa corrispondente all'ID specificato.
     * @param levelId identificativo del livello (1-8 o 9 per lo shop).
     */
    public Level(int levelId) {

        String[] mapData = LevelFactory.getLevelData(levelId);
        initialEnemies = new ArrayList<>();
        loadMap(mapData);
    }

    private void loadMap(String[] mapData) {

        int rows = mapData.length;
        int cols = mapData[0].length();
        grid = new Tile[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char symbol = mapData[row].charAt(col);
                double xPos = col * TILE_SIZE;
                double yPos = row * TILE_SIZE;

                TileType type = TileType.GROUND; //valore di default

                switch (symbol) {
                    case '#': type = TileType.WALL; break;
                    case 'D':
                    case 'C': type = TileType.DOOR; break; // Accetta sia D che C!
                    case 'P':
                        playerStartX = xPos;
                        playerStartY = yPos;
                        break;
                    case 'M': //enemy Moblin
                        initialEnemies.add(new MoblinEnemy(xPos, yPos));
                        break;
                    case 'O': // enemy Octorok
                        initialEnemies.add(new OctorokEnemy(xPos, yPos));
                        break;
                }

                // creiamo il tile su cui saranno le nostre entità
                grid[row][col] = new Tile(xPos, yPos, TILE_SIZE, type);
            }
        }
    }
    // -- getter --
    /**
     * Restituisce tutti i tile componenti la griglia della mappa.
     * @return lista di tutti i Tile non nulli.
     */
    public List<Tile> getTiles() {

        List<Tile> allTiles = new ArrayList<>();
        if (grid!= null) {
            for (Tile[] row : grid ) {
                for (Tile t : row)  {
                    if (t!= null) allTiles.add(t);
                }
            }
        }
        return allTiles;
    }


    /**
     * Restituisce uno {@link Stream} contenente unicamente i tile solidi della mappa,
     * utilizzato per i calcoli efficienti delle collisioni.
     * @return stream di Tile solidi.
     */
    public Stream<Tile> getSolidTiles() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .filter(Tile::isSolid);
    }

    public List<Enemy> getInitialEnemies() { return initialEnemies; }
    public double getPlayerStartX() { return playerStartX; }
    public double getPlayerStartY() { return playerStartY; }
}
