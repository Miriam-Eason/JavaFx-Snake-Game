package com.example.snakegamerefactor;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Game Windows
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;
    private static final int SIZE = 20;
    private static final int SPEED = 5;

    // Canvas and GraphicsContext
    private static Canvas canvas = new Canvas(WIDTH * SIZE, HEIGHT * SIZE);
    private static Canvas canvas2 = new Canvas(WIDTH * SIZE, HEIGHT * SIZE);
    private static GraphicsContext gc = canvas.getGraphicsContext2D();

    // Stage and Scene
    private Stage stage;
    private Scene startScene;
    private Scene gameScene;
    private Scene twoPlayerScene;
    private Scene rankScene;

    // Food position
    private Point food;

    // Snake Players
    private Snake player1 = new Snake();
    private Snake player2 = new Snake();

    // Is End
    private boolean gameOver = false;

    // Is Paused
    private boolean gamePaused = false;



    // Graph


    @Override
    public void start(Stage stage) throws Exception {
//        Parent startRoot = FXMLLoader.load(getClass().getResource("src/main/resources/com/example/snakegamerefactor/gameStart.fxml"));

        // init
        this.stage = stage;
        // startScene
        FXMLLoader startRoot = new FXMLLoader(Main.class.getResource("gameStart.fxml"));
        Scene startScene = new Scene(startRoot.load());
        this.startScene = startScene;

        // pass MainToController
        StartController controller = startRoot.getController();
        controller.setMainApp(this);

        // gameScene (One Player)
        AnchorPane gameRoot = new AnchorPane(canvas);
        Scene gameScene = new Scene(gameRoot);
        this.gameScene = gameScene;
        gameScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    if (player1.getDirection() != Direction.DOWN) {
                        player1.setDirection(Direction.UP);
                    }
                    break;
                case DOWN:
                    if (player1.getDirection() != Direction.UP) {
                        player1.setDirection(Direction.DOWN);
                    }
                    break;
                case LEFT:
                    if (player1.getDirection() != Direction.RIGHT) {
                        player1.setDirection(Direction.LEFT);
                    }
                    break;
                case RIGHT:
                    if (player1.getDirection() != Direction.LEFT) {
                        player1.setDirection(Direction.RIGHT);
                    }
                    break;
                case P:
                    gamePaused = !gamePaused;
                    break;
                case R:
                    initSingleGame();
                    break;
                default:
                    break;
            }
        });

        initSingleGame();
        updateSingleGame();

        // TwoPlayerGame Mode
        AnchorPane twoPlayersMode = new AnchorPane(canvas2);
        Scene twoPlayersScene = new Scene(twoPlayersMode);
        this.twoPlayerScene = twoPlayersScene;
        twoPlayersScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    if (player1.getDirection() != Direction.DOWN) {
                        player1.setDirection(Direction.UP);
                    }
                    break;
                case DOWN:
                    if (player1.getDirection() != Direction.UP) {
                        player1.setDirection(Direction.DOWN);
                    }
                    break;
                case LEFT:
                    if (player1.getDirection() != Direction.RIGHT) {
                        player1.setDirection(Direction.LEFT);
                    }
                    break;
                case RIGHT:
                    if (player1.getDirection() != Direction.LEFT) {
                        player1.setDirection(Direction.RIGHT);
                    }
                    break;
                case W:
                    if (player2.getDirection() != Direction.S) {
                        player2.setDirection(Direction.W);
                    }
                    break;
                case S:
                    if (player2.getDirection() != Direction.W) {
                        player2.setDirection(Direction.S);
                    }
                    break;
                case A:
                    if (player2.getDirection() != Direction.D) {
                        player2.setDirection(Direction.A);
                    }
                    break;
                case D:
                    if (player2.getDirection() != Direction.A) {
                        player2.setDirection(Direction.D);
                    }
                    break;
                case P:
                    gamePaused = !gamePaused;
                    break;
                case R:
