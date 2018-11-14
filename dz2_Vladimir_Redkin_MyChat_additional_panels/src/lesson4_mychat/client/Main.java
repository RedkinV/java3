package lesson4_mychat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mychat.fxml"));
        primaryStage.setTitle("MyChat");
        primaryStage.setScene(new Scene(root, 450, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
