package game;

import game.GameDefinition;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Models all the sudoku variation games
 *
 * @author Melina Zikou
 *
 */
public abstract class Model {

    protected int [][] display;
    protected int [][] solution;
    protected int [][] userDisplay;

    protected boolean wordoku;

    /**
     * loads the game data
     * @param text the comma separated strings containing the sudoku puzzle
     *
     */
    public abstract void load(String text);
    public abstract void load(GameDefinition game);

    public int getDisplayCell(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return userDisplay[row][col];
        }
        return -1;
    }

   // implemented in the game.ModelSudoku class
    public boolean isOriginalCell(int row, int col) {
        return false;
    }

    /**
     * Sets the value chosen by the user to a cell
     * @param row row coordinate
     * @param col column coordinate
     * @param num cell value
     * @return true if the number was put in the cell
     *         false otherwise
     */
    public boolean setCell(int row, int col, int num) {
        if(isValidCell(row, col, num)){
            userDisplay[row][col] = num;
            return true;
        }
        return false;
    }

    /**
     * Checks if a number is allowed by the
     * rules of sudoku to be put in a cell
     * @param row row coordinate
     * @param col column coordinate
     * @param num  cell value
     * @return true if it is valid
     *         false otherwise
     */
    public boolean isValidCell(int row, int col, int num) {
        if(getColumn(col).contains(num) || getRow(row).contains(num) || getBox(row, col).contains(num))
            return false;
        return true;
    }

    // implemented in the game.ModelKillerSudoku class
    public int getColor(int row, int col) {
        return 0;
    }

    // implemented in the game.ModelKillerSudoku class
    public int getSum(int row, int col) {
        return 0;
    }

    /**
     * Shows the solution of the game played
     * @param row row coordinate
     * @param col column coordinate
     * @return the solution of the board if a row and column is valid
     *         -1 otherwise
     */
    public int getSolution(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return solution[row][col];
        }
        return -1;
    }

    /**
     *  Shows the available options of numbers
     *  in a cell when the hint is enabled
     * @param row row coordinate
     * @param col column coordinate
     * @return an ArrayList of the numbers available to be played
     */
    public ArrayList<Integer> getHint(int row, int col) {
        Collection<Integer> horizontal = getRow(row);
        Collection<Integer> vertical = getColumn(col);
        Collection<Integer> box = getBox(row, col);

        Collection<Integer> union = new ArrayList<>();
        union.addAll(vertical);
        union.addAll(horizontal);
        union.addAll(box);

        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            if(!union.contains(i)){
                numbers.add(i);
            }
        }

        return numbers;
    }

    /**
     * @return the size of the blocks in the sudoku ( 9 by 9 board)
     */
    public int getSize() {
        return 9;
    }

    /**
     * Each board is divided into smaller boxes.
     * @return the number of cells in column/row of a box
     */
    public int getGroupSize() {
        return 3;
    }

    /**
     * Clears a cell from the previous choice of hte user
     * @param row row coordinate
     * @param col column coordinate
     */
    public void clearCell(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            userDisplay[row][col] = display [row][col];
        }
    }

    /**
     * Clears the board from previous entries
     */
    public void restartGame() {

        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                userDisplay[row][col] = display[row][col];
            }
        }
    }

    /**
     * Shows the solution of a game
     */
    public void solveSudoku() {
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                userDisplay[row][col] = solution[row][col];
            }
        }
    }

    /**
     * If all the cells are completed the game has ended
     * @return true if the game is over
     *         false otherwise
     */
    public boolean gameOver() {
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                if(userDisplay[row][col] != solution[row][col]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param cell number of a cell
     * @return number of cell if game is sudoku
     *         letter of cell if game is wordoku
     */
    public char toCharCell(int cell){
        if(wordoku) {
            if(cell != 0) {
                return (char) ('A' + cell - 1);
            }
        }
        else{
            return (char) ('0' + cell);
        }

        return '\0';
    }

    public boolean isWordoku() {
        return wordoku;
    }

    /**
     * Takes a string in this form "573268149298154367461793582342871956957436821816925734789342615125687493634519278"
     * and converts in to 2 - dimensional integer array for each cell in the sudoku
     * @param s the string containing the game
     * @return the 2 - dimensional array
     */
    protected int[][] parseSudokuValues(String s) {
        int [][] data = new int[9][9];

        int i = 0;
        for (char c: s.toCharArray()) {
            int row = i / 9;
            int col = i % 9;
            data[row][col] = c - '0';
            i++;
        }
        return data;
    }

    /**
     * Returns all the registered elements in a column as given by the user
     * @param col column coordinate
     * @return an Arraylist with the column elements
     */
    public ArrayList<Integer> getColumn(int col){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            list.add(userDisplay[i][col]);
        }

        return list;
    }

    /**
     * Returns all the registered elements in a row as given by the user
     * @param row row coordinate
     * @return an Arraylist with the row elements
     */
    public ArrayList<Integer> getRow(int row){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            list.add(userDisplay[row][i]);
        }

        return list;
    }

    /**
     * Returns all the registered elements in a box as given by the user
     * @param row row coordinate
     * @param col column coordinate
     * @return an ArrayList with the box elements
     */
    public ArrayList<Integer> getBox(int row, int col){
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