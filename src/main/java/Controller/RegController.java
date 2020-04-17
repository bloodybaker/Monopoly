package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import Configurate.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController extends Config {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button sReg;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label erLable;

    @FXML
    private PasswordField confPassword;

    @FXML
    private TextField email;

    @FXML
    void finish(ActionEvent event) {
        String userEmail = email.getText();
        String userLogin = login.getText();
        String userPass = password.getText();
        String cPass = confPassword.getText();
        if(!userLogin.equals(null) & !userEmail.equals(null) & !userPass.equals(null) & !cPass.equals(null)){
            if(cPass.equals(userPass)){
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(HOST,USER,PASS);
                        PreparedStatement insert = connection.prepareStatement("insert into "+ TABLE_NAME +" (email, login, pass) values(?,?,?)");
                        insert.setString(1,userEmail);
                        insert.setString(2,userLogin);
                        insert.setString(3,userPass);
                        insert.executeUpdate();
                        connection.close();
                        stageCloser();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/lobby.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage st = new Stage();
                        st.setScene(new Scene(root));
                        st.showAndWait();
                }catch (Exception e){
                    erLable.setVisible(true);
                    System.out.println(e);
                }
            }else{
                erLable.setText("Пароли не совпадают");
                erLable.setVisible(true);
            }
        }else {
            erLable.setVisible(true);
        }
    }

    @FXML
    void backPage(ActionEvent event) {
        stageCloser();
    }
    @FXML
    void initialize() {

    }
    void stageCloser(){
        back.getScene().getWindow().hide();
    }
}
