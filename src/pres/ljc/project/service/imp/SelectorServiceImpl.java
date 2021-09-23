package pres.ljc.project.service.imp;

import pres.ljc.project.dao.SelectorDAO;
import pres.ljc.project.factory.DAOFactory;
import pres.ljc.project.pojo.Selector;
import pres.ljc.project.service.SelectorService;

import java.util.List;

public class SelectorServiceImpl implements SelectorService {
    private SelectorDAO selectorDAO = DAOFactory.getSelectorDAO();

    /**
     * 根据fieldId查找selector集合 并返回
     * @param fieldId
     * @return 成功返回集合对象 失败Null
     */
    @Override
    public List<Selector> listSelector(Long fieldId) {
        List<Selector> selectorList = null;
        selectorList = selectorDAO.listSelector(fieldId);
        return selectorList;
    }
}
