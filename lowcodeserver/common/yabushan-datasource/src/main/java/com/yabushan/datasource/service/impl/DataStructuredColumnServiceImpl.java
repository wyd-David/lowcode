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
import com.yabushan.datasource.mapper.DataStructuredColumnMapper;
import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.service.IDataStructuredColumnService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字段元数据Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class DataStructuredColumnServiceImpl implements IDataStructuredColumnService {
    private static final Logger log = LoggerFactory.getLogger(DataStructuredColumnServiceImpl.class);
    @Autowired
    private DataStructuredColumnMapper dataStructuredColumnMapper;

    //存放导入所有的成功失败数据
    private static List<DataStructuredColumn> dataStructuredColumnInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void cleardataStructuredColumnInfo() {
        if (StringUtils.isNotNull(dataStructuredColumnInfo) && dataStructuredColumnInfo.size() > 0) {
            dataStructuredColumnInfo.clear();
        }
    }

    /**
     * 查询字段元数据
     *
     * @param columnId 字段元数据ID
     * @return 字段元数据
     */
    @Override
    public DataStructuredColumn selectDataStructuredColumnById(String columnId) {
        return dataStructuredColumnMapper.selectDataStructuredColumnById(columnId);
    }

    /**
     * 查询字段元数据列表
     *
     * @param dataStructuredColumn 字段元数据
     * @return 字段元数据
     */
    @Override
    public List<DataStructuredColumn> selectDataStructuredColumnList(DataStructuredColumn dataStructuredColumn) {
        return dataStructuredColumnMapper.selectDataStructuredColumnList(dataStructuredColumn);
    }

    /**
     * 新增字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    @Override
    public int insertDataStructuredColumn(DataStructuredColumn dataStructuredColumn) {
        return dataStructuredColumnMapper.insertDataStructuredColumn(dataStructuredColumn);
    }

    /**
     * 批量新增字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    @Override
    public int bathInsertDataStructuredColumn(List<DataStructuredColumn> dataStructuredColumn) {
        return dataStructuredColumnMapper.bathInsertDataStructuredColumn(dataStructuredColumn);
    }


    /**
     * 修改字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    @Override
    public int updateDataStructuredColumn(DataStructuredColumn dataStructuredColumn) {
        return dataStructuredColumnMapper.updateDataStructuredColumn(dataStructuredColumn);
    }

    /**
     * 批量删除字段元数据
     *
     * @param columnIds 需要删除的字段元数据ID
     * @return 结果
     */
    @Override
    public int deleteDataStructuredColumnByIds(String[] columnIds) {
        return dataStructuredColumnMapper.deleteDataStructuredColumnByIds(columnIds);
    }

    /**
     * 删除字段元数据信息
     *
     * @param columnId 字段元数据ID
     * @return 结果
     */
    @Override
    public int deleteDataStructuredColumnById(String columnId) {
        return dataStructuredColumnMapper.deleteDataStructuredColumnById(columnId);
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
                DataStructuredColumn dataStructuredColumn=dataStructuredColumnMapper.selectDataStructuredColumnById(idslist[i]);
                dataStructuredColumn.setDisableEnableState(disableEnableState);
                int isappinfo = dataStructuredColumnMapper.updateDataStructuredColumn(dataStructuredColumn);
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
     * 导入字段元数据Excel数据
     *
     * @param dataStructuredColumnList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataStructuredColumn>  importDataStructuredColumn(
            List<DataStructuredColumn> dataStructuredColumnList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < dataStructuredColumnList.size(); i++) {
            DataStructuredColumn dataStructuredColumn=dataStructuredColumnList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataStructuredColumn u = dataStructuredColumnMapper.selectDataStructuredColumnById(dataStructuredColumn.getColumnId());
                if (StringUtils.isNull(u)) {
                    dataStructuredColumnMapper.insertDataStructuredColumn(dataStructuredColumn);
                    dataStructuredColumn.setImportStatus("导入成功");
                    dataStructuredColumnInfo.add(dataStructuredColumn);
                } else if (isUpdateSupport) {
                    dataStructuredColumnMapper.updateDataStructuredColumn(dataStructuredColumn);
                    dataStructuredColumn.setImportStatus("更新成功");
                    dataStructuredColumnInfo.add(dataStructuredColumn);
                } else {
                    dataStructuredColumn.setImportStatus("已存在当前数据");
                    dataStructuredColumnInfo.add(dataStructuredColumn);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataStructuredColumn.setImportStatus("数据格式有问题，请认证检查数据！");
                dataStructuredColumnInfo.add(dataStructuredColumn);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importDataStructuredColumn(dataStructuredColumnList.subList(i + 1, dataStructuredColumnList.size()), isUpdateSupport, operName);
                return dataStructuredColumnInfo;
            }

        }
        return dataStructuredColumnInfo;
    }
}
