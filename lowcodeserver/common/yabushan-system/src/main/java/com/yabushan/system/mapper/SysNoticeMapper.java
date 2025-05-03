package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.SysNotice;

/**
 * 通知公告表 数据层
 *
 * @author ruoyi
 */
public interface SysNoticeMapper
{
    /**
     * 查询通知公告
     *
     * @param noticeId 通知公告ID
     * @return 通知公告
     */
    public SysNotice selectSysNoticeById(Long noticeId);

    /**
     * 查询通知公告列表
     *
     * @param sysNotice 通知公告
     * @return 通知公告集合
     */
    public List<SysNotice> selectSysNoticeList(SysNotice sysNotice);

    /**
     * 新增通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    public int insertSysNotice(SysNotice sysNotice);

    /**
     * 修改通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    public int updateSysNotice(SysNotice sysNotice);

    /**
     * 删除通知公告
     *
     * @param noticeId 通知公告ID
     * @return 结果
     */
    public int deleteSysNoticeById(Long noticeId);

    /**
     * 批量删除通知公告
     *
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysNoticeByIds(Long[] noticeIds);
}
