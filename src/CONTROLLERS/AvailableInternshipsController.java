package CONTROLLERS;

import APP.InternshipOffer;
import SERVICES.Database;
import SERVICES.Helpers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AvailableInternshipsController implements Initializable {
    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try { showAllAvailableInternships(); }
        catch (IOException | SQLException e) { e.printStackTrace(); }
    }

    public void showAllAvailableInternships() throws IOException, SQLException {
        ObservableList<InternshipOffer> allInternshipsList = InternshipOffersController.getAllAvailableInternships();

        for (int i = 0, j = 0; i < allInternshipsList.size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/InternshipBox.fxml"));
            AnchorPane pane = loader.load();
            InternshipBoxController internshipBox = loader.getController();
            InternshipOffer internship = allInternshipsList.get(i);

            /* save boxId as internshipId */
            internshipBox.setBoxId(internship.getInternshipId());
            internshipBox.getCompanyNameLabel().setText(internship.getCompanyName());
            internshipBox.getInternshipTitleLabel().setText(internship.getInternshipTitle());
            String requirements = internship.getRequirements();
            if (requirements.length() > 100) {
                requirements = requirements.substring(0, 100) + "...";
            }
            internshipBox.getRequirementsText().setText(requirements);

            final int companyId = retrieveCompanyId(internshipBox.getBoxId());
            final String imgPath = Helpers.FILE_UPLOADS + companyId + ".png";
            int imgWidth = 333;
            int imgHeight = 140;
            Image image = new Image(imgPath, imgWidth, imgHeight, false, true);
            if (image.isError()) {
                image = new Image(Helpers.placeholderImgPath, imgWidth, imgHeight, false, true);
            }
            internshipBox.getImageView().setImage(image);

            /* display 1D list elements into 2D */
            grid.add(pane, i % 2, j);
            j = ((i+1) % 2 == 0) ? j+1 : j;
        }
    }

    private int retrieveCompanyId(int internshipId) throws SQLException {
        final String query = "SELECT company_id FROM `internship` WHERE internship_id = ?";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);
        assert prepStmt != null;
        prepStmt.setInt(1, internshipId);
        ResultSet resultSet = Database.executeSelect(prepStmt);
        assert resultSet != null;
        if (resultSet.next()) {
            return resultSet.getInt("company_id");
        }
        System.out.println("internshipId: " + internshipId + " is not associated with any company.");
        return -1;
    }

    public void backHome(ActionEvent event) throws IOException {
        Helpers.loadHomePage(event);
    }

}
