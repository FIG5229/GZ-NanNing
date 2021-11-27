/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceChildDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceChild;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborSort;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录子表Service
 *
 * @author eav.liu
 * @version 2020-03-18
 */
@Service
@Transactional(readOnly = true)
public class AffairAttendanceChildService extends CrudService<AffairAttendanceChildDao, AffairAttendanceChild> {

    @Autowired
    private AffairAttendanceChildDao affairAttendanceChildDao;

    public AffairAttendanceChild get(String id) {
        return super.get(id);
    }

    public List<AffairAttendanceChild> findList(AffairAttendanceChild affairAttendanceChild) {
        return super.findList(affairAttendanceChild);
    }

    public Page<AffairAttendanceChild> findPage(Page<AffairAttendanceChild> page, AffairAttendanceChild affairAttendanceChild) {
//        affairAttendanceChild.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        affairAttendanceChild.setCreateBy(UserUtils.getUser());
        if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832") || UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79") || UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8") || UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
            affairAttendanceChild.setUserId(UserUtils.getUser().getCompany().getId());
        }else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
            affairAttendanceChild.setUserId("777");
        }else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
            affairAttendanceChild.setUserId("888");
        }else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
            affairAttendanceChild.setUserId("999");
        }
        else {
            affairAttendanceChild.setUserId(UserUtils.getUser().getOffice().getId());
        }
        return super.findPage(page, affairAttendanceChild);
    }

    public List<AffairAttendanceChild> findPersonById(String idNumber,String unitId, Integer year, Integer month) {
        return affairAttendanceChildDao.findPersonById(idNumber, unitId,year,month);
    }

    @Transactional(readOnly = false)
    public void save(AffairAttendanceChild affairAttendanceChild) {
        super.save(affairAttendanceChild);
    }

    @Transactional(readOnly = false)
    public void delete(AffairAttendanceChild affairAttendanceChild) {
        super.delete(affairAttendanceChild);
    }

    public List<PersonnelBase> findPerson(String unitId){
        return affairAttendanceChildDao.findPerson(unitId);
    }

    //从人员排序获取人员基本信息
    public List<AffairLaborSort> findPersonInfo(String unitId){
        return affairAttendanceChildDao.findPersonInfo(unitId);
    }

    //解锁
    @Transactional(readOnly = false)
    public void unlock(String unitId, String year, String month, String userOffice){
        affairAttendanceChildDao.unlock(unitId, year, month, userOffice);
    }

    //加锁
    @Transactional(readOnly = false)
    public void locked(String unitId, String year, String month, String userOffice){
        affairAttendanceChildDao.locked(unitId, year, month, userOffice);
    }

    public List<String> findLock(String id){
        return affairAttendanceChildDao.findLock(id);
    }

    public List<Map<String, String>> findSumLackDay(Integer year){
        return affairAttendanceChildDao.findSumLackDay(year);
    }
    public List<Map<String, String>> findSumLackDayByUnitId(String unitId,Integer year){
        return affairAttendanceChildDao.findSumLackDayByUnitId(unitId,year);
    }

    public List<String> selectAllIdNumber(){
        return affairAttendanceChildDao.selectAllIdNumber();
    }

    public Integer selectDegree(String keyId){
        return affairAttendanceChildDao.selectDegree(keyId);
    }

    public List<Map<String,Object>> selectNum(Map<String,Object> param){
        return affairAttendanceChildDao.selectNum(param);
    }

    public String selectNameById(String idNumber){
        return affairAttendanceChildDao.selectNameById(idNumber);
    }

    public Date selectTimeById(String idNumber){
        return affairAttendanceChildDao.selectTimeById(idNumber);
    }
    public Date selectHappenTimeById(String idNumber){
        return affairAttendanceChildDao.selectHappenTimeById(idNumber);
    }

    public AffairAttendanceChild findPersonAttendanceByIdNumber(String idNumber, String unitId, int year, int month) {
        return affairAttendanceChildDao.findPersonAttendanceByIdNumber(idNumber,unitId,year,month);
    }

    public List<AffairAttendanceChild> findChildListByAttId(String attId) {
        return affairAttendanceChildDao.findChildListByAttId(attId);
    }
    public List<AffairAttendanceChild> selectNumberUnit(String keyId,String state){
        return affairAttendanceChildDao.selectNumberUnit(keyId,state);
    }
    public List<AffairAttendanceChild> selectBean(String idNumber,int year,int month,String state){
        return affairAttendanceChildDao.selectBean(idNumber,year,month,state);
    }
    public List<AffairAttendanceChild> selectBeanLastForeDay(String idNumber,int year,int month,String state){
        return affairAttendanceChildDao.selectBeanLastForeDay(idNumber,year,month,state);
    }
    public Integer selectBeanYear(String idNumber,int year ,String state){
        return affairAttendanceChildDao.selectBeanYear(idNumber,year ,state);
    }
    public  List<AffairAttendanceChild> selectMjBeanYear(String idNumber,int year,int one,int elev ,String state){
        return affairAttendanceChildDao.selectMjBeanYear(idNumber,year,one,elev ,state);
    }
    public  List<AffairAttendanceChild> selectMjLastBeanYear(String idNumber,int year,int one,int elev  ,String state){
        return affairAttendanceChildDao.selectMjLastBeanYear(idNumber,year,one,elev  ,state);
    }
    public Integer selectLaNumber(String idNumber,int year ,String state){
        return affairAttendanceChildDao.selectLaNumber(idNumber,year ,state);
    }
    public Integer selectBeanTnumber(String idNumber,int year ,String state){
        return affairAttendanceChildDao.selectBeanTnumber(idNumber,year ,state);
    }

    public List<AffairAttendanceChild> selectLastBean(String idNumber, int lastYear, String state) {
        return affairAttendanceChildDao.selectLastBean(idNumber,lastYear,state);
    }

    public List<AffairAttendanceChild> selectThisYearBean(String idNumber, int y, String state) {
        return affairAttendanceChildDao.selectThisYearBean(idNumber,y,state);
    }

    public List<AffairAttendanceChild> selectMjThisMonth(String roid, int y, int m, String state) {
        return affairAttendanceChildDao.selectMjThisMonth(roid,y,m,state);
    }

    public List<AffairAttendanceChild> selectMjLastMonth(String roid, int y, int m1, String state) {
        return affairAttendanceChildDao.selectMjLastMonth(roid,y,m1,state);
    }

    public List<AffairAttendanceChild> selectMjLastYear(String policeRoleId, int lastYear, String state) {
        return affairAttendanceChildDao.selectMjLastYear(policeRoleId,lastYear,state);
    }

    public List<AffairAttendanceChild> selectMjThisYear(String policeRoleId, int y, String state) {
        return affairAttendanceChildDao.selectMjThisYear(policeRoleId,y,state);
    }

    public List<AffairAttendanceChild> selectHaveBugsIdNumber(String policeRoleId, int lastYear,int year) {
        return affairAttendanceChildDao.selectHaveBugsIdNumber(policeRoleId,lastYear,year);
    }

    public List<AffairAttendanceChild> selectOTBeanTnumber(String idNumber, int y2, String state) {
        return affairAttendanceChildDao.selectOTBeanTnumber(idNumber,y2,state);
    }

    public List<AffairAttendanceChild> selectZjcBean(String idNumber, int y, int m, String state) {
        return affairAttendanceChildDao.selectZjcBean(idNumber,y,m,state);
    }
}
