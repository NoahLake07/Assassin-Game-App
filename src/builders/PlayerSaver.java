package builders;

import data.Player;

import java.io.File;
import java.util.ArrayList;

public class PlayerSaver {

    public static ArrayList<String> saveSimple(ArrayList<Player> players){
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            data.add(players.get(i).saveSimple());
        }
        return data;
    }

    public static ArrayList<String> save(ArrayList<Player> players){
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            data.add(players.get(i).save());
        }
        return data;
    }

}
