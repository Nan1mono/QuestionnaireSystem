package pres.ljc.project.service;

import pres.ljc.project.pojo.Selector;

import java.util.List;

public interface SelectorService {
    /**
     * 根据fieldId查询selector集合
     * 查询成功返回集合对象 失败返回Null
     */
    List<Selector> listSelector(Long fieldId);
}
