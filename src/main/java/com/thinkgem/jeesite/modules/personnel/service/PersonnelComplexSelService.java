package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelComplexSelDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBatchSel;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 人员复合查询 service 用于查询 表名，字段名
 *
 * @author kevin.jia,
 * @version 2020/9/18
 */
@Service
@Transactional(readOnly = true)
public class PersonnelComplexSelService {
    @Autowired
    private PersonnelComplexSelDao complexSelDao;

    public List<Map<String, String>> selTableNames() {
        return complexSelDao.selTableNames();
    }

    public List<Map<String, String>> selColumnByTableName(String tableName) {
        List<Map<String, String>> mapList = complexSelDao.selColumnByTableName(tableName);
        /*
        * create_by  create_org_id  create_id_no   create_date  update_by  update_org_id  update_id_no  update_date  del_flag
        *  创建者     创建者机构id     创建者身份证号    创建时间     更新者      更新者机构id   更新者身份证号   更新时间    删除标记（1:删除)
        * */
        Iterator<Map<String, String>> iterator = mapList.iterator();
        while(iterator.hasNext()){
            Map<String, String> tempIterator = iterator.next();
            String columnName = tempIterator.get("name");
            if ("id".equals(columnName)
                    ||"create_by".equals(columnName)
                    ||"create_org_id".equals(columnName)
                    ||"create_id_no".equals(columnName)
                    ||"create_date".equals(columnName)
                    ||"update_by".equals(columnName)
                    ||"update_org_id".equals(columnName)
                    ||"update_id_no".equals(columnName)
                    ||"update_date".equals(columnName)
                    ||"del_flag".equals(columnName)
            ){
                iterator.remove();
            }

        }
        return mapList;
    }
    @Transactional(readOnly = false)
    public  Map<String,Object> createCustomSql(String submitTableName, String submitCheckColumntext, String submitCheckColumnvalue, List<Map<String, Object>> mapList) {
        Map<String,Object> resultMap = new HashMap<>();
        final String isNullStr = "XG_isNull";//用于判断前台传输值是否为空
        List<Map<String,Object>> list = new ArrayList<>();//用于存储显示字段
        //List<Map<String,Object>> list = new ArrayList<>();
        String customSql_sel = "";
        String customSql_from = "";
        String customSql_where = "";
        String customSql_del = "";  //删除标记
        Set<String> tableNameSet = new LinkedHashSet<>();
        String[] tableNameArr = null;//用于存储选中显示字段表名集合
        String[] checkColumnArr = null;//用于存储选中显示字段集合
        String[] checkColumntextArr = null;//用于存储选中显示字段名集合
        List<String> columnList = new ArrayList<>();
        List<String> columnTextList = new ArrayList<>();
        //选中表名
        if (submitTableName != null && !submitTableName.isEmpty()) {
            tableNameArr = submitTableName.split("\\+");
            if (tableNameArr.length > 0) {
                for (int i = 0; i < tableNameArr.length; i++) {
                    tableNameSet.add(tableNameArr[i]);
                }
            }
        }
        //选中显示字段
        if (submitCheckColumnvalue != null && !submitCheckColumnvalue.isEmpty()) {
            checkColumnArr = submitCheckColumnvalue.split("\\+");
            if (checkColumnArr.length > 0) {
                for (int i = 0; i < checkColumnArr.length; i++) {
                    columnList.add(checkColumnArr[i]);
                }
            }
        }

        //选中字段名
        if (submitCheckColumntext != null && !submitCheckColumntext.isEmpty()) {
            checkColumntextArr = submitCheckColumntext.split("\\+");
            if (checkColumntextArr.length > 0) {
                for (int i = 0; i < checkColumntextArr.length; i++) {
                    if(checkColumntextArr[i].split("\\.").length>1&&checkColumntextArr[i].split("\\.")[1]!=null&&!checkColumntextArr[i].split("\\.")[1].isEmpty()){
                        columnTextList.add(checkColumntextArr[i].split("\\.")[1]);
                    }else{
                        columnTextList.add(checkColumntextArr[i]);
                    }
                }
            }
        }
        //拼接select sql
        if (columnList.size() > 0) {
            if (customSql_sel == null || customSql_sel.isEmpty()) {
                customSql_sel = "SELECT ";
            }
            for (int i = 0; i < columnList.size(); i++) {
                String tempColumnText = "\"" + columnTextList.get(i)+ "\"";
                String tempSelColumn = columnList.get(i) + " AS " + tempColumnText;
                if (i + 1 < columnList.size()) {
                    customSql_sel += tempSelColumn + ",";
                } else {
                    customSql_sel += tempSelColumn;
                }

            }
        }


        //where条件
        if (mapList.size() > 0) {

            for (int i = 0;i<mapList.size();i++) {
                Map<String, Object> map = mapList.get(i);
                String selTableName = (String) map.get("tableSel");//选择表名
                String selColumn = (String) map.get("columnSel");//选择字段
                String selOperator = (String) map.get("operator");//选择操作符
                String inputValue = (String) map.get("inputValue");//值
                String selRelation = (String) map.get("relation");//选中关系
                if (!isNullStr.equals(selTableName)
                        && !isNullStr.equals(selColumn)
                        && !isNullStr.equals(selOperator)
                        &&("12".equals(selOperator)||"13".equals(selOperator)||!isNullStr.equals(inputValue))
                ) {
                    //如果操作符为 是空  非空  值无需再输入

                    if (customSql_where == null || customSql_where.isEmpty()) {
                        customSql_where = "WHERE ";
                    }
                    tableNameSet.add((String) map.get("tableSel"));
                    switch (selOperator) {
                        case "1"://等于
                            customSql_where += selTableName + "." + selColumn + " = '" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "2"://大于
                            customSql_where += selTableName + "." + selColumn + " > '" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "3"://大于等于
                            customSql_where += selTableName + "." + selColumn + " >= '" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "4"://小于
                            customSql_where += selTableName + "." + selColumn + " < '" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "5"://小于等于
                            customSql_where += selTableName + "." + selColumn + " <= '" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "6"://不等于
                            customSql_where += selTableName + "." + selColumn + " <> '" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "7"://包含
                            customSql_where += selTableName + "." + selColumn + " like '%" + inputValue + "%'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "8"://左包含
                            customSql_where += selTableName + "." + selColumn + " like '" + inputValue + "%'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "9"://右包含
                            customSql_where += selTableName + "." + selColumn + " like '%" + inputValue+"'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "10"://不包含
                            customSql_where += selTableName + "." + selColumn + " not like '%" + inputValue + "%'";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "11"://多条件选择
                            String[] valueArr = inputValue.split("-");
                            String valueStrs = "";
                            for(int j = 0;j<valueArr.length;j++){
                                if(j+1<valueArr.length){
                                    valueStrs += "'"+valueArr[j]+"',";
                                }else{
                                    valueStrs += "'"+valueArr[j]+"'";
                                }
                            }
                            if(!valueStrs.isEmpty()){
                                customSql_where += selTableName + "." + selColumn + " in (" + valueStrs + ")";
                            }
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "12"://是空
                            customSql_where += selTableName + "." + selColumn + " is NUll";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        case "13"://非空
                            customSql_where += selTableName + "." + selColumn + " IS NOT NULL";
                            if (!isNullStr.equals(selRelation)&&i+1<mapList.size()) {
                                customSql_where += " " + selRelation + " ";
                            }
                            continue;
                        default:
                            continue;
                    }

                }

            }
        }
        //拼接 from sql
        String[] tableNameSetSize = new String[tableNameSet.size()];
        String[] tableNameSetValue = tableNameSet.toArray(tableNameSetSize);
        String tempCustomSql_from = "FROM ";
        for (int i = 0; i < tableNameSetValue.length; i++) {
            if (customSql_from == null || customSql_from.isEmpty()) {
                customSql_from = "FROM ";
            }
            String tableName = tableNameSetValue[i];
            if (i == 0 && customSql_from.equals(tempCustomSql_from)) {
                customSql_from += tableName;
                customSql_del += "("+tableName+".del_flag = '0' or "+tableName+".del_flag IS NULL )";
            } else {
                customSql_from += " LEFT JOIN " + tableName + " ON " + tableNameSetValue[0] + ".id_number = " + tableName + ".id_number";
                customSql_del += " AND " + "("+tableName+".del_flag = '0' or "+tableName+".del_flag IS NULL )";
            }

        }

       /* System.out.println("============");
        System.out.println(customSql_where);
        System.out.println(customSql_from);
        System.out.println(customSql_sel);
        System.out.println("============");*/
        String customSql = "";
        //拼接sql
        if (customSql_sel != null && !customSql_sel.isEmpty()&&customSql_from != null && !customSql_from.isEmpty()&&customSql_where != null && !customSql_where.isEmpty()) {
            customSql = customSql_sel + " " + customSql_from + " " + customSql_where+" and "+customSql_del;
        } else if (customSql_sel != null && !customSql_sel.isEmpty()&&customSql_from != null && !customSql_from.isEmpty()) {
            customSql = customSql_sel + " " + customSql_from +" where "+customSql_del;
        }else{
            customSql = "";
        }
        if (!customSql.isEmpty()) {
            list = complexSelDao.selCustomSql(customSql);
        }
       /* System.out.println(customSql);*/
        resultMap.put("resultList",list);
        resultMap.put("columnText",columnTextList);
        resultMap.put("customSql",customSql);
        resultMap.put("columnList",columnList);
        return resultMap;
    }

