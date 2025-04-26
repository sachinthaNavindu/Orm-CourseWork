package edu.ijse.therapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class ReceptionistDashController {
    @FXML
    private Label btnLogOut;
    @FXML
    private AnchorPane loadPageAnchor;

    @FXML
    void navAppoinments(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/therapySession.fxml")));
    }

    @FXML
    void navBilling(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/payment.fxml")));
    }

    @FXML
    void navDash(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashBoard.fxml")));
    }

    @FXML
    void navPatitnt(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/patient.fxml")));
    }

    @FXML
    void navReports(ActionEvent event) {

    }
    @FXML
    void clickLogOut(MouseEvent event) {

    }
}
