package Server.Dao.Reflection;

import java.lang.reflect.Field;

public class ReflecSelect {
    public static void main(String[] args) throws Exception {

        Class clazz = Class.forName("edu.cqut.hr.teacher.Teacher");
        Object entity = clazz.newInstance();
        String sql = generateSelectSQL(entity);
        System.out.println(sql);
    }

    public static String generateSelectSQL(Object entity) throws Exception, IllegalAccessException {

        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        //select * from teacher
        StringBuffer sql = new StringBuffer();

        sql.append("select * from ");
        sql.append(clazz.getSimpleName());
        return sql.toString();
    }

}
