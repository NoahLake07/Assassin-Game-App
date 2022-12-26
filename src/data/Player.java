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

    public static final int DEFAULT_LIVES = 3;
    public static final int UNIDENTIFIED_PLAYER = -1;
    public static final int STARTING_SCORE = 0;

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
    public Player(String na, String phNum, String no, int playerID, int sc, int li,
                  ArrayList<Player> tar, ArrayList<Player> as, ArrayList<Player> sh, ArrayList<Player> ki, ArrayList<Timeout> ti) {
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
        this(na, phNum, no, playerID, STARTING_SCORE, DEFAULT_LIVES,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Creates a new player using a player directory line
     * @param pldir
     */
    public Player(String pldir){
        Player myData = decodeSimple(pldir);
        name = myData.name;
        phoneNumber = myData.phoneNumber;
        notes = myData.notes;
        lives = myData.lives;
        score = myData.score;
        PID = myData.PID;
        targets = myData.targets;
        assassins = myData.assassins;
        shots = myData.shots;
        kills = myData.kills;
    }

    public Player(String na, String phNum, String no) {
        this(na, phNum, no, UNIDENTIFIED_PLAYER, STARTING_SCORE, DEFAULT_LIVES,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public static Player decodeSimple(String pldir){
        int i = 0;
        StringBuffer name, number, notes;

        // scan player name
        name = new StringBuffer();
        while(pldir.charAt(i) != ','){
            name.append(pldir.charAt(i++));
        }
        i++;

        // scan player phone number
        number = new StringBuffer();
        while(pldir.charAt(i) != ','){
            number.append(pldir.charAt(i++));
        }
        i++;

        // scan player notes
        notes = new StringBuffer();
        while(i< pldir.length()){
            notes.append(pldir.charAt(i++));
        }

        Player player = new Player(name.toString(), number.toString(), notes.toString());

        if(player.timeouts == null){
            player.timeouts = new ArrayList<>();
        }
        if(player.assassins == null){
            player.assassins = new ArrayList<>();
        }
        if(player.targets == null){
            player.targets = new ArrayList<>();
        }
        if(player.shots == null){
            player.shots = new ArrayList<>();
        }

        return player;
    }

    public boolean isAlive(){
        return lives>0;
    }

    public String saveSimple(){

        /*
        SIMPLE SAVE STRUCTURE OF THE PLAYER:
        Name, Phone#, notes
         */

        StringBuffer sb = new StringBuffer();
        sb.append(name + ",");
        sb.append(phoneNumber + ",");
        sb.append(notes);

        return sb.toString();
    }

    public String simpleTargetString(){
        StringBuffer sb = new StringBuffer();
        sb.append("["+name + "]" + "\t");
        for (int i = 0; i < targets.size(); i++) {
            sb.append(" >"+ targets.get(i).name);
        }

        for (int i = 0; i < assassins.size(); i++) {
            sb.append(" §"+ assassins.get(i).name);
        }
        return String.valueOf(sb);
    }

    public String simpleAssassinString(){
        StringBuffer sb = new StringBuffer();
        sb.append("["+name + "]" + "\t");
        for (int i = 0; i < assassins.size(); i++) {
            sb.append(" §"+ assassins.get(i).name);
        }
        return String.valueOf(sb);
    }

    public String save(){

        /*
        SAVE STRUCTURE OF THE PLAYER:
        ( Name < Phone# < PID < notes < lives < score < killedBy-PID |
        assassin-PID < target-PIDs < shot-PIDs < kill-PIDs < timeout-logs )
         */

        StringBuffer sb = new StringBuffer();

        // basic data
        sb.append("(" + name + "<" + phoneNumber + "<" + PID + "<" + notes + "<" + lives + "<" + score +"<");
        if(killedBy == null){
            sb.append(-1 + "| \t");
        } else {
            sb.append(killedBy.PID + "|");
        }

        // assassin data
        if(assassins != null) {
            for (int i = 0; i < assassins.size(); i++) {
                sb.append("<§" + assassins.get(i).PID);
            }
        }

        // target data
        if(targets != null) {
            for (int i = 0; i < targets.size(); i++) {
                sb.append("<>" + targets.get(i).PID);
            }
        }

        // shot data
        if(shots != null) {
            for (int i = 0; i < shots.size(); i++) {
                sb.append("<^" + shots.get(i).PID);
            }
        }

        // killed data
        if(kills != null) {
            for (int i = 0; i < kills.size(); i++) {
                sb.append("<*" + kills.get(i).PID);
            }
        }

        // time suspension data
        if(timeouts != null) {
            for (int i = 0; i < timeouts.size(); i++) {
                sb.append("<Ø" + timeouts.get(i).saveString());
            }
        }

        sb.append(")");
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

    public void printContactTargets(){
        System.out.println();
        System.out.println("=== "+name+" ===");
        System.out.println("Targets:");
        for (int i = 0; i < targets.size(); i++) {
            System.out.println("> " + targets.get(i).name);
        }
        System.out.println("Assassins:");
        for (int i = 0; i < assassins.size(); i++) {
            System.out.println("> " + assassins.get(i).name);
        }
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
        for (int i = 0; i < targets.size(); i++) {
            System.out.println("> " + targets.get(i).name);
        }
        System.out.println("Assassins:");
        for (int i = 0; i < assassins.size(); i++) {
            System.out.println("> " + assassins.get(i).name);
        }
        System.out.println("====================");
        System.out.println();
    }

}
