package com.yabushan.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.PushProTodoDone;
import com.yabushan.system.domain.vo.PushProTodoDoneVo;
import com.yabushan.system.enums.BusinessType;
import com.yabushan.system.enums.PushStatus;
import com.yabushan.system.mapper.PushProTodoDoneMapper;
import com.yabushan.system.service.IPushProTodoDoneService;
import com.yabushan.system.utils.PushDataToPortal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 推送待办已办Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-28
 */
@Service
public class PushProTodoDoneServiceImpl implements IPushProTodoDoneService {
    private static final Logger log = LoggerFactory.getLogger(PushProTodoDoneServiceImpl.class);
    @Value("${portal.appName}")
    private String portalAppName;
    @Value("${portal.retryCount}")
    private String retryCount;//重推次数
    @Value("${portal.owner}")
    private String owner;//指定办理人
    @Value("${portal.cityList}")
    private List<String> cityList;//城市列表
    @Value("${portal.warnLevelList}")
    private List<String> warnLevelList;//预警级别
    @Autowired
    private PushProTodoDoneMapper pushProTodoDoneMapper;
    @Autowired
    private PushDataToPortal pushDataToPortal;

    //存放导入所有的成功失败数据
    private static List<PushProTodoDone> pushProTodoDoneInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearpushProTodoDoneInfo() {
        if (StringUtils.isNotNull(pushProTodoDoneInfo) && pushProTodoDoneInfo.size() > 0) {
            pushProTodoDoneInfo.clear();
        }
    }

    /**
     * 查询推送待办已办
     *
     * @param todoId 推送待办已办ID
     * @return 推送待办已办
     */
    @Override
    public PushProTodoDone selectPushProTodoDoneById(String todoId) {
        return pushProTodoDoneMapper.selectPushProTodoDoneById(todoId);
    }

    /**
     * 查询推送待办已办列表
     *
     * @param pushProTodoDone 推送待办已办
     * @return 推送待办已办
     */
    @Override
    public List<PushProTodoDone> selectPushProTodoDoneList(PushProTodoDone pushProTodoDone) {
        return pushProTodoDoneMapper.selectPushProTodoDoneList(pushProTodoDone);
    }

    /**
     * 新增推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    @Override
    public int insertPushProTodoDone(PushProTodoDone pushProTodoDone) {
        pushProTodoDone.setCreateTime(DateUtils.getNowDate());
        return pushProTodoDoneMapper.insertPushProTodoDone(pushProTodoDone);
    }

    /**
     * 批量新增推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    @Override
    public int bathInsertPushProTodoDone(List<PushProTodoDone> pushProTodoDone) {
        return pushProTodoDoneMapper.bathInsertPushProTodoDone(pushProTodoDone);
    }


    /**
     * 修改推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    @Override
    public int updatePushProTodoDone(PushProTodoDone pushProTodoDone) {
        return pushProTodoDoneMapper.updatePushProTodoDone(pushProTodoDone);
    }

    /**
     * 批量删除推送待办已办
     *
     * @param todoIds 需要删除的推送待办已办ID
     * @return 结果
     */
    @Override
    public int deletePushProTodoDoneByIds(String[] todoIds) {
        return pushProTodoDoneMapper.deletePushProTodoDoneByIds(todoIds);
    }

