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
    private String currentUser = null;
    private String selectedMode = "EASY";
    
    private WelcomePanel welcomePanel;
    private ModePanel modePanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private LeaderboardPanel leaderboardPanel;
    private EasyGamePanel easyGamePanel;

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
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        leaderboardPanel = new LeaderboardPanel(this);
        easyGamePanel = new EasyGamePanel(this);

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(modePanel, "Mode");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(leaderboardPanel, "Leaderboard");
        mainPanel.add(easyGamePanel, "Easy");

        add(mainPanel);
    }

    public String getCurrentUser() { return currentUser; }
    public void setCurrentUser(String user) { this.currentUser = user; }
    
    public WelcomePanel getWelcomePanel() { return welcomePanel; }
    public EasyGamePanel getEasyGamePanel() { return easyGamePanel; }   
    
    public void setSelectedMode(String mode) {
        this.selectedMode = mode;
        System.out.println("Đã chọn chế độ: " + mode); 
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void startNewGame(String mode) {
        setSelectedMode(mode);
        
        // B. Ra lệnh cho màn hình Game dọn dẹp chiến trường cũ và sinh số mới
        // Lưu ý: Phải gọi hàm này trước khi showScreen để người chơi vào là thấy mới tinh
        if (easyGamePanel != null) {
            easyGamePanel.initNewGame(mode);
        }
        
        // C. Chuyển màn hình sang màn chơi game
        showScreen("Easy");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemReset) {
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

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }   
}