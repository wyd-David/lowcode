package com.yabushan.datasource.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.datasource.mapper.DataflowJobMapper;
import com.yabushan.datasource.domain.DataflowJob;
import com.yabushan.datasource.service.IDataflowJobService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作业Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobServiceImpl implements IDataflowJobService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobServiceImpl.class);
    @Autowired
    private DataflowJobMapper dataflowJobMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJob> dataflowJobInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void cleardataflowJobInfo() {
        if (StringUtils.isNotNull(dataflowJobInfo) && dataflowJobInfo.size() > 0) {
            dataflowJobInfo.clear();
        }
    }

    /**
     * 查询作业
     *
     * @param jobId 作业ID
     * @return 作业
     */
    @Override
    public DataflowJob selectDataflowJobById(String jobId) {
        return dataflowJobMapper.selectDataflowJobById(jobId);
    }

    /**
     * 查询作业列表
     *
     * @param dataflowJob 作业
     * @return 作业
     */
    @Override
    public List<DataflowJob> selectDataflowJobList(DataflowJob dataflowJob) {
        return dataflowJobMapper.selectDataflowJobList(dataflowJob);
    }

    /**
     * 新增作业
     *
     * @param dataflowJob 作业
     * @return 结果
     */
    @Override
    public int insertDataflowJob(DataflowJob dataflowJob) {
        dataflowJob.setCreateTime(DateUtils.getNowDate());
        return dataflowJobMapper.insertDataflowJob(dataflowJob);
    }

    /**
     * 批量新增作业
     *
     * @param dataflowJob 作业
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJob(List<DataflowJob> dataflowJob) {
        return dataflowJobMapper.bathInsertDataflowJob(dataflowJob);
    }


    /**
     * 修改作业
     *
     * @param dataflowJob 作业
     * @return 结果
     */
    @Override
    public int updateDataflowJob(DataflowJob dataflowJob) {
        return dataflowJobMapper.updateDataflowJob(dataflowJob);
    }

    /**
     * 批量删除作业
     *
     * @param jobIds 需要删除的作业ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobByIds(String[] jobIds) {
        return dataflowJobMapper.deleteDataflowJobByIds(jobIds);
    }

    /**
     * 删除作业信息
     *
     * @param jobId 作业ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobById(String jobId) {
        return dataflowJobMapper.deleteDataflowJobById(jobId);
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
                DataflowJob dataflowJob=dataflowJobMapper.selectDataflowJobById(idslist[i]);
                dataflowJob.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobMapper.updateDataflowJob(dataflowJob);
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
     * 导入作业Excel数据
     *
     * @param dataflowJobList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJob>  importDataflowJob(
            List<DataflowJob> dataflowJobList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < dataflowJobList.size(); i++) {
            DataflowJob dataflowJob=dataflowJobList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJob u = dataflowJobMapper.selectDataflowJobById(dataflowJob.getJobId());
                if (StringUtils.isNull(u)) {
                    dataflowJobMapper.insertDataflowJob(dataflowJob);
                    dataflowJob.setImportStatus("导入成功");
                    dataflowJobInfo.add(dataflowJob);
                } else if (isUpdateSupport) {
                    dataflowJobMapper.updateDataflowJob(dataflowJob);
                    dataflowJob.setImportStatus("更新成功");
                    dataflowJobInfo.add(dataflowJob);
                } else {
                    dataflowJob.setImportStatus("已存在当前数据");
                    dataflowJobInfo.add(dataflowJob);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJob.setImportStatus("数据格式有问题，请认证检查数据！");
                dataflowJobInfo.add(dataflowJob);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importDataflowJob(dataflowJobList.subList(i + 1, dataflowJobList.size()), isUpdateSupport, operName);
                return dataflowJobInfo;
            }

        }
        return dataflowJobInfo;
    }
}
