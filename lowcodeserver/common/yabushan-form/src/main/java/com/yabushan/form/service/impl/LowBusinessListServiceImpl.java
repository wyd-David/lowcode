package com.yabushan.form.service.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.http.HttpUtils;
import com.yabushan.form.vo.ListTitleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.form.mapper.LowBusinessListMapper;
import com.yabushan.form.domain.LowBusinessList;
import com.yabushan.form.service.ILowBusinessListService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务列Service业务层处理
 *
 * @author yabushan
 * @date 2022-10-15
 */
@Service
public class LowBusinessListServiceImpl implements ILowBusinessListService {
    private static final Logger log = LoggerFactory.getLogger(LowBusinessListServiceImpl.class);
    @Autowired
    private LowBusinessListMapper lowBusinessListMapper;

    //存放导入所有的成功失败数据
    private static List<LowBusinessList> lowBusinessListInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearlowBusinessListInfo() {
        if (StringUtils.isNotNull(lowBusinessListInfo) && lowBusinessListInfo.size() > 0) {
                lowBusinessListInfo.clear();
        }
    }

    /**
     * 查询业务列
     *
     * @param listId 业务列ID
     * @return 业务列
     */
    @Override
    public LowBusinessList selectLowBusinessListById(String listId) {
        LowBusinessList lowBusinessList = lowBusinessListMapper.selectLowBusinessListById(listId);
        String listMemo = lowBusinessList.getListMemo();
        List<ListTitleVo> list = JSON.parseArray(listMemo, ListTitleVo.class);
        lowBusinessList.setListTitleVo(list);
        String listMemoChild = lowBusinessList.getListMemoChild();
        if(listMemoChild!=null && StringUtils.isNotNull(listMemoChild) && StringUtils.isNotEmpty(listMemoChild)){
            List<ListTitleVo> listMemoChildVo = JSON.parseArray(listMemoChild, ListTitleVo.class);
            lowBusinessList.setListChildTitleVo(listMemoChildVo);
        }
        return lowBusinessList;
    }

    /**
     * 查询业务列列表
     *
     * @param lowBusinessList 业务列
     * @return 业务列
     */
    @Override
    public List<LowBusinessList> selectLowBusinessListList(LowBusinessList lowBusinessList) {
  /*      lowBusinessList.setCreatedBy(SecurityUtils.getUsername());*/
        return lowBusinessListMapper.selectLowBusinessListList(lowBusinessList);
    }
    @Override
    public List<LowBusinessList> selectLowBusinessListListPage(LowBusinessList lowBusinessList) {
        String username = SecurityUtils.getUsername();
        if(!username.equals("admin")){
            lowBusinessList.setCreatedBy(SecurityUtils.getUsername());
        }
        return lowBusinessListMapper.selectLowBusinessListList(lowBusinessList);
    }

    /**
     * 新增业务列
     *
     * @param lowBusinessList 业务列
     * @return 结果
     */
    @Override
    public int insertLowBusinessList(LowBusinessList lowBusinessList) {
        lowBusinessList.setListId(StringUtils.getUUID());
        lowBusinessList.setCreatedTime(new Date());
        lowBusinessList.setCreatedBy(SecurityUtils.getUsername());
        return  lowBusinessListMapper.insertLowBusinessList(lowBusinessList);
    }

    /**
     * 批量新增业务列
     *
     * @param lowBusinessList 业务列
     * @return 结果
     */
    @Override
    public int bathInsertLowBusinessList(List<LowBusinessList> lowBusinessList) {
        return lowBusinessListMapper.bathInsertLowBusinessList(lowBusinessList);
    }


    /**
     * 修改业务列
     *
     * @param lowBusinessList 业务列
     * @return 结果
     */
    @Override
    public int updateLowBusinessList(LowBusinessList lowBusinessList) {
        lowBusinessList.setUpdatedBy(SecurityUtils.getUsername());
        lowBusinessList.setUpdatedTime(new Date());
        return lowBusinessListMapper.updateLowBusinessList(lowBusinessList);
    }

    /**
     * 批量删除业务列
     *
     * @param listIds 需要删除的业务列ID
     * @return 结果
     */
    @Override
    public int deleteLowBusinessListByIds(String[] listIds) {
        return lowBusinessListMapper.deleteLowBusinessListByIds(listIds);
    }

    /**
     * 删除业务列信息
     *
     * @param listId 业务列ID
     * @return 结果
     */
    @Override
    public int deleteLowBusinessListById(String listId) {
        return lowBusinessListMapper.deleteLowBusinessListById(listId);
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
                LowBusinessList lowBusinessList=lowBusinessListMapper.selectLowBusinessListById(idslist[i]);
                lowBusinessList.setDisableEnableState(disableEnableState);
                int isappinfo = lowBusinessListMapper.updateLowBusinessList(lowBusinessList);
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
     * 导入业务列Excel数据
     *
     * @param lowBusinessListList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<LowBusinessList>  importLowBusinessList(
    List<LowBusinessList> lowBusinessListList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < lowBusinessListList.size(); i++) {
            LowBusinessList lowBusinessList=lowBusinessListList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                LowBusinessList u = lowBusinessListMapper.selectLowBusinessListById(lowBusinessList.getListId());
                if (StringUtils.isNull(u)) {
                        lowBusinessListMapper.insertLowBusinessList(lowBusinessList);
                    lowBusinessList.setImportStatus("导入成功");
                        lowBusinessListInfo.add(lowBusinessList);
                } else if (isUpdateSupport) {
                        lowBusinessListMapper.updateLowBusinessList(lowBusinessList);
                    lowBusinessList.setImportStatus("更新成功");
                        lowBusinessListInfo.add(lowBusinessList);
                } else {
                    lowBusinessList.setImportStatus("已存在当前数据");
                        lowBusinessListInfo.add(lowBusinessList);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                lowBusinessList.setImportStatus("数据格式有问题，请认证检查数据！");
                    lowBusinessListInfo.add(lowBusinessList);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importLowBusinessList(lowBusinessListList.subList(i + 1, lowBusinessListList.size()), isUpdateSupport, operName);
                return lowBusinessListInfo;
            }

        }
        return lowBusinessListInfo;
    }
}
