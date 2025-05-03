package com.yabushan.ai.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.ai.mapper.AiChartBootMapper;
import com.yabushan.ai.domain.AiChartBoot;
import com.yabushan.ai.service.IAiChartBootService;
import org.springframework.transaction.annotation.Transactional;

/**
 * aibootService业务层处理
 *
 * @author yabushan
 * @date 2024-06-06
 */
@Service
public class AiChartBootServiceImpl implements IAiChartBootService {
    private static final Logger log = LoggerFactory.getLogger(AiChartBootServiceImpl.class);
    @Autowired
    private AiChartBootMapper aiChartBootMapper;

    //存放导入所有的成功失败数据
    private static List<AiChartBoot> aiChartBootInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void clearaiChartBootInfo() {
        if (StringUtils.isNotNull(aiChartBootInfo) && aiChartBootInfo.size() > 0) {
            aiChartBootInfo.clear();
        }
    }

    /**
     * 查询aiboot
     *
     * @param chartId aibootID
     * @return aiboot
     */
    @Override
    public AiChartBoot selectAiChartBootById(Integer chartId) {
        return aiChartBootMapper.selectAiChartBootById(chartId);
    }

    /**
     * 查询aiboot列表
     *
     * @param aiChartBoot aiboot
     * @return aiboot
     */
    @Override
    public List<AiChartBoot> selectAiChartBootList(AiChartBoot aiChartBoot) {
        return aiChartBootMapper.selectAiChartBootList(aiChartBoot);
    }
    @Override
    public List<AiChartBoot> selectAiChartBootList2(AiChartBoot aiChartBoot) {
        return aiChartBootMapper.selectAiChartBootList2(aiChartBoot);
    }

    /**
     * 新增aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    @Override
    public int insertAiChartBoot(AiChartBoot aiChartBoot) {
        return aiChartBootMapper.insertAiChartBoot(aiChartBoot);
    }
    @Override
    public int insertAiChartBoot2(AiChartBoot aiChartBoot) {
        return aiChartBootMapper.insertAiChartBoot2(aiChartBoot);
    }


    /**
     * 批量新增aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    @Override
    public int bathInsertAiChartBoot(List<AiChartBoot> aiChartBoot) {
        return aiChartBootMapper.bathInsertAiChartBoot(aiChartBoot);
    }


    /**
     * 修改aiboot
     *
     * @param aiChartBoot aiboot
     * @return 结果
     */
    @Override
    public int updateAiChartBoot(AiChartBoot aiChartBoot) {
        return aiChartBootMapper.updateAiChartBoot(aiChartBoot);
    }

    /**
     * 批量删除aiboot
     *
     * @param chartIds 需要删除的aibootID
     * @return 结果
     */
    @Override
    public int deleteAiChartBootByIds(Integer[] chartIds) {
        return aiChartBootMapper.deleteAiChartBootByIds(chartIds);
    }

    /**
     * 删除aiboot信息
     *
     * @param chartId aibootID
     * @return 结果
     */
    @Override
    public int deleteAiChartBootById(Integer chartId) {
        return aiChartBootMapper.deleteAiChartBootById(chartId);
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     * @param ids
     * @param disableEnableState
     * @return
     */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[] = ids.split("," , -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                // todo idslist[i] 返回的是一个字符串，如果实体id是其他类型，需要转换，如是int类型就修改成Integer.parseInt(idslist[i])
                AiChartBoot aiChartBoot=aiChartBootMapper.selectAiChartBootById(Integer.valueOf(idslist[i]));
                aiChartBoot.setDisableEnableState(disableEnableState);
                int isappinfo = aiChartBootMapper.updateAiChartBoot(aiChartBoot);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 导入aibootExcel数据
     *
     * @param aiChartBootList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<AiChartBoot>  importAiChartBoot(
            List<AiChartBoot> aiChartBootList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < aiChartBootList.size(); i++) {
            AiChartBoot aiChartBoot=aiChartBootList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                AiChartBoot u = aiChartBootMapper.selectAiChartBootById(aiChartBoot.getId());
                if (StringUtils.isNull(u)) {
                    aiChartBootMapper.insertAiChartBoot(aiChartBoot);
                    aiChartBoot.setImportStatus("导入成功");
                    aiChartBootInfo.add(aiChartBoot);
                } else if (isUpdateSupport) {
                    aiChartBootMapper.updateAiChartBoot(aiChartBoot);
                    aiChartBoot.setImportStatus("更新成功");
                    aiChartBootInfo.add(aiChartBoot);
                } else {
                    aiChartBoot.setImportStatus("已存在当前数据");
                    aiChartBootInfo.add(aiChartBoot);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                aiChartBoot.setImportStatus("数据格式有问题，请认证检查数据！");
                aiChartBootInfo.add(aiChartBoot);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importAiChartBoot(aiChartBootList.subList(i + 1, aiChartBootList.size()), isUpdateSupport, operName);
                return aiChartBootInfo;
            }

        }
        return aiChartBootInfo;
    }
}
