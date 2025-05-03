package com.yabushan.system.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.vo.PushProTodoDoneNew;
import com.yabushan.system.domain.vo.PushProToreadReadNew;
import com.yabushan.system.domain.vo.TodoInfoVo;
import com.yabushan.system.service.ITodoInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.system.mapper.TodoInfoPushMapper;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.service.ITodoInfoPushService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 待办待阅Service业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-24
 */
@Service
public class TodoInfoPushServiceImpl implements ITodoInfoPushService {
    private static final Logger log = LoggerFactory.getLogger(TodoInfoPushServiceImpl.class);
    @Autowired
    private TodoInfoPushMapper todoInfoPushMapper;
    @Autowired
    private ITodoInfoService todoInfoService;
    //存放导入所有的成功失败数据
    private static List<TodoInfoPush> todoInfoPushInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void cleartodoInfoPushInfo() {
        if (StringUtils.isNotNull(todoInfoPushInfo) && todoInfoPushInfo.size() > 0) {
            todoInfoPushInfo.clear();
        }
    }

    /**
     * 查询待办待阅
     *
     * @param id 待办待阅ID
     * @return 待办待阅
     */
    @Override
    public TodoInfoPush selectTodoInfoPushById(String id) {
        return todoInfoPushMapper.selectTodoInfoPushById(id);
    }

    @Override
    public TodoInfoPush selectTodoInfoPushListBytodoDataType(String todoDataType) {
        return todoInfoPushMapper.selectTodoInfoPushListBytodoDataType(todoDataType);
    }

    /**
     * 查询待办待阅列表
     *
     * @param todoInfoPush 待办待阅
     * @return 待办待阅
     */
    @Override
    public List<TodoInfoPush> selectTodoInfoPushList(TodoInfoPush todoInfoPush) {
        return todoInfoPushMapper.selectTodoInfoPushList(todoInfoPush);
    }

    /**
     * 新增待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    @Override
    public int insertTodoInfoPush(TodoInfoPush todoInfoPush) {
        todoInfoPush.setCreateTime(DateUtils.getNowDate());
        return todoInfoPushMapper.insertTodoInfoPush(todoInfoPush);
    }

    /**
     * 从工作流的待办取值增加到待办待阅推送记录表
     *
     * @param todoInfo 待办待阅
     * @return 结果
     */
    @Override
    public int insertTodoInfoPushByTodoInfo(TodoInfo todoInfo, boolean isdouble,String taskId) {
        //判断是否插入俩条记录true:插入俩条,false:插入一条
        if(isdouble){
            //插入推送待办记录表
            //插入已办
            //拷贝实体
            //推送表
            TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
            BeanUtils.copyProperties(todoInfo, todoInfoPushinfo);
            //重新set推送表UUID
            todoInfoPushinfo.setId(UUID.randomUUID().toString());
            todoInfoPushMapper.insertTodoInfoPush(todoInfoPushinfo);

            //插入推送待办记录表
            //插入待办
            //重新set推送表UUID
            todoInfoPushinfo.setId(UUID.randomUUID().toString());
            todoInfoPushinfo.setTodoDataType("1");//待办
            todoInfoPushinfo.setTodoType("0");//未处理
            todoInfoPushMapper.insertTodoInfoPush(todoInfoPushinfo);
        }else{
            //插入推送待办记录表
            //插入已办
            //拷贝实体
            //推送表
            TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
            BeanUtils.copyProperties(todoInfo, todoInfoPushinfo);
            //插入推送待办记录表
            //插入待办
            //重新set推送表UUID
            todoInfoPushinfo.setId(UUID.randomUUID().toString());
            //根据taskid获取上一步处理人信息
            TodoInfoVo todoInfoVo = todoInfoService.selectTodoInfoByTaskId(taskId);
            if(todoInfoVo!=null) {
                //把上一步处理人信息拷贝到推送表记录
                todoInfoPushinfo.setCurrentStepName(todoInfoVo.getCurrentStepName());
                todoInfoPushinfo.setAssignee(todoInfoVo.getAssignee());
                todoInfoPushinfo.setStartTime(todoInfoVo.getStartTime());
                todoInfoPushinfo.setDeleteReason(todoInfoVo.getDeleteReason());
                todoInfoPushinfo.setProcessName(todoInfoVo.getProcessName());
                todoInfoPushinfo.setPreLoginId(todoInfoVo.getPreLoginId());
                todoInfoPushinfo.setPreStepName(todoInfoVo.getPreStepName());
                todoInfoPushinfo.setDoration(todoInfoVo.getDoration());
            }
            todoInfoPushMapper.insertTodoInfoPush(todoInfoPushinfo);
        }
        return 0;
    }