    public List<Map<String,Object>> execCustomSql(String execCustomSql){
        return complexSelDao.selCustomSql(execCustomSql);
    }

    public Page<Map<String,Object>> execCustomSql(Page<Map<String,Object>> page,String execCustomSql,List<String> columnTextList,List<String> columnList){
        List<Map<String, Object>> mapList = complexSelDao.selCustomSql(execCustomSql);
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int count = mapList.size();
        List<Map<String, Object>> partMapList;
        if(pageSize*pageNo<count){
            partMapList = mapList.subList((pageNo-1)*pageSize,pageSize*pageNo);
        }else{
            partMapList = mapList.subList((pageNo-1)*pageSize,count);
        }
        page.setCount(count);
        for(Map<String,Object> map : partMapList){
            for(int i = 0; i<columnList.size();i++){
                String[] strArr = columnList.get(i).split("\\.");
                List<Map<String,Object>> list = complexSelDao.selDictTypeByTC(strArr[0],strArr[1]);
                if(list!=null && list.size()>0){
                    String dictType = (String) list.get(0).get("dictType");
                    map.put(columnTextList.get(i),DictUtils.getDictLabel((String) map.get(columnTextList.get(i)),dictType, (String) map.get(columnTextList.get(i))));
                }
            }
        }
        page.setList(partMapList);
        page.setFuncName("complexSelPageChange");
        return page;
    }
    /*导出所有所采用方法*/
    public Page<Map<String,Object>> execCustomSqlAllResult(Page<Map<String,Object>> page,String execCustomSql,List<String> columnTextList,List<String> columnList){
        List<Map<String, Object>> mapList = complexSelDao.selCustomSql(execCustomSql);
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int count = mapList.size();
        page.setCount(count);
        for(Map<String,Object> map : mapList){
            for(int i = 0; i<columnList.size();i++){
                String[] strArr = columnList.get(i).split("\\.");
                List<Map<String,Object>> list = complexSelDao.selDictTypeByTC(strArr[0],strArr[1]);
                if(list!=null && list.size()>0){
                    String dictType = (String) list.get(0).get("dictType");
                    map.put(columnTextList.get(i),DictUtils.getDictLabel((String) map.get(columnTextList.get(i)),dictType, (String) map.get(columnTextList.get(i))));
                }
            }
        }
        page.setList(mapList);
        return page;
    }

