package Controller;

import java.net.URL;
import java.util.*;

import Configurate.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Game extends Config {
    ArrayList <String> users = new ArrayList<String>();
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
    void initialize() {
        if(users.size() <= 4){
            users.add(this.user);
            balance.put(this.user,1000);
            System.out.println("Участник добавлен в игру: " + this.user + " $" + balance.get(this.user));
        }
        try {
            while (true) {
                updateUsers();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    void updateUsers(){
        try {
            if(users.size() == 1){
                userName1.setText(users.get(0));
                balance1.setText(balance.get(users.get(0)).toString());
                if (users.size() == 2){
                    userName2.setText(users.get(1));
                    balance2.setText(balance.get(users.get(1)).toString());
                    if(users.size() == 3){
                        userName3.setText(users.get(2));
                        balance3.setText(balance.get(users.get(2)).toString());
                        if (users.size() == 4){
                            userName4.setText(users.get(3));
                            balance4.setText(balance.get(users.get(3)).toString());
                        }
                    }
                }
            }
            System.out.println("UPDATED USER LIST:" + users);
            Thread.sleep(15000);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
