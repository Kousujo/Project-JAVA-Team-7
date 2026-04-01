package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import logic.EasyModeEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EasyGamePanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private Image backgroundImage;
    private JPanel historyBox;
    private JTextField txtInput;
    private JButton btnGuess, btnBack;
    private JLabel lblTurn, lblTime, lblFeedback; 
    private Timer gameTimer;
    
    private int secondsElapsed = 0;
    private EasyModeEngine engine; 

    public EasyGamePanel(MainFrame frame) {
        this.mainframe = frame;
        this.engine = new EasyModeEngine(); 
        backgroundImage = new ImageIcon("res/Toy.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. TIÊU ĐỀ
        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("GUESS IT", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 70)); 
        lblModeName.setForeground(new Color(255, 215, 0));
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.1;
        gbc.insets = new Insets(50, 0, 0, 0);
        add(lblModeName, gbc);

        // 2. PANEL THÔNG SỐ
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        lblTurn = new JLabel();
        lblTurn.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTime = new JLabel("[ 0s ]");
        lblTime.setForeground(Color.BLUE);
        lblTime.setFont(new Font("SansSerif", Font.BOLD, 18));
        statsPanel.add(lblTurn, BorderLayout.WEST);
        statsPanel.add(lblTime, BorderLayout.EAST);

        gbc.gridy = 1; gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 85, 10, 85);
        add(statsPanel, gbc);

        // 3. MÀN HÌNH GỢI Ý 
        lblFeedback = new JLabel("HÃY NHẬP SỐ!", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 150)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g); 
            }
        };
        lblFeedback.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblFeedback.setForeground(Color.WHITE);
        lblFeedback.setPreferredSize(new Dimension(400, 100));
        lblFeedback.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        
        gbc.gridy = 2; gbc.weighty = 0.15;
        gbc.fill = GridBagConstraints.NONE;
        add(lblFeedback, gbc);

        // 4. LỊCH SỬ 
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
        glassCard.setPreferredSize(new Dimension(350, 200));
        historyBox = new JPanel();
        historyBox.setLayout(new BoxLayout(historyBox, BoxLayout.Y_AXIS));
        historyBox.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(historyBox);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        glassCard.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 3; gbc.weighty = 0.3; gbc.insets = new Insets(20, 65, 20, 65);
        add(glassCard, gbc);

        // 5. ĐIỀU KHIỂN
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlPanel.setOpaque(false);
        txtInput = new JTextField();
        txtInput.setPreferredSize(new Dimension(180, 55)); 
        txtInput.setFont(new Font("SansSerif", Font.BOLD, 28));
        txtInput.setHorizontalAlignment(JTextField.CENTER);
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0-100");
        txtInput.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        
        btnGuess = new JButton("ĐOÁN!");
        btnGuess.setPreferredSize(new Dimension(120, 55));
        btnGuess.setBackground(new Color(70, 130, 180));
        btnGuess.setForeground(Color.WHITE);
        btnGuess.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGuess.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        controlPanel.add(txtInput);
        controlPanel.add(btnGuess);
        gbc.gridy = 4; gbc.weighty = 0.1;
        add(controlPanel, gbc);

        // 6. THOÁT
        btnBack = new JButton("THOÁT");
        btnBack.setPreferredSize(new Dimension(150, 45));
        btnBack.putClientProperty(FlatClientProperties.STYLE, "background: #c0392b; foreground: #ffffff; arc: 20");
        gbc.gridy = 5; gbc.weighty = 0.1; gbc.insets = new Insets(20, 0, 50, 0);
        add(btnBack, gbc);

        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            lblTime.setText("[ " + secondsElapsed + "s ]");
        });

        btnGuess.addActionListener(this);
        btnBack.addActionListener(this);
    }

    public void initNewGame(String mode) {
        engine.startNewGame(); 
        secondsElapsed = 0;

        updateTurnDisplay();

        lblTurn.setText("Lượt: 0/" + engine.getMaxAttempts());
        lblFeedback.setText("NHẬP SỐ TỪ 0 - 100");
        lblFeedback.setForeground(Color.WHITE);
        historyBox.removeAll();
        historyBox.repaint();
        txtInput.setText("");
        gameTimer.restart();
    }

    private void handleGuess() {
        String input = txtInput.getText().trim();
        if (!input.matches("\\d+")) {
            logic.SoundManager.playSound("assets/wrong.wav"); // Phát âm thanh báo lỗi
            return;
        }

        int guess = Integer.parseInt(input);
        String result = engine.checkGuess(guess);

        updateTurnDisplay();
        addHistoryRow("Lượt " + engine.getAttemptsUsed() + ": [" + guess + "] -> " + result);

        if (result.equals("QUÁ CAO")) {
            lblFeedback.setText("↓ QUÁ CAO ↓");
            lblFeedback.setForeground(new Color(231, 76, 60)); // Đỏ
        } else if (result.equals("QUÁ THẤP")) {
            lblFeedback.setText("↑ QUÁ THẤP ↑");
            lblFeedback.setForeground(new Color(52, 152, 219)); // Xanh dương
        } else if (result.equals("MATCH!")) {
            lblFeedback.setText("✨ CHÍNH XÁC! ✨");
            lblFeedback.setForeground(new Color(46, 204, 113)); // Xanh lá
        }

        txtInput.setText("");

        if (engine.isGameOver()) {
            gameTimer.stop();
            endGame(engine.isWin());
        }
    }

    private void handleWin() {
        int score = engine.calculateFinalScore();

        // LƯU VÀO DATABASE
        database.GameDAO dao = new database.GameDAO();
        dao.insertGameResult(score, "EASY", engine.getTargetNumber(), engine.getAttemptsUsed(), secondsElapsed);
    }

    private void addHistoryRow(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        historyBox.add(lbl);
        historyBox.revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = ((JScrollPane) historyBox.getParent().getParent()).getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private void updateTurnDisplay() {
        int used = engine.getAttemptsUsed();
        int max = engine.getMaxAttempts();
        int remaining = max - used;

        lblTurn.setText("Lượt: " + used + "/" + max + " (Còn lại: " + remaining + ")");

        float ratio = (float) used / max;
        int red = (int) (46 + (231 - 46) * ratio);
        int green = (int) (204 + (76 - 204) * ratio);
        int blue = (int) (113 + (60 - 113) * ratio);
        Color dynamicColor = new Color(red, green, blue);

        lblTurn.setForeground(dynamicColor);

        if (remaining <= 1) {
            lblTurn.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 20));
        } else {
            lblTurn.setFont(new Font("SansSerif", Font.BOLD, 18));
        }
    }

    private void endGame(boolean isWin) {
        String status = isWin ? "WINNER" : "GAME OVER";
        int finalScore = engine.calculateFinalScore();
        
        // Phát âm thanh thắng cuộc
        if (isWin) {
            logic.SoundManager.playSound("assets/win.wav");
        }

        ResultDialog dialog = new ResultDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            status,
            finalScore,
            secondsElapsed,
            engine.getAttemptsUsed()
        );

        dialog.setVisible(true);

        if (dialog.isRetry()) {
            initNewGame(mainframe.getSelectedMode());
        } else {
            mainframe.showScreen("Welcome");
        }

        if (isWin) {
            handleWin();
        } 
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        logic.SoundManager.playSound("assets/click.wav"); // Phát hiệu ứng click
        
        if (e.getSource() == btnGuess) handleGuess();
        if (e.getSource() == btnBack) {
            gameTimer.stop();
            mainframe.showScreen("Welcome");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}