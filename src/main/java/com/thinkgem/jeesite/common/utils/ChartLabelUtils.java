package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.modules.sys.entity.Dict;

import java.util.*;

/**
 * 统计分析工具类
 */

public class ChartLabelUtils {

    /*label 标签 柱状图中柱子的名称*/

    /**
     * 向查询到的list中添加不在labelArray中的label
     * @param list  查询到的list集合
     * @param labelArray    图表应展示的所有label
     * @return
     */
    public static List<Map<String,String>> fillLabel( List<Map<String,String>>  list,String[] labelArray){
        if (labelArray == null || list ==null){
            return new ArrayList<>();
        }
        /*所有的label转为list*/
        List<String> labelList = Arrays.asList(labelArray);
        /*查询到的label*/
        List<String> label = new ArrayList<>();
        list.forEach(item-> {
            label.add(item.get("label"));
        });
        /*遍历所有label，查询到的label没有 则添加*/
        labelList.forEach(s -> {
            boolean has = false;
            for(String item : label) {
                if (item.equals(s)){
                    has = true;
                }
            }
                if (!has){
                    Map<String,String> map = new HashMap<>();
                    map.put("label",s);
                    map.put("num","0");
                    list.add(map);
                }
        });
        return list;
    }

    /**
     *
     * @param list  查询到的集合
     * @param dicts 所有标签的字典
     * @return
     */
    public static List<Map<String,String>> fillLabel( List<Map<String,String>>  list,List<Dict> dicts){
        if (dicts == null || list ==null){
            return new ArrayList<>();
        }
        /*所有的label转为list*/
        List<String> labelList = new ArrayList<>();
        dicts.forEach(dict -> {
            labelList.add(dict.getLabel());
        });
        /*查询到的label*/
        List<String> label = new ArrayList<>();
        list.forEach(item-> {
            label.add(item.get("label"));
        });
        /*遍历所有label，查询到的label没有 则添加*/
        labelList.forEach(s -> {
            boolean has = false;
            for(String item : label) {
                if (item.equals(s)){
                    has = true;
                }
            }
            if (!has){
                Map<String,String> map = new HashMap<>();
                map.put("label",s);
                map.put("num","0");
                list.add(map);
            }
        });
        return list;
    }




}
