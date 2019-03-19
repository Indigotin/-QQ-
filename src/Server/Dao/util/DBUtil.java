package Server.Dao.util;

import Server.Model.Account;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    //可修改变量
    private String driver;
    private String url;
    private String username;
    private String password;
    //需要建立一些连接的变量
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private String path;

    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return path;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //构造方法，在构造方法中建立一些连接操作
    public DBUtil() {
        driver="com.mysql.jdbc.Driver";
        url="jdbc:mysql://localhost:3306/account?useUnicode=true&characterEncoding=utf-8";
        username="root";
        password="Hlq3328Mysql";
    }

    //获取连接对象
    private Connection getCon(){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public boolean execute(String sql) throws Exception
    {
        con = getCon();
        stmt = con.createStatement();
        return stmt.execute(sql);
    }

    public List getList(String sql) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        List<Object> list = new ArrayList();
        con = getCon();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        while(rs.next()){

            list.add(getObject(rs));
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }

    public static String setMethod(String name){
        String upper = name.substring(0,1).toUpperCase();//将给定的字符串的首字符变成大写
        String end = name.substring(1);//获得给定字符串除了首字符的余下部分
        return "set"+upper+end;
    }

    public Object getOne(String sql) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        con = getCon();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        rs.next();
        return getObject(rs);
    }

    private Object getObject(ResultSet rs) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {


        Class cla = Class.forName(path);
        Object temp =  cla.newInstance();

        ResultSetMetaData data = rs.getMetaData();//获取元数据
        int column_count = data.getColumnCount();//获取元数据字段的个数

        for(int i=1; i<=column_count;i++) {//通过循环逐个获得每个字段

            String column_name = data.getColumnName(i);//获得对应字段的名称
            String column_type = data. getColumnTypeName(i);//获取字段数据类型
            String setMethod = setMethod(column_name);//获取字段名称对应的set方法//根据数据车中字段的数据类型执行对应的set方法

            if (column_type.equalsIgnoreCase("integer")){
                temp.getClass().getMethod(setMethod, Integer.class).invoke(temp,rs.getInt(column_name));
            }
            else if (column_type.equalsIgnoreCase("VARCHAR")){
                temp.getClass().getMethod(setMethod, String.class).invoke(temp,rs.getString(column_name));
            }
        }
        return temp;
    }

    public boolean update(String sql) throws SQLException {

        con = getCon();
        pstmt = con.prepareStatement(sql);
        int result = pstmt.executeUpdate();
        if(result==1)
            return true;
        else
            return false;
    }

    //关闭对象
    private void close() {
        try {
            if(rs!=null) {
                rs.close();
            }
            if(con!=null) {
                con.close();
            }
            if(pstmt!=null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
