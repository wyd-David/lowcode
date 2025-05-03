package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.AppConnectorMapper;
import com.yabushan.datasource.domain.AppConnector;
import com.yabushan.datasource.service.IAppConnectorService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用实例连接器记录Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-25
 */
@Service
public class AppConnectorServiceImpl implements IAppConnectorService {
    private static final Logger log = LoggerFactory.getLogger(AppConnectorServiceImpl.class);
    @Autowired
    private AppConnectorMapper appConnectorMapper;

    //存放导入所有的成功失败数据
    private static List<AppConnector> appConnectorInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearappConnectorInfo() {
        if (StringUtils.isNotNull(appConnectorInfo) && appConnectorInfo.size() > 0) {
                appConnectorInfo.clear();
        }
    }

    /**
     * 查询应用实例连接器记录
     *
     * @param connectorId 应用实例连接器记录ID
     * @return 应用实例连接器记录
     */
    @Override
    public AppConnector selectAppConnectorById(String connectorId) {
        return appConnectorMapper.selectAppConnectorById(connectorId);
    }

    /**
     * 查询应用实例连接器记录列表
     *
     * @param appConnector 应用实例连接器记录
     * @return 应用实例连接器记录
     */
    @Override
    public List<AppConnector> selectAppConnectorList(AppConnector appConnector) {
        return appConnectorMapper.selectAppConnectorList(appConnector);
    }

    /**
     * 新增应用实例连接器记录
     *
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    @Override
    public int insertAppConnector(AppConnector appConnector) {
                                                                                                                                                            return appConnectorMapper.insertAppConnector(appConnector);
    }

    /**
     * 批量新增应用实例连接器记录
     *
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    @Override
    public int bathInsertAppConnector(List<AppConnector> appConnector) {
        return appConnectorMapper.bathInsertAppConnector(appConnector);
    }


    /**
     * 修改应用实例连接器记录
     *
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    @Override
    public int updateAppConnector(AppConnector appConnector) {
                                                                                                                                                            return appConnectorMapper.updateAppConnector(appConnector);
    }

    /**
     * 批量删除应用实例连接器记录
     *
     * @param connectorIds 需要删除的应用实例连接器记录ID
     * @return 结果
     */
    @Override
    public int deleteAppConnectorByIds(String[] connectorIds) {
        return appConnectorMapper.deleteAppConnectorByIds(connectorIds);
    }

    /**
     * 删除应用实例连接器记录信息
     *
     * @param connectorId 应用实例连接器记录ID
     * @return 结果
     */
    @Override
    public int deleteAppConnectorById(String connectorId) {
        return appConnectorMapper.deleteAppConnectorById(connectorId);
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
                AppConnector appConnector=appConnectorMapper.selectAppConnectorById(idslist[i]);
                appConnector.setDisableEnableState(disableEnableState);
                int isappinfo = appConnectorMapper.updateAppConnector(appConnector);
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
     * 导入应用实例连接器记录Excel数据
     *
     * @param appConnectorList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<AppConnector>  importAppConnector(
    List<AppConnector> appConnectorList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < appConnectorList.size(); i++) {
            AppConnector appConnector=appConnectorList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                AppConnector u = appConnectorMapper.selectAppConnectorById(appConnector.getConnectorId());
                if (StringUtils.isNull(u)) {
                        appConnectorMapper.insertAppConnector(appConnector);
                    appConnector.setImportStatus("导入成功");
                        appConnectorInfo.add(appConnector);
                } else if (isUpdateSupport) {
                        appConnectorMapper.updateAppConnector(appConnector);
                    appConnector.setImportStatus("更新成功");
                        appConnectorInfo.add(appConnector);
                } else {
                    appConnector.setImportStatus("已存在当前数据");
                        appConnectorInfo.add(appConnector);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                appConnector.setImportStatus("数据格式有问题，请认证检查数据！");
                    appConnectorInfo.add(appConnector);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importAppConnector(appConnectorList.subList(i + 1, appConnectorList.size()), isUpdateSupport, operName);
                return appConnectorInfo;
            }

        }
        return appConnectorInfo;
    }
}
