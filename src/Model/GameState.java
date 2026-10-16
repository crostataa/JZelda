package Model;

/**
 * Stati possibili della macchina a stati finiti (FSM) del gioco.
 */
public enum GameState {
    /** Partita in corso, input e collisioni attive. */
    PLAYING,
    /** Schermata di Game Over dopo la sconfitta. */
    GAME_OVER,
    /** Sequenza animata di rotazione di Link alla morte. */
    PLAYER_DEATH,
    /** Sequenza animata di celebrazione dopo aver raccolto l'oggetto finale. */
    VICTORY_ANIMATION,
    /** Schermata finale di vittoria con testo a scorrimento. */
    VICTORY,
    /** Menu principale del gioco. */
    MENU
}
