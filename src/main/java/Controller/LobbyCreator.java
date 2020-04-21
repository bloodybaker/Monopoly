package Controller;

import Configurate.Config;
import Configurate.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LobbyCreator extends Config {

    private String nickname;
    private String emailU;
    private String URL;

    public String getNickname() {
        return nickname;
    }

    public void setDataHandler(String name) {
        this.nickname = name;
    }

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3443;
    private Socket clientSocket;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private int number = 0;

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
    private TextArea area;

    @FXML
    private TextField inputField;

    @FXML
    void connectServer(ActionEvent event) {
        connect.getScene().getWindow().hide();
        try {
            //проверка количества людей в комнате
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(HOST, USER, PASS);
            PreparedStatement selectionStatement = connection.prepareStatement("select * from " + "room" + "");
            ResultSet list = selectionStatement.executeQuery();
            System.out.println("Successful execute");
            //rs.next();
            while(list.next()){
                int num = list.getInt("number");
                String ni = list.getString("nickname");
                int mo = list.getInt("money");
                System.out.println(num + " " + ni +  " " +  mo);
                if(num == 4){
                    System.out.println("Комната переполнена");
                    break;
                }else {
                    number++;
                    insert(number);
                }
            }


            list.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
            Parent root = (Parent) loader.load();
            Game game = loader.getController();
            game.setUser(nick.getText());
            game.initialize();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }

    }
    //добавление пользователя в комнату
    void insert(int value){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(HOST, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into " + "room" + " (number, nickname, money) values (?,?,?)");
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, nick.getText());
            preparedStatement.setInt(3, 1000);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    // валидация сообщения и вызов метода отправки
    @FXML
    void sendmsg(ActionEvent event) {
        if(!inputField.getText().equals("")) {
           send();
            inputField.setText("");
        }
    }
    @FXML
    void updateData(ActionEvent event) {

    }

    @FXML
    public void createLobby(String authLogin){

    }

    @FXML
    void initialize() {
        area.setPrefColumnCount(1);
        area.setPrefRowCount(40);
        try {
            //получение информаци о пользователе
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(HOST,USER,PASS);
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

        //connection
        try {
            // подключаемся к серверу
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    // бесконечный цикл
                    while (true) {
                        // если есть входящее сообщение
                        if (inMessage.hasNext()) {
                            // считываем его
                            String inMes = inMessage.nextLine();
                            area.appendText( inMes+ "\n");
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }
    public void send(){
        String messageStr = "["+ getNickname() +"]: " +inputField.getText() + "\n";
        outMessage.println(messageStr);
        outMessage.flush();
    }
}
