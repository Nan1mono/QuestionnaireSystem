package pres.ljc.project.service;

import pres.ljc.project.pojo.Field;
import pres.ljc.project.pojo.Selector;

import java.util.List;

/**
 * 这里是Field的服务层接口，在这里调用FieldDAO层实现业务处理操作
 */
public interface FieldService {

    /**
     * 更新或插入Field对象
     * 如果fieldId = -1 就插入
     * 如果fieldId !=  -1 就根据这个fieldId更新这个Field对象
     * @return 操作成功返回fieldId 失败返回-1
     */
    Long exeUpdateField(Field field, String[] selectors);

    /**
     * 根据fieldId删除field 返回受影响的行数(伪删除)
     */
    int deleteField(Long fieldId);

    /**
     * 根据paperId查询field集合
     */
    List<Field> listFieldByPaperId(Long paperId);


    /**
     * @Description 根据传入的fieldId确定fieldType
     * @param fieldId
     * @param fieldList
     * @return 返回fieldType
     */
    int getFieldType(Long fieldId, List<Field> fieldList);
}
