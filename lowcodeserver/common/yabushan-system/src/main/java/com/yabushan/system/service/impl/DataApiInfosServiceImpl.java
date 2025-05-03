package com.yabushan.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.service.ISysDeptService;
import com.yabushan.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.DataApiInfosMapper;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据服务APIService业务层处理
 *
 * @author yabushan
 * @date 2022-11-11
 */
@Service
public class DataApiInfosServiceImpl implements IDataApiInfosService {
    private static final Logger log = LoggerFactory.getLogger(DataApiInfosServiceImpl.class);
    @Autowired
    private DataApiInfosMapper dataApiInfosMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDeptService deptService;



    //存放导入所有的成功失败数据
    private static List<DataApiInfos> dataApiInfosInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void cleardataApiInfosInfo() {
        if (StringUtils.isNotNull(dataApiInfosInfo) && dataApiInfosInfo.size() > 0) {
            dataApiInfosInfo.clear();
        }
    }

    /**
     * 查询数据服务API
     *
     * @param id 数据服务APIID
     * @return 数据服务API
     */
    @Override
    public DataApiInfos selectDataApiInfosById(Long id) {
        return dataApiInfosMapper.selectDataApiInfosById(id);
    }

    @Override
    public DataApiInfos selectDataApiInfosCode(String id) {
        return dataApiInfosMapper.selectDataApiInfosCode(id);
    }


    /**
     * 查询数据服务API列表
     *
     * @param dataApiInfos 数据服务API
     * @return 数据服务API
     */
    @Override
    public List<DataApiInfos> selectDataApiInfosList(DataApiInfos dataApiInfos) {
        String userName = SecurityUtils.getUsername();
        if(!"admin".equals(userName)){
            dataApiInfos.setCreateBy(userName);
        }
        return dataApiInfosMapper.selectDataApiInfosList(dataApiInfos);
    }

    /**
     * 新增数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    @Override
    public int insertDataApiInfos(DataApiInfos dataApiInfos) {

        dataApiInfos.setCreateTime(DateUtils.getNowDate());
        dataApiInfos.setCreateBy(SecurityUtils.getUsername());

        dataApiInfos.setResourceId(StringUtils.getUUID());
        dataApiInfos.setResourceCode(StringUtils.getUUID());

        return dataApiInfosMapper.insertDataApiInfos(dataApiInfos);
    }

    /**
     * 批量新增数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    @Override
    public int bathInsertDataApiInfos(List<DataApiInfos> dataApiInfos) {
        return dataApiInfosMapper.bathInsertDataApiInfos(dataApiInfos);
    }


    /**
     * 修改数据服务API
     *
     * @param dataApiInfos 数据服务API
     * @return 结果
     */
    @Override
    public int updateDataApiInfos(DataApiInfos dataApiInfos) {
        dataApiInfos.setUpdateTime(DateUtils.getNowDate());
        return dataApiInfosMapper.updateDataApiInfos(dataApiInfos);
    }

    /**
     * 批量删除数据服务API
     *
     * @param ids 需要删除的数据服务APIID
     * @return 结果
     */
    @Override
    public int deleteDataApiInfosByIds(Long[] ids) {
        return dataApiInfosMapper.deleteDataApiInfosByIds(ids);
    }

    /**
     * 删除数据服务API信息
     *
     * @param id 数据服务APIID
     * @return 结果
     */
    @Override
    public int deleteDataApiInfosById(Long id) {
        return dataApiInfosMapper.deleteDataApiInfosById(id);
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
                DataApiInfos dataApiInfos=dataApiInfosMapper.selectDataApiInfosById(Long.parseLong(idslist[i]));
                dataApiInfos.setDisableEnableState(disableEnableState);
                int isappinfo = dataApiInfosMapper.updateDataApiInfos(dataApiInfos);
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
     * 导入数据服务APIExcel数据
     *
     * @param dataApiInfosList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<DataApiInfos>  importDataApiInfos(
            List<DataApiInfos> dataApiInfosList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < dataApiInfosList.size(); i++) {
            DataApiInfos dataApiInfos=dataApiInfosList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                DataApiInfos u = dataApiInfosMapper.selectDataApiInfosById(dataApiInfos.getId());
                if (StringUtils.isNull(u)) {
                    dataApiInfosMapper.insertDataApiInfos(dataApiInfos);
                    dataApiInfos.setImportStatus("导入成功");
                    dataApiInfosInfo.add(dataApiInfos);
                } else if (isUpdateSupport) {
                    dataApiInfosMapper.updateDataApiInfos(dataApiInfos);
                    dataApiInfos.setImportStatus("更新成功");
                    dataApiInfosInfo.add(dataApiInfos);
                } else {
                    dataApiInfos.setImportStatus("已存在当前数据");
                    dataApiInfosInfo.add(dataApiInfos);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                dataApiInfos.setImportStatus("数据格式有问题，请认证检查数据！");
                dataApiInfosInfo.add(dataApiInfos);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importDataApiInfos(dataApiInfosList.subList(i + 1, dataApiInfosList.size()), isUpdateSupport, operName);
                return dataApiInfosInfo;
            }

        }
        return dataApiInfosInfo;
    }

    @Override
    public Long getCompanyIdByUserName(String userName){
        SysUser sysUser = sysUserService.selectUserByUserName(userName);
        SysDept sysDept1 = deptService.selectDeptById(sysUser.getDeptId());
        if(sysDept1.getAncestors()!=null && StringUtils.isNotEmpty(sysDept1.getAncestors())) {
            String[] split = sysDept1.getAncestors().split(",");
            Long s = Long.parseLong(split[2]);
            return s;
        }
        return null;
    }
}
