package game;

public class ModelKillerSudoku extends Model {

    protected int [][] sum;
    protected int [][] color;


    public ModelKillerSudoku(){ wordoku = false; }

    //load format
    //solution
    //681974523245831697739652148857413962924786351316295784598127436473568219162349875,
    //sum
    // 15:00:00:16:00:09:00:11:18:06:00:16:00:00:03:07:00:00:15:19:00:10:06:00:00:10:00:
    // 00:07:00:00:00:09:12:00:03:12:00:10:26:00:00:00:13:00:00:10:00:00:00:12:00:00:10:
    // 10:00:22:00:00:15:08:00:00:00:18:00:00:00:00:19:00:21:00:00:00:07:00:00:00:00:00,
    //color
    // 111223314223331214411241234421242431324112421314113324213332114244332413244224433
    @Override
    public void load(String text) {
        String[] parts = text.split(",");

        solution = parseSudokuValues(parts[0]);
        color = parseSudokuValues(parts[2]);
        sum = parseSudokuSumValues(parts[1]);

        userDisplay = new int[getSize()][getSize()];
        display = new int [getSize()][getSize()];
    }

    @Override
    public void load(GameDefinition game) {
        solution = game.getSolution();
        color = game.getColor();
        sum = game.getSum();

        userDisplay = new int[getSize()][getSize()];
        display = new int [getSize()][getSize()];
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

    /**
     * Returns the number registered to a color as given by the file
     * @param row row coordinate
     * @param col column coordinate
     * @return the color number if column and row are valid
     *         false otherwise
     */
    @Override
    public int getColor(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return color[row][col];
        }
        return -1;
    }

    /**
     * Returns the sum of the killer sudoku game on each cell as given by the file
     * @param row row coordinate
     * @param col column coordinate
     * @return the sum number if column and row are valid
     *         false otherwise
     */
    @Override
    public int getSum(int row, int col) {
        if((row >= 0 && row < getSize()) || (col >= 0 && col < getSize())){
            return sum[row][col];
        }
        return -1;
    }
}