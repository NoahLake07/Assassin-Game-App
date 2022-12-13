package app;

import FFM.FileMaster;
import builders.PlayerBuilder;
import builders.PlayerSaver;
import data.Player;
import data.Time;

import data.Timeout;
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
import java.util.ArrayList;
import java.util.Scanner;

class AssassinSetup extends FreshProgram {

    // type of setup chooser ui
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

    public static void main(String[] args) {
        //new AssassinSetup().start();

        Player a = Player.decodeSimple("Player A,000-000-0001,Works at office");
        Player b = Player.decodeSimple("Player B,000-000-0002,No notes");
        Player x = Player.decodeSimple("Player C,000-000-0003,Other notes");

        a.PID = 1;
        b.PID = 2;
        x.PID = 3;

        a.assassins.add(x);
        b.assassins.add(a);
        x.assassins.add(b);
        a.targets.add(b);
        b.targets.add(x);
        x.targets.add(a);

        a.timeouts.add(new Timeout());

        ArrayList<String> y = new ArrayList<>();
        y.add(x.save());
        y.add(a.save());
        y.add(b.save());

        FileMaster.listToFile(y,"res/output");
    }

    // program to set up player directories
    public class PlayerSetup extends FreshProgram {

        public void init(){
            setSize(200,200);
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

    //
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
