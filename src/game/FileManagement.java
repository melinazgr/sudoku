package game;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads game information from the drive and organises it
 * proper form in order to be used to create the games.
 *
 * @author Melina Zikou
 */
public class FileManagement {

    //maps game id with a GameDefinition object which contains all the information about the game table
    private Map <String, GameDefinition> gameMap;

    // constructor that creates the map
    public FileManagement(){
        gameMap = new HashMap <String, GameDefinition>() ;
    }

    /**
     * Gets the information for the game tale
     * @param id 1 - 10 marking the available games
     * @param gameType Sudoku, KillerSudoku, Duidoku
     * @return the game to be played
     */
    public GameDefinition getGame(int id, GameType gameType){
        String key = gameType.toString() + id;
        return gameMap.get(key);
    }

    /**
     * Loads the csv file with game information.
     * csv file format:
     * GameType (1 Sudoku, 2 KillerSudoku), id (1 -10), display, solution [first 10 lines - Sudoku game]
     *                                                           solution, sum, color [last 10 lines - KillerSudoku game]
     * @param path file path
     * @throws IOException in case the file could not be opened
     */
    public void load(String path) throws IOException {

            try (BufferedReader in = new BufferedReader(new FileReader(path))){

                String line;
                while ((line = in.readLine()) != null){
                    line = line.replace('.', '0');
                    String[] parts = line.split(",");

                    // 1 = sudoku game
                    if(parts[0].equals("1")){
                        int id = Integer.valueOf(parts[1]);
                        int [][] display = parseSudokuValues(parts[2]);
                        int [][] solution = parseSudokuValues(parts[3]);
                        int [][] userDisplay = parseSudokuValues(parts[2]);

                        GameDefinition gameSudoku = new GameDefinition(id, GameType.Sudoku, solution, display);

                        String key = GameType.Sudoku.toString() + id;
                        gameMap.put(key, gameSudoku);
                    }
                    // 2 = killer sudoku game
                    else if (parts[0].equals("2")){
                        int id = Integer.valueOf(parts[1]);
                        int [][] solution = parseSudokuValues(parts[3]);
                        int [][] sum = parseSudokuSumValues(parts[4]);
                        int [][] color = parseSudokuValues(parts[5]);

                        GameDefinition gameKillerSudoku = new GameDefinition(id, GameType.KillerSudoku, solution, sum, color);

                        String key = GameType.KillerSudoku.toString() + id;
                        gameMap.put(key, gameKillerSudoku);
                    }
                    else{
                        throw new IllegalArgumentException("Illegal game type");
                    }
                }
            }
    }

    /**
     *Takes a string in this form "573268149298154367461793582342871956957436821816925734789342615125687493634519278"
     * and converts in to 2 - dimensional integer array for each cell in the sudoku
     * @param s the string containing the game
     * @return the 2 - dimensional array
     */
    protected int[][] parseSudokuValues(String s) {
        int[][] data = new int[9][9];

        int i = 0;
        for (char c : s.toCharArray()) {
            int row = i / 9;
            int col = i % 9;
            data[row][col] = c - '0';
            i++;
        }
        return data;
    }

    /**
     * Takes a string in this form wich contains the sum of each cell
     *  "5:00:00:16:00:09:00:11:18:06:00:16:00:00:03:07:00:00:15:19:00:10:06:00:00:10:00:
     *  00:07:00:00:00:09:12:00:03:12:00:10:26:00:00:00:13:00:00:10:00:00:00:12:00:00:10:
     *  10:00:22:00:00:15:08:00:00:00:18:00:00:00:00:19:00:21:00:00:00:07:00:00:00:00:00"
     *  and converts in to 2 - dimensional integer array for each cell in the sudoku.
     *
     * 00 indicates zero sum
     *
     * @param s the string containing the sums
     * @return 2 - dimensinal array with sums
     */
    private int[][] parseSudokuSumValues (String s) {
        int [][] data = new int[9][9];

        String [] sums = s.split(":");

        int i = 0;
        for (String sum: sums) {
            int row = i / 9;
            int col = i % 9;
            data[row][col] = Integer.parseInt(sum);
            i++;
        }
        return data;
    }
}