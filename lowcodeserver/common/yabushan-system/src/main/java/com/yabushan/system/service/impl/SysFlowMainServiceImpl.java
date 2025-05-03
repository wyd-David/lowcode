package com.yabushan.system.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.system.mapper.SysFlowMainMapper;
import com.yabushan.system.domain.SysFlowMain;
import com.yabushan.system.service.ISysFlowMainService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程主表Service业务层处理
 *
 * @author CJH
 * @date 2021-09-15
 */
@Service
public class SysFlowMainServiceImpl implements ISysFlowMainService
{
    private static final Logger log = LoggerFactory.getLogger(SysFlowMainServiceImpl.class);
    @Autowired
    private SysFlowMainMapper sysFlowMainMapper;
    //存放导入所有的成功失败数据
    private static List<SysFlowMain> sysFlowMainInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsysFlowMainInfo() {
        if (StringUtils.isNotNull(sysFlowMainInfo) && sysFlowMainInfo.size() > 0) {
                sysFlowMainInfo.clear();
        }
    }

    /**
     * 查询流程主表
     *
     * @param id 流程主表ID
     * @return 流程主表
     */
    @Override
    public SysFlowMain selectSysFlowMainById(String id)
    {
        return sysFlowMainMapper.selectSysFlowMainById(id);
    }

    /**
     * 查询流程主表列表
     *
     * @param sysFlowMain 流程主表
     * @return 流程主表
     */
    @Override
    public List<SysFlowMain> selectSysFlowMainList(SysFlowMain sysFlowMain)
    {
        return sysFlowMainMapper.selectSysFlowMainList(sysFlowMain);
    }

    /**
     * 新增流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    @Override
    public int insertSysFlowMain(SysFlowMain sysFlowMain)
    {
        return sysFlowMainMapper.insertSysFlowMain(sysFlowMain);
    }

    /**
     * 批量新增流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    @Override
    public int bathInsertSysFlowMain(List<SysFlowMain> sysFlowMain)
    {
        return sysFlowMainMapper.bathInsertSysFlowMain(sysFlowMain);
    }


    /**
     * 修改流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    @Override
    public int updateSysFlowMain(SysFlowMain sysFlowMain)
    {
        return sysFlowMainMapper.updateSysFlowMain(sysFlowMain);
    }

    /**
     * 批量删除流程主表
     *
     * @param ids 需要删除的流程主表ID
     * @return 结果
     */
    @Override
    public int deleteSysFlowMainByIds(String[] ids)
    {
        return sysFlowMainMapper.deleteSysFlowMainByIds(ids);
    }

    /**
     * 删除流程主表信息
     *
     * @param id 流程主表ID
     * @return 结果
     */
    @Override
    public int deleteSysFlowMainById(String id)
    {
        return sysFlowMainMapper.deleteSysFlowMainById(id);
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
        String idslist[]=ids.split(",",-1);
        if(StringUtils.isNotNull(idslist)&&idslist.length>=1){
            for (int i = 0; i < idslist.length; i++) {
                SysFlowMain sysFlowMain=sysFlowMainMapper.selectSysFlowMainById(ids);
                sysFlowMain.setDisableEnableState(disableEnableState);
                int isappinfo = sysFlowMainMapper.updateSysFlowMain(sysFlowMain);
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

}
