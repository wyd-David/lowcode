package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.datasource.domain.DigDataResource;
import com.yabushan.datasource.service.IDigDataResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.DigDataResourceMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据资源Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DigDigDataResourceServiceImpl implements IDigDataResourceService {
    private static final Logger log = LoggerFactory.getLogger(DigDigDataResourceServiceImpl.class);
    @Autowired
    private DigDataResourceMapper digDataResourceMapper;

    //存放导入所有的成功失败数据
    private static List<DigDataResource> digDataResourceInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void cleardataResourceInfo() {
        if (StringUtils.isNotNull(digDataResourceInfo) && digDataResourceInfo.size() > 0) {
            digDataResourceInfo.clear();
        }
    }

    /**
     * 查询数据资源
     *
     * @param resourceId 数据资源ID
     * @return 数据资源
     */
    @Override
    public DigDataResource selectDataResourceById(String resourceId) {
        return digDataResourceMapper.selectDataResourceById(resourceId);
    }

    /**
     * 查询数据资源列表
     *
     * @param digDataResource 数据资源
     * @return 数据资源
     */
    @Override
    public List<DigDataResource> selectDataResourceList(DigDataResource digDataResource) {
        return digDataResourceMapper.selectDataResourceList(digDataResource);
    }

    /**
     * 新增数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    @Override
    public int insertDataResource(DigDataResource digDataResource) {
        digDataResource.setCreateTime(DateUtils.getNowDate());
        return digDataResourceMapper.insertDataResource(digDataResource);
    }

    /**
     * 批量新增数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    @Override
    public int bathInsertDataResource(List<DigDataResource> digDataResource) {
        return digDataResourceMapper.bathInsertDataResource(digDataResource);
    }


    /**
     * 修改数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    @Override
    public int updateDataResource(DigDataResource digDataResource) {
        return digDataResourceMapper.updateDataResource(digDataResource);
    }

    /**
     * 批量删除数据资源
     *
     * @param resourceIds 需要删除的数据资源ID
     * @return 结果
     */
    @Override
    public int deleteDataResourceByIds(String[] resourceIds) {
        return digDataResourceMapper.deleteDataResourceByIds(resourceIds);
    }

    /**
     * 删除数据资源信息
     *
     * @param resourceId 数据资源ID
     * @return 结果
     */
    @Override
    public int deleteDataResourceById(String resourceId) {
        return digDataResourceMapper.deleteDataResourceById(resourceId);
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
                DigDataResource digDataResource = digDataResourceMapper.selectDataResourceById(idslist[i]);
                digDataResource.setDisableEnableState(disableEnableState);
                int isappinfo = digDataResourceMapper.updateDataResource(digDataResource);
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
     * 导入数据资源Excel数据
     *
     * @param digDataResourceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DigDataResource>  importDataResource(
            List<DigDataResource> digDataResourceList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < digDataResourceList.size(); i++) {
            DigDataResource digDataResource = digDataResourceList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DigDataResource u = digDataResourceMapper.selectDataResourceById(digDataResource.getResourceId());
                if (StringUtils.isNull(u)) {
                    digDataResourceMapper.insertDataResource(digDataResource);
                    digDataResource.setImportStatus("导入成功");
                    digDataResourceInfo.add(digDataResource);
                } else if (isUpdateSupport) {
                    digDataResourceMapper.updateDataResource(digDataResource);
                    digDataResource.setImportStatus("更新成功");
                    digDataResourceInfo.add(digDataResource);
                } else {
                    digDataResource.setImportStatus("已存在当前数据");
                    digDataResourceInfo.add(digDataResource);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                digDataResource.setImportStatus("数据格式有问题，请认证检查数据！");
                digDataResourceInfo.add(digDataResource);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importDataResource(digDataResourceList.subList(i + 1, digDataResourceList.size()), isUpdateSupport, operName);
                return digDataResourceInfo;
            }

        }
        return digDataResourceInfo;
    }
}
