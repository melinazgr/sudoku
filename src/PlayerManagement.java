import java.io.*;
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
