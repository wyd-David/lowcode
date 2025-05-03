package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
                                                                                                                                                                                import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.datasource.Vo.DataSourceDto;
import com.yabushan.datasource.domain.DigDataResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.AppUnitInfoMapper;
import com.yabushan.datasource.domain.AppUnitInfo;
import com.yabushan.datasource.service.IAppUnitInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 动态数据源管理Service业务层处理
 *
 * @author yabushan
 * @date 2024-01-13
 */
@Service
public class AppUnitInfoServiceImpl implements IAppUnitInfoService {
    private static final Logger log = LoggerFactory.getLogger(AppUnitInfoServiceImpl.class);

    @Autowired
    private AppUnitInfoMapper appUnitInfoMapper;


    //存放导入所有的成功失败数据
    private static List<AppUnitInfo> appUnitInfoInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void clearappUnitInfoInfo() {
        if (StringUtils.isNotNull(appUnitInfoInfo) && appUnitInfoInfo.size() > 0) {
                appUnitInfoInfo.clear();
        }
    }

    /**
     * 查询动态数据源管理
     *
     * @param datasourceid 动态数据源管理ID
     * @return 动态数据源管理
     */
    @Override
    public AppUnitInfo selectAppUnitInfoById(String datasourceid) {
        return appUnitInfoMapper.selectAppUnitInfoById(datasourceid);
    }

    @Override
    public AppUnitInfo selectAppUnitInfoByIdNoPassword(String datasourceid) {
        return appUnitInfoMapper.selectAppUnitInfoByIdNoPassword(datasourceid);
    }

    

    /**
     * 查询动态数据源管理列表
     *
     * @param appUnitInfo 动态数据源管理
     * @return 动态数据源管理
     */
    @Override
    public List<AppUnitInfo> selectAppUnitInfoList(AppUnitInfo appUnitInfo) {
        
        return appUnitInfoMapper.selectAppUnitInfoList(appUnitInfo);
    }

    /**
     * 新增动态数据源管理
     *
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    @Override
    public int insertAppUnitInfo(AppUnitInfo appUnitInfo) {
        appUnitInfo.setCreateTime(DateUtils.getNowDate());
        appUnitInfo.setCreateBy(SecurityUtils.getUsername());
        return appUnitInfoMapper.insertAppUnitInfo(appUnitInfo);
    }

    /**
     * 批量新增动态数据源管理
     *
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    @Override
    public int bathInsertAppUnitInfo(List<AppUnitInfo> appUnitInfo) {
        return appUnitInfoMapper.bathInsertAppUnitInfo(appUnitInfo);
    }


    /**
     * 修改动态数据源管理
     *
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    @Override
    public int updateAppUnitInfo(AppUnitInfo appUnitInfo) {
                                                                                                                                                                                                                                                                                                                            return appUnitInfoMapper.updateAppUnitInfo(appUnitInfo);
    }

    /**
     * 批量删除动态数据源管理
     *
     * @param datasourceids 需要删除的动态数据源管理ID
     * @return 结果
     */
    @Override
    public int deleteAppUnitInfoByIds(String[] datasourceids) {
        return appUnitInfoMapper.deleteAppUnitInfoByIds(datasourceids);
    }

    /**
     * 删除动态数据源管理信息
     *
     * @param datasourceid 动态数据源管理ID
     * @return 结果
     */
    @Override
    public int deleteAppUnitInfoById(String datasourceid) {
        return appUnitInfoMapper.deleteAppUnitInfoById(datasourceid);
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
                AppUnitInfo appUnitInfo=appUnitInfoMapper.selectAppUnitInfoById(idslist[i]);
                appUnitInfo.setDisableEnableState(disableEnableState);
                appUnitInfo.setIsDelete(disableEnableState.equals("1")?"禁用":"启用");
                int isappinfo = appUnitInfoMapper.updateAppUnitInfo(appUnitInfo);
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
     * 导入动态数据源管理Excel数据
     *
     * @param appUnitInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<AppUnitInfo>  importAppUnitInfo(
    List<AppUnitInfo> appUnitInfoList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < appUnitInfoList.size(); i++) {
            AppUnitInfo appUnitInfo=appUnitInfoList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                AppUnitInfo u = appUnitInfoMapper.selectAppUnitInfoById(appUnitInfo.getDatasourceid());
                if (StringUtils.isNull(u)) {
                        appUnitInfoMapper.insertAppUnitInfo(appUnitInfo);
                    appUnitInfo.setImportStatus("导入成功");
                        appUnitInfoInfo.add(appUnitInfo);
                } else if (isUpdateSupport) {
                        appUnitInfoMapper.updateAppUnitInfo(appUnitInfo);
                    appUnitInfo.setImportStatus("更新成功");
                        appUnitInfoInfo.add(appUnitInfo);
                } else {
                    appUnitInfo.setImportStatus("已存在当前数据");
                        appUnitInfoInfo.add(appUnitInfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                appUnitInfo.setImportStatus("数据格式有问题，请认证检查数据！");
                    appUnitInfoInfo.add(appUnitInfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importAppUnitInfo(appUnitInfoList.subList(i + 1, appUnitInfoList.size()), isUpdateSupport, operName);
                return appUnitInfoInfo;
            }

        }
        return appUnitInfoInfo;
    }


}
