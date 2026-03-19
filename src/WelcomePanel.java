import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomePanel extends JPanel implements ActionListener {
    private MainFrame mainframe; 
    private JPanel titlePanel, statusPanel, buttonPanel, infoPanel;
    private JLabel lbltitle, lblsubtitle, lblname, lblscore, lblmode, lblversion, lblauthor;
    private JButton btnNewGame, btnContinue;
    private Image backgroundImage;

    public WelcomePanel(MainFrame frame) {
        this.mainframe = frame; 
        backgroundImage = new ImageIcon("res/nefarith_arknights.png").getImage();

        setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints(); 
        Font mainFont = new Font("SansSerif", Font.PLAIN, 18);

        titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);

        lbltitle = new JLabel("THE CHOSEN NUMBER");
        lbltitle.setFont(new Font("SansSerif", Font.BOLD, 36));
        lbltitle.setForeground(new Color(255, 255, 255));
        lbltitle.setHorizontalAlignment(SwingConstants.CENTER);

        lblsubtitle = new JLabel("Chọn số bạn nghĩ là đúng");
        lblsubtitle.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblsubtitle.setForeground(new Color(220, 220, 220));
        lblsubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(lbltitle);
        titlePanel.add(lblsubtitle);

        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 0, 0, 0); 
        add(titlePanel, gbc);

        statusPanel = new JPanel(new GridLayout(3, 1, 0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 40)); // Màu trắng mờ (Alpha 40)
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Vẽ hình bo góc
                g2.dispose();
            }
        };
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        lblname = new JLabel("👤 Người chơi: Unknown"); lblname.setFont(mainFont);
        lblscore = new JLabel("⭐ Điểm cao: 0"); lblscore.setFont(mainFont);
        lblmode = new JLabel("🎯 Chế độ: Chưa rõ"); lblmode.setFont(mainFont);
        
        Color textColor = Color.WHITE;
        lblname.setForeground(textColor); lblscore.setForeground(textColor); lblmode.setForeground(textColor);

        statusPanel.add(lblname);
        statusPanel.add(lblscore);
        statusPanel.add(lblmode);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(statusPanel, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);

        btnNewGame = new JButton("NEW GAME");
        btnNewGame.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnNewGame.setPreferredSize(new Dimension(180, 50));
        btnNewGame.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnNewGame.setBackground(new Color(70, 130, 180));
        btnNewGame.setForeground(Color.BLACK);

        btnContinue = new JButton("CONTINUE");
        btnContinue.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnContinue.setPreferredSize(new Dimension(180, 50));
        btnContinue.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnContinue.setEnabled(false);

        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnContinue);

        gbc.gridy = 2;
        gbc.weighty = 0.2;
        add(buttonPanel, gbc);

        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        lblversion = new JLabel("Version 1.0");
        lblversion.setForeground(new Color(180, 180, 180));
        lblauthor = new JLabel("Developed by Team 7");
        lblauthor.setForeground(new Color(180, 180, 180));

        infoPanel.add(lblversion, BorderLayout.WEST);
        infoPanel.add(lblauthor, BorderLayout.EAST);

        gbc.gridy = 3;
        gbc.weighty = 0.1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(infoPanel, gbc);

        btnNewGame.addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        g2d.setColor(new Color(0, 0, 0, 80)); 
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            mainframe.showScreen("Mode");
        }
    }
}