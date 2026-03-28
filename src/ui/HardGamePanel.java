package ui;

import com.formdev.flatlaf.FlatClientProperties;
import graphic.MultiLineOutlineLabel;
import logic.HardModeEngine;
import javax.swing.*;
import java.awt.*;

public class HardGamePanel extends BaseGamePanel { 
    
    private static final String GREEN = "G";
    private static final String YELLOW = "Y";
    private static final Color COLOR_GREEN = new Color(46, 204, 113);
    private static final Color COLOR_YELLOW = new Color(241, 196, 15);
    private static final Color COLOR_GRAY = new Color(127, 140, 141); 

    public HardGamePanel(MainFrame frame) {
        super(frame);
        this.engine = new HardModeEngine();
        buildUI();
    }

    @Override
    protected void setupTitle() {
        MultiLineOutlineLabel lblModeName = new MultiLineOutlineLabel("HARD GUESS", SwingConstants.CENTER);
        lblModeName.setFont(new Font("SansSerif", Font.BOLD, 60)); 
        lblModeName.setForeground(new Color(230,71,71));
        lblModeName.setOutlineColor(new Color(0, 0, 0, 200));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weighty = 0.1;
        gbc.insets = new Insets(70, 0, 0, 0);
        add(lblModeName, gbc);
    }
    
    @Override
    protected void setupFeedback() {
    }
    
    @Override
    protected void setupControls() {
        super.setupControls();
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "#####");
    }
    
    @Override
    protected void setupHistory() {
        JPanel glassCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 100)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setPreferredSize(new Dimension(400, 350));
        
        historyBox = new JPanel();
        historyBox.setLayout(new BoxLayout(historyBox, BoxLayout.Y_AXIS));
        historyBox.setOpaque(false);
        
        scrollPane = new JScrollPane(historyBox);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        glassCard.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.weighty = 0.5;
        gbc.insets = new Insets(10, 50, 20, 50);
        add(glassCard, gbc);
    }

    @Override
    protected void setupBackButton() {
        super.setupBackButton(); 
        btnBack.setText("THOÁT");
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

    @Override
    protected void handleGuess() {
        String input = txtInput.getText().trim();
        if (!validateInput(input, 5)) return;

        int guess = Integer.parseInt(input);
        String result = engine.checkGuess(guess);

        updateTurnDisplay(); 
        
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        row.setOpaque(false);
        String[] colors = result.split(",");
        char[] numbers = input.toCharArray();
        
        for (int i = 0; i < 5; i++) {
            JLabel box = new JLabel(String.valueOf(numbers[i]), SwingConstants.CENTER);
            box.setOpaque(true);
            box.setPreferredSize(new Dimension(50, 50));
            box.setFont(new Font("SansSerif", Font.BOLD, 22));
            box.setForeground(Color.WHITE);
            switch (colors[i]) {
                case GREEN: box.setBackground(COLOR_GREEN); break;
                case YELLOW: box.setBackground(COLOR_YELLOW); break;
                default: box.setBackground(COLOR_GRAY); break;
            }
            row.add(box);
        }
        historyBox.add(row);
        historyBox.revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });

        txtInput.setText("");

        if (engine.isGameOver()) {
            gameTimer.stop();
            super.endGame(engine.isWin(), "HARD");
        }
    }
}