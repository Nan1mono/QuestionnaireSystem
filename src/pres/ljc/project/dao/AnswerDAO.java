package pres.ljc.project.dao;

import pres.ljc.project.pojo.Answer;

import java.util.List;

public interface AnswerDAO {
    /**
     * 将一组answer数据集合插入到数据库
     */
    int insertAnswerList(List<Answer> answerList);

    /**
     * 根据paperId查询answer数据集合
     */
    List<Answer> ListAnswerByPaperId(Long paperId, int pageNum, int size);

    /**
     * 根据paperId查询响应的answer总数
     * @param paperId
     * @return
     */
    int countAnswerByPaperId(Long paperId);

    /**
     * 根据groupId删除一组answer
     * @param groupId
     * @return
     */
    int deleteAnswerByGroupId(String groupId);
}
