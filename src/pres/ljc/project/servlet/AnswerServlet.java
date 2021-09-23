package pres.ljc.project.servlet;

import pres.ljc.project.factory.ServiceFactory;
import pres.ljc.project.service.AnswerService;
import pres.ljc.project.util.JSONUtil;
import pres.ljc.project.util.StResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deleteAnswer"})
public class AnswerServlet extends HttpServlet {
    private AnswerService answerService = ServiceFactory.getAnswerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String uri = req.getRequestURI();
        if (uri.endsWith("deleteAnswer")){
            deleteAnswer(req, resp);
        }
    }

    /**
     * 删除辉打
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void deleteAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupId = req.getParameter("groupId");
        StResult result = null;
        if (answerService.deleteAnswerByGroupId(groupId) != 0){
            result = StResult.ok("删除成功");
        } else {
            result = StResult.fail("删除失败");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }
}
