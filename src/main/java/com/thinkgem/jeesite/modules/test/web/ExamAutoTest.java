
package com.thinkgem.jeesite.modules.test.web;

import com.thinkgem.jeesite.modules.exam.service.ExamAutoEvaluationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring-context.xml"})
public class ExamAutoTest {

    @Autowired
    ExamAutoEvaluationService examAutoEvaluationService;


    @Test/*pass*/
    public void checkOut(){
        examAutoEvaluationService.checkOut("2020-01");
    }
    @Test /*pass*/
    public void checkOutYear(){
        examAutoEvaluationService.checkOutYear("2020");
    }
    @Test/*pass*/
    public void zjcCkeckOut(){
        examAutoEvaluationService.zjcCkeckOut("2020-06");
    }
    @Test/*pass*/
    public void zjcCkeckOutYear(){
        examAutoEvaluationService.zjcCkeckOutYear("2020");
    }
    @Test/*pass*/
    public void mjCkeckOut(){
        examAutoEvaluationService.mjCkeckOut("2020-12");
    }
    @Test/*pass*/
    public void mjCkeckOutYear(){
        examAutoEvaluationService.mjCkeckOutYear("2020");
    }
    /*@Test*//*pass*//*
    public void ckcDwzx(){
        examAutoEvaluationService.ckcDwzx("2020-02");
    }*/
    /*@Test*//*pass*//*
    public void ckcDwzxYear(){
        examAutoEvaluationService.ckcDwzxYear("2020");
    }*/
    @Test/*pass*/
    public void jkjRc(){
        examAutoEvaluationService.jkjRc("2020-05");
    }
    @Test/*pass*/
    public void jkjRcYear(){
        examAutoEvaluationService.jkjRcYear("2020");
    }
    @Test/*pass*/
    public void ckcRc(){
        examAutoEvaluationService.ckcRc("2020-10");
    }
    @Test/*pass*/
    public void ckcRcYear(){
        examAutoEvaluationService.ckcRcYear("2020");
    }
    @Test/*pass*/
    public void ckpcsRc(){
        examAutoEvaluationService.ckpcsRc("2020-10");
    }
    @Test/*pass*/
    public void ckpcsRcYear(){
        examAutoEvaluationService.ckpcsRcYear("2020");
    }
    /* @Test*//*pass*//*
    public void jkjXxpt(){
        examAutoEvaluationService.jkjXxpt("2020-10");
    }*/
    @Test/*pass*/
    public void ckcXxpt(){
        examAutoEvaluationService.ckcXxpt("2020-10");
    }
    @Test/*pass*/
    public void ckcXxptYear(){
        examAutoEvaluationService.ckcXxptYear("2020");
    }
    @Test/*pass*/
    public void ckpcsXxpt(){
        examAutoEvaluationService.ckpcsXxpt("2020-05");
    }
    @Test/*pass*/
    public void ckpcsXxptYear(){
        examAutoEvaluationService.ckpcsXxptYear("2020");
    }
    @Test/*pass*/
    public void gacXxpt(){
        examAutoEvaluationService.gacXxpt("2020-07");
    }
    @Test/*pass*/
    public void gacXxptYear(){
        examAutoEvaluationService.gacXxptYear("2021");
    }
    @Test/*pass*/
    public void zjcXxpt(){
        examAutoEvaluationService.zjcXxpt("2020-02");
    }
     @Test/*pass*/
    public void zjcXxptYear(){
        examAutoEvaluationService.zjcXxptYear("2021");
    }
    @Test/*pass*/
    public void mjXxpt(){
        examAutoEvaluationService.mjXxpt("2021-04");
    }
    @Test/*pass*/
    public void njXxptYear(){
        examAutoEvaluationService.njXxptYear("2020");
    }
   /* @Test
    public void jkjTx(){
        examAutoEvaluationService.jkjTx("2020-05");
    }
    @Test
    public void jkjTxYear(){
        examAutoEvaluationService.jkjTxYear("2020");
    }
    @Test
    public void ckcTx(){
        examAutoEvaluationService.ckcTx("2020-05");
    }
    @Test
    public void ckcTxYear(){
        examAutoEvaluationService.ckcTxYear("2020");
    }
    @Test*//*数据问题  一个单位返回两个id    南宁铁路公安处党委*//*
    public void ckpcsTxMonth(){
        examAutoEvaluationService.ckpcsTxMonth("2020-05");
    }
    @Test*//*数据问题  一个单位返回两个id    南宁铁路公安处党委*//*
    public void ckpcsTxYear(){
        examAutoEvaluationService.ckpcsTxYear("2020");
    }*/

