package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.WarnSmsModel;
import com.yabushan.system.mapper.WarnSmsModelMapper;
import com.yabushan.system.service.IWarnSmsModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信模型Service业务层处理
 *
 * @author yabushan
 * @date 2022-05-10
 */
@Service
public class WarnSmsModelServiceImpl implements IWarnSmsModelService {
    private static final Logger log = LoggerFactory.getLogger(WarnSmsModelServiceImpl.class);
    @Autowired
    private WarnSmsModelMapper warnSmsModelMapper;

    //存放导入所有的成功失败数据
    private static List<WarnSmsModel> warnSmsModelInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearwarnSmsModelInfo() {
        if (StringUtils.isNotNull(warnSmsModelInfo) && warnSmsModelInfo.size() > 0) {
            warnSmsModelInfo.clear();
        }
    }

    /**
     * 查询短信模型
     *
     * @param id 短信模型ID
     * @return 短信模型
     */
    @Override
    public WarnSmsModel selectWarnSmsModelById(String id) {
        return warnSmsModelMapper.selectWarnSmsModelById(id);
    }

    /**
     * 查询短信模型列表
     *
     * @param warnSmsModel 短信模型
     * @return 短信模型
     */
    @Override
    public List<WarnSmsModel> selectWarnSmsModelList(WarnSmsModel warnSmsModel) {
        return warnSmsModelMapper.selectWarnSmsModelList(warnSmsModel);
    }

    /**
     * 新增短信模型
     *
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    @Override
    public int insertWarnSmsModel(WarnSmsModel warnSmsModel) {
        warnSmsModel.setCreateTime(DateUtils.getNowDate());
        return warnSmsModelMapper.insertWarnSmsModel(warnSmsModel);
    }

    /**
     * 批量新增短信模型
     *
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    @Override
    public int bathInsertWarnSmsModel(List<WarnSmsModel> warnSmsModel) {
        return warnSmsModelMapper.bathInsertWarnSmsModel(warnSmsModel);
    }


    /**
     * 修改短信模型
     *
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    @Override
    public int updateWarnSmsModel(WarnSmsModel warnSmsModel) {
        return warnSmsModelMapper.updateWarnSmsModel(warnSmsModel);
    }

    /**
     * 批量删除短信模型
     *
     * @param ids 需要删除的短信模型ID
     * @return 结果
     */
    @Override
    public int deleteWarnSmsModelByIds(String[] ids) {
        return warnSmsModelMapper.deleteWarnSmsModelByIds(ids);
    }

    /**
     * 删除短信模型信息
     *
     * @param id 短信模型ID
     * @return 结果
     */
    @Override
    public int deleteWarnSmsModelById(String id) {
        return warnSmsModelMapper.deleteWarnSmsModelById(id);
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     *
     * @param ids
     * @param disableEnableState
     * @return
     */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[] = ids.split(",", -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                WarnSmsModel warnSmsModel = warnSmsModelMapper.selectWarnSmsModelById(idslist[i]);
                warnSmsModel.setDisableEnableState(disableEnableState);
                int isappinfo = warnSmsModelMapper.updateWarnSmsModel(warnSmsModel);
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
     * 导入短信模型Excel数据
     *
     * @param warnSmsModelList 数据列表
     * @param isUpdateSupport  是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<WarnSmsModel> importWarnSmsModel(List<WarnSmsModel> warnSmsModelList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i < warnSmsModelList.size(); i++) {
            WarnSmsModel warnSmsModel = warnSmsModelList.get(i);
            try {
                // 验证是否存在这个用户
                WarnSmsModel u = warnSmsModelMapper.selectWarnSmsModelById(warnSmsModel.getId());
                if (StringUtils.isNull(u)) {
                    warnSmsModelMapper.insertWarnSmsModel(warnSmsModel);
                    warnSmsModel.setImportStatus("导入成功");
                    warnSmsModelInfo.add(warnSmsModel);
                } else if (isUpdateSupport) {
                    warnSmsModelMapper.updateWarnSmsModel(warnSmsModel);
                    warnSmsModel.setImportStatus("更新成功");
                    warnSmsModelInfo.add(warnSmsModel);
                } else {
                    warnSmsModel.setImportStatus("已存在当前数据");
                    warnSmsModelInfo.add(warnSmsModel);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                warnSmsModel.setImportStatus("数据格式有问题，请认证检查数据！");
                warnSmsModelInfo.add(warnSmsModel);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importWarnSmsModel(warnSmsModelList.subList(i + 1, warnSmsModelList.size()), isUpdateSupport, operName);
                return warnSmsModelInfo;
            }

        }
        return warnSmsModelInfo;
    }
}
