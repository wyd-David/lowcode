package com.yabushan.system.mapper;

import com.yabushan.system.domain.UumSyncLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同步时间记录表Mapper接口
 *
 * @author yabushan
 * @date 2022-04-27
 */
public interface UumSyncLogMapper {
    /**
     * 查询同步时间记录表
     *
     * @param id 同步时间记录表ID
     * @return 同步时间记录表
     */
    public UumSyncLog selectUumSyncLogById(String id);


    public UumSyncLog selectUumSyncLogByType(@Param("type") String type);

    /**
     * 查询同步时间记录表列表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 同步时间记录表集合
     */
    public List<UumSyncLog> selectUumSyncLogList(UumSyncLog uumSyncLog);

    /**
     * 新增同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    public int insertUumSyncLog(UumSyncLog uumSyncLog);

    /**
     * 批量新增同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    public int bathInsertUumSyncLog(List<UumSyncLog> list);

    /**
     * 修改同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    public int updateUumSyncLog(UumSyncLog uumSyncLog);

    /**
     * 删除同步时间记录表
     *
     * @param id 同步时间记录表ID
     * @return 结果
     */
    public int deleteUumSyncLogById(String id);

    /**
     * 批量删除同步时间记录表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUumSyncLogByIds(String[] ids);
}
