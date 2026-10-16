package Model;


import java.io.*;
import java.util.*;

/**
 * Gestore Singleton per la persistenza e il tracciamento delle statistiche dei giocatori.
 * Salva e carica i dati degli utenti da un file di testo (stats.txt) e genera la classifica.
 */
public class StatsManager {

    private static StatsManager instance;
    private final String FILE_PATH = "stats.txt";
    private Map<String, PlayerStats> database;


    private StatsManager() {
        database = new HashMap<>();
        loadStats();
    }

    /**
     * Restituisce l'istanza unica del gestore delle statistiche (Singleton).
     * @return l'istanza di StatsManager.
     */
    public static StatsManager getInstance() {
        if (instance == null)  {
                instance = new StatsManager();
            }
        return instance;
        }

    /**
     * Recupera o crea le statistiche associate al nickname specificato.
     * @param username il nome del giocatore.
     * @return l'oggetto PlayerStats corrispondente.
     */
    public PlayerStats statsPlayer(String username) {
        if (username == null || username.trim().isEmpty()) {
            username = "Stefano";
        }
        if (!database.containsKey(username)) {
            database.put(username, new PlayerStats(username));
        }
        return database.get(username);
    }


    private void loadStats() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return; // Se il file non esiste, parte da zero

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    PlayerStats stats = new PlayerStats(parts[0]);
                    stats.setArrowsShot(Integer.parseInt(parts[1]));
                    stats.setMoblinsKilled(Integer.parseInt(parts[2]));
                    stats.setOctoroksKilled(Integer.parseInt(parts[3]));
                    stats.setTotalRupees(Integer.parseInt(parts[4]));
                    stats.setPotionsUsed(Integer.parseInt(parts[5]));
                    stats.setBombsUsed(Integer.parseInt(parts[6]));
                    stats.setGamesWon(Integer.parseInt(parts[7]));
                    stats.setAvatar(parts[8]);

                    database.put(stats.getUsername(), stats);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento statistiche: " + e.getMessage());
        }
    }

    /**
     * Salva tutte le statistiche registrate nel database sul file persistente stats.txt in formato CSV.
     */
    public void saveStats() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (PlayerStats stats : database.values()) {
                bw.write(stats.getUsername() + "," +
                             stats.getArrowsShot() + "," +
                             stats.getMoblinsKilled() + "," +
                             stats.getOctoroksKilled() + "," +
                             stats.getTotalRupees() + "," +
                             stats.getPotionsUsed() + "," +
                             stats.getBombsUsed() + "," +
                             stats.getGamesWon() + "," +
                             stats.getAvatar());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio statistiche: " + e.getMessage());
        }
    }

    /**
     * Genera la classifica ordinata dei giocatori, prioritizzando il numero di partite vinte
     * e, a parità, il totale di rupie accumulate.
     * @return lista ordinata di PlayerStats.
     */
    public List<PlayerStats> getLeaderboard() {
        List<PlayerStats> list = new ArrayList<>(database.values());

        //leaderboard ordinata per vittorie decrescenti e poi per rupie accumulate
        list.sort((p1, p2) -> {
            if (p1.getGamesWon() != p2.getGamesWon()) {
                return Integer.compare(p2.getGamesWon(), p1.getGamesWon());
            }
            return Integer.compare(p2.getTotalRupees(), p1.getTotalRupees());

        });
        return list;
    }
}


