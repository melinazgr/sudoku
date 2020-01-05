import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void createPlayer() {
        Player player = new Player("Melina");

        player.addGame(1, GameType.Sudoku);
        player.addGame(1, GameType.KillerSudoku);

        assertNotNull(player.findGame(1, GameType.Sudoku));
        assertNull(player.findGame(3, GameType.Sudoku));
    }
}