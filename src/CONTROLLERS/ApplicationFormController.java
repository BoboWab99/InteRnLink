package CONTROLLERS;

import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationFormController {
    @FXML
    private TextField fullNameTf;
    @FXML
    private TextField emailTf;
    @FXML
    private TextField phoneNumberTf;
    @FXML
    private TextField institutionTf;
    @FXML
    private TextField degreeTf;
    @FXML
    private Label chosenFileLabel;
    @FXML
    private Label msg;
    private File uploadedCV;

    @FXML
    void backToInternships(ActionEvent event) throws IOException {
        Helpers.loadAvailableInternships(event);
    }

    @FXML
    void submitApplication(ActionEvent event) throws SQLException, IOException {
        if (!userInputIsValid()) { return; }
        final String query = "INSERT INTO applicant(internshipId, fullName, emailAddress, phoneNumber, institution, degree, cvFileName) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.prepareStatementAndReturnKey(query);
        assert prepStmt != null;
        prepStmt.setInt(1, InternshipBoxController.internshipId);
        prepStmt.setString(2, fullNameTf.getText());
        prepStmt.setString(3, emailTf.getText());
        prepStmt.setString(4, phoneNumberTf.getText());
        prepStmt.setString(5, institutionTf.getText());
        prepStmt.setString(6, degreeTf.getText());
        prepStmt.setString(7, uploadedCV.getName());

        Database.executeNewUpdate(prepStmt);
        ResultSet resultSet = prepStmt.getGeneratedKeys();
        int applicantId = 0;
        if (resultSet.next()) {
            applicantId = resultSet.getInt(1);
        }
        final String fileName = applicantId + ".docx";
        File cvCopy = new File(Helpers.fileUploadsFolder + fileName);
        if (uploadedCV != null) {
            Files.copy(uploadedCV.toPath(), cvCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        prepStmt.close();
        Helpers.loadAvailableInternships(event);
    }

    @FXML
    void getNewFileUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(Helpers.wordDocExtensionFilter());
        File cvUpload = fileChooser.showOpenDialog(null);
        if (cvUpload != null) {
            chosenFileLabel.setText("Selected: " + cvUpload.getName());
            uploadedCV = cvUpload;
        }
    }

    private boolean userInputIsValid() {
        if (fullNameTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid names!");
            return false;
        }
        if (emailTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid email address!");
            return false;
        }
        if (phoneNumberTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid phone number!");
            return false;
        }
        if (institutionTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid institution name!");
            return false;
        }
        if (degreeTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Degree field should not be empty!");
            return false;
        }
        if (uploadedCV == null) {
            Helpers.showEmptyInputMsg(msg, "Attach your CV!");
            return false;
        }
        return true;
    }

}

