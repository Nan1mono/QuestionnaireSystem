package pres.ljc.project.factory;

import pres.ljc.project.dao.*;
import pres.ljc.project.dao.imp.*;
import pres.ljc.project.pojo.Field;
import pres.ljc.project.pojo.Selector;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个DAO层工厂模式，服务对象service层
 * 实现原理：通过map集合存储所有的对象，如果不存在就创建，如果存在，直接调用
 * 实现原理：通过map集合存储所有的DAO对象，如果不存在就创建，如果存在，直接调用
 * 存储形式：key:value
 * 通过工厂模式，可以做到一改全改的效果
 */
public class DAOFactory {
    // 存放DAO层的对象
    private static Map daoObjects = new HashMap();

    // 获取PaperDAO对象
    public static PaperDAO getPaperDAO(){
        if (daoObjects.containsValue("PaperDAO")) return (PaperDAO) daoObjects.get("PaperDAO");
        PaperDAO paperDAO = new PaperDAOImpl();
        daoObjects.put("PaperDAO",paperDAO);
        return paperDAO;
    }

    // 获取FieldDAO对象
    public static FieldDAO getFieldDAO(){
        if (daoObjects.containsKey("FieldDAO")) return (FieldDAO) daoObjects.get("FieldDAO");
        FieldDAO fieldDAO = new FieldDAOImpl();
        daoObjects.put("FieldDAO",fieldDAO);
        return fieldDAO;
    }

    // 获取SelectorDAO对象
    public static SelectorDAO getSelectorDAO(){
        if (daoObjects.containsKey("SelectorDAO")) return (SelectorDAO) daoObjects.get("SelectorDAO");
        SelectorDAO selectorDAO = new SelectorDAOImpl();
        daoObjects.put("SelectorDAO",selectorDAO);
        return selectorDAO;
    }

    // 获取answerDAO对象
    public static AnswerDAO getAnswerDAO(){
        if (daoObjects.containsKey("AnswerDAO")) return (AnswerDAO) daoObjects.get("AnswerDAO");
        AnswerDAO answerDAO = new AnswerDAOImpl();
        daoObjects.put("AnswerDAO",answerDAO);
        return answerDAO;
    }

    // 获取UserDAO对象
    public static UserDAO getUserDAO(){
        if (daoObjects.containsKey("UserDAO")) return (UserDAO) daoObjects.get("UserDAO");
        UserDAO userDAO = new UserDAOImpl();
        daoObjects.put("UserDAO",userDAO);
        return userDAO;
    }
}
