package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.EtlWorkCollect;

/**
 * 作业开发Mapper接口
 * 
 * @author yabushan
 * @date 2024-01-21
 */
public interface EtlWorkCollectMapper 
{
    /**
     * 查询作业开发
     * 
     * @param collectId 作业开发ID
     * @return 作业开发
     */
    public EtlWorkCollect selectEtlWorkCollectById(String collectId);

    /**
     * 查询作业开发列表
     * 
     * @param etlWorkCollect 作业开发
     * @return 作业开发集合
     */
    public List<EtlWorkCollect> selectEtlWorkCollectList(EtlWorkCollect etlWorkCollect);

    /**
     * 新增作业开发
     * 
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    public int insertEtlWorkCollect(EtlWorkCollect etlWorkCollect);

    /**
     * 批量新增作业开发
     *
     * @param list 作业开发
     * @return 结果
     */
    public int bathInsertEtlWorkCollect(List<EtlWorkCollect> list);

    /**
     * 修改作业开发
     * 
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    public int updateEtlWorkCollect(EtlWorkCollect etlWorkCollect);

    /**
     * 删除作业开发
     * 
     * @param collectId 作业开发ID
     * @return 结果
     */
    public int deleteEtlWorkCollectById(String collectId);

    /**
     * 批量删除作业开发
     * 
     * @param collectIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEtlWorkCollectByIds(String[] collectIds);
}
