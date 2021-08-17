package CONTROLLERS;

import SERVICES.Database;
import SERVICES.Helpers;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupportController {
    @FXML
    private Text app_summary_txt;
    @FXML
    private TextField person_email;
    @FXML
    private TextField person_contact_number;
    @FXML
    private TextArea person_message;
    @FXML
    private ImageView imageView;
    @FXML
    private Text app_info;
    @FXML
    private Text company_name_txt;
    @FXML
    private Label msg;
    @FXML
    void initialize() throws SQLException {
        fillCompanyDetails();
    }

    private void fillCompanyDetails() throws SQLException {
        final String query = "SELECT * FROM app_info";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);
        ResultSet result = Database.executeSelect(prepStmt);
        assert result != null;
        if (result.next()) {
            final String appInfo = "App version: InternLink v" + result.getString("app_version");
            final String companyName = "Developed by: " + result.getString("company_name") + "\nNairobi, Kenya";
            final String appSummary = "This application offers an opportunity for the interns coming from the University " +
                    "to apply internship. Millions of interns have been able to benefit from this app. Why internLink? " +
                    "It offers convenience and saves a lot of time compared to the old ways of doing it.";
            app_info.setText(appInfo);
            company_name_txt.setText(companyName);
            app_summary_txt.setText(appSummary);

            final String imgPath = "file:///C:/Users/arnau/IdeaProjects/internlink/src/RESOURCES/linklogo.png";
            Image image = new Image(imgPath, 210, 120, false, false);
            imageView.setImage(image);
        }
    }

    @FXML
    void submitFeedback() {
        if (!userInputIsValid()) return;
        final String someoneEmail = person_email.getText();
        final String someonePhoneNumber = person_contact_number.getText();
        final String someoneMsg = person_message.getText();

        System.out.println("***** GOT SOME FEEDBACK ******\n");
        System.out.println("Email: " + someoneEmail);
        System.out.println("Contact Number: " + someonePhoneNumber);
        System.out.println("Message: " + someoneMsg);
    }

    private boolean userInputIsValid() {
        if (person_email.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid email address!");
            return false;
        }
        if (person_contact_number.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid phone number!");
            return false;
        }
        if (person_message.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter your message, please!");
            return false;
        }
        return true;
    }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Helpers.loadHomePage(event);
    }


}
