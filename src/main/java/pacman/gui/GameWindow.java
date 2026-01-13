package pacman.gui;

import pacman.game.GameState;
import pacman.thread.GameLoop;
import pacman.thread.GhostAIThread;
import pacman.util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {

    private GamePanel gamePanel;
    private OverlayPanel overlayPanel;
    private final GameState gameState;
    private boolean paused = false;

    private Thread gameLoopThread;
    private Thread ghostThread;

    public boolean isPaused() {
        return paused;
    }

    public GameWindow(GameState gameState) {
        this.gameState = gameState;
        this.setTitle("Pac-Man Swing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        int width = 20 * GamePanel.CELL_SIZE;
        int height = 11 * GamePanel.CELL_SIZE;
        this.setPreferredSize(new Dimension(width + 20, height + 40));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        StartPanel startPanel = new StartPanel(e -> startGame());
        this.add(startPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    private void startGame() {
        System.out.println("[GameWindow] Game started");

        getContentPane().removeAll();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(20 * GamePanel.CELL_SIZE, 11 * GamePanel.CELL_SIZE));
        this.setContentPane(layeredPane);

        gamePanel = new GamePanel(gameState);
        gamePanel.setBounds(0, 0, gamePanel.getPreferredSize().width, gamePanel.getPreferredSize().height);
        gamePanel.setFocusable(true);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        overlayPanel = new OverlayPanel();
        overlayPanel.setBounds(0, 0, gamePanel.getPreferredSize().width, gamePanel.getPreferredSize().height);
        overlayPanel.setVisible(false);
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());

        gameState.startGame();

        gameLoopThread = new Thread(new GameLoop(gameState, this), "GameLoopThread");
        ghostThread = new Thread(new GhostAIThread(gameState, this), "GhostAIThread");
        gameLoopThread.start();
        ghostThread.start();

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameState.isGameRunning()) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        gameState.setPacmanDirection(Direction.UP);
                        System.out.println("[Input] Pac-Man direction set to UP");
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        gameState.setPacmanDirection(Direction.DOWN);
                        System.out.println("[Input] Pac-Man direction set to DOWN");
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        gameState.setPacmanDirection(Direction.LEFT);
                        System.out.println("[Input] Pac-Man direction set to LEFT");
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        gameState.setPacmanDirection(Direction.RIGHT);
                        System.out.println("[Input] Pac-Man direction set to RIGHT");
                    }
                    case KeyEvent.VK_P -> togglePause();
                    case KeyEvent.VK_Q -> showGameOver(gameState.getScore());
                }
            }
        });

        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void togglePause() {
        paused = !paused;
        overlayPanel.setMessage(paused ? "PAUZA" : "");
        overlayPanel.setVisible(paused);
        System.out.println(paused ? "[Game] Gra wstrzymana" : "[Game] Gra wznowiona");
    }

    public void showGameOver(int score) {
        overlayPanel.setMessage("GAME OVER!\nScore: " + score);
        overlayPanel.setVisible(true);
        overlayPanel.repaint();
        System.out.println("[GameWindow] Game Over! Score: " + score);
    }

    public void showWin(int score) {
        overlayPanel.setMessage("YOU WIN!\nScore: " + score);
        overlayPanel.setVisible(true);
        overlayPanel.repaint();
        System.out.println("[GameWindow] You Win! Score: " + score);
    }

    public void repaintGame() {
        if (gamePanel != null) {
            SwingUtilities.invokeLater(() -> gamePanel.repaint());
        }
    }
}
