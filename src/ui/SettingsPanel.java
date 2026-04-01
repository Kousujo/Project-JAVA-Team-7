package ui;

import logic.SoundManager;
import logic.AppSettings;
import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        // Xếp các nút từ trên xuống dưới
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1A. TẠO NÚT BẬT/TẮT HIỆU ỨNG ÂM THANH (SFX)
        JCheckBox chkSound = new JCheckBox("Bật hiệu ứng âm thanh (Click, Win, Error)");
        chkSound.setFont(new Font("Arial", Font.PLAIN, 16));
        chkSound.setSelected(SoundManager.isSoundEnabled); // Lấy trạng thái hiện tại
        
        chkSound.addActionListener(e -> {
            // Cập nhật lại biến khi người chơi tick hoặc bỏ tick
            SoundManager.isSoundEnabled = chkSound.isSelected();
            
            // Test thử một tiếng click nếu họ bật lại
            if (SoundManager.isSoundEnabled) {
                SoundManager.playSound("assets/click.wav");
            }
        });

        // 1B. TẠO NÚT BẬT/TẮT NHẠC NỀN (BGM)
        JCheckBox chkBgm = new JCheckBox("Bật nhạc nền (Background Music)");
        chkBgm.setFont(new Font("Arial", Font.PLAIN, 16));
        chkBgm.setSelected(SoundManager.isBgmEnabled);
        
        chkBgm.addActionListener(e -> {
            SoundManager.isBgmEnabled = chkBgm.isSelected();
            
            if (SoundManager.isBgmEnabled) {
                // Nếu bật thì cho phát lại nhạc nền
                SoundManager.playBgm("assets/bgm.wav");
            } else {
                // Tắt thì dừng hẳn nhạc nền
                SoundManager.stopBgm();
            }
        });

        // 2. TẠO NÚT BẬT/TẮT CHẾ ĐỘ HIỂN THỊ ĐÁP ÁN (DÀNH CHO DEV)
        JCheckBox chkDevMode = new JCheckBox("Hiển thị đáp án (Dành cho Dev test game)");
        chkDevMode.setFont(new Font("Arial", Font.PLAIN, 16));
        chkDevMode.setSelected(AppSettings.isDevMode);
        
        chkDevMode.addActionListener(e -> {
            // Cập nhật lại biến
            AppSettings.isDevMode = chkDevMode.isSelected();
            
            if (AppSettings.isDevMode) {
                JOptionPane.showMessageDialog(this, "Đã bật chế độ xem trước đáp án!");
            }
        });

        // Thêm các nút này vào màn hình
        add(chkSound);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(chkBgm);
        add(Box.createRigidArea(new Dimension(0, 20))); // Khoảng trống cho đẹp
        add(chkDevMode);
    }
}
