package pres.ljc.project.service.imp;

import pres.ljc.project.dao.AnswerDAO;
import pres.ljc.project.dao.PaperDAO;
import pres.ljc.project.factory.DAOFactory;
import pres.ljc.project.pojo.Answer;
import pres.ljc.project.pojo.Paper;
import pres.ljc.project.service.PaperService;

import java.util.List;

public class PaperServiceImpl implements PaperService {
    private PaperDAO paperDAO = DAOFactory.getPaperDAO();

    /**
     * 更新或插入paper对象，通过paperId判断
     * 如果paperId = -1 代表paper不存在，需要插入paper对象
     * 如果paperId != -1 代表paper已经存在，那就进行修改操作
     * @return 操作成功返回paperId，失败返回-1
     */
    @Override
    public Long exeUpdatePaper(Paper paper) {
        Long paperId = -1L;
        if (paperId.equals(paper.getPaperId())) {
            paperId = paperDAO.insertPaper(paper);// 如果paperId为-1 执行插入操作
        } else {
            paperId = paperDAO.updatePaper(paper);// 如果paperId不为-1 执行修改操作
        }
        return paperId;
    }

    /**
     * 根据pageNum和size分页查询paper，实现表格
     * @param pageNum
     * @param size
     * @return 失败返回null， 成功返回paper集合
     */
    public List<Paper> listPaperByPage(int pageNum,int size) {
        List<Paper> papers = null;
        papers = paperDAO.listPaperByPage(pageNum,size);
        return papers;
    }

    /**
     * 模糊搜素
     * 成功返回模糊搜素到的集合，失败返回null
     */
    public List<Paper> listPaperFuzzy(int pageNum, int size, String title, int status, String createdTime, String endTime) {
        List<Paper> papers = null;
        papers = paperDAO.listPaperByFuzzy(pageNum, size, title, status, createdTime, endTime);
        return papers;
    }

    /**
     * 根据paperId删除paper
     * @param paperId
     * @return
     */
    @Override
    public Long deletePaper(Long paperId) {
        Long id = paperDAO.deletePaper(paperId);
        return id;
    }

    /**
     * 根据paperId查询paper
     * 成功返回paper对象，失败返回null
     */
    public Paper getPaperById(Long paperId){
        Paper paper = paperDAO.getPaperById(paperId);
        return paper;
    }

    /**
     * 将answerList插入到数据库 返回插入的行数，并更新paper_count 记录已经提交调查问卷的人数
     * 这一步，将insertAnswerList和addSubCount合并为了一步
     * @param paperId
     * @param answerList
     */
    @Override
    public void subPaper(Long paperId, List<Answer> answerList) {
        int i = 0;
        AnswerDAO answerDAO = DAOFactory.getAnswerDAO();
        i = answerDAO.insertAnswerList(answerList);
        if (i > 0) paperDAO.addSubCount(paperId, i);
    }

    /**
     * 改变指定paper的状态
     * @param paperId
     * @param status
     * @return
     */
    @Override
    public int updatePaperStatus(Long paperId, int status) {
        return paperDAO.updatePaperStatus(paperId, status);
    }

    /**
     * 查询paper总数
     * @return
     */
    @Override
    public int paperTotal() {
        return paperDAO.paperTotal();
    }

    /**
     * 查询带条件的paper总数
     * @param title
     * @param status
     * @param createdTime
     * @param endTime
     * @return
     */
    @Override
    public int paperTotalByFuzzy(String title, int status, String createdTime, String endTime) {
        return paperDAO.paperTotalByFuzzy(title, status, createdTime, endTime);
    }


}
