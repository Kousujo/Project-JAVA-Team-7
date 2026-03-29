package ui;

import com.formdev.flatlaf.FlatClientProperties;
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
    public void initNewGame(String mode) {
        super.initNewGame(mode);
        lblFeedback.setText("NHẬP SỐ TỪ 0 - 100");
        lblFeedback.setForeground(Color.WHITE);
    }
    
    @Override
    protected void setupTitle() {
        super.setupTitle("EASY GUESS", new Color(143, 185, 53));
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
        buildHistoryUI(
            new Color(255, 255, 255, 140), 
            new Dimension(350, 200), 
            0.3, 
            new Insets(20, 65, 20, 65)
        );
    }
    
    @Override
    protected void setupControls() {
        super.setupControls(); 
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0-100");
        btnGuess.setText("ĐOÁN!");
        btnGuess.setBackground(new Color(70, 130, 180));
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
        finalizeTurn("EASY");
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