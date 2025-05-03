package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SysSyncDep;
import com.yabushan.system.mapper.SysSyncDepMapper;
import com.yabushan.system.service.ISysSyncDepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统部门与对接部门中间Service业务层处理
 *
 * @author yabushan
 * @date 2021-09-02
 */
@Service
public class SysSyncDepServiceImpl implements ISysSyncDepService
{
    private static final Logger log = LoggerFactory.getLogger(SysSyncDepServiceImpl.class);
    @Autowired
    private SysSyncDepMapper sysSyncDepMapper;
    //存放导入所有的成功失败数据
    private static List<SysSyncDep> sysSyncDepInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsysSyncDepInfo() {
        if (StringUtils.isNotNull(sysSyncDepInfo) && sysSyncDepInfo.size() > 0) {
                sysSyncDepInfo.clear();
        }
    }

    /**
     * 查询系统部门与对接部门中间
     *
     * @param id 系统部门与对接部门中间ID
     * @return 系统部门与对接部门中间
     */
    @Override
    public SysSyncDep selectSysSyncDepById(Long id)
    {
        return sysSyncDepMapper.selectSysSyncDepById(id);
    }

    /**
     * 查询系统部门与对接部门中间列表
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 系统部门与对接部门中间
     */
    @Override
    public List<SysSyncDep> selectSysSyncDepList(SysSyncDep sysSyncDep)
    {
        return sysSyncDepMapper.selectSysSyncDepList(sysSyncDep);
    }

    /**
     * 新增系统部门与对接部门中间
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 结果
     */
    @Override
    public int insertSysSyncDep(SysSyncDep sysSyncDep)
    {
        return sysSyncDepMapper.insertSysSyncDep(sysSyncDep);
    }

    /**
     * 修改系统部门与对接部门中间
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 结果
     */
    @Override
    public int updateSysSyncDep(SysSyncDep sysSyncDep)
    {
        return sysSyncDepMapper.updateSysSyncDep(sysSyncDep);
    }

    /**
     * 批量删除系统部门与对接部门中间
     *
     * @param ids 需要删除的系统部门与对接部门中间ID
     * @return 结果
     */
    @Override
    public int deleteSysSyncDepByIds(Long[] ids)
    {
        return sysSyncDepMapper.deleteSysSyncDepByIds(ids);
    }

    /**
     * 删除系统部门与对接部门中间信息
     *
     * @param id 系统部门与对接部门中间ID
     * @return 结果
     */
    @Override
    public int deleteSysSyncDepById(Long id)
    {
        return sysSyncDepMapper.deleteSysSyncDepById(id);
    }
}
