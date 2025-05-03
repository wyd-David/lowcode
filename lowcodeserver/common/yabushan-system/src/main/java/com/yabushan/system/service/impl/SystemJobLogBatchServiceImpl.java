package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SystemJobLogBatch;
import com.yabushan.system.mapper.SystemJobLogBatchMapper;
import com.yabushan.system.service.ISystemJobLogBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志数据批次 Service业务层处理
 *
 * @author yabushan
 * @date 2021-11-16
 */
@Service
public class SystemJobLogBatchServiceImpl implements ISystemJobLogBatchService
{
    private static final Logger log = LoggerFactory.getLogger(SystemJobLogBatchServiceImpl.class);
    @Autowired
    private SystemJobLogBatchMapper systemJobLogBatchMapper;


    //存放导入所有的成功失败数据
    private static List<SystemJobLogBatch> systemJobLogBatchInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsystemJobLogBatchInfo() {
        if (StringUtils.isNotNull(systemJobLogBatchInfo) && systemJobLogBatchInfo.size() > 0) {
                systemJobLogBatchInfo.clear();
        }
    }

    /**
     * 查询日志数据批次 
     *
     * @param id 日志数据批次 ID
     * @return 日志数据批次 
     */
    @Override
    public SystemJobLogBatch selectSystemJobLogBatchById(String id)
    {
        return systemJobLogBatchMapper.selectSystemJobLogBatchById(id);
    }

    /**
     * 查询日志数据批次 列表
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 日志数据批次 
     */
    @Override
    public List<SystemJobLogBatch> selectSystemJobLogBatchList(SystemJobLogBatch systemJobLogBatch)
    {
        return systemJobLogBatchMapper.selectSystemJobLogBatchList(systemJobLogBatch);
    }

    /**
     * 新增日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    @Override
    public int insertSystemJobLogBatch(SystemJobLogBatch systemJobLogBatch)
    {
        systemJobLogBatch.setId(StringUtils.getUUID());
        return systemJobLogBatchMapper.insertSystemJobLogBatch(systemJobLogBatch);
    }

    /**
     * 批量新增日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    @Override
    public int bathInsertSystemJobLogBatch(List<SystemJobLogBatch> systemJobLogBatch)
    {
        return systemJobLogBatchMapper.bathInsertSystemJobLogBatch(systemJobLogBatch);
    }


    /**
     * 修改日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    @Override
    public int updateSystemJobLogBatch(SystemJobLogBatch systemJobLogBatch)
    {
        return systemJobLogBatchMapper.updateSystemJobLogBatch(systemJobLogBatch);
    }

    /**
     * 批量删除日志数据批次 
     *
     * @param ids 需要删除的日志数据批次 ID
     * @return 结果
     */
    @Override
    public int deleteSystemJobLogBatchByIds(Long[] ids)
    {
        return systemJobLogBatchMapper.deleteSystemJobLogBatchByIds(ids);
    }

    /**
     * 删除日志数据批次 信息
     *
     * @param id 日志数据批次 ID
     * @return 结果
     */
    @Override
    public int deleteSystemJobLogBatchById(Long id)
    {
        return systemJobLogBatchMapper.deleteSystemJobLogBatchById(id);
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    * @param ids
    * @param disableEnableState
    * @return
    */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[]=ids.split(",",-1);
        if(StringUtils.isNotNull(idslist)&&idslist.length>=1){
            for (int i = 0; i < idslist.length; i++) {
                SystemJobLogBatch systemJobLogBatch=systemJobLogBatchMapper.selectSystemJobLogBatchById(idslist[i]);
                systemJobLogBatch.setDisableEnableState(disableEnableState);
                int isappinfo = systemJobLogBatchMapper.updateSystemJobLogBatch(systemJobLogBatch);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 导入日志数据批次 Excel数据
     *
     * @param systemJobLogBatchList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<SystemJobLogBatch>  importSystemJobLogBatch(List<SystemJobLogBatch> systemJobLogBatchList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < systemJobLogBatchList.size(); i++) {
            SystemJobLogBatch systemJobLogBatch=systemJobLogBatchList.get(i);
        try {
            // 验证是否存在这个用户
            SystemJobLogBatch u = systemJobLogBatchMapper.selectSystemJobLogBatchById(systemJobLogBatch.getId());
            if (StringUtils.isNull(u)) {
                    systemJobLogBatchMapper.insertSystemJobLogBatch(systemJobLogBatch);
                    systemJobLogBatch.setImportStatus("导入成功");
                    systemJobLogBatchInfo.add(systemJobLogBatch);
            } else if (isUpdateSupport) {
                    systemJobLogBatchMapper.updateSystemJobLogBatch(systemJobLogBatch);
                    systemJobLogBatch.setImportStatus("更新成功");
                    systemJobLogBatchInfo.add(systemJobLogBatch);
            } else {
                    systemJobLogBatch.setImportStatus("已存在当前数据");
                    systemJobLogBatchInfo.add(systemJobLogBatch);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            systemJobLogBatch.setImportStatus("数据格式有问题，请认证检查数据！");
            systemJobLogBatchInfo.add(systemJobLogBatch);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importSystemJobLogBatch(systemJobLogBatchList.subList(i+1,systemJobLogBatchList.size()), isUpdateSupport, operName);
            return systemJobLogBatchInfo;
        }

    }
        return systemJobLogBatchInfo;
    }
}
