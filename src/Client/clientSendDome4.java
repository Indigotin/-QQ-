package Client;
/*
发送数据 线程
 */
import Server.closeUtilDome4;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class clientSendDome4 implements Runnable{

    private DataOutputStream dr;
    private BufferedReader console;
    private String account;
    private String password;
    //控制线程的标识
    private boolean isRunning = true;

    public clientSendDome4(){

        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public clientSendDome4(Socket client,String account,String password){

        this();
        try {
            dr = new DataOutputStream(client.getOutputStream());
            this.account = account;
            this.password = password;
            send("登录");
            send(this.account);
            send(this.password);
        } catch (IOException e) {
            isRunning = false;
            closeUtilDome4.closeAll(dr,console);
        }
    }

    private String getMsgFromConsole(){
        try {
            return console.readLine();
        } catch (IOException e) {

            isRunning = false;
            closeUtilDome4.closeAll(dr,console);
        }
        return null;
    }

    public void send(String msg){
        if (msg != null&&!msg.equals("")){
            try {
                dr.writeUTF(msg);
                dr.flush();//强制刷新
            } catch (IOException e) {

                isRunning = false;
                closeUtilDome4.closeAll(dr,console);
            }
        }
    }

    @Override
    public void run() {

        while(isRunning){
            send(getMsgFromConsole());
        }
    }
}
