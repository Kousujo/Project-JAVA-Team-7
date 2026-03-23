package ui;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResultDialog extends JDialog {
    private boolean retry = false;

    public ResultDialog(Frame owner, String status, int score, int time, int turns) {
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
        content.setPreferredSize(new Dimension(350, 450));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblStatus = new JLabel(status, SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 40));
        lblStatus.setForeground(status.equals("WINNER") ? new Color(46, 204, 113) : new Color(231, 76, 60));
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 20, 0);
        content.add(lblStatus, gbc);

        String statsHtml = "<html><div style='text-align: center; font-family: SansSerif; font-size: 14pt; color: #333;'>"
                + "⭐ Điểm số: <b>" + score + "</b><br><br>"
                + "🕒 Thời gian: <b>" + time + "s</b><br><br>"
                + "🎯 Số lượt: <b>" + turns + "</b>"
                + "</div></html>";
        JLabel lblStats = new JLabel(statsHtml, SwingConstants.CENTER);
        gbc.gridy = 1; gbc.insets = new Insets(10, 0, 30, 0);
        content.add(lblStats, gbc);

        JButton btnRetry = new JButton("CHƠI LẠI");
        btnRetry.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnRetry.setPreferredSize(new Dimension(0, 50));
        btnRetry.setBackground(new Color(70, 130, 180));
        btnRetry.setForeground(Color.WHITE);
        btnRetry.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        JButton btnMenu = new JButton("VỀ MENU");
        btnMenu.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnMenu.setPreferredSize(new Dimension(0, 50));
        btnMenu.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 10, 0);
        content.add(btnRetry, gbc);
        gbc.gridy = 3;
        content.add(btnMenu, gbc);

        btnRetry.addActionListener(e -> { retry = true; dispose(); });
        btnMenu.addActionListener(e -> { retry = false; dispose(); });

        add(content);
        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isRetry() { return retry; }
}