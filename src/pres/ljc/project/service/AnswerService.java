package pres.ljc.project.service;

import pres.ljc.project.pojo.Answer;
import pres.ljc.project.util.StResult;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    /**
     * 保存一个answer集合到数据库
     */
    int insertAnswerList(List<Answer> answerList);

    /**
     * 根据paperId查询answer集合
     */
    StResult listAnswerByPaperId(Long paperId, int pageNum, int size);

    /**
     * 根据groupId删除一组answer
     * @param groupId
     * @return
     */
    int deleteAnswerByGroupId(String groupId);
}
