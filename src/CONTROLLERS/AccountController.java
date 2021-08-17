package CONTROLLERS;

import APP.Company;
import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountController implements  Initializable {
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField nameTf;
    @FXML
    private TextField emailTf;
    @FXML
    private TextField phoneNumberTf;
    @FXML
    private TextField websiteTf;
    @FXML
    private TextField passwordTf;
    @FXML
    private Label msg;
    @FXML
    private ImageView imageView;
    @FXML
    private Label imgChosenLabel;

    private File uploadedImage;
    private static final Company company = new Company();
    private final int companyId = LoginController.companyId;
    private final int imgWidth = 400;
    private final int imgHeight = 200;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillCompanyDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillCompanyDetails() throws SQLException {
        final String query = "SELECT * FROM company_account WHERE id = ?";
        PreparedStatement newPrepStmt =  Database.prepareNewStatement(query);

        assert newPrepStmt != null;
        newPrepStmt.setInt(1, companyId);

        ResultSet resultSet =  Database.executeSelect(newPrepStmt);
        assert resultSet != null;
        if (resultSet.next()) {
            company.setName(resultSet.getString("name"));
            company.setEmail(resultSet.getString("email"));
            company.setPhoneNumber(resultSet.getString("phone"));
            company.setOfficialWebsite(resultSet.getString("website"));
            company.setPassword(resultSet.getString("pass"));
        }
        final String imgPath = Helpers.FILE_UPLOADS + companyId + ".png";
        Image image = new Image(imgPath, imgWidth, imgHeight, false, true);
        if (image.isError()) {
            image = new Image(Helpers.placeholderImgPath, imgWidth, imgHeight, false, true);
        }
        imageView.setImage(image);

        companyNameLabel.setText(company.getName());
        nameTf.setText(company.getName());
        emailTf.setText(company.getEmail());
        phoneNumberTf.setText(company.getPhoneNumber());
        websiteTf.setText(company.getOfficialWebsite());
        passwordTf.setText(company.getPassword());
        newPrepStmt.close();
    }

    @FXML
    void saveChanges() throws SQLException, IOException {
        if (!userInputIsValid()) { return; }
        final String query = "UPDATE `company_account` SET name = ?, email = ?, phone = ?, website = ?, pass = ? WHERE id = ?";
        PreparedStatement prepStmt =  Database.prepareNewStatement(query);

        assert prepStmt != null;
        prepStmt.setString(1, nameTf.getText());
        prepStmt.setString(2, emailTf.getText());
        prepStmt.setString(3, phoneNumberTf.getText());
        prepStmt.setString(4, websiteTf.getText());
        prepStmt.setString(5, passwordTf.getText());
        prepStmt.setInt(6, companyId);

        if (companyId > 0) {
            final String imgName = companyId + ".png";
            File imgCopy = new File(Helpers.fileUploadsFolder + imgName);
            Files.copy(uploadedImage.toPath(), imgCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        Database.executeNewUpdate(prepStmt);
        fillCompanyDetails();
        prepStmt.close();

        msg.setText("Successfully updated!");
        msg.setVisible(true);
        Helpers.setTimeout(() -> msg.setVisible(false), 5000);
    }

    @FXML
    void changeImage() {
        File img = Helpers.getUploadedImage();
        if (img == null) return;

        imgChosenLabel.setText("Selected: " + img.getName());
        imageView.setImage(new Image(img.toURI().toString(), imgWidth, imgHeight, false, true));
        uploadedImage = img;
    }

    private boolean userInputIsValid() {
        if (nameTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter company name!");
            return false;
        }
        if (emailTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid email address!");
            msg.setVisible(true);
            return false;
        }
        if (phoneNumberTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid phone number!");
            msg.setVisible(true);
            return false;
        }
        if (websiteTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Enter valid website link!");
            msg.setVisible(true);
            return false;
        }
        if (passwordTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Password should not be empty!");
            msg.setVisible(true);
            return false;
        }
        if (uploadedImage == null) {
            Helpers.showEmptyInputMsg(msg, "Add an image!");
            msg.setVisible(true);
            return false;
        }
        return true;
    }

    @FXML
    void goToApplicants(ActionEvent event) throws IOException {
        Helpers.loadApplicants(event);
    }
    @FXML
    void goToInternships(ActionEvent event) throws IOException {
        Helpers.loadCompanyInternshipOffers(event);
    }
    @FXML
    void goBackHome(ActionEvent event) throws IOException {
        Helpers.loadHomePage(event);
    }

}
