import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModePanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private JRadioButton rbEasy, rbNormal, rbHard;
    private ButtonGroup group;
    private JButton btnStart, btnBack;
    private Image backgroundImage;
    private JPanel modePanel, buttonPanel;
    private MultiLineOutlineLabel lblHeader; 

    public ModePanel(MainFrame frame) {
        this.mainframe = frame;
        backgroundImage = new ImageIcon("res/Toy.png").getImage();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Font mainFont = new Font("SansSerif", Font.PLAIN, 18);
        
        Color statusTextColor = new Color(50, 50, 50);

        lblHeader = new MultiLineOutlineLabel("CHỌN CHẾ\nĐỘ CHƠI", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 60));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setOutlineColor(new Color(0, 0, 0, 180));
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(80, 0, 0, 0); 
        add(lblHeader, gbc);

        modePanel = new JPanel(new GridLayout(3, 1, 0, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 120)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        modePanel.setOpaque(false);
        modePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        rbEasy = new JRadioButton("DỄ (4 ô số - Phạm vi 1-50)");
        rbNormal = new JRadioButton("VỪA (7 ô số - Phạm vi 1-100)");
        rbHard = new JRadioButton("KHÓ (10 ô số - Phạm vi 1-500)");

        setupRadioButton(rbEasy, mainFont, statusTextColor);
        setupRadioButton(rbNormal, mainFont, statusTextColor);
        setupRadioButton(rbHard, mainFont, statusTextColor);
        rbNormal.setSelected(true);

        group = new ButtonGroup();
        group.add(rbEasy); group.add(rbNormal); group.add(rbHard);

        modePanel.add(rbEasy);
        modePanel.add(rbNormal);
        modePanel.add(rbHard);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(modePanel, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 10));
        buttonPanel.setOpaque(false);

        btnStart = new JButton("START");
        btnStart.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnStart.setPreferredSize(new Dimension(180, 50));
        btnStart.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnStart.setBackground(new Color(70, 130, 180)); 
        btnStart.setForeground(Color.WHITE);

        btnBack = new JButton("BACK");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnBack.setPreferredSize(new Dimension(180, 50));
        btnBack.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        buttonPanel.add(btnStart);
        buttonPanel.add(btnBack);

        gbc.gridy = 2;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(buttonPanel, gbc);

        btnStart.addActionListener(this);
        btnBack.addActionListener(this);
    }

    private void setupRadioButton(JRadioButton rb, Font font, Color color) {
        rb.setFont(font);
        rb.setForeground(color);
        rb.setOpaque(false);
        rb.setFocusPainted(false);
        rb.putClientProperty("JComponent.sizeVariant", "large");
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
        if (e.getSource() == btnStart) {
            mainframe.showScreen("Game"); 
        } else if (e.getSource() == btnBack) {
            mainframe.showScreen("Welcome");
        }
    }
}