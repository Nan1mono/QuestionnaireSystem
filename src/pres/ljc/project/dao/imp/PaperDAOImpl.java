package pres.ljc.project.dao.imp;

import pres.ljc.project.dao.PaperDAO;
import pres.ljc.project.pojo.Paper;
import pres.ljc.project.util.CRUDUtil;
import pres.ljc.project.util.DBUtil;
import pres.ljc.project.util.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaperDAOImpl implements PaperDAO {
    private  Connection conn = null;
    private  PreparedStatement pst = null;
    private  ResultSet rs = null;

    @Override
    public Long insertPaper(Paper paper) {
        Long paperId = -1L;
        String insert = "insert into paper (paper_title,paper_desc,paper_created,paper_status,paper_count) values (?,?,?,default,default)";
        try {
            conn = DBUtil.getCon();
            // *这里需要一个特殊处理：关闭自动提交事务     原因：需要获取当前事务中产生的PaperId
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(insert);
            pst.setString(1, paper.getPaperTitle());
            pst.setString(2, paper.getPaperDesc());
            pst.setString(3, paper.getPaperCreated());
            pst.executeUpdate();
            // 获取当前事务中产生的paperId
            String select = "select LAST_INSERT_ID()";
            // 再次编译执行
            pst = conn.prepareStatement(select);
            rs = pst.executeQuery();
            if (rs.next()) paperId = rs.getLong(1);     // 获取当前当前事务中的ID
            conn.commit();                                          // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            // *注意：前面开启的事务出现异常要及时回滚以免线程不能正常结束
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.closeAll(rs,pst,conn);
        }
        return paperId;
    }

    @Override
    public Long updatePaper(Paper paper) {
        Long paperId = -1L;
        String update = "update paper set paper_title=?,paper_desc=?,paper_created=? where paper_id=?";
        int i = 0;
        i = CRUDUtil.exeUpdate(update, paper.getPaperTitle(), paper.getPaperDesc(), DateUtils.getNow(),paper.getPaperId());
        if (i != 0 ) paperId = paper.getPaperId();
        return paperId;
    }

    @Override
    public List<Paper> listPaperByPage(int pageNum, int size) {
        int page = (pageNum - 1) * size;
        List<Paper> papers = new ArrayList<>();
        String  list = "select * from paper where paper_status!=3  limit ?,? ";
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(list);
            pst.setInt(1,page);
            pst.setInt(2,size);
            rs = pst.executeQuery();
            while (rs.next()){
                Long paperId = rs.getLong(1);
                String paperTitle = rs.getString(2);
                String paperDesc = rs.getString(3);
                String paperCreated = rs.getString(4);
                int paperStatus = rs.getInt(5);
                int paperCount = rs.getInt(6);
                papers.add(new Paper(paperId,paperTitle,paperDesc,paperCreated,paperStatus,paperCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return papers;
    }

    @Override
    public List<Paper> listPaperByFuzzy(int pageNum, int size, String title, int status, String createdTime, String endTime) {
        String list = "select * from paper where 1=1";
        // 如果标题不为空，添加上标题条件
        if (title != null && !("".equals(title))){
            list = list + " and paper_title like '%"+title+"%'";
        }
        if (status >= 1 && status <= 3){
            list = list + " and paper_status="+status;
        }
        if (createdTime != null && !("".equals(createdTime))){
            list = list + " and paper_created >= '"+createdTime+"'";
        }
        if (endTime != null && !("".equals(endTime))){
            list = list + " and paper_created <= '"+endTime+"'";
        }
        int page = (pageNum - 1) * size;
        list = list + " limit ?,?";
        List<Paper> papers = new ArrayList<>();
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(list);
            pst.setInt(1,page);
            pst.setInt(2,size);
            rs = pst.executeQuery();
            while (rs.next()){
                Long paperId = rs.getLong(1);
                String paperTitle = rs.getString(2);
                String paperDesc = rs.getString(3);
                String paperCreated = rs.getString(4);
                int paperStatus = rs.getInt(5);
                int paperCount = rs.getInt(6);
                papers.add(new Paper(paperId,paperTitle,paperDesc,paperCreated,paperStatus,paperCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return papers;
    }

    /**
     * 根据paperId删除指定paperId (伪删除)
     * @param paperId
     * @return 删除成功返回paperId 删除失败返回-1
     */
    @Override
    public Long deletePaper(Long paperId) {
        String delete = "update paper set paper_status=3 where paper_id=?";
        int i = 0;
        i = CRUDUtil.exeUpdate(delete, paperId);
        if (i != 0) return paperId;
        else return -1L;
    }

    /**
     * 根据paperId查询paper
     * 查询成功 返回paper对象，失败返回null
     */
    @Override
    public Paper getPaperById(Long paperId) {
        String select = "select * from paper where paper_id=?";
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setLong(1,paperId);
            rs = pst.executeQuery();
            if (rs.next()){
                String paperTitle = rs.getString(2);
                String paperDesc = rs.getString(3);
                String paperCreated = rs.getString(4);
                int paperStatus = rs.getInt(5);
                int paperCount = rs.getInt(6);
                return new Paper(paperId,paperTitle,paperDesc,paperCreated,paperStatus,paperCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return null;
    }

    /**
     * 更新paper的提交人数
     * @param paperId paper的编号
     * @param i 提交的人数
     */
    @Override
    public void addSubCount(Long paperId, int i) {
        String update = "update paper set paper_count = paper_count + ? where paper_id = ?";
        CRUDUtil.exeUpdate(update, i, paperId);
    }

    /**
     * @Description 改变指定paperId的paper的状态  1 开放 2 关闭
     * @param paperId
     * @param status
     * @return
     */
    @Override
    public int updatePaperStatus(Long paperId, int status) {
        String update = "update paper set paper_status = ? where paper_id = ?";
        int i = 0;
        i = CRUDUtil.exeUpdate(update, status, paperId);
        return i;
    }

    @Override
    public int paperTotal() {
        String select = "select count(*) from paper";
        int total = 0;
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            rs = pst.executeQuery();
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return total;
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
        String select = "select count(*) from paper where 1=1";
        int total = 0;
        // 如果标题不为空，添加上标题条件
        if (title != null && !("".equals(title))){
            select = select + " and paper_title like '%"+title+"%'";
        }
        if (status >= 1 && status <= 3){
            select = select + " and paper_status="+status;
        }
        if (createdTime != null && !("".equals(createdTime))){
            select = select + " and paper_created >= '"+createdTime+"'";
        }
        if (endTime != null && !("".equals(endTime))){
            select = select + " and paper_created <= '"+endTime+"'";
        }
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            rs = pst.executeQuery();
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pst, conn);
        }
        return total;
    }
}
