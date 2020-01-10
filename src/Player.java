import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to manage player names
 *
 * @author Melina Zikou
 */
public class Player implements Serializable {

    private String name;
    private ArrayList<GameStatistics> games;

    // constructor to create ArrayList containing player names
    public Player(){
        games = new ArrayList<GameStatistics>();
    }

    public Player(String name){
        this();
        this.name = name;
    }

    /**
     * gets the name of a player
     * @return the name of a player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player
     * @param name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets an ArrayList with games played by a player
     * @return the ArrayList with the games played
     */
    public ArrayList<GameStatistics> getGames() {
        return games;
    }

    /**
     * Adds a game played by a player using the game id and the game type
     * @param id the if of one of the 10 games saved
     * @param gameType Sudoku, killer sudoku or duidoku game
     */
    public void addGame(int id, GameType gameType){
        if(findGame(id, gameType) != null){
            return;
        }
        games.add(new GameStatistics(id, gameType));
    }

    /**
     * Checks if one of 10 games saved, has been played by a certain player
     * @param id the if of one of the 10 games saved
     * @param gameType Sudoku, killer sudoku or duidoku game
     * @return the game if it's been played
     *         null otherwise
     */
    public GameStatistics findGame(int id, GameType gameType) {
        for (GameStatistics g: games) {
            if(g.getId() == id && g.getGameType() == gameType){
               return g;
            }
        }
        return null;
    }
}