    @Test/*pass*/
    public void ckpcsSxfxMonth(){
        examAutoEvaluationService.ckpcsSxfxMonth("2020-05");
    }
    @Test/*pass*/
    public void ckpcsSxfxYear(){
        examAutoEvaluationService.ckpcsSxfxYear("2020");
    }
    @Test/*pass*/
    public void ckcHdqkMonth(){
        examAutoEvaluationService.ckcHdqkMonth("2020-05");
    }
    @Test/*pass*/
    public void ckcHdqkYear(){
        examAutoEvaluationService.ckcHdqkYear("2020");
    }
    @Test /*pass*/
    public void ckpcsHdqkMonth(){
        examAutoEvaluationService.ckpcsHdqkMonth("2020-05");
    }
    @Test/*pass*/
    public void ckpcsHdqkYear(){
        examAutoEvaluationService.ckpcsHdqkYear("2020");
    }
    @Test/*pass*/
    public void ckpcsTnshhYear(){
        examAutoEvaluationService.ckpcsTnshhYear("2020");
    }
    @Test/*pass*/
    public void ckpcsMzYear(){
        examAutoEvaluationService.ckpcsMzYear("2020");
    }
    @Test/*pass*/
    public void ckpcsTy(){
        examAutoEvaluationService.ckpcsTy("2020-05");
    }
    @Test/*pass*/
    public void jkjLzjy(){
        examAutoEvaluationService.jkjLzjy("2020");
    }
    @Test/*pass*/
    public void ckcLzjy(){
        examAutoEvaluationService.ckcLzjy("2020");
    }
    @Test/*pass*/
    public void ckpcsLzjy(){
        /*examAutoEvaluationService.ckpcsLzjy("2020");*/
    }
    @Test/*pass*/
    public void ckcZgjb(){
        examAutoEvaluationService.ckcZgjb("2020");
    }
    @Test/*pass*/
    public void ckpcsZgjb(){
        examAutoEvaluationService.ckpcsZgjb("2020");
    }
    @Test/*pass*/
    public void dyjkjSxfx(){
        examAutoEvaluationService.dyjkjSxfx("2020-01");
    }
    @Test/*pass*/
    public void dyjkjSxfxYear(){
        examAutoEvaluationService.dyjkjSxfxYear("2019");
    }
    @Test/*pass*/
    public void dyckcSxfxQuarter(){
        examAutoEvaluationService.dyckcSxfxQuarter("2020-01");
    }
    @Test/*pass*/
    public void dyckcSxfxYear(){
        examAutoEvaluationService.dyckcSxfxYear("2020");
    }
    @Test/*pass*/
    public void dyckpcSxfxQuarter(){
        examAutoEvaluationService.dyckpcSxfxQuarter("2020-01");
    }
    @Test/*pass*/
    public void dyckpcSxfxYear(){
        examAutoEvaluationService.dyckpcSxfxYear("2020");
    }
    @Test/*pass*/
    public void gacSxfxQuarter(){
        examAutoEvaluationService.gacSxfxQuarter("2020-01");
    }
    @Test/*pass*/
    public void gacSxfxYear(){
        examAutoEvaluationService.gacSxfxYear("2020");
    }
    @Test/*pass*/
    public void zjcSxfxQuarter(){
        examAutoEvaluationService.zjcSxfxQuarter("2020-01");
    }
    @Test/*pass*/
    public void zjcSxfxYear(){
        examAutoEvaluationService.zjcSxfxYear("2020");
    }
    @Test/*pass*/
    public void ckpcsYsxt(){
        examAutoEvaluationService.ckpcsYsxt("2020");
    }
    @Test
    public void jkjWechar() throws ParseException {
        examAutoEvaluationService.jkjWechar("2020-01");
    }
    @Test
    public void jkjWecharYear() throws ParseException {
        examAutoEvaluationService.jkjWecharYear("2020");
    }
    @Test
    public void ckcWechar() throws ParseException {
        examAutoEvaluationService.ckcWechar("2020-01");
    }
    @Test
    public void ckcWecharYear() throws ParseException {
        examAutoEvaluationService.ckcWecharYear("2020");
    }
    @Test
    public void ckpcsWechar() throws ParseException {
        examAutoEvaluationService.ckpcsWechar("2020-01");
    }
    @Test
    public void ckpcsWecharYear() throws ParseException {
        examAutoEvaluationService.ckpcsWecharYear("2020");
    }
    @Test
    public void ckpcsGtws() throws Exception {examAutoEvaluationService.ckpcsGtws("2019-04");}
    @Test
    public void ckpcsGtwsYear() throws Exception {examAutoEvaluationService.ckpcsGtwsYear("2019");}
    @Test/*pass*/
    public void jkcZyYear(){
        examAutoEvaluationService.jkcZyYear("2020");
    }
    @Test/*pass*/
    public void jkcSxfx(){
        examAutoEvaluationService.jkcSxfx("2020-06");
    }
    @Test/*pass*/
    public void jkcSxfxYear(){
        examAutoEvaluationService.jkcSxfxYear("2020");
    }
    @Test/*pass*/
    public void jkcDwzxzxx(){
        examAutoEvaluationService.jkcDwzxzxx("2020-05");
    }
    @Test/*pass*/
    public void jkcDwzxzxxYear(){
        examAutoEvaluationService.jkcDwzxzxxYear("2020");
    }
   /* @Test*//*pass*//*
    public void jkcXxpt(){
        examAutoEvaluationService.jkcXxpt("2020-05");
    }
    @Test*//*pass*//*
    public void jkcXxptYear(){
        examAutoEvaluationService.jkcXxptYear("2020");
    }*/
    @Test/*pass*/
    public void jkcScsxfxYear(){
        examAutoEvaluationService.jkcXcsxfxYear("2020");
    }
    @Test/*pass*/
    public void jkcDshd(){
        examAutoEvaluationService.jkcDshd("2020-01");
    }
    @Test/*pass*/
    public void jkcDshdYear(){
        examAutoEvaluationService.jkcDshdYear("2020");
    }
    @Test/*pass*/
    public void jkcTntp(){
        examAutoEvaluationService.jkcTntp("2021-05");
    }
    @Test
    public void jkcTntpYear(){
        examAutoEvaluationService.jkcTntpYear("2021");
    }
   /* @Test
    public void jkcSjkyYear(){
        examAutoEvaluationService.jkcSjkyYear("2020");
    }*/
    @Test/*pass*/
    public void jkcLzjyYear(){
        examAutoEvaluationService.jkcLzjyYear("2020");
    }
   @Test
    public void jkcWechar() throws ParseException {
        examAutoEvaluationService.jkcWechar("2020-05");
    }
    @Test
    public void jkcWecharYear() throws ParseException {
        examAutoEvaluationService.jkcWecharYear("2020");
    }
    @Test/*pass*/
    public void syZgjbYear(){
        examAutoEvaluationService.syZgjbYear("2020");
    }
    @Test/*pass*/
    public void syNtgaYear(){
        examAutoEvaluationService.syNtgaYear("2020");
    }
    @Test
    public void jkcGtws() throws Exception {
        examAutoEvaluationService.jkcGtws("2019-04");
    }
    @Test
    public void jkcGtwsYear() throws Exception {
        examAutoEvaluationService.jkcGtwsYear("2019");
    }
    @Test/*pass*/
    public void ckpcsTyrd(){
        examAutoEvaluationService.ckpcsTyrd("2020-05");
    }
    @Test
    public void jkcJzDpcj(){
        examAutoEvaluationService.jkcJzDpcj("2020-11");
    }
    @Test
    public void jkcJzDpcjYear(){
        examAutoEvaluationService.jkcJzDpcjYear("2020");
    }
    @Test
    public void jkcJzZysc(){
        examAutoEvaluationService.jkcJzZysc("2020-09");
    }
    @Test
    public void jkcJzZyscYear(){
        examAutoEvaluationService.jkcJzZyscYear("2020");
    }
    @Test
    public void jkjJzZysc(){
        examAutoEvaluationService.jkjJzZysc("2020-04");
    }
    @Test
    public void jkjJzZyscYear(){
        examAutoEvaluationService.jkjJzZyscYear("2019");
    }

