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
        Player pl2 = players.createPlayer("foobar");

        players.save("test.txt");

        players.load("test.txt");

        assertEquals(players.getPlayerCount(), 2);

        assertTrue(players.searchPlayer(pl.getName()));
        assertTrue(players.searchPlayer(pl2.getName()));
    }
}