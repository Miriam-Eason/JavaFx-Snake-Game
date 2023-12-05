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
}
