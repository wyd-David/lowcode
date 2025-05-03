package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.SyncUser;
import org.apache.ibatis.annotations.Param;

/**
 * 同步用户信息Service接口
 * 
 * @author yabushan
 * @date 2021-08-24
 */
public interface ISyncUserService 
{
    /**
     * 查询同步用户信息
     * 
     * @param id 同步用户信息ID
     * @return 同步用户信息
     */
    public SyncUser selectSyncUserById(Long id);

    /**
     * 查询同步用户信息列表
     * 
     * @param syncUser 同步用户信息
     * @return 同步用户信息集合
     */
    public List<SyncUser> selectSyncUserList(SyncUser syncUser);

    /**
     * 新增同步用户信息
     * 
     * @param syncUser 同步用户信息
     * @return 结果
     */
    public int insertSyncUser(SyncUser syncUser);

    /**
     * 修改同步用户信息
     * 
     * @param syncUser 同步用户信息
     * @return 结果
     */
    public int updateSyncUser(SyncUser syncUser);

    /**
     * 批量删除同步用户信息
     * 
     * @param ids 需要删除的同步用户信息ID
     * @return 结果
     */
    public int deleteSyncUserByIds(Long[] ids);

    /**
     * 删除同步用户信息信息
     * 
     * @param id 同步用户信息ID
     * @return 结果
     */
    public int deleteSyncUserById(Long id);


    /**
     * 导入同步用户信息Excel数据
     *
     * @param syncUserList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSyncUser(List<SyncUser> syncUserList, Boolean isUpdateSupport, String operName);

    /**
     * 同步用户信息
     *
     * @param urlTmp url
     * @param appId appId
     * @param start 状态
     * @param limit 条数
     * @return 结果
     */
    public void insertOrUpdateSynUser();

    /**
     * 是否是部门领导
     *
     * @param positioncat 部门领导字段
     * @param loginid 登录id
     * @param erp rep
     * @return 结果
     */
    public boolean isDepartmentHead(String positioncat,String loginid,String erp);


    /**
     * 查询所有部门领导或特指某个人是否是部门领导
     *
     * @param positioncat
     * @param loginid 登录id
     * @param erp rep
     * @return 结果
     */
    public List<SyncUser> isDepartmentHeadList(@Param("positioncat") String positioncat, @Param("loginid")  String loginid, @Param("erp")  String erp);

    List<SyncUser> setlecDealUserByDept(SyncUser user);
}
