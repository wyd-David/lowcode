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
import com.yabushan.datasource.mapper.MetadataDatasourceTypeMapper;
import com.yabushan.datasource.domain.MetadataDatasourceType;
import com.yabushan.datasource.service.IMetadataDatasourceTypeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据源类型Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class MetadataDatasourceTypeServiceImpl implements IMetadataDatasourceTypeService {
    private static final Logger log = LoggerFactory.getLogger(MetadataDatasourceTypeServiceImpl.class);
    @Autowired
    private MetadataDatasourceTypeMapper metadataDatasourceTypeMapper;

    //存放导入所有的成功失败数据
    private static List<MetadataDatasourceType> metadataDatasourceTypeInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void clearmetadataDatasourceTypeInfo() {
        if (StringUtils.isNotNull(metadataDatasourceTypeInfo) && metadataDatasourceTypeInfo.size() > 0) {
            metadataDatasourceTypeInfo.clear();
        }
    }

    /**
     * 查询数据源类型
     *
     * @param typeCode 数据源类型ID
     * @return 数据源类型
     */
    @Override
    public MetadataDatasourceType selectMetadataDatasourceTypeById(String typeCode) {
        return metadataDatasourceTypeMapper.selectMetadataDatasourceTypeById(typeCode);
    }

    /**
     * 查询数据源类型列表
     *
     * @param metadataDatasourceType 数据源类型
     * @return 数据源类型
     */
    @Override
    public List<MetadataDatasourceType> selectMetadataDatasourceTypeList(MetadataDatasourceType metadataDatasourceType) {
        return metadataDatasourceTypeMapper.selectMetadataDatasourceTypeList(metadataDatasourceType);
    }

    /**
     * 新增数据源类型
     *
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    @Override
    public int insertMetadataDatasourceType(MetadataDatasourceType metadataDatasourceType) {
        return metadataDatasourceTypeMapper.insertMetadataDatasourceType(metadataDatasourceType);
    }

    /**
     * 批量新增数据源类型
     *
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    @Override
    public int bathInsertMetadataDatasourceType(List<MetadataDatasourceType> metadataDatasourceType) {
        return metadataDatasourceTypeMapper.bathInsertMetadataDatasourceType(metadataDatasourceType);
    }


    /**
     * 修改数据源类型
     *
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    @Override
    public int updateMetadataDatasourceType(MetadataDatasourceType metadataDatasourceType) {
        return metadataDatasourceTypeMapper.updateMetadataDatasourceType(metadataDatasourceType);
    }

    /**
     * 批量删除数据源类型
     *
     * @param typeCodes 需要删除的数据源类型ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceTypeByIds(String[] typeCodes) {
        return metadataDatasourceTypeMapper.deleteMetadataDatasourceTypeByIds(typeCodes);
    }

    /**
     * 删除数据源类型信息
     *
     * @param typeCode 数据源类型ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceTypeById(String typeCode) {
        return metadataDatasourceTypeMapper.deleteMetadataDatasourceTypeById(typeCode);
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
                MetadataDatasourceType metadataDatasourceType=metadataDatasourceTypeMapper.selectMetadataDatasourceTypeById(idslist[i]);
                metadataDatasourceType.setDisableEnableState(disableEnableState);
                int isappinfo = metadataDatasourceTypeMapper.updateMetadataDatasourceType(metadataDatasourceType);
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
     * 导入数据源类型Excel数据
     *
     * @param metadataDatasourceTypeList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<MetadataDatasourceType>  importMetadataDatasourceType(
            List<MetadataDatasourceType> metadataDatasourceTypeList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < metadataDatasourceTypeList.size(); i++) {
            MetadataDatasourceType metadataDatasourceType=metadataDatasourceTypeList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                MetadataDatasourceType u = metadataDatasourceTypeMapper.selectMetadataDatasourceTypeById(metadataDatasourceType.getTypeCode());
                if (StringUtils.isNull(u)) {
                    metadataDatasourceTypeMapper.insertMetadataDatasourceType(metadataDatasourceType);
                    metadataDatasourceType.setImportStatus("导入成功");
                    metadataDatasourceTypeInfo.add(metadataDatasourceType);
                } else if (isUpdateSupport) {
                    metadataDatasourceTypeMapper.updateMetadataDatasourceType(metadataDatasourceType);
                    metadataDatasourceType.setImportStatus("更新成功");
                    metadataDatasourceTypeInfo.add(metadataDatasourceType);
                } else {
                    metadataDatasourceType.setImportStatus("已存在当前数据");
                    metadataDatasourceTypeInfo.add(metadataDatasourceType);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                metadataDatasourceType.setImportStatus("数据格式有问题，请认证检查数据！");
                metadataDatasourceTypeInfo.add(metadataDatasourceType);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importMetadataDatasourceType(metadataDatasourceTypeList.subList(i + 1, metadataDatasourceTypeList.size()), isUpdateSupport, operName);
                return metadataDatasourceTypeInfo;
            }

        }
        return metadataDatasourceTypeInfo;
    }
}
