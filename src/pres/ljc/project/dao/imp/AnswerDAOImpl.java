package pres.ljc.project.dao.imp;

import pres.ljc.project.dao.AnswerDAO;
import pres.ljc.project.pojo.Answer;
import pres.ljc.project.util.CRUDUtil;
import pres.ljc.project.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAOImpl implements AnswerDAO {
    private  Connection conn = null;
    private  PreparedStatement pst = null;
    private  ResultSet rs = null;

    /**
     * 将一组answer数据集合（一个paper有多个field，所以也会有多个answer）插入数据库，返回受影响的行数
     * @param answerList
     * @return 成功返回！0 失败返回0
     */
    @Override
    public int insertAnswerList(List<Answer> answerList) {
        String insert = "insert into answer (answer_text,answer_time,field_id,paper_id,group_id) values (?,?,?,?,?)";
        int i = 0;
        for (Answer answer : answerList){
            i = CRUDUtil.exeUpdate(insert, answer.getAnswerText(), answer.getAnswerTime(), answer.getFieldId(), answer.getPaperId(), answer.getGroupId());
        }
        return i;
    }

    /**
     * 根据paperId返回一组answer，以集合形式
     * @param paperId
     * @return
     */
    @Override
    public List<Answer> ListAnswerByPaperId(Long paperId, int pageNum, int size) {
        String select = "select * from answer where paper_id=? order by group_id limit ?,?";
        List<Answer> answerList = new ArrayList<>();
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setLong(1, paperId);
            pst.setInt(2, (pageNum - 1) * size);
            pst.setInt(3, size);
            rs = pst.executeQuery();
            while (rs.next()) {
                Long answerId = rs.getLong(1);
                String answerText = rs.getString(2);
                String answerTime = rs.getString(3);
                Long fieldId = rs.getLong(5);
                String groupId = rs.getString(6);
                answerList.add(new Answer(answerId, answerText, answerTime, paperId, fieldId, groupId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return answerList;
    }

    /**
     * 查找指定Id的问卷的所有回答信息的总数
     * @param paperId
     * @return
     */
    @Override
    public int countAnswerByPaperId(Long paperId) {
        String select = "select count(*) from answer where paper_id = ?";
        int count = 0;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setLong(1,paperId);
            rs = pst.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return count;
    }

    /**
     * 根据groupId删除一组answer
     * @param groupId
     * @return 成功返回!0
     */
    @Override
    public int deleteAnswerByGroupId(String groupId) {
        String delete = "delete from answer where group_id = ?";
        int i = 0;
//        int i = 0;
//        i = CRUDUtil.exeUpdate(delete, groupId);
//        return i;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(delete);
            pst.setString(1,groupId);
            i = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return i;
    }
}
