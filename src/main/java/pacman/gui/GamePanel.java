package pacman.gui;

import pacman.game.GameState;
import pacman.entity.Pacman;
import pacman.entity.Ghost;
import pacman.board.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {

    private final GameState gameState;
    public static final int CELL_SIZE = 40;

    public GamePanel(GameState gameState) {
        this.gameState = gameState;

        GameBoard board = gameState.getGameBoard();
        this.setPreferredSize(new Dimension(board.getWidth() * CELL_SIZE, board.getHeight() * CELL_SIZE));
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        synchronized (gameState) {

            GameBoard board = gameState.getGameBoard();
            Pacman pacman = gameState.getPacman();
            List<Ghost> ghosts = gameState.getGhosts();

            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int CELL_SIZE = 40;
            int radius = 15;
            Color wallColor = Color.decode("#8377FF");

            // Ściany
            for (int y = 0; y < board.getHeight(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {

                    if (board.getField(x, y) == '#') {
                        int px = x * CELL_SIZE;
                        int py = y * CELL_SIZE;

                        g2.setColor(Color.BLACK);
                        g2.fillRect(px, py, CELL_SIZE, CELL_SIZE);

                        g2.setColor(wallColor);
                        g2.setStroke(new BasicStroke(4));

                        boolean top = y == 0 || board.getField(x, y - 1) != '#';
                        boolean bottom = y == board.getHeight() - 1 || board.getField(x, y + 1) != '#';
                        boolean left = x == 0 || board.getField(x - 1, y) != '#';
                        boolean right = x == board.getWidth() - 1 || board.getField(x + 1, y) != '#';

                        if (top)
                            g2.drawLine(px + (left ? radius / 2 : 0), py,
                                        px + CELL_SIZE - (right ? radius / 2 : 0), py);
                        if (bottom)
                            g2.drawLine(px + (left ? radius / 2 : 0), py + CELL_SIZE,
                                        px + CELL_SIZE - (right ? radius / 2 : 0), py + CELL_SIZE);
                        if (left)
                            g2.drawLine(px, py + (top ? radius / 2 : 0),
                                        px, py + CELL_SIZE - (bottom ? radius / 2 : 0));
                        if (right)
                            g2.drawLine(px + CELL_SIZE, py + (top ? radius / 2 : 0),
                                        px + CELL_SIZE, py + CELL_SIZE - (bottom ? radius / 2 : 0));

                        if (top && left)
                            g2.drawArc(px, py, radius, radius, 90, 90);
                        if (top && right)
                            g2.drawArc(px + CELL_SIZE - radius, py, radius, radius, 0, 90);
                        if (bottom && left)
                            g2.drawArc(px, py + CELL_SIZE - radius, radius, radius, 180, 90);
                        if (bottom && right)
                            g2.drawArc(px + CELL_SIZE - radius, py + CELL_SIZE - radius,
                                       radius, radius, 270, 90);
                    }
                }
            }

            // Punkty
            for (int y = 0; y < board.getHeight(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {

                    char field = board.getField(x, y);

                    switch (field) {
                        case '.' -> {
                            g2.setColor(Color.WHITE);
                            int dotSize = CELL_SIZE / 6;
                            g2.fillOval(
                                    x * CELL_SIZE + CELL_SIZE / 2 - dotSize / 2,
                                    y * CELL_SIZE + CELL_SIZE / 2 - dotSize / 2,
                                    dotSize,
                                    dotSize
                            );
                        }
                        case '*' -> {
                            g2.setColor(Color.WHITE);
                            int dotSize = CELL_SIZE / 3;
                            g2.fillOval(
                                    x * CELL_SIZE + CELL_SIZE / 2 - dotSize / 2,
                                    y * CELL_SIZE + CELL_SIZE / 2 - dotSize / 2,
                                    dotSize,
                                    dotSize
                            );
                        }
                    }
                }
            }

            // Pac-Man 
            g2.setColor(Color.decode("#F0CA77"));
            g2.fillOval(
                        pacman.getPosition().getX() * CELL_SIZE,
                        pacman.getPosition().getY() * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );

            // Duszki
            for (Ghost ghost : ghosts) {
                g2.setColor(ghost.getColor());
                g2.fillOval(
                        ghost.getPosition().getX() * CELL_SIZE,
                        ghost.getPosition().getY() * CELL_SIZE,
                        CELL_SIZE,
                        CELL_SIZE
                );
            }

            // Score
            g2.setColor(Color.WHITE);
            g2.drawString("Score: " + gameState.getScore(), 10, 15);
        }
    }
}
