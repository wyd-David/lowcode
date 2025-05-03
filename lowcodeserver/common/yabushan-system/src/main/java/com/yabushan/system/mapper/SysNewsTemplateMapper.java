package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.SysNewsTemplate;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 消息模板Mapper接口
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
public interface SysNewsTemplateMapper
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
     * 删除消息模板
     *
     * @param id 消息模板ID
     * @return 结果
     */
    public int deleteSysNewsTemplateById(String id);

    /**
     * 批量删除消息模板
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysNewsTemplateByIds(String[] ids);

    //根据名称查询模板
    SysNewsTemplate getTemplateByName(@Param("name") String name);

    public SysNewsTemplate getTemplateBytemplateIdentification(@Param("templateIdentification")String templateIdentification);
}
