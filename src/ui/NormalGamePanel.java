package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import logic.NormalModeEngine;
import javax.swing.*;
import java.awt.*;

public class NormalGamePanel extends BaseGamePanel {
    private JLabel lblFeedbackLeft, lblFeedbackRight;
    private JLabel lblBonus;
    
    private static final String MATCH = "MATCH";
    private static final String UP = "UP";
    private static final String DOWN = "DOWN";
    private static final Color COLOR_MATCH = new Color(46, 204, 113);
    private static final Color COLOR_UP = new Color(52, 152, 219);
    private static final Color COLOR_DOWN = new Color(231, 76, 60); 

    public NormalGamePanel(MainFrame frame) {
        super(frame);
        this.engine = new NormalModeEngine();
        buildUI();
    }
    
    @Override
    protected void setupTitle() {
        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("NORMAL GUESS", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 60)); 
        lblModeName.setForeground(new Color(230,226,46));
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.1;
        gbc.insets = new Insets(70, 0, 0, 0);
        add(lblModeName, gbc);
    }
    
    @Override
    protected void setupStats() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        lblTurn = new JLabel();
        lblTurn.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        lblBonus = new JLabel("BONUS: READY");
        lblBonus.setForeground(Color.LIGHT_GRAY);
        lblBonus.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 16));
        
        lblTime = new JLabel("[ 0s ]");
        lblTime.setForeground(Color.BLUE);
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
    
    @Override
    protected void setupFeedback() {
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
    
    @Override
    protected void setupControls() {
        super.setupControls();
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1000-9999");
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

    @Override
    protected void handleGuess() {
        String input = txtInput.getText().trim();
        if (input.length() != 4 || !input.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Hãy nhập đúng 4 chữ số!");
            return;
        }

        int guess = Integer.parseInt(input);
        String result = engine.checkGuess(guess);

        updateTurnDisplay(); 

        String[] hints = result.split("\\|");

        if (hints.length == 2) {
            lblFeedbackLeft.setText(hints[0]);
            switch (hints[0]) {
                case MATCH: lblFeedbackLeft.setForeground(COLOR_MATCH); break;
                case UP: lblFeedbackLeft.setForeground(COLOR_UP); break;
                case DOWN: lblFeedbackLeft.setForeground(COLOR_DOWN); break;
                default: lblFeedbackLeft.setForeground(Color.WHITE);
            }
            lblFeedbackRight.setText(hints[1]);
            switch (hints[1]) {
                case MATCH: lblFeedbackRight.setForeground(COLOR_MATCH); break;
                case UP: lblFeedbackRight.setForeground(COLOR_UP); break;
                case DOWN: lblFeedbackRight.setForeground(COLOR_DOWN); break;
                default: lblFeedbackRight.setForeground(Color.WHITE);
            }
        }

        if (lblFeedbackLeft.getText().equals(MATCH) || lblFeedbackRight.getText().equals(MATCH)) {
            if (engine.getAttemptsUsed() <= 5) {
                lblBonus.setText("BONUS ACTIVE! 🔥");
                lblBonus.setForeground(new Color(255, 215, 0));
            }
        }

        JLabel lbl = new JLabel("Lượt " + engine.getAttemptsUsed() + ": [" + input + "] -> " + result.replace("|", " / "));
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        historyBox.add(lbl);
        historyBox.revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });

        txtInput.setText("");

        if (engine.isGameOver()) {
            gameTimer.stop();
            super.endGame(engine.isWin(), "NORMAL"); 
        }
    }
}