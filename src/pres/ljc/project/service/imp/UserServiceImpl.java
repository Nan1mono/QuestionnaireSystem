package pres.ljc.project.service.imp;

import pres.ljc.project.dao.UserDAO;
import pres.ljc.project.factory.DAOFactory;
import pres.ljc.project.pojo.User;
import pres.ljc.project.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = DAOFactory.getUserDAO();

    /**
     * 判断传入的userName 和 userPass 是否匹配
     * @param userName 用户名
     * @param userPass 密码
     * @return
     */
    @Override
    public boolean userLogin(String userName, String userPass) {
        if (userPass.equals(userDAO.userLogin(userName))) {
            return true;
        }
        return false;
    }

    /**
     * 注册用户
     * @param userName
     * @param userPass
     * @return
     */
    @Override
    public String registerUser(String userName, String userPass) {
        String userId = userDAO.registerUser(userName, userPass);
        return userId;
    }

    /**
     * 查询user集合 分页
     */
    public List<User> listUser(int pageNum, int size){
        List<User> userList = null;
        userList = userDAO.listUser(pageNum, size);
        return userList;
    }

    /**
     * 查询user总数
     * @return
     */
    @Override
    public int userTotal() {
        return userDAO.userTotal();
    }

    /**
     * 根据userId删除一个user
     * @param userId
     * @return
     */
    @Override
    public int deleteUserById(String userId) {
        return userDAO.deleteUserById(userId);
    }

    @Override
    public int updateUserById(String userId, String userName) {
        return userDAO.updateUserById(userId, userName);
    }

}
