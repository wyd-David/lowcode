package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.DataApiResourceMapper;
import com.yabushan.datasource.domain.DataApiResource;
import com.yabushan.datasource.service.IDataApiResourceService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据服务Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataApiResourceServiceImpl implements IDataApiResourceService {
    private static final Logger log = LoggerFactory.getLogger(DataApiResourceServiceImpl.class);
    @Autowired
    private DataApiResourceMapper dataApiResourceMapper;

    //存放导入所有的成功失败数据
    private static List<DataApiResource> dataApiResourceInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void cleardataApiResourceInfo() {
        if (StringUtils.isNotNull(dataApiResourceInfo) && dataApiResourceInfo.size() > 0) {
            dataApiResourceInfo.clear();
        }
    }

    /**
     * 查询数据服务
     *
     * @param resourceId 数据服务ID
     * @return 数据服务
     */
    @Override
    public DataApiResource selectDataApiResourceById(String resourceId) {
        return dataApiResourceMapper.selectDataApiResourceById(resourceId);
    }

    /**
     * 查询数据服务列表
     *
     * @param dataApiResource 数据服务
     * @return 数据服务
     */
    @Override
    public List<DataApiResource> selectDataApiResourceList(DataApiResource dataApiResource) {
        return dataApiResourceMapper.selectDataApiResourceList(dataApiResource);
    }

    /**
     * 新增数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    @Override
    public int insertDataApiResource(DataApiResource dataApiResource) {
        return dataApiResourceMapper.insertDataApiResource(dataApiResource);
    }

    /**
     * 批量新增数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    @Override
    public int bathInsertDataApiResource(List<DataApiResource> dataApiResource) {
        return dataApiResourceMapper.bathInsertDataApiResource(dataApiResource);
    }


    /**
     * 修改数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    @Override
    public int updateDataApiResource(DataApiResource dataApiResource) {
        return dataApiResourceMapper.updateDataApiResource(dataApiResource);
    }

    /**
     * 批量删除数据服务
     *
     * @param resourceIds 需要删除的数据服务ID
     * @return 结果
     */
    @Override
    public int deleteDataApiResourceByIds(String[] resourceIds) {
        return dataApiResourceMapper.deleteDataApiResourceByIds(resourceIds);
    }

    /**
     * 删除数据服务信息
     *
     * @param resourceId 数据服务ID
     * @return 结果
     */
    @Override
    public int deleteDataApiResourceById(String resourceId) {
        return dataApiResourceMapper.deleteDataApiResourceById(resourceId);
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
        String idslist[] = ids.split("," , -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                // todo idslist[i] 返回的是一个字符串，如果实体id是其他类型，需要转换，如是int类型就修改成Integer.parseInt(idslist[i])
                DataApiResource dataApiResource=dataApiResourceMapper.selectDataApiResourceById(idslist[i]);
                dataApiResource.setDisableEnableState(disableEnableState);
                int isappinfo = dataApiResourceMapper.updateDataApiResource(dataApiResource);
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
     * 导入数据服务Excel数据
     *
     * @param dataApiResourceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataApiResource>  importDataApiResource(
            List<DataApiResource> dataApiResourceList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < dataApiResourceList.size(); i++) {
            DataApiResource dataApiResource=dataApiResourceList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataApiResource u = dataApiResourceMapper.selectDataApiResourceById(dataApiResource.getResourceId());
                if (StringUtils.isNull(u)) {
                    dataApiResourceMapper.insertDataApiResource(dataApiResource);
                    dataApiResource.setImportStatus("导入成功");
                    dataApiResourceInfo.add(dataApiResource);
                } else if (isUpdateSupport) {
                    dataApiResourceMapper.updateDataApiResource(dataApiResource);
                    dataApiResource.setImportStatus("更新成功");
                    dataApiResourceInfo.add(dataApiResource);
                } else {
                    dataApiResource.setImportStatus("已存在当前数据");
                    dataApiResourceInfo.add(dataApiResource);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataApiResource.setImportStatus("数据格式有问题，请认证检查数据！");
                dataApiResourceInfo.add(dataApiResource);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importDataApiResource(dataApiResourceList.subList(i + 1, dataApiResourceList.size()), isUpdateSupport, operName);
                return dataApiResourceInfo;
            }

        }
        return dataApiResourceInfo;
    }
}
