package com.thinkgem.jeesite.modules.search.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.thinkgem.jeesite.common.utils.EncryptUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.SysIndex;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysIndexService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import groovy.util.logging.Slf4j;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping({"/keysearch"})
@Slf4j
public class FastSearchSearchProxy {

    //@Value("${secure.key:cfe10f8490ra44cc8a7f48fc4134e8f0}")
    private String key;

    //@Value("${fastersearch.ip}")
    private String host;

    //@Value("${fastersearch.port}")
    private int port;

    //@Value("${fastersearch.user}")
    private String user;

    //@Value("${fastersearch.password}")
    private String password;

    @Autowired
    private SysIndexService sysIndexService;

    @Autowired
    private OfficeService officeService;

    //private String userAndPassword = user+ ":"+password;


    /**
     * 获取一键检索左边的类型列表
     *
     * @return
     */
    @RequestMapping(value = "/listType", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> listType(HttpServletRequest request) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders head = new HttpHeaders();
        head.add("Authorization", EncryptUtils.encrypt3DES(user + ":" + password, key));
        head.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity entity = new HttpEntity<String>("", head);
        URI uri = new URI("http", null, host, port, "/fastsearch/restapi/keysearch/listType", request.getQueryString(), null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok().body(responseEntity.getBody());
    }


    @RequestMapping(value = "/count", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> countIndex(@RequestBody(required = false) String body, HttpServletRequest request) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders head = new HttpHeaders();
        head.add("Authorization", EncryptUtils.encrypt3DES(user + ":" + password, key));
        head.add("Content-Type", "application/json; charset=utf-8");
        JSONObject bodyObject = JSON.parseObject(body);
        String queryString = bodyObject.getString("queryString");
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(bodyObject.getString("queryString"));
        if (StringUtils.isNotBlank(queryString) && !"admin".equals(UserUtils.getUser().getLoginName())) {
            queryBuffer.append(this.getOrgQueryString());
            bodyObject.put("queryString", queryBuffer);
        }
        body = JSONObject.toJSONString(bodyObject);
        HttpEntity entity = new HttpEntity<String>(body, head);
        URI uri = new URI("http", null, host, port, "/fastsearch/restapi/keysearch/count", request.getQueryString(), null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        JSONObject responseBodyObject = JSON.parseObject(responseEntity.getBody());
        JSONObject data = responseBodyObject.getJSONObject("data");
        JSONArray dataData = data.getJSONArray("data");
        this.filterCountByIndexs(dataData);
        String responseBody = JSONObject.toJSONString(responseBodyObject);
        return ResponseEntity.ok().body(responseBody);
    }

    @RequestMapping(value = "/search", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> search(@RequestBody(required = false) String body, HttpServletRequest request) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders head = new HttpHeaders();
        head.add("Authorization", EncryptUtils.encrypt3DES(user + ":" + password, key));
        head.add("Content-Type", "application/json; charset=utf-8");
        JSONObject bodyObject = JSON.parseObject(body);
        String queryString = bodyObject.getString("queryString");
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(bodyObject.getString("queryString"));
        if (StringUtils.isNotBlank(queryString) && !"admin".equals(UserUtils.getUser().getLoginName())) {
            queryBuffer.append(this.getOrgQueryString());
            bodyObject.put("queryString", queryBuffer);
        }
        body = JSONObject.toJSONString(bodyObject);
        HttpEntity entity = new HttpEntity<String>(body, head);
        URI uri = new URI("http", null, host, port, "/fastsearch/restapi/keysearch/search", request.getQueryString(), null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        JSONObject responseBodyObject = JSON.parseObject(responseEntity.getBody());
        JSONObject data = responseBodyObject.getJSONObject("data");
        JSONObject dataData = data.getJSONObject("data");
        JSONArray rows = dataData.getJSONArray("rows");
        dataData.put("rows", this.filterByIndexs(rows));
        String responseBody = JSONObject.toJSONString(responseBodyObject);
        return ResponseEntity.ok().body(responseBody);
    }

    @RequestMapping(value = "/export", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public void export(@RequestBody(required = false) String body, HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders head = new HttpHeaders();
        head.add("Authorization", EncryptUtils.encrypt3DES(user + ":" + password, key));
        head.add("Content-Type", "application/json; charset=utf-8");
        JSONObject bodyObject = JSON.parseObject(body);
        String queryString = bodyObject.getString("queryString");
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(bodyObject.getString("queryString"));
        if (StringUtils.isNotBlank(queryString) && !"admin".equals(UserUtils.getUser().getLoginName())) {
            queryBuffer.append(this.getOrgQueryString());
            bodyObject.put("queryString", queryBuffer);
        }
        body = JSONObject.toJSONString(bodyObject);
        HttpEntity entity = new HttpEntity<String>(body, head);
        URI uri = new URI("http", null, host, port, "/fastsearch/restapi/keysearch/export", request.getQueryString(), null);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, byte[].class);
        response.reset();
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Encoding", "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", responseEntity.getHeaders().get("Content-disposition").get(0));
        response.getOutputStream().write(responseEntity.getBody());
    }

    @RequestMapping(value = "/dict", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> dict(@RequestBody(required = false) String body, HttpServletRequest request) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders head = new HttpHeaders();
        head.add("Authorization", EncryptUtils.encrypt3DES(user + ":" + password, key));
        head.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity entity = new HttpEntity<String>(body, head);
        URI uri = new URI("http", null, host, port, "/fastsearch/restapi/keysearch/dict", request.getQueryString(), null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok().body(responseEntity.getBody());
    }

    private String getOrgQueryString() {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("  and (");
        if("nnjzzbxcjyc".equals(UserUtils.getUser().getLoginName())||"jyxlgl".equals(UserUtils.getUser().getLoginName())){
            List<String> officeList = officeService.findIdsByParentId("1");
            this.setEduOfficeStr(queryBuffer, officeList);
        }else if("nnczzcxcjys".equals(UserUtils.getUser().getLoginName())||"nnjcrsxlgl".equals(UserUtils.getUser().getLoginName())){
            List<String> officeList = officeService.findIdsByParentId("34");
            this.setEduOfficeStr(queryBuffer, officeList);
        }else if("lzczzcxcjys".equals(UserUtils.getUser().getLoginName())||"lzcrsxlgl".equals(UserUtils.getUser().getLoginName())){
            List<String> officeList = officeService.findIdsByParentId("95");
            this.setEduOfficeStr(queryBuffer, officeList);
        }else if("bhczzcxcjys".equals(UserUtils.getUser().getLoginName())||"bhcrsxlgl".equals(UserUtils.getUser().getLoginName())){
            List<String> officeList = officeService.findIdsByParentId("156");
            this.setEduOfficeStr(queryBuffer, officeList);
        } else {
            List<Office> officeList  = UserUtils.getOfficeList();
            for (int i = 0; i < officeList.size(); i++) {
                Office office = (Office) officeList.get(i);
                if (i == 0) {
                    queryBuffer.append(" create_org_id:" + office.getId());
                } else {
                    queryBuffer.append(" or create_org_id:" + office.getId());
                }
            }
        }
        queryBuffer.append(")");
        return queryBuffer.toString();
    }

    /**
     * 教育训练部门特殊处理
     * @return
     */
    private void setEduOfficeStr(StringBuffer queryBuffer, List<String> officeList){
        for (int i = 0; i < officeList.size(); i++) {
            if (i == 0) {
                queryBuffer.append(" create_org_id:" + officeList.get(i));
            } else {
                queryBuffer.append(" or create_org_id:" + officeList.get(i));
            }
        }
    }

    private JSONArray filterByIndexs(JSONArray rows) {
        JSONArray resultRows = new JSONArray();
        if ("admin".equals(UserUtils.getUser().getLoginName())) {
            resultRows = rows;
        } else {
            Set<String> indexs = new HashSet<String>();
            List<SysIndex> sysIndexList = sysIndexService.findListByRoles();
            if (null != sysIndexList) {
                for (SysIndex sysIndex : sysIndexList) {
                    indexs.add(sysIndex.getIndexCode());
                }
            }
            if (indexs.size() > 0) {
                if (rows != null && rows.size() > 0) {
                    for (int i = 0; i < rows.size(); i++) {
                        JSONObject row = (JSONObject) rows.get(i);
                        if (indexs.contains(row.get("index").toString())) {
                            resultRows.add(row);
                        }
                    }
                }
            }
        }
        return resultRows;
    }

    private void filterCountByIndexs(JSONArray rows) {
        if ("admin".equals(UserUtils.getUser().getLoginName())) {
            return;
        } else {
            Set<String> indexs = new HashSet<String>();
            List<SysIndex> sysIndexList = sysIndexService.findListByRoles();
            if (null != sysIndexList) {
                for (SysIndex sysIndex : sysIndexList) {
                    indexs.add(sysIndex.getIndexCode());
                }
            }
            if (rows != null && rows.size() > 0) {
                for (int i = 0; i < rows.size(); i++) {
                    JSONObject row = (JSONObject) rows.get(i);
                    JSONArray children = row.getJSONArray("children");
                    for (int j = 0; j < children.size(); j++) {
                        JSONObject child = (JSONObject) children.get(j);
                        if (!indexs.contains(child.get("code").toString())) {
                            child.put("cnt", "0");
                        }
                    }
                }
            }
        }
    }
}