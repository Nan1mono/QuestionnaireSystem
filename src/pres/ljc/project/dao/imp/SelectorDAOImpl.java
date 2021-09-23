package pres.ljc.project.dao.imp;

import pres.ljc.project.dao.SelectorDAO;
import pres.ljc.project.pojo.Selector;
import pres.ljc.project.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SelectorDAOImpl implements SelectorDAO {
    private  Connection conn = null;
    private  PreparedStatement pst = null;
    private  ResultSet rs = null;
    /**
     * 根据fieldId查询selector
     * @param fieldId
     * @return 返回查询到的集合
     */
    @Override
    public List<Selector> listSelector(Long fieldId) {
        String select = "select * from selector where field_id=?";
        List<Selector> selectorList = new ArrayList<>();
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setLong(1,fieldId);
            rs = pst.executeQuery();
            while (rs.next()){
                Long selectorId = rs.getLong(1);
                String selectorText = rs.getString(2);
                selectorList.add(new Selector(selectorId, selectorText, fieldId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return selectorList;
    }
}
