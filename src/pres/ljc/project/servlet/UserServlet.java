package pres.ljc.project.servlet;

import pres.ljc.project.factory.ServiceFactory;
import pres.ljc.project.pojo.User;
import pres.ljc.project.service.UserService;
import pres.ljc.project.util.JSONUtil;
import pres.ljc.project.util.StResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/login","/register","/listUser","/deleteUser","/updateUser"})
public class UserServlet extends HttpServlet {
    private UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String uri = req.getRequestURI();
        if (uri.endsWith("login")){
            login(req, resp);
        } else if (uri.endsWith("register")){
            register(req, resp);
        } else if (uri.endsWith("listUser")){
            listUser(req, resp);
        } else if (uri.endsWith("deleteUser")){
            deleteUser(req, resp);
        } else if (uri.endsWith("updateUser")){
            updateUser(req, resp);
        }
    }

    /**
     * 登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取用户名
        String userName = null;
        userName = req.getParameter("userName");
        String userPass = null;
        // 获取密码
        userPass = req.getParameter("userPass");
        StResult result = null;
        // 名字和密码不能为空
        if (userName == null || "".equals(userName) || userPass == null || "".equals(userPass)){
            result = StResult.fail("用户名或密码不能为空");
        } else {
            boolean b = userService.userLogin(userName, userPass);
            if (!b){
                result = StResult.fail("密码错误");
            } else {
                result = StResult.ok("登录成功");
                // 登录成功 生成一个cookie发送到客户端
//                Cookie cookie = new Cookie("userName",userName);
//                cookie.setMaxAge(-1);
//                cookie.setPath("/");
//                resp.addCookie(cookie);
            }
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取用户名
        String userName = null;
        userName = req.getParameter("userName");
        String userPass = null;
        // 获取密码
        userPass = req.getParameter("userPass");
        StResult result = null;
        // 名字和密码不能为空
        if (userName == null || "".equals(userName) || userPass == null || "".equals(userPass)){
            result = StResult.fail("用户名或密码不能为空");
        } else {
            if (userService.registerUser(userName, userPass) == null){
                result = StResult.fail("注册失败，用户名不能重复");
            } else {
                result = StResult.ok("注册成功");
            }
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 查询所有user 并作为集合返回 分页
     */
    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNum = 1;
        int size = 10;
        StResult result = null;
        if (req.getParameter("page") != null && !("".equals(req.getParameter("page")))) pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("size") != null && !("".equals(req.getParameter("size")))) size = Integer.parseInt(req.getParameter("size"));
        // 查询user集合
        List<User> userList = userService.listUser(pageNum,size);
        // 查询user总数
        int total = userService.userTotal();
        if (userList != null && userList.size() > 0){
            result = StResult.ok(userList);
            result.setObj(total);
        } else {
            result = StResult.fail("查询失败");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 删除一个user
     */
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        StResult result = null;
        int i = userService.deleteUserById(userId);
        if (i != 0){
            result = StResult.ok();
        } else {
            result = StResult.fail();
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 根据userId修改userName
     */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        StResult result = null;
        // 如果用户名绝对为空 修改失败
        if (userName == null || "".equals(userName)){
            result = StResult.fail();
        } else {
            int i = userService.updateUserById(userId, userName);
            if ( i != 0){
                result = StResult.ok();
            } else {
                result = StResult.fail();
            }
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }
}
