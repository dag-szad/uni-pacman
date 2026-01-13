package pacman.game;

import pacman.board.GameBoard;
import pacman.entity.Ghost;
import pacman.entity.Pacman;
import pacman.entity.Position;
import pacman.util.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;

public class GameState {

    private final GameBoard gameBoard;

    private volatile boolean gameRunning = false;
    private volatile boolean gameStarted = false;

    private Pacman pacman;
    private List<Ghost> ghosts;
    private Direction pacmanDirection = Direction.NONE;

    private int score = 0;
    private final Random random = new Random();

    public Position getRandomEmptyPosition() {
        int x, y;
        int attempts = 0;
        do {
            x = random.nextInt(gameBoard.getWidth());
            y = random.nextInt(gameBoard.getHeight());
            attempts++;
            if (attempts > 100) break;
        } while (gameBoard.isWall(x, y));
        return new Position(x, y);
    }    

    public GameState() {
        this.gameBoard = new GameBoard();
        this.pacman = new Pacman(1, 1);
        this.ghosts = new ArrayList<>();

        ghosts.add(new Ghost(getRandomEmptyPosition().getX(),
                             getRandomEmptyPosition().getY(),
                             Color.decode("#61FFBF")));

        ghosts.add(new Ghost(getRandomEmptyPosition().getX(),
                             getRandomEmptyPosition().getY(),
                             Color.decode("#F694FF")));
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }
    
    public boolean isGameStarted() {
        return gameStarted;
    }
    
    public void startGame() {
        gameRunning = true;
        gameStarted = true;
    }
    
    public void stopGame() {
        gameRunning = false;
        gameStarted = false;
    }

    public synchronized Pacman getPacman() {
        return pacman;
    }

    public synchronized List<Ghost> getGhosts() {
        return ghosts;
    }

    public synchronized Direction getPacmanDirection() {
        return pacmanDirection;
    }

    public synchronized void setPacmanDirection(Direction direction) {
        this.pacmanDirection = direction;
    }

    public synchronized int getScore() {
        return score;
    }

    public synchronized void addScore(int points) {
        score += points;
    }
}
