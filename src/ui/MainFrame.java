package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logic.SoundManager;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuSystem, menuSetting, menuHelp, menuBgm;
    private JMenuItem itemReset, itemExit, itemAbout, itemSetting, itemBgm1, itemBgm2, itemBgm3;
    private String selectedMode = "EASY";

    private WelcomePanel welcomePanel;
    private ModePanel modePanel;
    private LeaderboardPanel leaderboardPanel;
    private EasyGamePanel easyGamePanel;
    private NormalGamePanel normalGamePanel;
    private HardGamePanel hardGamePanel;
    private SettingsPanel settingsPanel;

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
        settingsPanel = new SettingsPanel(this);

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(modePanel, "Mode");
        mainPanel.add(leaderboardPanel, "Leaderboard");
        mainPanel.add(easyGamePanel, "Easy");
        mainPanel.add(normalGamePanel, "Normal");
        mainPanel.add(hardGamePanel, "Hard");
        mainPanel.add(settingsPanel, "Settings");

        add(mainPanel);

        showScreen("Welcome");

        SoundManager.playBgm(SoundManager.currentBgmPath);
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
        itemSetting = new JMenuItem("Cài đặt");

        menuBgm = new JMenu("Nhạc nền");
        itemBgm1 = new JMenuItem("Bản nhạc 1");
        itemBgm2 = new JMenuItem("Bản nhạc 2");
        itemBgm3 = new JMenuItem("Bản nhạc 3");

        menuBgm.add(itemBgm1);
        menuBgm.add(itemBgm2);
        menuBgm.add(itemBgm3);

        menuSetting.add(itemSetting);
        menuSetting.add(menuBgm);

        menuHelp = new JMenu("Trợ giúp");
        itemAbout = new JMenuItem("Về chúng tôi");
        menuHelp.add(itemAbout);

        menuBar.add(menuSystem);
        menuBar.add(menuSetting);
        menuBar.add(menuHelp);

        this.setJMenuBar(menuBar);

        itemReset.addActionListener(this);
        itemExit.addActionListener(this);
        itemAbout.addActionListener(this);
        itemBgm1.addActionListener(this);
        itemBgm2.addActionListener(this);
        itemBgm3.addActionListener(this);
        itemSetting.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemReset) {
            stopAllTimers();
            this.showScreen("Welcome");
        } else if (e.getSource() == itemExit) {
            System.exit(0);
        } else if (e.getSource() == itemSetting) {
            showScreen("Settings");
        } else if (e.getSource() == itemBgm1) {
            changeBgm("assets/bgm1.wav");
        } else if (e.getSource() == itemBgm2) {
            changeBgm("assets/bgm2.wav");
        } else if (e.getSource() == itemBgm3) {
            changeBgm("assets/bgm3.wav");
        } else if (e.getSource() == itemAbout) {
            JOptionPane.showMessageDialog(this, "Ứng dụng được phát triển bởi Team 7");
        }
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

    public void stopAllTimers() {
        easyGamePanel.stopGameTimer();
        normalGamePanel.stopGameTimer();
        hardGamePanel.stopGameTimer();
    }

    private void changeBgm(String path) {
        SoundManager.currentBgmPath = path;
        if (SoundManager.isBgmEnabled) {
            SoundManager.playBgm(path);
        }
    }

}