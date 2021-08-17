package APP;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * ****** InternLink APP 2020 *********
 *
 * AIM:
 * ---------------------------------------------------------------------------------------------------------------------
 * Reduce time taken and complications involved in applying for internships by creating a platform where:
 * organizations can create an account, publish their internship offers, and get applicants
 * Students are able to view all available internships, and apply.
 *
 * TOOLS
 * ---------------------------------------------------------------------------------------------------------------------
 * XML files for UI design
 * JavaFx for Logic
 * MySQL database
 *
 *
 * DEVELOPED BY:
 * ---------------------------------------------------------------------------------------------------------------------
 * Arnaud Wingabire (Bobo Wab)
 * @see <a href="https://github.com/BoboWab99">Arnaud on GitHub!</a>
 *
 *
 * CONTRIBUTORS
 * ---------------------------------------------------------------------------------------------------------------------
 * Rose Komen
 * Peris Wambui
 * Nimi Natalie
 * Geogina Abom
 */

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image logo = new Image(Objects.requireNonNull(getClass().getResource("/RESOURCES/linklogo.png")).toExternalForm());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home.fxml")));
        Scene scene = new Scene(root);

        primaryStage.setTitle("InterLink | Home");
        primaryStage.getIcons().add(logo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
