package com.yabushan.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.yabushan.system.domain.XunchaBaseLog;
import com.yabushan.system.mapper.XunchaBaseLogMapper;
import com.yabushan.system.service.IXunchaBaseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 服务层处理
 *
 * @author ruoyi
 */
@Service
public class XunchaBaseLogServiceImpl implements IXunchaBaseLogService
{
    @Autowired
    private XunchaBaseLogMapper xunchaBaseLogMapper;

    @Override
    public void insertXunchaBaseLog(XunchaBaseLog xunchaBaseLog) {
        // 如果是新增/保存操作, 日志仅保存请求参数的 value值
        Integer businessType = xunchaBaseLog.getBusinessType();
        if (businessType == 1 || businessType == 2) {
            try {
                // 如果是新增操作则使用返回参数作为解析对象用于保存业务 id
                String businessId = getBusinessId(xunchaBaseLog.getTitle(), businessType == 1 ? xunchaBaseLog.getJsonResult() : xunchaBaseLog.getOperParam());
                xunchaBaseLog.setBusinessId(Long.valueOf(businessId));
//                xunchaBaseLog.setOperParam(getNewOperParam(xunchaBaseLog.getOperParam()));
                xunchaBaseLog.setOperParam(xunchaBaseLog.getOperParam());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        xunchaBaseLogMapper.insertXunchaBaseLog(xunchaBaseLog);
    }


    @Override
    public List<XunchaBaseLog> selectXunchaBaseLogList(XunchaBaseLog xunchaBaseLog) {
        return xunchaBaseLogMapper.selectXunchaBaseLogList(xunchaBaseLog);
    }

    @Override
    public int deleteXunchaBaseLogByIds(Long[] operIds) {
        return 0;
    }

    @Override
    public XunchaBaseLog selectXunchaBaseLogById(Long operId) {
        return null;
    }

    private Map<String, String> getTableColumns(String tableName){
        xunchaBaseLogMapper.getTableColumns(tableName);
        return null;
    }

    /**
     * 请求参数仅保留 value值并拼接
     * @param operParam
     * @return
     */
    private String getNewOperParam(String operParam) throws Exception {
        Map param = JSON.parseObject(operParam);
        Collection values = param.values();
        String str = "";
        for (Object value : values) {
            str += "、" + value;
        }
        str = str.substring(1);
        return str;
    }


    private String getBusinessId(String title, String param){
        Map newParam = JSON.parseObject(param);
        Object data = newParam.get("data");
        if (null != data) {
            newParam = JSON.parseObject(data.toString());
        }
        switch (title) {
            case "xuncha_pici":
                return newParam.get("piciId").toString();
            case "xuncha_lunci":
                return newParam.get("lunciId").toString();
            case "xuncha_task":
                return newParam.get("taskId").toString();
            case "xuncha_dict":
                return newParam.get("dictId").toString();
            case "xuncha_dict_data":
                return newParam.get("dataId").toString();
            default:
                return null;
        }
    }
}
