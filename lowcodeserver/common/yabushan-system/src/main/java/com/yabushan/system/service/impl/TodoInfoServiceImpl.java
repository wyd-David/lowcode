package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.vo.ConstantTodo;
import com.yabushan.system.domain.vo.TodoInfoVo;
import com.yabushan.system.domain.vo.TodoVo;
import com.yabushan.system.mapper.TodoInfoMapper;
import com.yabushan.system.service.ISysConfigService;
import com.yabushan.system.service.ITodoInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 待办待阅Service业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-02
 */
@Service
public class TodoInfoServiceImpl implements ITodoInfoService {
    private static final Logger log = LoggerFactory.getLogger(TodoInfoServiceImpl.class);
    @Autowired
    private TodoInfoMapper todoInfoMapper;
    //存放导入所有的成功失败数据
    private static List<TodoInfo> todoInfoInfo = new ArrayList<>();
    @Autowired
    private ISysConfigService iSysConfigService;


    /**
     * 清空返回导入数据的集合
     */
    public void cleartodoInfoInfo() {
        if (StringUtils.isNotNull(todoInfoInfo) && todoInfoInfo.size() > 0) {
            todoInfoInfo.clear();
        }
    }

    /**
     * 查询待办待阅
     *
     * @param todoId 待办待阅ID
     * @return 待办待阅
     */
    @Override
    public TodoInfo selectTodoInfoById(String todoId) {
        return todoInfoMapper.selectTodoInfoById(todoId);
    }

    @Override
    public TodoInfoVo selectTodoInfoByTaskId(String taskId) {
        return todoInfoMapper.selectTodoInfoByTaskId(taskId);
    }

    /**
     * 查询待办待阅列表
     *
     * @param todoInfo 待办待阅
     * @return 待办待阅
     */
    @Override
    public List<TodoInfo> selectTodoInfoList(TodoInfo todoInfo) {

        return todoInfoMapper.selectTodoInfoList(todoInfo);
    }
    /**
     * 我的申请，小程序待办我的申请
     */
    @Override
    public List<TodoInfoVo> selectMyTodoInfoList(TodoInfoVo todoInfo) {
        return todoInfoMapper.selectMyTodoInfoList(todoInfo);
    }

    @Override
    public List<TodoInfo> selectTodoInfoListPC(TodoInfo todoInfo) {
        //调用系统参数来判断查询对应模块的信息
        String todo_push_applyId = iSysConfigService.selectConfigByKey("todo_Push_applyId");
        if (!todo_push_applyId.split(",", -1)[0].contains("ALL")) {
            todoInfo.setApplyIdList(todo_push_applyId.split(",", -1));
        }

        return todoInfoMapper.selectTodoInfoList(todoInfo);
    }

    @Override
    public Map<String, Integer> selectTodoInfoListPCCount(TodoInfo todoInfo) {
        Map<String, Integer> map = new HashMap<>();
        //调用系统参数来判断查询对应模块的信息
        String todo_push_applyId = iSysConfigService.selectConfigByKey("todo_Push_applyId");
        if (!todo_push_applyId.split(",", -1)[0].contains("ALL")) {
            todoInfo.setApplyIdList(todo_push_applyId.split(",", -1));
        }
        todoInfo.setTodoDataType("1");
        List<TodoInfo> todos = todoInfoMapper.selectTodoInfoList(todoInfo);
        map.put("todos",todos.size());
        todoInfo.setTodoDataType("2");
        List<TodoInfo> dones = todoInfoMapper.selectTodoInfoList(todoInfo);
        map.put("dones",dones.size());
        todoInfo.setTodoDataType("3");
        List<TodoInfo> toreads = todoInfoMapper.selectTodoInfoList(todoInfo);
        map.put("toreads",toreads.size());
        todoInfo.setTodoDataType("4");
        List<TodoInfo> reads = todoInfoMapper.selectTodoInfoList(todoInfo);
        map.put("reads",reads.size());
        return map;
    }

    @Override
    public Map<String, Object> selectTodoInfoCount(String todoHandler) {
        List<TodoInfoVo> todoInfoVos = todoInfoMapper.selectTodoInfoCount(todoHandler);
        Map<String, Object> todoInfoVosMap = new HashMap<>();
        Integer todoNum = 0;
        Integer todoReadNum = 0;
        if (todoInfoVos.size() >= 1) {
            for (int i = 0; i < todoInfoVos.size(); i++) {
                if (todoInfoVos.get(i).getTodoType() == null || todoInfoVos.get(i).getTodoType().equals("")) {
                    todoInfoVosMap.put("todoNum", todoInfoVos.get(i).getTodoDataType());
                } else if (todoInfoVos.get(i).getTodoType().equals("completed")) {
                    todoInfoVosMap.put("todoReadNum", todoInfoVos.get(i).getTodoDataType());
                }
              /*  String retype=todoInfoVos.get(i).getTodoType().trim().equals("completed")?"todoReadNum":"todoNum";
                todoInfoVosMap.put(retype,todoInfoVos.get(i).getTodoInfoCount());*/
            }
        } else {
            todoInfoVosMap.put("todoNum", 0);
            todoInfoVosMap.put("todoReadNum", 0);
        }
        return todoInfoVosMap;
    }

