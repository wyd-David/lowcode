package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.DepartmentInfo;
import com.yabushan.system.mapper.DepartmentInfoMapper;
import com.yabushan.system.service.IDepartmentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门信息 Service业务层处理
 *
 * @author yabushan
 * @date 2021-10-21
 */
@Service
public class DepartmentInfoServiceImpl implements IDepartmentInfoService
{
    private static final Logger log = LoggerFactory.getLogger(DepartmentInfoServiceImpl.class);
    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;


    //存放导入所有的成功失败数据
    private static List<DepartmentInfo> departmentInfoInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void cleardepartmentInfoInfo() {
        if (StringUtils.isNotNull(departmentInfoInfo) && departmentInfoInfo.size() > 0) {
                departmentInfoInfo.clear();
        }
    }

    /**
     * 查询部门信息
     *
     * @param deptId 部门信息 ID
     * @return 部门信息
     */
    @Override
    public DepartmentInfo selectDepartmentInfoById(Integer deptId)
    {
        return departmentInfoMapper.selectDepartmentInfoById(deptId);
    }

    /**
     * 查询部门信息 列表
     *
     * @param departmentInfo 部门信息
     * @return 部门信息
     */
    @Override
    public List<DepartmentInfo> selectDepartmentInfoList(DepartmentInfo departmentInfo)
    {
        return departmentInfoMapper.selectDepartmentInfoList(departmentInfo);
    }

    /**
     * 新增部门信息
     *
     * @param departmentInfo 部门信息
     * @return 结果
     */
    @Override
    public int insertDepartmentInfo(DepartmentInfo departmentInfo)
    {
        departmentInfo.setCreateTime(DateUtils.getNowDate());
        return departmentInfoMapper.insertDepartmentInfo(departmentInfo);
    }

    /**
     * 批量新增部门信息
     *
     * @param departmentInfo 部门信息
     * @return 结果
     */
    @Override
    public int bathInsertDepartmentInfo(List<DepartmentInfo> departmentInfo)
    {
        return departmentInfoMapper.bathInsertDepartmentInfo(departmentInfo);
    }


    /**
     * 修改部门信息
     *
     * @param departmentInfo 部门信息
     * @return 结果
     */
    @Override
    public int updateDepartmentInfo(DepartmentInfo departmentInfo)
    {
        departmentInfo.setUpdateTime(DateUtils.getNowDate());
        return departmentInfoMapper.updateDepartmentInfo(departmentInfo);
    }

    /**
     * 批量删除部门信息
     *
     * @param deptIds 需要删除的部门信息 ID
     * @return 结果
     */
    @Override
    public int deleteDepartmentInfoByIds(Integer[] deptIds)
    {
        return departmentInfoMapper.deleteDepartmentInfoByIds(deptIds);
    }

    /**
     * 删除部门信息 信息
     *
     * @param deptId 部门信息 ID
     * @return 结果
     */
    @Override
    public int deleteDepartmentInfoById(Integer deptId)
    {
        return departmentInfoMapper.deleteDepartmentInfoById(deptId);
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
                DepartmentInfo departmentInfo=departmentInfoMapper.selectDepartmentInfoById(Integer.valueOf(ids));
                departmentInfo.setDisableEnableState(disableEnableState);
                int isappinfo = departmentInfoMapper.updateDepartmentInfo(departmentInfo);
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
     * 导入部门信息 Excel数据
     *
     * @param departmentInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DepartmentInfo>  importDepartmentInfo(List<DepartmentInfo> departmentInfoList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < departmentInfoList.size(); i++) {
            DepartmentInfo departmentInfo=departmentInfoList.get(i);
        try {
            // 验证是否存在这个用户
            DepartmentInfo u = departmentInfoMapper.selectDepartmentInfoById(departmentInfo.getDeptId());
            if (StringUtils.isNull(u)) {
                    departmentInfoMapper.insertDepartmentInfo(departmentInfo);
                    departmentInfo.setImportStatus("导入成功");
                    departmentInfoInfo.add(departmentInfo);
            } else if (isUpdateSupport) {
                    departmentInfoMapper.updateDepartmentInfo(departmentInfo);
                    departmentInfo.setImportStatus("更新成功");
                    departmentInfoInfo.add(departmentInfo);
            } else {
                    departmentInfo.setImportStatus("已存在当前数据");
                    departmentInfoInfo.add(departmentInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            departmentInfo.setImportStatus("数据格式有问题，请认证检查数据！");
            departmentInfoInfo.add(departmentInfo);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importDepartmentInfo(departmentInfoList.subList(i+1,departmentInfoList.size()), isUpdateSupport, operName);
            return departmentInfoInfo;
        }

    }
        return departmentInfoInfo;
    }
}
