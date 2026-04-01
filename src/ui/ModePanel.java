package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
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
        
        lblHeader = new MultiLineOutlineLabel("SELECT\nMODE", SwingConstants.CENTER); // Gọn hơn
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 60));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setOutlineColor(new Color(0, 0, 0, 180));
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(100, 0, -50, 0); 
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

        rbEasy = new JRadioButton("EASY ( 0-100 )");
        rbNormal = new JRadioButton("NORMAL ( 1000-9999 )");
        rbHard = new JRadioButton("HARD ( 00000-99999 )");

        rbEasy.setFont(new Font("SansSerif", Font.PLAIN, 18));
        rbEasy.setForeground(new Color(50, 50, 50));
        rbEasy.setOpaque(false);
        rbEasy.setFocusPainted(false);

        rbNormal.setFont(new Font("SansSerif", Font.PLAIN, 18));
        rbNormal.setForeground(new Color(50, 50, 50));
        rbNormal.setOpaque(false);
        rbNormal.setFocusPainted(false);

        rbHard.setFont(new Font("SansSerif", Font.PLAIN, 18));
        rbHard.setForeground(new Color(50, 50, 50));
        rbHard.setOpaque(false);
        rbHard.setFocusPainted(false);

        rbEasy.setSelected(true); 

        group = new ButtonGroup();
        group.add(rbEasy); group.add(rbNormal); group.add(rbHard);

        modePanel.add(rbEasy);
        modePanel.add(rbNormal);
        modePanel.add(rbHard);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(modePanel, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 10));
        buttonPanel.setOpaque(false);

        btnStart = new JButton("START!");
        btnStart.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnStart.setPreferredSize(new Dimension(180, 55));
        btnStart.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnStart.setBackground(new Color(70, 130, 180)); 
        btnStart.setForeground(Color.WHITE);

        btnBack = new JButton("BACK");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnBack.setPreferredSize(new Dimension(180, 55));
        btnBack.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        buttonPanel.add(btnStart);
        buttonPanel.add(btnBack);

        gbc.gridy = 2;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(0, 0, 65, 0);
        add(buttonPanel, gbc);

        btnStart.addActionListener(this);
        btnBack.addActionListener(this);
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
        logic.SoundManager.playSound("assets/click.wav"); // Phát tiếng click
        
        if (e.getSource() == btnStart) {
            String selectedMode = "EASY";
            if (rbNormal.isSelected()) selectedMode = "NORMAL";
            else if (rbHard.isSelected()) selectedMode = "HARD";
            
            mainframe.startNewGame(selectedMode);
            
        } else if (e.getSource() == btnBack) {
            mainframe.showScreen("Welcome");
        }
    }
}