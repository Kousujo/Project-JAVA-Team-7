package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import logic.HardModeEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HardGamePanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private Image backgroundImage;
    private JPanel historyBox;
    private JTextField txtInput;
    private JButton btnGuess, btnBack;
    private JLabel lblTurn, lblTime;
    private Timer gameTimer;
    
    private int secondsElapsed = 0;
    private HardModeEngine engine; 

    public HardGamePanel(MainFrame frame) {
        this.mainframe = frame;
        this.engine = new HardModeEngine(); 
        backgroundImage = new ImageIcon("res/Toy.png").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. TIÊU ĐỀ (Đỏ rực cho đúng chất Hardcore)
        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("HARD MISSION", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 60)); 
        lblModeName.setForeground(new Color(231, 76, 60)); 
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.1;
        gbc.insets = new Insets(30, 0, 0, 0);
        add(lblModeName, gbc);

        // 2. PANEL THÔNG SỐ
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        lblTurn = new JLabel();
        lblTurn.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTime = new JLabel("[ 0s ]");
        lblTime.setForeground(Color.WHITE);
        lblTime.setFont(new Font("SansSerif", Font.BOLD, 18));
        statsPanel.add(lblTurn, BorderLayout.WEST);
        statsPanel.add(lblTime, BorderLayout.EAST);

        gbc.gridy = 1; gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 85, 5, 85);
        add(statsPanel, gbc);

        // 3. KHÔNG GIAN HIỂN THỊ WORDLE (LỊCH SỬ)
        JPanel glassCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 100)); // Nền tối cho nổi bật các ô màu
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setPreferredSize(new Dimension(400, 350));
        
        historyBox = new JPanel();
        historyBox.setLayout(new BoxLayout(historyBox, BoxLayout.Y_AXIS));
        historyBox.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(historyBox);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        glassCard.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 2; gbc.weighty = 0.5; gbc.insets = new Insets(10, 50, 20, 50);
        add(glassCard, gbc);

        // 4. INPUT ĐIỀU KHIỂN
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlPanel.setOpaque(false);
        
        txtInput = new JTextField();
        txtInput.setPreferredSize(new Dimension(200, 55)); 
        txtInput.setFont(new Font("SansSerif", Font.BOLD, 30));
        txtInput.setHorizontalAlignment(JTextField.CENTER);
        // Hint cho người dùng
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "#####");
        txtInput.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        
        btnGuess = new JButton("CHECK");
        btnGuess.setPreferredSize(new Dimension(120, 55));
        btnGuess.setBackground(new Color(231, 76, 60));
        btnGuess.setForeground(Color.WHITE);
        btnGuess.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGuess.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        controlPanel.add(txtInput);
        controlPanel.add(btnGuess);
        gbc.gridy = 3; gbc.weighty = 0.1;
        add(controlPanel, gbc);

        btnBack = new JButton("EXIT MISSION");
        btnBack.setPreferredSize(new Dimension(150, 45));
        btnBack.putClientProperty(FlatClientProperties.STYLE, "background: #333; foreground: #eee; arc: 20");
        gbc.gridy = 4; gbc.weighty = 0.1; gbc.insets = new Insets(10, 0, 30, 0);
        add(btnBack, gbc);

        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            lblTime.setText("[ " + secondsElapsed + "s ]");
        });

        btnGuess.addActionListener(this);
        btnBack.addActionListener(this);
        
        // Nhấn Enter để đoán luôn cho nhanh
        txtInput.addActionListener(e -> handleGuess());
    }

    public void initNewGame(String mode) {
        engine.startNewGame();
        secondsElapsed = 0;
        updateTurnDisplay();
        historyBox.removeAll();
        historyBox.repaint();
        txtInput.setText("");
        gameTimer.restart();
    }

    private void handleGuess() {
        String input = txtInput.getText().trim();
        if (input.length() != 5 || !input.matches("\\d+")) {
            logic.SoundManager.playSound("assets/wrong.wav"); // Phát âm thanh báo lỗi
            JOptionPane.showMessageDialog(this, "Mật mã phải có đúng 5 chữ số!");
            return;
        }

        String result = engine.checkWordleGuess(input);

        updateTurnDisplay();
        addWordleRow(input, result);

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
        dao.insertGameResult(score, "HARD", engine.getTargetNumber(), engine.getAttemptsUsed(), secondsElapsed);
    }

    private void addWordleRow(String guess, String result) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        row.setOpaque(false);

        String[] colors = result.split(",");
        char[] numbers = guess.toCharArray();

        for (int i = 0; i < 5; i++) {
            JLabel box = new JLabel(String.valueOf(numbers[i]), SwingConstants.CENTER);
            box.setOpaque(true);
            box.setPreferredSize(new Dimension(50, 50));
            box.setFont(new Font("SansSerif", Font.BOLD, 22));
            box.setForeground(Color.WHITE);

            // Đổ màu theo kết quả Engine trả về
            switch (colors[i]) {
                case "G":
                    box.setBackground(new Color(46, 204, 113));
                    break; // Xanh lá
                case "Y":
                    box.setBackground(new Color(241, 196, 15));
                    break; // Vàng
                default:
                    box.setBackground(new Color(127, 140, 141));
                    break; // Xám
            }
            row.add(box);
        }

        historyBox.add(row);
        historyBox.revalidate();

        // Tự động cuộn xuống cuối
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
        lblTurn.setForeground(new Color(red, green, blue));

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
        logic.SoundManager.playSound("assets/click.wav"); // Phát tiếng click
        
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