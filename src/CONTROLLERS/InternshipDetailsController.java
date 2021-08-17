package CONTROLLERS;

import SERVICES.Helpers;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class InternshipDetailsController {
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label internshipTitleLabel;
    @FXML
    private Text requirementsTxt;
    @FXML
    private Text companyDescriptionTxt;
    @FXML
    private Text internshipDetailsTxt;
    @FXML
    private Text submissionInfoTxt;
    @FXML
    private ImageView imageView;

    public Label getCompanyNameLabel() { return companyNameLabel; }
    public Label getInternshipTitleLabel() { return internshipTitleLabel; }
    public Text getRequirementsTxt() { return requirementsTxt; }
    public Text getCompanyDescriptionTxt() { return companyDescriptionTxt; }
    public Text getInternshipDescriptionTxt() { return internshipDetailsTxt; }
    public Text getSubmissionInfoTxt() { return submissionInfoTxt; }
    public ImageView getImageView() { return imageView; }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Helpers.loadHomePage(event);
    }
    @FXML
    void backToInternships(ActionEvent event) throws IOException {
        Helpers.loadAvailableInternships(event);
    }
    @FXML
    void showApplicationForm(ActionEvent event) throws IOException {
        Helpers.changeStage(event, "/FXML/ApplicationForm.fxml", "InternLink | Apply");
    }

}
