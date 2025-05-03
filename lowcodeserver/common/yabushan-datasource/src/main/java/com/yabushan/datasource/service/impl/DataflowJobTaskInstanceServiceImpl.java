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
import com.yabushan.datasource.mapper.DataflowJobTaskInstanceMapper;
import com.yabushan.datasource.domain.DataflowJobTaskInstance;
import com.yabushan.datasource.service.IDataflowJobTaskInstanceService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作业任务实例Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobTaskInstanceServiceImpl implements IDataflowJobTaskInstanceService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobTaskInstanceServiceImpl.class);
    @Autowired
    private DataflowJobTaskInstanceMapper dataflowJobTaskInstanceMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJobTaskInstance> dataflowJobTaskInstanceInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void cleardataflowJobTaskInstanceInfo() {
        if (StringUtils.isNotNull(dataflowJobTaskInstanceInfo) && dataflowJobTaskInstanceInfo.size() > 0) {
                dataflowJobTaskInstanceInfo.clear();
        }
    }

    /**
     * 查询作业任务实例
     *
     * @param jobTaskInstanceId 作业任务实例ID
     * @return 作业任务实例
     */
    @Override
    public DataflowJobTaskInstance selectDataflowJobTaskInstanceById(String jobTaskInstanceId) {
        return dataflowJobTaskInstanceMapper.selectDataflowJobTaskInstanceById(jobTaskInstanceId);
    }

    /**
     * 查询作业任务实例列表
     *
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 作业任务实例
     */
    @Override
    public List<DataflowJobTaskInstance> selectDataflowJobTaskInstanceList(DataflowJobTaskInstance dataflowJobTaskInstance) {
        return dataflowJobTaskInstanceMapper.selectDataflowJobTaskInstanceList(dataflowJobTaskInstance);
    }

    /**
     * 新增作业任务实例
     *
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    @Override
    public int insertDataflowJobTaskInstance(DataflowJobTaskInstance dataflowJobTaskInstance) {
                                                                                                dataflowJobTaskInstance.setCreateTime(DateUtils.getNowDate());
                                                                                                                                                                                                                                                                                                                                                                                                    return dataflowJobTaskInstanceMapper.insertDataflowJobTaskInstance(dataflowJobTaskInstance);
    }

    /**
     * 批量新增作业任务实例
     *
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJobTaskInstance(List<DataflowJobTaskInstance> dataflowJobTaskInstance) {
        return dataflowJobTaskInstanceMapper.bathInsertDataflowJobTaskInstance(dataflowJobTaskInstance);
    }


    /**
     * 修改作业任务实例
     *
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    @Override
    public int updateDataflowJobTaskInstance(DataflowJobTaskInstance dataflowJobTaskInstance) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        return dataflowJobTaskInstanceMapper.updateDataflowJobTaskInstance(dataflowJobTaskInstance);
    }

    /**
     * 批量删除作业任务实例
     *
     * @param jobTaskInstanceIds 需要删除的作业任务实例ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskInstanceByIds(String[] jobTaskInstanceIds) {
        return dataflowJobTaskInstanceMapper.deleteDataflowJobTaskInstanceByIds(jobTaskInstanceIds);
    }

    /**
     * 删除作业任务实例信息
     *
     * @param jobTaskInstanceId 作业任务实例ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskInstanceById(String jobTaskInstanceId) {
        return dataflowJobTaskInstanceMapper.deleteDataflowJobTaskInstanceById(jobTaskInstanceId);
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
                DataflowJobTaskInstance dataflowJobTaskInstance=dataflowJobTaskInstanceMapper.selectDataflowJobTaskInstanceById(idslist[i]);
                dataflowJobTaskInstance.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobTaskInstanceMapper.updateDataflowJobTaskInstance(dataflowJobTaskInstance);
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
     * 导入作业任务实例Excel数据
     *
     * @param dataflowJobTaskInstanceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJobTaskInstance>  importDataflowJobTaskInstance(
    List<DataflowJobTaskInstance> dataflowJobTaskInstanceList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < dataflowJobTaskInstanceList.size(); i++) {
            DataflowJobTaskInstance dataflowJobTaskInstance=dataflowJobTaskInstanceList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJobTaskInstance u = dataflowJobTaskInstanceMapper.selectDataflowJobTaskInstanceById(dataflowJobTaskInstance.getJobTaskInstanceId());
                if (StringUtils.isNull(u)) {
                        dataflowJobTaskInstanceMapper.insertDataflowJobTaskInstance(dataflowJobTaskInstance);
                    dataflowJobTaskInstance.setImportStatus("导入成功");
                        dataflowJobTaskInstanceInfo.add(dataflowJobTaskInstance);
                } else if (isUpdateSupport) {
                        dataflowJobTaskInstanceMapper.updateDataflowJobTaskInstance(dataflowJobTaskInstance);
                    dataflowJobTaskInstance.setImportStatus("更新成功");
                        dataflowJobTaskInstanceInfo.add(dataflowJobTaskInstance);
                } else {
                    dataflowJobTaskInstance.setImportStatus("已存在当前数据");
                        dataflowJobTaskInstanceInfo.add(dataflowJobTaskInstance);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJobTaskInstance.setImportStatus("数据格式有问题，请认证检查数据！");
                    dataflowJobTaskInstanceInfo.add(dataflowJobTaskInstance);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importDataflowJobTaskInstance(dataflowJobTaskInstanceList.subList(i + 1, dataflowJobTaskInstanceList.size()), isUpdateSupport, operName);
                return dataflowJobTaskInstanceInfo;
            }

        }
        return dataflowJobTaskInstanceInfo;
    }
}
