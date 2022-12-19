package Util;

import data.Player;

import java.util.ArrayList;

public class PlayerUtil {

    public static boolean debugMode = false;
    public static final int DEFAULT_NUMBER_OF_TARGETS = 1;

    public static int randomInt(int min, int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

    /**
     * Reassigns all targets
     * @param toAssign the players
     * @return the assigned targets
     */
    public static ArrayList<Player> assignTargets(ArrayList<Player> toAssign){
        ArrayList<Player> players = toAssign;

        if(players.size()%2 == 0){
            // number of players is even
        } else {
            // number of players is odd
        }

        // loop through the players
        for (int i = 0; i < players.size(); i++) {

            // reset all values of the players
            players.get(i).assassins = new ArrayList<>();
            players.get(i).targets = new ArrayList<>();

            // find a player that is not yet targeted
            int randomPlayer = randomInt(1,players.size()-1);
            while(players.get(randomPlayer).assassins.size() >= DEFAULT_NUMBER_OF_TARGETS){
                randomPlayer = randomInt(1,players.size()-1);
            }

            // assign the targets and assassins
            players.get(i).targets.add(players.get(randomPlayer));
            System.out.println(players.get(i).name + " \t is now targeting \t " + players.get(randomPlayer).name);
            players.get(randomPlayer).assassins.add(players.get(i));
            System.out.println(players.get(randomPlayer).name + "'s \t assassin is now \t " + players.get(i).name);
        }

        return players;
    }

    public static ArrayList<Player> printReadablePlayerTargets(ArrayList<Player> players){
        System.out.println("PLAYER TARGETS:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).name + "\t is targeting \t" + players.get(i).targets.get(0).name);
        }
        System.out.println("==============");

        return players;
    }

    public static ArrayList<Player> printAllPlayerData(ArrayList<Player> players){

        System.out.println();
        System.out.println("PLAYER-DATA PRINT:");
        for (int i = 0; i < players.size(); i++) {
            players.get(i).printContactDetails();
        }
        System.out.println("==============");

        return players;
    }

    public static ArrayList<Player> indexPID(ArrayList<Player> toIndex){
        ArrayList<Player> list = toIndex;

        for (int i = 0; i < list.size(); i++) {
            list.get(i).PID = i+1;
            if(debugMode){
                System.out.println("Set " +list.get(i).name+"'s PID to " + list.get(i).PID);
            }
        }

        return list;
    }

    public static ArrayList<String> playerDataToArray(ArrayList<Player> toSave){
        ArrayList<Player> list = toSave;
        ArrayList<String> save = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            save.add(list.get(i).save());
            if(debugMode){
                System.out.println("Saved " +list.get(i).name+"'s data to list");
            }
        }

        return save;
    }

}
