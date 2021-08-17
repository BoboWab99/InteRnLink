package SERVICES;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;

public class Helpers {
    public static final String fileUploadsFolder = "C:\\Users\\arnau\\IdeaProjects\\internlink\\src\\FILE_UPLOADS\\";
    public static final String FILE_UPLOADS = "file:///C:/Users/arnau/IdeaProjects/internlink/src/FILE_UPLOADS/";
    public static final String placeholderImgPath = FILE_UPLOADS + "No_Image_Available.jpg";

    public static FXMLLoader changeStage(ActionEvent event, String newPageUrl, String newPageTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader(Helpers.class.getResource(newPageUrl));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        newStage.setScene(newScene);
        newStage.setTitle(newPageTitle);
        newStage.show();
        return loader;
    }

    // setTimeout() from stackOverflow
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }).start();
    }

    public static void showEmptyInputMsg(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        Helpers.setTimeout(() -> label.setVisible(false), 5000);
    }

    public static FileChooser.ExtensionFilter wordDocExtensionFilter() {
        return new FileChooser.ExtensionFilter("Word Files", "*.doc", "*.docx", "*.DOC", "*.DOCX");
    }

    public static FileChooser.ExtensionFilter imgDocExtensionFilter() {
        return new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
    }

    public static File getUploadedImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(imgDocExtensionFilter());
        File imgFile =  fileChooser.showOpenDialog(null);
        if (imgFile == null) {
            System.out.println("No image uploaded.");
            return null;
        }
        return imgFile;
    }

    public static void loadHomePage(ActionEvent event) throws IOException {
        final String url = "/FXML/Home.fxml";
        final String title = "InternLink | Home";
        changeStage(event, url, title);
    }

    public static void loadCompanyAccount(ActionEvent event) throws IOException {
        final String url = "/FXML/Account.fxml";
        final String title = "InternLink | Account";
        changeStage(event, url, title);
    }

    public static void loadApplicants(ActionEvent event) throws IOException {
        final String url = "/FXML/Applicants.fxml";
        final String title = "InternLink | Applicants";
        changeStage(event, url, title);
    }

    public static void loadCompanyInternshipOffers(ActionEvent event) throws IOException {
        final String url = "/FXML/InternshipOffers.fxml";
        final String title = "InternLink | Applicants";
        changeStage(event, url, title);
    }

    public static void loadAddNewInternship(ActionEvent event) throws IOException {
        final String url = "/FXML/AddInternship.fxml";
        final String title = "InternLink | Add New Internship Offer";
        changeStage(event, url, title);
    }

    public static void loadAvailableInternships(ActionEvent event) throws IOException {
        final String url = "/FXML/AvailableInternships.fxml";
        final String title = "InternLink | Add New Internship Offer";
        changeStage(event, url, title);
    }

    public static void loadSupportPage(ActionEvent event) throws IOException {
        final String url = "/FXML/Support.fxml";
        final String title = "InternLink | Get Support";
        changeStage(event, url, title);
    }

}
