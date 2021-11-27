package com.thinkgem.jeesite.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @program: politics
 * @description: 异常信息获取类
 * @author: kevin.jia
 * @create: 2021-06-30 16:08
 **/

public class ExceptionInfoUtils {

    //异常信息
    public static String getExceptionInfo(Exception e){
        String message = e.toString();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            message = new String(baos.toByteArray());
            baos.close();
        }catch (Exception e2){
           e2.toString();
           message = e2.toString();
        }
        return message;

    }

    public static String getExceptionCauseInfo(Exception e){
        Throwable cause = e.getCause();
        String message = e.getClass().getTypeName();
        try {
            if(cause==null){
                message = e.getMessage();
            }else{
                message = cause.getMessage();
            }
            if(StringUtils.isBlank(message)){
                message = e.getClass().getName();
            }
        }catch (Exception e1){
            message = e.getClass().getName();
        }
        return message;

    }
}
