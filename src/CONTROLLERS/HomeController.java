package CONTROLLERS;

import javafx.fxml.FXML;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import java.io.IOException;

public class HomeController {

    public void goToSignUp(ActionEvent event) throws IOException {
        Helpers.changeStage(event, "/FXML/SignUp.fxml", "InternLink | Sign Up");
    }
    public void goToInternships(ActionEvent event) throws IOException {
        Helpers.loadAvailableInternships(event);
    }
    public void goToLogin(ActionEvent event) throws IOException {
        Helpers.changeStage(event, "/FXML/Login.fxml", "InternLink | Login");
    }
    @FXML
    void goToSupportPage(ActionEvent event) throws IOException {
        Helpers.loadSupportPage(event);
    }

}
