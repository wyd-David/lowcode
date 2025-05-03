package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.yabushan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.AppAccountMapper;
import com.yabushan.datasource.domain.AppAccount;
import com.yabushan.datasource.service.IAppAccountService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用实例账号Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class AppAccountServiceImpl implements IAppAccountService {
    private static final Logger log = LoggerFactory.getLogger(AppAccountServiceImpl.class);
    @Autowired
    private AppAccountMapper appAccountMapper;

    //存放导入所有的成功失败数据
    private static List<AppAccount> appAccountInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void clearappAccountInfo() {
        if (StringUtils.isNotNull(appAccountInfo) && appAccountInfo.size() > 0) {
            appAccountInfo.clear();
        }
    }

    /**
     * 查询应用实例账号
     *
     * @param accountId 应用实例账号ID
     * @return 应用实例账号
     */
    @Override
    public AppAccount selectAppAccountById(String accountId) {
        return appAccountMapper.selectAppAccountById(accountId);
    }

    /**
     * 查询应用实例账号列表
     *
     * @param appAccount 应用实例账号
     * @return 应用实例账号
     */
    @Override
    public List<AppAccount> selectAppAccountList(AppAccount appAccount) {
        return appAccountMapper.selectAppAccountList(appAccount);
    }

    /**
     * 新增应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    @Override
    public int insertAppAccount(AppAccount appAccount) {
        appAccount.setCreateTime(DateUtils.getNowDate());
        return appAccountMapper.insertAppAccount(appAccount);
    }

    /**
     * 批量新增应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    @Override
    public int bathInsertAppAccount(List<AppAccount> appAccount) {
        return appAccountMapper.bathInsertAppAccount(appAccount);
    }


    /**
     * 修改应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    @Override
    public int updateAppAccount(AppAccount appAccount) {
        return appAccountMapper.updateAppAccount(appAccount);
    }

    /**
     * 批量删除应用实例账号
     *
     * @param accountIds 需要删除的应用实例账号ID
     * @return 结果
     */
    @Override
    public int deleteAppAccountByIds(String[] accountIds) {
        return appAccountMapper.deleteAppAccountByIds(accountIds);
    }

    /**
     * 删除应用实例账号信息
     *
     * @param accountId 应用实例账号ID
     * @return 结果
     */
    @Override
    public int deleteAppAccountById(String accountId) {
        return appAccountMapper.deleteAppAccountById(accountId);
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
                AppAccount appAccount=appAccountMapper.selectAppAccountById(idslist[i]);
                appAccount.setDisableEnableState(disableEnableState);
                int isappinfo = appAccountMapper.updateAppAccount(appAccount);
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
     * 导入应用实例账号Excel数据
     *
     * @param appAccountList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<AppAccount>  importAppAccount(
            List<AppAccount> appAccountList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < appAccountList.size(); i++) {
            AppAccount appAccount=appAccountList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                AppAccount u = appAccountMapper.selectAppAccountById(appAccount.getAccountId());
                if (StringUtils.isNull(u)) {
                    appAccountMapper.insertAppAccount(appAccount);
                    appAccount.setImportStatus("导入成功");
                    appAccountInfo.add(appAccount);
                } else if (isUpdateSupport) {
                    appAccountMapper.updateAppAccount(appAccount);
                    appAccount.setImportStatus("更新成功");
                    appAccountInfo.add(appAccount);
                } else {
                    appAccount.setImportStatus("已存在当前数据");
                    appAccountInfo.add(appAccount);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                appAccount.setImportStatus("数据格式有问题，请认证检查数据！");
                appAccountInfo.add(appAccount);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importAppAccount(appAccountList.subList(i + 1, appAccountList.size()), isUpdateSupport, operName);
                return appAccountInfo;
            }

        }
        return appAccountInfo;
    }
}
