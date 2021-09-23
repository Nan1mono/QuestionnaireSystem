package pres.ljc.project.dao;

import pres.ljc.project.pojo.Selector;

import java.util.List;

public interface SelectorDAO {
    /**
     * 根据fieldId查询selector集合
     */
    List<Selector> listSelector(Long fieldId);
}
