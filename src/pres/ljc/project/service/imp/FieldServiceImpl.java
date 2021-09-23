package pres.ljc.project.service.imp;

import pres.ljc.project.dao.FieldDAO;
import pres.ljc.project.factory.DAOFactory;
import pres.ljc.project.pojo.Field;
import pres.ljc.project.pojo.Selector;
import pres.ljc.project.service.FieldService;

import java.util.List;

public class FieldServiceImpl implements FieldService {
    private FieldDAO fieldDAO = DAOFactory.getFieldDAO();

    /**
     * 更新或插入Field对象
     * 如果fieldId = -1就插入
     * 如果fieldId !=  -1 就根据这个fieldId更新这个Field对象
     * @return 操作成功返回fieldId 失败返回-1
     */
    @Override
    public Long exeUpdateField(Field field, String[] selectors) {
        Long fieldId = -1L;
        if (fieldId.equals(field.getFieldId())) {
            fieldId = fieldDAO.insertField(field, selectors);            // 如果fieldId = -1 执行插入操作
        } else {
            fieldId = fieldDAO.updateField(field, selectors);            // 如果是fieldId != -1 执行更新操作
        }
        return fieldId;
    }

    public int deleteField(Long fieldId) {
        int i = 0;
        i = fieldDAO.deleteField(fieldId);
        return i;   // 返回受影响的行数
    }

    /**
     * 根据paperId查询field集合
     * 成功返回field集合  失败返回null
     */
    @Override
    public List<Field> listFieldByPaperId(Long paperId) {
        List<Field> fieldList = null;
        fieldList = fieldDAO.listFieldByPaperId(paperId);
        return fieldList;
    }

    /**
     * @Description 根据传入的fieldId确定fieldType
     * @param fieldId
     * @param fieldList
     * @return 返回fieldType
     */
    @Override
    public int getFieldType(Long fieldId, List<Field> fieldList) {
        for (int i = 0 ;i < fieldList.size();i++){
            if (fieldId.equals(fieldList.get(i).getFieldId())){
                return fieldList.get(i).getFieldType();
            }
        }
        return 0;
    }
}
