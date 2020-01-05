enum GameType{
    Sudoku,
    KillerSudoku,
    Duidoku
}

public class GameStatistics {

    //number id of gamed played
    //between the 10 games saved
    private int id;
    private GameType gameType;

    public GameStatistics(){}

    public GameStatistics(int id, GameType gameType){
        this.id = id;
        this.gameType = gameType;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public GameType getGameType(){
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}