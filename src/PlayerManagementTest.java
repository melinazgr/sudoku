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
}