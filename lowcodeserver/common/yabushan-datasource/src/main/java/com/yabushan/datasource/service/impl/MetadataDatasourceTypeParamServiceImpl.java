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
import com.yabushan.datasource.mapper.MetadataDatasourceTypeParamMapper;
import com.yabushan.datasource.domain.MetadataDatasourceTypeParam;
import com.yabushan.datasource.service.IMetadataDatasourceTypeParamService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据源类型参数Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class MetadataDatasourceTypeParamServiceImpl implements IMetadataDatasourceTypeParamService {
    private static final Logger log = LoggerFactory.getLogger(MetadataDatasourceTypeParamServiceImpl.class);
    @Autowired
    private MetadataDatasourceTypeParamMapper metadataDatasourceTypeParamMapper;

    //存放导入所有的成功失败数据
    private static List<MetadataDatasourceTypeParam> metadataDatasourceTypeParamInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void clearmetadataDatasourceTypeParamInfo() {
        if (StringUtils.isNotNull(metadataDatasourceTypeParamInfo) && metadataDatasourceTypeParamInfo.size() > 0) {
            metadataDatasourceTypeParamInfo.clear();
        }
    }

    /**
     * 查询数据源类型参数
     *
     * @param paramId 数据源类型参数ID
     * @return 数据源类型参数
     */
    @Override
    public MetadataDatasourceTypeParam selectMetadataDatasourceTypeParamById(String paramId) {
        return metadataDatasourceTypeParamMapper.selectMetadataDatasourceTypeParamById(paramId);
    }

    /**
     * 查询数据源类型参数列表
     *
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 数据源类型参数
     */
    @Override
    public List<MetadataDatasourceTypeParam> selectMetadataDatasourceTypeParamList(MetadataDatasourceTypeParam metadataDatasourceTypeParam) {
        return metadataDatasourceTypeParamMapper.selectMetadataDatasourceTypeParamList(metadataDatasourceTypeParam);
    }

    /**
     * 新增数据源类型参数
     *
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 结果
     */
    @Override
    public int insertMetadataDatasourceTypeParam(MetadataDatasourceTypeParam metadataDatasourceTypeParam) {
        return metadataDatasourceTypeParamMapper.insertMetadataDatasourceTypeParam(metadataDatasourceTypeParam);
    }

    /**
     * 批量新增数据源类型参数
     *
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 结果
     */
    @Override
    public int bathInsertMetadataDatasourceTypeParam(List<MetadataDatasourceTypeParam> metadataDatasourceTypeParam) {
        return metadataDatasourceTypeParamMapper.bathInsertMetadataDatasourceTypeParam(metadataDatasourceTypeParam);
    }


    /**
     * 修改数据源类型参数
     *
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 结果
     */
    @Override
    public int updateMetadataDatasourceTypeParam(MetadataDatasourceTypeParam metadataDatasourceTypeParam) {
        return metadataDatasourceTypeParamMapper.updateMetadataDatasourceTypeParam(metadataDatasourceTypeParam);
    }

    /**
     * 批量删除数据源类型参数
     *
     * @param paramIds 需要删除的数据源类型参数ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceTypeParamByIds(String[] paramIds) {
        return metadataDatasourceTypeParamMapper.deleteMetadataDatasourceTypeParamByIds(paramIds);
    }

    /**
     * 删除数据源类型参数信息
     *
     * @param paramId 数据源类型参数ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceTypeParamById(String paramId) {
        return metadataDatasourceTypeParamMapper.deleteMetadataDatasourceTypeParamById(paramId);
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
                MetadataDatasourceTypeParam metadataDatasourceTypeParam=metadataDatasourceTypeParamMapper.selectMetadataDatasourceTypeParamById(idslist[i]);
                metadataDatasourceTypeParam.setDisableEnableState(disableEnableState);
                int isappinfo = metadataDatasourceTypeParamMapper.updateMetadataDatasourceTypeParam(metadataDatasourceTypeParam);
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
     * 导入数据源类型参数Excel数据
     *
     * @param metadataDatasourceTypeParamList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<MetadataDatasourceTypeParam>  importMetadataDatasourceTypeParam(
            List<MetadataDatasourceTypeParam> metadataDatasourceTypeParamList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < metadataDatasourceTypeParamList.size(); i++) {
            MetadataDatasourceTypeParam metadataDatasourceTypeParam=metadataDatasourceTypeParamList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                MetadataDatasourceTypeParam u = metadataDatasourceTypeParamMapper.selectMetadataDatasourceTypeParamById(metadataDatasourceTypeParam.getParamId());
                if (StringUtils.isNull(u)) {
                    metadataDatasourceTypeParamMapper.insertMetadataDatasourceTypeParam(metadataDatasourceTypeParam);
                    metadataDatasourceTypeParam.setImportStatus("导入成功");
                    metadataDatasourceTypeParamInfo.add(metadataDatasourceTypeParam);
                } else if (isUpdateSupport) {
                    metadataDatasourceTypeParamMapper.updateMetadataDatasourceTypeParam(metadataDatasourceTypeParam);
                    metadataDatasourceTypeParam.setImportStatus("更新成功");
                    metadataDatasourceTypeParamInfo.add(metadataDatasourceTypeParam);
                } else {
                    metadataDatasourceTypeParam.setImportStatus("已存在当前数据");
                    metadataDatasourceTypeParamInfo.add(metadataDatasourceTypeParam);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                metadataDatasourceTypeParam.setImportStatus("数据格式有问题，请认证检查数据！");
                metadataDatasourceTypeParamInfo.add(metadataDatasourceTypeParam);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importMetadataDatasourceTypeParam(metadataDatasourceTypeParamList.subList(i + 1, metadataDatasourceTypeParamList.size()), isUpdateSupport, operName);
                return metadataDatasourceTypeParamInfo;
            }

        }
        return metadataDatasourceTypeParamInfo;
    }
}
