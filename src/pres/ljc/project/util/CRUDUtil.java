package pres.ljc.project.util;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtil {

    /**
     * 添加\删除\修改 一条数据
     * @param sql
     * @param params
     * @return 返回收影响的行数
     */
    public static int exeUpdate(String sql,Object...params) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(sql);
            for (int i = 0;i < params.length;i++){
                pst.setObject((i+1),params[i]);
            }
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return result;
    }

    /**
     * 查询一条数据
     * @param sql
     * @param params
     * @return 查询成功返回数据对象，查询失败返回null
     */
    public static <T> T select(String sql, Class<T> tClass, Object... params){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(sql);
            for (int i = 0;i < params.length;i++){
                pst.setObject((i+1),params[i]);
            }
            rs = pst.executeQuery();
            return getBean(rs,tClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return null;
    }

    /**
     * 查询多个数据并将查询结果作为集合返回
     * @param sql
     * @param params
     * @return 查询成功返回对象集合，查询失败返回null
     */
    public static <T> List<T> listSelect(String sql, Class<T> tClass, Object...params){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(sql);
            for (int i = 0;i < params.length;i++){
                pst.setObject((i+1),params[i]);
            }
            rs = pst.executeQuery();
            return getBeans(rs,tClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return null;
    }


    /**
     * 单条resultSet反射转实体类
     * @return 返回这个数据的指定对象
     */
    private static <T> T getBean(ResultSet resultSet, Class<T> className) {
        T instance = null;
        try {
            instance = className.newInstance();
            Field fields[] = className.getDeclaredFields();
            while(resultSet.next()){
                for (Field field : fields) {
                    Object result = resultSet.getObject(field.getName());
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    field.set(instance, result);
                    field.setAccessible(flag);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }


    /**
     * 单条resultSet反射转实体类集合
     * @return 返回这个数据的指定对象集合
     */
    private static <T> List<T> getBeans(ResultSet resultSet, Class<T> className) {
        List<T> list = new ArrayList<T>();
        Field fields[] = className.getDeclaredFields();
        try {
            while (resultSet.next()) {
                T instance = className.newInstance();
                for (Field field : fields) {
                    Object result = resultSet.getObject(field.getName());
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    field.set(instance, result);
                    field.setAccessible(flag);
                }
                list.add(instance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

}
