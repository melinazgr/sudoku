import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelSudokuTest {

    ModelSudoku model;

    @BeforeEach
    void setUp() {
        model = new ModelSudoku();

        String s =
                "..3.....9" +
                "2....43.." +
                "461......" +
                "...8..9.6" +
                "...4..8.." +
                ".......3." +
                "..9....15" +
                ".2.68..9." +
                "63.5..2.8," +
                "573268149298154367461793582342871956957436821816925734789342615125687493634519278";

        model.load(s);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDisplayCell() {
        assertEquals(model.getDisplayCell(0, 0), 0);
        assertEquals(model.getDisplayCell(0, 2), 3);
        assertEquals(model.getDisplayCell(4, 3), 4);

    }

    @Test
    void checkSettingValues() {
        assertEquals(model.isValidCell(0,0,5), true);
        assertEquals(model.isValidCell(0,0,2), false);
        assertEquals(model.isValidCell(0,1,8), true);

        assertEquals(model.setCell(0,1,8), true);
        assertEquals(model.setCell(0,2,3), false);

        assertEquals(model.getDisplayCell(0,1), 8);

        assertEquals(model.setCell(1,1,6), false);
        assertEquals(model.getDisplayCell(1,1), 0);
    }

    @Test
    void getColor() {
    }

    @Test
    void getSum() {
    }

    @Test
    void getSolution() {
    }

    @Test
    void getHint() {
        ArrayList<Integer> hints = model.getHint(0,0);

        assertEquals(hints.contains(5), true);
        assertEquals(hints.contains(7), true);
        assertEquals(hints.contains(8), true);

        assertEquals(model.setCell(0,0,5), true);

        hints = model.getHint(0,0);

        assertEquals(hints.contains(5), false);
        assertEquals(hints.contains(7), true);
        assertEquals(hints.contains(8), true);
    }

    @Test
    void getSize() {
    }

    @Test
    void getGroupSize() {
    }

    @Test
    void getColumn() {
        ArrayList<Integer> col = model.getColumn(0);

        //column 0
        assertEquals(col.get(0), 0, "col0");
        assertEquals(col.get(1), 2, "col1");
        assertEquals(col.get(2), 4, "col2");
        assertEquals(col.get(3), 0, "col3");
        assertEquals(col.get(4), 0, "col4");
        assertEquals(col.get(5), 0, "col5");
        assertEquals(col.get(6), 0, "col6");
        assertEquals(col.get(7), 0, "col7");
        assertEquals(col.get(8), 6, "col8");

        col = model.getColumn(8);
        //column 8
        assertEquals(col.get(0), 9, "col0");
        assertEquals(col.get(1), 0, "col1");
        assertEquals(col.get(2), 0, "col2");
        assertEquals(col.get(3), 6, "col3");
        assertEquals(col.get(4), 0, "col4");
        assertEquals(col.get(5), 0, "col5");
        assertEquals(col.get(6), 5, "col6");
        assertEquals(col.get(7), 0, "col7");
        assertEquals(col.get(8), 8, "col8");
    }

    @Test
    void getRow() {
        ArrayList<Integer> row = model.getRow(0);

        //column 0
        assertEquals(row.get(0), 0, "row0");
        assertEquals(row.get(1), 0, "row1");
        assertEquals(row.get(2), 3, "row2");
        assertEquals(row.get(3), 0, "row3");
        assertEquals(row.get(4), 0, "row4");
        assertEquals(row.get(5), 0, "row5");
        assertEquals(row.get(6), 0, "row6");
        assertEquals(row.get(7), 0, "row7");
        assertEquals(row.get(8), 9, "row8");

        row = model.getRow(8);
        //column 8
        assertEquals(row.get(0), 6, "row0");
        assertEquals(row.get(1), 3, "row1");
        assertEquals(row.get(2), 0, "row2");
        assertEquals(row.get(3), 5, "row3");
        assertEquals(row.get(4), 0, "row4");
        assertEquals(row.get(5), 0, "row5");
        assertEquals(row.get(6), 2, "row6");
        assertEquals(row.get(7), 0, "row7");
        assertEquals(row.get(8), 8, "row8");
    }

    @Test
    void getBox() {
        ArrayList<Integer> box = model.getBox(0, 0);

        //column 0
        assertEquals(box.get(0), 0, "box0");
        assertEquals(box.get(1), 0, "box1");
        assertEquals(box.get(2), 3, "box2");
        assertEquals(box.get(3), 2, "box3");
        assertEquals(box.get(4), 0, "box4");
        assertEquals(box.get(5), 0, "box5");
        assertEquals(box.get(6), 4, "box6");
        assertEquals(box.get(7), 6, "box7");
        assertEquals(box.get(8), 1, "box8");

        box = model.getBox(8,8);
        //column 8
        assertEquals(box.get(0), 0, "box0");
        assertEquals(box.get(1), 1, "box1");
        assertEquals(box.get(2), 5, "box2");
        assertEquals(box.get(3), 0, "box3");
        assertEquals(box.get(4), 9, "box4");
        assertEquals(box.get(5), 0, "box5");
        assertEquals(box.get(6), 2, "box6");
        assertEquals(box.get(7), 0, "box7");
        assertEquals(box.get(8), 8, "box8");
    }

}