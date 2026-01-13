package pacman;

import pacman.game.GameState;
import pacman.thread.GameLoop;
import pacman.thread.GhostAIThread;
import pacman.gui.GameWindow;

public class Main {
    public static void main(String[] args) {

        System.out.println("Pac-Man Game starting...");

        GameState gameState = new GameState();
        GameWindow gameWindow = new GameWindow(gameState);

        Thread gameLoopThread = new Thread(new GameLoop(gameState, gameWindow), "GameLoopThread");
        Thread ghostThread = new Thread(new GhostAIThread(gameState, gameWindow), "GhostAIThread");

        gameLoopThread.start();
        ghostThread.start();
    }
}
