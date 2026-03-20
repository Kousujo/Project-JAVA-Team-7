import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomePanel extends JPanel implements ActionListener {
    private MainFrame mainframe; 
    private JPanel statusPanel, buttonPanel, infoPanel;
    private MultiLineOutlineLabel lbltitle;
    private JLabel lblsubtitle, lblname, lblscore, lblmode, lblversion, lblauthor;
    private JButton btnNewGame, btnContinue, btnAccounts;
    private Image backgroundImage;

    public WelcomePanel(MainFrame frame) {
        this.mainframe = frame; 
        backgroundImage = new ImageIcon("res/Toy.png").getImage();

        setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints(); 
        Font mainFont = new Font("SansSerif", Font.PLAIN, 18);

        btnAccounts = new JButton("👤"); 
        btnAccounts.setToolTipText("Đăng nhập / Đăng ký");
        btnAccounts.setFont(new Font("SansSerif", Font.PLAIN, 20));
        btnAccounts.setPreferredSize(new Dimension(50, 50));
        btnAccounts.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_SQUARE);
        btnAccounts.putClientProperty(FlatClientProperties.STYLE, "arc: 999");
        btnAccounts.setBackground(new Color(255, 255, 255, 150));

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(20, 0, 0, 20);
        add(btnAccounts, gbc);

        lbltitle = new MultiLineOutlineLabel("THE CHOSEN\nNUMBER", SwingConstants.CENTER);
        lbltitle.setFont(new Font("SansSerif", Font.BOLD, 48));
        lbltitle.setForeground(Color.WHITE);
        lbltitle.setOutlineColor(new Color(0, 0, 0, 180)); // Viền đen mờ 

        lblsubtitle = new JLabel("Chọn số bạn nghĩ là đúng");
        lblsubtitle.setFont(new Font("SansSerif", Font.ITALIC, 22));
        lblsubtitle.setForeground(new Color(250, 250, 250));
        lblsubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(70, 0, -20, 0); 
        add(lbltitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 80, 0); 
        add(lblsubtitle, gbc);

        statusPanel = new JPanel(new GridLayout(3, 1, 0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Khử  răng cưa
                g2.setColor(new Color(255, 255, 255, 100)); // Màu trắng mờ 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Vẽ hình bo góc
                g2.dispose();
            }
        };
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(15, 45, 25, 45));

        lblname = new JLabel("👤 Người chơi: Unknown"); lblname.setFont(mainFont);
        lblscore = new JLabel("⭐ Điểm cao: 0"); lblscore.setFont(mainFont);
        lblmode = new JLabel("🎯 Chế độ: Chưa rõ"); lblmode.setFont(mainFont);
        
        Color statusTextColor = new Color(50, 50, 50);
        lblname.setForeground(statusTextColor); 
        lblscore.setForeground(statusTextColor); 
        lblmode.setForeground(statusTextColor);

        statusPanel.add(lblname);
        statusPanel.add(lblscore);
        statusPanel.add(lblmode);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(statusPanel, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);

        btnNewGame = new JButton("NEW GAME");
        btnNewGame.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnNewGame.setPreferredSize(new Dimension(180, 50));
        btnNewGame.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnNewGame.setBackground(new Color(70, 130, 180));
        btnNewGame.setForeground(Color.WHITE);

        btnContinue = new JButton("CONTINUE");
        btnContinue.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnContinue.setPreferredSize(new Dimension(180, 50));
        btnContinue.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnContinue.setEnabled(false);

        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnContinue);

        gbc.gridy = 3;
        gbc.weighty = 0.2;
        add(buttonPanel, gbc);

        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        Font infoFont = new Font("SansSerif", Font.BOLD, 16);

        lblversion = new JLabel("Version 1.0.0");
        lblversion.setFont(infoFont);
        lblversion.setForeground(Color.WHITE);

        lblauthor = new JLabel("Developed by Team 7");
        lblauthor.setFont(infoFont);
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
        btnAccounts.addActionListener(this); 
        btnContinue.addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        g2d.setColor(new Color(0, 0, 0, 15)); 
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            mainframe.showScreen("Mode");
        }
        if (e.getSource() == btnContinue) {
            // TODO: Kết nối GameDatabase
        }
        if (e.getSource() == btnAccounts) {
            mainframe.showScreen("Login");
        }
    }
}