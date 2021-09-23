package pres.ljc.project.service;

import pres.ljc.project.pojo.Answer;
import pres.ljc.project.pojo.Paper;

import java.util.List;

/**
 * 这里是Paper的服务层接口，在这里调用FieldDAO层实现业务处理操作
 */
public interface PaperService {
    /**
     * 更新或插入paper对象，通过paperId判断
     * 如果paperId = -1 代表paper不存在，需要插入paper对象
     * 如果paperId != -1 代表paper已经存在，那就进行修改操作
     * @return 操作成功返回paperId，失败返回-1L
     */
    Long exeUpdatePaper(Paper paper);

    /**
     * 根据pageNum和size查询表格
     * @param pageNu
     * @param size
     * @return
     */
    List<Paper> listPaperByPage(int pageNu, int size);

    /**
     * 模糊搜素
     * 成功返回模糊搜素到的集合，失败返回null
     */
    List<Paper> listPaperFuzzy(int pageNum, int size, String title, int status, String createdTime, String endTime);

    /**
     * 根据paperId删除paper 成功返回paperId 失败返回-1
     */
    Long deletePaper(Long paperId);

    /**
     * 根据paperId查询paper对象
     */
    Paper getPaperById(Long paperId);

    /**
     * （这里比较特殊，我在AnswerService中有insertAnswerList的实现，这里的目的是插入要给answerList就将paper_count+1 ，后面的servlet主要使用这个，但我对那个insertAnswerList做了保留）
     * （如果有别的需求要单独插入，可以查看AnswerService中的insertAnswerList）
     * @param paperId
     * @param answerList
     */
    void subPaper(Long paperId, List<Answer> answerList);

    /**
     * 改变指定paper的状态
     * @param paperId
     * @param status
     * @return
     */
    int updatePaperStatus(Long paperId, int status);

    /**
     * 查询paper总数
     */
    int paperTotal();

    /**
     * 查询带条件的paper总数
     */
    int paperTotalByFuzzy(String title, int status, String createdTime, String endTime);
}
