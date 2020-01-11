package test;
import game.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerManagementTest {

    @Test
    void createPlayer() {
        PlayerManagement players = new PlayerManagement();

        Player pl = players.createPlayer("Melina");
        assertEquals(players.searchPlayer(pl.getName()), true);
        assertEquals(players.searchPlayer("foobar"), false);

        pl = players.createPlayer("Melina");
        assertEquals(players.searchPlayer(pl.getName()), true);

        assertEquals(players.getPlayerCount(), 1);
    }

    @Test
    void savePlayer(){
        PlayerManagement players = new PlayerManagement();

        Player pl = players.createPlayer("Melina");
        pl.addGame(1, GameType.Sudoku);
        Player pl2 = players.createPlayer("foobar");
        pl2.addGame(4, GameType.KillerSudoku);

        players.save("test.txt");

        players.load("test.txt");

        assertEquals(players.getPlayerCount(), 2);
        pl = players.getPlayer("Melina");
        pl2 = players.getPlayer("foobar");

        assertEquals(pl.getName(), "Melina");
        assertEquals(pl2.getName(), "foobar");

        assertNotNull(pl.findGame(1, GameType.Sudoku));
        assertNotNull(pl2.findGame(4, GameType.KillerSudoku));

        assertTrue(players.searchPlayer(pl.getName()));
        assertTrue(players.searchPlayer(pl2.getName()));
    }
}