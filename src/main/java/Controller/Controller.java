package Controller;

import Configurate.Config;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import Configurate.DataHandler;
import java.sql.*;

public class Controller extends Config {
    DataHandler dataHandler = new DataHandler();

    @FXML
    private Label erLable;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane back;

    @FXML
    private Button register;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button submit;

    @FXML
    void register(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/registration.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.showAndWait();
    }

    @FXML
    void checkUser(ActionEvent event) {
        String userLogin = login.getText().trim();
        String userPass = password.getText().trim();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(HOST,USER,PASS);

            PreparedStatement preparedStatement = connection.prepareStatement("select login,pass from "+ TABLE_NAME +" where login = ? and pass = ?");
            preparedStatement.setString(1,userLogin);
            preparedStatement.setString(2,userPass);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String log = rs.getString("login");
            System.out.println("LOG: " + log);
            if(!userLogin.equals(null) && !userPass.equals(null)) {
                if (userLogin.trim().equals(rs.getString("login"))) {
                    if (userPass.trim().equals(rs.getString("pass"))) {
                        submit.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
                        Parent root = (Parent) loader.load();
                        LobbyCreator lobbyCreator = loader.getController();
                        lobbyCreator.setDataHandler(rs.getString("login"));
                        lobbyCreator.initialize();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
            }else {
                erLable.setVisible(true);
            }
            rs.close();
        }catch (Exception e){
            erLable.setVisible(true);
            System.out.println(e);
        }

    }

    @FXML
    void initialize() {


    }
}
