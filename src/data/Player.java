package data;

import java.util.ArrayList;

public class Player {

    // player contact info
    public String name, phoneNumber, notes;

    // player details
    public int PID, lives, score;
    public ArrayList<Player> targets, assassins, shots, kills;
    public Player killedBy;
    public ArrayList<Timeout> timeouts;

    /**
     * Creates a new player using given values
     *
     * @param playerID The ID assigned to the player
     * @param sc       Score
     * @param li       Lives
     * @param tar      Targets
     * @param as       Assassins (The people hunting them)
     * @param sh       Shots (who've they've shot)
     * @param ki       Kills
     * @param ti       Timeouts
     */
    public Player(String na, String phNum, String no, int playerID, int sc, int li, ArrayList<Player> tar, ArrayList<Player> as, ArrayList<Player> sh, ArrayList<Player> ki, ArrayList<Timeout> ti) {
        name = na;
        phoneNumber = phNum;
        notes = no;
        lives = li;
        score = sc;
        PID = playerID;
        targets = tar;
        assassins = as;
        shots = sh;
        kills = ki;
    }

    /**
     * NEW PLAYER, values will be defaulted.
     * @param playerID
     */
    public Player(String na, String phNum, String no, int playerID) {
        this(na, phNum, no, playerID, 0000, 3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Creates a new player using a player directory line
     * @param pldir
     */
    public Player(String pldir){

    }

    public boolean isAlive(){
        return lives>0;
    }

    public String getSimpleSaveString(){
        StringBuffer sb = new StringBuffer();
        sb.append(name + ",");
        sb.append(phoneNumber + ",");
        sb.append(notes + "\\");

        return sb.toString();
    }

    public void printContact() {
        System.out.println("=== CONTACT INFO ===");
        System.out.println("Name: " + name);
        System.out.println("Phone#: " + phoneNumber);
        System.out.println("PID: " + PID);
        System.out.println("Notes: " + notes);
        System.out.println("====================");
        System.out.println();
    }

    public void printContactDetails() {
        System.out.println("=== CONTACT INFO ===");
        System.out.println("Name:" + name);
        System.out.println("Phone#: " + phoneNumber);
        System.out.println("PID: " + PID);
        System.out.println("Notes: " + notes);
        System.out.println();
        if(isAlive()){
            System.out.println("data.Player is ALIVE");
        } else{
            System.out.println("data.Player is DEAD");
        }
        System.out.println("Lives: " + lives);
        System.out.println("Targets:");
        for (int i = 0; i < targets.size()-1; i++) {
            System.out.println("> " + targets.get(i).name);
        }
        System.out.println("Assassins:");
        for (int i = 0; i < assassins.size()-1; i++) {
            System.out.println("> " + assassins.get(i).name);
        }
        System.out.println("====================");
        System.out.println();
    }

}
