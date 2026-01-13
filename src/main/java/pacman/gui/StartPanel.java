package pacman.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {

    public StartPanel(ActionListener startListener) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nagłówek
        JLabel title = new JLabel("PAC-MAN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.decode("#F0CA77"));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        this.add(title, BorderLayout.NORTH);

        // Instrukcje
        String htmlText = "<html><div style='text-align: center; color: white;'>" +
                "Sterowanie:<br>" +
                "W / ↑ – ruch w górę<br>" +
                "S / ↓ – ruch w dół<br>" +
                "A / ← – ruch w lewo<br>" +
                "D / → – ruch w prawo<br>" +
                "P – pauza<br>" +
                "Q – zakończenie gry" +
                "</div></html>";

        JLabel instructions = new JLabel(htmlText, SwingConstants.CENTER);
        instructions.setFont(new Font("Monospaced", Font.PLAIN, 18));
        instructions.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        this.add(instructions, BorderLayout.CENTER);

        // Przycisk startu
        JButton startButton = new JButton("Rozpocznij grę");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.addActionListener(startListener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(startButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
