import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagementTest {

    @Test
    void load() {
        FileManagement file = new FileManagement();
        try{
            file.load(".\\resources\\games.csv");
        }
        catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        GameDefinition game1 = file.getGame(1,GameType.Sudoku);
        Model model = new ModelSudoku();
        model.load(game1);

        assertEquals(model.getDisplayCell(0, 0), 0);
        assertEquals(model.getDisplayCell(0, 2), 3);
        assertEquals(model.getDisplayCell(4, 3), 4);
        assertEquals(model.getDisplayCell(4, 6), 8);
    }

}