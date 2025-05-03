package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.AppConnectorAttr;

/**
 * 应用实例连接器属性Service接口
 *
 * @author yabushan
 * @date 2023-11-25
 */
public interface IAppConnectorAttrService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearappConnectorAttrInfo();


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
    public int bathInsertAppConnectorAttr(List<AppConnectorAttr> appConnectorAttr);

    /**
     * 修改应用实例连接器属性
     *
     * @param appConnectorAttr 应用实例连接器属性
     * @return 结果
     */
    public int updateAppConnectorAttr(AppConnectorAttr appConnectorAttr);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除应用实例连接器属性
     *
     * @param attrIds 需要删除的应用实例连接器属性ID
     * @return 结果
     */
    public int deleteAppConnectorAttrByIds(String[] attrIds);

    /**
     * 删除应用实例连接器属性信息
     *
     * @param attrId 应用实例连接器属性ID
     * @return 结果
     */
    public int deleteAppConnectorAttrById(String attrId);

    /**
     * 导入应用实例连接器属性Excel数据
     *
     * @param appConnectorAttrList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<AppConnectorAttr> importAppConnectorAttr(List<AppConnectorAttr> appConnectorAttrList, Boolean isUpdateSupport, String operName);
}
