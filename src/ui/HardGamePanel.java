package ui;

import com.formdev.flatlaf.FlatClientProperties;
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
    public void initNewGame(String mode) {
        super.initNewGame(mode);
    }

    @Override
    protected void setupTitle() {
        super.setupTitle("HARD GUESS", new Color(230, 226, 46));
    }
    
    @Override
    protected void setupFeedback() {
    }

    @Override
    protected void setupHistory() {
        buildHistoryUI(
            new Color(0, 0, 0, 100), 
            new Dimension(400, 350), 
            0.5, 
            new Insets(10, 50, 20, 50)
        );
    }
    
    @Override
    protected void setupControls() {
        super.setupControls();
        txtInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "#####");
    }
    

    @Override
    protected void setupBackButton() {
        super.setupBackButton(); 
        btnBack.setText("THOÁT");
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

        if (!result.contains("G,G,G,G,G")) {
            logic.SoundManager.playSound("assets/wrong.wav");
        }
        finalizeTurn("HARD");
    }
}