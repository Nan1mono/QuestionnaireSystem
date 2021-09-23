package pres.ljc.project.dao;

import pres.ljc.project.pojo.Paper;

import java.util.List;

/**
 * 这里是PaperDAO层接口，负责数据库增删改查操作
 */
public interface PaperDAO {
    /**
     * 传入的paper保存至数据库，成功返回当前paperId，失败返回-1
     */
    Long insertPaper(Paper paper);

    /**
     * 修改paper的信息，成功返回当前的paperId，失败返回-1
     */
    Long updatePaper(Paper paper);

    /**
     * 分页查询paper
     * @param pageNum
     * @param size
     * @return 查询成功返回paper集合，失败返回null
     */
    List<Paper> listPaperByPage(int pageNum,int size);

    /**
     * 不确定查询，实现模糊搜索
     */
    List<Paper> listPaperByFuzzy(int pageNum, int size, String title, int status, String createdTime, String endTime);

    /**
     * 根据paperId删除
     * 删除成功返回paperId 失败返回-1
     */
    Long deletePaper(Long paperId);

    /**
     * 根据paperId查询paper
     * 查询成功 返回paper对象，失败返回null
     */
    Paper getPaperById(Long paperId);

    /**
     * 修改paper的提交人数
     * @param paperId paper的编号
     * @param i 提交的人数
     */
    void addSubCount(Long paperId, int i);

    /**
     * @Description 改变指定paperId的paper的状态  1 开放 2 关闭
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
