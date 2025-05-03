package com.yabushan.quartz.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.yabushan.quartz.domain.WeixinPushInfo;
import com.yabushan.quartz.mapper.WeixinPushInfoMapper;
import com.yabushan.quartz.service.IWeixinPushInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.activiti.service.ActivitiService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 微信小程序消息推送内容Service业务层处理
 *
 * @author CJH
 * @date 2022-03-04
 */
@Service
public class WeixinPushInfoServiceImpl implements IWeixinPushInfoService
{
    private static final Logger log = LoggerFactory.getLogger(WeixinPushInfoServiceImpl.class);
    @Autowired
    private WeixinPushInfoMapper weixinPushInfoMapper;
    @Autowired
    private ActivitiService activitiService;

    //存放导入所有的成功失败数据
    private static List<WeixinPushInfo> weixinPushInfoInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearweixinPushInfoInfo() {
        if (StringUtils.isNotNull(weixinPushInfoInfo) && weixinPushInfoInfo.size() > 0) {
            weixinPushInfoInfo.clear();
        }
    }

    /**
     * 查询微信小程序消息推送内容
     *
     * @param id 微信小程序消息推送内容ID
     * @return 微信小程序消息推送内容
     */
    @Override
    public WeixinPushInfo selectWeixinPushInfoById(String id)
    {
        return weixinPushInfoMapper.selectWeixinPushInfoById(id);
    }

    /**
     * 查询微信小程序消息推送内容列表
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 微信小程序消息推送内容
     */
    @Override
    public List<WeixinPushInfo> selectWeixinPushInfoList(WeixinPushInfo weixinPushInfo)
    {
        return weixinPushInfoMapper.selectWeixinPushInfoList(weixinPushInfo);
    }

    /**
     * 新增微信小程序消息推送内容
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    @Override
    public int insertWeixinPushInfo(WeixinPushInfo weixinPushInfo)
    {
        return weixinPushInfoMapper.insertWeixinPushInfo(weixinPushInfo);
    }

    /**
     * 批量新增微信小程序消息推送内容
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    @Override
    public int bathInsertWeixinPushInfo(List<WeixinPushInfo> weixinPushInfo)
    {
        return weixinPushInfoMapper.bathInsertWeixinPushInfo(weixinPushInfo);
    }


    /**
     * 修改微信小程序消息推送内容
     *
     * @param weixinPushInfo 微信小程序消息推送内容
     * @return 结果
     */
    @Override
    public int updateWeixinPushInfo(WeixinPushInfo weixinPushInfo)
    {
        return weixinPushInfoMapper.updateWeixinPushInfo(weixinPushInfo);
    }

    /**
     * 批量删除微信小程序消息推送内容
     *
     * @param ids 需要删除的微信小程序消息推送内容ID
     * @return 结果
     */
    @Override
    public int deleteWeixinPushInfoByIds(String[] ids)
    {
        return weixinPushInfoMapper.deleteWeixinPushInfoByIds(ids);
    }

    /**
     * 删除微信小程序消息推送内容信息
     *
     * @param id 微信小程序消息推送内容ID
     * @return 结果
     */
    @Override
    public int deleteWeixinPushInfoById(String id)
    {
        return weixinPushInfoMapper.deleteWeixinPushInfoById(id);
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
        String idslist[]=ids.split(",",-1);
        if(StringUtils.isNotNull(idslist)&&idslist.length>=1){
            for (int i = 0; i < idslist.length; i++) {
                WeixinPushInfo weixinPushInfo=weixinPushInfoMapper.selectWeixinPushInfoById(idslist[i]);
                weixinPushInfo.setDisableEnableState(disableEnableState);
                int isappinfo = weixinPushInfoMapper.updateWeixinPushInfo(weixinPushInfo);
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
     * 微信小程序消息推送内容启动流程
     * @param businessKey 业务主键ID
     * @param isAutoFinishFirstStep 1:完成第一步节点，0：启动流程，不自动完成第一步节点
     * @param processDefinitionKey 流程名称
     * @param requestUserId 发起人账号
     * @param variables 流程变量
     * @return 结果
     * */
    @Override
    public String weixinPushInfoStartFormTask(String businessKey,String isAutoFinishFirstStep,String processDefinitionKey,
                                              String requestUserId,String variables){
        return activitiService.starkFormTask(processDefinitionKey,requestUserId,businessKey,variables,isAutoFinishFirstStep);
    }

    /**
     * 微信小程序消息推送内容结束流程
     * @param TaskVariables 任务变量
     * @param excutionVariables 实例变量
     * @param formVariables 表单变量
     * @param taskId 任务ID
     * @param userId 当前办理人ID
     * @return 结果
     * */
    @Override
    public String weixinPushInfoFininshFormTask(String taskId,String userId,String formVariables, String TaskVariables,String excutionVariables){
        return activitiService.fininshFormTask(taskId, userId, formVariables, TaskVariables, excutionVariables);
    }

    /**
     * 微信小程序消息推送内容任务转办
     * @param taskId 任务ID
     * @param dealUser 被转办人
     * @return 结果
     * */
    @Override
    public String weixinPushInfoTrunTask(String taskId, String dealUser){
        return activitiService.trunTask(taskId, dealUser);
    }

    /**
     * 微信小程序消息推送内容任务指派
     * @param taskId 任务ID
     * @param dealUser 被指派人
     * @return 结果
     * */
    @Override
    public String weixinPushInfoClaimMyTask(String taskId, String dealUser){
        return activitiService.claimMyTask(taskId, dealUser);
    }

    /**
     * 微信小程序消息推送内容保存任务表单
     * @param taskId 任务ID
     * @param formVariables 键值对形式的表单变量
     * @param TaskVariables 键值对形式的任务变量
     * @return 结果
     * */
    @Override
    public String weixinPushInfoSaveTask(String taskId,String formVariables,String TaskVariables){
        return activitiService.saveTask(taskId, formVariables, TaskVariables);
    }

    /**
     * 导入微信小程序消息推送内容Excel数据
     *
     * @param weixinPushInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<WeixinPushInfo>  importWeixinPushInfo(List<WeixinPushInfo> weixinPushInfoList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < weixinPushInfoList.size(); i++) {
            WeixinPushInfo weixinPushInfo=weixinPushInfoList.get(i);
            try {
                // 验证是否存在这个用户
                WeixinPushInfo u = weixinPushInfoMapper.selectWeixinPushInfoById(weixinPushInfo.getId());
                if (StringUtils.isNull(u)) {
                    weixinPushInfoMapper.insertWeixinPushInfo(weixinPushInfo);
                    weixinPushInfo.setImportStatus("导入成功");
                    weixinPushInfoInfo.add(weixinPushInfo);
                } else if (isUpdateSupport) {
                    weixinPushInfoMapper.updateWeixinPushInfo(weixinPushInfo);
                    weixinPushInfo.setImportStatus("更新成功");
                    weixinPushInfoInfo.add(weixinPushInfo);
                } else {
                    weixinPushInfo.setImportStatus("已存在当前数据");
                    weixinPushInfoInfo.add(weixinPushInfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                weixinPushInfo.setImportStatus("数据格式有问题，请认证检查数据！");
                weixinPushInfoInfo.add(weixinPushInfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importWeixinPushInfo(weixinPushInfoList.subList(i+1,weixinPushInfoList.size()), isUpdateSupport, operName);
                return weixinPushInfoInfo;
            }

        }
        return weixinPushInfoInfo;
    }
}
