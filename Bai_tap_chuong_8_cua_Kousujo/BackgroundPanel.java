import java.awt.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try {
            java.net.URL imgURL = getClass().getResource("/res/" + fileName);
            if (imgURL != null) {
                this.backgroundImage = new ImageIcon(imgURL).getImage();
            } else {
                System.err.println("Không tìm thấy ảnh: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}