//                    initSingleGame();
                    break;
                default:
                    break;
            }
        });

        // RankingScene



        stage.setTitle("Snake Game");
        stage.setScene(startScene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

    // change stage scene
    public void switchToGameScene() {
        stage.setScene(gameScene);
    }

    public void switchToTwoPlayersScene() {
        stage.setScene(twoPlayerScene);
    }

    private void initSingleGame() {
        // clear snake body length
        player1.getSnake().clear();

        // init start position
        int x = WIDTH / 2;
        int y = HEIGHT / 2;
        player1.getSnake().add(new Point(x, y));
        player1.setDirection(Direction.RIGHT);

        // init food
        generateFood();
        // init game state
        gameOver = false;
        gamePaused = false;
    }

    private void movePlayer1() {
        Point head = player1.getSnake().getFirst();
        Point newHead = null;

        switch(player1.getDirection()) {
            case UP:
                newHead = new Point(head.getX(), head.getY() - 1);
                break;
            case DOWN:
                newHead = new Point(head.getX(), head.getY() + 1);
                break;
            case LEFT:
                newHead = new Point(head.getX() - 1, head.getY());
                break;
            case RIGHT:
                newHead = new Point(head.getX() + 1, head.getY());
                break;
            default:
                break;
        }

        // if crash snake body
        if (player1.getSnake().contains(newHead)) {
            gameOver = true;
            showSingleGameOverDialog();
            return;
        }

        // if crash the border
        if (newHead.getX() < 0 || newHead.getX() >= WIDTH ||
                newHead.getY() < 0 || newHead.getY() >= HEIGHT) {
            gameOver = true;
            showSingleGameOverDialog();
            return;
        }

        // Update Snake position
        player1.getSnake().addFirst(newHead);

        // If eat food
        if (newHead.equals(food)) {
            // if ate, increase the snake's body length
            generateFood();
        } else {
            // remove the last body element
            player1.getSnake().removeLast();
        }

    }

    private void movePlayer2() {
        Point head = player2.getSnake().getFirst();
        Point newHead = null;

        switch(player2.getDirection()) {
            case W:
                newHead = new Point(head.getX(), head.getY() - 1);
                break;
            case S:
                newHead = new Point(head.getX(), head.getY() + 1);
                break;
            case A:
                newHead = new Point(head.getX() - 1, head.getY());
                break;
            case D:
                newHead = new Point(head.getX() + 1, head.getY());
                break;
            default:
                break;
        }

        // if crash snake body
        if (player2.getSnake().contains(newHead)) {
            gameOver = true;
            showSingleGameOverDialog();
            return;
        }

        // if crash the border
        if (newHead.getX() < 0 || newHead.getX() >= WIDTH ||
                newHead.getY() < 0 || newHead.getY() >= HEIGHT) {
            gameOver = true;
            showTwoPlayersGameOverDialog();
            return;
        }

        // Update Snake position
        player2.getSnake().addFirst(newHead);

        // If eat food
        if (newHead.equals(food)) {
            // if ate, increase the snake's body length
            generateFood();
        } else {
            // remove the last body element
            player2.getSnake().removeLast();
        }

    }

    private void showSingleGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Game Over, Your Score: " + (player1.getSnake().size() - 1));
        alert.show();

        alert.setOnHidden(event -> {
            System.out.println("close the end game button");
            // re-start Game
            initSingleGame();
        });
    }

    private void showTwoPlayersGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Game Over");
        alert.show();

//        alert.setOnHidden(event -> {
//            System.out.println("close the end game button");
//            // re-start Game
//            initSingleGame();
//        });
    }

    // Create Food
    private void generateFood(){
        boolean validPosition;
        int x, y;

        do {
            validPosition = true;
            x = (int)(Math.random() * WIDTH);
            y = (int)(Math.random() * HEIGHT);

            for (Point point : player1.getSnake()) {
                if (point.getX() == x && point.getY() == y) {
                    validPosition = false;
                    break;
                }
            }

            for (Point point : player2.getSnake()) {
                if (point.getX() == x && point.getY() == y) {
                    validPosition = false;
                    break;
                }
            }
        } while(!validPosition);
        food = new Point(x, y);
    }

    private void paint() {
        // clean snakeBody
        gc.clearRect(0, 0, WIDTH * SIZE, HEIGHT * SIZE);

        // draw player1 snakeBody
        gc.setFill(Color.GREEN);
        for (Point point : player1.getSnake()) {
            gc.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
        }

        // draw player1 snakeHead
        gc.setFill(Color.DARKGREEN);
        Point player1Head = player1.getSnake().getFirst();
        gc.fillRect(player1Head.getX() * SIZE, player1Head.getY() * SIZE, SIZE, SIZE);

//        // draw player2 snakeBody
//        gc.setFill(Color.BLUE);
//        for (Point point : player2.getSnake()) {
//            gc.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
//        }
//
//        // draw player2 snakeHead
//        gc.setFill(Color.DARKBLUE);
//        Point player2Head = player2.getSnake().getFirst();
//        gc.fillRect(player2Head.getX() * SIZE, player2Head.getY() * SIZE, SIZE, SIZE);

        // draw food
        gc.setFill(Color.RED);
//        System.out.printf("food: [%d, %d]", food.getX(), food.getY());
        gc.fillOval(food.getX() * SIZE, food.getY() * SIZE, SIZE, SIZE);
    }

    private void updateSingleGame() {
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdateTime;
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= 1_000_000_000 / SPEED) {
                    lastUpdateTime = now;
                    if (!gameOver && !gamePaused) {
                        movePlayer1();
                        paint();
                    }
                }
            }
        };

        animationTimer.start();
    }
}