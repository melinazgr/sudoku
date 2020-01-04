import java.util.ArrayList;

/**
 * Models all the sudoku variation games
 *
 * @author Melina Zikou
 *
 */
public abstract class Model {

    protected boolean wordoku;

    /**
     * loads sudoku numbers from the drive
     * @param file the name of the file to load
     *
     */
    public abstract void load(String file);

    /**
     * Get user cell value at given coordinates.
     *
     * @param row row coordinate
     * @param col column coordinate
     * @return cell value, -1 if coordinates out of bounds
     */
    public abstract int getDisplayCell(int row, int col);

    /**
     * Checks if a given cell is supposed to be displayed
     * by default on the sudoku board or filled in by the user
     * @param row row coordinate
     * @param col column coordinate
     * @return true if it is original
     *         false otherwise
     */
    public abstract boolean isOriginalCell (int row, int col);

    public abstract boolean setCell(int row, int col, int num);


    /**
     * Checks if setting a number is a valid number for
     * the sudoku at the current state of the game
     * @param row
     * @param col
     * @param num
     * @return
     */
    public abstract boolean isValidCell(int row, int col, int num);

    public abstract int getColor(int row, int col);

    public abstract int getSum(int row, int col);

    /**
     * Get solution cell value at given coordinates.
     *
     * @param row row coordinate
     * @param col column coordinate
     * @return cell value, -1 if coordinates out of bounds
     */
    public abstract int getSolution(int row, int col);

    /**
     * Finds the elements allowed to be played in a cell with given coordinates
     * @param row row coordinates
     * @param col column coordinates
     * @return an array list with all the allowed values
     */
    public abstract ArrayList<Integer> getHint (int row, int col);

    public abstract int getSize();

    /**
     * Returns the number of blocks of panels in UI
     * @return
     */
    public abstract int getGroupSize();

    public abstract void clearCell(int row, int col);

    public abstract void restartGame ();

    public abstract void solveSudoku();

    /**
     *
     * @return true when game is finished successfully
     *          false otherwise
     */
    public abstract boolean gameOver();

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
}


