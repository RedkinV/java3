package lesson4_mychat.client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

       @FXML
       private MenuBar menuBar;
        @FXML
        private MenuItem changeNickMenuBtn;

        @FXML
        private MenuItem registerMenuBtn;

        @FXML
        private HBox upperPanel;

        @FXML
        private TextField loginField;

        @FXML
        private PasswordField passwordField;

        @FXML
        private Button registerBtn;

        @FXML
        private TextArea registerTextArea;


        public void tryToRegister(ActionEvent actionEvent) {


            Stage currentStage=(Stage)upperPanel.getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("mychat.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root=loader.getRoot();
            Stage stage=new Stage();
            stage.setTitle("MyChat..");
            stage.setScene(new Scene(root, 450, 400));
            stage.showAndWait();

        }
    }
