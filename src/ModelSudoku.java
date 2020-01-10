import sun.security.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Models the 9x9 sudoku game
 *
 * @author Melina Zikou
 */
public class ModelSudoku extends Model{

    public ModelSudoku(boolean wordoku){
        this.wordoku = wordoku;
    }

    public ModelSudoku(){ wordoku = false; }

    //load format as taken from https://qqwing.com/generate.html
    //user display (initial display of the game)
    //2....43..461.........8..9.6...4..8.........3...9....15.2.68..9.63.5..2.8,
    //solution
    //573268149298154367461793582342871956957436821816925734789342615125687493634519278,

    /**
     * Loads the data from a file to set the board.
     * In the load format the user display is filled with dots
     * in the places that have to be vacant.
     * Dots are replaced by zeros.
     * @param text the comma separated strings containing the sudoku puzzle
     */
    @Override
    public void load(String text) {

        text = text.replace('.', '0');
        String[] parts = text.split(",");

        display = parseSudokuValues(parts[0]);
        solution = parseSudokuValues(parts[1]);
        userDisplay = parseSudokuValues(parts[0]);
    }

    /**
     * Checks if a cell value is shown during the whole
     * game and cannot be changed by the user.
     * Used in the plain sudoku version.
     * @param row row coordinate
     * @param col column coordinate
     * @return true if the cell is original
     *         false otherwise
     */
    @Override
    public boolean isOriginalCell(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return (display[row][col] != 0);
        }
        return false;
    }
}