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
import com.yabushan.datasource.mapper.DataflowJobTaskInstanceBadDataMapper;
import com.yabushan.datasource.domain.DataflowJobTaskInstanceBadData;
import com.yabushan.datasource.service.IDataflowJobTaskInstanceBadDataService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作业脏数据Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataflowJobTaskInstanceBadDataServiceImpl implements IDataflowJobTaskInstanceBadDataService {
    private static final Logger log = LoggerFactory.getLogger(DataflowJobTaskInstanceBadDataServiceImpl.class);
    @Autowired
    private DataflowJobTaskInstanceBadDataMapper dataflowJobTaskInstanceBadDataMapper;

    //存放导入所有的成功失败数据
    private static List<DataflowJobTaskInstanceBadData> dataflowJobTaskInstanceBadDataInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void cleardataflowJobTaskInstanceBadDataInfo() {
        if (StringUtils.isNotNull(dataflowJobTaskInstanceBadDataInfo) && dataflowJobTaskInstanceBadDataInfo.size() > 0) {
                dataflowJobTaskInstanceBadDataInfo.clear();
        }
    }

    /**
     * 查询作业脏数据
     *
     * @param badDataId 作业脏数据ID
     * @return 作业脏数据
     */
    @Override
    public DataflowJobTaskInstanceBadData selectDataflowJobTaskInstanceBadDataById(String badDataId) {
        return dataflowJobTaskInstanceBadDataMapper.selectDataflowJobTaskInstanceBadDataById(badDataId);
    }

    /**
     * 查询作业脏数据列表
     *
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 作业脏数据
     */
    @Override
    public List<DataflowJobTaskInstanceBadData> selectDataflowJobTaskInstanceBadDataList(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData) {
        return dataflowJobTaskInstanceBadDataMapper.selectDataflowJobTaskInstanceBadDataList(dataflowJobTaskInstanceBadData);
    }

    /**
     * 新增作业脏数据
     *
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    @Override
    public int insertDataflowJobTaskInstanceBadData(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData) {
                                                                                                                                        return dataflowJobTaskInstanceBadDataMapper.insertDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadData);
    }

    /**
     * 批量新增作业脏数据
     *
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    @Override
    public int bathInsertDataflowJobTaskInstanceBadData(List<DataflowJobTaskInstanceBadData> dataflowJobTaskInstanceBadData) {
        return dataflowJobTaskInstanceBadDataMapper.bathInsertDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadData);
    }


    /**
     * 修改作业脏数据
     *
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    @Override
    public int updateDataflowJobTaskInstanceBadData(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData) {
                                                                                                                                        return dataflowJobTaskInstanceBadDataMapper.updateDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadData);
    }

    /**
     * 批量删除作业脏数据
     *
     * @param badDataIds 需要删除的作业脏数据ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskInstanceBadDataByIds(String[] badDataIds) {
        return dataflowJobTaskInstanceBadDataMapper.deleteDataflowJobTaskInstanceBadDataByIds(badDataIds);
    }

    /**
     * 删除作业脏数据信息
     *
     * @param badDataId 作业脏数据ID
     * @return 结果
     */
    @Override
    public int deleteDataflowJobTaskInstanceBadDataById(String badDataId) {
        return dataflowJobTaskInstanceBadDataMapper.deleteDataflowJobTaskInstanceBadDataById(badDataId);
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
                DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData=dataflowJobTaskInstanceBadDataMapper.selectDataflowJobTaskInstanceBadDataById(idslist[i]);
                dataflowJobTaskInstanceBadData.setDisableEnableState(disableEnableState);
                int isappinfo = dataflowJobTaskInstanceBadDataMapper.updateDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadData);
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
     * 导入作业脏数据Excel数据
     *
     * @param dataflowJobTaskInstanceBadDataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataflowJobTaskInstanceBadData>  importDataflowJobTaskInstanceBadData(
    List<DataflowJobTaskInstanceBadData> dataflowJobTaskInstanceBadDataList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < dataflowJobTaskInstanceBadDataList.size(); i++) {
            DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData=dataflowJobTaskInstanceBadDataList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataflowJobTaskInstanceBadData u = dataflowJobTaskInstanceBadDataMapper.selectDataflowJobTaskInstanceBadDataById(dataflowJobTaskInstanceBadData.getBadDataId());
                if (StringUtils.isNull(u)) {
                        dataflowJobTaskInstanceBadDataMapper.insertDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadData);
                    dataflowJobTaskInstanceBadData.setImportStatus("导入成功");
                        dataflowJobTaskInstanceBadDataInfo.add(dataflowJobTaskInstanceBadData);
                } else if (isUpdateSupport) {
                        dataflowJobTaskInstanceBadDataMapper.updateDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadData);
                    dataflowJobTaskInstanceBadData.setImportStatus("更新成功");
                        dataflowJobTaskInstanceBadDataInfo.add(dataflowJobTaskInstanceBadData);
                } else {
                    dataflowJobTaskInstanceBadData.setImportStatus("已存在当前数据");
                        dataflowJobTaskInstanceBadDataInfo.add(dataflowJobTaskInstanceBadData);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataflowJobTaskInstanceBadData.setImportStatus("数据格式有问题，请认证检查数据！");
                    dataflowJobTaskInstanceBadDataInfo.add(dataflowJobTaskInstanceBadData);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importDataflowJobTaskInstanceBadData(dataflowJobTaskInstanceBadDataList.subList(i + 1, dataflowJobTaskInstanceBadDataList.size()), isUpdateSupport, operName);
                return dataflowJobTaskInstanceBadDataInfo;
            }

        }
        return dataflowJobTaskInstanceBadDataInfo;
    }
}
