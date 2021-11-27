package com.thinkgem.jeesite.common.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.ResultUtil;
import com.thinkgem.jeesite.common.vo.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/file")
public class UploadController  extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

//    @RequestMapping(value = "/image/show", method = RequestMethod.GET)
//    public void showImage(@RequestParam String path,
//                          HttpServletResponse response) throws IOException {
//        response.sendRedirect("");
//    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> uploadFile(
            HttpServletRequest request,
            @RequestParam(value="path", required = true) String path,
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="type", required = false) String type,
            @RequestParam(value="subDir", required = false) String subDir,
            @RequestParam(value="size", required = false) int size,
            @RequestParam("file") MultipartFile file) {
        String relativePath = null;
        try {
            path = StringUtils.isEmpty(path) ? "" : path;
            if (StringUtils.isEmpty(subDir)) {
                subDir = DateFormatUtils.format(new Date(), "yyyyMMdd/HHmm");
            }

            String baseDir = Global.getUserfilesBaseDir();
            String relativeDir = Global.USERFILES_BASE_URL + path + "/" + subDir;

            String saveDir = baseDir + relativeDir;
            FileUtils.createDirectory(FileUtils.path(saveDir));

            InputStream content = file.getInputStream();
            byte[] buffer = new byte[size];
            IOUtils.readFully(content, buffer);

            relativePath = relativeDir + "/" + name;
            String filePath = baseDir + relativePath;
            FileUtils.writeByteArrayToFile(new File(filePath), buffer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResultUtil<String>().setErrorMsg(e.getMessage());
        }
        return new ResultUtil<String>().setData(relativePath);
    }

    @RequestMapping(value = "template/download")
    public String importFileTemplate(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        try {
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
            }
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            //String filePath=Global.getUserfilesBaseDir()+"\\userfiles\\template\\";
            InputStream in = new FileInputStream(filePath+fileName);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/file/template/download/view";
    }

    @RequestMapping(value = "download")
    public void fileDownload(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        try {
            String fileName = "";
            String tempFlieName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
                tempFlieName = fileName.replaceAll("“","&ldquo;");
                tempFlieName = tempFlieName.replaceAll("”","&rdquo;");
//                tempFlieName = URLEncoder.encode(fileName, "UTF-8");
            }
            String filePath=Global.getUserfilesBaseDir();
            InputStream in = new FileInputStream(filePath+tempFlieName);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            String tmpFileName=fileName.substring(fileName.lastIndexOf("/")+1);
            response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(tmpFileName));
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "template/download/view")
    public String download(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        String id ="";
        if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
            id=request.getParameter("id").toString();
            Map<String,String> map=ImpDatas.getInstance().getValue(id);
            request.setAttribute("url",map.get("url"));
            request.setAttribute("template",map.get("template"));
        }
        return "modules/test/template_download";
    }

    /*
     * 民警个人训历档案导入
     * */
    @RequestMapping(value = "template/trainingFiledownload/view")
    public String trainingFiledownload(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {

        String id ="";
        if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
            id=request.getParameter("id").toString();
            Map<String,String> map=ImpDatas.getInstance().getValue(id);
            String url = map.get("url");
            String idNumber = request.getParameter("idNumber");
            request.setAttribute("url",url+"?idNumber="+idNumber);
            request.setAttribute("template",map.get("template"));
        }
        return "modules/test/template_download";
    }

    /*
     * 人员基本信息集导入
     * */
    @RequestMapping(value = "template/personnelBasesdownload/view")
    public String personnelBasesdownload(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {

        String id ="";
        if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
            id=request.getParameter("id").toString();
            Map<String,String> map=ImpDatas.getInstance().getValue(id);
            String url = map.get("url");
            String isCover = request.getParameter("isCover");//是否覆盖 覆盖：1 插入 ：0
            request.setAttribute("url",url+"?isCover="+isCover);
            request.setAttribute("template",map.get("template"));
        }
        return "modules/test/template_download";
    }

    /*
     * 七知档案导入
     *
    @RequestMapping(value = "template/SevenKnowledgedownload/view")
    public String SevenKnowledgedownload(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {

        String id ="";
        if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
            id=request.getParameter("id").toString();
            Map<String,String> map=ImpDatas.getInstance().getValue(id);
            String url = map.get("url");
            String idNumber = request.getParameter("idNumber");
            request.setAttribute("url",url+"?idNumber="+idNumber);
            request.setAttribute("template",map.get("template"));
        }
        return "modules/test/template_download";
    }
 */
    /**
     * 文件路径字符串截取分割处理，获得路径和文件名字
     * @param filePath
     * @return
     */
    @RequestMapping(value = "/filePathHandle")
    public List<Map<String,String>> filePathHandle(String filePath){
        List<Map<String,String>> list= new ArrayList();
        String[] split = filePath.split("\\|");
        List<String> pathArr = Arrays.asList(split);
        for (String path:pathArr) {
            HashMap<String, String> map = new HashMap<>();
            String fileName=path.substring(path.lastIndexOf("/")+1);
            map.put("fileName",fileName);
            map.put("path",path);
            list.add(map);
        }
        return list;
    }

    /**
     * 供考勤记录模块使用
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "template/download/affairAttendanceDownloadView")
    public String affairAttendanceDownload(HttpServletRequest request, Model model) {
        String id = "";
        if (null != request.getParameter("id") && !"".equals(request.getParameter("id"))) {
            id = request.getParameter("id").toString();
            Map<String, String> map = ImpDatas.getInstance().getValue(id);
            request.setAttribute("url", map.get("url"));
            request.setAttribute("template", map.get("template"));
        }
        return "modules/affair/affairAttendanceDownloadView";
    }
}
