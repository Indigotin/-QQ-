package Server.Dao.Reflection;

import java.lang.reflect.Field;

public class ReflecUpdate {

    public static void main(String[] args) throws Exception {

        Class clazz = Class.forName("edu.cqut.hr.teacher.Teacher");
        Object entity = clazz.newInstance();
        String sql = generateUpdateSQL(entity);
        System.out.println(sql);
    }

    public static String generateUpdateSQL(Object entity) throws Exception, IllegalAccessException {

        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        //update Teacher set Name='hh',Sex='男',Birth='1000-12-01',Salary=200.0,System='软件工程',Acadamy='计算机'where ID='2'
        StringBuffer sql = new StringBuffer();

        sql.append("update ");
        sql.append(clazz.getSimpleName());
        sql.append(" set ");
        for(Field field:fields) {
            if("ID".equals(field.getName())) {
                continue;
            }
            sql.append(field.getName()).append("=");
            field.setAccessible(true);
            Object value = field.get(entity);
            String valueString="'"+value+"',";
            sql.append(valueString);
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" where ");
        for(Field field:fields) {
            if("ID".equals(field.getName())) {
                sql.append(field.getName()).append("=");
                field.setAccessible(true);
                Object value = field.get(entity);
                String valueString="'"+value+"'";
                sql.append(valueString);
            }
        }
        return sql.toString();
    }
}
