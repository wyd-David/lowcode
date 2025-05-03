package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
                                                    import com.yabushan.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.AppConnectorAttrMapper;
import com.yabushan.datasource.domain.AppConnectorAttr;
import com.yabushan.datasource.service.IAppConnectorAttrService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用实例连接器属性Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-25
 */
@Service
public class AppConnectorAttrServiceImpl implements IAppConnectorAttrService {
    private static final Logger log = LoggerFactory.getLogger(AppConnectorAttrServiceImpl.class);
    @Autowired
    private AppConnectorAttrMapper appConnectorAttrMapper;

    //存放导入所有的成功失败数据
    private static List<AppConnectorAttr> appConnectorAttrInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearappConnectorAttrInfo() {
        if (StringUtils.isNotNull(appConnectorAttrInfo) && appConnectorAttrInfo.size() > 0) {
                appConnectorAttrInfo.clear();
        }
    }

    /**
     * 查询应用实例连接器属性
     *
     * @param attrId 应用实例连接器属性ID
     * @return 应用实例连接器属性
     */
    @Override
    public AppConnectorAttr selectAppConnectorAttrById(String attrId) {
        return appConnectorAttrMapper.selectAppConnectorAttrById(attrId);
    }

    /**
     * 查询应用实例连接器属性列表
     *
     * @param appConnectorAttr 应用实例连接器属性
     * @return 应用实例连接器属性
     */
    @Override
    public List<AppConnectorAttr> selectAppConnectorAttrList(AppConnectorAttr appConnectorAttr) {
        return appConnectorAttrMapper.selectAppConnectorAttrList(appConnectorAttr);
    }

    /**
     * 新增应用实例连接器属性
     *
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    @Override
    public int insertAppConnectorAttr(AppConnectorAttr appConnectorAttr) {
                                                                                                return appConnectorAttrMapper.insertAppConnectorAttr(appConnectorAttr);
    }

    /**
     * 批量新增应用实例连接器属性
     *
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    @Override
    public int bathInsertAppConnectorAttr(List<AppConnectorAttr> appConnectorAttr) {
        return appConnectorAttrMapper.bathInsertAppConnectorAttr(appConnectorAttr);
    }


    /**
     * 修改应用实例连接器属性
     *
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    @Override
    public int updateAppConnectorAttr(AppConnectorAttr appConnectorAttr) {
                                                                                                return appConnectorAttrMapper.updateAppConnectorAttr(appConnectorAttr);
    }

    /**
     * 批量删除应用实例连接器属性
     *
     * @param attrIds 需要删除的应用实例连接器属性ID
     * @return 结果
     */
    @Override
    public int deleteAppConnectorAttrByIds(String[] attrIds) {
        return appConnectorAttrMapper.deleteAppConnectorAttrByIds(attrIds);
    }

    /**
     * 删除应用实例连接器属性信息
     *
     * @param attrId 应用实例连接器属性ID
     * @return 结果
     */
    @Override
    public int deleteAppConnectorAttrById(String attrId) {
        return appConnectorAttrMapper.deleteAppConnectorAttrById(attrId);
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
                AppConnectorAttr appConnectorAttr=appConnectorAttrMapper.selectAppConnectorAttrById(idslist[i]);
                appConnectorAttr.setDisableEnableState(disableEnableState);
                int isappinfo = appConnectorAttrMapper.updateAppConnectorAttr(appConnectorAttr);
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
     * 导入应用实例连接器属性Excel数据
     *
     * @param appConnectorAttrList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<AppConnectorAttr>  importAppConnectorAttr(
    List<AppConnectorAttr> appConnectorAttrList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < appConnectorAttrList.size(); i++) {
            AppConnectorAttr appConnectorAttr=appConnectorAttrList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                AppConnectorAttr u = appConnectorAttrMapper.selectAppConnectorAttrById(appConnectorAttr.getAttrId());
                if (StringUtils.isNull(u)) {
                        appConnectorAttrMapper.insertAppConnectorAttr(appConnectorAttr);
                    appConnectorAttr.setImportStatus("导入成功");
                        appConnectorAttrInfo.add(appConnectorAttr);
                } else if (isUpdateSupport) {
                        appConnectorAttrMapper.updateAppConnectorAttr(appConnectorAttr);
                    appConnectorAttr.setImportStatus("更新成功");
                        appConnectorAttrInfo.add(appConnectorAttr);
                } else {
                    appConnectorAttr.setImportStatus("已存在当前数据");
                        appConnectorAttrInfo.add(appConnectorAttr);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                appConnectorAttr.setImportStatus("数据格式有问题，请认证检查数据！");
                    appConnectorAttrInfo.add(appConnectorAttr);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importAppConnectorAttr(appConnectorAttrList.subList(i + 1, appConnectorAttrList.size()), isUpdateSupport, operName);
                return appConnectorAttrInfo;
            }

        }
        return appConnectorAttrInfo;
    }
}
