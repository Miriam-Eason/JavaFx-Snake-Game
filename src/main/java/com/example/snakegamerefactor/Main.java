package com.example.snakegamerefactor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Game Windows
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;
    private static final int SIZE = 20;
    private Stage stage;
    private Scene startScene;
    private Scene gameScene;
    private Scene rankScene;


    // Direction
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Graph


    @Override
    public void start(Stage stage) throws IOException {
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

        // gameScene
        Canvas canvas = new Canvas(WIDTH * SIZE, HEIGHT * SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnchorPane gameRoot = new AnchorPane(canvas);
        Scene gameScene = new Scene(gameRoot);
        this.gameScene = gameScene;

        // RankingScene



        stage.setTitle("Snake Game");
        stage.setScene(startScene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

    public void switchToGameScene() {
        stage.setScene(gameScene);
    }
}