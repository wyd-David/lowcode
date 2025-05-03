package com.yabushan.capacity.service.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.capacity.mapper.NgsTextVoidInfoMapper;
import com.yabushan.capacity.domain.NgsTextVoidInfo;
import com.yabushan.capacity.service.INgsTextVoidInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文字转声音Service业务层处理
 *
 * @author yabushan
 * @date 2024-02-20
 */
@Service
public class NgsTextVoidInfoServiceImpl implements INgsTextVoidInfoService {
    private static final Logger log = LoggerFactory.getLogger(NgsTextVoidInfoServiceImpl.class);
    @Autowired
    private NgsTextVoidInfoMapper ngsTextVoidInfoMapper;

    //存放导入所有的成功失败数据
    private static List<NgsTextVoidInfo> ngsTextVoidInfoInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearngsTextVoidInfoInfo() {
        if (StringUtils.isNotNull(ngsTextVoidInfoInfo) && ngsTextVoidInfoInfo.size() > 0) {
            ngsTextVoidInfoInfo.clear();
        }
    }

    /**
     * 查询文字转声音
     *
     * @param textId 文字转声音ID
     * @return 文字转声音
     */
    @Override
    public NgsTextVoidInfo selectNgsTextVoidInfoById(String textId) {
        return ngsTextVoidInfoMapper.selectNgsTextVoidInfoById(textId);
    }

    /**
     * 查询文字转声音列表
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 文字转声音
     */
    @Override
    public List<NgsTextVoidInfo> selectNgsTextVoidInfoList(NgsTextVoidInfo ngsTextVoidInfo) {
        return ngsTextVoidInfoMapper.selectNgsTextVoidInfoList(ngsTextVoidInfo);
    }

    /**
     * 新增文字转声音
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    @Override
    public int insertNgsTextVoidInfo(NgsTextVoidInfo ngsTextVoidInfo) {
        ngsTextVoidInfo.setTextId(StringUtils.getUUID());
        ngsTextVoidInfo.setCreatedTime(new Date());
        ngsTextVoidInfo.setCreatedBy(SecurityUtils.getUsername());
        return ngsTextVoidInfoMapper.insertNgsTextVoidInfo(ngsTextVoidInfo);
    }

    /**
     * 批量新增文字转声音
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    @Override
    public int bathInsertNgsTextVoidInfo(List<NgsTextVoidInfo> ngsTextVoidInfo) {
        return ngsTextVoidInfoMapper.bathInsertNgsTextVoidInfo(ngsTextVoidInfo);
    }


    /**
     * 修改文字转声音
     *
     * @param ngsTextVoidInfo 文字转声音
     * @return 结果
     */
    @Override
    public int updateNgsTextVoidInfo(NgsTextVoidInfo ngsTextVoidInfo) {
        NgsTextVoidInfo ngsTextVoidInfo1 = ngsTextVoidInfoMapper.selectNgsTextVoidInfoById(ngsTextVoidInfo.getTextId());
        ngsTextVoidInfo1.setTextTitle(ngsTextVoidInfo.getTextTitle());
        ngsTextVoidInfo1.setTextValue(ngsTextVoidInfo.getTextValue());
        ngsTextVoidInfo1.setUpdatedTime(new Date());
        ngsTextVoidInfo1.setUpdatedBy(SecurityUtils.getUsername());
        return ngsTextVoidInfoMapper.updateNgsTextVoidInfo(ngsTextVoidInfo1);
    }

    /**
     * 批量删除文字转声音
     *
     * @param textIds 需要删除的文字转声音ID
     * @return 结果
     */
    @Override
    public int deleteNgsTextVoidInfoByIds(String[] textIds) {
        return ngsTextVoidInfoMapper.deleteNgsTextVoidInfoByIds(textIds);
    }

    /**
     * 删除文字转声音信息
     *
     * @param textId 文字转声音ID
     * @return 结果
     */
    @Override
    public int deleteNgsTextVoidInfoById(String textId) {
        return ngsTextVoidInfoMapper.deleteNgsTextVoidInfoById(textId);
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
                // todo idslist[i] 返回的是一个字符串，如果实体id是其他类型，需要转换，如是int类型就修改成Integer.parseInt(idslist[i])
                NgsTextVoidInfo ngsTextVoidInfo = ngsTextVoidInfoMapper.selectNgsTextVoidInfoById(idslist[i]);
                ngsTextVoidInfo.setDisableEnableState(disableEnableState);
                int isappinfo = ngsTextVoidInfoMapper.updateNgsTextVoidInfo(ngsTextVoidInfo);
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
     * 导入文字转声音Excel数据
     *
     * @param ngsTextVoidInfoList 数据列表
     * @param isUpdateSupport     是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<NgsTextVoidInfo> importNgsTextVoidInfo(
            List<NgsTextVoidInfo> ngsTextVoidInfoList, Boolean
            isUpdateSupport,
            String operName) {
        for (int i = 0; i < ngsTextVoidInfoList.size(); i++) {
            NgsTextVoidInfo ngsTextVoidInfo = ngsTextVoidInfoList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                NgsTextVoidInfo u = ngsTextVoidInfoMapper.selectNgsTextVoidInfoById(ngsTextVoidInfo.getTextId());
                if (StringUtils.isNull(u)) {
                    ngsTextVoidInfoMapper.insertNgsTextVoidInfo(ngsTextVoidInfo);
                    ngsTextVoidInfo.setImportStatus("导入成功");
                    ngsTextVoidInfoInfo.add(ngsTextVoidInfo);
                } else if (isUpdateSupport) {
                    ngsTextVoidInfoMapper.updateNgsTextVoidInfo(ngsTextVoidInfo);
                    ngsTextVoidInfo.setImportStatus("更新成功");
                    ngsTextVoidInfoInfo.add(ngsTextVoidInfo);
                } else {
                    ngsTextVoidInfo.setImportStatus("已存在当前数据");
                    ngsTextVoidInfoInfo.add(ngsTextVoidInfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                ngsTextVoidInfo.setImportStatus("数据格式有问题，请认证检查数据！");
                ngsTextVoidInfoInfo.add(ngsTextVoidInfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importNgsTextVoidInfo(ngsTextVoidInfoList.subList(i + 1, ngsTextVoidInfoList.size()), isUpdateSupport, operName);
                return ngsTextVoidInfoInfo;
            }

        }
        return ngsTextVoidInfoInfo;
    }
}
