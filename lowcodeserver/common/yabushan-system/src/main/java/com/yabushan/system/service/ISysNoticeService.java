package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.SysNotice;

/**
 * 公告 服务层
 *
 * @author ruoyi
 */
public interface ISysNoticeService
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
     * 批量删除通知公告
     *
     * @param noticeIds 需要删除的通知公告ID
     * @return 结果
     */
    public int deleteSysNoticeByIds(Long[] noticeIds);

    /**
     * 删除通知公告信息
     *
     * @param noticeId 通知公告ID
     * @return 结果
     */
    public int deleteSysNoticeById(Long noticeId);

    /**
     * 导入通知公告Excel数据
     *
     * @param sysNoticeList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<SysNotice> importSysNotice(List<SysNotice> sysNoticeList, Boolean isUpdateSupport, String operName);
}
