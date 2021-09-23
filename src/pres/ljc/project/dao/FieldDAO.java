package pres.ljc.project.dao;

import pres.ljc.project.pojo.Field;
import pres.ljc.project.pojo.Selector;

import java.util.List;

/**
 * 这里是FieldDAO层，负责Field对象在数据库的增删改查
 */
public interface FieldDAO {

    /**
     * 插入一个表单域对象(Field)，插入成功返回fieldId
     * 当paperId = -1 时，代表表单还没保存，也会导致保存失败
     * 当不满足条件时，也会保存失败，具体看实现类
     */
    Long insertField(Field field, String[] selectors);

    /**
     * 修改一个表单域对象(Field)，修改成功返回fieldId
     * 当paperId = -1 时，代表表单还没保存，也会导致保存失败
     * 当不满足条件时，也会保存失败，具体看实现类
     */
    Long updateField(Field field, String[] selectors);

    /**
     * 删除一个表单对象(Field)，并不是真正意义上的物理删除 将field_status = 0
     */
    int deleteField(Long fieldId);

    /**
     * 根据paperId查询所有的field对象 集合形式返回
     * 存在返回集合对象 失败返回Null
     */
    List<Field> listFieldByPaperId(Long paperId);


}
