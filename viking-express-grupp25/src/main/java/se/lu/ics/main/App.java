package se.lu.ics.main;

import javafx.scene.image.Image;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Get the location of the MainView.fxml file
        // The location is relative to the root of the classpath (target/classes)
        URL location = getClass().getResource("/fxml/MainView.fxml");
        // Create a new FXMLLoader with the location of the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        // Load the FXML file to create the UI layout as defined in MainView.fxml
        AnchorPane root = fxmlLoader.load();
        // Get the controller associated with the FXML file
       
        // Create a scene with the root node and set it on the primary stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // add icon viking-express-icon.png
        Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setTitle("Viking Express");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
}