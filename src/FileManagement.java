import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileManagement {

    private Map <String, GameDefinition> gameMap;

    public FileManagement(){
        gameMap = new HashMap <String, GameDefinition>() ;
    }

    public GameDefinition getGame(int id, GameType gameType){
        String key = gameType.toString() + id;
        return gameMap.get(key);
    }

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
     * Takes a string in this form "573268149298154367461793582342871956957436821816925734789342615125687493634519278"
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