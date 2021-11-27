package com.thinkgem.jeesite.common.elasticsearch;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportEntityService {
    private static final Logger logger = LoggerFactory.getLogger(ImportEntityService.class);
    @Autowired
    EsAdminService esAdminService;

    /**
     * 根据dao类名称，自动查询表中所有数据，并导入到ES中
     * 注：暂未处理附件，如需自动处理附件，需修改代码
     * @param daoClassNameList dao的className列表
     * @param awaitSeconds 等待时长
     * @param dropIndex 导入前是否删除索引
     * @throws Exception
     */
    public void importDatas(List<String> daoClassNameList, long awaitSeconds, boolean dropIndex) throws Exception {
        daoClassNameList.stream().forEach(d -> {
            try {
                importDatas(d, awaitSeconds,  dropIndex);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 根据dao类名称，自动查询表中所有数据，并导入到ES中
     * 注：暂未处理附件，如需自动处理附件，需修改代码
     * @param daoClassName dao的className全名
     * @param awaitSeconds 等待时长
     * @param dropIndex 导入前是否删除索引
     * @return
     * @throws Exception
     */
    public boolean importDatas(String daoClassName, long awaitSeconds, boolean dropIndex) throws Exception {
        try {
            Class cls = Class.forName(daoClassName);
            String entityClsName = daoClassName.replaceAll("\\.dao\\.", ".entity.")
                    .replaceAll("Dao$", "");
            Class entityCls = Class.forName(entityClsName);

            CrudDao dao = (CrudDao)SpringContextHolder.getBean(cls);
/*
            esAdminService.batchImport(entityCls, dao.findAllList((BaseEntity)entityCls.newInstance()), awaitSeconds, dropIndex);
*/
        } catch (Exception e) {
           throw e;
        }
        return true;
    }
}
