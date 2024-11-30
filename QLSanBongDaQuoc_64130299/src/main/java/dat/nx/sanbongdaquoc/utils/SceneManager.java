package dat.nx.sanbongdaquoc.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage; // Stage chính của ứng dụng

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void changeScene(String fxmlFile) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(SceneManager.class.getResource(fxmlFile));
            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
