package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
