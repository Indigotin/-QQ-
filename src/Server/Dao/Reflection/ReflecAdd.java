package Server.Dao.Reflection;

import java.lang.reflect.Field;

public class ReflecAdd {

    public static void main(String[] args) throws Exception {

        Class clazz = Class.forName("edu.cqut.hr.teacher.Teacher");
        Object entity = clazz.newInstance();
        String sql = generateInsertSQL(entity);
        System.out.println(sql);
    }

    public static String generateInsertSQL(Object entity) throws Exception, IllegalAccessException {

        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        //insert into Teacher (Name,Sex,Birth,Salary,Acadamy,System) values ('张三','男','1997-11-10',2000.5,'计算机科学与工程','软件工程')
        StringBuffer sql = new StringBuffer();

        sql.append("insert into ");
        sql.append(clazz.getSimpleName());
        sql.append(" (");
        for(Field field:fields) {
            if("ID".equals(field.getName())) {
                continue;
            }
            sql.append(field.getName()).append(",");
        }
        sql.deleteCharAt(sql.length()-1);

        sql.append(") values (");

        for(Field field:fields) {
            if("ID".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(entity);
            String valueString ="";
            if(value instanceof String) {
                valueString="'"+value+"'";
            }
            else {
                valueString=value.toString();
            }
            sql.append(valueString).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");
        return sql.toString();
    }
}
