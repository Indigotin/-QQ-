package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginPane extends Application {

    private Thread send;
    private Thread recive;
    private Socket client = null;
    private StageController stageController = new StageController();
    public void start(Stage primarStage)
    {
        BorderPane pane1 = new BorderPane();
        GridPane pane2 = new GridPane();
        Image image = new Image("image/1.png");
        ImageView imageview = new ImageView(image);
        imageview.fitHeightProperty().bind(pane1.heightProperty().divide(2));
        imageview.fitWidthProperty().bind(pane1.widthProperty());
        TextField field1 = new TextField();
        field1.setPrefColumnCount(10);
        PasswordField field2 = new PasswordField();
        field2.setPrefColumnCount(10);
        Label label1 = new Label("Account:");
        Label label2 = new Label("Password:");
        Button bt1 = new Button("New Account");
        Button bt2 = new Button("Change Password");
        Button bt3 = new Button("Longing");
        pane2.add(label1, 0, 0);
        pane2.add(field1, 1, 0);
        pane2.add(label2, 0, 1);
        pane2.add(field2, 1, 1);
        pane2.add(bt1, 2, 0);
        pane2.add(bt2, 2, 1);
        pane2.add(bt3, 1, 2);
        pane2.setAlignment(Pos.CENTER);
        pane2.setPadding(new Insets(11.5,12.5,13.5,14.5));
        pane2.setHgap(10);
        pane2.setVgap(10);
        pane1.setTop(imageview);
        pane1.setCenter(pane2);
        Scene sence = new Scene(pane1,530,380);
        primarStage.setTitle("Login");
        primarStage.setScene(sence);
        primarStage.show();
        bt1.setOnAction(e->{

        });
        bt2.setOnAction(e->{

        });
        bt3.setOnAction(e->{

            Platform.runLater(()-> {
                String Account = field1.getText();
                String Password = field2.getText();

                try {
                    client = new Socket("localhost", 9000);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    DataOutputStream dr = new DataOutputStream(client.getOutputStream());
                    dr.writeUTF("登录");
                    dr.flush();
                    dr.writeUTF(Account);
                    dr.flush();
                    dr.writeUTF(Password);
                    dr.flush();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //一条线程
                /*send = new Thread(new clientSendDome4(client,Account,Password));
                send.start();*/

                //another
                recive = new Thread(new clinetReciveDome4(client,primarStage,Account,client,stageController));
                recive.start();

                });
            });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

