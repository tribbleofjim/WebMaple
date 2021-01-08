package com.webmaple.worker.util;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lyifee
 * on 2020/12/28
 */
public class ConfigUtil {
    // ${} 占位符 正则表达式
    private static Pattern p1 = Pattern.compile("\\$\\{.*?\\}");

    private ConfigUtil(){
        throw new AssertionError();
    }

    /**
     * key:文件索引名
     * value：配置文件内容
     */
    private static Map<String , LinkedHashMap> ymls = new HashMap<>();
    /**
     * String:当前线程需要查询的文件名
     */
    private static ThreadLocal<String> nowFileName = new InheritableThreadLocal<>();

    /**
     * 加载配置文件
     * @param fileName
     */
    private static void loadYml(String fileName){
        nowFileName.set(fileName);
        if (!ymls.containsKey(fileName)){
            ymls.put(fileName , new Yaml().loadAs(ConfigUtil.class.getResourceAsStream("/" + fileName),LinkedHashMap.class));
        }
    }

    /**
     * 读取yml文件中的某个value。
     * 支持解析 yml文件中的 ${} 占位符
     * @param key
     * @return Object
     */
    private static Object getValue(String key){
        String[] keys = key.split("[.]");
        Map ymlInfo = (Map) ymls.get(nowFileName.get()).clone();
        for (int i = 0; i < keys.length; i++) {
            Object value = ymlInfo.get(keys[i]);
            if (i < keys.length - 1){
                ymlInfo = (Map) value;
            }else if (value == null){
                throw new RuntimeException("key不存在");
            }else {
                String g;
                String keyChild;
                String v1 = (String)value;
                for(Matcher m = p1.matcher(v1); m.find(); value = v1.replace(g, (String)getValue(keyChild))) {
                    g = m.group();
                    keyChild = g.replaceAll("\\$\\{", "").replaceAll("\\}", "");
                }
                return value;
            }
        }
        return "";
    }

    /**
     * 读取yml文件中的某个value
     * @param fileName  yml名称
     * @param key
     * @return Object
     */
    public static Object getValue(String fileName , String key){
        loadYml(fileName);
        return getValue(key);
    }

    /**
     * 读取yml文件中的某个value，返回String
     * @param fileName
     * @param key
     * @return String
     */
    public static String getValueToString(String fileName , String key){
        return (String)getValue(fileName , key);
    }

}
