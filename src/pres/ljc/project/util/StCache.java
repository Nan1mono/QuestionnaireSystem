package pres.ljc.project.util;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class StCache {
    // 缓存数据池
    private static Map<String, Object> pool = new ConcurrentHashMap<>();
    // 缓存池配对的定时器池
    private static Map<String, Timer> timerMap = new ConcurrentHashMap<>();

    // 根据key判断对应数据在缓存池中是否存在
    public static boolean cache(String key) {
        return pool.containsKey(key);
    }

    // 清除缓存Key和对应的数据
    public static void clean(String key) {
        // 清除缓存池数据
        pool.remove(key);
        // 重置定时器
        Timer timer = timerMap.get(key);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static Object get(String key) {
        return pool.get(key);
    }

    /**
     * 根据Key从缓存池中获取数据
     */
    public static <T> T get(String key, Class<T> tClass) {
        return (T) pool.get(key);
    }

    /**
     * 给缓存池添加数据，默认数据存活30分钟，30分钟刷新数据
     */
    public static void put(String key, Object data){
        put(key, data, 1000*60*30);
    }

    public static void put(String key, Object data, long delay){
        pool.put(key, data);
        // 声明一个定时器
        Timer timer = null;
        // 从定时器池中取出Key对应的定时器
        timer = timerMap.get(key);
        // 重置定时器
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        // 重新创建一个定时器
        timer = new Timer();
        // 设定执行延迟时间
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 清空缓存池
                pool.remove(key);
                // 删除定时器
                timerMap.remove(key);
            }
        }, delay);
    }
}
