package com.yabushan.system.mapper;

import com.yabushan.system.domain.SyncUser;
import com.yabushan.system.domain.vo.SyncUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同步用户信息Mapper接口
 * 
 * @author yabushan
 * @date 2021-08-24
 */
public interface SyncUserMapper 
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

    public List<SyncUser> selectSyncUserMaindepartmentList(SyncUser syncUser);

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
     * 删除同步用户信息
     * 
     * @param id 同步用户信息ID
     * @return 结果
     */
    public int deleteSyncUserById(Long id);

    /**
     * 批量删除同步用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSyncUserByIds(Long[] ids);

    /**
     * 清除表数据
     * @return 结果
     */
    public int trunCateSyncUser();


    /**
     * 获取岗位信息
     *getSysPost
     * @return 岗位信息
     */
    List<String> getSysPost();

    /**
     * 获取角色列表
     * @return
     */
    List<String> getSysPostCat();

    //根据条件查询用户是否存在
    int selectSyncUserCount(SyncUser syncUser);


    List<SyncUserVo> getSyncUserList(SyncUserVo vo);

    /**
     * 是否是部门领导
     *
     * @param positioncat
     * @param loginid 登录id
     * @param erp rep
     * @return 结果
     */
    public Integer isDepartmentHead(@Param("positioncat") String positioncat,@Param("loginid")  String loginid,@Param("erp")  String erp);

    /**
     * 查询所有部门领导或特指某个人是否是部门领导
     *
     * @param positioncat
     * @param loginid 登录id
     * @param erp rep
     * @return 结果
     */
    public List<SyncUser> isDepartmentHeadList(@Param("positioncat") String positioncat,@Param("loginid")  String loginid,@Param("erp")  String erp);

    List<SyncUser> setlecDealUserByDept(SyncUser user);

}
