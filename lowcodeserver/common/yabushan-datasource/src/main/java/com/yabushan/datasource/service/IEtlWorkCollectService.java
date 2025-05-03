package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.EtlWorkCollect;

/**
 * 作业开发Service接口
 *
 * @author yabushan
 * @date 2024-01-21
 */
public interface IEtlWorkCollectService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearetlWorkCollectInfo();


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
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    public int bathInsertEtlWorkCollect(List<EtlWorkCollect> etlWorkCollect);

    /**
     * 修改作业开发
     *
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    public int updateEtlWorkCollect(EtlWorkCollect etlWorkCollect);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业开发
     *
     * @param collectIds 需要删除的作业开发ID
     * @return 结果
     */
    public int deleteEtlWorkCollectByIds(String[] collectIds);

    /**
     * 删除作业开发信息
     *
     * @param collectId 作业开发ID
     * @return 结果
     */
    public int deleteEtlWorkCollectById(String collectId);

    /**
     * 导入作业开发Excel数据
     *
     * @param etlWorkCollectList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<EtlWorkCollect> importEtlWorkCollect(List<EtlWorkCollect> etlWorkCollectList, Boolean isUpdateSupport, String operName);
}
