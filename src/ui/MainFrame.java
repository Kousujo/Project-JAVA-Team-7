package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuSystem, menuSetting, menuHelp;
    private JMenuItem itemReset, itemExit, itemTheme, itemAbout;
    private String selectedMode = "EASY";
    
    private WelcomePanel welcomePanel;
    private ModePanel modePanel;
    private LeaderboardPanel leaderboardPanel;
    private EasyGamePanel easyGamePanel;
    private NormalGamePanel normalGamePanel;
    private HardGamePanel hardGamePanel;

    public MainFrame() {
        setTitle("The Chosen Number");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initMenuBar();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        welcomePanel = new WelcomePanel(this);
        modePanel = new ModePanel(this);
        leaderboardPanel = new LeaderboardPanel(this);
        easyGamePanel = new EasyGamePanel(this);
        normalGamePanel = new NormalGamePanel(this);
        hardGamePanel = new HardGamePanel(this);

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(modePanel, "Mode");
        mainPanel.add(leaderboardPanel, "Leaderboard");
        mainPanel.add(easyGamePanel, "Easy");
        mainPanel.add(normalGamePanel, "Normal");
        mainPanel.add(hardGamePanel, "Hard");

        add(mainPanel);

        showScreen("Welcome");
    }  

    public void showScreen(String screenName) {
        if (screenName.equals("Leaderboard")) {
            leaderboardPanel.refreshData(); 
        } else if (screenName.equals("Welcome")) {
            welcomePanel.refreshStatus();
        }
        
        cardLayout.show(mainPanel, screenName);
    }  

    public String getSelectedMode() {
        return selectedMode;
    }

    public void startNewGame(String mode) {
        this.selectedMode = mode; 
        
        switch (mode) {
            case "EASY":
                easyGamePanel.initNewGame(mode);
                showScreen("Easy");
                break;
                
            case "NORMAL":
                normalGamePanel.initNewGame(mode);
                showScreen("Normal");
                break;
                
            case "HARD":
                hardGamePanel.initNewGame(mode);
                showScreen("Hard");
                break;
                
            default:
                showScreen("Welcome");
                break;
        }
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        menuSystem = new JMenu("Hệ thống");
        itemReset = new JMenuItem("Chơi lại");
        itemExit = new JMenuItem("Thoát");

        menuSystem.add(itemReset);
        menuSystem.addSeparator();
        menuSystem.add(itemExit);

        menuSetting = new JMenu("Tùy chỉnh");
        itemTheme = new JMenuItem("Đổi giao diện");
        menuSetting.add(itemTheme);

        menuHelp = new JMenu("Trợ giúp");
        itemAbout = new JMenuItem("Về chúng tôi");
        menuHelp.add(itemAbout);

        menuBar.add(menuSystem);
        menuBar.add(menuSetting);
        menuBar.add(menuHelp);

        this.setJMenuBar(menuBar);

        itemReset.addActionListener(this);
        itemExit.addActionListener(this);
        itemTheme.addActionListener(this);
        itemAbout.addActionListener(this);
    }

    public void stopAllTimers() {
        easyGamePanel.stopGameTimer();
        normalGamePanel.stopGameTimer();
        hardGamePanel.stopGameTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemReset) {
            stopAllTimers();
            this.showScreen("Welcome");
        }
        else if (e.getSource() == itemExit) {
            System.exit(0);
        }
        else if (e.getSource() == itemTheme) {
            JOptionPane.showMessageDialog(this, "Tính năng hiện tại chưa có!\nVui lòng đợi bản cập nhật sau!");
        }
        else if (e.getSource() == itemAbout) {
            JOptionPane.showMessageDialog(this,"Ứng dụng được phát triển bởi Team 7");
        }
    }
}