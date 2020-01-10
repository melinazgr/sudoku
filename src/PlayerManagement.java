import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages all the players and the games the have played
 *
 * @author Melina Zikou
 */
public class PlayerManagement {

    private Map<String, Player> playerMap;

    //constructor that creates a map matching the name of the player
    //to a player object which matches the player to a game and a game id
    public PlayerManagement(){
        playerMap = new HashMap<String, Player>();
    }

    /**
     * Creates a new player
     * @param name of the player
     * @return the player name
     */
    public Player createPlayer(String name){
        if(!playerMap.containsKey(name)){
            Player player = new Player(name);
            playerMap.put(name, player);
            return player;
        }
        else{
            return playerMap.get(name);
        }
    }

    /**
     * Searches if a player exists
     * @param name of the player
     * @return the name of the player
     */
    public boolean searchPlayer(String name){
        return playerMap.containsKey(name);
    }

    /**
     * Counts how many players exist
     * @return the number of the players
     */
    public int getPlayerCount(){
        return playerMap.values().size();
    }

    /**
     * Saves the players information to a given path/filename
     * @param path filename used to save the statistics and other information needed
     */
    public void save(String path){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(getPlayerCount());
            for(Player p: playerMap.values()){
                out.writeObject(p);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads previous files with players information
     * @param path filename used to save the statistics and other information needed
     */
    public void load(String path){
        playerMap.clear();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
            Player p;
            int count = (int) in.readObject();
            for (int i = 0; i < count ; i++) {
                p = (Player) in.readObject();
                playerMap.put(p.getName(), p);
            }
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}