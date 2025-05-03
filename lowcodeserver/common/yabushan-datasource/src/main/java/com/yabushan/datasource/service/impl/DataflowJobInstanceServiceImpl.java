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
import com.yabushan.datasource.mapper.DataflowJobInstanceMapper;
import com.yabushan.datasource.domain.DataflowJobInstance;
import com.yabushan.datasource.service.IDataflowJobInstanceService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作业运行实例Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobInstanceServiceImpl implements IDataflowJobInstanceService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobInstanceServiceImpl.class);
    @Autowired
    private DataflowJobInstanceMapper dataflowJobInstanceMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJobInstance> dataflowJobInstanceInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void cleardataflowJobInstanceInfo() {
        if (StringUtils.isNotNull(dataflowJobInstanceInfo) && dataflowJobInstanceInfo.size() > 0) {
                dataflowJobInstanceInfo.clear();
        }
    }

    /**
     * 查询作业运行实例
     *
     * @param jobInstanceId 作业运行实例ID
     * @return 作业运行实例
     */
    @Override
    public DataflowJobInstance selectDataflowJobInstanceById(String jobInstanceId) {
        return dataflowJobInstanceMapper.selectDataflowJobInstanceById(jobInstanceId);
    }

    /**
     * 查询作业运行实例列表
     *
     * @param dataflowJobInstance 作业运行实例
     * @return 作业运行实例
     */
    @Override
    public List<DataflowJobInstance> selectDataflowJobInstanceList(DataflowJobInstance dataflowJobInstance) {
        return dataflowJobInstanceMapper.selectDataflowJobInstanceList(dataflowJobInstance);
    }

    /**
     * 新增作业运行实例
     *
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    @Override
    public int insertDataflowJobInstance(DataflowJobInstance dataflowJobInstance) {
                                                                            dataflowJobInstance.setCreateTime(DateUtils.getNowDate());
                                                                                                                                                                                                                                                                                                                                        return dataflowJobInstanceMapper.insertDataflowJobInstance(dataflowJobInstance);
    }

    /**
     * 批量新增作业运行实例
     *
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJobInstance(List<DataflowJobInstance> dataflowJobInstance) {
        return dataflowJobInstanceMapper.bathInsertDataflowJobInstance(dataflowJobInstance);
    }


    /**
     * 修改作业运行实例
     *
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    @Override
    public int updateDataflowJobInstance(DataflowJobInstance dataflowJobInstance) {
                                                                                                                                                                                                                                                                                                                                                                                        return dataflowJobInstanceMapper.updateDataflowJobInstance(dataflowJobInstance);
    }

    /**
     * 批量删除作业运行实例
     *
     * @param jobInstanceIds 需要删除的作业运行实例ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobInstanceByIds(String[] jobInstanceIds) {
        return dataflowJobInstanceMapper.deleteDataflowJobInstanceByIds(jobInstanceIds);
    }

    /**
     * 删除作业运行实例信息
     *
     * @param jobInstanceId 作业运行实例ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobInstanceById(String jobInstanceId) {
        return dataflowJobInstanceMapper.deleteDataflowJobInstanceById(jobInstanceId);
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
                DataflowJobInstance dataflowJobInstance=dataflowJobInstanceMapper.selectDataflowJobInstanceById(idslist[i]);
                dataflowJobInstance.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobInstanceMapper.updateDataflowJobInstance(dataflowJobInstance);
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
     * 导入作业运行实例Excel数据
     *
     * @param dataflowJobInstanceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJobInstance>  importDataflowJobInstance(
    List<DataflowJobInstance> dataflowJobInstanceList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < dataflowJobInstanceList.size(); i++) {
            DataflowJobInstance dataflowJobInstance=dataflowJobInstanceList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJobInstance u = dataflowJobInstanceMapper.selectDataflowJobInstanceById(dataflowJobInstance.getJobInstanceId());
                if (StringUtils.isNull(u)) {
                        dataflowJobInstanceMapper.insertDataflowJobInstance(dataflowJobInstance);
                    dataflowJobInstance.setImportStatus("导入成功");
                        dataflowJobInstanceInfo.add(dataflowJobInstance);
                } else if (isUpdateSupport) {
                        dataflowJobInstanceMapper.updateDataflowJobInstance(dataflowJobInstance);
                    dataflowJobInstance.setImportStatus("更新成功");
                        dataflowJobInstanceInfo.add(dataflowJobInstance);
                } else {
                    dataflowJobInstance.setImportStatus("已存在当前数据");
                        dataflowJobInstanceInfo.add(dataflowJobInstance);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJobInstance.setImportStatus("数据格式有问题，请认证检查数据！");
                    dataflowJobInstanceInfo.add(dataflowJobInstance);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importDataflowJobInstance(dataflowJobInstanceList.subList(i + 1, dataflowJobInstanceList.size()), isUpdateSupport, operName);
                return dataflowJobInstanceInfo;
            }

        }
        return dataflowJobInstanceInfo;
    }
}
