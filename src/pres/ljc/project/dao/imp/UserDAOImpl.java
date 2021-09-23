package pres.ljc.project.dao.imp;

import pres.ljc.project.dao.UserDAO;
import pres.ljc.project.pojo.User;
import pres.ljc.project.util.CRUDUtil;
import pres.ljc.project.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAOImpl implements UserDAO {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    /**
     * 用户登录接口判断
     * @param userName
     * @return 返回指定userName的userPass 不存在返回Null
     */
    @Override
    public String userLogin(String userName) {
        String select = "select user_pass from user where user_name = ?";
        String userPass = null;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setString(1,userName);
            rs = pst.executeQuery();
            if (rs.next()) userPass = rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return userPass;
    }

    /**
     * 注册一个用户
     * @param userName
     * @param userPass
     * @return 注册成功，返回userId，注册失败返回0
     */
    @Override
    public String registerUser(String userName, String userPass) {
        String insert = "insert into user values (?, ?, ?)";
        String  userId = null;
        try {
            conn = DBUtil.getCon();
            // 关闭自动提交事务
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(insert);
            pst.setString(1, UUID.randomUUID().toString().replace("-",""));
            pst.setString(2,userName);
            pst.setString(3,userPass);
            pst.executeUpdate();
            String select = "select LAST_INSERT_ID()";
            pst = conn.prepareStatement(select);
            rs = pst.executeQuery();
            if (rs.next()) userId = rs.getString(1);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeAll(rs, pst, conn);
        }
        return userId;
    }

    /**
     * 查询所有user并返回他们的集合
     * @return
     */
    @Override
    public List<User> listUser(int pageNum, int size) {
        String select = "select * from user limit ?,?";
        List<User> userList = new ArrayList<>();
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setInt(1,(pageNum-1)*size);
            pst.setInt(2,size);
            rs = pst.executeQuery();
            while (rs.next()){
                String userId = rs.getString(1);
                String userName = rs.getString(2);
                String userPass = rs.getString(3);
                userList.add(new User(userId, userName, userPass));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return userList;
    }

    /**
     * 查询user总数
     * @return
     */
    @Override
    public int userTotal() {
        int total = 0;
        String select = "select count(*) from user";
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            rs = pst.executeQuery();
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return total;
    }

    /**
     * 根据userId删除一个user
     * @param userId
     * @return
     */
    @Override
    public int deleteUserById(String userId) {
        String delete = "delete from user where user_id = ?";
        int i = 0;
        i = CRUDUtil.exeUpdate(delete, userId);
        return i;
    }

    /**
     * 根据userId修改userName
     * @param userId
     * @param userName
     * @return
     */
    @Override
    public int updateUserById(String userId, String userName) {
        String update = "update user set user_name = ? where user_id = ?";
        int i = 0;
        i = CRUDUtil.exeUpdate(update, userName, userId);
        return i;
    }
}
