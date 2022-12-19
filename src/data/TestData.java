package data;

import FFM.FileMaster;
import Util.PlayerUtil;

import java.util.ArrayList;

public class TestData {

    public static ArrayList<Player> examplePlayers(){
            // add players to the array
            ArrayList<Player> example = new ArrayList<>();
            example.add(Player.decodeSimple("Player A,000-000-0001,Works at office"));
            example.add(Player.decodeSimple("Player B,000-000-0002,No notes"));
            example.add(Player.decodeSimple("Player C,000-000-0003,Has no job"));
            example.add(Player.decodeSimple("Player D,000-000-0004,Works from home"));
            example.add(Player.decodeSimple("Player E,000-000-0005,"));
            example.add(Player.decodeSimple("Player F,000-000-0006,Nifty notes"));
            example.add(Player.decodeSimple("Player G,000-000-0007,Notes."));
            example.add(Player.decodeSimple("Player H,000-000-0008,"));

            example = PlayerUtil.indexPID(example);

            return example;
    }

}
