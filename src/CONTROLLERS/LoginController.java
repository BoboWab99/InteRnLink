package CONTROLLERS;

import SERVICES.Database;
import SERVICES.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController {
    @FXML
    private TextField compNameTf;
    @FXML
    private TextField compPasswordTf;
    @FXML
    private Label msg;
    public static int companyId;
    public static String companyName;

    public void backHome(ActionEvent event) throws IOException {
        Helpers.loadHomePage(event);
    }

    public void login(ActionEvent event) throws SQLException, IOException {
        if (compNameTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Please, enter your email address!");
            return;
        }
        if (compPasswordTf.getText().isEmpty()) {
            Helpers.showEmptyInputMsg(msg, "Please, enter your password!");
            return;
        }

        String username = compNameTf.getText();
        String password = compPasswordTf.getText();

        if (userCredentialsVerified(username, password)) {
            Helpers.loadCompanyAccount(event);
        } else {
            msg.setText("Enter valid user credentials!");
            msg.setVisible(true);
            compNameTf.clear();
            compPasswordTf.clear();
        }
    }

    public boolean userCredentialsVerified(String username, String password) throws SQLException {
        final String loginInfoQuery = "SELECT * FROM company_account WHERE email = ? AND pass = ?";
        PreparedStatement newPrepStmt =  Database.prepareNewStatement(loginInfoQuery);
        assert newPrepStmt != null;
        newPrepStmt.setString(1, username);
        newPrepStmt.setString(2, password);

        ResultSet result = Database.executeSelect(newPrepStmt);
        assert result != null;
        if (result.next()) {
            companyId = result.getInt("id");
            companyName = result.getString("name");
            return true;
        }
        return false;
    }

}
