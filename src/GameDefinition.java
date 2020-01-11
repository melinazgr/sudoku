public class GameDefinition {

    int id;
    GameType gameType;
    int [][] display;
    int [][] solution;
    int [][] color;
    int [][] sum;

    public GameDefinition(int id, GameType gameType, int [][] solution, int [][] display){
        this.id = id;
        this.gameType = gameType;
        this.solution = solution;
        this.display = display;
    }

    public GameDefinition(int id, GameType gameType, int [][] solution, int [][] sum, int [][] color){
        this.id = id;
        this.gameType = gameType;
        this.solution = solution;
        this.sum = sum;
        this.color = color;
    }

    public GameType getGameType(){
        return gameType;
    }

    public int getId(){
        return id;
    }

    public int[][] getDisplay() {
        return display;
    }

    public int[][] getSolution() {
        return solution;
    }

    public int[][] getSum() {
        return sum;
    }

    public int[][] getColor() {
        return color;
    }
}