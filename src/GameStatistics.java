/**
 * enum that stores which game type is played by a player
 */
enum GameType{
    Sudoku,
    KillerSudoku,
    Duidoku
}

/**
 * Manages and stores using an enum a game id attached to a game type
 * in order to save player statistics
 * @author Melina Zikou
 */
public class GameStatistics {

    //number id of games played
    //between the 10 games saved
    private int id;
    private GameType gameType;

    //default constructor
    public GameStatistics(){}

    //constructor
    public GameStatistics(int id, GameType gameType){
        this.id = id;
        this.gameType = gameType;
    }

    /**
     * @return the game id
     */
    public int getId(){
        return id;
    }

    /**
     * sets the game id
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * @return the game type from the enum
     */
    public GameType getGameType(){
        return gameType;
    }

    /**
     * sets the game type
     * @param gameType
     */
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}