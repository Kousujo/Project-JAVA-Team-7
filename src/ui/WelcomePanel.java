package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomePanel extends JPanel implements ActionListener {
    private MainFrame mainframe; 
    private JPanel statusPanel, infoPanel;
    private MultiLineOutlineLabel lbltitle;
    private JLabel lblsubtitle, lblname, lblscore, lblversion, lblauthor;
    private JButton btnNewGame, btnLeaderboard; 
    private Image backgroundImage;

    public WelcomePanel(MainFrame frame) {
        this.mainframe = frame; 
        backgroundImage = new ImageIcon("res/Toy.png").getImage();

        setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints(); 
        Font mainFont = new Font("SansSerif", Font.PLAIN, 18);

        btnLeaderboard = new JButton("🏆");
        btnLeaderboard.setToolTipText("Bảng xếp hạng");
        btnLeaderboard.setFont(new Font("SansSerif", Font.PLAIN, 20));
        btnLeaderboard.setPreferredSize(new Dimension(50, 50));
        btnLeaderboard.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_SQUARE);
        btnLeaderboard.putClientProperty(FlatClientProperties.STYLE, "arc: 999");
        btnLeaderboard.setBackground(new Color(255, 255, 255, 150));

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(20, 20, 0, 0); 
        add(btnLeaderboard, gbc);

        lbltitle = new MultiLineOutlineLabel("THE CHOSEN\nNUMBER", SwingConstants.CENTER);
        lbltitle.setFont(new Font("SansSerif", Font.BOLD, 48));
        lbltitle.setForeground(Color.WHITE);
        lbltitle.setOutlineColor(new Color(0, 0, 0, 180)); 

        lblsubtitle = new JLabel("Chọn số bạn nghĩ là đúng");
        lblsubtitle.setFont(new Font("SansSerif", Font.ITALIC, 22));
        lblsubtitle.setForeground(new Color(0, 0, 0));
        lblsubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85, 0, -20, 0); 
        add(lbltitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 80, 0); 
        add(lblsubtitle, gbc);

        statusPanel = new JPanel(new GridLayout(2, 1, 0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 100)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(15, 45, 15, 45));

        lblname = new JLabel("👤 Chế độ: Offline"); lblname.setFont(mainFont);
        lblscore = new JLabel("⭐ Kỷ lục: 0"); lblscore.setFont(mainFont);
        
        Color statusTextColor = new Color(50, 50, 50);
        lblname.setForeground(statusTextColor); 
        lblscore.setForeground(statusTextColor); 

        statusPanel.add(lblname);
        statusPanel.add(lblscore);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(statusPanel, gbc);

        btnNewGame = new JButton("START");
        btnNewGame.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnNewGame.setPreferredSize(new Dimension(250, 60));
        btnNewGame.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnNewGame.setBackground(new Color(70, 130, 180));
        btnNewGame.setForeground(Color.WHITE);

        gbc.gridy = 3;
        gbc.weighty = 0.2;
        add(btnNewGame, gbc);

        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        lblversion = new JLabel("Version 1.0.0");
        lblversion.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblversion.setForeground(Color.WHITE);

        lblauthor = new JLabel("Developed by Team 7");
        lblauthor.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblauthor.setForeground(Color.WHITE);

        infoPanel.add(lblversion, BorderLayout.WEST);
        infoPanel.add(lblauthor, BorderLayout.EAST);

        gbc.gridy = 4;
        gbc.weighty = 0.1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(infoPanel, gbc);

        btnNewGame.addActionListener(this);
        btnLeaderboard.addActionListener(this);
    }

    public void refreshStatus(int localHighScore) {
        lblscore.setText("⭐ Kỷ lục: " + localHighScore);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            mainframe.showScreen("Mode");
        }
        if (e.getSource() == btnLeaderboard) {
            mainframe.showScreen("Leaderboard");
        }
    }
}