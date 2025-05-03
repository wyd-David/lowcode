package com.yabushan.datasource.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.domain.EtlWorkCollect;
import com.yabushan.datasource.mapper.EtlWorkCollectMapper;
import com.yabushan.datasource.service.IEtlWorkCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作业开发Service业务层处理
 *
 * @author yabushan
 * @date 2024-01-21
 */
@Service
public class EtlWorkCollectServiceImpl implements IEtlWorkCollectService {
    private static final Logger log = LoggerFactory.getLogger(EtlWorkCollectServiceImpl.class);
    @Autowired
    private EtlWorkCollectMapper etlWorkCollectMapper;

    //存放导入所有的成功失败数据
    private static List<EtlWorkCollect> etlWorkCollectInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearetlWorkCollectInfo() {
        if (StringUtils.isNotNull(etlWorkCollectInfo) && etlWorkCollectInfo.size() > 0) {
            etlWorkCollectInfo.clear();
        }
    }

    /**
     * 查询作业开发
     *
     * @param collectId 作业开发ID
     * @return 作业开发
     */
    @Override
    public EtlWorkCollect selectEtlWorkCollectById(String collectId) {
        return etlWorkCollectMapper.selectEtlWorkCollectById(collectId);
    }

    /**
     * 查询作业开发列表
     *
     * @param etlWorkCollect 作业开发
     * @return 作业开发
     */
    @Override
    public List<EtlWorkCollect> selectEtlWorkCollectList(EtlWorkCollect etlWorkCollect) {
        return etlWorkCollectMapper.selectEtlWorkCollectList(etlWorkCollect);
    }

    /**
     * 新增作业开发
     *
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    @Override
    public int insertEtlWorkCollect(EtlWorkCollect etlWorkCollect) {
        etlWorkCollect.setCollectId(StringUtils.getUUID());
        etlWorkCollect.setCreateTime(DateUtils.getNowDate());
        etlWorkCollect.setCreateBy(SecurityUtils.getUsername());
        etlWorkCollect.setLastUpdateTime(new Date());
        etlWorkCollect.setUpdateTime(new Date());
        return etlWorkCollectMapper.insertEtlWorkCollect(etlWorkCollect);
    }

    /**
     * 批量新增作业开发
     *
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    @Override
    public int bathInsertEtlWorkCollect(List<EtlWorkCollect> etlWorkCollect) {
        return etlWorkCollectMapper.bathInsertEtlWorkCollect(etlWorkCollect);
    }


    /**
     * 修改作业开发
     *
     * @param etlWorkCollect 作业开发
     * @return 结果
     */
    @Override
    public int updateEtlWorkCollect(EtlWorkCollect etlWorkCollect) {
        return etlWorkCollectMapper.updateEtlWorkCollect(etlWorkCollect);
    }

    /**
     * 批量删除作业开发
     *
     * @param collectIds 需要删除的作业开发ID
     * @return 结果
     */
    @Override
    public int deleteEtlWorkCollectByIds(String[] collectIds) {
        return etlWorkCollectMapper.deleteEtlWorkCollectByIds(collectIds);
    }

    /**
     * 删除作业开发信息
     *
     * @param collectId 作业开发ID
     * @return 结果
     */
    @Override
    public int deleteEtlWorkCollectById(String collectId) {
        return etlWorkCollectMapper.deleteEtlWorkCollectById(collectId);
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
                EtlWorkCollect etlWorkCollect = etlWorkCollectMapper.selectEtlWorkCollectById(idslist[i]);
                etlWorkCollect.setDisableEnableState(disableEnableState);
                int isappinfo = etlWorkCollectMapper.updateEtlWorkCollect(etlWorkCollect);
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
     * 导入作业开发Excel数据
     *
     * @param etlWorkCollectList 数据列表
     * @param isUpdateSupport    是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<EtlWorkCollect> importEtlWorkCollect(
            List<EtlWorkCollect> etlWorkCollectList, Boolean
            isUpdateSupport,
            String operName) {
        for (int i = 0; i < etlWorkCollectList.size(); i++) {
            EtlWorkCollect etlWorkCollect = etlWorkCollectList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                EtlWorkCollect u = etlWorkCollectMapper.selectEtlWorkCollectById(etlWorkCollect.getCollectId());
                if (StringUtils.isNull(u)) {
                    etlWorkCollectMapper.insertEtlWorkCollect(etlWorkCollect);
                    etlWorkCollect.setImportStatus("导入成功");
                    etlWorkCollectInfo.add(etlWorkCollect);
                } else if (isUpdateSupport) {
                    etlWorkCollectMapper.updateEtlWorkCollect(etlWorkCollect);
                    etlWorkCollect.setImportStatus("更新成功");
                    etlWorkCollectInfo.add(etlWorkCollect);
                } else {
                    etlWorkCollect.setImportStatus("已存在当前数据");
                    etlWorkCollectInfo.add(etlWorkCollect);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                etlWorkCollect.setImportStatus("数据格式有问题，请认证检查数据！");
                etlWorkCollectInfo.add(etlWorkCollect);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importEtlWorkCollect(etlWorkCollectList.subList(i + 1, etlWorkCollectList.size()), isUpdateSupport, operName);
                return etlWorkCollectInfo;
            }

        }
        return etlWorkCollectInfo;
    }
}
