package Model;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.*;

public class Level {

    private Tile[][] grid;
    public static final int TILE_SIZE = 32;

    //liste temporanee per passare le entità iniziali al gamemodel

    private List<Enemy> initialEnemies;
    private double playerStartX, playerStartY;


    public Level(String[] mapData) {

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
                    case '~': type = TileType.WATER; break;
                    case 'D': type = TileType.DOOR; break;
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


    public Stream<Tile> getSolidTiles() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .filter(Tile::isSolid);
    }

    // -- getter --
    public List<Enemy> getInitialEnemies() { return initialEnemies; }
    public double getPlayerStartX() { return playerStartX; }
    public double getPlayerStartY() { return playerStartY; }
}
