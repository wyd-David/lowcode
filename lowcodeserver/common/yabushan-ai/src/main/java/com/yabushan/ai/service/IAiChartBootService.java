package com.yabushan.ai.service;


import java.util.List;
import com.yabushan.ai.domain.AiChartBoot;

/**
 * aibootService接口
 *
 * @author yabushan
 * @date 2024-06-06
 */
public interface IAiChartBootService
{



    /**
     * 清空返回导入数据的集合
     */
    public void clearaiChartBootInfo();


    /**
     * 查询aiboot
     *
     * @param chartId aibootID
     * @return aiboot
     */
    public AiChartBoot selectAiChartBootById(Integer chartId);

    /**
     * 查询aiboot列表
     *
     * @param aiChartBoot aiboot
     * @return aiboot集合
     */
    public List<AiChartBoot> selectAiChartBootList(AiChartBoot aiChartBoot);
    public List<AiChartBoot> selectAiChartBootList2(AiChartBoot aiChartBoot);
    /**
     * 新增aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    public int insertAiChartBoot(AiChartBoot aiChartBoot);
    public int insertAiChartBoot2(AiChartBoot aiChartBoot);
    /**
     * 批量新增aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    public int bathInsertAiChartBoot(List<AiChartBoot> aiChartBoot);

    /**
     * 修改aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    public int updateAiChartBoot(AiChartBoot aiChartBoot);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除aiboot
     *
     * @param chartIds 需要删除的aibootID
     * @return 结果
     */
    public int deleteAiChartBootByIds(Integer[] chartIds);

    /**
     * 删除aiboot信息
     *
     * @param chartId aibootID
     * @return 结果
     */
    public int deleteAiChartBootById(Integer chartId);

    /**
     * 导入aibootExcel数据
     *
     * @param aiChartBootList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<AiChartBoot> importAiChartBoot(List<AiChartBoot> aiChartBootList, Boolean isUpdateSupport, String operName);
}
