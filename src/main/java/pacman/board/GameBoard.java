package pacman.board;

public class GameBoard {

    private final char[][] board;

    public GameBoard() {
        board = new char[][]{
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '*', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '*', '#'},
                {'#', '.', '#', '#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '.', '#'},
                {'#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#'},
                {'#', '.', '#', '.', '#', '#', '.', '#', '#', '.', '.', '#', '#', '.', '#', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '#', '*', '.', '.', '*', '#', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '.', '#'},
                {'#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#'},
                {'#', '.', '#', '#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '.', '#'},
                {'#', '*', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '*', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }

    public char getField(int x, int y) {
        return board[y][x];
    }

    public boolean isWall(int x, int y) {
        return board[y][x] == '#';
    }

    public boolean isSmallPoint(int x, int y) {
        return board[y][x] == '.';
    }

    public boolean isBigPoint(int x, int y) {
        return board[y][x] == '*';
    }

    public boolean isPoint(int x, int y) {
        return isSmallPoint(x, y) || isBigPoint(x, y);
    }

    public void clearPoint(int x, int y) {
        board[y][x] = ' ';
    }

    public char[][] getBoard() {
        return board;
    }
}
