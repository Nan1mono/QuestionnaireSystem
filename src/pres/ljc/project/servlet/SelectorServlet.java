package pres.ljc.project.servlet;

import pres.ljc.project.factory.ServiceFactory;
import pres.ljc.project.pojo.Selector;
import pres.ljc.project.service.SelectorService;
import pres.ljc.project.util.JSONUtil;
import pres.ljc.project.util.StResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/listSelector"})
public class SelectorServlet extends HttpServlet {
    private SelectorService selectorService = ServiceFactory.getSelectorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String uri = req.getRequestURI();
        if (uri.endsWith("listSelector")){
            listSelector(req,resp);
        }
    }

    /**
     * 根据fieldId查询selector集合
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void listSelector(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long fieldId = Long.parseLong(req.getParameter("fieldId"));
        StResult result = null;
        List<Selector> selectorList = selectorService.listSelector(fieldId);
        if (selectorList != null){
            result = StResult.ok(selectorList);
        } else {
            result = StResult.fail("查询出错了");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }
}
