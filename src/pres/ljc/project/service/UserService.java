package pres.ljc.project.service;

import pres.ljc.project.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 登录业务
     * @param userName 用户名
     * @param userPass 密码
     * @return 判断用户名和密码是否匹配 true or false
     */
    boolean userLogin(String userName, String userPass);

    /**
     * 注册用户
     * @param userName
     * @param userPass
     * @return 注册成功返回userId 失败返回0
     */
    String registerUser(String userName, String userPass);

    /**
     * 分页查询user集合
     * @param pageNum
     * @param size
     * @return
     */
    List<User> listUser(int pageNum, int size);

    /**
     * 查询user总数
     * @return
     */
    int userTotal();

    /**
     * 根据userId 删除一个user
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