    @Test/*pass*/
    public void gacJxwh(){
        examAutoEvaluationService.gacJxwh("2019-04");
    }
    @Test/*pass*/
    public void gacJxwhYear(){
        examAutoEvaluationService.gacJxwhYear("2021");
    }
    @Test/*pass*/
    public void zjcJxwh(){
        examAutoEvaluationService.zjcJxwh("2019-04");
    }
    @Test/*pass*/
    public void zjcJxwhYear(){
        examAutoEvaluationService.zjcJxwhYear("2017");
    }
    @Test/*pass*/
    public void mjJxwh(){
        examAutoEvaluationService.mjJxwh("2021-01");
    }
    @Test/*pass*/
    public void mjJxwhYear(){
        examAutoEvaluationService.mjJxwhYear("2021");
    }
    @Test/*pass*/
    public void ckpcsJxwh(){
        examAutoEvaluationService.ckpcsJxwh("2019-10");
    }
    @Test/*pass*/
    public void ckcJxwh(){
        examAutoEvaluationService.ckcJxwh("2019-10");
    }
    @Test/*pass*/
    public void jkjJxwh(){
        examAutoEvaluationService.jkjJxwh("2019-05");
    }
    @Test/*pass*/
    public void jkjJxwhYear(){
        examAutoEvaluationService.jkjJxwhYear("2017");
    }
    @Test/*pass*/
    public void jkcJxwh(){
        examAutoEvaluationService.jkcJxwh("2021-06");
    }
    @Test/*pass*/
    public void jkcJxwhYear(){
        examAutoEvaluationService.jkcJxwhYear("2020");
    }
    @Test
    public void jkcHf(){
        examAutoEvaluationService.jkcHf("2019-05");
    }
    @Test
    public void jkcHfQuarter(){
        examAutoEvaluationService.jkcHfQuarter("2019-05");
    }
    @Test
    public void jkcHfHalf(){
        examAutoEvaluationService.jkcHfHalf("2019-01");
    }
    @Test
    public void jkcHfYear(){
        examAutoEvaluationService.jkcHfYear("2019");
    }
    @Test
    public void ckpcsHf(){
        examAutoEvaluationService.ckpcsHf("2019-08");
    }
    @Test
    public void ckpcsHfYear(){
        examAutoEvaluationService.ckpcsHfYear("2019");
    }
    @Test
    public void jkcKgpm(){
        examAutoEvaluationService.jkcKgpm("2020-11");
    }
    @Test
    public void jkcKgpmYear(){
        examAutoEvaluationService.jkcKgpmYear("2020");
    }

