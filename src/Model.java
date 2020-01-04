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

    public int getDisplayCell(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return userDisplay[row][col];
        }
        return -1;
    }

    public boolean isOriginalCell(int row, int col) {
        return false;
    }

    public boolean setCell(int row, int col, int num) {
        if(isValidCell(row, col, num)){
            userDisplay[row][col] = num;
            return true;
        }
        return false;
    }

    public boolean isValidCell(int row, int col, int num) {
        if(getColumn(col).contains(num) || getRow(row).contains(num) || getBox(row, col).contains(num))
            return false;
        return true;
    }

    public int getColor(int row, int col) {
        return 0;
    }

    public int getSum(int row, int col) {
        return 0;
    }

    public int getSolution(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return solution[row][col];
        }
        return -1;
    }

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

    public int getSize() {
        return 9;
    }

    public int getGroupSize() {
        return 3;
    }

    public void clearCell(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            userDisplay[row][col] = display [row][col];
        }
    }

    public void restartGame() {

        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                userDisplay[row][col] = display[row][col];
            }
        }
    }

    public void solveSudoku() {
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                userDisplay[row][col] = solution[row][col];
            }
        }
    }

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


