package ui;

import com.formdev.flatlaf.FlatClientProperties;
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
    public void initNewGame(String mode) {
        super.initNewGame(mode);
        lblFeedbackLeft.setText("LEFT half");
        lblFeedbackRight.setText("RIGHT half");
        lblFeedbackLeft.setForeground(Color.WHITE);
        lblFeedbackRight.setForeground(Color.WHITE);
        lblBonus.setText("BONUS: READY");
        lblBonus.setForeground(Color.LIGHT_GRAY);
    }
    
    @Override
    protected void setupTitle() {
        super.setupTitle("NORMAL GUESS", new Color(230, 226, 46));
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
    protected void setupHistory() {
        buildHistoryUI(
            new Color(0, 0, 0, 100), 
            new Dimension(400, 250), 
            0.4, 
            new Insets(10, 50, 20, 50)
        );
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

        lblWarning = new JLabel("", SwingConstants.CENTER);
        lblWarning.setForeground(Color.RED);
        lblWarning.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblWarning.setVisible(false);

        GridBagConstraints gbcWarn = new GridBagConstraints();
        gbcWarn.gridx = 0; 
        gbcWarn.gridy = 6;
        gbcWarn.weighty = 0.02;
        add(lblWarning, gbcWarn);
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

    @Override
    protected void setupControls() {
        super.setupControls();
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1000-9999");
    }

    @Override
    protected void handleGuess() {
        String input = txtInput.getText().trim();
        if (!validateInput(input, 4)) return;

        int guess = Integer.parseInt(input);
        String result = engine.checkGuess(guess);

        updateTurnDisplay(); 

        String[] hints = result.split("\\|");

        boolean allMatch = false;
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
            allMatch = hints[0].equals(MATCH) && hints[1].equals(MATCH);
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

        finalizeTurn("NORMAL");
    }
}