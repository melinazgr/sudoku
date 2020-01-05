import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Melina Zikou
 */
public class Player implements Serializable {

    private String name;
    private ArrayList<GameStatistics> games;

    public Player(){
        games = new ArrayList<GameStatistics>();
    }

    public Player(String name){
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<GameStatistics> getGames() {
        return games;
    }
    
    public void addGame(int id, GameType gameType){
        if(findGame(id, gameType) != null){
            return;
        }
        games.add(new GameStatistics(id, gameType));
    }

    public GameStatistics findGame(int id, GameType gameType) {
        for (GameStatistics g: games) {
            if(g.getId() == id && g.getGameType() == gameType){
               return g;
            }
        }
        return null;
    }
}