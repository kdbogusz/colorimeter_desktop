package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private Stage primaryStage;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Test");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TestController.class
                    .getResource("../view/TestWindow.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();

            // set initial data into controller
            TestController controller = loader.getController();
            controller.setAppController(this);

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }

}
