package game;

/**
 * Contains all the information taken from the game file
 *
 * @author Melina Zikou
 */
public class GameDefinition {

    int id;
    GameType gameType;
    int [][] display;
    int [][] solution;
    int [][] color;
    int [][] sum;

    // constructor for game sudoku
    public GameDefinition(int id, GameType gameType, int [][] solution, int [][] display){
        this.id = id;
        this.gameType = gameType;
        this.solution = solution;
        this.display = display;
    }

    // constructor for game killer sudoku
    public GameDefinition(int id, GameType gameType, int [][] solution, int [][] sum, int [][] color){
        this.id = id;
        this.gameType = gameType;
        this.solution = solution;
        this.sum = sum;
        this.color = color;
    }

    /**
     * Gets the game type (sudoku, killerSudoku)
     * @return the game type
     */
    public GameType getGameType(){
        return gameType;
    }

    /**
     * Gets the id of the game (1 - 10)
     * @return the game id
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the game display for game sudoku
     * (numbers showing at the game board at the start of the game)
     * @return the game display array
     */
    public int[][] getDisplay() {
        return display;
    }

    /**
     * Gets the game solution
     * @return the game solution array
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Gets the sum of the cells for killer sudoku game
     * @return the sums array
     */
    public int[][] getSum() {
        return sum;
    }

    /**
     * Gets the color of the cells for killer sudoku game
     * @return the colors array
     */
    public int[][] getColor() {
        return color;
    }
}