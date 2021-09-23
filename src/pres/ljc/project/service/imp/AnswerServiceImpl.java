package pres.ljc.project.service.imp;

import pres.ljc.project.dao.AnswerDAO;
import pres.ljc.project.dao.FieldDAO;
import pres.ljc.project.factory.DAOFactory;
import pres.ljc.project.factory.ServiceFactory;
import pres.ljc.project.pojo.Answer;
import pres.ljc.project.pojo.Field;
import pres.ljc.project.service.AnswerService;
import pres.ljc.project.service.FieldService;
import pres.ljc.project.util.StResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerServiceImpl implements AnswerService {
    private AnswerDAO answerDAO = DAOFactory.getAnswerDAO();
    /**
     * 保存一个answer集合到数据库
     * @param answerList
     * @return 返回受影响的行数 失败返回0
     */
    @Override
    public int insertAnswerList(List<Answer> answerList) {
        int i = 0;
        i = answerDAO.insertAnswerList(answerList);
        return i;
    }

    /**
     * 根据paperId查询answer集合
     * @param paperId
     * @return
     */
    @Override
    public StResult listAnswerByPaperId(Long paperId, int pageNum, int size) {
        // 1 准备一个集合存放 answerList
        List<Answer> answerList = answerDAO.ListAnswerByPaperId(paperId,pageNum,size);
        // 2 准备一个集合存放 answerList中不同的field
        FieldDAO fieldDAO = DAOFactory.getFieldDAO();
        List<Field> fieldList = fieldDAO.listFieldByPaperId(paperId);
        FieldService fieldService = ServiceFactory.getFieldService();
        // 3 准备一个集合存放，所有answer查询结果
        List<Map> result = new ArrayList<>();

        // 如果answerList中有值，再做处理
        while (answerList.size() > 0){
            // 准备一个Map 将answerList中的每个answer以 key : value 的形式放到里面
            Map answerMap = new HashMap();
            // 将数据转换成前端指定的格式
            // 取出一个groupId 以这个groupId为基准，将所有与之相同的groupId的answer组成一个Map
            String groupId = answerList.get(0).getGroupId();
            for (int i = 0; i < answerList.size(); i++) {
                Answer answer = answerList.get(i);
                // 先判断当前answer中的groupId是否相同，确定为同一个用户
                if (answer.getGroupId().equals(groupId)){
                    // 将这个answer对象从answerList中先删除，以为他接下来即将被安排 同时也是为了跳出while循环
                    answerList.remove(i);
                    i--;
                    // 设定key的值
                    String key = "";
                    // 针对不同类型的field 生成不同类型的field
                    switch (fieldService.getFieldType(answer.getFieldId(),fieldList)){
                        // 生成指定格式，格式可以看看PaperServlet中的sub方法
                        // 单行文本框
                        case 2:
                            key = "text_"+answer.getFieldId();
                            break;
                        // 多行文本框
                        case 3:
                            key = "textarea_"+answer.getFieldId();
                            break;
                        // 单选按钮
                        case 4:
                            key = "radio_"+answer.getFieldId();
                            break;
                        // 多选按钮
                        case 5:
                            key = "checkbox_"+answer.getFieldId();
                    }
                    // 将这个field以存入answerMap中
                    answerMap.put(key, answer.getAnswerText());
                    answerMap.put("answerTime", answer.getAnswerTime());
                    answerMap.put("groupId",answer.getGroupId());
                    // 当answerMap的大小和fieldList一样时，就代表所有的field都存进去了，可以进去下一个answer对象了
                    if (answerMap.size()-2 == fieldList.size()) break;
                }
            }
            // 将这个answer对象存入result中
            result.add(answerMap);
        }
        StResult stResult = StResult.ok();
        stResult.setData(result);
        // 查询总数
        int total = answerDAO.countAnswerByPaperId(paperId);
        stResult.setObj(Integer.toString(total));
        return stResult;
    }

    /**
     * 根据groupId删除一组answer
     * @param groupId
     * @return
     */
    @Override
    public int deleteAnswerByGroupId(String groupId) {
        int i = answerDAO.deleteAnswerByGroupId(groupId);
        return i;
    }
}
