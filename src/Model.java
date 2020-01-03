import java.util.ArrayList;

/**
 * Models all the sudoku variation games
 *
 * @author Melina Zikou
 *
 */
public abstract class Model {

    /**
     * loads sudoku numbers from the drive
     * @param file the name of the file to load
     *
     */
    public abstract void load(String file);

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public abstract int getDisplayCell(int x, int y);

    public abstract void setCell(int x, int y, int z);

    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public abstract boolean isValidCell(int x, int y, int z);

    public abstract int getColor(int x, int y);

    public abstract int getSum(int x, int y);

    public abstract int getSolution(int x, int y);

    public abstract ArrayList<Integer> getHint (int x, int y);

    public abstract int getSize();

    /**
     * Returns the number of blocks of panels in UI
     * @return
     */
    public abstract int getGroupSize();
}