    @Override
    public List<TodoInfoVo> selectTodoInfoOrApplyinfoList(TodoInfoVo todoInfo) {
        return todoInfoMapper.selectTodoInfoOrApplyinfoList(todoInfo);
    }

    /**
     * 获取当前登陆人去重的应用名称跟应用ID
     */
    @Override
    public List<TodoInfoVo> selectTodoInfoGetAppName(TodoInfoVo todoInfo) {
        return todoInfoMapper.selectTodoInfoGetAppName(todoInfo);
    }

    /**
     * 新增待办待阅
     *
     * @param todoInfo 待办待阅
     * @return 结果
     */
    @Override
    public int insertTodoInfo(TodoInfo todoInfo) {
        todoInfo.setCreateTime(DateUtils.getNowDate());
        return todoInfoMapper.insertTodoInfo(todoInfo);
    }

    /**
     * 修改待办待阅
     *
     * @param todoInfo 待办待阅
     * @return 结果
     */
    @Override
    public int updateTodoInfo(TodoInfo todoInfo) {
        return todoInfoMapper.updateTodoInfo(todoInfo);
    }

    @Override
    public int updateTodoInfo(String taskId, String todoHandler) {
        return todoInfoMapper.updateTodoInfoByTaskIdAndTodoHandler(taskId, todoHandler);
    }

    /**
     * 批量删除待办待阅
     *
     * @param todoIds 需要删除的待办待阅ID
     * @return 结果
     */
    @Override
    public int deleteTodoInfoByIds(String[] todoIds) {
        return todoInfoMapper.deleteTodoInfoByIds(todoIds);
    }

    /**
     * 删除待办待阅信息
     *
     * @param todoId 待办待阅ID
     * @return 结果
     */
    @Override
    public int deleteTodoInfoById(String todoId) {
        return todoInfoMapper.deleteTodoInfoById(todoId);
    }

    /**
     * 批量插入待办
     *
     * @param todoVoList
     */
    @Override
    public void batchSaveTodoInfo(List<TodoVo> todoVoList) {
        TodoInfo todoInfo = null;
        if (todoVoList.size() != 0) {
            for (int i = 0; i < todoVoList.size(); i++) {
                //封装待办信息
                todoInfo = getTodoInfo(todoVoList, i);
                //todoInfo.setTodoUrl(ConstantTodo.APPLICATION_TODO_URL.get(todoVoList.get(i).getApplyId()));
                todoInfo.setTodoDataType("1");//待办
                todoInfoMapper.insertTodoInfo(todoInfo);
            }
        }
    }

    /**
     * 批量已办
     *
     * @param todoVoList todoId必须传入
     */
    @Override
    public void batchSaveHadDone(List<TodoVo> todoVoList) {
        if (todoVoList.size() != 0) {
            for (int i = 0; i < todoVoList.size(); i++) {
                //将原待办设置为已处理
                TodoInfo todoInfo1 = todoInfoMapper.selectTodoInfoById(todoVoList.get(i).getTodoId());
                todoInfo1.setTodoType("1");//已处理
                todoInfo1.setModifyTime(new Date());
                todoInfo1.setUpdateTime(new Date());
                todoInfoMapper.updateTodoInfo(todoInfo1);

                //插入已办信息
                todoInfo1.setCreateTime(new Date());
                todoInfo1.setTodoId(StringUtils.getUUID());
                todoInfo1.setTodoUrl(todoVoList.get(i).getUrl());
                // todoInfo1.setTodoUrl(ConstantTodo.APPLICATION_HAD_DONE_URL.get(todoVoList.get(i).getApplyId()));
                todoInfo1.setTodoDataType("2");//已办
                todoInfoMapper.insertTodoInfo(todoInfo1);
            }
        }
    }


    /**
     * 批量插入待阅
     *
     * @param todoVoList
     */
    @Override
    public void batchSaveToReadInfo(List<TodoVo> todoVoList) {
        TodoInfo todoInfo = null;
        if (todoVoList.size() != 0) {
            for (int i = 0; i < todoVoList.size(); i++) {
                //封装待办信息
                todoInfo = getTodoInfo(todoVoList, i);
                // todoInfo.setTodoUrl(ConstantTodo.APPLICATION_TO_READ_URL.get(todoVoList.get(i).getApplyId()));
                todoInfo.setTodoDataType("3");//待阅
                todoInfoMapper.insertTodoInfo(todoInfo);
            }
        }
    }

