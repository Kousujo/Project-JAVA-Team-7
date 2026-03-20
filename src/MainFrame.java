import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuSystem, menuSetting, menuHelp;
    private JMenuItem itemReset, itemExit, itemTheme, itemAbout;

    public MainFrame() {
        setTitle("The Chosen Number");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initMenuBar();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel(this);
        ModePanel modePanel = new ModePanel(this);
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(modePanel, "Mode");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        add(mainPanel);
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