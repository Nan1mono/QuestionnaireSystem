package pres.ljc.project.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;


public class JSONUtil {
    private static ObjectMapper mapper=new ObjectMapper();

    /**
     * @Description 将Json转换成Object对象
     */
    public static <T> T jsonToObject(String json,Class<T> tClass){
        T obj=null;
        try{
            obj=mapper.readValue(json,tClass);
        } catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @Description 将object对象转换成Json
     * @param o
     * @return 返还json字符串
     */
    public static String  objectToJson(Object o){
        String s=null;
        try {
            s=mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * @Description 将Json数组转换成对象集合
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToObjectList(String json,Class<T> tClass){
        List<T> list=null;
        try{
            list=mapper.readValue(json,new TypeReference<List<T>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
