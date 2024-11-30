package dat.nx.sanbongdaquoc.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage; // Stage chính của ứng dụng

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void changeScene(String fxmlFile) {
        try {
            Pane pane = FXMLLoader.load(SceneManager.class.getResource(fxmlFile));
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
