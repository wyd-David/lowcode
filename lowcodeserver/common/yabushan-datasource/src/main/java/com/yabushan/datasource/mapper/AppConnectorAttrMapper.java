package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.AppConnectorAttr;

/**
 * 应用实例连接器属性Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-25
 */
public interface AppConnectorAttrMapper 
{
    /**
     * 查询应用实例连接器属性
     * 
     * @param attrId 应用实例连接器属性ID
     * @return 应用实例连接器属性
     */
    public AppConnectorAttr selectAppConnectorAttrById(String attrId);

    /**
     * 查询应用实例连接器属性列表
     * 
     * @param appConnectorAttr 应用实例连接器属性
     * @return 应用实例连接器属性集合
     */
    public List<AppConnectorAttr> selectAppConnectorAttrList(AppConnectorAttr appConnectorAttr);

    /**
     * 新增应用实例连接器属性
     * 
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    public int insertAppConnectorAttr(AppConnectorAttr appConnectorAttr);

    /**
     * 批量新增应用实例连接器属性
     *
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    public int bathInsertAppConnectorAttr(List<AppConnectorAttr> list);

    /**
     * 修改应用实例连接器属性
     * 
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    public int updateAppConnectorAttr(AppConnectorAttr appConnectorAttr);

    /**
     * 删除应用实例连接器属性
     * 
     * @param attrId 应用实例连接器属性ID
     * @return 结果
     */
    public int deleteAppConnectorAttrById(String attrId);

    /**
     * 批量删除应用实例连接器属性
     * 
     * @param attrIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppConnectorAttrByIds(String[] attrIds);
}
