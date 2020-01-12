package game;

import java.util.ArrayList;
import java.util.Collection;

public class ModelDuidoku extends Model {

    public ModelDuidoku(boolean wordoku){
        this.wordoku = wordoku;

        userDisplay = new int[getSize()][getSize()];

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                userDisplay[i][j] = 0;
            }
        }
    }

    public ModelDuidoku(){ wordoku = false; }

    @Override
    public boolean gameOver() {

        return false;
    }

    @Override
    public void load(String text) {

    }

    @Override
    public void load(GameDefinition game) {

    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public int getGroupSize() {
        return 2;
    }


}