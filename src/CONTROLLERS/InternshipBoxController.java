package CONTROLLERS;

import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InternshipBoxController {
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label internshipTitleLabel;
    @FXML
    private Text requirementsText;
    @FXML
    private ImageView imageView;
    private int boxId;
    public static int internshipId;

    // constructor
    public InternshipBoxController() {}

    public void setBoxId(int id) { this.boxId = id; }
    public int getBoxId() { return boxId; }
    public Label getCompanyNameLabel() { return companyNameLabel; }
    public Label getInternshipTitleLabel() { return internshipTitleLabel; }
    public Text getRequirementsText() { return requirementsText; }
    public ImageView getImageView() { return imageView; }

    @FXML
    void goToDetails(ActionEvent event) throws IOException, SQLException {
        final String url = "/FXML/InternshipDetails.fxml";
        final String title = "InternLink | Internship Details";
        FXMLLoader loader = Helpers.changeStage(event, url, title);
        InternshipDetailsController detailsPage = loader.getController();

        ResultSet resultSet = getInternshipDetails();
        if (resultSet.next()) {
            final int companyId = resultSet.getInt("company_id");
            final String companyName = InternshipOffersController.getCompanyName(companyId);
            detailsPage.getCompanyNameLabel().setText(companyName);
            detailsPage.getInternshipTitleLabel().setText(resultSet.getString("internship_title"));
            detailsPage.getCompanyDescriptionTxt().setText(resultSet.getString("short_company_description"));
            detailsPage.getInternshipDescriptionTxt().setText(resultSet.getString("internship_description"));
            detailsPage.getRequirementsTxt().setText(resultSet.getString("requirements"));

            final String submissionInfo = "Interested applicants should submit their Curriculum Vitae (CV) before 5:00PM - " +
                    resultSet.getString("application_deadline") + ". Note that all applications are addressed" +
                    " to the " + resultSet.getString("manager") + ".";
            detailsPage.getSubmissionInfoTxt().setText(submissionInfo);

            final String imgPath = Helpers.FILE_UPLOADS + companyId + ".png";
            Image image = new Image(imgPath, 350, 180, false, false);
            if (image.isError()) {
                image = new Image(Helpers.placeholderImgPath, 350, 180, false, false);
            }
            detailsPage.getImageView().setImage(image);
        }
    }

    private ResultSet getInternshipDetails() throws SQLException {
        final String query = "SELECT * FROM internship WHERE internship_id = ?";
        PreparedStatement prepStmt = Database.prepareNewStatement(query);
        assert prepStmt != null;
        prepStmt.setInt(1, getBoxId());
        internshipId = getBoxId();
        return Database.executeSelect(prepStmt);
    }

}
