package ui;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;

public class ResultDialog extends JDialog {
    private boolean retry = false;

    public ResultDialog(Frame owner, String status, int score, int time, int turns, String secret ) {
        super(owner, true);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        JPanel content = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.setColor(new Color(70, 130, 180));
                g2.setStroke(new BasicStroke(4));
                g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 40, 40);
                g2.dispose();
            }
        };
        content.setPreferredSize(new Dimension(320, 420));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblStatus = new JLabel(status, SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 40));
        lblStatus.setForeground(status.equals("WINNER") ? new Color(46, 204, 113) : new Color(231, 76, 60));
        
        gbc.gridy = 0; 
        gbc.insets = new Insets(10, 0, 20, 0);
        content.add(lblStatus, gbc);

        String stats = String.format(
            "<html><div style='text-align: center; font-family: SansSerif; font-size: 13pt; color: #333;'>"
            + "⭐ Điểm: <b>%d</b> | 🎯 Lượt: <b>%d</b><br>"
            + "🕒 Thời gian: <b>%ds</b><br>"
            + "<font color='red'>🔑 Số bí mật: <b>%s</b></font>"
            + "</div></html>", score, time, turns, secret
        );
        JLabel lblStats = new JLabel(stats, SwingConstants.CENTER);
        
        gbc.gridy = 1; 
        gbc.insets = new Insets(10, 0, 30, 0);
        content.add(lblStats, gbc);

        JButton btnRetry = createBtn("AGAIN!", new Color(70, 130, 180), Color.WHITE);
        JButton btnMenu = createBtn("NEW GAME", Color.WHITE, Color.BLACK);

        btnRetry.addActionListener(e -> { retry = true; dispose(); });
        btnMenu.addActionListener(e -> { retry = false; dispose(); });

        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 10, 0);
        content.add(btnRetry, gbc);
        gbc.gridy = 3;
        content.add(btnMenu, gbc);

        add(content);
        pack();
        setLocationRelativeTo(owner);
    }

    private JButton createBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(0, 50));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        btn.setFocusPainted(false);
        return btn;
    }

    public boolean isRetry() { return retry; }
}