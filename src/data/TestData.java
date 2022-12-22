package data;

import Util.PlayerUtil;

import java.util.ArrayList;

public class TestData {

    public static ArrayList<Player> exampleSimplePlayers(){
            // add players to the array
            ArrayList<Player> example = new ArrayList<>();
            example.add(Player.decodeSimple("Player A,000-000-0001,notes"));
            example.add(Player.decodeSimple("Player B,000-000-0002,notes"));
            example.add(Player.decodeSimple("Player C,000-000-0003,notes"));
            example.add(Player.decodeSimple("Player D,000-000-0004,notes"));
            example.add(Player.decodeSimple("Player E,000-000-0005,notes"));
            example.add(Player.decodeSimple("Player F,000-000-0006,notes"));
            example.add(Player.decodeSimple("Player G,000-000-0007,notes"));
            example.add(Player.decodeSimple("Player H,000-000-0008,notes"));

            example = PlayerUtil.indexPID(example);

            return example;
    }

        public static ArrayList<Player> suicidalPlayers(){
                // add players to the array
                ArrayList<Player> example = new ArrayList<>();
                example.add(Player.decodeSimple("Player A,000-000-0001,notes"));
                example.add(Player.decodeSimple("Player B,000-000-0002,notes"));

                // index players
                example = PlayerUtil.indexPID(example);

                // assign targets to themselves
                example.get(0).targets = new ArrayList<>();
                example.get(1).targets = new ArrayList<>();
                example.get(0).targets.add(example.get(0));
                example.get(1).targets.add(example.get(1));

                return example;
        }

}
