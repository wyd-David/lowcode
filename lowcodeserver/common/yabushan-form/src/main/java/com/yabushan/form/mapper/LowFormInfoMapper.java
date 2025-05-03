package com.yabushan.form.mapper;

import java.util.List;
import java.util.Map;

import com.yabushan.form.domain.LowFormInfo;
import com.yabushan.form.vo.AutoParamVo;
import org.apache.ibatis.annotations.MapKey;

/**
 * 基础单信息Mapper接口
 *
 * @author yabushan
 * @date 2022-10-10
 */
public interface LowFormInfoMapper
{
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
    public int bathInsertLowFormInfo(List<LowFormInfo> list);

    /**
     * 修改基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    public int updateLowFormInfo(LowFormInfo lowFormInfo);

    /**
     * 删除基础单信息
     *
     * @param formUuid 基础单信息ID
     * @return 结果
     */
    public int deleteLowFormInfoById(String formUuid);

    /**
     * 批量删除基础单信息
     *
     * @param formUuids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLowFormInfoByIds(String[] formUuids);

    public  List<Map<String, Object>> tableDataInfo(AutoParamVo autoParamVo);

    public LowFormInfo selectLowFormInfoByFormEntityName(String formEntityName);
}
