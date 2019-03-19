package Server;

import Server.Dao.ShipDao;
import Server.Model.Account;
import Server.Model.CheckAccount;
import Server.Model.Ship;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<ServerReciveDome4> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        new Server().start();
    }

    public void start() throws IOException {

        ServerSocket Server = new ServerSocket(9000);

        while(true){
            Socket serverSocket = Server.accept();
            ServerReciveDome4 ser = new ServerReciveDome4(serverSocket);
            list.add(ser);
            new Thread(ser).start();    //一条通道
        }
    }

    //内部类
    private class ServerReciveDome4 implements Runnable{

        private DataInputStream dr;
        private DataOutputStream dw;
        //private ObjectOutputStream dwObject;
        private boolean isRunning = true;
        private String account;
        private String password;

        public ServerReciveDome4(){}

        public ServerReciveDome4(Socket server){

            try {
                dr = new DataInputStream(server.getInputStream());
                dw = new DataOutputStream(server.getOutputStream());
                //dwObject = new ObjectOutputStream(server.getOutputStream());

                /*account = dr.readUTF();
                password = dr.readUTF();
                System.out.println("account:"+account+",  password:"+password);
                System.out.println(new CheckAccount().Check(account,password));
                if(new CheckAccount().Check(account,password)){

                    this.ServerRecive("successful");//登录成功
                }*/
                /*for(ServerReciveDome4 temp:list){
                    if(temp==this){
                        continue;
                    }
                    temp.ServerRecive("欢迎"+this.account+"进入聊天室！");
                }*/

            } catch (IOException e) {
                isRunning = false;
                closeUtilDome4.closeAll(dr,dw);
                list.remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public String getMsgFromClient(){
            try {
                return dr.readUTF();
            } catch (IOException e) {
                isRunning = false;
                closeUtilDome4.closeAll(dr,dw);
                list.remove(this);
            }
            return "";
        }

        public void ServerRecive(String msg){

            if(msg!=null&&!msg.equals("")){
                try {
                    dw.writeUTF(msg);
                    dw.flush();
                } catch (IOException e) {
                    isRunning = false;
                    closeUtilDome4.closeAll(dr,dw);
                    list.remove(this);
                }
            }
        }

        public void sendOne(String msg, String from,String arrive){

            for(ServerReciveDome4 temp:list){

                if(arrive.equals(temp.account)){
                    temp.ServerRecive("添加好友");
                    temp.ServerRecive(this.account);
                }
            }
        }

        public void sendOthers(String msg){

            if(msg.indexOf('@') == 0){
                //sendOne(msg);

            }else{
                for(ServerReciveDome4 temp:list){

                    if(temp==this){
                        continue;
                    }
                    temp.ServerRecive(this.account+"对所有人说:"+msg);
                }
            }
        }

        @Override
        public void run() {
            while(isRunning){
                try {
                    String type = dr.readUTF();

                    if(type.equalsIgnoreCase("登录")){
                        account = dr.readUTF();
                        password = dr.readUTF();
                        System.out.println("account:"+account+",  password:"+password);
                        System.out.println(new CheckAccount().Check(account,password));
                        if(new CheckAccount().Check(account,password)){

                            this.ServerRecive("successful");//登录成功
                        }
                    }
                    else if(type.equalsIgnoreCase("添加好友")){
                        account = dr.readUTF();
                        String fri = dr.readUTF();
                        sendOne("添加好友",account,fri);

                    }
                    else if(type.equalsIgnoreCase("聊天信息")){

                    }
                    else if(type.equalsIgnoreCase("结果")){

                        String from = dr.readUTF();
                        String result = dr.readUTF();
                        String arrive = dr.readUTF();
                        if(result.equalsIgnoreCase("同意")){
                            Ship ship = new Ship(from,arrive);
                            ShipDao shipDao = new ShipDao();
                            shipDao.add(ship);
                            List<Ship> list = shipDao.select(ship);
                            for(int i=0;i<list.size();i++){

                                dw.writeUTF(list.get(i).getShip2());
                                dw.flush();
                            }
                            dw.writeUTF("end");
                            dw.flush();
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //sendOthers(getMsgFromClient());
            }
        }
    }

}

