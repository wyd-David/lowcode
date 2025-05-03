package com.yabushan.system.service.impl;

import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.bean.BeanUtils;
import com.yabushan.system.domain.ActXunchaProcinst;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.vo.ActXunchaProcinstVO;
import com.yabushan.system.mapper.ActXunchaProcinstMapper;
import com.yabushan.system.service.IActXunchaProcinstService;
import com.yabushan.system.service.ITodoInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的工单Service业务层处理
 *
 * @author yabushan
 * @date 2022-07-07
 */
@Service
public class ActXunchaProcinstServiceImpl implements IActXunchaProcinstService {
    private static final Logger log = LoggerFactory.getLogger(ActXunchaProcinstServiceImpl.class);
    @Resource
    private ActXunchaProcinstMapper actXunchaProcinstMapper;
    @Resource
    private ITodoInfoService todoInfoService;

    //存放导入所有的成功失败数据
    private static List<ActXunchaProcinst> actXunchaProcinstInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearactXunchaProcinstInfo() {
        if (StringUtils.isNotNull(actXunchaProcinstInfo) && actXunchaProcinstInfo.size() > 0) {
            actXunchaProcinstInfo.clear();
        }
    }

    /**
     * 查询我的工单
     *
     * @param procinstId 我的工单ID
     * @return 我的工单
     */
    @Override
    public ActXunchaProcinst selectActXunchaProcinstById(Long procinstId) {
        return actXunchaProcinstMapper.selectActXunchaProcinstById(procinstId);
    }

    /**
     * 查询我的工单列表
     *
     * @param actXunchaProcinst 我的工单
     * @return 我的工单
     */
    @Override
    public List<ActXunchaProcinst> selectActXunchaProcinstList(ActXunchaProcinst actXunchaProcinst) {
        return actXunchaProcinstMapper.selectActXunchaProcinstList(actXunchaProcinst);
    }

    /**
     * 新增我的工单
     *
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    @Override
    public int insertActXunchaProcinst(ActXunchaProcinst actXunchaProcinst) {
        setDefaultValue(actXunchaProcinst);
        return actXunchaProcinstMapper.insertActXunchaProcinst(actXunchaProcinst);
    }

    /**
     * 批量新增我的工单
     *
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    @Override
    public int bathInsertActXunchaProcinst(List<ActXunchaProcinst> actXunchaProcinst) {
        return actXunchaProcinstMapper.bathInsertActXunchaProcinst(actXunchaProcinst);
    }


    /**
     * 修改我的工单
     *
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    @Override
    public int updateActXunchaProcinst(ActXunchaProcinst actXunchaProcinst) {
        return actXunchaProcinstMapper.updateActXunchaProcinst(actXunchaProcinst);
    }

    /**
     * 批量删除我的工单
     *
     * @param procinstIds 需要删除的我的工单ID
     * @return 结果
     */
    @Override
    public int deleteActXunchaProcinstByIds(Long[] procinstIds) {
        return actXunchaProcinstMapper.deleteActXunchaProcinstByIds(procinstIds);
    }

