package com.example.snakegamerefactor;

import java.util.ArrayDeque;
import java.util.Deque;

public class Snake {
    private Deque<Point> snake = new ArrayDeque<>();
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Deque<Point> getSnake() {
        return snake;
    }

    public void setSnake(Deque<Point> snake) {
        this.snake = snake;
    }

    public boolean isCollision(Snake otherSnake) {
        if (otherSnake == null) {
            return false;
        }

        Point head = this.getSnake().getFirst(); // get self Head
        for (Point point : otherSnake.getSnake()) {
            if (head.equals(point)) {
                return true; // if crash return true
            }
        }

        return false; // else return false
    }
}
