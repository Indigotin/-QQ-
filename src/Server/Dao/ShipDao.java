package Server.Dao;

import Server.Dao.Reflection.*;
import Server.Dao.util.DBUtil;
import Server.Model.Ship;

import java.util.List;

public class ShipDao {

    private static ShipDao shipDao;
    private DBUtil dbUtil = new DBUtil();

    public static ShipDao getAccountDao() {
        if (shipDao == null) {
            shipDao = new ShipDao();
        }
        return shipDao;
    }

    public void add(Ship ship) throws Exception {
        String sql = ReflecAdd.generateInsertSQL(ship);
        try {
            //System.out.println(sql);
            dbUtil.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean delete(Ship ship) throws Exception {
        String sql = ReflecDel.generateDelSQL(ship);
        System.out.println(sql);
        return dbUtil.execute(sql);
    }

    public List select(Ship ship) throws Exception {
        //String sql = "select * from teacher";
        String sql = ReflecSelect.generateSelectSQL(ship);
        dbUtil.setPath("Server.Model.Ship");
        return dbUtil.getList(sql);
    }

    public Ship selectOne(Ship temp) throws Exception {
        String sql = RelecSelectOne.generateSeletOneSQL(temp);
        return (Ship) dbUtil.getOne(sql);
    }

    public boolean update(Ship ship) throws Exception {

        String sql = ReflecUpdate.generateUpdateSQL(ship);
        System.out.println(sql);
        return dbUtil.update(sql);
    }
}
