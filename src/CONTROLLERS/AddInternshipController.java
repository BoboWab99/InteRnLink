package CONTROLLERS;


import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddInternshipController implements Initializable {
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField companyNameTf;
    @FXML
    private TextField internshipTitleTf;
    @FXML
    private TextArea companyDescriptionTf;
    @FXML
    private TextArea internshipDescriptionTf;
    @FXML
    private TextField requirementsTf;
    @FXML
    private TextField deadlineTf;
    @FXML
    private TextField managerTf;
    @FXML
    private Label msg;
    private final String companyName = LoginController.companyName;
    private final int companyId = LoginController.companyId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCompanyName();
    }

    private void fillCompanyName() {
        companyNameLabel.setText(companyName);
        companyNameTf.setText(companyName);
    }

    public void addNewInternshipOffer(ActionEvent event) throws SQLException, IOException {
        if (!userInputIsValid()) { return; }
        final String query = "INSERT INTO internship(company_id, internship_title, short_company_description, internship_description, requirements, application_deadline, manager) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);
        assert prepStmt != null;
        prepStmt.setInt(1, companyId);
        prepStmt.setString(2, internshipTitleTf.getText());
        prepStmt.setString(3, companyDescriptionTf.getText());
        prepStmt.setString(4, internshipDescriptionTf.getText());
        prepStmt.setString(5, requirementsTf.getText());
        prepStmt.setString(6, deadlineTf.getText());
        prepStmt.setString(7, managerTf.getText());

        Database.executeNewUpdate(prepStmt);
        prepStmt.close();
        Helpers.changeStage(event, "/FXML/InternshipOffers.fxml", "InternLink | Internships");
    }

    public boolean userInputIsValid() {
        if (internshipTitleTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Plz, enter the internship title!");
            return false;
        }
        if (internshipDescriptionTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Must provide the internship description!");
            return false;
        }
        if (requirementsTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Must provide the internship requirements!");
            return false;
        }
        if (deadlineTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Must provide the application deadline!");
            return false;
        }
        if (managerTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Must provide the internship applications receiver!");
            return false;
        }
        return true;
    }

    @FXML
    void goToAccount(ActionEvent event) throws IOException {
        Helpers.loadCompanyAccount(event);
    }
    @FXML
    void goToApplicants(ActionEvent event) throws IOException {
        Helpers.loadApplicants(event);
    }
    @FXML
    void goBackToInternships(ActionEvent event) throws IOException {
        Helpers.loadCompanyInternshipOffers(event);
    }

}
