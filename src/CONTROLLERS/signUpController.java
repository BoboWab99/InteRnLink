package CONTROLLERS;

import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signUpController {
    @FXML
    private TextField name_tf;
    @FXML
    private TextField email_tf;
    @FXML
    private TextField phone_number_tf;
    @FXML
    private TextField website_tf;
    @FXML
    private PasswordField password_tf;
    @FXML
    private Label imageChosenLabel;
    @FXML
    private Label msg;
    private File uploadedImage;

    public void signUp(ActionEvent event) throws IOException, SQLException {
        if (!userInputIsValid()) { return; }
        final String query = "INSERT INTO company_account(name, email, phone, website, pass) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement prepStmt =  Database.prepareStatementAndReturnKey(query);

        assert prepStmt != null;
        prepStmt.setString(1, name_tf.getText());
        prepStmt.setString(2, email_tf.getText());
        prepStmt.setString(3, phone_number_tf.getText());
        prepStmt.setString(4, website_tf.getText());
        prepStmt.setString(5, password_tf.getText());

        Database.executeNewUpdate(prepStmt);
        ResultSet resultSet = prepStmt.getGeneratedKeys();
        if (resultSet.next()) {
            final int companyId = resultSet.getInt(1);
            final String imgName = companyId + ".png";
            File imgCopy = new File(Helpers.fileUploadsFolder + imgName);
            Files.copy(uploadedImage.toPath(), imgCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        prepStmt.close();
        Helpers.changeStage(event, "/FXML/Login.fxml", "InternLink | Login");
    }

    @FXML
    void getImageUpload() {
        File img = Helpers.getUploadedImage();
        if (img == null) return;
        imageChosenLabel.setText("Selected: " + img.getName());
        uploadedImage = img;
    }

    private boolean userInputIsValid() {
        if (name_tf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter company name!");
            return false;
        }
        if (email_tf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid email address!");
            return false;
        }
        if (phone_number_tf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid phone number!");
            return false;
        }
        if (website_tf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid website link!");
            return false;
        }
        if (password_tf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "A password is needed!");
            return false;
        }
        if (uploadedImage == null) {
            Helpers.showEmptyInputMsg(msg, "Add an image!");
            return false;
        }
        return true;
    }

    public void backHome(ActionEvent event) throws IOException {
        Helpers.loadHomePage(event);
    }

}
