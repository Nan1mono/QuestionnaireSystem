package pres.ljc.project.factory;

import pres.ljc.project.service.*;
import pres.ljc.project.service.imp.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个service层工厂模式，服务对象servlet层
 * 实现原理：通过map集合存储所有的service对象，如果不存在就创建，如果存在，直接调用
 * 存储形式：key:value
 * 通过工厂模式，可以做到一改全改的效果
 */
public class ServiceFactory {
    // 使用静态map集合存储service对象
    private static Map serviceObjects = new HashMap();

    // 获取PaperService对象
    public static PaperService getPaperService(){
        // 如果是serviceObjects中存在PaperService对象，那就返回Service对象，不存在创建这个对象并返回
        if (serviceObjects.containsKey("PaperService")) return (PaperService) serviceObjects.get("PaperService");
        PaperService paperService = new PaperServiceImpl();
        serviceObjects.put("PaperService",paperService);
        return paperService;
    }

    // 获取FieldService对象
    public static FieldService getFieldService(){
        // 如果是serviceObjects中存在PaperService对象，那就返回Service对象，不存在创建这个对象并返回
        if (serviceObjects.containsKey("FieldService")) return (FieldService) serviceObjects.get("FieldService");
        FieldService fieldService = new FieldServiceImpl();
        serviceObjects.put("FieldService",fieldService);
        return fieldService;
    }

    // 获取SelectorService对象
    public static SelectorService getSelectorService(){
        if (serviceObjects.containsKey("SelectorService")) return (SelectorService) serviceObjects.get("SelectorService");
        SelectorService selectorService = new SelectorServiceImpl();
        serviceObjects.put("SelectorService",selectorService);
        return selectorService;
    }

    // 获取AnswerService对象
    public static AnswerService getAnswerService(){
        if (serviceObjects.containsKey("AnswerService")) return (AnswerService) serviceObjects.get("AnswerService");
        AnswerService answerService = new AnswerServiceImpl();
        serviceObjects.put("AnswerService",answerService);
        return answerService;
    }

    // 获取UserService对象
    public static UserService getUserService(){
        if (serviceObjects.containsKey("UserService")) return (UserService) serviceObjects.get("UserService");
        UserService userService = new UserServiceImpl();
        serviceObjects.put("UserService",userService);
        return userService;
    }
}
