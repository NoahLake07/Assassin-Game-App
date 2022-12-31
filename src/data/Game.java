package data;

import Debug.Printer;
import Util.PlayerUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public ArrayList<Player> players = null;
    public String gameTitle = null;
    public String twilioSID = null;
    public String twilioAUTH = null;
    public String twilioNUM = null;

    public Game(ArrayList<Player> p, String title, String messageSID, String messageAuth, String number){
        players = p;
        gameTitle = title;
        twilioAUTH = messageAuth;
        twilioSID = messageSID;
        twilioNUM = number;
    }

    public Game(){
        players = new ArrayList<>();
        gameTitle = "null";
        twilioSID = "null";
        twilioAUTH = "null";
        twilioNUM = "null";
    }

    public static Game loadGameFromFile(File file){

        return null;
    }

    public static Game loadGameFromFile(String path){
        return loadGameFromFile(new File(path));
    }

    public class Loader {

        File data;
        Scanner scr;
        // create tracker variables
        int i = 0;

        Game game = new Game();

        public Loader(File file){
            data = file;
        }

        public void startScan(){
            // create file scanner
            try {
                scr = new Scanner(data);
                Printer.println("\t > STARTED GAME LOAD FROM " + data.getPath(), Printer.GREEN);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            // make sure that the file is an approved file that contains all game data
            if(scr.nextLine().contains("@approvedFile")) {
                // increment past the first line now that we know the file is approved
                i++;

                Printer.println("\t > FILE IS APPROVED. DECODE STARTING... <", Printer.BLUE);
                Printer.println("\t\t LINES DECODED: ");

                // scan each line of the file provided until the game file closes.
                while (scr.hasNextLine()) {
                    decodeLine(scr.nextLine());
                    i++;
                }
            }
        }

        private void decodeLine(String data){
            int c = 0;

            // end of file
            if(data.contains("}")){
                Printer.println("END OF FILE AT LINE " + i);
            }

            // player data
            if(data.startsWith("(")){
                // decode the player into its own player object
                // add the player to the game's ArrayList of players
            }

            // twilio sid
            if(data.startsWith("¿")){
                c++;
                StringBuffer sb = new StringBuffer();
                while(data.charAt(c) != '¿'){
                    sb.append(data.charAt(c));
                }
                game.twilioSID = sb.toString();
            }

            // twilio account auth token
            if(data.startsWith("±")){
                c++;
                StringBuffer sb = new StringBuffer();
                while(data.charAt(c) != '±'){
                    sb.append(data.charAt(c));
                }
                game.twilioAUTH = sb.toString();
            }

            // twilio number
            if(data.startsWith("#")){
                c++;
                StringBuffer sb = new StringBuffer();
                while(data.charAt(c) != '#'){
                    sb.append(data.charAt(c));
                }

                game.twilioNUM = sb.toString();
            }
        }

    }

    public class Saver {

        /**
         * Saves all game data to a specified path. The file will be converted to an assassin file type (.asn)
         * @param gameTitle The game title
         * @param playerData The ArrayList of player data to be streamed into the game data
         * @param savePath The location of the final file to be saved to
         * @param ACCOUNT_SID The twilio account sid
         * @param AUTH_TOKEN The twilio account auth token
         * @param TWILIO_NUMBER The twilio number purchased from twilio
         * @throws IOException If the file fails to create, this method will throw an IO Exception.
         */
        public static void saveGame(String gameTitle, ArrayList<Player> playerData, String savePath,
                                    String ACCOUNT_SID, String AUTH_TOKEN, String TWILIO_NUMBER) throws IOException {

            // instantiate the file writer
            FileWriter save;
            try {
                save = new FileWriter(savePath + "/" + gameTitle + ".asn/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // open the file with approved char sequence
            save.append("@approvedFile\r");

            // game file title, player data opener
            save.append(gameTitle + "{\r");

            // stream all player save strings into file
            for (int i = 0; i < playerData.size(); i++) {
                save.append(playerData.get(i).save() + "\r");
            }

            // stream twilio account info into game file
            save.append("¿"+ACCOUNT_SID+"¿\r"); // account sid
            save.append("±"+AUTH_TOKEN+"±\r"); // account auth token
            save.append("#"+TWILIO_NUMBER+"#\r"); // number purchased with twilio

            // end game data
            save.append("}\r");

            // close the file and write the information
            save.close();
        }

        public static void saveGame(Game game, String pathToSave){
            try {
                saveGame(game.gameTitle, game.players, pathToSave, game.twilioSID,game.twilioAUTH,game.twilioNUM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
