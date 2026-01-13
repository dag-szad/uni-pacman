package pacman.entity;

import java.awt.Color;

public class Ghost {
    private Position position;
    private final Color color;

    public Ghost(int x, int y, Color color) {
        this.position = new Position(x, y);
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }
}
