package Server.Dao.Reflection;

import java.lang.reflect.Field;

public class RelecSelectOne {
    public static void main(String[] args) throws Exception {

        Class clazz = Class.forName("edu.cqut.hr.teacher.Teacher");
        Object entity = clazz.newInstance();
        String sql = generateSeletOneSQL(entity);
        System.out.println(sql);
    }

    public static String generateSeletOneSQL(Object entity) throws Exception, IllegalAccessException {

        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        //"select * from Teacher where ID='"+ID+"'"
        StringBuffer sql = new StringBuffer();

        sql.append("select * from ");
        sql.append(clazz.getSimpleName());
        sql.append(" where ");
        for(Field field:fields) {
            if("ID".equals(field.getName())) {
                sql.append(field.getName());
            }
        }
        sql.append("=");

        for(Field field:fields) {
            if("ID".equals(field.getName())) {
                field.setAccessible(true);
                Object value = field.get(entity);
                String valueString="'"+value+"'";
                sql.append(valueString);
            }
        }
        return sql.toString();
    }
}
