package com.yabushan.quartz.task;

import com.yabushan.quartz.service.CheckedResultJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Copyright: ...
 * Company: ...
 * Version: 1.0.0
 * Author: ...
 * Date: 2022/7/19
 * Description:
 */
@Component
public class CheckedResultTask {

    @Autowired
    private CheckedResultJobService checkedResultJobService;
    /**
     * 计算分数定时器
     */
    public void checkedResult(){
        /*
        * 每分钟启动执行
        * 获取计算历史记录，获取最后同步时间（精确到时分），需要处理最后一次处理时间到当前时间内每分钟相关的记录
        * 获取当前时间相关截止时间的评分人记录
        * 获取所有相关评分人评分记录
        * 根据规则计算以下分数：
        *   1、领导小组评分结果：每个分组相关组长、副组长得分；
        *   2、领导小组评分结果：每个分组相关得分
        *   3、巡查办评分结果：每个分组相关得分
        *   4、被巡查单位结果：每个分组相关得分
        *   5、巡察组当前批次最终得分
        *   6、巡察员评分：每个分组成员个人得分
        * 更新相关评分人记录状态：
        *   1、更新评分关系记录状态；
        *   2、更新申请评分明细记录状态；
        *   3、判断更新申请记录状态
        * 生成优秀优良巡察员明细记录（待申报状态）
        * 回收未评分的评分人待办
        * 记录同步历史（同步时间、同步统计结果、同步处理数据量等）
        * */
        checkedResultJobService.checkedResult();

    }
}
