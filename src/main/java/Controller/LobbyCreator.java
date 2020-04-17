package Controller;

import Configurate.Config;
import Configurate.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LobbyCreator extends Config {

    private DataHandler dataHandler;
    private String nickname;
    private String emailU;
    private String URL;

    public String getNickname() {
        return nickname;
    }

    public void setDataHandler(String name) {
        this.nickname = name;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane back;

    @FXML
    private Button connect;

    @FXML
    private Label erLable;

    @FXML
    private ImageView image;

    @FXML
    private Label nick;

    @FXML
    private Label email;

    @FXML
    void connectServer(ActionEvent event) {

    }

    @FXML
    void updateData(ActionEvent event) {

    }

    @FXML
    public void createLobby(String authLogin){

    }

    @FXML
    void initialize() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(getNickname());
            Connection connection = DriverManager.getConnection(HOST, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from " + TABLE_NAME + " where login = ?");
            preparedStatement.setString(1, getNickname());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            Image image_pattern = new Image("https://media-exp1.licdn.com/dms/image/C560BAQE8wqB_4YolTQ/company-logo_200_200/0?e=2159024400&v=beta&t=ZXhnor7eLwPLQATsrJ5pNplC5zUmrFvLyOIbZKtM7A8");
            image.setImage(image_pattern);
            this.nickname = rs.getString("login");
            this.emailU = rs.getString("email");
            System.out.println("Data dump: " + rs.getString("email") + " " +rs.getString("login"));
            rs.close();
        }catch (Exception e){
            System.out.println(e);
        }
        nick.setText(nickname);
        email.setText(emailU);
    }
}
