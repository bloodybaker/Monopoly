package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import Configurate.Config;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Game extends Config {
    Gson gson = new Gson();
    Map <String,Integer> balance = new LinkedHashMap<String, Integer>();
    String user;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane back;

    @FXML
    private Label userName1;

    @FXML
    private Label userName2;

    @FXML
    private Label userName3;

    @FXML
    private Label userName4;

    @FXML
    private ToggleButton toggle;

    @FXML
    private Label balance1;

    @FXML
    private Label balance2;

    @FXML
    private Label balance3;

    @FXML
    private Label balance4;

    public void setUser(String username){
        this.user = username;
    }

    @FXML
    void pawn(MouseEvent event) {
        int random = (int)Math.round(Math.random()*6);
        System.out.println(random);
    }

    @FXML
    void ready(ActionEvent event) {

        if(balance.size() >= 4){
            toggle.setVisible(false);
            updateUsers();
        }else {
            if (toggle.isSelected()) {
                toggle.setText("ГОТОВ");
                toggle.getStyleClass().add("green-button");
                updateUsers();
            } else {
                toggle.setText("НЕ ГОТОВ");
                toggle.getStyleClass().add("red-button");
                updateUsers();
            }
        }
    }

    @FXML
    void initialize() {

    }
    void updateUsers(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(HOST, USER, PASS);
            PreparedStatement selectionStatement = connection.prepareStatement("select * from " + "room" + "");
            ResultSet list = selectionStatement.executeQuery();
            System.out.println("Getting data from DB");
            while (list.next()) {
                int num = list.getInt("number");
                String ni = list.getString("nickname");
                int mo = list.getInt("money");
                System.out.println(num + " " + ni + " " + mo);
                if (balance.size() <= 4) {
                    balance.put(ni,mo);
                }
            }
            startGame();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    void tempUpdateData(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(HOST, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from " + "room" + "");
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Successful execute");
            //rs.next();
            while(rs.next()){
                System.out.println(rs.getInt("number") + rs.getString("nickname") + rs.getInt("money"));
            }
            rs.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    void startGame(){
        String json = gson.toJson(balance);
        System.out.println(json);
    }
}