    /**
     * 已阅
     *
     * @param todoVoList
     */
    @Override
    public void batchSaveHadRead(List<TodoVo> todoVoList) {
        if (todoVoList.size() != 0) {
            for (int i = 0; i < todoVoList.size(); i++) {
                //将原待办设置为已处理
                TodoInfo todoInfo1 = todoInfoMapper.selectTodoInfoById(todoVoList.get(i).getTodoId());
                todoInfo1.setTodoType("1");//已处理
                todoInfo1.setModifyTime(new Date());
                todoInfo1.setUpdateTime(new Date());
                todoInfoMapper.updateTodoInfo(todoInfo1);
                //插入已阅信息
                todoInfo1.setCreateTime(new Date());
                todoInfo1.setTodoId(StringUtils.getUUID());
                todoInfo1.setTodoUrl(todoVoList.get(i).getUrl());
                todoInfo1.setTodoDataType("4");//已阅
                todoInfoMapper.insertTodoInfo(todoInfo1);
            }
        }
    }

    /**
     * 申请人撤销待办
     *
     * @param todoId
     */
    @Override
    public void applyUserCancel(String todoId) {
        TodoInfo todoInfo = todoInfoMapper.selectTodoInfoById(todoId);
        todoInfo.setModifyTime(new Date());
        todoInfo.setTodoType("2");//申请人撤销待办
        todoInfo.setPushStatus("1");//未推送
        todoInfoMapper.updateTodoInfo(todoInfo);
    }

    @Override
    public List<TodoInfo> importTodoInfo(List<TodoInfo> todoInfoList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i < todoInfoList.size(); i++) {
            TodoInfo todoInfo = todoInfoList.get(i);
            try {
                // 验证是否存在这个用户
                TodoInfo u = todoInfoMapper.selectTodoInfoById(todoInfo.getTodoId());
                if (StringUtils.isNull(u)) {
                    todoInfoMapper.insertTodoInfo(todoInfo);
                    todoInfo.setImportStatus("导入成功");
                    todoInfoInfo.add(todoInfo);
                } else if (isUpdateSupport) {
                    todoInfoMapper.updateTodoInfo(todoInfo);
                    todoInfo.setImportStatus("更新成功");
                    todoInfoInfo.add(todoInfo);
                } else {
                    todoInfo.setImportStatus("已存在当前数据");
                    todoInfoInfo.add(todoInfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                todoInfo.setImportStatus("数据格式有问题，请认证检查数据！");
                todoInfoInfo.add(todoInfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importTodoInfo(todoInfoList.subList(i + 1, todoInfoList.size()), isUpdateSupport, operName);
                return todoInfoInfo;
            }

        }
        return todoInfoInfo;
    }

    @Override
    public List<Map<String, Object>> getToDoInfoCount(String appId, String todoHandler, String applyType) {
        return todoInfoMapper.getToDoInfoCount(appId, todoHandler, applyType);
    }

    @Override
    public List<TodoInfo> getToDoList(TodoInfo todoInfo) {
        return todoInfoMapper.getToDoList(todoInfo);
    }


    private TodoInfo getTodoInfo(List<TodoVo> todoVoList, int i) {
        TodoInfo todoInfo = new TodoInfo();
        todoInfo.setTodoId(StringUtils.getUUID());
        todoInfo.setBusinessKey(todoVoList.get(i).getBusinessKey());
        todoInfo.setTodoTitle(todoVoList.get(i).getTodoTitle());
        todoInfo.setApplyId(todoVoList.get(i).getApplyId());
        todoInfo.setTodoText(todoVoList.get(i).getTodoText());
        todoInfo.setTodoUrl(todoVoList.get(i).getUrl());
        todoInfo.setTodoApplicant(todoVoList.get(i).getTodoApplicant());
        todoInfo.setTodoHandler(todoVoList.get(i).getTodoHandler());
        todoInfo.setTodoHandlerName(todoVoList.get(i).getTodoHandlerName());
        todoInfo.setTodoTpLink(todoVoList.get(i).getTodoTpLink());
        todoInfo.setTodoTpName(ConstantTodo.WORKFLOW_NAME.get(todoVoList.get(i).getApplyId()));
        todoInfo.setCreateBy(SecurityUtils.getUsername());
        todoInfo.setCreateTime(new Date());
        todoInfo.setTodoType("0");//未处理
        return todoInfo;
    }

    /**
     * 根据businesskey删除待办待阅信息
     *
     * @param businesskey 业务id
     * @return 结果
     */
    @Override
    public int deleteTodoInfoByBusinesskey(String businesskey) {
        return todoInfoMapper.deleteTodoInfoByBusinesskey(businesskey);
    }

    @Override
    public int deleteTodoInfoByBusinesskeys(String[] businesskeys) {
        return todoInfoMapper.deleteTodoInfoByBusinesskeys(businesskeys);
    }

    @Override
    public TodoInfo selectTodoInfoByBusinessKeyAndTodoHandler(String businessKey, String todoHandler) {
        return todoInfoMapper.selectTodoInfoByBusinessKeyAndTodoHandler(businessKey, todoHandler);
    }

    /**
     * 通过权限获取待办
     * @param param
     * @return
     */
    @Override
    public List<TodoInfo> getAuthorizeToDoList(Map param) {
        return todoInfoMapper.getAuthorizeToDoList(param);
    }

    @Override
    public int updateTodoInfoByBusinessKey(TodoInfo todoInfo) {
        return todoInfoMapper.updateTodoInfoByBusinessKey(todoInfo);
    }

}
