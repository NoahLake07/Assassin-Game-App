package app;

import freshui.FreshUI;
import freshui.gui.NavBar;
import freshui.program.FreshProgram;

import java.awt.*;
import java.util.ArrayList;

public class AssassinApp extends FreshProgram {

    // UI objects
    NavBar navigation;

    // UI data
    ArrayList<String> pages = new ArrayList<>();
    final Color BACKGROUND_COLOR = new Color(169, 169, 169);

    public void init(){
        setProgramName("Assassin Moderator Assistant");
        setBackground(BACKGROUND_COLOR);

        setupUI();
    }

    private void setupUI(){
        // navigation bar
        pages.add("Home");
        pages.add("Search");
        pages.add("Logs");
        navigation = new NavBar(pages,getWidth(),getHeight()/10,this);
        add(navigation, 0, getHeight() - navigation.getHeight());
    }

    public static void main(String[] args) {
        new AssassinApp().start();
    }

}
