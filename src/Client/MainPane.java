package Client;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainPane {

    private Socket socket;

    public void showMain(Stage primarStage, String Account,Socket socket,StageController stageController){


        this.socket = socket;
        BorderPane borderPane = new BorderPane();
        TextArea textArea = new TextArea();
        TextField fri = new TextField();
        Button add = new Button("添加好友");
        TextField msg = new TextField();
        Button send = new Button("发送");
        borderPane.setCenter(textArea);
        HBox bottom = new HBox();
        bottom.getChildren().addAll(fri,add,msg,send);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        borderPane.setBottom(bottom);

        //添加好友
        add.setOnAction(e->{
            try {
                DataOutputStream dw = new DataOutputStream(socket.getOutputStream());
                DataInputStream dr = new DataInputStream(socket.getInputStream());
                dw.writeUTF("添加好友");
                dw.flush();
                dw.writeUTF(Account);
                dw.flush();
                dw.writeUTF(fri.getText());
                dw.flush();
                List<String> list = new ArrayList<>();
                String str = dr.readUTF();
                while(!str.equals("end")){
                    list.add(str);
                    str = dr.readUTF();
                }

                /*ChoiceBox choiceBox = new ChoiceBox();
                choiceBox.setItems();*/


            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        Scene scene = new Scene(borderPane,400,500);
        Platform.runLater(()-> {
            stageController.addStage("mainStage",primarStage);
            primarStage.setTitle(Account);
            primarStage.setScene(scene);
            primarStage.show();
        });

    }
}
