package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.PushProToreadRead;
import com.yabushan.system.enums.BusinessType;

/**
 * 推送待阅已阅Service接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface IPushProToreadReadService
{



    /**
     * 清空返回导入数据的集合
     */
    public void clearpushProToreadReadInfo();


    /**
     * 查询推送待阅已阅
     *
     * @param toreadId 推送待阅已阅ID
     * @return 推送待阅已阅
     */
    public PushProToreadRead selectPushProToreadReadById(String toreadId);

    /**
     * 查询推送待阅已阅列表
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 推送待阅已阅集合
     */
    public List<PushProToreadRead> selectPushProToreadReadList(PushProToreadRead pushProToreadRead);

    /**
     * 新增推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    public int insertPushProToreadRead(PushProToreadRead pushProToreadRead);

    /**
     * 批量新增推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    public int bathInsertPushProToreadRead(List<PushProToreadRead> pushProToreadRead);

    /**
     * 修改推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    public int updatePushProToreadRead(PushProToreadRead pushProToreadRead);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除推送待阅已阅
     *
     * @param toreadIds 需要删除的推送待阅已阅ID
     * @return 结果
     */
    public int deletePushProToreadReadByIds(String[] toreadIds);

    /**
     * 删除推送待阅已阅信息
     *
     * @param toreadId 推送待阅已阅ID
     * @return 结果
     */
    public int deletePushProToreadReadById(String toreadId);

    /**
     * 导入推送待阅已阅Excel数据
     *
     * @param pushProToreadReadList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<PushProToreadRead> importPushProToreadRead(List<PushProToreadRead> pushProToreadReadList, Boolean isUpdateSupport, String operName);

    boolean push(PushProToreadRead pushProToreadRead) throws Exception;
    //    推送数据到portal
    boolean pushDataToPortal(BusinessType businessType);
}
