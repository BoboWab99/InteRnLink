package CONTROLLERS;

import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ApplicantDetailsBoxController {
    @FXML
    private Label fullNameLabel2;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label schoolLabel2;
    @FXML
    private Label degreeLabel;
    @FXML
    private Label cvFileNameLabel;
    @FXML
    private Label fullNameLabel1;
    @FXML
    private Label schoolLabel1;
    @FXML
    private Label internshipTitleLabel;
    private int applicantId;

    public void setApplicantId(int applicantId) { this.applicantId = applicantId; }
    public int getApplicantId() { return applicantId; }
    public Label getFullNameLabel2() { return fullNameLabel2; }
    public Label getEmailLabel() { return emailLabel; }
    public Label getPhoneNumberLabel() { return phoneNumberLabel; }
    public Label getSchoolLabel2() { return schoolLabel2; }
    public Label getCvFileNameLabel() { return cvFileNameLabel; }
    public Label getFullNameLabel1() { return fullNameLabel1; }
    public Label getSchoolLabel1() { return schoolLabel1; }
    public Label getDegreeLabel() { return degreeLabel; }
    public Label getInternshipTitleLabel() { return internshipTitleLabel; }

    @FXML
    private void downloadApplicantCV(ActionEvent event) throws IOException {
        File cv = getCV();
        if (cv == null) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName(fullNameLabel1.getText() + " CV.docx");
        fileChooser.getExtensionFilters().addAll(Helpers.wordDocExtensionFilter());
        File dest = fileChooser.showSaveDialog(null);
        if (dest != null) {
            Files.copy(cv.toPath(), dest.toPath());
        }
    }

    @FXML
    void openApplicantCV(ActionEvent event) throws IOException {
        File cv = getCV();
        if (cv == null) return;
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.OPEN)) {
            desktop.open(cv);
        }
    }

    private File getCV() {
        final String fileName = getApplicantId() + ".docx";
        File img = new File(Helpers.fileUploadsFolder + fileName);
        if (!img.exists()) {
            System.out.println("No image found!");
            return null;
        }
        return img;
    }

}
