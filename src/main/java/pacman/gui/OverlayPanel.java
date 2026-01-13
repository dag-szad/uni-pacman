package pacman.gui;

import javax.swing.*;
import java.awt.*;

public class OverlayPanel extends JPanel {

    private String message = "";

    public OverlayPanel() {
        this.setOpaque(false);
    }

    public void setMessage(String message) {
        this.message = message;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!message.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, getWidth(), getHeight());

            int fontSize = Math.min(getWidth(), getHeight()) / 10;
            Font font = new Font("Arial", Font.BOLD, fontSize);
            g2.setFont(font);
            g2.setColor(Color.WHITE);

            String[] lines = message.split("\n");
            FontMetrics fm = g2.getFontMetrics();
            int totalHeight = fm.getHeight() * lines.length;
            int startY = (getHeight() - totalHeight) / 2 + fm.getAscent();

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                int x = (getWidth() - fm.stringWidth(line)) / 2;
                int y = startY + i * fm.getHeight();
                g2.drawString(line, x, y);
            }
        }
    }
}
