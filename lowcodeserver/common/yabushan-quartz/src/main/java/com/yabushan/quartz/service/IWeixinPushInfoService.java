package com.yabushan.quartz.service;

import com.yabushan.quartz.domain.WeixinPushInfo;

import java.util.List;

/**
 * 微信小程序消息推送内容Service接口
 *
 * @author CJH
 * @date 2022-03-04
 */
public interface IWeixinPushInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearweixinPushInfoInfo();


    /**
     * 查询微信小程序消息推送内容
     *
     * @param id 微信小程序消息推送内容ID
     * @return 微信小程序消息推送内容
     */
    public WeixinPushInfo selectWeixinPushInfoById(String id);

    /**
     * 查询微信小程序消息推送内容列表
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 微信小程序消息推送内容集合
     */
    public List<WeixinPushInfo> selectWeixinPushInfoList(WeixinPushInfo weixinPushInfo);

    /**
     * 新增微信小程序消息推送内容
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    public int insertWeixinPushInfo(WeixinPushInfo weixinPushInfo);

    /**
     * 批量新增微信小程序消息推送内容
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    public int bathInsertWeixinPushInfo(List<WeixinPushInfo> weixinPushInfo);

    /**
     * 修改微信小程序消息推送内容
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    public int updateWeixinPushInfo(WeixinPushInfo weixinPushInfo);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除微信小程序消息推送内容
     *
     * @param ids 需要删除的微信小程序消息推送内容ID
     * @return 结果
     */
    public int deleteWeixinPushInfoByIds(String[] ids);

    /**
     * 删除微信小程序消息推送内容信息
     *
     * @param id 微信小程序消息推送内容ID
     * @return 结果
     */
    public int deleteWeixinPushInfoById(String id);

    /**
     * 微信小程序消息推送内容启动流程
     * @param businessKey 业务主键ID
     * @param isAutoFinishFirstStep 1:完成第一步节点，0：启动流程，不自动完成第一步节点
     * @param processDefinitionKey 流程名称
     * @param requestUserId 发起人账号
     * @param variables 流程变量
     * @return 结果
     * */
    public String weixinPushInfoStartFormTask(String businessKey, String isAutoFinishFirstStep, String processDefinitionKey,
                                              String requestUserId, String variables);


    /**
     * 微信小程序消息推送内容结束流程
     * @param TaskVariables 任务变量
     * @param excutionVariables 实例变量
     * @param formVariables 表单变量
     * @param taskId 任务ID
     * @param userId 当前办理人ID
     * @return 结果
     * */
    public String weixinPushInfoFininshFormTask(String taskId, String userId, String formVariables, String TaskVariables, String excutionVariables);

    /**
     * 微信小程序消息推送内容任务转办
     * @param taskId 任务ID
     * @param dealUser 被转办人
     * @return 结果
     * */
    public String weixinPushInfoTrunTask(String taskId, String dealUser);

    /**
     * 微信小程序消息推送内容任务指派
     * @param taskId 任务ID
     * @param dealUser 被指派人
     * @return 结果
     * */
    public String weixinPushInfoClaimMyTask(String taskId, String dealUser);

    /**
     * 微信小程序消息推送内容保存任务表单
     * @param taskId 任务ID
     * @param formVariables 键值对形式的表单变量
     * @param TaskVariables 键值对形式的任务变量
     * @return 结果
     * */
    public String weixinPushInfoSaveTask(String taskId, String formVariables, String TaskVariables);

    /**
     * 导入微信小程序消息推送内容Excel数据
     *
     * @param weixinPushInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<WeixinPushInfo> importWeixinPushInfo(List<WeixinPushInfo> weixinPushInfoList, Boolean isUpdateSupport, String operName);
}
