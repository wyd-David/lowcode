package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.yabushan.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.DataflowJobTaskMapper;
import com.yabushan.datasource.domain.DataflowJobTask;
import com.yabushan.datasource.service.IDataflowJobTaskService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作业任务Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobTaskServiceImpl implements IDataflowJobTaskService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobTaskServiceImpl.class);
    @Autowired
    private DataflowJobTaskMapper dataflowJobTaskMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJobTask> dataflowJobTaskInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void cleardataflowJobTaskInfo() {
        if (StringUtils.isNotNull(dataflowJobTaskInfo) && dataflowJobTaskInfo.size() > 0) {
            dataflowJobTaskInfo.clear();
        }
    }

    /**
     * 查询作业任务
     *
     * @param jobTaskId 作业任务ID
     * @return 作业任务
     */
    @Override
    public DataflowJobTask selectDataflowJobTaskById(String jobTaskId) {
        return dataflowJobTaskMapper.selectDataflowJobTaskById(jobTaskId);
    }

    /**
     * 查询作业任务列表
     *
     * @param dataflowJobTask 作业任务
     * @return 作业任务
     */
    @Override
    public List<DataflowJobTask> selectDataflowJobTaskList(DataflowJobTask dataflowJobTask) {
        return dataflowJobTaskMapper.selectDataflowJobTaskList(dataflowJobTask);
    }

    /**
     * 新增作业任务
     *
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    @Override
    public int insertDataflowJobTask(DataflowJobTask dataflowJobTask) {
        return dataflowJobTaskMapper.insertDataflowJobTask(dataflowJobTask);
    }

    /**
     * 批量新增作业任务
     *
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJobTask(List<DataflowJobTask> dataflowJobTask) {
        return dataflowJobTaskMapper.bathInsertDataflowJobTask(dataflowJobTask);
    }


    /**
     * 修改作业任务
     *
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    @Override
    public int updateDataflowJobTask(DataflowJobTask dataflowJobTask) {
        return dataflowJobTaskMapper.updateDataflowJobTask(dataflowJobTask);
    }

    /**
     * 批量删除作业任务
     *
     * @param jobTaskIds 需要删除的作业任务ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskByIds(String[] jobTaskIds) {
        return dataflowJobTaskMapper.deleteDataflowJobTaskByIds(jobTaskIds);
    }

    /**
     * 删除作业任务信息
     *
     * @param jobTaskId 作业任务ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskById(String jobTaskId) {
        return dataflowJobTaskMapper.deleteDataflowJobTaskById(jobTaskId);
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
                DataflowJobTask dataflowJobTask=dataflowJobTaskMapper.selectDataflowJobTaskById(idslist[i]);
                dataflowJobTask.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobTaskMapper.updateDataflowJobTask(dataflowJobTask);
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
     * 导入作业任务Excel数据
     *
     * @param dataflowJobTaskList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJobTask>  importDataflowJobTask(
            List<DataflowJobTask> dataflowJobTaskList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < dataflowJobTaskList.size(); i++) {
            DataflowJobTask dataflowJobTask=dataflowJobTaskList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJobTask u = dataflowJobTaskMapper.selectDataflowJobTaskById(dataflowJobTask.getJobTaskId());
                if (StringUtils.isNull(u)) {
                    dataflowJobTaskMapper.insertDataflowJobTask(dataflowJobTask);
                    dataflowJobTask.setImportStatus("导入成功");
                    dataflowJobTaskInfo.add(dataflowJobTask);
                } else if (isUpdateSupport) {
                    dataflowJobTaskMapper.updateDataflowJobTask(dataflowJobTask);
                    dataflowJobTask.setImportStatus("更新成功");
                    dataflowJobTaskInfo.add(dataflowJobTask);
                } else {
                    dataflowJobTask.setImportStatus("已存在当前数据");
                    dataflowJobTaskInfo.add(dataflowJobTask);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJobTask.setImportStatus("数据格式有问题，请认证检查数据！");
                dataflowJobTaskInfo.add(dataflowJobTask);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importDataflowJobTask(dataflowJobTaskList.subList(i + 1, dataflowJobTaskList.size()), isUpdateSupport, operName);
                return dataflowJobTaskInfo;
            }

        }
        return dataflowJobTaskInfo;
    }
}