    @Test
    public void ckpcsKgpm(){
        examAutoEvaluationService.ckpcsKgpm("2020-05");
    }

    @Test
    public void gacKgpm(){
        examAutoEvaluationService.gacKgpm("2020-05");
    }

    @Test
    public void gacKgpmYear(){
        examAutoEvaluationService.gacKgpmYear("2020");
    }
    @Test
    public void zjcKgpm(){
        examAutoEvaluationService.zjcKgpm("2020-05");
    }
    @Test
    public void zjcKgpmYear(){
        examAutoEvaluationService.zjcKgpmYear("2020");
    }
    @Test
    public void mjKgpm(){
        examAutoEvaluationService.mjKgpm("2020-05");
    }
    @Test
    public void mjKgpmYear(){
        examAutoEvaluationService.mjKgpmYear("2020");
    }
/*    @Test
    public void test(){
        examAutoEvaluationService.policeYswc();
    }*/

    @Test
    public void zhiyuanYearJKJ(){
        examAutoEvaluationService.zhiyuanYearJKJ("2020");
    }
    @Test
    public void jkcShyk(){
        examAutoEvaluationService.jkcShyk("2020-06");
    }
    @Test
    public void jkcShykYear(){
        examAutoEvaluationService.jkcShykYear("2020");
    }
    @Test
    public void jkjShyk(){
        examAutoEvaluationService.jkjShyk("2021-04");
    }
    @Test
    public void jkjShykYear(){
        examAutoEvaluationService.jkjShykYear("2021");
    }
    @Test
    public void ckcShyk(){
        examAutoEvaluationService.ckcShyk("2020-06");
    }
    @Test
    public void ckcShykYear(){
        examAutoEvaluationService.ckcShykYear("2021");
    }
    @Test
    public void ckpcsShyk(){
        examAutoEvaluationService.ckpcsShyk("2020-06");
    }
    @Test
    public void ckpcsShykYear(){
        examAutoEvaluationService.ckpcsShykYear("2020");
    }
    @Test
    public void zjcShyk(){
        examAutoEvaluationService.zjcShyk("2020-06");
    }
    @Test
    public void zjcShykYear(){
        examAutoEvaluationService.zjcShykYear("2020");
    }
    @Test
    public void jkcTnmzYear(){
        examAutoEvaluationService.jkcTnmzYear("2020");
    }
    @Test
    public void ckpcsTnmzYear(){
        examAutoEvaluationService.ckpcsTnmzYear("2020");
    }
    @Test
    public void tyShyk(){
        examAutoEvaluationService.tyShyk("2020-01");
    }

    @Test
    public void zjcZhiyuanYear(){
        examAutoEvaluationService.zjcZhiyuanYear("2020");
    }

    @Test
    public void ckcXcsxfx(){
        examAutoEvaluationService.ckcXcsxfx("2021-06");
    }
    @Test
    public void juChuPartyDayActivitiesMonth(){
        examAutoEvaluationService.juChuPartyDayActivitiesMonth("2021","06");
    }
    @Test
    public void juPartyDayActivitiesMonth(){
        examAutoEvaluationService.juPartyDayActivitiesMonth("2021","06");
    }
    @Test
    public void juUnitElectionYear(){
        examAutoEvaluationService.juUnitElectionYear("2021");
    }
    @Test
    public void ckpcsTntp(){
        examAutoEvaluationService.ckpcsTntp("2021-07");
    }




}