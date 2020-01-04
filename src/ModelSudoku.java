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

    @Override
    public void load(String text) {

        text = text.replace('.', '0');
        String[] parts = text.split(",");

        display = parseSudokuValues(parts[0]);
        solution = parseSudokuValues(parts[1]);
        userDisplay = parseSudokuValues(parts[0]);
    }

    @Override
    public boolean isOriginalCell(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return (display[row][col] != 0);
        }
        return false;
    }
}