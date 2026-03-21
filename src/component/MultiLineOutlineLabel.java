package component;

import java.awt.*;
import javax.swing.*;

public class MultiLineOutlineLabel extends JLabel {
    private Color outlineColor = Color.BLACK;
    private int strokeWidth = 2;
    private int lineSpacing = 10;

    public MultiLineOutlineLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public void setOutlineColor(Color color) {
        this.outlineColor = color;
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        String[] lines = getText().split("\n");
        
        int maxWidth = 0;
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, fm.stringWidth(line));
        }
        
        int totalHeight = (fm.getHeight() * lines.length) + (lineSpacing * (lines.length - 1));
        
        Insets insets = getInsets();
        return new Dimension(
            maxWidth + insets.left + insets.right + (strokeWidth * 2), 
            totalHeight + insets.top + insets.bottom + (strokeWidth * 2)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Insets insets = getInsets();
        int viewWidth = getWidth() - insets.left - insets.right;
        int viewHeight = getHeight() - insets.top - insets.bottom;

        FontMetrics fm = g2.getFontMetrics(getFont());
        String[] lines = getText().split("\n");
        int totalHeight = (fm.getHeight() * lines.length) + (lineSpacing * (lines.length - 1));
        
        int startY = insets.top + (viewHeight - totalHeight) / 2 + fm.getAscent();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int x = insets.left;
            if (getHorizontalAlignment() == SwingConstants.CENTER) {
                x = insets.left + (viewWidth - fm.stringWidth(line)) / 2;
            }
            int currentY = startY + (i * (fm.getHeight() + lineSpacing));

            g2.setColor(outlineColor);
            for (int sx = -strokeWidth; sx <= strokeWidth; sx++) {
                for (int sy = -strokeWidth; sy <= strokeWidth; sy++) {
                    if (sx != 0 || sy != 0) g2.drawString(line, x + sx, currentY + sy);
                }
            }

            g2.setColor(getForeground());
            g2.drawString(line, x, currentY);
        }
        g2.dispose();
    }
}