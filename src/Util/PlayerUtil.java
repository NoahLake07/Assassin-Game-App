package Util;

import data.Player;

import java.util.ArrayList;

public class PlayerUtil {

    public static boolean debugMode = false;
    public static final int DEFAULT_NUMBER_OF_TARGETS = 1;

    public static void printSimpleTargets(ArrayList<Player> players){
        System.out.println();
        System.out.println("\t ALL SIMPLE PLAYER TARGET DATA");
        System.out.println();

        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).simpleTargetString());
        }
        System.out.println();
    }

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

        // loop through the players and assign targets
        for (int i = 0; i < players.size(); i++) {

            // reset all values of the players
            players.get(i).assassins = new ArrayList<>();
            players.get(i).targets = new ArrayList<>();


            // instantiate random int
            int randomPlayer=randomInt(1,players.size()-1);

            /*  while the random player is assigned to someone who already has a target,
                generate a different number. In other words, make sure that the random
                player that will be targeted by this player does not already have an assassin.
             */
            do {
                randomPlayer = randomInt(1,players.size()-1);
                System.out.println("randomizing for \t" + players.get(i).name);
            } while (
                     players.get(randomPlayer).assassins.size() >= DEFAULT_NUMBER_OF_TARGETS // random player's assassin list size is more than a specified amount
                     || players.get(randomPlayer) == players.get(i) // random player selected is the same player
                    );

            if(
                    players.get(randomPlayer).assassins.size() >= DEFAULT_NUMBER_OF_TARGETS // random player's assassin list size is more than a specified amount
                    || players.get(randomPlayer) == players.get(i) // random player selected is the same player
            ){
                System.out.println(">> RANDOMIZING LOOP FAILED TO SEE THAT \t"+ players.get(i).name +"\t TARGETS ITSELF <<");
            }

            // add the target for the current player
            players.get(i).targets.add(players.get(randomPlayer));
        }

        // trace the targets and assign a node for the assassin data
        //players = updateAssassins(players);

        return players;
    }

    public static void checkForSuicidalTargets(ArrayList<Player> players){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).targets.contains(players.get(i))){
                System.out.println("> "+players.get(i).name + "\t is targeting themselves.");
            }
        }
    }

    /**
     * Using existing target data, this method goes through all players from the list
     * inputted through the parameter, assigns a value to the assassin data of the players,
     * and returns the new list with the updated data.
     * @return updated list containing player data with new assassins assigned based from
     * given targets.
     */
    public static ArrayList<Player> updateAssassins(ArrayList<Player> players){

        // loop through the players
        for (int i = 0; i < players.size(); i++) {
            // check to see if the data is null
            if(players.get(i).targets == null){
                System.out.println(">> CANNOT ASSIGN NULL DATA TO '"+ players.get(i).name+"' <<");
            } else {
                // print out the quantity of targets that the player has
                System.out.println("> "+ players.get(i).name + "\t has " + players.get(i).targets.size() + "\t targets.");

                // delete all assassin data of target to be ready for the refresh
                players.get(i).assassins = new ArrayList<>();

                // loop through all the targets of the specified player
                for (int j = 0; j < players.get(i).targets.size(); j++) {
                    // get the current target
                    Player target = players.get(i).targets.get(j);

                    // directly add the assassin to the target's data
                    players.get(target.PID-1).assassins.add(players.get(i));
                }
            }
        }

        return players;
    }

    public static ArrayList<Player> getAllAssassinsOfPlayer(ArrayList<Player> players, Player player){

        ArrayList<Player> foundPlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            // if the current player has player as a target, add them to the list
            if(players.get(i).targets.contains(player)){
                foundPlayers.add(players.get(i));
                System.out.println(player.name + "\t is the assassin of \t" + players.get(i).name);
            }
        }

        return foundPlayers;
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

    public static ArrayList<Player> printAllPlayerTargets(ArrayList<Player> players){

        System.out.println();
        System.out.println("\t ALL PLAYER TARGETING DATA:");
        for (int i = 0; i < players.size(); i++) {
            players.get(i).printContactTargets();
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
                System.out.println("Saved " + list.get(i).name+"'s data to list");
            }
        }

        return save;
    }

}
