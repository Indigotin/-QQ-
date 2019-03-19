package Server.Dao;

import Server.Dao.Reflection.*;
import Server.Dao.util.DBUtil;
import Server.Model.Account;

import java.util.List;

public class AccountDao {

    private static AccountDao accountDao;
    private DBUtil dbUtil = new DBUtil();

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao();
        }
        return accountDao;
    }

    public void add(Account account) throws Exception {
        String sql = ReflecAdd.generateInsertSQL(account);
        try {
            //System.out.println(sql);
            dbUtil.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean delete(Account account) throws Exception {
        String sql = ReflecDel.generateDelSQL(account);
        System.out.println(sql);
        return dbUtil.execute(sql);
    }

    public List select(Account account) throws Exception {
        //String sql = "select * from teacher";
        String sql = ReflecSelect.generateSelectSQL(account);
        dbUtil.setPath("Server.Model.Account");
        return dbUtil.getList(sql);
    }

    public Account selectOne(Account temp) throws Exception {
        String sql = RelecSelectOne.generateSeletOneSQL(temp);
        return (Account) dbUtil.getOne(sql);
    }

    public boolean update(Account account) throws Exception {

        String sql = ReflecUpdate.generateUpdateSQL(account);
        System.out.println(sql);
        return dbUtil.update(sql);
    }

}
