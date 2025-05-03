package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SysNewsTemplate;
import com.yabushan.system.mapper.SysNewsTemplateMapper;
import com.yabushan.system.service.ISysNewsTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 消息模板Service业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
@Service
public class SysNewsTemplateServiceImpl implements ISysNewsTemplateService
{
    private static final Logger log = LoggerFactory.getLogger(SysNewsTemplateServiceImpl.class);
    @Autowired
    private SysNewsTemplateMapper sysNewsTemplateMapper;
    //存放导入所有的成功失败数据
    private static List<SysNewsTemplate> sysNewsTemplateInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsysNewsTemplateInfo() {
        if (StringUtils.isNotNull(sysNewsTemplateInfo) && sysNewsTemplateInfo.size() > 0) {
                sysNewsTemplateInfo.clear();
        }
    }

    /**
     * 查询消息模板
     *
     * @param id 消息模板ID
     * @return 消息模板
     */
    @Override
    public SysNewsTemplate selectSysNewsTemplateById(String id)
    {
        return sysNewsTemplateMapper.selectSysNewsTemplateById(id);
    }

    /**
     * 查询消息模板列表
     *
     * @param sysNewsTemplate 消息模板
     * @return 消息模板
     */
    @Override
    public List<SysNewsTemplate> selectSysNewsTemplateList(SysNewsTemplate sysNewsTemplate)
    {
        return sysNewsTemplateMapper.selectSysNewsTemplateList(sysNewsTemplate);
    }

    /**
     * 新增消息模板
     *
     * @param sysNewsTemplate 消息模板
     * @return 结果
     */
    @Override
    public int insertSysNewsTemplate(SysNewsTemplate sysNewsTemplate)
    {
        sysNewsTemplate.setId(UUID.randomUUID().toString());
        sysNewsTemplate.setCreateTime(DateUtils.getNowDate());
        return sysNewsTemplateMapper.insertSysNewsTemplate(sysNewsTemplate);
    }

    /**
     * 修改消息模板
     *
     * @param sysNewsTemplate 消息模板
     * @return 结果
     */
    @Override
    public int updateSysNewsTemplate(SysNewsTemplate sysNewsTemplate)
    {
        sysNewsTemplate.setUpdateTime(DateUtils.getNowDate());
        return sysNewsTemplateMapper.updateSysNewsTemplate(sysNewsTemplate);
    }

    /**
     * 批量删除消息模板
     *
     * @param ids 需要删除的消息模板ID
     * @return 结果
     */
    @Override
    public int deleteSysNewsTemplateByIds(String[] ids)
    {
        return sysNewsTemplateMapper.deleteSysNewsTemplateByIds(ids);
    }

    /**
     * 删除消息模板信息
     *
     * @param id 消息模板ID
     * @return 结果
     */
    @Override
    public int deleteSysNewsTemplateById(String id)
    {
        return sysNewsTemplateMapper.deleteSysNewsTemplateById(id);
    }

    /**
     * 导入消息模板Excel数据
     *
     * @param sysNewsTemplateList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<SysNewsTemplate>  importSysNewsTemplate(List<SysNewsTemplate> sysNewsTemplateList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < sysNewsTemplateList.size(); i++) {
            SysNewsTemplate sysNewsTemplate=sysNewsTemplateList.get(i);
        try {
            // 验证是否存在这个用户
            SysNewsTemplate u = sysNewsTemplateMapper.selectSysNewsTemplateById(sysNewsTemplate.getId());
            if (StringUtils.isNull(u)) {
                    sysNewsTemplateMapper.insertSysNewsTemplate(sysNewsTemplate);
                    sysNewsTemplate.setImportStatus("导入成功");
                    sysNewsTemplateInfo.add(sysNewsTemplate);
            } else if (isUpdateSupport) {
                    sysNewsTemplateMapper.updateSysNewsTemplate(sysNewsTemplate);
                    sysNewsTemplate.setImportStatus("更新成功");
                    sysNewsTemplateInfo.add(sysNewsTemplate);
            } else {
                    sysNewsTemplate.setImportStatus("已存在当前数据");
                    sysNewsTemplateInfo.add(sysNewsTemplate);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            sysNewsTemplate.setImportStatus("数据格式有问题，请认证检查数据！");
            sysNewsTemplateInfo.add(sysNewsTemplate);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importSysNewsTemplate(sysNewsTemplateList.subList(i+1,sysNewsTemplateList.size()), isUpdateSupport, operName);
            return sysNewsTemplateInfo;
        }

    }
        return sysNewsTemplateInfo;
    }

    @Override
    public SysNewsTemplate getTemplateByName(String name) {
        return sysNewsTemplateMapper.getTemplateByName(name);
    }

    @Override
    public SysNewsTemplate getTemplateBytemplateIdentification(String templateIdentification) {
        return sysNewsTemplateMapper.getTemplateBytemplateIdentification(templateIdentification);
    }
}
