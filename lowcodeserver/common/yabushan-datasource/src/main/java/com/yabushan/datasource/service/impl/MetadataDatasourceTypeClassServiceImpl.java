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
import com.yabushan.datasource.mapper.MetadataDatasourceTypeClassMapper;
import com.yabushan.datasource.domain.MetadataDatasourceTypeClass;
import com.yabushan.datasource.service.IMetadataDatasourceTypeClassService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据源类型分类Service业务层处理
 *
 * @author yabushan
 * @date 2023-11-19
 */
@Service
public class MetadataDatasourceTypeClassServiceImpl implements IMetadataDatasourceTypeClassService {
    private static final Logger log = LoggerFactory.getLogger(MetadataDatasourceTypeClassServiceImpl.class);
    @Autowired
    private MetadataDatasourceTypeClassMapper metadataDatasourceTypeClassMapper;

    //存放导入所有的成功失败数据
    private static List<MetadataDatasourceTypeClass> metadataDatasourceTypeClassInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void clearmetadataDatasourceTypeClassInfo() {
        if (StringUtils.isNotNull(metadataDatasourceTypeClassInfo) && metadataDatasourceTypeClassInfo.size() > 0) {
                metadataDatasourceTypeClassInfo.clear();
        }
    }

    /**
     * 查询数据源类型分类
     *
     * @param classCode 数据源类型分类ID
     * @return 数据源类型分类
     */
    @Override
    public MetadataDatasourceTypeClass selectMetadataDatasourceTypeClassById(String classCode) {
        return metadataDatasourceTypeClassMapper.selectMetadataDatasourceTypeClassById(classCode);
    }

    /**
     * 查询数据源类型分类列表
     *
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 数据源类型分类
     */
    @Override
    public List<MetadataDatasourceTypeClass> selectMetadataDatasourceTypeClassList(MetadataDatasourceTypeClass metadataDatasourceTypeClass) {
        return metadataDatasourceTypeClassMapper.selectMetadataDatasourceTypeClassList(metadataDatasourceTypeClass);
    }

    /**
     * 新增数据源类型分类
     *
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    @Override
    public int insertMetadataDatasourceTypeClass(MetadataDatasourceTypeClass metadataDatasourceTypeClass) {
                                                                            return metadataDatasourceTypeClassMapper.insertMetadataDatasourceTypeClass(metadataDatasourceTypeClass);
    }

    /**
     * 批量新增数据源类型分类
     *
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    @Override
    public int bathInsertMetadataDatasourceTypeClass(List<MetadataDatasourceTypeClass> metadataDatasourceTypeClass) {
        return metadataDatasourceTypeClassMapper.bathInsertMetadataDatasourceTypeClass(metadataDatasourceTypeClass);
    }


    /**
     * 修改数据源类型分类
     *
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    @Override
    public int updateMetadataDatasourceTypeClass(MetadataDatasourceTypeClass metadataDatasourceTypeClass) {
                                                                            return metadataDatasourceTypeClassMapper.updateMetadataDatasourceTypeClass(metadataDatasourceTypeClass);
    }

    /**
     * 批量删除数据源类型分类
     *
     * @param classCodes 需要删除的数据源类型分类ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceTypeClassByIds(String[] classCodes) {
        return metadataDatasourceTypeClassMapper.deleteMetadataDatasourceTypeClassByIds(classCodes);
    }

    /**
     * 删除数据源类型分类信息
     *
     * @param classCode 数据源类型分类ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceTypeClassById(String classCode) {
        return metadataDatasourceTypeClassMapper.deleteMetadataDatasourceTypeClassById(classCode);
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
                MetadataDatasourceTypeClass metadataDatasourceTypeClass=metadataDatasourceTypeClassMapper.selectMetadataDatasourceTypeClassById(idslist[i]);
                metadataDatasourceTypeClass.setDisableEnableState(disableEnableState);
                int isappinfo = metadataDatasourceTypeClassMapper.updateMetadataDatasourceTypeClass(metadataDatasourceTypeClass);
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
     * 导入数据源类型分类Excel数据
     *
     * @param metadataDatasourceTypeClassList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<MetadataDatasourceTypeClass>  importMetadataDatasourceTypeClass(
    List<MetadataDatasourceTypeClass> metadataDatasourceTypeClassList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < metadataDatasourceTypeClassList.size(); i++) {
            MetadataDatasourceTypeClass metadataDatasourceTypeClass=metadataDatasourceTypeClassList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                MetadataDatasourceTypeClass u = metadataDatasourceTypeClassMapper.selectMetadataDatasourceTypeClassById(metadataDatasourceTypeClass.getClassCode());
                if (StringUtils.isNull(u)) {
                        metadataDatasourceTypeClassMapper.insertMetadataDatasourceTypeClass(metadataDatasourceTypeClass);
                    metadataDatasourceTypeClass.setImportStatus("导入成功");
                        metadataDatasourceTypeClassInfo.add(metadataDatasourceTypeClass);
                } else if (isUpdateSupport) {
                        metadataDatasourceTypeClassMapper.updateMetadataDatasourceTypeClass(metadataDatasourceTypeClass);
                    metadataDatasourceTypeClass.setImportStatus("更新成功");
                        metadataDatasourceTypeClassInfo.add(metadataDatasourceTypeClass);
                } else {
                    metadataDatasourceTypeClass.setImportStatus("已存在当前数据");
                        metadataDatasourceTypeClassInfo.add(metadataDatasourceTypeClass);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                metadataDatasourceTypeClass.setImportStatus("数据格式有问题，请认证检查数据！");
                    metadataDatasourceTypeClassInfo.add(metadataDatasourceTypeClass);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importMetadataDatasourceTypeClass(metadataDatasourceTypeClassList.subList(i + 1, metadataDatasourceTypeClassList.size()), isUpdateSupport, operName);
                return metadataDatasourceTypeClassInfo;
            }

        }
        return metadataDatasourceTypeClassInfo;
    }
}
