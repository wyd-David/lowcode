package com.yabushan.capacity.service;

import java.util.List;
import com.yabushan.capacity.domain.NgsTextVoidInfo;

/**
 * 文字转声音Service接口
 *
 * @author yabushan
 * @date 2024-02-20
 */
public interface INgsTextVoidInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearngsTextVoidInfoInfo();


    /**
     * 查询文字转声音
     *
     * @param textId 文字转声音ID
     * @return 文字转声音
     */
    public NgsTextVoidInfo selectNgsTextVoidInfoById(String textId);

    /**
     * 查询文字转声音列表
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 文字转声音集合
     */
    public List<NgsTextVoidInfo> selectNgsTextVoidInfoList(NgsTextVoidInfo ngsTextVoidInfo);

    /**
     * 新增文字转声音
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    public int insertNgsTextVoidInfo(NgsTextVoidInfo ngsTextVoidInfo);

    /**
     * 批量新增文字转声音
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    public int bathInsertNgsTextVoidInfo(List<NgsTextVoidInfo> ngsTextVoidInfo);

    /**
     * 修改文字转声音
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    public int updateNgsTextVoidInfo(NgsTextVoidInfo ngsTextVoidInfo);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除文字转声音
     *
     * @param textIds 需要删除的文字转声音ID
     * @return 结果
     */
    public int deleteNgsTextVoidInfoByIds(String[] textIds);

    /**
     * 删除文字转声音信息
     *
     * @param textId 文字转声音ID
     * @return 结果
     */
    public int deleteNgsTextVoidInfoById(String textId);

    /**
     * 导入文字转声音Excel数据
     *
     * @param ngsTextVoidInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<NgsTextVoidInfo> importNgsTextVoidInfo(List<NgsTextVoidInfo> ngsTextVoidInfoList, Boolean isUpdateSupport, String operName);
}