    public Map<String, Object> createCustomBatchSql(MultipartFile file, String selItem, String sheetPage, String startLine, String excelColumn, String unitId, String status) {
        Map<String,Object> resultMap = new HashMap<>();
        StringBuffer customBatchSelSql = new StringBuffer("SELECT a.name AS \"name\"," +
                "a.id_number AS \"idNumber\"," +
                "a.workunit_name AS \"workunitName\"," +
                "a.id_number AS \"idNumber\"," +
                "a.police_id_number AS \"policeIdNumber\"," +
                "a.job_abbreviation AS \"jobAbbreviation\"," +
                "a.status AS \"status\"," +
                "a.remarks AS \"remarks\" " +
                " FROM " +
                " personnel_base a " +
                "where a.del_flag = '0' AND ");
        try {
            int sheetPageInt = Integer.parseInt(sheetPage);//sheet页数
            int startLineInt = Integer.parseInt(startLine);//开始行
            int excelColumnInt = Integer.parseInt(excelColumn);//所在列
            if(sheetPageInt<1||startLineInt<1||excelColumnInt<1){
                resultMap.put("result",false);
                resultMap.put("failMessage","所在列，所在sheet页，数据开始行应大于等于1");
                return resultMap;
            }
            ImportExcel ei = new ImportExcel(file, startLineInt-2, sheetPageInt-1); //headerNum 标题行号 数据行号=标题行号+1
            List<PersonnelBatchSel> list = ei.getBatchSelDataList(PersonnelBatchSel.class,excelColumnInt-1);//系统读取excel，列从0开始
            if(list.isEmpty()){
                resultMap.put("result",false);
                resultMap.put("failMessage","未从文件中读取数据，请检查文件是否选择正确，各个选项是否填写正确");
                return resultMap;
            }else{
                StringBuffer inSql = new StringBuffer();
                for(int i = 0;i<list.size(); i++){
                    PersonnelBatchSel personnelBatchSel = list.get(i);
                    String tempContent = personnelBatchSel.getContent().trim();
                    if(tempContent!=null && !tempContent.isEmpty()){
                        if(i>0){
                            inSql.append(",");
                        }
                        String content = "'"+tempContent+"'";
                        inSql.append(content);
                    }
                }
                if (StringUtils.isBlank(inSql.toString())) {
                    resultMap.put("result",false);
                    resultMap.put("failMessage","从文件中读取数据为空，请检查文件/信息是否填写正确");
                    return resultMap;
                }else {
                    if(StringUtils.isBlank(selItem)){
                        resultMap.put("result",false);
                        resultMap.put("failMessage","请选择查询项");
                        return resultMap;
                    }else{
                        customBatchSelSql.append(selItem + " in ( "+inSql.toString()+" ) ");
                    }
                }
                if(unitId!=null && StringUtils.isNotBlank(unitId)){
                    customBatchSelSql.append(" AND a.workunit_id = '"+unitId+"' ");
                }
                if(status!=null && StringUtils.isNotBlank(status)){
                    customBatchSelSql.append(" AND a.status = '"+status+"'");
                }
                customBatchSelSql.append(" ORDER BY a.update_date DESC");
                String customBatchSqlStr = customBatchSelSql.toString();
                List<PersonnelBase> resultList = complexSelDao.selCustomBatchSql(customBatchSqlStr);
                resultMap.put("customBatchSql",customBatchSqlStr);
                resultMap.put("resultList",resultList);
                resultMap.put("result",true);
            }
        } catch (Exception e) {
            resultMap.put("result",false);
            e.printStackTrace();
            resultMap.put("failMessage","发生错误，确认所有信息填写正确后，再次重试");
        }
        return resultMap;
    }

