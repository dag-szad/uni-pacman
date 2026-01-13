package pacman.entity;

public class Pacman {

    private final Position position;

    public Pacman(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }
}
