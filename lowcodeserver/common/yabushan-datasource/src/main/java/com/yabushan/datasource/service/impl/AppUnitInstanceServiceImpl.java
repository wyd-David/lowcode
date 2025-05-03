package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
                                                                                                                                                                                                                    import com.yabushan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.AppUnitInstanceMapper;
import com.yabushan.datasource.domain.AppUnitInstance;
import com.yabushan.datasource.service.IAppUnitInstanceService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分配实例Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class AppUnitInstanceServiceImpl implements IAppUnitInstanceService {
    private static final Logger log = LoggerFactory.getLogger(AppUnitInstanceServiceImpl.class);
    @Autowired
    private AppUnitInstanceMapper appUnitInstanceMapper;

    //存放导入所有的成功失败数据
    private static List<AppUnitInstance> appUnitInstanceInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void clearappUnitInstanceInfo() {
        if (StringUtils.isNotNull(appUnitInstanceInfo) && appUnitInstanceInfo.size() > 0) {
                appUnitInstanceInfo.clear();
        }
    }

    /**
     * 查询分配实例
     *
     * @param instanceId 分配实例ID
     * @return 分配实例
     */
    @Override
    public AppUnitInstance selectAppUnitInstanceById(String instanceId) {
        return appUnitInstanceMapper.selectAppUnitInstanceById(instanceId);
    }

    /**
     * 查询分配实例列表
     *
     * @param appUnitInstance 分配实例
     * @return 分配实例
     */
    @Override
    public List<AppUnitInstance> selectAppUnitInstanceList(AppUnitInstance appUnitInstance) {
        return appUnitInstanceMapper.selectAppUnitInstanceList(appUnitInstance);
    }

    /**
     * 新增分配实例
     *
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    @Override
    public int insertAppUnitInstance(AppUnitInstance appUnitInstance) {
                                                                                                                                                                                                                                                                                                                                                                    appUnitInstance.setCreateTime(DateUtils.getNowDate());
                                                                                                                                                                                                                                                                            return appUnitInstanceMapper.insertAppUnitInstance(appUnitInstance);
    }

    /**
     * 批量新增分配实例
     *
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    @Override
    public int bathInsertAppUnitInstance(List<AppUnitInstance> appUnitInstance) {
        return appUnitInstanceMapper.bathInsertAppUnitInstance(appUnitInstance);
    }


    /**
     * 修改分配实例
     *
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    @Override
    public int updateAppUnitInstance(AppUnitInstance appUnitInstance) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    return appUnitInstanceMapper.updateAppUnitInstance(appUnitInstance);
    }

    /**
     * 批量删除分配实例
     *
     * @param instanceIds 需要删除的分配实例ID
     * @return 结果
     */
    @Override
    public int deleteAppUnitInstanceByIds(String[] instanceIds) {
        return appUnitInstanceMapper.deleteAppUnitInstanceByIds(instanceIds);
    }

    /**
     * 删除分配实例信息
     *
     * @param instanceId 分配实例ID
     * @return 结果
     */
    @Override
    public int deleteAppUnitInstanceById(String instanceId) {
        return appUnitInstanceMapper.deleteAppUnitInstanceById(instanceId);
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
                AppUnitInstance appUnitInstance=appUnitInstanceMapper.selectAppUnitInstanceById(idslist[i]);
                appUnitInstance.setDisableEnableState(disableEnableState);
                int isappinfo = appUnitInstanceMapper.updateAppUnitInstance(appUnitInstance);
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
     * 导入分配实例Excel数据
     *
     * @param appUnitInstanceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<AppUnitInstance>  importAppUnitInstance(
    List<AppUnitInstance> appUnitInstanceList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < appUnitInstanceList.size(); i++) {
            AppUnitInstance appUnitInstance=appUnitInstanceList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                AppUnitInstance u = appUnitInstanceMapper.selectAppUnitInstanceById(appUnitInstance.getInstanceId());
                if (StringUtils.isNull(u)) {
                        appUnitInstanceMapper.insertAppUnitInstance(appUnitInstance);
                    appUnitInstance.setImportStatus("导入成功");
                        appUnitInstanceInfo.add(appUnitInstance);
                } else if (isUpdateSupport) {
                        appUnitInstanceMapper.updateAppUnitInstance(appUnitInstance);
                    appUnitInstance.setImportStatus("更新成功");
                        appUnitInstanceInfo.add(appUnitInstance);
                } else {
                    appUnitInstance.setImportStatus("已存在当前数据");
                        appUnitInstanceInfo.add(appUnitInstance);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                appUnitInstance.setImportStatus("数据格式有问题，请认证检查数据！");
                    appUnitInstanceInfo.add(appUnitInstance);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importAppUnitInstance(appUnitInstanceList.subList(i + 1, appUnitInstanceList.size()), isUpdateSupport, operName);
                return appUnitInstanceInfo;
            }

        }
        return appUnitInstanceInfo;
    }
}
