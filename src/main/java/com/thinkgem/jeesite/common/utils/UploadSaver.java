package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.config.Global;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

public class UploadSaver {

    private static final int MEMORY_THR = 1024 * 1024 * 3;
    private static final int FILE_MAX = 1024 * 1024 * 200;
    private static final int MAX = 1024 * 1024 * 1000;
    private static final String UPLOAD_PATH = "upload";

    //------------------递归遍历文件--------------------------------------
    public static void judgeFile(File file, List list) {

        if (!file.isFile()) {
            File[] files = file.listFiles();
            for (File f : files) {
                judgeFile(f, list);
            }
        } else {
            //map.put(file.getName(), file.getName());
            list.add(file.getName());
        }
    }

    //-----------------------下载文件------------------------------------------------
    public static void downloadFile(HttpServletRequest req, HttpServletResponse res, String filePath) throws ServletException, IOException {
        //获取下载文件名称
        File file = new File(filePath);

        if (!file.exists()) {
            req.setAttribute("message", "下载的文件不存在....");
            return;
        }
        //如果文件存在 则下载
        res.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "utf-8"));
        //已文件流的方式 进行下载操作
        FileInputStream in = new FileInputStream(file);
        OutputStream out = res.getOutputStream();
        byte buffer[] = new byte[1024];
        int lne = 0;
        //循环读取
        while ((lne = in.read(buffer)) > 0) {
            out.write(buffer, 0, lne);
        }
        in.close();
        out.close();
    }
}
