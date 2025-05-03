package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.WarnSmsLog;

/**
 * 短信日志记录Service接口
 *
 * @author yabushan
 * @date 2022-05-10
 */
public interface IWarnSmsLogService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearwarnSmsLogInfo();


    /**
     * 查询短信日志记录
     *
     * @param id 短信日志记录ID
     * @return 短信日志记录
     */
    public WarnSmsLog selectWarnSmsLogById(String id);

    /**
     * 查询短信日志记录列表
     *
     * @param warnSmsLog 短信日志记录
     * @return 短信日志记录集合
     */
    public List<WarnSmsLog> selectWarnSmsLogList(WarnSmsLog warnSmsLog);

    /**
     * 新增短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    public int insertWarnSmsLog(WarnSmsLog warnSmsLog);

    /**
     * 批量新增短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    public int bathInsertWarnSmsLog(List<WarnSmsLog> warnSmsLog);

    /**
     * 修改短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    public int updateWarnSmsLog(WarnSmsLog warnSmsLog);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除短信日志记录
     *
     * @param ids 需要删除的短信日志记录ID
     * @return 结果
     */
    public int deleteWarnSmsLogByIds(String[] ids);

    /**
     * 删除短信日志记录信息
     *
     * @param id 短信日志记录ID
     * @return 结果
     */
    public int deleteWarnSmsLogById(String id);



    /**
     * 导入短信日志记录Excel数据
     *
     * @param warnSmsLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<WarnSmsLog> importWarnSmsLog(List<WarnSmsLog> warnSmsLogList, Boolean isUpdateSupport, String operName);


    void send();

    void sendTest();

    void insertSMSSendLog();

}
