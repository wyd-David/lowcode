package com.yabushan.quartz.task;

import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.datasource.domain.EtlWorkCollect;
import com.yabushan.datasource.service.DigDatasourceService;
import com.yabushan.datasource.service.IEtlWorkCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("etlTask")
@Slf4j
public class ETLTask {

    @Autowired
    private IEtlWorkCollectService etlWorkCollectService;
    
    @Autowired
    private DigDatasourceService digDatasourceService;

    
    public void run(String ids){
        //判断作业所属类型是采集还是抽取
        EtlWorkCollect etlWorkCollect = etlWorkCollectService.selectEtlWorkCollectById(ids);
        if("1".equals(etlWorkCollect.getCollectType())){
            //采集
            digDatasourceService.dataFlow(ids,"dataCollect");
           
        }else if("2".equals(etlWorkCollect.getCollectType())){
            //抽取
            digDatasourceService.dataFlow(ids,"dataFlow");

        }
      
        
    
        
    }
    
}
