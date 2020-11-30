package com.gongjun.changsha.tools;

import org.junit.Test;

import java.util.*;


/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 16:39 2020/11/30
 */
public class ChangshaExcelNameUtils {
    private static List<String> NAME_RELATION_LIST = Arrays.asList(
            "922-1,综合",
            "922-2,企业",
            "922-3,文化及相关产业",
            "922-4,科技",
            "922-5,信息化和电子商务",
            "922-6,工业",
            "922-7,建筑业",
            "922-8,批发和零售业",
            "922-9,住宿和餐饮业",
            "922-10,房地产开发经营业",
            "922-11,服务业"
    );

    public static List<String> getCodeAndNameList(){
        return NAME_RELATION_LIST;
    }

    public static Map<String,String> getNameMap(){
        Map<String,String> nameMap = new HashMap<>();
        NAME_RELATION_LIST.forEach(e->{
            String[] ss = e.split(",");
            nameMap.put(ss[0],ss[1]);
        });
        return sortByKey(nameMap);
    }

    public static List<String> getNameList(){
        List<String> nameList = new ArrayList<>();
        Map<String,String> nameMap = getNameMap();
        for (String code:nameMap.keySet()){
            nameList.add(nameMap.get(code));
        }
        return nameList;
    }

    public static List<String> getCodeList(){
        Map<String,String> nameMap = getNameMap();
        return new ArrayList<>(nameMap.keySet());
    }

    private static Map<String, String> sortByKey(Map<String, String> map) {
        Map<String, String> result = new LinkedHashMap<>(map.size());
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    @Test
    public void test(){
        System.out.println(getCodeList());
    }
}
