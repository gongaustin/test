package com.gongjun.changsha.tools;

import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 14:34 2020/11/26
 */
public class ChangshaZoneUtils {
    private static List<String> ZONE_RELATION_LIST = Arrays.asList(
            "430102,芙蓉区",
            "430103,天心区",
            "430104,岳麓区",
            "430105,开福区",
            "430111,雨花区",
            "430112,望城区",
            "430121,长沙县",
            "430181,浏阳市",
            "430182,宁乡市"
    );

    public static List<String> getCodeAndZoneList(){
        return ZONE_RELATION_LIST;
    }

    public static Map<String,String> getZoneMap(){
        Map<String,String> zoneMap = new HashMap<>();
        ZONE_RELATION_LIST.forEach(e->{
            String[] ss = e.split(",");
            zoneMap.put(ss[0],ss[1]);
        });
        return sortByKey(zoneMap);
    }

    public static List<String> getZoneList(){
        List<String> zoneList = new ArrayList<>();
        Map<String,String> zoneMap = getZoneMap();
        for (String code:zoneMap.keySet()){
            zoneList.add(zoneMap.get(code));
        }
        return zoneList;
    }

    public static List<String> getCodeList(){
        Map<String,String> zoneMap = getZoneMap();
        return new ArrayList<>(zoneMap.keySet());
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
