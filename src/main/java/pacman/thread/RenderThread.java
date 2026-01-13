package pacman.thread;

import pacman.game.GameState;
import pacman.entity.Ghost;
import pacman.game.GameConfig;

public class RenderThread implements Runnable {

    private final GameState gameState;

    public RenderThread(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run() {
        System.out.println("RenderThread started");

        while (gameState.isGameRunning()) {
            synchronized (gameState) {
                render();
            }

            try {
                Thread.sleep(GameConfig.RENDER_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void render() {
    var board = gameState.getGameBoard();
    var pacman = gameState.getPacman();
    var ghosts = gameState.getGhosts();

    char[][] view = board.getBoard();

    for (int y = 0; y < board.getHeight(); y++) {
        for (int x = 0; x < board.getWidth(); x++) {

            boolean drawn = false;

            // Pac-Man
            if (pacman.getPosition().getX() == x &&
                pacman.getPosition().getY() == y) {
                System.out.print('P');
                drawn = true;
            }

            // Duszki
            for (Ghost ghost : ghosts) {
                if (ghost.getPosition().getX() == x &&
                    ghost.getPosition().getY() == y) {
                    System.out.print('G');
                    drawn = true;
                    break;
                }
            }

            // Pole planszy
            if (!drawn) {
                System.out.print(view[y][x]);
            }
        }
        System.out.println();
    }

    System.out.println("Score: " + gameState.getScore());
    System.out.println("------------------------");
}

}
