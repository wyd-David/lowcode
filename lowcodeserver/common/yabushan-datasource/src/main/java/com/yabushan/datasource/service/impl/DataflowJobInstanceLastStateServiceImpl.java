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
import com.yabushan.datasource.mapper.DataflowJobInstanceLastStateMapper;
import com.yabushan.datasource.domain.DataflowJobInstanceLastState;
import com.yabushan.datasource.service.IDataflowJobInstanceLastStateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作业运行的最后状态Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobInstanceLastStateServiceImpl implements IDataflowJobInstanceLastStateService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobInstanceLastStateServiceImpl.class);
    @Autowired
    private DataflowJobInstanceLastStateMapper dataflowJobInstanceLastStateMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJobInstanceLastState> dataflowJobInstanceLastStateInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void cleardataflowJobInstanceLastStateInfo() {
        if (StringUtils.isNotNull(dataflowJobInstanceLastStateInfo) && dataflowJobInstanceLastStateInfo.size() > 0) {
                dataflowJobInstanceLastStateInfo.clear();
        }
    }

    /**
     * 查询作业运行的最后状态
     *
     * @param jobId 作业运行的最后状态ID
     * @return 作业运行的最后状态
     */
    @Override
    public DataflowJobInstanceLastState selectDataflowJobInstanceLastStateById(String jobId) {
        return dataflowJobInstanceLastStateMapper.selectDataflowJobInstanceLastStateById(jobId);
    }

    /**
     * 查询作业运行的最后状态列表
     *
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 作业运行的最后状态
     */
    @Override
    public List<DataflowJobInstanceLastState> selectDataflowJobInstanceLastStateList(DataflowJobInstanceLastState dataflowJobInstanceLastState) {
        return dataflowJobInstanceLastStateMapper.selectDataflowJobInstanceLastStateList(dataflowJobInstanceLastState);
    }

    /**
     * 新增作业运行的最后状态
     *
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    @Override
    public int insertDataflowJobInstanceLastState(DataflowJobInstanceLastState dataflowJobInstanceLastState) {
                                                                                                                    return dataflowJobInstanceLastStateMapper.insertDataflowJobInstanceLastState(dataflowJobInstanceLastState);
    }

    /**
     * 批量新增作业运行的最后状态
     *
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJobInstanceLastState(List<DataflowJobInstanceLastState> dataflowJobInstanceLastState) {
        return dataflowJobInstanceLastStateMapper.bathInsertDataflowJobInstanceLastState(dataflowJobInstanceLastState);
    }


    /**
     * 修改作业运行的最后状态
     *
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    @Override
    public int updateDataflowJobInstanceLastState(DataflowJobInstanceLastState dataflowJobInstanceLastState) {
                                                                                                                    return dataflowJobInstanceLastStateMapper.updateDataflowJobInstanceLastState(dataflowJobInstanceLastState);
    }

    /**
     * 批量删除作业运行的最后状态
     *
     * @param jobIds 需要删除的作业运行的最后状态ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobInstanceLastStateByIds(String[] jobIds) {
        return dataflowJobInstanceLastStateMapper.deleteDataflowJobInstanceLastStateByIds(jobIds);
    }

    /**
     * 删除作业运行的最后状态信息
     *
     * @param jobId 作业运行的最后状态ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobInstanceLastStateById(String jobId) {
        return dataflowJobInstanceLastStateMapper.deleteDataflowJobInstanceLastStateById(jobId);
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
                DataflowJobInstanceLastState dataflowJobInstanceLastState=dataflowJobInstanceLastStateMapper.selectDataflowJobInstanceLastStateById(idslist[i]);
                dataflowJobInstanceLastState.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobInstanceLastStateMapper.updateDataflowJobInstanceLastState(dataflowJobInstanceLastState);
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
     * 导入作业运行的最后状态Excel数据
     *
     * @param dataflowJobInstanceLastStateList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJobInstanceLastState>  importDataflowJobInstanceLastState(
    List<DataflowJobInstanceLastState> dataflowJobInstanceLastStateList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < dataflowJobInstanceLastStateList.size(); i++) {
            DataflowJobInstanceLastState dataflowJobInstanceLastState=dataflowJobInstanceLastStateList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJobInstanceLastState u = dataflowJobInstanceLastStateMapper.selectDataflowJobInstanceLastStateById(dataflowJobInstanceLastState.getJobId());
                if (StringUtils.isNull(u)) {
                        dataflowJobInstanceLastStateMapper.insertDataflowJobInstanceLastState(dataflowJobInstanceLastState);
                    dataflowJobInstanceLastState.setImportStatus("导入成功");
                        dataflowJobInstanceLastStateInfo.add(dataflowJobInstanceLastState);
                } else if (isUpdateSupport) {
                        dataflowJobInstanceLastStateMapper.updateDataflowJobInstanceLastState(dataflowJobInstanceLastState);
                    dataflowJobInstanceLastState.setImportStatus("更新成功");
                        dataflowJobInstanceLastStateInfo.add(dataflowJobInstanceLastState);
                } else {
                    dataflowJobInstanceLastState.setImportStatus("已存在当前数据");
                        dataflowJobInstanceLastStateInfo.add(dataflowJobInstanceLastState);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJobInstanceLastState.setImportStatus("数据格式有问题，请认证检查数据！");
                    dataflowJobInstanceLastStateInfo.add(dataflowJobInstanceLastState);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importDataflowJobInstanceLastState(dataflowJobInstanceLastStateList.subList(i + 1, dataflowJobInstanceLastStateList.size()), isUpdateSupport, operName);
                return dataflowJobInstanceLastStateInfo;
            }

        }
        return dataflowJobInstanceLastStateInfo;
    }
}