    /**
     * 批量新增待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    @Override
    public int bathInsertTodoInfoPush(List<TodoInfoPush> todoInfoPush) {
        return todoInfoPushMapper.bathInsertTodoInfoPush(todoInfoPush);
    }


    /**
     * 修改待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    @Override
    public int updateTodoInfoPush(TodoInfoPush todoInfoPush) {
        return todoInfoPushMapper.updateTodoInfoPush(todoInfoPush);
    }

    /**
     * 批量删除待办待阅
     *
     * @param ids 需要删除的待办待阅ID
     * @return 结果
     */
    @Override
    public int deleteTodoInfoPushByIds(String[] ids) {
        return todoInfoPushMapper.deleteTodoInfoPushByIds(ids);
    }

    /**
     * 删除待办待阅信息
     *
     * @param id 待办待阅ID
     * @return 结果
     */
    @Override
    public int deleteTodoInfoPushById(String id) {
        return todoInfoPushMapper.deleteTodoInfoPushById(id);
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
                TodoInfoPush todoInfoPush = todoInfoPushMapper.selectTodoInfoPushById(idslist[i]);
                todoInfoPush.setDisableEnableState(disableEnableState);
                int isappinfo = todoInfoPushMapper.updateTodoInfoPush(todoInfoPush);
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
     * 导入待办待阅Excel数据
     *
     * @param todoInfoPushList 数据列表
     * @param isUpdateSupport  是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<TodoInfoPush> importTodoInfoPush(List<TodoInfoPush> todoInfoPushList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i < todoInfoPushList.size(); i++) {
            TodoInfoPush todoInfoPush = todoInfoPushList.get(i);
            try {
                // 验证是否存在这个用户
                TodoInfoPush u = todoInfoPushMapper.selectTodoInfoPushById(todoInfoPush.getId());
                if (StringUtils.isNull(u)) {
                    todoInfoPushMapper.insertTodoInfoPush(todoInfoPush);
                    todoInfoPush.setImportStatus("导入成功");
                    todoInfoPushInfo.add(todoInfoPush);
                } else if (isUpdateSupport) {
                    todoInfoPushMapper.updateTodoInfoPush(todoInfoPush);
                    todoInfoPush.setImportStatus("更新成功");
                    todoInfoPushInfo.add(todoInfoPush);
                } else {
                    todoInfoPush.setImportStatus("已存在当前数据");
                    todoInfoPushInfo.add(todoInfoPush);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                todoInfoPush.setImportStatus("数据格式有问题，请认证检查数据！");
                todoInfoPushInfo.add(todoInfoPush);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importTodoInfoPush(todoInfoPushList.subList(i + 1, todoInfoPushList.size()), isUpdateSupport, operName);
                return todoInfoPushInfo;
            }

        }
        return todoInfoPushInfo;
    }



    @Override
    public List<TodoInfoPush> selectUnHandlePushList(TodoInfoPush todoInfoPush) {
        return todoInfoPushMapper.selectUnHandlePushList(todoInfoPush);
    }



    /**
     * 根据businesskey删除待办待阅信息
     *
     * @param businesskey 业务id
     * @return 结果
     */
    @Override
    public int deleteTodoInfoPushByBusinesskey(String businesskey) {
        return todoInfoPushMapper.deleteTodoInfoPushByBusinesskey(businesskey);
    }

    public List<PushProTodoDoneNew> getTodoInfoToPush(String todoDataType){
        return todoInfoPushMapper.getTodoInfoToPush(todoDataType);
    }

    public List<PushProToreadReadNew> getToReadInfoToPush(String todoDataType){
        return todoInfoPushMapper.getToReadInfoToPush(todoDataType);
    }
}
