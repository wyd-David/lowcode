package com.yabushan.capacity.mapper;

import java.util.List;
import com.yabushan.capacity.domain.NgsTextVoidInfo;

/**
 * 文字转声音Mapper接口
 * 
 * @author yabushan
 * @date 2024-02-20
 */
public interface NgsTextVoidInfoMapper 
{
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
     * @param list 文字转声音
     * @return 结果
     */
    public int bathInsertNgsTextVoidInfo(List<NgsTextVoidInfo> list);

    /**
     * 修改文字转声音
     * 
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    public int updateNgsTextVoidInfo(NgsTextVoidInfo ngsTextVoidInfo);

    /**
     * 删除文字转声音
     * 
     * @param textId 文字转声音ID
     * @return 结果
     */
    public int deleteNgsTextVoidInfoById(String textId);

    /**
     * 批量删除文字转声音
     * 
     * @param textIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNgsTextVoidInfoByIds(String[] textIds);
}
