import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("The Chosen Number");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel(this);
        ModePanel modePanel = new ModePanel(this);

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(modePanel, "Mode");

        add(mainPanel);
    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }   
}