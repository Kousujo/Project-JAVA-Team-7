package ui;

import com.formdev.flatlaf.FlatClientProperties;
import logic.AbstractGameEngine;
import database.GameDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class BaseGamePanel extends JPanel implements ActionListener {
    protected MainFrame mainframe;
    protected Image backgroundImage;
    
    protected JPanel historyBox;
    protected JScrollPane scrollPane;
    protected JTextField txtInput;
    protected JButton btnGuess, btnBack;
    protected JLabel lblTurn, lblTime, lblWarning;
    protected Timer gameTimer;
    
    protected int secondsElapsed = 0;
    protected AbstractGameEngine engine;
    protected GameDAO dao;

    public BaseGamePanel(MainFrame frame) {
        this.mainframe = frame;
        this.dao = new GameDAO();
        this.backgroundImage = new ImageIcon("res/Toy.png").getImage();
        setLayout(new GridBagLayout());
    }

    protected void buildUI() {
        setupTitle();
        setupStats();
        setupFeedback();
        setupHistory();
        setupControls();
        setupWarning();
        setupBackButton();
        setupTimer();   
    }

    protected void setupTitle() {}
    protected abstract void setupHistory();
    protected abstract void setupFeedback();
    protected abstract void handleGuess();

    public void initNewGame(String mode) {
        if (engine != null) {
            engine.startNewGame();
        }
        secondsElapsed = 0;
        updateTurnDisplay();
        historyBox.removeAll();
        historyBox.repaint();
        txtInput.setText("");
        gameTimer.restart();
    }

    protected void setupTitle(String titleText, Color titleColor) {
        graphic.MultiLineOutlineLabel lblModeName = new graphic.MultiLineOutlineLabel(titleText, SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 60)); 
        lblModeName.setForeground(titleColor);
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.1;
        gbc.insets = new Insets(70, 0, 0, 0);
        add(lblModeName, gbc);
    }

    protected void setupStats() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        lblTurn = new JLabel();
        lblTurn.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        lblTime = new JLabel("[ 0s ]");
        lblTime.setForeground(Color.BLUE);
        lblTime.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        statsPanel.add(lblTurn, BorderLayout.WEST);
        statsPanel.add(lblTime, BorderLayout.EAST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1; 
        gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 85, 10, 85);
        add(statsPanel, gbc);
    }

    protected void buildHistoryUI(Color bgColor, Dimension preferredSize, double weighty, Insets insets) {
        JPanel glassCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setPreferredSize(preferredSize);

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
        gbc.weighty = weighty; 
        gbc.insets = insets;   
        add(glassCard, gbc);
    }

    protected void setupControls() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlPanel.setOpaque(false);
        
        txtInput = new JTextField();
        txtInput.setPreferredSize(new Dimension(200, 55)); 
        txtInput.setFont(new Font("SansSerif", Font.BOLD, 28));
        txtInput.setHorizontalAlignment(JTextField.CENTER);
        txtInput.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        
        btnGuess = new JButton("ĐOÁN");
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
        txtInput.addActionListener(e -> handleGuess());
    }

    protected void setupWarning() {
        lblWarning = new JLabel("", SwingConstants.CENTER);
        lblWarning.setForeground(Color.RED);
        lblWarning.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblWarning.setVisible(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.weighty = 0.02;
        add(lblWarning, gbc);
    }

    protected void setupBackButton() {
        btnBack = new JButton("THOÁT");
        btnBack.setPreferredSize(new Dimension(150, 45));
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnBack.putClientProperty(FlatClientProperties.STYLE, "background: #c0392b; foreground: #ffffff; arc: 20");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 0, 30, 0);
        add(btnBack, gbc);
        
        btnBack.addActionListener(this);
    }

    public void setupTimer() {
        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            lblTime.setText("[ " + secondsElapsed + "s ]");
        });
    }

    public void stopGameTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
    }

    protected void updateTurnDisplay() {
        if (engine == null) return;
        
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

    protected void endGame(boolean isWin, String modeName) {
        String status = isWin ? "WINNER" : "GAME OVER";
        
        if (isWin) {
            dao.insertGameResult(engine.calculateFinalScore(), modeName, engine.getSecretCode(), engine.getAttemptsUsed(), secondsElapsed);
        }

        ResultDialog dialog = new ResultDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            status,
            engine.calculateFinalScore(),
            secondsElapsed,
            engine.getAttemptsUsed(),
            engine.getSecretCode()
        );

        dialog.setVisible(true);

        if (dialog.isRetry()) {
            initNewGame(mainframe.getSelectedMode());
        } else {
            mainframe.showScreen("Mode");
        }
    }

    protected void finalizeTurn(String modeName) {
        txtInput.setText("");

        if (engine != null && engine.isGameOver()) {
            stopGameTimer();
            endGame(engine.isWin(), modeName);
        }
    }

    protected boolean validateInput(String input, int requiredLength) {
        if (input.isEmpty()) {
            lblWarning.setText("⚠️ Không được để trống!");
            lblWarning.setVisible(true);
            return false;
        }
        if (!input.matches("\\d+")) {
            lblWarning.setText("⚠️ Vui lòng chỉ nhập số!");
            lblWarning.setVisible(true);
            return false;
        }
        if (requiredLength > 0 && input.length() != requiredLength) {
            lblWarning.setText("⚠️ Mật mã phải có đúng " + requiredLength + " chữ số!");
            lblWarning.setVisible(true);
            return false;
        }
        lblWarning.setVisible(false);
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuess) {
            handleGuess();
        }
        if (e.getSource() == btnBack) {
            gameTimer.stop();
            mainframe.showScreen("Welcome");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}