    public Page<PersonnelBase> execBatchCustomSql(Page<PersonnelBase> page, String customSql) {
        List<PersonnelBase> resultList = complexSelDao.selCustomBatchSql(customSql);
        page.setCount(resultList.size());
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int count = resultList.size();
        List<PersonnelBase> partMapList;
        if(pageSize*pageNo<count){
            partMapList = resultList.subList((pageNo-1)*pageSize,pageSize*pageNo);
        }else{
            partMapList = resultList.subList((pageNo-1)*pageSize,count);
        }
        page.setList(partMapList);
        page.setFuncName("batchSelPageChange");
        return page;
    }

    public Page<PersonnelBase> execBatchCustomSqlAllResult(Page<PersonnelBase> page, String customSql) {
        List<PersonnelBase> resultList = complexSelDao.selCustomBatchSql(customSql);
        page.setCount(resultList.size());
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int count = resultList.size();
        page.setList(resultList);
        page.setFuncName("batchSelPageChange");
        return page;
    }

    public Map<String, Object> isDictFields(String tableSel, String columnSel) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list = complexSelDao.selDictTypeByTC(tableSel,columnSel);
        String dictType = "";
        List<Dict> dictList = new ArrayList<>();
        if(list!=null && list.size()>0){
            Map<String,Object> tempMap = list.get(0);
            dictType = (String) tempMap.get("dictType");
            dictList = DictUtils.getDictList(dictType);
            map.put("isDictFields",true);
            map.put("dictList",dictList);
        }else{
            map.put("isDictFields",false);
        }
        return map;
    }

}
