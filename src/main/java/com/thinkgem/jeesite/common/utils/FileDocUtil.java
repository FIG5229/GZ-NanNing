package com.thinkgem.jeesite.common.utils;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

/**
 * Created by shawn on 2017/4/11.
 */
public class FileDocUtil {
    static Tika tika = new Tika();

    public static void main(String[] args) {
        try {
            String path = "D:/politics//userfiles/test/files/20191107/0920/index.jsp";
//            String path = "D:/politics//userfiles/test/files/20191107/0920/index.jsp";
//            path = "D:\\分公司\\李晓川个人简历-专家级分析设计师.doc";
//            path = "D:\\workspaces\\embrace\\politics\\LICENSE.txt";
            path = "D:\\分公司\\合同\\4.jpg";
            System.out.println(FileDocUtil.detect(path));
            String text = FileDocUtil.parseText(path);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    public static String parseText(String path) throws IOException, TikaException {
        File file = new File(path);
        //tika.detect(file);
        String text = tika.parseToString(file);
        return text;
    }


    public static String detect(String path) throws IOException {
        File file = new File(path);
        return tika.detect(file);
    }

    public static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }
}
