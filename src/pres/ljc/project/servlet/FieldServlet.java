package pres.ljc.project.servlet;

import pres.ljc.project.factory.ServiceFactory;
import pres.ljc.project.pojo.Field;
import pres.ljc.project.service.FieldService;
import pres.ljc.project.service.imp.FieldServiceImpl;
import pres.ljc.project.util.Data;
import pres.ljc.project.util.JSONUtil;
import pres.ljc.project.util.StResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/updateField","/deleteField","/listField"})
public class FieldServlet extends HttpServlet {
    private FieldService fieldService = ServiceFactory.getFieldService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String uri = req.getRequestURI();
        if (uri.endsWith("updateField")){
            updateField(req,resp);
        } else if (uri.endsWith("deleteField")){
            deleteField(req,resp);
        } else if (uri.endsWith("listField")){
            listField(req,resp);
        }
    }

    /**
     * 更新或插入field的servlet
     */
    private void updateField(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json;charset=UTF-8");
        // 创建一个返回结果对象，将以json形式传送给前端
        StResult result = null;
        // 强制fieldName不能为空 且插入or更新之前需要保存表单 否则失败
        if (req.getParameter("fieldTitle") == null || "".equals(req.getParameter("fieldTitle"))){
            result = StResult.fail("标题不能为空");
        } else if (Integer.parseInt(req.getParameter("paperId")) == -1){
            System.out.println( Integer.parseInt(req.getParameter("paperId")) );
            result = new StResult(Data.RESPONSE_UNABLE,"请保存问卷");
        } else {
            Long fieldId = -1L;
            try {fieldId = Long.parseLong(req.getParameter("fieldId"));} catch (Exception e) {}
            String fieldName = req.getParameter("fieldTitle");
            int fieldType = Integer.parseInt(req.getParameter("fieldType"));
            // *这里做一个区分 如果是4，5的话，是单选或者多选按钮，需要传多个selectorText  (未处理任务)
            // 准备一个selectors数组，这里面存储从前端获取的所有selectorText的名字
            String[] selectors = null;
            if (req.getParameterValues("selectorText") != null && req.getParameterValues("selectorText").length > 0){
                selectors = req.getParameterValues("selectorText");
            }
            // fieldDesc可以为空
            String fieldDesc = (req.getParameter("fieldDesc") != null) ? req.getParameter("fieldDesc") : "";
            Long paperId = Long.parseLong(req.getParameter("paperId"));
            Field field = new Field();
            field.setFieldId(fieldId);
            field.setFieldName(fieldName);
            field.setFieldDesc(fieldDesc);
            field.setFieldType(fieldType);
            field.setPaperId(paperId);
            // 返回插入or更新之后的结果 成功fieldId 失败-1
            fieldId = fieldService.exeUpdateField(field,selectors);
            if (new Long(-1).equals(fieldId)){
                result = StResult.fail("操作失败");
            } else {
                result = StResult.ok(Long.toString(fieldId));      // 操作成功讲fieldId传给前端
            }
        }
        // 将result转换成json 并发送给前端
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 根据删除field
     */
    private void deleteField(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StResult result = new StResult();
        Long fieldId = "undefined".equals(req.getParameter("fieldId")) ? (new Long(-1)) : Long.parseLong(req.getParameter("fieldId"));
        // 先确定表单域存在才做删除操作，否则不执行
        if (fieldId != -1) {
            int i = 0;
            i = fieldService.deleteField(fieldId);
            if (i != 0){
                result = StResult.ok(Long.toString(fieldId));
            }
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 根据paperId查询field集合
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void listField(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StResult result = null;
        Long paperId = Long.parseLong(req.getParameter("paperId"));
        List<Field> fieldList = fieldService.listFieldByPaperId(paperId);
        if (fieldList != null){
            result = StResult.ok(fieldList);
        } else {
            result = StResult.fail("查询失败");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }
}
