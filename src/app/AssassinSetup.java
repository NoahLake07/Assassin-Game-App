package app;

import Debug.ConsoleColor;
import Debug.Printer;
import FFM.FileMaster;
import Util.PlayerUtil;
import builders.GameSaver;
import data.Player;
import data.TestData;

import freshui.Constants;
import freshui.graphics.FButton;
import freshui.gui.Header;
import freshui.gui.input.Input;
import freshui.program.FreshProgram;
import freshui.util.FColor;
import freshui.util.Resizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class AssassinSetup extends FreshProgram {

    /* TODO:
    - Fix the assassin lists to accurately show what the targets
      lists show. Currently they unexpectedly show up in some places and
      others do not. Figure out a way to scan the targeting lists and assign
      the target an assassin, ensuring that every single player is accurate.
    - Complete the player directory creator, and test the compatibility with
      the game setup off the .pldir file.
    - Fix FreshUI to support Input visibility changes
     */

    public static void main(String[] args) {
        new PlayerSetup().start();
    }

    public void test1(){
        // create the players from the test data
        ArrayList<Player> players = TestData.exampleSimplePlayers();

        // randomly assign targets
        players = PlayerUtil.assignTargets(players);

        // assign assassins based on targets
        players = PlayerUtil.updateAssassins(players);

        System.out.println("______________________________________________|");

        // print out the player target data
        PlayerUtil.printAllPlayerTargets(players);

        // check for players assigned to themselves
        PlayerUtil.checkForSuicidalTargets(players);

        // print simplified targets of players
        PlayerUtil.printSimpleTargets(players);

        // convert all the player data to a save string array
        ArrayList<String> playerSaveData = PlayerUtil.playerDataToArray(players);

        // stream the player data into the test output file
        FileMaster.listToFile(playerSaveData, "testData/output/");
    }

    public void test2(){
        ArrayList<Player> players = TestData.suicidalPlayers();

        // assign assassins based on targets
        players = PlayerUtil.updateAssassins(players);

        // check for players assigned to themselves
        PlayerUtil.checkForSuicidalTargets(players);

        // print out the player target data
        PlayerUtil.printAllPlayerTargets(players);

        // convert all the player data to a save string array
        ArrayList<String> playerSaveData = PlayerUtil.playerDataToArray(players);

        // stream the player data into the test output file
        FileMaster.listToFile(playerSaveData, "testData/output/");
    }

    public void test3(){
        ArrayList<Player> players = TestData.examplePlayers1();

        // print out the assassin data
        PlayerUtil.getAllAssassinsOfPlayer(players,players.get(0));

        // check for players assigned to themselves
        PlayerUtil.checkForSuicidalTargets(players);

        // using existing target data, create assassin data
        players = PlayerUtil.updateAssassins(players);

        // print simplified targets of players
        PlayerUtil.printSimpleTargets(players);

        // convert all the player data to a save string array
        ArrayList<String> playerSaveData = PlayerUtil.playerDataToArray(players);

        // stream the player data into the test output file
        FileMaster.listToFile(playerSaveData, "testData/output/");
    }

    public void gameSaveTest(){
        try {
            GameSaver.saveGame("Test Game",TestData.examplePlayers1(),"testData/gameSave1",
                    "1234","abcd","8012223334");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void debugColorTest(){
        System.out.print(ConsoleColor.BLACK_BOLD);
        System.out.println("Black_Bold");
        System.out.print(ConsoleColor.RESET);

        System.out.print(ConsoleColor.YELLOW);
        System.out.print(ConsoleColor.BLUE_BACKGROUND);
        System.out.println("YELLOW & BLUE");
        System.out.print(ConsoleColor.RESET);

        System.out.print(ConsoleColor.YELLOW);
        System.out.println("YELLOW");
        System.out.print(ConsoleColor.RESET);
    }

    // setup chooser ui
    public void init(){
        setProgramName("Choose Action");
        setSize(300,300);
        setBackground(new Color(121, 115, 115));

        Header header = new Header(getWidth(), "Assassin Game Setup", CENTER, this);
        header.setColor(new Color(169, 169, 169));
        add(header, 0,0);

        Color dBC = new Color(190, 190, 190);
        Color hBC = FColor.darker(dBC, 0.8);
        Font bF = new Font("Arial",Font.PLAIN,10);
        FButton setupPlayers = new FButton("<html>Player<p>Setup</html>");
        FButton setupGame = new FButton("<html>New Game</html>");
        setupPlayers.setFont(bF);
        setupGame.setFont(bF);
        setupPlayers.setSize(80,80);
        setupGame.setSize(80,80);
        setupPlayers.setColor(dBC);
        setupGame.setColor(dBC);
        int vertSpacing = 55;
        int horSpacing = 50;
        add(setupPlayers, horSpacing, header.getHeight() + vertSpacing);
        add(setupGame, getWidth() - setupGame.getWidth() - horSpacing, header.getHeight() + vertSpacing);
        setupGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO close previous window
                 setupGame.getProgramParent().setVisible(false);
                new GameSetup().start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setupGame.setColor(hBC);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setupGame.setColor(dBC);
            }
        });
        setupPlayers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setupPlayers.getProgramParent().setVisible(false);
                new PlayerSetup().start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setupPlayers.setColor(hBC);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setupPlayers.setColor(dBC);
            }
        });
    }

    // program to set up player directories
    public static class PlayerSetup extends FreshProgram {

        Color buttonNorm, buttonHov;

        public void init(){
            setSize(300,300);
            setProgramName("Initialize Players");

            // setup colors
            buttonNorm = new Color(171, 204, 204);
            buttonHov = FColor.darker(buttonNorm,0.9);

            // header
            Header header = new Header(getWidth(),"<html>Create Player Directory</html>",CENTER,this);
            header.setColor(new Color(213, 122, 122));
            add(header,0,0);

            // name input
            Input name = new Input("Player Name:",this);
            name.setSize(getWidth()-20,getHeight()/10);
            name.setColor(new Color(225, 185, 153));
            add(name,10,header.getHeight()+10);

            // phone number input
            Input phoneNum = new Input("Phone Number",this);
            phoneNum.setSize(getWidth()-20,getHeight()/10);
            phoneNum.setColor(new Color(176, 235, 153));
            add(phoneNum,10,name.getY()+name.getHeight()+10);

            // notes/bio input
            Input notes = new Input("Notes/Bio",this);
            notes.setSize(getWidth()-20,getHeight()/9);
            notes.setColor(new Color(232, 235, 153));
            add(notes,10,phoneNum.getY()+phoneNum.getHeight()+10);

            // clear button
            FButton clearBtn = new FButton("Clear",getWidth()/4,getHeight()/13);
            add(clearBtn, 10, notes.getY()+notes.getHeight()+13);
            clearBtn.setCornerRadius(5);
            clearBtn.setColor(new Color(203, 118, 86));
            clearBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    name.setInputText("");
                    notes.setInputText("");
                    phoneNum.setInputText("");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    clearBtn.setColor(new Color(208, 107, 73));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    clearBtn.setColor(new Color(203, 118, 86));
                }
            });

            // write player to file button
            FButton writeBtn = new FButton("Write Player To File",getWidth()-20,getHeight()*0.2);
            add(writeBtn, 10, getHeight()-writeBtn.getHeight()-10);
            writeBtn.setCornerRadius(5);
            writeBtn.setColor(buttonNorm);
            writeBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Printer.println("\t > PLAYER ADDED <", Printer.BLUE);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    writeBtn.setColor(buttonHov);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    writeBtn.setColor(buttonNorm);
                }
            });

            // hide all components
            // TODO update FreshUI Inputs to support visibility changes
            name.setVisible(false);
            phoneNum.setVisible(false);
            notes.setVisible(false);
            clearBtn.setVisible(false);
            writeBtn.setVisible(false);
        }
    }

    // program to take player directories and make a new game
    public class GameSetup extends FreshProgram {
        File playerInput, gameOutput;
        String gameTitle = "";

        public void init() {
            setSize(350, 410);
            setProgramName("New Assassin Game");

            Header header = new Header(getWidth(), "Assassin Game Setup Wizard", Constants.CENTER, this);
            add(header, 0, 0);
            header.setColor(new Color(204, 151, 151));

            Input gameTitle = new Input("Game Title", this);
            gameTitle.setColor(new Color(134, 171, 220));
            gameTitle.setWidth(getWidth() - 30);
            add(gameTitle, 10, header.getHeight() + 30);

            Color defaultC1 = new Color(185, 215, 225);
            Color hoverC1 = new Color(157, 181, 190);
            Color defaultC2 = new Color(200, 185, 225);
            Color hoverC2 = new Color(186, 157, 190);

            FButton chooseFile = new FButton("Choose a .pldir file");
            chooseFile.setSize(150, 20);
            chooseFile.setColor(defaultC1);
            add(chooseFile, 10, gameTitle.getY() + gameTitle.getHeight() + 10);
            JLabel selectedFile = new JLabel("No File Selected.");
            selectedFile.setSize(200, 300);
            selectedFile.setVerticalAlignment(SwingConstants.TOP);
            add(selectedFile, chooseFile.getX() + chooseFile.getWidth() + 10, chooseFile.getY());
            chooseFile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    File selected = choosePlayerFile();
                    selectedFile.setText(selected.getName());
                    playerInput = selected;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    chooseFile.setColor(hoverC1);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    chooseFile.setColor(defaultC1);
                }
            });

            FButton chooseGameLocation = new FButton("Choose a save location");
            chooseGameLocation.setSize(150, 20);
            chooseGameLocation.setColor(defaultC2);
            add(chooseGameLocation, 10, chooseFile.getY() + chooseFile.getHeight() + 10);
            JLabel selOutputFile = new JLabel("No Location Selected");
            selOutputFile.setSize(200, 300);
            selOutputFile.setVerticalAlignment(SwingConstants.TOP);
            add(selOutputFile, chooseGameLocation.getX() + chooseGameLocation.getWidth() + 10, chooseGameLocation.getY());

            Input adminPassword = new Input("Admin Password",this);
            adminPassword.setWidth(getWidth()-30);
            add(adminPassword,10, chooseGameLocation.getY()+40);

            FButton createGame = new FButton("Create Game Files");
            createGame.setSize(getWidth() - 20, 50);
            createGame.setColor(new Color(220, 220, 220));
            add(createGame, 10, getHeight() - createGame.getHeight() - 10);
            chooseGameLocation.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    File selected = chooseOutput();
                    selOutputFile.setText(selected.getName());
                    gameOutput = selected;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    chooseGameLocation.setColor(hoverC2);
                    if (selectedFile != null && gameOutput != null) {
                        createGame.setColor(new Color(62, 210, 215));
                    } else {
                        createGame.setColor(new Color(220, 220, 220));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    chooseGameLocation.setColor(defaultC2);
                    if (selectedFile != null && gameOutput != null) {
                        createGame.setColor(new Color(62, 210, 215));
                    } else {
                        createGame.setColor(new Color(220, 220, 220));
                    }
                }
            });
            createGame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedFile != null && gameOutput != null) {
                        File selected = gameOutput;
                        Setup setupWizard = new Setup();
                        setupWizard.loadProfiles(selected.getPath());
                        setupWizard.exportGame(gameOutput.getPath(), "Assassin Game 1");
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selectedFile != null && gameOutput != null) {
                        createGame.setColor(new Color(62, 210, 215));
                    } else {
                        createGame.setColor(new Color(220, 220, 220));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (selectedFile != null && gameOutput != null) {
                        createGame.setColor(new Color(48, 189, 232));
                    } else {
                        createGame.setColor(new Color(220, 220, 220));
                    }
                }
            });

            // Start resizing components on window
            Resizer resizeTool1 = new Resizer(this);
            resizeTool1.add(header);
            //resizeTool1.startResizing();
        }

        private File choosePlayerFile() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(0);

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            int result = fileChooser.showOpenDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                return selectedFile;
            } else {
                return null;
            }
        }

        private File chooseOutput() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                return selectedFile;
            } else {
                return null;
            }
        }
    }

    // basic setup methods
    public class Setup extends FileMaster {
        ArrayList<String> scannedData;
        public ArrayList<Player> participants = new ArrayList<>();

        public void exportGame(String path, String gameName){
            File output = new File(path + "/" + gameName + ".pldir");
        }

        private void printProfiles(ArrayList<Player> profiles) {
            for (int i = 0; i < profiles.size(); i++) {
                profiles.get(i).printContactDetails();
            }
        }

        private void loadProfiles(String pathname) {
            // scan in the data
            File file = new File(pathname);
            if (file.exists()) {
                Scanner s;
                try {
                    s = new Scanner(file);
                } catch (FileNotFoundException var5) {
                    throw new RuntimeException(var5);
                }

                int count = 0;
                ArrayList list = new ArrayList();
                while (s.hasNextLine()) {
                    list.add(s.nextLine());
                    count++;
                }

                s.close();
                scannedData = list;
            }

            // scan all players from data
            for (int i = 0; i < scannedData.size(); i++) {
                String currentLine = scannedData.get(i);
                String name, phoneNum, notes;

                // get player name
                StringBuffer sb = new StringBuffer();
                int ch = 0;
                while (currentLine.charAt(ch) != ',') {
                    sb.append(currentLine.charAt(ch));
                    ch++;
                }
                name = String.valueOf(sb);
                ch++;

                // get player number
                sb = new StringBuffer();
                while (currentLine.charAt(ch) != ',') {
                    sb.append(currentLine.charAt(ch));
                    ch++;
                }
                phoneNum = String.valueOf(sb);

                // get player notes (bio)
                sb = new StringBuffer();
                ch = ch + 2;
                while (currentLine.charAt(ch) != '/') {
                    sb.append(currentLine.charAt(ch));
                    ch++;
                }
                notes = String.valueOf(sb);
                participants.add(new Player(name, phoneNum, notes, i));
            }
        }
    }

}
