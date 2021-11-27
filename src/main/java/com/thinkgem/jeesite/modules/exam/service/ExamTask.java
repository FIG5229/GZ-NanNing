package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

@Service
@Lazy(false)
public class ExamTask {

    private static final Logger log = LoggerFactory.getLogger(ExamTask.class);

    @Autowired
    private  ExamAutoEvaluationService examAutoEvaluationService;


    /**
     * 当前服务器时间慢三个多小时
     */
    /*每天凌晨三十分执行一次*/
    //@Scheduled(cron = "0 */5 * * * ?")//五分钟执行一次
    @Scheduled(cron = "0 30 0 0/1 * ?")
    public  void autoExamByDay() {

        try {

            log.info("绩效自动考评：每天执行一次");

            /**
             * 修改时间测试
             */
            Calendar calendar = Calendar.getInstance();
            int year1 = calendar.get(Calendar.YEAR);
            int month1 = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            String year = String.valueOf(year1);
            String month = String.valueOf(month1);
            String newDay;
            if (day-1>0){
                newDay = String.valueOf(day-1);
            }else {
                newDay = "01";
            }
            String everyDayTime = year+"-"+month+newDay;
            String time = null;
            /*三月时  时间为 03*/
            if (month.length()==1){
                month = "0"+month;
            }


          /*每月25号到上月26号，无需判断当前月份是否为一月*/
            time = year + "-" + (month);
            {
                /*每天的*/

                try{
                    //局考处  宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.jkcKgpmYear(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //公安处领导  宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.gacKgpmYear(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //中基层  宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.zjcKgpmYear(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警  宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.mjKgpmYear(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //局考处 宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.jkcKgpm(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //处考处 宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.ckcKgpm(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //处考派出所 宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.ckpcsKgpm(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //公安处领导 宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.gacKgpm(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //中基层 宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.zjcKgpm(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //民警 宣传思想-新闻宣传-稿件汇总-刊稿排名
                    examAutoEvaluationService.mjKgpm(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    //局机关谈话函询
                    examAutoEvaluationService.juTalkManagementMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //处机关谈话函询
                    examAutoEvaluationService.chuTalkManagementMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //派出所谈话函询
                    examAutoEvaluationService.suochuTalkManagementMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //普通民警谈话函询
                    examAutoEvaluationService.minTalkManagementMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局机关谈话函询
                    examAutoEvaluationService.juTalkManagementYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //处机关谈话函询
                    examAutoEvaluationService.chuTalkManagementYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //派出所谈话函询
                    examAutoEvaluationService.suoTalkManagementYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //普通民警谈话函询
                    examAutoEvaluationService.minTalkManagementYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局机关纪律处分
                    examAutoEvaluationService.juDisciplinaryActionMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处机关纪律处分
                    examAutoEvaluationService.chuDisciplinaryActionMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //派出所纪律处分
                    examAutoEvaluationService.suoDisciplinaryActionMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警纪律处分
                    examAutoEvaluationService.minDisciplinaryActionMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局机关纪律处分
                    examAutoEvaluationService.juDisciplinaryActionYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处机关纪律处分
                    examAutoEvaluationService.chuDisciplinaryActionYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //派出所纪律处分
                    examAutoEvaluationService.suoDisciplinaryActionYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                //民警纪律处分
                try{
                    examAutoEvaluationService.minDisciplinaryActionYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //公安处领导班子 月 宣传思想-奖项维护
                    examAutoEvaluationService.gacJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //公安处领导班子 年  宣传思想-奖项维护
                    examAutoEvaluationService.gacJxwhYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //中基层 月  宣传思想-奖项维护
                    examAutoEvaluationService.zjcJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //中基层 年  宣传思想-奖项维护
                    examAutoEvaluationService.zjcJxwhYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警 月 宣传思想-奖项维护
                    examAutoEvaluationService.mjJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警 年  宣传思想-奖项维护
                    examAutoEvaluationService.mjJxwhYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所 月  宣传思想-奖项维护
                    examAutoEvaluationService.ckpcsJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处 月  宣传思想-奖项维护
                    examAutoEvaluationService.ckcJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考局 月  宣传思想-奖项维护
                    examAutoEvaluationService.jkjJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考局 年  宣传思想-奖项维护
                    examAutoEvaluationService.jkjJxwhYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处 月  宣传思想-奖项维护
                    examAutoEvaluationService.jkcJxwh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处 年  宣传思想-奖项维护
                    examAutoEvaluationService.jkcJxwhYear(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //公安处领导班子考核（局）  考勤管理，旷工
                    examAutoEvaluationService.checkOut(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //中基层领导班子 考勤管理，旷工
                    examAutoEvaluationService.zjcCkeckOut(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警 考勤管理，旷工
                    examAutoEvaluationService.mjCkeckOut(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //奖励信息（党建工作-组织）-月度-局考处
                    examAutoEvaluationService.jkcJlxx(year+"-"+month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-年度-局考处
                    examAutoEvaluationService.jkcJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-月度-局考局机关
                    examAutoEvaluationService.jkjJlxx(year+"-"+month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-年度-局考局机关
                    examAutoEvaluationService.jkjJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-月度-处考出机关
                    examAutoEvaluationService.ckcJlxx(year+"-"+month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-年度-处考出机关
                    examAutoEvaluationService.ckcJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-月度-处考派出所
                    examAutoEvaluationService.ckpcsJlxx(year+"-"+month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-年度-处考派出所
                    examAutoEvaluationService.ckpcsJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-月度-处领导
                    examAutoEvaluationService.cldJlxx(year+"-"+month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-年度-处领导
                    examAutoEvaluationService.cldJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-月度-中基层
                    examAutoEvaluationService.zjcJlxx(year+"-"+month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作-组织）-年度-中基层
                    examAutoEvaluationService.zjcJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                //辖区发生火灾事故  局考处
                examAutoEvaluationService.juKaoChuFireMonth(time.substring(0,4),time.substring(time.length(),2),everyDayTime);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                //辖区发生火灾事故  局考局机关
                examAutoEvaluationService.juKaoJuJiGuanFireMonth(time.substring(0,4),time.substring(time.length(),2),everyDayTime);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                //辖区发生火灾事故  处考处机关
                examAutoEvaluationService.chuKaoChuJiGuanFireMonth(time.substring(0,4),time.substring(time.length(),2),everyDayTime);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                //辖区发生火灾事故  处考所
                examAutoEvaluationService.chuKaoSuoFireMonth(time.substring(0,4),time.substring(time.length(),2),everyDayTime);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //辖区发生火灾事故  处领导
                    examAutoEvaluationService.chuLingDaoFireMonth(time.substring(0,4),time.substring(time.length(),2),everyDayTime);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //高铁入网-月度-局考处
                    examAutoEvaluationService.gtrwJKCByMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //高铁入网-年度-局考处
                    examAutoEvaluationService.gtrwJKCByYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //高铁入网-月度-处考派出所
                    examAutoEvaluationService.gtrwCkdsByMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //路外伤亡-月度-局考处
                    examAutoEvaluationService.jkcLWSWByMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //路外伤亡-年度-局考处
                    examAutoEvaluationService.jkcLWSWByYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //警综平台 -路外伤亡-月度-处考处机关
                    examAutoEvaluationService.ckcjgLWSWByMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    //警综平台 -路外伤亡-月度-处考队所
                    examAutoEvaluationService.ckpcsLWSWByMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                //惩戒信息-党建工作
                try{
                    examAutoEvaluationService.jkcCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.jkjCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckcCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckpcsCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckldbzCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    examAutoEvaluationService.zjcCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjCjYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    examAutoEvaluationService.jkcCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.jkjCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckcCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckpcsCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    examAutoEvaluationService.ckldbzCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.zjcCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjCjMonrh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //辖区发生火灾事故  年度
                try {
                    examAutoEvaluationService.juKaoChuFireYear("",time.substring(0,4),everyDayTime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    examAutoEvaluationService.chuKaoChuJiGuanFireYear("",time.substring(0,4),everyDayTime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    examAutoEvaluationService.chuKaoSuoFireYear("",time.substring(0,4),everyDayTime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    examAutoEvaluationService.chuLingDaoFireYear("",time.substring(0,4),everyDayTime);
                }catch (Exception e){
                    e.printStackTrace();
                }

                /**
                 * 奖励信息党建个人*/

                try{
                    examAutoEvaluationService.grjkcYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grjkjjgYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckcjgYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckpcsYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.gacJlxxGrYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.zjcJlxxGrYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjgrYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjgrMonrh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.zjcJlxxGr(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckpcsMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckcjgMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grjkjjgMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grjkcMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.gacJlxxGr(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                /*方法内部增加2月1号判断*/
                try{
                    //处考派出所   团员教育评议
                    examAutoEvaluationService.tuanyuanCkpYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }



            }

            /*每月的*/
            Date date = new Date();
            /*自然月*/
            if (isFirstDayOfMonth(date)) {
                String month2 = String.valueOf(month1);//自然月，每月一号，抓取上一月数据
                if (month1 - 1 == 0) {
                    time =  (year1 - 1) + "-" + 12;
                    month2 = "12";
                } else {
                    month2 = String.valueOf(month1-1);
                    /*三月时  时间为 03*/
                    if (month2.length()==1){
                        month2 = "0"+month2;
                    }
                    time = year + "-" + (month2);
                }
                try {
//                    examAutoEvaluationService.talkRecord(time.substring(0,4),time.substring(time.length()-2));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //处考派出所 对精神病回访
                    examAutoEvaluationService.ckpcsHf(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //上报团内图片
                    examAutoEvaluationService.ckpcsTntp(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //团支部 三会一课
                    examAutoEvaluationService.tyShyk(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //党委三会一课  中基层
                    examAutoEvaluationService.zjcShyk(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //党委三会一课  处考派出所
                    examAutoEvaluationService.ckpcsShyk(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //党委三会一课  处考处
                    examAutoEvaluationService.ckcShyk(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //党委三会一课  局考局
                    examAutoEvaluationService.jkjShyk(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //党委三会一课  局考处
                    examAutoEvaluationService.jkcShyk(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.jkcXcsxfx(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.ckcXcsxfx(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.ckpcsXcsxfx(time);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    //局考处 高铁卫士-对精神病患回访
                    examAutoEvaluationService.jkcHf(time);
                }catch (Exception e){
                    e.printStackTrace();
                }



                try{
                    //局考局机关  日常学习
                    examAutoEvaluationService.jkjRc(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //处考处  日常学习
                    examAutoEvaluationService.ckcRc(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所  日常学习
                    examAutoEvaluationService.ckpcsRc(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处  单位政治学习-- 学习平台
                    examAutoEvaluationService.ckcXxpt(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所  单位政治学习-- 学习平台
                    examAutoEvaluationService.ckpcsXxpt(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //公安处领导班子考核  单位政治学习-- 学习平台
                    examAutoEvaluationService.gacXxpt(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //中基层领导班子考核  单位政治学习-- 学习平台
                    examAutoEvaluationService.zjcXxpt(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警考核（局处）  单位政治学习-- 学习平台
                    examAutoEvaluationService.mjXxpt(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //=======================================================================
               /* try{
                    //局考局机关  单位政治学习-- 宣传思想-经常性思想工作-谈心家访-谈心
                    examAutoEvaluationService.jkjTx(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处机关  单位政治学习-- 宣传思想-经常性思想工作-谈心家访-谈心
                    examAutoEvaluationService.ckcTx(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/

                try{
                    //处考处  宣传思想-警营文化-读书活动-活动情况
                    examAutoEvaluationService.ckcHdqkMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所  宣传思想-警营文化-读书活动-活动情况
                    examAutoEvaluationService.ckpcsHdqkMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }


                /*
                12.15  经客户要求不进行考评
                try{
                    //处考派出所    109联侦平台
                    examAutoEvaluationService.ckpcsLianZhenMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    // 处领导换届选举
                    examAutoEvaluationService.chuLdElectionMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //基层领导换届选举
                    examAutoEvaluationService.jiLdElectionMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考局 换届选举
                    examAutoEvaluationService.juUnitElectionMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处换届选举
                    examAutoEvaluationService.chuElectionMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //初考队所换届选举
                    examAutoEvaluationService.duiElectionMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处换届选举
                    examAutoEvaluationService.juChuElectionMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //基层领导党日活动
                    examAutoEvaluationService.jiPartyDayActivitiesMonth(year, month2);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //局党日活动
                    examAutoEvaluationService.juPartyDayActivitiesMonth(year, month2);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处党日活动
                    examAutoEvaluationService.chuPartyDayActivitiesMonth(year, month2);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //支队党日活动
                    examAutoEvaluationService.duiPartyDayActivitiesMonth(year, month2);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处党日活动
                    examAutoEvaluationService.juChuPartyDayActivitiesMonth(year, month2);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处机关会员福利
                    examAutoEvaluationService.chuWelfareCondolencsMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //派出所会员福利
                    examAutoEvaluationService.suoWelfareCondolencsMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //派出所会员福利
                    examAutoEvaluationService.suoWelfareCondolencsMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*1月21与处绩效办沟通慰问工作无需考核*/
               /* try{
                    //处机关慰问工作
                    examAutoEvaluationService.chuConsolationWorkInfoMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //派出所慰问工作
                    examAutoEvaluationService.suoConsolationWorkInfoMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/


                //局考局机关-教育训练-基本知识(未参加)
                try {
                    examAutoEvaluationService.juBasicKnowledgeMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考处机关-教育训练-基本知识（未参加）
                try {
                    examAutoEvaluationService.chuBasicKnowledgeMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考派出所-教育训练-基本知识（未参加）
                try {
                    examAutoEvaluationService.duiBasicKnowledgeMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考处机关-教育训练-基本知识（未通过）
                try {
                    examAutoEvaluationService.chuNoPassBasicKnowledgeMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考派出所-教育训练-基本知识（未通过）
                try {
                    examAutoEvaluationService.duiNoPassBasicKnowledgeMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //局考局机关-教育训练-练兵比武台账录入次数
                try {
                    examAutoEvaluationService.juLedgerEntryTimesMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考处机关-教育训练-练兵比武台账录入次数
                try {
                    examAutoEvaluationService.chuLedgerEntryTimesMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考派出所-教育训练-练兵比武台账录入次数
                try {
                    examAutoEvaluationService.duiLedgerEntryTimesMonth(year, month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }


                //奖惩信息（党建工作）
                /*
                //调整至每天执行一次的定时任务中
                try{
                    examAutoEvaluationService.jkcCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.jkjCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckcCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckpcsCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckldbzCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.zjcCjMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjCjMonrh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                //党建工作  个人
                /*try{
                    examAutoEvaluationService.mjgrMonrh(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.zjcJlxxGr(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckpcsMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckcjgMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grjkjjgMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grjkcMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.gacJlxxGr(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                }*/
                try{
                    //党委中心组学习
                    examAutoEvaluationService.jkcDwzxzxx(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*try{
                     //学习平台
                     examAutoEvaluationService.jkcXxpt(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/

                try{
                    //宣传思想-警营文化-读书活动-活动情况
                    examAutoEvaluationService.jkcDshd(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //上报团内图片信息3条，其中北海处1条
                    examAutoEvaluationService.jkcTntp(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.jkcWechar(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.jkjWechar(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.ckcWechar(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.ckpcsWechar(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*12.23   调至每日*/
                /*try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.jkcJlxx(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                 try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.jkjJlxx(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.ckcJlxx(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.ckpcsJlxx(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.cldJlxx(time);
                }catch (Exception e){
                   e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.zjcJlxx(time);
                }catch (Exception e){
                    e.printStackTrace();
                }
*/

               /* try{
                    //处考派出所推优入党
                    examAutoEvaluationService.ckpcsTyrd(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
            }
            /*日历月*/
            if (isNewMonth(date)){
                try{
                    //局考处高铁卫士
                    examAutoEvaluationService.jkcGtws(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所高铁卫士
                    examAutoEvaluationService.ckpcsGtws(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //警综-局考处毒品查缉
                    examAutoEvaluationService.jkcJzDpcj(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //警综-局考处撞轧牲畜
                    examAutoEvaluationService.jkcJzZysc(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //警综-局考局撞轧牲畜
                    examAutoEvaluationService.jkjJzZysc(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    /*撞轧牲畜-处考派出所-月度*/
                    examAutoEvaluationService.ckpcsZYSCByMonth(year,month);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /**
                 * 反馈问题（组干问题1.28--第39行）说是不考了
                 */
                /*try{
                    //警综-局考处  案件3日内未侦破
                    examAutoEvaluationService.jzptjkcMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //警综-处考派出所  案件3日内未侦破
                    examAutoEvaluationService.jzptckpcsMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    examAutoEvaluationService.ckcjgZYSCByMonth(String.valueOf(year1), month);//撞轧牲畜-处考处机关-月度
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.jkcBMMJJCAJ(year, month);//百名民警局考处月度
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                // 3.22 未在aj_xs_001  表  ajlb字段发现危及行车案件
                /*//刑侦危及行车  局考处
                try{
                    examAutoEvaluationService.jkcWxxsajMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                //刑侦危及行车  处考派出所
                try{
                    examAutoEvaluationService.ckpcsWxxsajMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                //刑侦危及行车  公安处领导
                try{
                    examAutoEvaluationService.gacldWxxsajMonth(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/


            }
            /*每季度的 季度结束的第一天*/
            if ((month1 == 4 || month1 == 7 || month1 == 10 || month1 == 1)) {
                /*日历月*/
                if (day == 26){
                    try{
                        //局考处-高铁卫士-对精神病患回访
                        examAutoEvaluationService.jkcHfQuarter(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                }
                /*自然月*/
                if (day == 1){

                    try{
                        //局考处党员队伍思想分析
                        examAutoEvaluationService.jkcSxfx(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                    try{
                        //局考局党员队伍思想分析
                        examAutoEvaluationService.dyjkjSxfx(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                    try{
                        //处考处党员队伍思想分析
                        examAutoEvaluationService.dyckcSxfxQuarter(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                    try{
                        //处考派出所党员队伍思想分析，未按时录入
                        examAutoEvaluationService.dyckpcSxfxQuarter(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                    try{
                        //中基层党员队伍思想分析
                        examAutoEvaluationService.zjcSxfxQuarter(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                    try{
                        examAutoEvaluationService.gacSxfxQuarter(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }

                    try{
                        examAutoEvaluationService.ckpcsHfQuarter(time);
                    }catch (Exception e){
                        log.error(getExceptionInfo(e));
                        e.printStackTrace();
                    }
                }
                //String time = year + "-" + month;
               /* 第一季度结束的第一天
                try{
                    examAutoEvaluationService.dzbdydhJd(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
*/
                /*发展党员思想汇报*/
                try {
//                    examAutoEvaluationService.recruitingPartyMembers(time.substring(0,4),time.substring(time.length()-2));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
//        else if (month1==7 && day==1){
//            /*第二季度结束的第一天*/
//        }else if (month1 ==10 && day ==1){
//            /*第三季度结束的第一天*/
//        }else if (month1==1 && day ==1){
//            /*第四季度结束的第一天*/
//        }
            /*每半年的 日历月*/
                String realTime = "";
            if ((month1 == 6) || (month1 == 12 ) && day ==26) {
                /*日历月*/
                if (day == 26){
                    if (month1 == 6 && day == 26){
                        realTime =  "06" + "-" + "25";
                    }else if (month1 == 12 && day == 26){
                        realTime = "12" + "-" + "25";
                    }
                }


                //String time = year + "-" + month;

                try{
                    //局考处-高铁安保-对精神病患回访
                    examAutoEvaluationService.jkcHfHalf(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    examAutoEvaluationService.ckpcsHfHalf(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //团委思想分析
                    examAutoEvaluationService.juChuThought(realTime,year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //团委工作信息
                    examAutoEvaluationService.juChuWorkInfo(realTime,year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //团委工作总结
                    examAutoEvaluationService.juChuWorkSummary(realTime,year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
//            if (month1-1>0){
//                System.out.println("上半年");
//            }else {
//                System.out.println("下半年");
//            }

            }

            /*每半年的  自然月*/
            /*自然月*/
            if ((month1 == 7) || (month1 == 1 ) && day ==1){
                if (month1 == 7 ){
                    realTime =  "06" + "-" + "30";
                }else if (month1 == 1 && day == 1){
                    realTime = "12" + "-" + "31";
                }
            }
            /*每年的 自然月*/
            if (isFirstYearOfLastMonth(date)) {


                try{
                    //处考派出所 对精神病回访
                    examAutoEvaluationService.ckpcsHfYear(year);
                }catch (Exception e){
                    System.out.println(e.getCause());
                    e.printStackTrace();
                }
                try{
                    //局考处 上报团内图片
                    examAutoEvaluationService.jkcTntpYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所 上报团内图片
                    examAutoEvaluationService.ckpcsTntpYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    // 党委三会一课 处考队所
                    examAutoEvaluationService.zjcShykYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    // 党委三会一课 处考队所
                    examAutoEvaluationService.ckpcsShykYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    // 党委三会一课 处考处
                    examAutoEvaluationService.ckcShykYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    // 党委三会一课 局考局
                    examAutoEvaluationService.jkjShykYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    // 党委三会一课 局考处
                    examAutoEvaluationService.jkcShykYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //党员队伍思想分析
                    examAutoEvaluationService.jkcSxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.ckpcsXcsxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.ckcXcsxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.jkcXcsxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处  高铁卫士-对精神病患回访
                    examAutoEvaluationService.jkcHfYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所  团内民主生活会
                    examAutoEvaluationService.ckpcsTnmzYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处  团内民主生活会
                    examAutoEvaluationService.jkcTnmzYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*try{
                    //公安处领导班子 考勤管理，旷工
                    examAutoEvaluationService.checkOutYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    //中基层领导班子 考勤管理，旷工
                    examAutoEvaluationService.zjcCkeckOutYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //民警 考勤管理，旷工
                    examAutoEvaluationService.mjCkeckOutYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考局机关 志愿服务
                    examAutoEvaluationService.zhiyuanYearJKJ(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处机关 志愿服务
                    examAutoEvaluationService.zhiyuanCkcYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所 志愿服务
                    examAutoEvaluationService.zhiyuanCkpYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //中基层 志愿服务
                    examAutoEvaluationService.zjcZhiyuanYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处 志愿服务
                    examAutoEvaluationService.jkcZyYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }



                /*try{
                    //处考处机关   宣传思想-理论学习-党委中心组学习
                    examAutoEvaluationService.ckcDwzxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/

                try{
                    //局考局机关   日常学习
                    examAutoEvaluationService.jkjRcYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处   日常学习
                    examAutoEvaluationService.ckcRcYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所   日常学习
                    examAutoEvaluationService.ckpcsRcYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处   单位政治学习-- 学习平台
                    examAutoEvaluationService.ckcXxptYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所      单位政治学习-- 学习平台
                    examAutoEvaluationService.ckpcsXxptYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //公安处领导班子考核    单位政治学习--学习平台
                    examAutoEvaluationService.gacXxptYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //中基层领导班子考核   单位政治学习-- 学习平台
                    examAutoEvaluationService.zjcXxptYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                 try{
                     //民警考核（局处）   单位政治学习-- 学习平台
                     examAutoEvaluationService.njXxptYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*try{
                    //局考局机关   宣传思想-经常性思想工作-谈心家访-谈心
                    examAutoEvaluationService.jkjTxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处机关    宣传思想-经常性思想工作-谈心家访-谈心
                    examAutoEvaluationService.ckcTxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/

                try{
                    //处考派出所    宣传思想-经常性思想工作-思想分析
                    examAutoEvaluationService.ckpcsXcsxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //处考处 宣传思想-警营文化-读书活动-活动情况
                    examAutoEvaluationService.ckcHdqkYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所 宣传思想-警营文化-读书活动-活动情况
                    examAutoEvaluationService.ckpcsHdqkYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*try{
                    //处考派出所       团内民主组织生活会
                    examAutoEvaluationService.ckpcsMzYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    //局考局       廉政教育
                    examAutoEvaluationService.jkjLzjy(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                 try{
                     // 处考处           廉政教育
                     examAutoEvaluationService.ckcLzjy(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                 try{
                     //处考处      政工简报
                     examAutoEvaluationService.ckcZgjb(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所    政工简报
                    examAutoEvaluationService.ckpcsZgjb(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*try{
                    //宣传思想-立功创模-荣誉管理-奖项维护
                    examAutoEvaluationService.gacJxwh(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    //局考局   党员队伍思想分.析，未按时录入
                    examAutoEvaluationService.dyjkjSxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考处       党员队伍思想分析，未按时录入
                    examAutoEvaluationService.dyckcSxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所   党员队伍思想分.析，未按时录入
                    examAutoEvaluationService.dyckpcSxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }


                try{
                    //公安处领导班子考核（局）     党员队伍思想分析，未按时录入
                    examAutoEvaluationService.gacSxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //中基层领导班子考核      党员队伍思想分析，未按时录入
                    examAutoEvaluationService.zjcSxfxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*try{
                    //处考派出所          宣传思想-意识形态
                    examAutoEvaluationService.ckpcsYsxt(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    //处领导换届选举
                    examAutoEvaluationService.chuLdElectionYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //基层领导换届选举
                    examAutoEvaluationService.jiLdElectionYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考局换届选举
                    examAutoEvaluationService.juUnitElectionYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处换届选举
                    examAutoEvaluationService.chuElectionYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //初考队所换届选举
                    examAutoEvaluationService.duiElectionYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处换届选举
                    examAutoEvaluationService.juChuElectionYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

               try{
                   //基层党日活动
                   examAutoEvaluationService.jiPartyDayActivitiesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

               try{
                   //局党日活动
                   examAutoEvaluationService.juPartyDayActivitiesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处党日活动
                    examAutoEvaluationService.chuPartyDayActivitiesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //支队党日活动
                    examAutoEvaluationService.duiPartyDayActivitiesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处党日活动
                    examAutoEvaluationService.juChuPartyDayActivitiesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处领导党组织书记测评
                    examAutoEvaluationService.chuLdAssessYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //基层领导党组织书记
                    examAutoEvaluationService.jiAssessYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局党组织书记
                    examAutoEvaluationService.juAssessYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处党组织书记
                    examAutoEvaluationService.chuAssessYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //支队党组织书记
                    examAutoEvaluationService.duiAssessYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //局考处党组织书记
                    examAutoEvaluationService.juChuAssessYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //党委中心组学习
                    examAutoEvaluationService.jkcDwzxzxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                 /*try{
                     //学习平台
                     examAutoEvaluationService.jkcXxptYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    //活动情况
                    examAutoEvaluationService.jkcDshdYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //每季编发廉政教育资料
                    examAutoEvaluationService.jkcLzjyYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.jkcWecharYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.jkjWecharYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.ckcWecharYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //微信接警不及时
                    examAutoEvaluationService.ckpcsWecharYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //政工简报
                    examAutoEvaluationService.syZgjbYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //宁铁公安
                    examAutoEvaluationService.syNtgaYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*12.23  调动至每日*/
                /*try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.jkcJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
               /* try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.jkjJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
               /* try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.ckcJlxxYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
               /* try {
                    //奖励信息（党建工作，组织） 处考队所
                    examAutoEvaluationService.ckpcsJlxxYear(year);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                /*try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.cldJlxxYear(year);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.zjcJlxxYear(year);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    //奖励信息（党建工作，组织）
                    examAutoEvaluationService.zjcJlxxYear(year);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                try{
                    //局机关会员福利
                    examAutoEvaluationService.juWelfareCondolencsYear(year,"");
                }catch (Exception e){
                    log.error("自动考评-会员福利，局考局机关-年度，发生错误，错误信息："+e.toString());
                    e.printStackTrace();
                }
                try{
                    //处机关会员福利
                    examAutoEvaluationService.chuWelfareCondolencsYear(year,"");
                }catch (Exception e){
                    log.error("自动考评-会员福利，处考处机关-年度，发生错误，错误信息："+e.toString());
                    e.printStackTrace();
                }
                try{
                    //派出所会员福利
                    examAutoEvaluationService.suoWelfareCondolencsYear(year,"");
                }catch (Exception e){
                    log.error("自动考评-会员福利，处考派出所-年度，发生错误，错误信息："+e.toString());
                    e.printStackTrace();
                }
                /*
                12.15  经客户要求不进行考评
                try{
                    //109
                    examAutoEvaluationService.ckpcsLianZhenYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //109
                    examAutoEvaluationService.gacldbzLianZhenYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //109
                    examAutoEvaluationService.jkcLianZhenYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                */
                //奖惩信息（党建工作）
                /*
                //调整至每天的定时任务中
                try{
                    examAutoEvaluationService.jkcCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.jkjCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckcCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckpcsCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.ckldbzCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    examAutoEvaluationService.zjcCjYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjCjYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                try{
                    //智慧政工-民警体检
                    examAutoEvaluationService.mjtjYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //工会文体活动记录
                    examAutoEvaluationService.ghhdYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                //党建 个人

               /*
                try{
                    examAutoEvaluationService.grjkcYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grjkjjgYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                 try{
                     examAutoEvaluationService.grckcjgYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.grckpcsYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.gacJlxxGrYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                 try{
                     examAutoEvaluationService.zjcJlxxGrYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    examAutoEvaluationService.mjgrYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

*/
                try{
                    //局机关慰问工作
                    examAutoEvaluationService.juConsolationWorkInfoYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /*1月22 与处绩效办沟通慰问工作无需考核*/
               /* try{
                    //处机关慰问工作
                    examAutoEvaluationService.chuConsolationWorkInfoYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    //派出所慰问工作
                    examAutoEvaluationService.suoConsolationWorkInfoYear(year,"");
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/



               /* try{
                    examAutoEvaluationService.gtrwJKCByYear(String.valueOf(year1 - 1));//高铁入网-局考处-年度
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/

                try{
                    examAutoEvaluationService.ckcjgLWSWByYear(year);//路外伤亡-处考处机关-年度
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                try{
                    examAutoEvaluationService.ckpcsLWSWByYear(year);//路外伤亡-处考队所-年度
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }



                //处考处机关-教育训练-基本知识(未参加)
                try {
                    examAutoEvaluationService.chuBasicKnowledgeYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考派出所-教育训练-基本知识(未参加)
                try {
                    examAutoEvaluationService.duiBasicKnowledgeYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考处机关-教育训练-练兵台账录入次数
                try {
                    examAutoEvaluationService.chuLedgerEntryTimesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                // 处考派出所-教育训练-练兵台账录入次数
                try {
                    examAutoEvaluationService.duiLedgerEntryTimesYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                // 局考处-百名民警缉查案件-年度
                try {
                    examAutoEvaluationService.jkcBMMJJCAJYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }

                //一帮一
                try{
                    examAutoEvaluationService.ybyYear(time);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }





                try {
//                    examAutoEvaluationService.formalParty(time.substring(0,4),time.substring(time.length()-2));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            /*每年的 日历月*/
            if (isNewYear(date)){

                try{
                    //局考处高铁卫士
                    examAutoEvaluationService.jkcGtwsYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //处考派出所高铁卫士
                    examAutoEvaluationService.ckpcsGtwsYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //局考处-警综  毒品查缉
                try{
                    examAutoEvaluationService.jkcJzDpcjYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //局考处-警综  撞轧牲畜
                try{
                    examAutoEvaluationService.jkcJzZyscYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //局考局-警综  撞轧牲畜
                try{
                    examAutoEvaluationService.jkjJzZyscYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try {
                    /*撞轧牲畜-处考派出所-年度*/
                    examAutoEvaluationService.ckpcsZYSCByYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                try{
                    //警综-处考处撞轧牲畜
                    examAutoEvaluationService.ckcjgZYSCByYear(year);
                }catch (Exception e){
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                /**
                 * 反馈问题（组干问题1.28--第39行）说是不考了
                 */
                //局考局-警综  案件3日内未侦破
                /*try{
                    examAutoEvaluationService.jzptjkcYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                //处考派出所-警综  案件3日内未侦破
                try{
                    examAutoEvaluationService.jzptckpcsYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/
                // 3.22 未在aj_xs_001 表  ajlb字段发现危及行车案件
                // 刑侦危及行车 局考处
                /*try{
                    examAutoEvaluationService.jkcWxxsajYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                 // 刑侦危及行车 处考派出所
                try{
                    examAutoEvaluationService.ckpcsWxxsajYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }
                 // 刑侦危及行车 公安处领导
                try{
                    examAutoEvaluationService.gacldWxxsajYear(year);
                }catch (Exception e) {
                    log.error(getExceptionInfo(e));
                    e.printStackTrace();
                }*/



            }

        } catch (Exception e) {
            e.printStackTrace();
            log.debug("自动考评异常");
        } finally {

        }
        log.info("-------------------------------------------自动考评结束--------------------------------------------------------");
    }

    /**
     * 自然月第一天
     * @param date
     * @return
     */
    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        System.out.println(calendar.get(Calendar.MONTH));
      return calendar.get(Calendar.DAY_OF_MONTH) == 1;
        //  return true;
    }

    /**
     * 日历月  第一天
     * @param date
     * @return
     */
    public static boolean isNewMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        System.out.println(calendar.get(Calendar.MONTH));
      return calendar.get(Calendar.DAY_OF_MONTH) == 26;
        //  return true;
    }

    public static boolean isFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        System.out.println(calendar.get(Calendar.MONTH));
       return calendar.get(Calendar.DAY_OF_YEAR) == 1;
        //  return true;

    }

    public static boolean isFirstYearOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MONTH)+1==12){
            return calendar.get(Calendar.DAY_OF_MONTH) == 1;
        }else {
            return false;
        }
    }

    public static boolean isNewYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MONTH)+1==12){
            return calendar.get(Calendar.DAY_OF_MONTH) == 11;
        }else {
            return false;
        }
    }
    //异常信息打印到日志
    public static String getExceptionInfo(Exception e){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            return baos.toString();
        }catch (Exception e2){
            return e.toString();
        }
        
    }
}
