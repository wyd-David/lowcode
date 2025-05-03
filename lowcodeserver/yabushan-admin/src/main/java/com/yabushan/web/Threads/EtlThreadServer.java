package com.yabushan.web.Threads;

import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.datasource.service.DigDatasourceService;

public class EtlThreadServer implements  Runnable{
    
    private String ids;
    private String type;//dataflow 数据抽取


    public EtlThreadServer(String ids, String type) {
        this.ids = ids;
        this.type=type;
    }

    @Override
    public void run() {
       // System.out.println("111");
        try {
            DigDatasourceService bean = SpringUtils.getBean(DigDatasourceService.class);
            bean.dataFlow(ids,type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
