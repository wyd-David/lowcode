package com.yabushan.quartz.mapper;

import com.yabushan.quartz.domain.WeixinPushInfo;

import java.util.List;

/**
 * 微信小程序消息推送内容Mapper接口
 * 
 * @author CJH
 * @date 2022-03-04
 */
public interface WeixinPushInfoMapper 
{
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
    public int bathInsertWeixinPushInfo(List<WeixinPushInfo> list);

    /**
     * 修改微信小程序消息推送内容
     * 
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    public int updateWeixinPushInfo(WeixinPushInfo weixinPushInfo);

    /**
     * 删除微信小程序消息推送内容
     * 
     * @param id 微信小程序消息推送内容ID
     * @return 结果
     */
    public int deleteWeixinPushInfoById(String id);

    /**
     * 批量删除微信小程序消息推送内容
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWeixinPushInfoByIds(String[] ids);
}
