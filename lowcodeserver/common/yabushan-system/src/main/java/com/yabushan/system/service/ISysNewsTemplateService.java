package com.yabushan.system.service;

import com.yabushan.system.domain.SysNewsTemplate;

import java.util.List;

/**
 * 消息模板Service接口
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
public interface ISysNewsTemplateService
{
    /**
     * 查询消息模板
     *
     * @param id 消息模板ID
     * @return 消息模板
     */
    public SysNewsTemplate selectSysNewsTemplateById(String id);

    /**
     * 查询消息模板列表
     *
     * @param sysNewsTemplate 消息模板
     * @return 消息模板集合
     */
    public List<SysNewsTemplate> selectSysNewsTemplateList(SysNewsTemplate sysNewsTemplate);

    /**
     * 新增消息模板
     *
     * @param sysNewsTemplate 消息模板
     * @return 结果
     */
    public int insertSysNewsTemplate(SysNewsTemplate sysNewsTemplate);

    /**
     * 修改消息模板
     *
     * @param sysNewsTemplate 消息模板
     * @return 结果
     */
    public int updateSysNewsTemplate(SysNewsTemplate sysNewsTemplate);

    /**
     * 批量删除消息模板
     *
     * @param ids 需要删除的消息模板ID
     * @return 结果
     */
    public int deleteSysNewsTemplateByIds(String[] ids);

    /**
     * 删除消息模板信息
     *
     * @param id 消息模板ID
     * @return 结果
     */
    public int deleteSysNewsTemplateById(String id);


    /**
     * 导入消息模板Excel数据
     *
     * @param sysNewsTemplateList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<SysNewsTemplate> importSysNewsTemplate(List<SysNewsTemplate> sysNewsTemplateList, Boolean isUpdateSupport, String operName);

    //根据名称查询模板
    SysNewsTemplate getTemplateByName(String name);

    //根据名称查询模板
    SysNewsTemplate getTemplateBytemplateIdentification(String templateIdentification);
}
