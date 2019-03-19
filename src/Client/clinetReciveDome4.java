package Client;
/*
接受数据 线程
 */
import Server.closeUtilDome4;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class clinetReciveDome4 implements Runnable{

    private StageController stageController;
    private DataInputStream dr;
    private DataOutputStream dw;
    private boolean isRunning = true;
    private Stage stage;
    private String Account;
    private Socket socket;
    private TextArea textArea;
    public clinetReciveDome4(){

    }

    public clinetReciveDome4(Socket client,Stage stage,String Account,Socket socket,StageController stageController){

        this();
        this.stage = stage;
        this.Account = Account;
        this.socket = socket;
        this.stageController = stageController;
        //this.textArea = textArea;
        try {
            dr = new DataInputStream(client.getInputStream());
            dw = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {

            isRunning = false;
            closeUtilDome4.closeAll(dr);
            //e.printStackTrace();
        }
    }

    public String recive(){
        try {
            return dr.readUTF();
        } catch (IOException e) {
            isRunning = false;
            closeUtilDome4.closeAll(dr);
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run () {
        System.out.println(isRunning);
        while (isRunning) {
            String msg = recive();
            System.out.println(msg);
            if (msg.equals("successful")) {
                new MainPane().showMain(stage, Account,socket,stageController);
                //isRunning = false;
            }
            else if(msg.equals("添加好友")){
                String from = null;
                try {
                    from = dr.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = creatStage("请问你是否接受"+from+"添加你为好友?",from);
                stageController.addStage("添加好友",stage);
                stage.show();
            }
            else if(msg.equalsIgnoreCase("聊天信息")){


            }
            System.out.println(recive());
        }
    }

    private Stage creatStage(String msg,String from){

        Stage stage = new Stage();
        StackPane stackPane = new StackPane(new Label("msg"));
        Button ok = new Button("同意");
        Button reject = new Button("拒绝");
        ok.setOnAction(e->{
            try {
                dw.writeUTF("结果");
                dw.flush();
                dw.writeUTF(Account);
                dw.flush();
                dw.writeUTF("同意");
                dw.writeUTF(from);
                dw.flush();
                Stage newStage = stageController.getStage("mainStage");
                newStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        reject.setOnAction(e->{

            try {
                dw.writeUTF("结果");
                dw.flush();
                dw.writeUTF(Account);
                dw.flush();
                dw.writeUTF("拒绝");
                dw.flush();
                dw.writeUTF(from);
                dw.flush();
                Stage newStage = stageController.getStage("mainStage");
                newStage.show();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        HBox hBox = new HBox(ok,reject);
        VBox vBox = new VBox(stackPane,hBox);
        Scene scene = new Scene(vBox);
        stage.setTitle(Account);
        stage.setScene(scene);
        return stage;
    }
}
