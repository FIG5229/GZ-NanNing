package com.thinkgem.jeesite.modules.affair.service.scheduletask;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyMemberDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyCost;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyCostService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 党费相关的任务定时器
 */
//@Component
//@Lazy(false)
public class AffairPartyCostTask {

    private static final Logger log = LoggerFactory.getLogger(AffairPartyCostTask.class);

    //@Autowired
    private AffairPartyMemberDao affairPartyMemberDao;

   // @Autowired
    private AffairPartyCostService affairPartyCostService;

    //@Scheduled(cron = "0 0 0 1 1 ?")//每年1月1号0点0分0秒开始执行
    public void doTasks() {
        log.info("AffairPartyCostTask-向党费表灌数据-定时任务开始执行");
        task();
    }
    public void task(){
        List<Map<String, String>> list = affairPartyMemberDao.getAllInfo();
        for (Map<String, String> m: list){
            AffairPartyCost affairPartyCost = new AffairPartyCost();
            affairPartyCost.setName(m.get("name"));
            affairPartyCost.setIdNumber(m.get("cardNum"));
            affairPartyCost.setYear(DateUtils.getYear());
            //管理员admin，并取消数据过滤，由左侧党组织数来控制
            User user = new User();
            user.setId("1");
            Office office = new Office();
            office.setId("1");
            user.setOffice(office);
            affairPartyCost.setCreateBy(user);
            affairPartyCost.setUpdateBy(user);
            affairPartyCostService.save(affairPartyCost);
        }
    }
}
