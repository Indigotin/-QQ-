package Server;

import Server.Dao.AccountDao;
import Server.Model.Account;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        AccountDao dao = new AccountDao();
        Account account = new Account("qwerty","13");
        //dao.add(account);
        List list = dao.select(account);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString());
        }
    }
}
