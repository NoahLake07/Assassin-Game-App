package builders;

import data.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerBuilder extends ArrayList<Player> {

    private File given;

    public PlayerBuilder(File playerDirectory){
        Scanner scr;
        try {
            scr = new Scanner(playerDirectory);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while(scr.hasNextLine()){
            add(Player.decodeSimple(scr.nextLine()));
        }

        for (int i = 0; i < this.size(); i++) {
            get(i).PID = i+1;
        }
    }

    public void printData(){
        for (int i = 0; i < this.size(); i++) {
            get(i).printContact();
        }
    }

    public ArrayList<Player> getPlayerData(){
        return this;
    }

    public PlayerBuilder(String path){
        this(new File(path));
    }

    public static ArrayList<Player> build(File pldir){
        return new PlayerBuilder(pldir);
    }

    public static ArrayList<Player> build(String pldirPath){
        return new PlayerBuilder(pldirPath);
    }

}
