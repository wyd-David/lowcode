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
import com.yabushan.datasource.mapper.DataflowJobTaskInstanceLastStateMapper;
import com.yabushan.datasource.domain.DataflowJobTaskInstanceLastState;
import com.yabushan.datasource.service.IDataflowJobTaskInstanceLastStateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 节点运行的最后状态Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobTaskInstanceLastStateServiceImpl implements IDataflowJobTaskInstanceLastStateService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobTaskInstanceLastStateServiceImpl.class);
    @Autowired
    private DataflowJobTaskInstanceLastStateMapper dataflowJobTaskInstanceLastStateMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJobTaskInstanceLastState> dataflowJobTaskInstanceLastStateInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void cleardataflowJobTaskInstanceLastStateInfo() {
        if (StringUtils.isNotNull(dataflowJobTaskInstanceLastStateInfo) && dataflowJobTaskInstanceLastStateInfo.size() > 0) {
                dataflowJobTaskInstanceLastStateInfo.clear();
        }
    }

    /**
     * 查询节点运行的最后状态
     *
     * @param jobTaskId 节点运行的最后状态ID
     * @return 节点运行的最后状态
     */
    @Override
    public DataflowJobTaskInstanceLastState selectDataflowJobTaskInstanceLastStateById(String jobTaskId) {
        return dataflowJobTaskInstanceLastStateMapper.selectDataflowJobTaskInstanceLastStateById(jobTaskId);
    }

    /**
     * 查询节点运行的最后状态列表
     *
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 节点运行的最后状态
     */
    @Override
    public List<DataflowJobTaskInstanceLastState> selectDataflowJobTaskInstanceLastStateList(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState) {
        return dataflowJobTaskInstanceLastStateMapper.selectDataflowJobTaskInstanceLastStateList(dataflowJobTaskInstanceLastState);
    }

    /**
     * 新增节点运行的最后状态
     *
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    @Override
    public int insertDataflowJobTaskInstanceLastState(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState) {
                                                                                                                    return dataflowJobTaskInstanceLastStateMapper.insertDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastState);
    }

    /**
     * 批量新增节点运行的最后状态
     *
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJobTaskInstanceLastState(List<DataflowJobTaskInstanceLastState> dataflowJobTaskInstanceLastState) {
        return dataflowJobTaskInstanceLastStateMapper.bathInsertDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastState);
    }


    /**
     * 修改节点运行的最后状态
     *
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    @Override
    public int updateDataflowJobTaskInstanceLastState(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState) {
                                                                                                                    return dataflowJobTaskInstanceLastStateMapper.updateDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastState);
    }

    /**
     * 批量删除节点运行的最后状态
     *
     * @param jobTaskIds 需要删除的节点运行的最后状态ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskInstanceLastStateByIds(String[] jobTaskIds) {
        return dataflowJobTaskInstanceLastStateMapper.deleteDataflowJobTaskInstanceLastStateByIds(jobTaskIds);
    }

    /**
     * 删除节点运行的最后状态信息
     *
     * @param jobTaskId 节点运行的最后状态ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskInstanceLastStateById(String jobTaskId) {
        return dataflowJobTaskInstanceLastStateMapper.deleteDataflowJobTaskInstanceLastStateById(jobTaskId);
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
                DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState=dataflowJobTaskInstanceLastStateMapper.selectDataflowJobTaskInstanceLastStateById(idslist[i]);
                dataflowJobTaskInstanceLastState.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobTaskInstanceLastStateMapper.updateDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastState);
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
     * 导入节点运行的最后状态Excel数据
     *
     * @param dataflowJobTaskInstanceLastStateList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJobTaskInstanceLastState>  importDataflowJobTaskInstanceLastState(
    List<DataflowJobTaskInstanceLastState> dataflowJobTaskInstanceLastStateList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < dataflowJobTaskInstanceLastStateList.size(); i++) {
            DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState=dataflowJobTaskInstanceLastStateList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJobTaskInstanceLastState u = dataflowJobTaskInstanceLastStateMapper.selectDataflowJobTaskInstanceLastStateById(dataflowJobTaskInstanceLastState.getJobTaskId());
                if (StringUtils.isNull(u)) {
                        dataflowJobTaskInstanceLastStateMapper.insertDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastState);
                    dataflowJobTaskInstanceLastState.setImportStatus("导入成功");
                        dataflowJobTaskInstanceLastStateInfo.add(dataflowJobTaskInstanceLastState);
                } else if (isUpdateSupport) {
                        dataflowJobTaskInstanceLastStateMapper.updateDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastState);
                    dataflowJobTaskInstanceLastState.setImportStatus("更新成功");
                        dataflowJobTaskInstanceLastStateInfo.add(dataflowJobTaskInstanceLastState);
                } else {
                    dataflowJobTaskInstanceLastState.setImportStatus("已存在当前数据");
                        dataflowJobTaskInstanceLastStateInfo.add(dataflowJobTaskInstanceLastState);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJobTaskInstanceLastState.setImportStatus("数据格式有问题，请认证检查数据！");
                    dataflowJobTaskInstanceLastStateInfo.add(dataflowJobTaskInstanceLastState);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importDataflowJobTaskInstanceLastState(dataflowJobTaskInstanceLastStateList.subList(i + 1, dataflowJobTaskInstanceLastStateList.size()), isUpdateSupport, operName);
                return dataflowJobTaskInstanceLastStateInfo;
            }

        }
        return dataflowJobTaskInstanceLastStateInfo;
    }
}
