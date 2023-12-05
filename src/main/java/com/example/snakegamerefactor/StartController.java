package com.example.snakegamerefactor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private Button rankView;

    @FXML
    private Button singleMode;

    @FXML
    private Button twoMode;

    @FXML
    void initSingleMode(ActionEvent event) {
        mainApp.switchToGameScene();
    }

    @FXML
    void initTwoMode(ActionEvent event) {
        System.out.println("click twoMode");
        mainApp.switchToTwoPlayersScene();
    }

    @FXML
    void openRankView(ActionEvent event) {
        System.out.println("click ranking board");
    }

}
