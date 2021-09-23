package pres.ljc.project.servlet;

import pres.ljc.project.factory.ServiceFactory;
import pres.ljc.project.pojo.Answer;
import pres.ljc.project.pojo.Paper;
import pres.ljc.project.service.AnswerService;
import pres.ljc.project.service.PaperService;
import pres.ljc.project.util.DateUtils;
import pres.ljc.project.util.JSONUtil;
import pres.ljc.project.util.StResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

@WebServlet(urlPatterns = {"/updatePaper","/listPaperByPage","/listPaperFuzzy","/deletePaper","/getPaper","/getGroupId","/sub","/answerList","/updatePaperStatus"})
public class PaperServlet extends HttpServlet {
    private PaperService paperService = ServiceFactory.getPaperService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String uri = req.getRequestURI();
        if (uri.endsWith("updatePaper")){
            updatePaper(req, resp);
        } else if (uri.endsWith("listPaperByPage")){
            listPaperByPage(req, resp);
        } else if (uri.endsWith("listPaperFuzzy")){
            listPaperFuzzy(req, resp);
        } else if (uri.endsWith("deletePaper")){
            deletePaper(req, resp);
        } else if (uri.endsWith("getPaper")){
            getPaper(req, resp);
        } else if (uri.endsWith("getGroupId")){
            getGroupId(req, resp);
        } else if (uri.endsWith("sub")){
            sub(req, resp);
        } else if (uri.endsWith("answerList")){
            answerList(req, resp);
        } else if (uri.endsWith("updatePaperStatus")){
            updatePaperStatus(req, resp);
        }
    }

    /**
     * 更新或插入paper的servlet
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updatePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json;charset=UTF-8");
        // 创建一个返回结果对象，将以json形式传送给前端
        StResult result = new StResult();
        // 强制标题不能为空
        if (req.getParameter("paperTitle") == null || "".equals(req.getParameter("paperTitle"))) {
            result = StResult.fail("标题不能为空");
        } else {
            Long paperId = Long.parseLong(req.getParameter("paperId"));
            String paperTitle = req.getParameter("paperTitle");
            // *这里做一个处理：PaperDesc可以为空
            String paperDesc = (req.getParameter("paperDesc") != null) ? req.getParameter("paperDesc") : "";
            String paperCreated = DateUtils.getNow();           // 获得当前时间
            Paper paper = new Paper();
            paper.setPaperId(paperId);
            paper.setPaperTitle(paperTitle);
            paper.setPaperDesc(paperDesc);
            paper.setPaperCreated(paperCreated);
            // 返回插入or更新后的结果 -1 失败 paperId 成功
            paperId = paperService.exeUpdatePaper(paper);
            if (new Long(-1).equals(paperId)){
                result = StResult.fail("操作失败");
            } else {
                result = StResult.ok(Long.toString(paperId));       // 插入or更新成功后将paperId传送给前端
            }
        }
        // 将result转换成json 并发送给前端
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 分页查询Paper
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void listPaperByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StResult result = new StResult();
        int pageNum =  1;
        int size = 10;
        if (req.getParameter("page") != null && !("".equals(req.getParameter("page")))) pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("size") != null && !("".equals(req.getParameter("size")))) size = Integer.parseInt(req.getParameter("size"));
        // 查询paper列表
        List<Paper> papers =paperService.listPaperByPage(pageNum,size);
        // 查询paper总数
        int total = paperService.paperTotal();
        result = StResult.ok(papers);
        result.setObj(total);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 不确定条件的模糊搜索
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void listPaperFuzzy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        StResult result = new StResult();
        int pageNum = 1;
        int size = 10;
        String title = null;
        int status = 0;
        String startTime = null;
        String endTime = null;
        if (req.getParameter("page") != null && !("".equals(req.getParameter("page")))) pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("size") != null && !("".equals(req.getParameter("size")))) size = Integer.parseInt(req.getParameter("size"));
        try {
            title = req.getParameter("paperTitle");
            status = Integer.parseInt(req.getParameter("paperStatus"));
            startTime = req.getParameter("startTime");
            endTime = req.getParameter("endTime");
        } catch (Exception e) {}
        // 查询规定条件下的paper集合
        List<Paper> papers = paperService.listPaperFuzzy(pageNum,size,title,status,startTime,endTime);
        // 查询规定条件下的paper总数
        int total = paperService.paperTotalByFuzzy(title,status,startTime,endTime);
        result = StResult.ok(papers);
        result.setObj(total);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 根据paperId删除paper
     */
    private void deletePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Long paperId = -1L;
        StResult result = new StResult();
        try { paperId = Long.parseLong(req.getParameter("paperId")); } catch (Exception e) {}
        paperId = paperService.deletePaper(paperId);
        if (paperId == -1L){
            result = StResult.fail("删除失败");
        }else {
            result = StResult.ok(Long.toString(paperId));
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 根据paperId查询paper
     */
    private void getPaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Long paperId = Long.parseLong(req.getParameter("paperId"));
        StResult result = null;
        Paper paper = paperService.getPaperById(paperId);
        if (paper != null){
            result = StResult.ok();
            result.setObj(paper);
        } else {
            result = StResult.fail("获得问卷失败");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 利用UUID生成一个groupId
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void getGroupId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String groupId = UUID.randomUUID().toString().replace("-","");
        // Cookie cookie = new Cookie("groupId",groupId);
        // 将cookie发送到客户端
        // resp.addCookie(cookie);
        StResult result = null;
        result = StResult.ok(groupId);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 从表单获取answerList 然后按规定格式存到数据库中
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void sub(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 准一个answer集合，将提交的所有answer和值全部存进去
        List<Answer> answerList = new ArrayList<>();
        // 获取提交的所有表单的
        Enumeration<String> names = req.getParameterNames();
        // 给定默认值(其实用不上，但是为了容错率)
        Long paperId = -1L;
        String groupId = null;
        while (names.hasMoreElements()){
            String name = names.nextElement();
            if (name.equals("paperId")){                // 获得paperId的值
                paperId = Long.parseLong(req.getParameter(name));
            }else if (name.equals("groupId")){          // 获得groupId的值
                groupId = req.getParameter(name);
            }else {                                     // 获得单行文本框 多行文本框 单选按钮 多选按钮的值 统一处理成规定格式
                // 前端传输格式为： xxx_xxx  类型_fieldId
                // 我们用“_”分隔，第二个参数就是fieldId
                Long fieldId = Long.parseLong(name.split("_")[1]);
                // 直接去values数组 不用区分是否是单选/多选按钮或者是单行/多行文本框
                String[] texts = req.getParameterValues(name);
                // 一个“中转站” 将表单信息以 value & value & value 形式拼接
                StringBuilder sb = new StringBuilder();
                for (String text : texts){
                    sb.append(text);
                    sb.append("&");
                }
                // 删除最后一个 &
                sb.deleteCharAt(sb.length()-1);
                Answer answer = new Answer();
                answer.setAnswerText(sb.toString());
                answer.setPaperId(paperId);
                answer.setFieldId(fieldId);
                answer.setGroupId(groupId);
                answer.setAnswerTime(DateUtils.getNow());
                answerList.add(answer);
            }
        }
        paperService.subPaper(paperId, answerList);
        resp.getWriter().write(JSONUtil.objectToJson(StResult.ok("提交成功")));
    }

    /**
     * 查看已经提交的问卷的数据详情
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void answerList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StResult result = null;
        Long paperId = Long.parseLong(req.getParameter("paperId"));
        int pageNum = 1;
        int size = 10;
        try{
            pageNum = Integer.parseInt(req.getParameter("page"));
            size = Integer.getInteger(req.getParameter("size"));
        } catch (Exception e){}
        AnswerService answerService = ServiceFactory.getAnswerService();
        result = answerService.listAnswerByPaperId(paperId,pageNum,size);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * 更新paper的状态
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updatePaperStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long paperId = Long.parseLong(req.getParameter("paperId"));
        int status = Integer.parseInt(req.getParameter("paperStatus"));
        StResult result = null;
        int i = paperService.updatePaperStatus(paperId, status);
        if (i != 0){
            result = StResult.ok("ok");
        } else {
            result = StResult.fail("fail");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }
}
