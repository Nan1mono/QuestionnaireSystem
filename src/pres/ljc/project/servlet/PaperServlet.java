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
     * ???????????????paper???servlet
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updatePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json;charset=UTF-8");
        // ???????????????????????????????????????json?????????????????????
        StResult result = new StResult();
        // ????????????????????????
        if (req.getParameter("paperTitle") == null || "".equals(req.getParameter("paperTitle"))) {
            result = StResult.fail("??????????????????");
        } else {
            Long paperId = Long.parseLong(req.getParameter("paperId"));
            String paperTitle = req.getParameter("paperTitle");
            // *????????????????????????PaperDesc????????????
            String paperDesc = (req.getParameter("paperDesc") != null) ? req.getParameter("paperDesc") : "";
            String paperCreated = DateUtils.getNow();           // ??????????????????
            Paper paper = new Paper();
            paper.setPaperId(paperId);
            paper.setPaperTitle(paperTitle);
            paper.setPaperDesc(paperDesc);
            paper.setPaperCreated(paperCreated);
            // ????????????or?????????????????? -1 ?????? paperId ??????
            paperId = paperService.exeUpdatePaper(paper);
            if (new Long(-1).equals(paperId)){
                result = StResult.fail("????????????");
            } else {
                result = StResult.ok(Long.toString(paperId));       // ??????or??????????????????paperId???????????????
            }
        }
        // ???result?????????json ??????????????????
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * ????????????Paper
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
        // ??????paper??????
        List<Paper> papers =paperService.listPaperByPage(pageNum,size);
        // ??????paper??????
        int total = paperService.paperTotal();
        result = StResult.ok(papers);
        result.setObj(total);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * ??????????????????????????????
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
        // ????????????????????????paper??????
        List<Paper> papers = paperService.listPaperFuzzy(pageNum,size,title,status,startTime,endTime);
        // ????????????????????????paper??????
        int total = paperService.paperTotalByFuzzy(title,status,startTime,endTime);
        result = StResult.ok(papers);
        result.setObj(total);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * ??????paperId??????paper
     */
    private void deletePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Long paperId = -1L;
        StResult result = new StResult();
        try { paperId = Long.parseLong(req.getParameter("paperId")); } catch (Exception e) {}
        paperId = paperService.deletePaper(paperId);
        if (paperId == -1L){
            result = StResult.fail("????????????");
        }else {
            result = StResult.ok(Long.toString(paperId));
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * ??????paperId??????paper
     */
    private void getPaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Long paperId = Long.parseLong(req.getParameter("paperId"));
        StResult result = null;
        Paper paper = paperService.getPaperById(paperId);
        if (paper != null){
            result = StResult.ok();
            result.setObj(paper);
        } else {
            result = StResult.fail("??????????????????");
        }
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * ??????UUID????????????groupId
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void getGroupId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String groupId = UUID.randomUUID().toString().replace("-","");
        // Cookie cookie = new Cookie("groupId",groupId);
        // ???cookie??????????????????
        // resp.addCookie(cookie);
        StResult result = null;
        result = StResult.ok(groupId);
        resp.getWriter().write(JSONUtil.objectToJson(result));
    }

    /**
     * ???????????????answerList ???????????????????????????????????????
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void sub(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ?????????answer???????????????????????????answer?????????????????????
        List<Answer> answerList = new ArrayList<>();
        // ??????????????????????????????
        Enumeration<String> names = req.getParameterNames();
        // ???????????????(???????????????????????????????????????)
        Long paperId = -1L;
        String groupId = null;
        while (names.hasMoreElements()){
            String name = names.nextElement();
            if (name.equals("paperId")){                // ??????paperId??????
                paperId = Long.parseLong(req.getParameter(name));
            }else if (name.equals("groupId")){          // ??????groupId??????
                groupId = req.getParameter(name);
            }else {                                     // ????????????????????? ??????????????? ???????????? ?????????????????? ???????????????????????????
                // ???????????????????????? xxx_xxx  ??????_fieldId
                // ????????????_?????????????????????????????????fieldId
                Long fieldId = Long.parseLong(name.split("_")[1]);
                // ?????????values?????? ???????????????????????????/???????????????????????????/???????????????
                String[] texts = req.getParameterValues(name);
                // ????????????????????? ?????????????????? value & value & value ????????????
                StringBuilder sb = new StringBuilder();
                for (String text : texts){
                    sb.append(text);
                    sb.append("&");
                }
                // ?????????????????? &
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
        resp.getWriter().write(JSONUtil.objectToJson(StResult.ok("????????????")));
    }

    /**
     * ??????????????????????????????????????????
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
     * ??????paper?????????
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
