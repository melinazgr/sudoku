import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManagement {

    private Map<String, Player> playerMap;

    public PlayerManagement(){
        playerMap = new HashMap<String, Player>();
    }

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

    public boolean searchPlayer(String name){
        return playerMap.containsKey(name);
    }

    public int getPlayerCount(){
        return playerMap.values().size();
    }
}
