package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.UumUserinfo;
import com.yabushan.system.mapper.UumUserinfoMapper;
import com.yabushan.system.service.IUumUserinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步用户信息Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-27
 */
@Service
public class UumUserinfoServiceImpl implements IUumUserinfoService
{
    private static final Logger log = LoggerFactory.getLogger(UumUserinfoServiceImpl.class);
    @Autowired
    private UumUserinfoMapper uumUserinfoMapper;

    //存放导入所有的成功失败数据
    private static List<UumUserinfo> uumUserinfoInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearuumUserinfoInfo() {
        if (StringUtils.isNotNull(uumUserinfoInfo) && uumUserinfoInfo.size() > 0) {
            uumUserinfoInfo.clear();
        }
    }

    /**
     * 查询同步用户信息
     *
     * @param employee 同步用户信息ID
     * @return 同步用户信息
     */
    @Override
    public UumUserinfo selectUumUserinfoById(String employee)
    {
        return uumUserinfoMapper.selectUumUserinfoById(employee);
    }

    /**
     * 查询同步用户信息列表
     *
     * @param uumUserinfo 同步用户信息
     * @return 同步用户信息
     */
    @Override
    public List<UumUserinfo> selectUumUserinfoList(UumUserinfo uumUserinfo)
    {
        return uumUserinfoMapper.selectUumUserinfoList(uumUserinfo);
    }

    /**
     * 新增同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    @Override
    public int insertUumUserinfo(UumUserinfo uumUserinfo)
    {
        return uumUserinfoMapper.insertUumUserinfo(uumUserinfo);
    }

    /**
     * 批量新增同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    @Override
    public int bathInsertUumUserinfo(List<UumUserinfo> uumUserinfo)
    {
        return uumUserinfoMapper.bathInsertUumUserinfo(uumUserinfo);
    }


    /**
     * 修改同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    @Override
    public int updateUumUserinfo(UumUserinfo uumUserinfo)
    {
        return uumUserinfoMapper.updateUumUserinfo(uumUserinfo);
    }

    /**
     * 批量删除同步用户信息
     *
     * @param employees 需要删除的同步用户信息ID
     * @return 结果
     */
    @Override
    public int deleteUumUserinfoByIds(String[] employees)
    {
        return uumUserinfoMapper.deleteUumUserinfoByIds(employees);
    }

    /**
     * 删除同步用户信息信息
     *
     * @param employee 同步用户信息ID
     * @return 结果
     */
    @Override
    public int deleteUumUserinfoById(String employee)
    {
        return uumUserinfoMapper.deleteUumUserinfoById(employee);
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
                UumUserinfo uumUserinfo=uumUserinfoMapper.selectUumUserinfoById(idslist[i]);
                uumUserinfo.setDisableEnableState(disableEnableState);
                int isappinfo = uumUserinfoMapper.updateUumUserinfo(uumUserinfo);
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
     * 导入同步用户信息Excel数据
     *
     * @param uumUserinfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<UumUserinfo>  importUumUserinfo(List<UumUserinfo> uumUserinfoList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < uumUserinfoList.size(); i++) {
            UumUserinfo uumUserinfo=uumUserinfoList.get(i);
            try {
                // 验证是否存在这个用户
                UumUserinfo u = uumUserinfoMapper.selectUumUserinfoById(uumUserinfo.getUserid());
                if (StringUtils.isNull(u)) {
                    uumUserinfoMapper.insertUumUserinfo(uumUserinfo);
                    uumUserinfo.setImportStatus("导入成功");
                    uumUserinfoInfo.add(uumUserinfo);
                } else if (isUpdateSupport) {
                    uumUserinfoMapper.updateUumUserinfo(uumUserinfo);
                    uumUserinfo.setImportStatus("更新成功");
                    uumUserinfoInfo.add(uumUserinfo);
                } else {
                    uumUserinfo.setImportStatus("已存在当前数据");
                    uumUserinfoInfo.add(uumUserinfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                uumUserinfo.setImportStatus("数据格式有问题，请认证检查数据！");
                uumUserinfoInfo.add(uumUserinfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importUumUserinfo(uumUserinfoList.subList(i+1,uumUserinfoList.size()), isUpdateSupport, operName);
                return uumUserinfoInfo;
            }

        }
        return uumUserinfoInfo;
    }

    @Override
    public UumUserinfo selectByUserId(String userId) {
        return uumUserinfoMapper.selectByUserId(userId);
    }

    @Override
    public List<UumUserinfo> getYjxtUumUserInfos(){
        return uumUserinfoMapper.getYjxtUumUserInfos();
    }
}