    /**
     * 删除我的工单信息
     *
     * @param procinstId 我的工单ID
     * @return 结果
     */
    @Override
    public int deleteActXunchaProcinstById(Long procinstId) {
        ActXunchaProcinst actXunchaProcinst = new ActXunchaProcinst();
        actXunchaProcinst.setProcinstId(procinstId);
        actXunchaProcinst.setDelFlag(1);
        return actXunchaProcinstMapper.updateActXunchaProcinst(actXunchaProcinst);
    }
    /**
     * 通过流水号和类型删除我的工单信息
     *
     * @param businessKey 我的工单ID
     * @return 结果
     */
    @Override
    public int deleteActXunchaProcinstByBusinessKey(String  businessKey){
        return actXunchaProcinstMapper.deleteActXunchaProcinstByBusinessKey(businessKey);
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
                // idslist[i] 返回的是一个字符串，如果实体id是其他类型，需要转换，如是int类型就修改成Integer.parseInt(idslist[i])
                ActXunchaProcinst actXunchaProcinst = actXunchaProcinstMapper.selectActXunchaProcinstById(Long.valueOf(idslist[i]));
                actXunchaProcinst.setDisableEnableState(disableEnableState);
                int isappinfo = actXunchaProcinstMapper.updateActXunchaProcinst(actXunchaProcinst);
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
     * 导入我的工单Excel数据
     *
     * @param actXunchaProcinstList 数据列表
     * @param isUpdateSupport       是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<ActXunchaProcinst> importActXunchaProcinst(
            List<ActXunchaProcinst> actXunchaProcinstList,
            Boolean isUpdateSupport,
            String operName) {
        for (int i = 0; i < actXunchaProcinstList.size(); i++) {
            ActXunchaProcinst actXunchaProcinst = actXunchaProcinstList.get(i);
            try {
                // 验证是否存在这个用户
                // getLogId() 换成获取实体 id 的方法
                ActXunchaProcinst u = actXunchaProcinstMapper.selectActXunchaProcinstById(actXunchaProcinst.getProcinstId());
                if (StringUtils.isNull(u)) {
                    actXunchaProcinstMapper.insertActXunchaProcinst(actXunchaProcinst);
                    actXunchaProcinst.setImportStatus("导入成功");
                    actXunchaProcinstInfo.add(actXunchaProcinst);
                } else if (isUpdateSupport) {
                    actXunchaProcinstMapper.updateActXunchaProcinst(actXunchaProcinst);
                    actXunchaProcinst.setImportStatus("更新成功");
                    actXunchaProcinstInfo.add(actXunchaProcinst);
                } else {
                    actXunchaProcinst.setImportStatus("已存在当前数据");
                    actXunchaProcinstInfo.add(actXunchaProcinst);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                actXunchaProcinst.setImportStatus("数据格式有问题，请认证检查数据！");
                actXunchaProcinstInfo.add(actXunchaProcinst);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importActXunchaProcinst(actXunchaProcinstList.subList(i + 1, actXunchaProcinstList.size()), isUpdateSupport, operName);
                return actXunchaProcinstInfo;
            }

        }
        return actXunchaProcinstInfo;
    }

    @Override
    public List<ActXunchaProcinstVO> myWorkList(ActXunchaProcinst procinst) {
        String username = SecurityUtils.getUsername(); //当前登录人
        procinst.setCreatedBy(username);
        List<ActXunchaProcinstVO> procinstVOList = actXunchaProcinstMapper.myWorkList(procinst);
        for (ActXunchaProcinstVO actXunchaProcinstVO : procinstVOList) {
            if(StringUtils.isEmpty(actXunchaProcinstVO.getStatus())){
                actXunchaProcinstVO.setStatus("已结束");
            }
            if(null == actXunchaProcinstVO.getCountNum() || actXunchaProcinstVO.getCountNum() == 1){
                actXunchaProcinstVO.setDelete(true);
            }
        }
        return procinstVOList;
    }

    @Override
    public List<ActXunchaProcinst> myWorkListNew(ActXunchaProcinst actXunchaProcinst) {
        String username = SecurityUtils.getUsername(); //当前登录人
        actXunchaProcinst.setCreatedBy(username);
        actXunchaProcinst.setDelFlag(0);
        // 查询我的工单列表
        List<ActXunchaProcinst> actXunchaProcinstList = actXunchaProcinstMapper.selectActXunchaProcinstList(actXunchaProcinst);
        for (ActXunchaProcinst xunchaProcinst : actXunchaProcinstList) {
            BeanUtils.copyProperties(xunchaProcinst, xunchaProcinst);
            TodoInfo queryTodoInfo = new TodoInfo();
            queryTodoInfo.setBusinessKey(xunchaProcinst.getBusinessKey());
            List<TodoInfo> todoInfoList = todoInfoService.selectTodoInfoList(queryTodoInfo);
            xunchaProcinst.setDelete(false);
            if (todoInfoList.size() > 0) {
                if(todoInfoList.size() == 1 && username.equals(xunchaProcinst.getCreatedBy())){
                    xunchaProcinst.setDelete(true);
                }
                TodoInfo todoInfo = todoInfoList.get(0);
                xunchaProcinst.setTodoUrl(todoInfo.getTodoUrl());
                xunchaProcinst.setLastUpdateTime(todoInfo.getCreateTime());
                String todoType = todoInfo.getTodoType();
                xunchaProcinst.setTodoType(todoType);
                if("1".equals(todoType)){
                    xunchaProcinst.setStatus("已结束");
                    xunchaProcinst.setTodoHandlerName(null);
                }else{
                    xunchaProcinst.setTodoHandlerName(todoInfo.getTodoHandlerName());
                    xunchaProcinst.setStatus(todoInfo.getTodoTpLink());
                }
            }else{
                xunchaProcinst.setDelete(true);
            }
        }

        return actXunchaProcinstList;
    }

    private void setDefaultValue(ActXunchaProcinst actXunchaProcinst) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (actXunchaProcinst.getProcinstId() == null) {
            actXunchaProcinst.setCreatedBy(user.getUserName());
            actXunchaProcinst.setCreatedByName(user.getNickName());
            actXunchaProcinst.setCreatedTime(new Date());
            actXunchaProcinst.setDelFlag(0);

        }
        if(actXunchaProcinst.getStatus()==null){
            actXunchaProcinst.setStatus("草稿");
        }
        actXunchaProcinst.setUpdatedBy(user.getUserName());
        actXunchaProcinst.setUpdatedByName(user.getNickName());
        actXunchaProcinst.setUpdatedTime(new Date());
    }

}
