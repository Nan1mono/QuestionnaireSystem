package pres.ljc.project.dao;

import pres.ljc.project.pojo.User;

import java.util.List;

public interface UserDAO {
    /**
     * 登录接口
     * @param userName
     * @return 返回指定userName的userPass
     */
    String userLogin(String userName);

    /**
     * 注册接口
     * @param userName
     * @param userPass
     * @return
     */
    String registerUser(String userName, String userPass);

    /**
     * 得到所有user的集合
     */
    List<User> listUser(int pageNum, int size);

    /**
     * 查询user总数
     */
    int userTotal();

    /**
     * 根据userId删除一个user
     * @param userId
     * @return
     */
    int deleteUserById(String userId);

    /**
     * 根据userId修改userName
     * @param userId
     * @param userName
     * @return
     */
    int updateUserById(String userId, String userName);
}
