package Server.Model;

import Server.Dao.AccountDao;

import java.util.List;

public class CheckAccount {

    public boolean Check(String account,String password) throws Exception {

        //System.out.println("dsfkksd");
        AccountDao dao = new AccountDao();
        List<Account> list = dao.select(new Account());
        for(int i=0;i<list.size();i++){
            Account temp = list.get(i);
            System.out.println(temp.getAccount()+temp.getPassword());
            if(temp.getAccount().equals(account) && temp.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

}
