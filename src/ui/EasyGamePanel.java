package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import logic.EasyModeEngine;
import javax.swing.*;
import java.awt.*;

public class EasyGamePanel extends BaseGamePanel {
    private JLabel lblFeedback;
    
    private static final String TOO_HIGH = "QUÁ CAO";
    private static final String TOO_LOW = "QUÁ THẤP";
    private static final String MATCH = "MATCH!";
    private static final Color COLOR_TOO_HIGH = new Color(231, 76, 60);
    private static final Color COLOR_TOO_LOW = new Color(52, 152, 219);
    private static final Color COLOR_MATCH = new Color(46, 204, 113); 

    public EasyGamePanel(MainFrame frame) {
        super(frame);
        this.engine = new EasyModeEngine();
        buildUI();
    }
    
    @Override
    protected void setupTitle() {
        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("EASY GUESS", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 60)); 
        lblModeName.setForeground(new Color(143,185,53));
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.1;
        gbc.insets = new Insets(70, 0, 0, 0);
        add(lblModeName, gbc);
    }
    
    @Override
    protected void setupFeedback() {
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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.weighty = 0.15;
        gbc.fill = GridBagConstraints.NONE;
        add(lblFeedback, gbc);
    }
    
    @Override
    protected void setupHistory() {
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

        scrollPane = new JScrollPane(historyBox);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        glassCard.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.insets = new Insets(20, 65, 20, 65);
        add(glassCard, gbc);
    }
    
    @Override
    protected void setupControls() {
        super.setupControls(); 
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0-100");
        btnGuess.setText("ĐOÁN!");
        btnGuess.setBackground(new Color(70, 130, 180));
    }

    public void initNewGame(String mode) {
        engine.startNewGame(); 
        secondsElapsed = 0;

        updateTurnDisplay();
        lblFeedback.setText("NHẬP SỐ TỪ 0 - 100");
        lblFeedback.setForeground(Color.WHITE);
        historyBox.removeAll();
        historyBox.repaint();
        txtInput.setText("");
        gameTimer.restart();
    }

    @Override
    protected void handleGuess() {
        String input = txtInput.getText().trim();
        if (!validateInput(input, 0)) return;

        int guess;
        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            lblWarning.setText("⚠️ Số nhập vào quá lớn! Hãy nhập từ 0-100.");
            lblWarning.setVisible(true);
            return;
        }
        String result = engine.checkGuess(guess);

        updateTurnDisplay();
        addHistoryRow("Lượt " + engine.getAttemptsUsed() + ": [" + guess + "] -> " + result);
        updateFeedback(result);
        txtInput.setText("");

        if (engine.isGameOver()) {
            gameTimer.stop();
            endGame(engine.isWin(), "EASY");
        }
    }
    
    private void updateFeedback(String result) {
        if (result.equals(TOO_HIGH)) {
            lblFeedback.setText("↓ QUÁ CAO ↓");
            lblFeedback.setForeground(COLOR_TOO_HIGH);
        } else if (result.equals(TOO_LOW)) {
            lblFeedback.setText("↑ QUÁ THẤP ↑");
            lblFeedback.setForeground(COLOR_TOO_LOW);
        } else if (result.equals(MATCH)) {
            lblFeedback.setText("✨ CHÍNH XÁC! ✨");
            lblFeedback.setForeground(COLOR_MATCH);
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
}