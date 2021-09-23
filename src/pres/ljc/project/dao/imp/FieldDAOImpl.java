package pres.ljc.project.dao.imp;

import pres.ljc.project.dao.FieldDAO;
import pres.ljc.project.pojo.Field;
import pres.ljc.project.util.CRUDUtil;
import pres.ljc.project.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FieldDAOImpl implements FieldDAO {
    private  Connection conn = null;
    private  PreparedStatement pst = null;
    private  ResultSet rs = null;

    /**
     * 插入Field
     * @param field
     * @return 成功field 失败-1L
     */
    @Override
    public Long insertField(Field field, String[] selectors) {
        String insert = "insert into field (field_name,field_type,field_desc,field_status,paper_id) values (?,?,?,default,?)";
        Long fieldId = -1L;
        try {
            conn = DBUtil.getCon();
            // *这里需要一个特殊处理：关闭自动提交事务     原因：需要获取当前事务中产生的FieldId
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(insert);
            pst.setString(1,field.getFieldName());
            pst.setInt(2,field.getFieldType());
            pst.setString(3,field.getFieldDesc());
            pst.setLong(4,field.getPaperId());
            pst.executeUpdate();
            // 获取当前事务中产生的fieldId
            String select = "select LAST_INSERT_ID()";
            // 再次编译执行
            pst = conn.prepareStatement(select);
            rs = pst.executeQuery();
            if (rs.next()) fieldId = rs.getLong(1);        // 获得当前事务中的fieldId
            // *这里开始处理 多选框，单选框等组件(单选/多选时，会有多个选项，需要把field里的这些选项传到selector表中)
            // 条件1 field!=-1  条件2 fieldId>=4  条件3 selectors绝对不为空
            if (fieldId != -1 && field.getFieldType() >= 4 && selectors != null && selectors.length > 0){
                insert = "insert into selector (selector_text,field_id) values (?,?)";
                pst = conn.prepareStatement(insert);
                for (int i = 0;i < selectors.length;i++){
                    pst.setString(1,selectors[i]);
                    pst.setLong(2,fieldId);
                    pst.executeUpdate();
                }
            }
            conn.commit();  // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
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
        return fieldId;
    }

    /**
     * 这里处理field的更新，field的处理比较特殊，因为4，5类型可能会携带selectors，但是又不能保证每次修改前后的selectors的数量相同
     * 主要思路：field的name,desc修改比较方便，但是对于selectors只能首先全部删除和当前fieldId相关的所有selectorId，然后重新插入
     * 缺点：会频繁消耗自增列
     * @param field
     * @param selectors
     * @return 返回fieldId
     */
    @Override
    public Long updateField(Field field, String[] selectors) {
        // 先对field进行更新
        String update = "update field set field_name=?,field_desc=? where field_id=?";
        CRUDUtil.exeUpdate(update, field.getFieldName(), field.getFieldDesc(), field.getFieldId());
        // 再根据fieldId判断是否覆盖fieldId对应的selector
        if (field.getFieldId() >= 4){
            // 先删除
            String delete = "delete from selector where field_id=?";
            CRUDUtil.exeUpdate(delete,field.getFieldId());
            // 如果有selector就添加，没有不操作
            if (selectors.length > 0 && selectors != null){
                String insert = "insert into selector (selector_text,field_id) values (?,?)";
                for (int i = 0;i < selectors.length;i++){
                    CRUDUtil.exeUpdate(insert,selectors[i],field.getFieldId());
                }
            }
        }
        return field.getFieldId();
    }

    @Override
    public int deleteField(Long fieldId) {
        String update = "update field set field_status=0 where field_id=?";
        int i = 0;
        i = CRUDUtil.exeUpdate(update, fieldId);
        return i;
    }

    /**
     * 根据paperId查询所有的field对象 集合形式返回
     * 存在返回集合对象 失败返回Null
     */
    @Override
    public List<Field> listFieldByPaperId(Long paperId) {
        String select = "select * from field where paper_id=?";
        List<Field> fieldList = new ArrayList<>();
        try {
            conn = DBUtil.getCon();
            pst = conn.prepareStatement(select);
            pst.setLong(1,paperId);
            rs = pst.executeQuery();
            while (rs.next()){
                Long fieldId = rs.getLong(1);
                String fieldName = rs.getString(2);
                int fieldType = rs.getInt(3);
                String fieldDesc = rs.getString(4);
                fieldList.add(new Field(fieldId,fieldName,fieldType,fieldDesc,paperId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return fieldList;
    }
}
