package ui;

import com.formdev.flatlaf.FlatClientProperties;
import logic.SoundManager;
import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private MainFrame mainframe;
    private Image backgroundImage;

    public SettingsPanel(MainFrame frame) {
        this.mainframe = frame;
        
        // 1. Nạp ảnh nền giống với BaseGamePanel
        this.backgroundImage = new ImageIcon("res/Toy.png").getImage();
        
        // Căn giữa toàn bộ bằng GridBagLayout
        setLayout(new GridBagLayout());
        
        // 2. Tạo hiệu ứng Kính mờ (Glassmorphism) để đồng bộ với màn hình Game
        JPanel glassCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Nền trắng mờ để nổi bật chữ
                g2.setColor(new Color(255, 255, 255, 200)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        glassCard.setOpaque(false);
        glassCard.setLayout(new BoxLayout(glassCard, BoxLayout.Y_AXIS));
        glassCard.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Tiêu đề màn hình
        JLabel lblTitle = new JLabel("CÀI ĐẶT HỆ THỐNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitle.setForeground(new Color(44, 62, 80)); // Màu chữ tối cho dễ đọc trên nền sáng
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 3. NÚT BẬT/TẮT HIỆU ỨNG ÂM THANH (SFX)
        JCheckBox chkSound = new JCheckBox("Bật hiệu ứng âm thanh (Click, Win, Error)");
        chkSound.setFont(new Font("SansSerif", Font.BOLD, 18));
        chkSound.setSelected(SoundManager.isSoundEnabled); 
        chkSound.setAlignmentX(Component.CENTER_ALIGNMENT);
        chkSound.setOpaque(false);
        chkSound.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        chkSound.addActionListener(e -> {
            SoundManager.isSoundEnabled = chkSound.isSelected();
            if (SoundManager.isSoundEnabled) {
                SoundManager.playSound("assets/click.wav");
            }
        });

        // 4. NÚT BẬT/TẮT NHẠC NỀN (BGM)
        JCheckBox chkBgm = new JCheckBox("Bật nhạc nền (Background Music)");
        chkBgm.setFont(new Font("SansSerif", Font.BOLD, 18));
        chkBgm.setSelected(SoundManager.isBgmEnabled);
        chkBgm.setAlignmentX(Component.CENTER_ALIGNMENT);
        chkBgm.setOpaque(false);
        chkBgm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        chkBgm.addActionListener(e -> {
            SoundManager.isBgmEnabled = chkBgm.isSelected();
            if (SoundManager.isBgmEnabled) {
                SoundManager.playBgm("assets/bgm.wav");
            } else {
                SoundManager.stopBgm();
            }
        });

        // 5. NÚT QUAY LẠI (Được phóng to và gắn style FlatLaf)
        JButton btnBack = new JButton("QUAY LẠI");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 22)); // Font to hơn
        btnBack.setPreferredSize(new Dimension(250, 55)); // Kích thước bự hơn
        btnBack.setMaximumSize(new Dimension(250, 55)); 
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Dùng FlatLaf để bo góc và tô màu giống hệt nút THOÁT ở màn hình Game
        btnBack.putClientProperty(FlatClientProperties.STYLE, "background: #c0392b; foreground: #ffffff; arc: 20");
        
        btnBack.addActionListener(e -> mainframe.showScreen("Welcome"));

        // Thêm các thành phần vào thẻ kính mờ (glassCard)
        glassCard.add(lblTitle);
        glassCard.add(Box.createRigidArea(new Dimension(0, 50)));
        glassCard.add(chkSound);
        glassCard.add(Box.createRigidArea(new Dimension(0, 20)));
        glassCard.add(chkBgm);
        glassCard.add(Box.createRigidArea(new Dimension(0, 60)));
        glassCard.add(btnBack);

        // Thêm thẻ kính mờ vào giữa màn hình
        add(glassCard);
    }

    // Ghi đè phương thức paintComponent để vẽ ảnh nền
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}