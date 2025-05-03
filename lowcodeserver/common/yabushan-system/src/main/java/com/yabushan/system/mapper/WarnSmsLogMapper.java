package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.WarnSmsLog;
import org.apache.ibatis.annotations.Param;

/**
 * 短信日志记录Mapper接口
 *
 * @author yabushan
 * @date 2022-05-10
 */
public interface WarnSmsLogMapper
{
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
     * @param list 短信日志记录
     * @return 结果
     */
    public int bathInsertWarnSmsLog(List<WarnSmsLog> list);

    /**
     * 修改短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    public int updateWarnSmsLog(WarnSmsLog warnSmsLog);

    /**
     * 删除短信日志记录
     *
     * @param id 短信日志记录ID
     * @return 结果
     */
    public int deleteWarnSmsLogById(String id);

    /**
     * 批量删除短信日志记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWarnSmsLogByIds(String[] ids);


    List<WarnSmsLog> getSMSSendLogByStatus(@Param("status") String status);
}