    /**
     * 删除推送待办已办信息
     *
     * @param todoId 推送待办已办ID
     * @return 结果
     */
    @Override
    public int deletePushProTodoDoneById(String todoId) {
        return pushProTodoDoneMapper.deletePushProTodoDoneById(todoId);
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
                PushProTodoDone pushProTodoDone = pushProTodoDoneMapper.selectPushProTodoDoneById(idslist[i]);
                pushProTodoDone.setDisableEnableState(disableEnableState);
                int isappinfo = pushProTodoDoneMapper.updatePushProTodoDone(pushProTodoDone);
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
     * 导入推送待办已办Excel数据
     *
     * @param pushProTodoDoneList 数据列表
     * @param isUpdateSupport     是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<PushProTodoDone> importPushProTodoDone(
            List<PushProTodoDone> pushProTodoDoneList, Boolean
            isUpdateSupport,
            String operName) {
        for (int i = 0; i < pushProTodoDoneList.size(); i++) {
            PushProTodoDone pushProTodoDone = pushProTodoDoneList.get(i);
            try {
                // 验证是否存在这个用户
                PushProTodoDone u = pushProTodoDoneMapper.selectPushProTodoDoneById(pushProTodoDone.getTodoId());
                if (StringUtils.isNull(u)) {
                    pushProTodoDoneMapper.insertPushProTodoDone(pushProTodoDone);
                    pushProTodoDone.setImportStatus("导入成功");
                    pushProTodoDoneInfo.add(pushProTodoDone);
                } else if (isUpdateSupport) {
                    pushProTodoDoneMapper.updatePushProTodoDone(pushProTodoDone);
                    pushProTodoDone.setImportStatus("更新成功");
                    pushProTodoDoneInfo.add(pushProTodoDone);
                } else {
                    pushProTodoDone.setImportStatus("已存在当前数据");
                    pushProTodoDoneInfo.add(pushProTodoDone);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                pushProTodoDone.setImportStatus("数据格式有问题，请认证检查数据！");
                pushProTodoDoneInfo.add(pushProTodoDone);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importPushProTodoDone(pushProTodoDoneList.subList(i + 1, pushProTodoDoneList.size()), isUpdateSupport, operName);
                return pushProTodoDoneInfo;
            }

        }
        return pushProTodoDoneInfo;
    }

    @Override
    public boolean push(PushProTodoDone pushProTodoDone) throws Exception {
        boolean isCanPush = true;
//        boolean flag = this.save(pushProTodoDone);
//        if(!flag) {
//            return flag;
//        }
        //先判断是否可以推送
        PushProTodoDone td = null;


//        QueryWrapper<PushProTodoDone> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(Maps.of("td.data_type",pushProTodoDone.getDataType(),
//                        "td.push_status", PushStatus.NO_PUSH.getStatus()))
//                .lt("td.retry_count",retryCount);

        if(CollectionUtil.isNotEmpty(cityList)||CollectionUtil.isNotEmpty(warnLevelList)) {

//            if(CollectionUtil.isNotEmpty(cityList)) {
//                queryWrapper.in("o.cmcc_city_cd",cityList);
//            }
//            if(CollectionUtil.isNotEmpty(warnLevelList)) {
//                queryWrapper.in("o.warn_level_id",warnLevelList);
//            }

            List<PushProTodoDone> pushProTodoDones = pushProTodoDoneMapper.selectTodoDonePushData(null);
            if(CollectionUtil.isNotEmpty(pushProTodoDones)) {
                td = pushProTodoDones.get(0);
            }else{
                isCanPush = false;
            }
        }

        if(isCanPush) {
            PushProTodoDoneVo pushProTodoDoneVo = new PushProTodoDoneVo();
            BeanUtil.copyProperties(pushProTodoDone, pushProTodoDoneVo);
            pushProTodoDoneVo.setTodoTitle("td.getWarnOrderSummary().getTitle()");
            // FIXME: 2021/4/21 暂时写死
            if(StringUtils.isNotEmpty(owner)) {
                pushProTodoDoneVo.setOwner(owner);
            }


            String jsonStr = pushDataToPortal.push(BusinessType.getTodoDoneValByCode(pushProTodoDone.getDataType()),
                    JSON.toJSONString(Lists.newArrayList(pushProTodoDoneVo)));
            com.alibaba.fastjson.JSONObject jsonObject = JSONUtil.toBean(jsonStr, com.alibaba.fastjson.JSONObject.class);
            String code = jsonObject.getString("code");
            if("000".equals(code)) {
                //成功
                //修改推送数据的 pushStatus 改为PushStatus.PUSHED
//                LambdaUpdateWrapper<PushProTodoDone> updateWrapper = new LambdaUpdateWrapper<>();
//                updateWrapper.set(PushProTodoDone::getPushStatus,PushStatus.PUSHED.getStatus())
//                        .allEq(Maps.of(PushProTodoDone::getTodoId,pushProTodoDone.getTodoId(),
//                                PushProTodoDone::getDataType,pushProTodoDone.getDataType(),
//                                PushProTodoDone::getPushStatus, PushStatus.NO_PUSH.getStatus()));
                //待定
                PushProTodoDone todoDone = new PushProTodoDone();
                todoDone.setTodoId(pushProTodoDone.getTodoId());
                todoDone.setPushStatus(PushStatus.PUSHED.getStatus());
                todoDone.setDataType(pushProTodoDone.getDataType());
                int updateFlag = pushProTodoDoneMapper.updatePushProTodoDone(todoDone);
                if(updateFlag<1) {
                    log.error("****推送数据修改为：PushStatus.PUSHED 失败");
                }
                return true;
            }
            String message = jsonObject.getString("message");
            log.error("****pushDataToPortal-推送数据失败：{}",message);
            return false;
        }else{
            //修改推送数据的 pushStatus 改为PushStatus.NO_OPEN_PUSH
            //待定
//            LambdaUpdateWrapper<PushProTodoDone> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.set(PushProTodoDone::getPushStatus,PushStatus.NO_OPEN_PUSH.getStatus())
//                    .allEq(Maps.of(PushProTodoDone::getTodoId,pushProTodoDone.getTodoId(),
//                            PushProTodoDone::getDataType,pushProTodoDone.getDataType(),
//                            PushProTodoDone::getPushStatus, PushStatus.NO_PUSH.getStatus()));
//            this.update(updateWrapper);
            PushProTodoDone todoDone = new PushProTodoDone();
            todoDone.setTodoId(pushProTodoDone.getTodoId());
            todoDone.setPushStatus(PushStatus.NO_PUSH.getStatus());
            todoDone.setDataType(pushProTodoDone.getDataType());
            int updateFlag = pushProTodoDoneMapper.updatePushProTodoDone(todoDone);
        }

        return true;
    }

    @Override
    public boolean pushDataToPortal(BusinessType businessType) {
        //QueryWrapper<PushProTodoDone> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(Maps.of(PushProTodoDone::getDataType,businessType.getCode(),
//                PushProTodoDone::getPushStatus, PushStatus.NO_PUSH.getStatus()))
//                .lt(PushProTodoDone::getRetryCount,retryCount);
//        queryWrapper.allEq(Maps.of("td.data_type",businessType.getCode(),
//                        "td.push_status", PushStatus.NO_PUSH.getStatus()))
//                .lt("td.retry_count",retryCount);
//        if(CollectionUtil.isNotEmpty(cityList)) {
//            queryWrapper.in("o.cmcc_city_cd",cityList);
//        }
//        if(CollectionUtil.isNotEmpty(warnLevelList)) {
//            queryWrapper.in("o.warn_level_id",warnLevelList);
//        }
//        List<PushProTodoDone> pushProTodoDones = baseMapper.selectList(queryWrapper);

        List<PushProTodoDone> pushProTodoDones = pushProTodoDoneMapper.selectTodoDonePushData(businessType.getCode());

        if(CollectionUtil.isEmpty(pushProTodoDones)) {//没有数据推送
            return true;
        }
        List<PushProTodoDoneVo> pushProTodoDoneVos = pushProTodoDones.stream().map(i -> {
            PushProTodoDoneVo pushProTodoDoneVo = new PushProTodoDoneVo();
            BeanUtil.copyProperties(i, pushProTodoDoneVo);
            //没有工单的用任务名
            pushProTodoDoneVo.setTodoTitle(i.getTaskName());

            // FIXME: 2021/4/21 暂时写死
            if(StringUtils.isNotEmpty(owner)) {
                pushProTodoDoneVo.setOwner(owner);
            }

            if(StringUtils.isEmpty(pushProTodoDoneVo.getAppName())) {
                pushProTodoDoneVo.setAppName(portalAppName);
            }
            return pushProTodoDoneVo;
        }).collect(Collectors.toList());

        String jsonStr = null;
        try {
            jsonStr = pushDataToPortal.push(businessType.getVal(), JSONUtil.toJsonStr(pushProTodoDoneVos));
            log.error("****pushDataToPortal-推送数据结果：",jsonStr);
        } catch (Exception e) {
            log.error("****pushDataToPortal-推送数据异常：",e.getMessage());
            e.printStackTrace();
        }

//        LambdaUpdateWrapper<PushProTodoDone> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.set(PushProTodoDone::getPushStatus,PushStatus.PUSHED.getStatus())
//                .in(PushProTodoDone::getTodoId,pushProTodoDones.parallelStream()
//                        .map(PushProTodoDone::getTodoId).collect(Collectors.toList()))
//                .allEq(Maps.of(PushProTodoDone::getDataType,businessType.getCode(),
//                        PushProTodoDone::getPushStatus, PushStatus.NO_PUSH.getStatus()));

        if(StringUtils.isNotEmpty(jsonStr)) {
            com.alibaba.fastjson.JSONObject jsonObject = JSONUtil.toBean(jsonStr, com.alibaba.fastjson.JSONObject.class);
            String code = jsonObject.getString("code");
            if("000".equals(code)) {
                //成功
                //修改推送数据的 pushStatus 改为PushStatus.PUSHED
                //待定
                PushProTodoDone todoDone = new PushProTodoDone();
                todoDone.setTodoId("pushProTodoDones.parallelStream()");
                todoDone.setPushStatus(PushStatus.NO_PUSH.getStatus());
                todoDone.setDataType(businessType.getCode());
                int updateFlag = pushProTodoDoneMapper.updatePushProTodoDone(todoDone);
//                boolean updateFlag = this.update(updateWrapper);
                if(updateFlag<1) {

                    log.error("****pushDataToPortal-推送数据修改为：PushStatus.PUSHED 失败");
                }
                return true;
            }
            String message = jsonObject.getString("message");
            log.error("****pushDataToPortal-推送数据失败：{}",message);
        }

        //重推次数自增
        //pushProTodoDoneMapper.retryCountIncreByCondi(updateWrapper);

        return false;
    }
}
