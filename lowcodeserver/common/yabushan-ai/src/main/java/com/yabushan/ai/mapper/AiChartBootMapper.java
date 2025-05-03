package com.yabushan.ai.mapper;

import java.util.List;
import java.util.Map;

import com.yabushan.ai.domain.AiChartBoot;

/**
 * aibootMapper接口
 *
 * @author yabushan
 * @date 2024-06-06
 */
public interface AiChartBootMapper
{
    /**
     * 查询aiboot
     *
     * @param Id aibootID
     * @return aiboot
     */
    public AiChartBoot selectAiChartBootById(Integer Id);

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
     * @param
     * @return 结果
     */
    public int bathInsertAiChartBoot(List<AiChartBoot> list);

    /**
     * 修改aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    public int updateAiChartBoot(AiChartBoot aiChartBoot);

    /**
     * 删除aiboot
     *
     * @param chartId aibootID
     * @return 结果
     */
    public int deleteAiChartBootById(Integer chartId);

    /**
     * 批量删除aiboot
     *
     * @param Ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAiChartBootByIds(Integer[] Ids);


    public List<Map> getSendTodoInfo();
}
