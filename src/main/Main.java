package main;
import com.formdev.flatlaf.*;
import ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new MainFrame().setVisible(true); 
    }
}