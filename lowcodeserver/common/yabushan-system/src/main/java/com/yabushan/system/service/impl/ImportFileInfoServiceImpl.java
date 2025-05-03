package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.ImportFileInfo;
import com.yabushan.system.mapper.ImportFileInfoMapper;
import com.yabushan.system.service.IImportFileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传记录Service业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
@Service
public class ImportFileInfoServiceImpl implements IImportFileInfoService
{
    private static final Logger log = LoggerFactory.getLogger(ImportFileInfoServiceImpl.class);
    @Autowired
    private ImportFileInfoMapper importFileInfoMapper;
    //存放导入所有的成功失败数据
    private static List<ImportFileInfo> importFileInfoInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearimportFileInfoInfo() {
        if (StringUtils.isNotNull(importFileInfoInfo) && importFileInfoInfo.size() > 0) {
                importFileInfoInfo.clear();
        }
    }

    /**
     * 查询文件上传记录
     *
     * @param id 文件上传记录ID
     * @return 文件上传记录
     */
    @Override
    public ImportFileInfo selectImportFileInfoById(Long id)
    {
        return importFileInfoMapper.selectImportFileInfoById(id);
    }

    /**
     * 查询文件上传记录列表
     *
     * @param importFileInfo 文件上传记录
     * @return 文件上传记录
     */
    @Override
    public List<ImportFileInfo> selectImportFileInfoList(ImportFileInfo importFileInfo)
    {
        return importFileInfoMapper.selectImportFileInfoList(importFileInfo);
    }

    /**
     * 新增文件上传记录
     *
     * @param importFileInfo 文件上传记录
     * @return 结果
     */
    @Override
    public int insertImportFileInfo(ImportFileInfo importFileInfo)
    {
        importFileInfo.setCreateTime(DateUtils.getNowDate());
        return importFileInfoMapper.insertImportFileInfo(importFileInfo);
    }

    /**
     * 修改文件上传记录
     *
     * @param importFileInfo 文件上传记录
     * @return 结果
     */
    @Override
    public int updateImportFileInfo(ImportFileInfo importFileInfo)
    {
        return importFileInfoMapper.updateImportFileInfo(importFileInfo);
    }

    /**
     * 批量删除文件上传记录
     *
     * @param ids 需要删除的文件上传记录ID
     * @return 结果
     */
    @Override
    public int deleteImportFileInfoByIds(Long[] ids)
    {
        return importFileInfoMapper.deleteImportFileInfoByIds(ids);
    }

    /**
     * 删除文件上传记录信息
     *
     * @param id 文件上传记录ID
     * @return 结果
     */
    @Override
    public int deleteImportFileInfoById(Long id)
    {
        return importFileInfoMapper.deleteImportFileInfoById(id);
    }
    @Override
    public void updateByIds(List<String> ids, String disableEnableState) {
        if(StringUtils.isNotNull(ids)&&ids.size()>=1){
            for (int i = 0; i < ids.size(); i++) {
                ImportFileInfo importFileInfo=new ImportFileInfo();
                importFileInfo.setId(Long.valueOf(ids.get(i)));
                importFileInfo.setDisableEnableState(disableEnableState);
                importFileInfoMapper.updateImportFileInfo(importFileInfo);
            }
        }
    }
    /**
     * 导入文件上传记录Excel数据
     *
     * @param importFileInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<ImportFileInfo>  importImportFileInfo(List<ImportFileInfo> importFileInfoList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < importFileInfoList.size(); i++) {
            ImportFileInfo importFileInfo=importFileInfoList.get(i);
        try {
            // 验证是否存在这个用户
            ImportFileInfo u = importFileInfoMapper.selectImportFileInfoById(importFileInfo.getId());
            if (StringUtils.isNull(u)) {
                    importFileInfoMapper.insertImportFileInfo(importFileInfo);
                    importFileInfo.setImportStatus("导入成功");
                    importFileInfoInfo.add(importFileInfo);
            } else if (isUpdateSupport) {
                    importFileInfoMapper.updateImportFileInfo(importFileInfo);
                    importFileInfo.setImportStatus("更新成功");
                    importFileInfoInfo.add(importFileInfo);
            } else {
                    importFileInfo.setImportStatus("已存在当前数据");
                    importFileInfoInfo.add(importFileInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            importFileInfo.setImportStatus("数据格式有问题，请认证检查数据！");
            importFileInfoInfo.add(importFileInfo);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importImportFileInfo(importFileInfoList.subList(i+1,importFileInfoList.size()), isUpdateSupport, operName);
            return importFileInfoInfo;
        }

    }
        return importFileInfoInfo;
    }
}
