package CONTROLLERS;

import APP.InternshipOffer;
import SERVICES.Database;
import SERVICES.Helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InternshipOffersController implements Initializable {
    @FXML
    private Label companyNameLabel;
    @FXML
    private TableView<InternshipOffer> internshipsTable;
    @FXML
    private TableColumn<InternshipOffer, String> titleColumn;
    @FXML
    private TableColumn<InternshipOffer, String> managerColumn;
    @FXML
    private TableColumn<InternshipOffer, String> deadlineColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showCompanyInternshipOffers();
            fillCompanyNameLabel();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fillCompanyNameLabel() {
        companyNameLabel.setText(LoginController.companyName);
    }

    private void showCompanyInternshipOffers() throws SQLException {
        ObservableList<InternshipOffer> companyInternshipsList = getCompanyInternshipOffers();

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("internshipTitle"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("applicationDeadline"));

        internshipsTable.setItems(companyInternshipsList);
    }

    public static ObservableList<InternshipOffer> getCompanyInternshipOffers() throws SQLException {
        ObservableList<InternshipOffer> companyInternshipsList = FXCollections.observableArrayList();
        final String query = "SELECT * FROM internship WHERE company_id = ?";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);

        assert prepStmt != null;
        prepStmt.setInt(1, LoginController.companyId);
        ResultSet resultSet = Database.executeSelect(prepStmt);
        assert resultSet != null;

        while (resultSet.next()) {
            InternshipOffer internship = new InternshipOffer();
            internship.setInternshipTitle(resultSet.getString("internship_title"));
            internship.setManager(resultSet.getString("manager"));
            internship.setApplicationDeadline(resultSet.getString("application_deadline"));
            companyInternshipsList.add(internship);
        }
        return companyInternshipsList;
    }

    public static ObservableList<InternshipOffer> getAllAvailableInternships() throws SQLException {
        ObservableList<InternshipOffer> allInternshipsList = FXCollections.observableArrayList();
        final String query = "SELECT * FROM internship";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);

        ResultSet resultSet = Database.executeSelect(prepStmt);
        assert resultSet != null;
        while (resultSet.next()) {
            InternshipOffer internship = new InternshipOffer();
            internship.setInternshipId(resultSet.getInt("internship_id"));
            internship.setCompanyName(getCompanyName(resultSet.getInt("company_id")));
            internship.setInternshipTitle(resultSet.getString("internship_title"));
            internship.setCompanyDescription(resultSet.getString("short_company_description"));
            internship.setInternshipDescription(resultSet.getString("internship_description"));
            internship.setManager(resultSet.getString("manager"));
            internship.setRequirements(resultSet.getString("requirements"));
            internship.setApplicationDeadline(resultSet.getString("application_deadline"));
            allInternshipsList.add(internship);
        }
        return allInternshipsList;
    }

    public static String getCompanyName(int companyId) throws SQLException {
        final String query = "SELECT name FROM company_account WHERE id = ?";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);
        assert prepStmt != null;
        prepStmt.setInt(1, companyId);

        ResultSet resultSet = Database.executeSelect(prepStmt);
        assert resultSet != null;
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        System.out.println("No company has the Id: "+ companyId);
        return null;
    }

    @FXML
    void goToAddNewInternship(ActionEvent event) throws IOException {
        Helpers.loadAddNewInternship(event);
    }
    @FXML
    void goToAccount(ActionEvent event) throws IOException {
        Helpers.loadCompanyAccount(event);
    }
    @FXML
    void goToApplicants(ActionEvent event) throws IOException {
        Helpers.loadApplicants(event);
    }

}
