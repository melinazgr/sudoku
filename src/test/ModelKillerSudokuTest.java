package test;

import game.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelKillerSudokuTest {

    ModelKillerSudoku model;

    @BeforeEach
    void setUp() {

model = new ModelKillerSudoku();

        String s = "681974523245831697739652148857413962924786351316295784598127436473568219162349875," +
                "15:00:00:16:00:09:00:11:18:06:00:16:00:00:03:07:00:00:15:19:00:10:06:00:00:10:00:" +
                "00:07:00:00:00:09:12:00:03:12:00:10:26:00:00:00:13:00:00:10:00:00:00:12:00:00:10:" +
                "10:00:22:00:00:15:08:00:00:00:18:00:00:00:00:19:00:21:00:00:00:07:00:00:00:00:00," +
                "111223314223331214411241234421242431324112421314113324213332114244332413244224433";

        model.load(s);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSum() {
        Assertions.assertEquals(model.getSum(0, 0), 15);
        Assertions.assertEquals(model.getSum(0, 1), 0);
        Assertions.assertEquals(model.getSum(0, 8), 18);
        Assertions.assertEquals(model.getSum(8, 8), 0);
    }

    @Test
    void getColor() {
        Assertions.assertEquals(model.getColor(0, 0), 1);
        Assertions.assertEquals(model.getColor(0, 3), 2);
        Assertions.assertEquals(model.getColor(0, 8), 4);
        Assertions.assertEquals(model.getColor(8, 8), 3);
    }

}