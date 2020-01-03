import sun.security.util.ArrayUtil;

import java.util.ArrayList;

/**
 *
 * Models the 9x9 sudoku game
 *
 * @author Melina Zikou
 */
public class ModelSudoku extends Model{

    /**
     *
     */
    int [][] display;
    int [][] solution;
    int [][] userDisplay;

    @Override
    public void load(String text) {

        text = text.replace('.', '0');
        String[] parts = text.split(",");


        display = parseSudokuValues(parts[0]);
        solution = parseSudokuValues(parts[1]);
        userDisplay = parseSudokuValues(parts[0]);

    }

    //TODO check if x,y is in bounds
    @Override
    public int getDisplayCell(int x, int y) {
        return userDisplay[x][y];

    }

    @Override
    public void setCell(int x, int y, int z) {

    }

    @Override
    public boolean isValidCell(int x, int y, int z) {
    return false;
    }

    @Override
    public int getColor(int x, int y) {
        return 0;
    }

    @Override
    public int getSum(int x, int y) {
        return 0;
    }

    @Override
    public int getSolution(int x, int y) {
        return 0;
    }

    @Override
    public ArrayList<Integer> getHint(int x, int y) {
        return null;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public int getGroupSize() {
        return 3;
    }


    private int[][] parseSudokuValues(String s) {
        int [][] data = new int[9][9];

        int i = 0;
        for (char c: s.toCharArray()) {
            int row = i / 9;
            int col = i % 9;
            data[row][col] = c;
            i++;
        }
        return data;
    }

    /**
     * Returns all the registered elements in a column as given by the user
     * @param col
     * @return
     */
    public ArrayList<Integer> getColumn(int col){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            list.add(userDisplay[i][col]);
        }

        return list;
    }

    /**
     *
     * @param row
     * @return
     */
    public ArrayList<Integer> getRow(int row){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            list.add(userDisplay[row][i]);
        }

        return list;
    }

    /**
     *
     * @param col
     * @param row
     * @return
     */
    public ArrayList<Integer> getBox(int col, int row){
        ArrayList<Integer> list = new ArrayList<Integer>();

        int startCol = col / 3;
        int startRow = row / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(userDisplay[startRow * 3 + i][startCol * 3 + j]);
            }
        }

        return list;
    }

}
