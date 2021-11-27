package com.thinkgem.jeesite.modules.org.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.ImpDatas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 机构信息管理excel导入上传Controller
 * @author eav.liu
 * @version 2019-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/org/upload")
public class OrgUploadController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(OrgUploadController.class);

    @RequestMapping(value = "template/download/view")
    public String download(HttpServletResponse response, HttpServletRequest request) {
        String id = "";
        String orgId = "";
        if(null != request.getParameter("id") && !"".equals(request.getParameter("id")) && null != request.getParameter("orgId") && !"".equals(request.getParameter("orgId"))){
            id=request.getParameter("id").toString();
            orgId=request.getParameter("orgId").toString();
            Map<String,String> map=ImpDatas.getInstance().getValue(id);
            request.setAttribute("url",map.get("url")+"?orgId="+orgId);
            request.setAttribute("template",map.get("template"));
        }
        return "modules/test/template_download";
    }
}
