package com.edu.duongdua.fxdao.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmUploadController extends Controller
{
    private boolean isConfirm = false;

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    @FXML
    private Label nameLabel;

    public void setNameLabelText(String fileName) {
        this.nameLabel.setText("Xác nhận tải lên file " + fileName + " ?");
    }

    public void close(ActionEvent event){
        // phat event dong window
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void onActionConfirm(ActionEvent event)
    {
        isConfirm = true;
        close(event);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Upload thành công!");
        alert.show();
    }
    public void onActionCancel(ActionEvent event)
    {
        isConfirm = false;
        close(event);
    }
}
