package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import logic.NormalModeEngine; 
import database.GameDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NormalGamePanel extends JPanel implements ActionListener {
    private MainFrame mainframe;
    private Image backgroundImage;
    private JPanel historyBox;
    private JScrollPane scrollPane;
    private JTextField txtInput;
    private JButton btnGuess, btnBack;
    private JLabel lblTurn, lblTime;
    private JLabel lblFeedbackLeft, lblFeedbackRight;
    private JLabel lblBonus;
    private Timer gameTimer;
    
    private int secondsElapsed = 0;
    private NormalModeEngine engine;
    private GameDAO dao;
    
    // Constants
    private static final String MATCH = "MATCH";
    private static final String UP = "UP";
    private static final String DOWN = "DOWN";
    private static final Color COLOR_MATCH = new Color(46, 204, 113);
    private static final Color COLOR_UP = new Color(52, 152, 219);
    private static final Color COLOR_DOWN = new Color(231, 76, 60); 

    public NormalGamePanel(MainFrame frame) {
        this.mainframe = frame;
        this.engine = new NormalModeEngine();
        this.dao = new GameDAO();
        backgroundImage = new ImageIcon("res/Toy.png").getImage();
        setLayout(new GridBagLayout());
        
        setupTitle();
        setupStats();
        setupFeedback();
        setupHistory();
        setupControls();
        setupBackButton();
        setupTimer();
    }
    
    private void setupTitle() {
        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("NORMAL MISSION", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 60)); 
        lblModeName.setForeground(new Color(255, 215, 0));
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.1;
        gbc.insets = new Insets(40, 0, 0, 0);
        add(lblModeName, gbc);
    }
    
    private void setupStats() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        lblTurn = new JLabel();
        lblTurn.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        lblBonus = new JLabel("BONUS: READY");
        lblBonus.setForeground(Color.LIGHT_GRAY);
        lblBonus.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 16));
        
        lblTime = new JLabel("[ 0s ]");
        lblTime.setForeground(Color.CYAN);
        lblTime.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        statsPanel.add(lblTurn, BorderLayout.WEST);
        statsPanel.add(lblBonus, BorderLayout.CENTER);
        lblBonus.setHorizontalAlignment(SwingConstants.CENTER);
        statsPanel.add(lblTime, BorderLayout.EAST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1; 
        gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 85, 10, 85);
        add(statsPanel, gbc);
    }
    
    private void setupFeedback() {
        JPanel feedbackPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        feedbackPanel.setOpaque(false);
        
        lblFeedbackLeft = createFeedbackLabel("??");
        lblFeedbackRight = createFeedbackLabel("??");
        
        feedbackPanel.add(lblFeedbackLeft);
        feedbackPanel.add(lblFeedbackRight);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.weighty = 0.15;
        gbc.fill = GridBagConstraints.NONE;
        add(feedbackPanel, gbc);
    }
    
    private void setupHistory() {
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
        glassCard.setPreferredSize(new Dimension(380, 180));
        historyBox = new JPanel();
        historyBox.setLayout(new BoxLayout(historyBox, BoxLayout.Y_AXIS));
        historyBox.setOpaque(false);
        scrollPane = new JScrollPane(historyBox);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        glassCard.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(10, 65, 10, 65);
        add(glassCard, gbc);
    }
    
    private void setupControls() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlPanel.setOpaque(false);
        txtInput = new JTextField();
        txtInput.setPreferredSize(new Dimension(200, 55)); 
        txtInput.setFont(new Font("SansSerif", Font.BOLD, 28));
        txtInput.setHorizontalAlignment(JTextField.CENTER);
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1000-9999");
        txtInput.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        
        btnGuess = new JButton("ĐOÁN!");
        btnGuess.setPreferredSize(new Dimension(120, 55));
        btnGuess.setBackground(new Color(70, 130, 180));
        btnGuess.setForeground(Color.WHITE);
        btnGuess.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGuess.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        controlPanel.add(txtInput);
        controlPanel.add(btnGuess);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weighty = 0.1;
        add(controlPanel, gbc);
        
        btnGuess.addActionListener(this);
    }
    
    private void setupBackButton() {
        btnBack = new JButton("THOÁT");
        btnBack.setPreferredSize(new Dimension(150, 45));
        btnBack.putClientProperty(FlatClientProperties.STYLE, "background: #c0392b; foreground: #ffffff; arc: 20");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 0, 40, 0);
        add(btnBack, gbc);
        
        btnBack.addActionListener(this);
    }
    
    private void setupTimer() {
        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            lblTime.setText("[ " + secondsElapsed + "s ]");
        });
    }

    private JLabel createFeedbackLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 160)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lbl.setFont(new Font("SansSerif", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(180, 80));
        lbl.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        return lbl;
    }

    public void initNewGame(String mode) {
        engine.startNewGame(); 
        secondsElapsed = 0;

        updateTurnDisplay();
        
        lblFeedbackLeft.setText("LEFT half");
        lblFeedbackRight.setText("RIGHT half");
        lblFeedbackLeft.setForeground(Color.WHITE);
        lblFeedbackRight.setForeground(Color.WHITE);
        lblBonus.setText("BONUS: READY");
        lblBonus.setForeground(Color.LIGHT_GRAY);
        
        historyBox.removeAll();
        historyBox.repaint();
        txtInput.setText("");
        gameTimer.restart();
    }

    private void handleGuess() {
        String input = txtInput.getText().trim();
        if (!isValidInput(input)) {
            JOptionPane.showMessageDialog(this, "Hãy nhập đúng 4 chữ số!");
            return;
        }

        int guess = Integer.parseInt(input);
        String result = engine.checkGuess(guess);

        updateTurnDisplay();

        // Xử lý tách chuỗi gợi ý dựa trên dấu "|"
        String[] hints = result.split("\\|");

        if (hints.length == 2) {
            updateFeedback(lblFeedbackLeft, hints[0]);
            updateFeedback(lblFeedbackRight, hints[1]);
        }

        // Cập nhật trạng thái Bonus nếu có
        if (lblFeedbackLeft.getText().equals(MATCH) || lblFeedbackRight.getText().equals(MATCH)) {
            if (engine.getAttemptsUsed() <= 5) {
                lblBonus.setText("BONUS ACTIVE! 🔥");
                lblBonus.setForeground(new Color(255, 215, 0));
            }
        }

        addHistoryRow("Lượt " + engine.getAttemptsUsed() + ": [" + input + "] -> " + result.replace("|", " / "));
        txtInput.setText("");

        if (engine.isGameOver()) {
            gameTimer.stop();
            endGame(engine.isWin());
        }
    }
    
    private boolean isValidInput(String input) {
        return input.length() == 4 && input.matches("\\d+");
    }

    private void handleWin() {
        // LƯU VÀO DATABASE
        dao.insertGameResult(engine.calculateFinalScore(), "NORMAL", engine.getTargetNumber(), engine.getAttemptsUsed(), secondsElapsed);
    }

    private void updateFeedback(JLabel lbl, String hint) {
        lbl.setText(hint);
        switch (hint) {
            case MATCH:
                lbl.setForeground(COLOR_MATCH);
                break;
            case UP:
                lbl.setForeground(COLOR_UP);
                break;
            case DOWN:
                lbl.setForeground(COLOR_DOWN);
                break;
            default:
                lbl.setForeground(Color.WHITE);
        }
    }

    private void addHistoryRow(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        historyBox.add(lbl);
        historyBox.revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
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
        
        if (isWin) {
            handleWin();
        }

        ResultDialog dialog = new ResultDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            status,
            engine.calculateFinalScore(),
            secondsElapsed,
            engine.getAttemptsUsed(),
            engine.getTargetNumber()
        );
        dialog.setVisible(true);

        if (dialog.isRetry()) {
            initNewGame(mainframe.getSelectedMode());
        } else {
            mainframe.showScreen("Welcome");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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