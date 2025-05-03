package com.yabushan.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.service.ISysNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.domain.SysNotice;
import com.yabushan.system.mapper.SysNoticeMapper;

/**
 * 公告 服务层实现
 *
 * @author ruoyi
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    private static final Logger log = LoggerFactory.getLogger(SysNoticeServiceImpl.class);
    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    //存放导入所有的成功失败数据
    private static List<SysNotice> sysNoticeInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearsysNoticeInfo() {
        if (StringUtils.isNotNull(sysNoticeInfo) && sysNoticeInfo.size() > 0) {
            sysNoticeInfo.clear();
        }
    }

    /**
     * 查询通知公告
     *
     * @param noticeId 通知公告ID
     * @return 通知公告
     */
    @Override
    public SysNotice selectSysNoticeById(Long noticeId)
    {
        return sysNoticeMapper.selectSysNoticeById(noticeId);
    }

    /**
     * 查询通知公告列表
     *
     * @param sysNotice 通知公告
     * @return 通知公告
     */
    @Override
    public List<SysNotice> selectSysNoticeList(SysNotice sysNotice)
    {
        String userName = SecurityUtils.getUsername();
        if(!"admin".equals(userName)){
            sysNotice.setCreateBy(userName);
        }
        return sysNoticeMapper.selectSysNoticeList(sysNotice);
    }

    /**
     * 新增通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    @Override
    public int insertSysNotice(SysNotice sysNotice)
    {
        sysNotice.setCreateTime(DateUtils.getNowDate());
        sysNotice.setCreateBy(SecurityUtils.getUsername());
        return sysNoticeMapper.insertSysNotice(sysNotice);
    }

    /**
     * 修改通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    @Override
    public int updateSysNotice(SysNotice sysNotice)
    {
        sysNotice.setUpdateTime(DateUtils.getNowDate());
        return sysNoticeMapper.updateSysNotice(sysNotice);
    }

    /**
     * 批量删除通知公告
     *
     * @param noticeIds 需要删除的通知公告ID
     * @return 结果
     */
    @Override
    public int deleteSysNoticeByIds(Long[] noticeIds)
    {
        return sysNoticeMapper.deleteSysNoticeByIds(noticeIds);
    }

    /**
     * 删除通知公告信息
     *
     * @param noticeId 通知公告ID
     * @return 结果
     */
    @Override
    public int deleteSysNoticeById(Long noticeId)
    {
        return sysNoticeMapper.deleteSysNoticeById(noticeId);
    }


    /**
     * 导入通知公告Excel数据
     *
     * @param sysNoticeList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<SysNotice>  importSysNotice(List<SysNotice> sysNoticeList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < sysNoticeList.size(); i++) {
            SysNotice sysNotice=sysNoticeList.get(i);
            try {
                // 验证是否存在这个用户
                SysNotice u = sysNoticeMapper.selectSysNoticeById(sysNotice.getNoticeId());
                if (StringUtils.isNull(u)) {
                    sysNoticeMapper.insertSysNotice(sysNotice);
                    sysNotice.setImportStatus("导入成功");
                    sysNoticeInfo.add(sysNotice);
                } else if (isUpdateSupport) {
                    sysNoticeMapper.updateSysNotice(sysNotice);
                    sysNotice.setImportStatus("更新成功");
                    sysNoticeInfo.add(sysNotice);
                } else {
                    sysNotice.setImportStatus("已存在当前数据");
                    sysNoticeInfo.add(sysNotice);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                sysNotice.setImportStatus("数据格式有问题，请认证检查数据！");
                sysNoticeInfo.add(sysNotice);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importSysNotice(sysNoticeList.subList(i+1,sysNoticeList.size()), isUpdateSupport, operName);
                return sysNoticeInfo;
            }

        }
        return sysNoticeInfo;
    }
}
