package test;
import game.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void gamesNotPlayed() {
        Player player = new Player("Melina");

        player.addGame(1, GameType.Sudoku);
        player.addGame(3, GameType.Sudoku);
        player.addGame(4, GameType.Sudoku);
        player.addGame(5, GameType.Sudoku);

        ArrayList<Integer> games;

        games = player.gamesNotPlayed(GameType.Sudoku, 10);

        assertTrue(!games.contains(1));
        assertTrue(!games.contains(3));
        assertTrue(!games.contains(5));
        assertTrue(!games.contains(4));
        assertTrue(games.contains(2));
        assertTrue(games.contains(6));
        assertTrue(games.contains(7));
        assertTrue(games.contains(8));
        assertTrue(games.contains(9));
        assertTrue(games.contains(10));

        int game = player.randomGame(GameType.Sudoku, 10);

        assertNotEquals(game, -1);

        player.addGame(2, GameType.Sudoku);
        player.addGame(6, GameType.Sudoku);
        player.addGame(7, GameType.Sudoku);
        player.addGame(8, GameType.Sudoku);
        player.addGame(9, GameType.Sudoku);
        player.addGame(10, GameType.Sudoku);

        game = player.randomGame(GameType.Sudoku, 10);
        assertEquals(game, -1);
    }
}