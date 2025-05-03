package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.SysNewsPushLog;
import com.yabushan.system.domain.vo.SysNewsPushLogVo;

/**
 * 消息推送记录Service接口
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
public interface ISysNewsPushLogService
{
    /**
     * 查询消息推送记录
     *
     * @param id 消息推送记录ID
     * @return 消息推送记录
     */
    public SysNewsPushLog selectSysNewsPushLogById(Long id);

    /**
     * 连表查询
     * @param sysNewsPushLogVo
     * @return
     */
    public List<SysNewsPushLogVo> selectSysNewsPushLogVoList(SysNewsPushLogVo sysNewsPushLogVo);
    /**
     * 查询已读未读数量
     */
    public SysNewsPushLogVo selectReadCount(String sendOutBy);
    /**
     * 查询消息推送记录列表
     *
     * @param sysNewsPushLog 消息推送记录
     * @return 消息推送记录集合
     */
    public List<SysNewsPushLog> selectSysNewsPushLogList(SysNewsPushLog sysNewsPushLog);

    /**
     * 新增消息推送记录
     *
     * @param sysNewsPushLog 消息推送记录
     * @return 结果
     */
    public int insertSysNewsPushLog(SysNewsPushLog sysNewsPushLog);

    /**
     * 修改消息推送记录
     *
     * @param sysNewsPushLog 消息推送记录
     * @return 结果
     */
    public int updateSysNewsPushLog(SysNewsPushLog sysNewsPushLog);
    /**
     * 批量修改消息状态接口
     */
    public int updateSysNewsPushLogBySendOutBy(SysNewsPushLog sysNewsPushLog);

    /**
     * 批量删除消息推送记录
     *
     * @param ids 需要删除的消息推送记录ID
     * @return 结果
     */
    public int deleteSysNewsPushLogByIds(Long[] ids);

    /**
     * 删除消息推送记录信息
     *
     * @param id 消息推送记录ID
     * @return 结果
     */
    public int deleteSysNewsPushLogById(Long id);

    /**
     * 导入消息推送记录Excel数据
     *
     * @param sysNewsPushLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<SysNewsPushLog> importSysNewsPushLog(List<SysNewsPushLog> sysNewsPushLogList, Boolean isUpdateSupport, String operName);
}
