package CONTROLLERS;

import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApplicantsController implements Initializable {
    @FXML
    private Label companyNameLabel;
    @FXML
    private ComboBox<String> internshipChoiceBox;
    @FXML
    private Accordion accordion;
    private static final String defaultInternshipChoice = "All";
    private final ArrayList<Integer> databaseInternshipIdList = new ArrayList<>();
    private boolean internshipChoiceBoxIsInitialized = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCompanyNameLabel();
        try {
            showAllApplicants();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void fillCompanyNameLabel() {
        companyNameLabel.setText(LoginController.companyName);
    }
    @FXML
    void goToAccount(ActionEvent event) throws IOException {
        Helpers.loadCompanyAccount(event);
    }
    @FXML
    void goToInternships(ActionEvent event) throws IOException {
        Helpers.loadCompanyInternshipOffers(event);
    }

    private void showAllApplicants() throws SQLException, IOException {
        final String query = "SELECT applicant.*, internship.internship_title FROM `applicant` INNER JOIN `internship` " +
                "ON applicant.internshipId = internship.internship_id WHERE internship.company_id = ? " +
                "ORDER BY `applicant`.`applicantId` ASC";

        final String distinctTitlesQuery = "SELECT DISTINCT internship.internship_id, internship.internship_title FROM `applicant` INNER JOIN `internship` " +
                "ON applicant.internshipId = internship.internship_id WHERE internship.company_id = ?";

        PreparedStatement prepStmt1 = Database.prepareNewStatement(query);
        PreparedStatement prepStmt2 = Database.prepareNewStatement(distinctTitlesQuery);
        assert prepStmt1 != null;
        assert prepStmt2 != null;
        prepStmt1.setInt(1, LoginController.companyId);
        prepStmt2.setInt(1, LoginController.companyId);
        ResultSet resultSet1 = Database.executeSelect(prepStmt1);
        ResultSet resultSet2 = Database.executeSelect(prepStmt2);

        assert resultSet2 != null;
        if(!internshipChoiceBoxIsInitialized) {
            internshipChoiceBox.getItems().add(defaultInternshipChoice);
            while (resultSet2.next()) {
                databaseInternshipIdList.add(resultSet2.getInt("internship_id"));
                internshipChoiceBox.getItems().add(resultSet2.getString("internship_title"));
            }
            internshipChoiceBox.getSelectionModel().selectFirst();
        }
        assert resultSet1 != null;
        while (resultSet1.next()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ApplicantDetailsBox.fxml"));
            TitledPane box = loader.load();
            ApplicantDetailsBoxController applicantDetailsBox = loader.getController();

            applicantDetailsBox.setApplicantId(resultSet1.getInt("applicantId"));
            applicantDetailsBox.getFullNameLabel1().setText(resultSet1.getString("fullName"));
            applicantDetailsBox.getFullNameLabel2().setText(resultSet1.getString("fullName"));
            applicantDetailsBox.getSchoolLabel1().setText(resultSet1.getString("institution"));
            applicantDetailsBox.getSchoolLabel2().setText(resultSet1.getString("institution"));

            applicantDetailsBox.getInternshipTitleLabel().setText(resultSet1.getString("internship_title"));
            applicantDetailsBox.getEmailLabel().setText(resultSet1.getString("emailAddress"));
            applicantDetailsBox.getPhoneNumberLabel().setText(resultSet1.getString("phoneNumber"));
            applicantDetailsBox.getDegreeLabel().setText(resultSet1.getString("degree"));
            applicantDetailsBox.getCvFileNameLabel().setText(resultSet1.getString("cvFileName"));

            accordion.getPanes().add(box);
        }
        internshipChoiceBoxIsInitialized = true;
    }

    @FXML
    void showSelectedApplicants(ActionEvent event) throws SQLException, IOException {
        /* remove all applicants */
        accordion.getPanes().remove(0, accordion.getPanes().size());

        int indexInChoiceBox = internshipChoiceBox.getItems().indexOf(internshipChoiceBox.getValue());

        if (indexInChoiceBox > 0) {
            final int internshipId = databaseInternshipIdList.get(indexInChoiceBox - 1);
            final String query = "SELECT applicant.*, internship.internship_title FROM `applicant` INNER JOIN `internship` " +
                    "ON applicant.internshipId = internship.internship_id WHERE internship.company_id = ? " +
                    "AND internship.internship_id = ? ORDER BY `applicant`.`applicantId` ASC";

            PreparedStatement prepStmt = Database.prepareNewStatement(query);
            assert prepStmt != null;
            prepStmt.setInt(1, LoginController.companyId);
            prepStmt.setInt(2, internshipId);
            ResultSet resultSet = Database.executeSelect(prepStmt);

            while (true) {
                assert resultSet != null;
                if (!resultSet.next()) break;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ApplicantDetailsBox.fxml"));
                TitledPane box = loader.load();
                ApplicantDetailsBoxController applicantDetailsBox = loader.getController();

                applicantDetailsBox.getFullNameLabel1().setText(resultSet.getString("fullName"));
                applicantDetailsBox.getFullNameLabel2().setText(resultSet.getString("fullName"));
                applicantDetailsBox.getSchoolLabel1().setText(resultSet.getString("institution"));
                applicantDetailsBox.getSchoolLabel2().setText(resultSet.getString("institution"));

                applicantDetailsBox.getInternshipTitleLabel().setText(resultSet.getString("internship_title"));
                applicantDetailsBox.getEmailLabel().setText(resultSet.getString("emailAddress"));
                applicantDetailsBox.getPhoneNumberLabel().setText(resultSet.getString("phoneNumber"));
                applicantDetailsBox.getDegreeLabel().setText(resultSet.getString("degree"));
                applicantDetailsBox.getCvFileNameLabel().setText(resultSet.getString("cvFileName"));

                accordion.getPanes().add(box);
            }
        }
        else {
            showAllApplicants();
        }
    }

    @FXML
    void removeAllApplicants(ActionEvent event) {
        // no logic yet
    }

}
