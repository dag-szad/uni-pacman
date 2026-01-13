package pacman.thread;

import pacman.game.GameState;
import pacman.game.GameConfig;
import pacman.entity.Ghost;
import pacman.entity.Position;
import pacman.util.Direction;
import pacman.gui.GameWindow;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class GhostAIThread implements Runnable {

    private final GameState gameState;
    private final GameWindow gameWindow;
    private final Random random = new Random();

    public GhostAIThread(GameState gameState, GameWindow gameWindow) {
        this.gameState = gameState;
        this.gameWindow = gameWindow;
    }

    @Override
    public void run() {
        System.out.println("[GhostAIThread] started");

        while (!Thread.currentThread().isInterrupted()) {
            if (gameState.isGameRunning() && !gameWindow.isPaused()) {
                moveGhosts();
            }
            try {
                Thread.sleep(GameConfig.GHOST_AI_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("[GhostAIThread] stopped");
    }

    private void moveGhosts() {
        boolean moved = false;

        synchronized (gameState) {
            List<Ghost> ghosts = gameState.getGhosts();

            for (Ghost ghost : ghosts) {
                Position pos = ghost.getPosition();
                int oldX = pos.getX();
                int oldY = pos.getY();

                Direction dir = Direction.values()[random.nextInt(4)];
                int newX = oldX;
                int newY = oldY;

                switch (dir) {
                    case UP -> newY--;
                    case DOWN -> newY++;
                    case LEFT -> newX--;
                    case RIGHT -> newX++;
                    case NONE -> {}
                }

                if (!gameState.getGameBoard().isWall(newX, newY)) {
                    pos.set(newX, newY);
                    moved = true;
                    System.out.println("[GhostAIThread] Ghost moved to (" + newX + "," + newY + ") in direction " + dir);
                }
            }
        }

        if (moved) {
            SwingUtilities.invokeLater(() -> gameWindow.repaint());
        }
    }
}
