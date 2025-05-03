package com.yabushan.form.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.form.mapper.LowFormFiledInfoMapper;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.service.ILowFormFiledInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础表单字段信息Service业务层处理
 *
 * @author yabushan
 * @date 2022-10-10
 */
@Service
public class LowFormFiledInfoServiceImpl implements ILowFormFiledInfoService {
    private static final Logger log = LoggerFactory.getLogger(LowFormFiledInfoServiceImpl.class);
    @Autowired
    private LowFormFiledInfoMapper lowFormFiledInfoMapper;

    //存放导入所有的成功失败数据
    private static List<LowFormFiledInfo> lowFormFiledInfoInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearlowFormFiledInfoInfo() {
        if (StringUtils.isNotNull(lowFormFiledInfoInfo) && lowFormFiledInfoInfo.size() > 0) {
                lowFormFiledInfoInfo.clear();
        }
    }

    /**
     * 查询基础表单字段信息
     *
     * @param filedId 基础表单字段信息ID
     * @return 基础表单字段信息
     */
    @Override
    public LowFormFiledInfo selectLowFormFiledInfoById(String filedId) {
        return lowFormFiledInfoMapper.selectLowFormFiledInfoById(filedId);
    }

    /**
     * 查询基础表单字段信息列表
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 基础表单字段信息
     */
    @Override
    public List<LowFormFiledInfo> selectLowFormFiledInfoList(LowFormFiledInfo lowFormFiledInfo) {
      /*  lowFormFiledInfo.setCreatedBy(SecurityUtils.getUsername());*/

        String userName = SecurityUtils.getUsername();
        if(!"admin".equals(userName)){
            lowFormFiledInfo.setCreateBy(userName);
        }
        return lowFormFiledInfoMapper.selectLowFormFiledInfoList(lowFormFiledInfo);
    }

    /**
     * 新增基础表单字段信息
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    @Override
    public int insertLowFormFiledInfo(LowFormFiledInfo lowFormFiledInfo) {
                                                                                                                                                                                                                                                                                                        return lowFormFiledInfoMapper.insertLowFormFiledInfo(lowFormFiledInfo);
    }

    /**
     * 批量新增基础表单字段信息
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    @Override
    public int bathInsertLowFormFiledInfo(List<LowFormFiledInfo> lowFormFiledInfo) {
        return lowFormFiledInfoMapper.bathInsertLowFormFiledInfo(lowFormFiledInfo);
    }


    /**
     * 修改基础表单字段信息
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    @Override
    public int updateLowFormFiledInfo(LowFormFiledInfo lowFormFiledInfo) {
                                                                                                                                                                                                                                                                                                        return lowFormFiledInfoMapper.updateLowFormFiledInfo(lowFormFiledInfo);
    }

    /**
     * 批量删除基础表单字段信息
     *
     * @param filedIds 需要删除的基础表单字段信息ID
     * @return 结果
     */
    @Override
    public int deleteLowFormFiledInfoByIds(String[] filedIds) {
        return lowFormFiledInfoMapper.deleteLowFormFiledInfoByIds(filedIds);
    }

    /**
     * 删除基础表单字段信息信息
     *
     * @param filedId 基础表单字段信息ID
     * @return 结果
     */
    @Override
    public int deleteLowFormFiledInfoById(String filedId) {
        return lowFormFiledInfoMapper.deleteLowFormFiledInfoById(filedId);
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
                LowFormFiledInfo lowFormFiledInfo=lowFormFiledInfoMapper.selectLowFormFiledInfoById(idslist[i]);
                lowFormFiledInfo.setDisableEnableState(disableEnableState);
                int isappinfo = lowFormFiledInfoMapper.updateLowFormFiledInfo(lowFormFiledInfo);
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
     * 导入基础表单字段信息Excel数据
     *
     * @param lowFormFiledInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<LowFormFiledInfo>  importLowFormFiledInfo(
    List<LowFormFiledInfo> lowFormFiledInfoList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < lowFormFiledInfoList.size(); i++) {
            LowFormFiledInfo lowFormFiledInfo=lowFormFiledInfoList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                LowFormFiledInfo u = lowFormFiledInfoMapper.selectLowFormFiledInfoById(lowFormFiledInfo.getFiledId());
                if (StringUtils.isNull(u)) {
                        lowFormFiledInfoMapper.insertLowFormFiledInfo(lowFormFiledInfo);
                    lowFormFiledInfo.setImportStatus("导入成功");
                        lowFormFiledInfoInfo.add(lowFormFiledInfo);
                } else if (isUpdateSupport) {
                        lowFormFiledInfoMapper.updateLowFormFiledInfo(lowFormFiledInfo);
                    lowFormFiledInfo.setImportStatus("更新成功");
                        lowFormFiledInfoInfo.add(lowFormFiledInfo);
                } else {
                    lowFormFiledInfo.setImportStatus("已存在当前数据");
                        lowFormFiledInfoInfo.add(lowFormFiledInfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                lowFormFiledInfo.setImportStatus("数据格式有问题，请认证检查数据！");
                    lowFormFiledInfoInfo.add(lowFormFiledInfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importLowFormFiledInfo(lowFormFiledInfoList.subList(i + 1, lowFormFiledInfoList.size()), isUpdateSupport, operName);
                return lowFormFiledInfoInfo;
            }

        }
        return lowFormFiledInfoInfo;
    }
    @Override
    public  List<LowFormFiledInfo>  selectLowFormFiledInfoByFormUuid(String formUuid){
        return   lowFormFiledInfoMapper.selectLowFormFiledInfoByFormUuid(formUuid);
    }
}
