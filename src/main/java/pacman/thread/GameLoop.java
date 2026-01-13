package pacman.thread;

import pacman.game.GameConfig;
import pacman.game.GameState;
import pacman.gui.GameWindow;

import javax.swing.SwingUtilities;

public class GameLoop implements Runnable {

    private static final int WIN_SCORE = 1300;

    private final GameState gameState;
    private final GameWindow gameWindow;

    public GameLoop(GameState gameState, GameWindow gameWindow) {
        this.gameState = gameState;
        this.gameWindow = gameWindow;
    }

    private void movePacman() {
        synchronized (gameState) {
            if (!gameState.isGameRunning() || gameWindow.isPaused()) return;

            var pacman = gameState.getPacman();
            var pos = pacman.getPosition();
            var dir = gameState.getPacmanDirection();
            var board = gameState.getGameBoard();

            int newX = pos.getX();
            int newY = pos.getY();

            switch (dir) {
                case UP -> newY--;
                case DOWN -> newY++;
                case LEFT -> newX--;
                case RIGHT -> newX++;
                default -> {
                    return;
                }
            }

            if (board.isWall(newX, newY)) return;

            pos.set(newX, newY);

            boolean scoreChanged = false;

            if (board.isSmallPoint(newX, newY)) {
                board.clearPoint(newX, newY);
                gameState.addScore(10);
                scoreChanged = true;
            } else if (board.isBigPoint(newX, newY)) {
                board.clearPoint(newX, newY);
                gameState.addScore(50);
                scoreChanged = true;
            }

            System.out.println("[GameLoop] Pac-Man moved to (" + newX + "," + newY + ")");

            if (scoreChanged && gameState.getScore() >= WIN_SCORE) {
                System.out.println("[GameLoop] All points collected – YOU WIN!");
                gameState.stopGame();
                SwingUtilities.invokeLater(() ->
                    gameWindow.showWin(gameState.getScore()));
            }
        }

        SwingUtilities.invokeLater(gameWindow::repaint);
    }

    private void checkCollisions() {
        synchronized (gameState) {
            if (!gameState.isGameRunning()) return;

            var pacmanPos = gameState.getPacman().getPosition();

            for (var ghost : gameState.getGhosts()) {
                var ghostPos = ghost.getPosition();
                if (pacmanPos.getX() == ghostPos.getX() &&
                    pacmanPos.getY() == ghostPos.getY()) {

                    System.out.println("[GameLoop] Collision! Pac-Man hit a ghost at (" +
                            pacmanPos.getX() + "," + pacmanPos.getY() + ")");
                    gameState.stopGame();
                    SwingUtilities.invokeLater(() ->
                            gameWindow.showGameOver(gameState.getScore()));
                    return;
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("[GameLoop] started");

        while (!Thread.currentThread().isInterrupted()) {
            if (gameState.isGameRunning() && !gameWindow.isPaused()) {
                movePacman();
                checkCollisions();
            }

            try {
                Thread.sleep(GameConfig.GAME_LOOP_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("[GameLoop] stopped");
    }
}
