package com.yabushan.form.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.form.domain.LowFormInfo;

/**
 * 基础单信息Service接口
 *
 * @author yabushan
 * @date 2022-10-10
 */
public interface ILowFormInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearlowFormInfoInfo();


    /**
     * 查询基础单信息
     *
     * @param formUuid 基础单信息ID
     * @return 基础单信息
     */
    public LowFormInfo selectLowFormInfoById(String formUuid);

    /**
     * 查询基础单信息列表
     *
     * @param lowFormInfo 基础单信息
     * @return 基础单信息集合
     */
    public List<LowFormInfo> selectLowFormInfoList(LowFormInfo lowFormInfo);

    /**
     * 新增基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    public int insertLowFormInfo(LowFormInfo lowFormInfo);

    /**
     * 批量新增基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    public int bathInsertLowFormInfo(List<LowFormInfo> lowFormInfo);

    /**
     * 修改基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    public int updateLowFormInfo(LowFormInfo lowFormInfo);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除基础单信息
     *
     * @param formUuids 需要删除的基础单信息ID
     * @return 结果
     */
    public int deleteLowFormInfoByIds(String[] formUuids);

    /**
     * 删除基础单信息信息
     *
     * @param formUuid 基础单信息ID
     * @return 结果
     */
    public int deleteLowFormInfoById(String formUuid);

    /**
     * 导入基础单信息Excel数据
     *
     * @param lowFormInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<LowFormInfo> importLowFormInfo(List<LowFormInfo> lowFormInfoList, Boolean isUpdateSupport, String operName);

    /**
     * 根据sql动态获取一条数据
     * @param formId
     * @param key
     * @return
     */
    public List<Map<String, Object>> queryOneAutoSqlInfo(String formId, String key);

    public  AjaxResult saveFormInfo(Map paramMap) throws Exception;

    public JSONObject getData(String ApiCode , String param, String rowType );
    public String result(String param, String apiCode,String rowType);

    public LowFormInfo selectLowFormInfoByFormEntityName(String entityName);

    public  AjaxResult saveChildFormInfo(Map paramMap) throws Exception;
}
