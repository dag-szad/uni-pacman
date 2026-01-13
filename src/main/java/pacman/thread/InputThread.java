package pacman.thread;

import pacman.game.GameState;
import pacman.util.Direction;

import java.util.Scanner;

public class InputThread implements Runnable {

    private final GameState gameState;

    public InputThread(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run() {
        System.out.println("InputThread started");

        Scanner scanner = new Scanner(System.in);

        while (gameState.isGameRunning()) {
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "w" -> gameState.setPacmanDirection(Direction.UP);
                case "s" -> gameState.setPacmanDirection(Direction.DOWN);
                case "a" -> gameState.setPacmanDirection(Direction.LEFT);
                case "d" -> gameState.setPacmanDirection(Direction.RIGHT);
                case "q" -> gameState.stopGame();
            }
        }
    }
}
