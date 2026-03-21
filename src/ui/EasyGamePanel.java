package ui;

import com.formdev.flatlaf.FlatClientProperties;

import component.MultiLineOutlineLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class EasyGamePanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private Image backgroundImage;
    private JPanel historyBox, digitPanel;
    private JTextField txtInput;
    private JButton btnGuess, btnHint, btnBack;
    private JLabel lblTurn, lblTime, lblSubtitle;
    private JLabel[] digitBoxes; 
    private Timer gameTimer;
    
    private int secondsElapsed = 0;
    private int currentTurn = 0;
    private final int MAX_TURNS = 5;
    
    private int[] targetNumbers = {7, 15, 32, 48}; 

    public EasyGamePanel(MainFrame frame) {
        this.mainframe = frame;
        backgroundImage = new ImageIcon("res/Toy.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("CLASSIC", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 70)); 
        lblModeName.setForeground(new Color(255, 215, 0));
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.1;
        gbc.insets = new Insets(50, 0, 0, 0);
        add(lblModeName, gbc);

        lblSubtitle = new JLabel("Nhập số (1-50)");
        lblSubtitle.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblSubtitle.setForeground(Color.WHITE);
        gbc.gridy = 1; gbc.weighty = 0.02;
        gbc.insets = new Insets(-10, 0, 5, 0); 
        add(lblSubtitle, gbc);

        
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        lblTurn = new JLabel("Bạn đã đoán 0 lần. Còn lại 5 lượt.");
        lblTurn.setForeground(Color.WHITE);
        lblTurn.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTime = new JLabel("[ 0s ]");
        lblTime.setForeground(Color.WHITE);
        lblTime.setFont(new Font("SansSerif", Font.BOLD, 18));
        statsPanel.add(lblTurn, BorderLayout.WEST);
        statsPanel.add(lblTime, BorderLayout.EAST);

        gbc.gridy = 2; gbc.weighty = 0.03;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 85, 10, 85);
        add(statsPanel, gbc);

        digitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        digitPanel.setOpaque(false);
        digitBoxes = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            digitBoxes[i] = new JLabel("?", SwingConstants.CENTER);
            digitBoxes[i].setPreferredSize(new Dimension(60, 60));
            digitBoxes[i].setFont(new Font("SansSerif", Font.BOLD, 28));
            digitBoxes[i].setOpaque(true);
            digitBoxes[i].setBackground(Color.WHITE);
            digitBoxes[i].setForeground(Color.BLACK);
            digitBoxes[i].setBorder(new LineBorder(Color.GRAY, 2));
            digitPanel.add(digitBoxes[i]);
        }
        gbc.gridy = 3; gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(digitPanel, gbc);

        JPanel glassCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 140)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setPreferredSize(new Dimension(330, 180)); 
        glassCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        historyBox = new JPanel();
        historyBox.setLayout(new BoxLayout(historyBox, BoxLayout.Y_AXIS));
        historyBox.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(historyBox);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        glassCard.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 4; gbc.weighty = 0.3; gbc.insets = new Insets(0, 65, 15, 65);
        add(glassCard, gbc);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setOpaque(false);
        GridBagConstraints cbc = new GridBagConstraints();
        
        txtInput = new JTextField();
        txtInput.setPreferredSize(new Dimension(280, 55)); 
        txtInput.setFont(new Font("SansSerif", Font.BOLD, 24));
        txtInput.setHorizontalAlignment(JTextField.CENTER);
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập số...");
        txtInput.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        
        Dimension commonBtnSize = new Dimension(145, 50); 
        btnGuess = new JButton("ĐOÁN!");
        btnGuess.setPreferredSize(commonBtnSize);
        btnGuess.setBackground(new Color(70, 130, 180));
        btnGuess.setForeground(Color.WHITE);
        btnGuess.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGuess.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        btnHint = new JButton("GỢI Ý");
        btnHint.setPreferredSize(commonBtnSize);
        btnHint.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnHint.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        cbc.insets = new Insets(5, 5, 5, 5);
        cbc.gridx = 0; cbc.gridwidth = 2;
        controlPanel.add(txtInput, cbc);
        cbc.gridy = 1; cbc.gridwidth = 1; 
        cbc.gridx = 0; controlPanel.add(btnGuess, cbc);
        cbc.gridx = 1; controlPanel.add(btnHint, cbc);

        gbc.gridy = 5; gbc.weighty = 0.15;
        add(controlPanel, gbc);

        btnBack = new JButton("THOÁT GAME");
        btnBack.setPreferredSize(new Dimension(300, 50)); 
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnBack.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btnBack.putClientProperty(FlatClientProperties.STYLE, "background: #c0392b; foreground: #ffffff");
        gbc.gridy = 6; gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 70, 0); 
        add(btnBack, gbc);

        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            lblTime.setText("[ " + secondsElapsed + "s ]");
        });
        gameTimer.start();

        btnGuess.addActionListener(this);
        btnHint.addActionListener(this);
        btnBack.addActionListener(this);
    }

    private void addHistoryRow(String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setOpaque(false);
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.BLACK); 
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        row.add(lbl);
        historyBox.add(row);
        historyBox.revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = ((JScrollPane)historyBox.getParent().getParent()).getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private void handleGuess() {
        String input = txtInput.getText().trim();
        if (input.isEmpty() || !input.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "LỖI: Chỉ nhập số nguyên dương!", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
            txtInput.setText("");
            return; 
        }

        int guessValue = Integer.parseInt(input);
        currentTurn++;
        lblTurn.setText("Bạn đã đoán " + currentTurn + " lần. Còn lại " + (MAX_TURNS - currentTurn) + " lượt.");
        
        // LOGIC WORDLE STYLE TẠM THỜI
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            if (guessValue == targetNumbers[i]) {
                digitBoxes[i].setText(String.valueOf(guessValue));
                digitBoxes[i].setBackground(new Color(46, 204, 113)); // Màu Xanh lá (Đúng)
                digitBoxes[i].setForeground(Color.WHITE);
                found = true;
            }
        }
        
        String resultMsg = found ? "CHÍNH XÁC VỊ TRÍ!" : "KHÔNG CÓ TRONG BỘ MÃ";
        addHistoryRow("Lượt " + currentTurn + ": [" + guessValue + "] -> " + resultMsg);
        txtInput.setText("");

        if (currentTurn >= MAX_TURNS) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "Hết lượt! Trò chơi kết thúc.");
            mainframe.showScreen("Welcome");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuess) handleGuess();
        if (e.getSource() == btnBack) {
            gameTimer.stop();
            mainframe.showScreen("Mode");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}