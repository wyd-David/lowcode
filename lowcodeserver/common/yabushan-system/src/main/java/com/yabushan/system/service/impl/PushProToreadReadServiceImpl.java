package com.yabushan.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.PushProToreadRead;
import com.yabushan.system.domain.vo.PushProToreadReadVo;
import com.yabushan.system.enums.BusinessType;
import com.yabushan.system.enums.PushStatus;
import com.yabushan.system.mapper.PushProToreadReadMapper;
import com.yabushan.system.service.IPushProToreadReadService;
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
 * 推送待阅已阅Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-28
 */
@Service
public class PushProToreadReadServiceImpl implements IPushProToreadReadService {
    private static final Logger log = LoggerFactory.getLogger(PushProToreadReadServiceImpl.class);
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
    private PushProToreadReadMapper pushProToreadReadMapper;
    @Autowired
    private PushDataToPortal pushDataToPortal;

    //存放导入所有的成功失败数据
    private static List<PushProToreadRead> pushProToreadReadInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearpushProToreadReadInfo() {
        if (StringUtils.isNotNull(pushProToreadReadInfo) && pushProToreadReadInfo.size() > 0) {
            pushProToreadReadInfo.clear();
        }
    }

    /**
     * 查询推送待阅已阅
     *
     * @param toreadId 推送待阅已阅ID
     * @return 推送待阅已阅
     */
    @Override
    public PushProToreadRead selectPushProToreadReadById(String toreadId) {
        return pushProToreadReadMapper.selectPushProToreadReadById(toreadId);
    }

    /**
     * 查询推送待阅已阅列表
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 推送待阅已阅
     */
    @Override
    public List<PushProToreadRead> selectPushProToreadReadList(PushProToreadRead pushProToreadRead) {
        return pushProToreadReadMapper.selectPushProToreadReadList(pushProToreadRead);
    }

    /**
     * 新增推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    @Override
    public int insertPushProToreadRead(PushProToreadRead pushProToreadRead) {
        pushProToreadRead.setCreateTime(DateUtils.getNowDate());
        return pushProToreadReadMapper.insertPushProToreadRead(pushProToreadRead);
    }

    /**
     * 批量新增推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    @Override
    public int bathInsertPushProToreadRead(List<PushProToreadRead> pushProToreadRead) {
        return pushProToreadReadMapper.bathInsertPushProToreadRead(pushProToreadRead);
    }


    /**
     * 修改推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    @Override
    public int updatePushProToreadRead(PushProToreadRead pushProToreadRead) {
        return pushProToreadReadMapper.updatePushProToreadRead(pushProToreadRead);
    }

    /**
     * 批量删除推送待阅已阅
     *
     * @param toreadIds 需要删除的推送待阅已阅ID
     * @return 结果
     */
    @Override
    public int deletePushProToreadReadByIds(String[] toreadIds) {
        return pushProToreadReadMapper.deletePushProToreadReadByIds(toreadIds);
    }

    /**
     * 删除推送待阅已阅信息
     *
     * @param toreadId 推送待阅已阅ID
     * @return 结果
     */
    @Override
    public int deletePushProToreadReadById(String toreadId) {
        return pushProToreadReadMapper.deletePushProToreadReadById(toreadId);
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
                PushProToreadRead pushProToreadRead = pushProToreadReadMapper.selectPushProToreadReadById(idslist[i]);
                pushProToreadRead.setDisableEnableState(disableEnableState);
                int isappinfo = pushProToreadReadMapper.updatePushProToreadRead(pushProToreadRead);
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
     * 导入推送待阅已阅Excel数据
     *
     * @param pushProToreadReadList 数据列表
     * @param isUpdateSupport       是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<PushProToreadRead> importPushProToreadRead(
            List<PushProToreadRead> pushProToreadReadList, Boolean
            isUpdateSupport,
            String operName) {
        for (int i = 0; i < pushProToreadReadList.size(); i++) {
            PushProToreadRead pushProToreadRead = pushProToreadReadList.get(i);
            try {
                // 验证是否存在这个用户
                PushProToreadRead u = pushProToreadReadMapper.selectPushProToreadReadById(pushProToreadRead.getToreadId());
                if (StringUtils.isNull(u)) {
                    pushProToreadReadMapper.insertPushProToreadRead(pushProToreadRead);
                    pushProToreadRead.setImportStatus("导入成功");
                    pushProToreadReadInfo.add(pushProToreadRead);
                } else if (isUpdateSupport) {
                    pushProToreadReadMapper.updatePushProToreadRead(pushProToreadRead);
                    pushProToreadRead.setImportStatus("更新成功");
                    pushProToreadReadInfo.add(pushProToreadRead);
                } else {
                    pushProToreadRead.setImportStatus("已存在当前数据");
                    pushProToreadReadInfo.add(pushProToreadRead);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                pushProToreadRead.setImportStatus("数据格式有问题，请认证检查数据！");
                pushProToreadReadInfo.add(pushProToreadRead);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importPushProToreadRead(pushProToreadReadList.subList(i + 1, pushProToreadReadList.size()), isUpdateSupport, operName);
                return pushProToreadReadInfo;
            }

        }
        return pushProToreadReadInfo;
    }

    @Override
    public boolean push(PushProToreadRead pushProToreadRead) throws Exception {
        boolean isCanPush = true;

//        boolean flag = this.save(pushProToreadRead);
//        if(!flag) {
//            return flag;
//        }

        //先判断是否可以推送

//        QueryWrapper<PushProToreadRead> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(Maps.of("tr.data_type",pushProToreadRead.getDataType(),
//                        "tr.push_status", PushStatus.NO_PUSH.getStatus()))
//                .lt("tr.retry_count",retryCount);

        PushProToreadRead tr = null;
        if(CollectionUtil.isNotEmpty(cityList)||CollectionUtil.isNotEmpty(warnLevelList)) {

//            if(CollectionUtil.isNotEmpty(cityList)) {
//                queryWrapper.in("o.cmcc_city_cd",cityList);
//            }
//            if(CollectionUtil.isNotEmpty(warnLevelList)) {
//                queryWrapper.in("o.warn_level_id",warnLevelList);
//            }

            List<PushProToreadRead> pushProToreadReads = pushProToreadReadMapper.selectToreadReadPushData(null);
            if(CollectionUtil.isNotEmpty(pushProToreadReads)) {
                tr = pushProToreadReads.get(0);
            }else{
                isCanPush = false;
            }
        }


        if(isCanPush) {
            PushProToreadReadVo pushProToreadReadVo = new PushProToreadReadVo();
            BeanUtil.copyProperties(pushProToreadRead, pushProToreadReadVo);
            pushProToreadReadVo.setTitle("tr.getWarnOrderSummary().getTitle()");
            // FIXME: 2021/4/21 暂时写死
            if(StringUtils.isNotEmpty(owner)) {
                pushProToreadReadVo.setOwner(owner);
            }


            String jsonStr = pushDataToPortal.push(BusinessType.getTodoDoneValByCode(pushProToreadRead.getDataType()),
                    JSON.toJSONString(Lists.newArrayList(pushProToreadReadVo)));
            com.alibaba.fastjson.JSONObject jsonObject = JSONUtil.toBean(jsonStr, com.alibaba.fastjson.JSONObject.class);
            String code = jsonObject.getString("code");
            if("000".equals(code)) {
                //成功
                //修改推送数据的 pushStatus 改为PushStatus.PUSHED
//                LambdaUpdateWrapper<PushProToreadRead> updateWrapper = new LambdaUpdateWrapper<>();
//                updateWrapper.set(PushProToreadRead::getPushStatus,PushStatus.PUSHED.getStatus())
//                        .allEq(Maps.of(PushProToreadRead::getToreadId,pushProToreadRead.getToreadId(),
//                                PushProToreadRead::getDataType,pushProToreadRead.getDataType(),
//                                PushProToreadRead::getPushStatus, PushStatus.NO_PUSH.getStatus()));

                //待定
//                boolean updateFlag = this.update(updateWrapper);
//                if(!updateFlag) {
//                    log.error("****推送数据修改为：PushStatus.PUSHED 失败");
//                }
                return true;
            }
            String message = jsonObject.getString("message");
            log.error("****推送数据失败：{}",message);
            return false;
        }else{
            //修改推送数据的 pushStatus 改为PushStatus.PUSHED
//            LambdaUpdateWrapper<PushProToreadRead> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.set(PushProToreadRead::getPushStatus,PushStatus.NO_OPEN_PUSH.getStatus())
//                    .allEq(Maps.of(PushProToreadRead::getToreadId,pushProToreadRead.getToreadId(),
//                            PushProToreadRead::getDataType,pushProToreadRead.getDataType(),
//                            PushProToreadRead::getPushStatus, PushStatus.NO_PUSH.getStatus()));
//            //待定
            //this.update(updateWrapper);
        }

        return true;
    }

    @Override
    public boolean pushDataToPortal(BusinessType businessType) {
        //QueryWrapper<PushProToreadRead> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(Maps.of(PushProToreadRead::getDataType,businessType.getCode(),
//                        PushProToreadRead::getPushStatus, PushStatus.NO_PUSH.getStatus()))
//                     .lt(PushProToreadRead::getRetryCount,retryCount);

//        queryWrapper.allEq(Maps.of("tr.data_type",businessType.getCode(),
//                        "tr.push_status", PushStatus.NO_PUSH.getStatus()))
//                .lt("tr.retry_count",retryCount);
//        if(CollectionUtil.isNotEmpty(cityList)) {
//            queryWrapper.in("o.cmcc_city_cd",cityList);
//        }
//        if(CollectionUtil.isNotEmpty(warnLevelList)) {
//            queryWrapper.in("o.warn_level_id",warnLevelList);
//        }
//        List<PushProToreadRead> pushProToreadReads = baseMapper.selectList(queryWrapper);

        List<PushProToreadRead> pushProToreadReads = pushProToreadReadMapper.selectToreadReadPushData(businessType.getCode());


        if(CollectionUtil.isEmpty(pushProToreadReads)) {//没有数据推送
            return true;
        }

        List<PushProToreadReadVo> pushProToreadReadVos = pushProToreadReads.stream().map(i -> {
            PushProToreadReadVo pushProToreadReadVo = new PushProToreadReadVo();
            BeanUtil.copyProperties(i, pushProToreadReadVo);

//            pushProToreadReadVo.setTitle(i.getWarnOrderSummary().getTitle());
            //没有工单的用任务名
            pushProToreadReadVo.setTitle(i.getTaskName());

            // FIXME: 2021/4/21 暂时写死
            if(StringUtils.isNotEmpty(owner)) {
                pushProToreadReadVo.setOwner(owner);
            }

            if(StringUtils.isEmpty(pushProToreadReadVo.getAppName())) {
                pushProToreadReadVo.setAppName(portalAppName);
            }
            return pushProToreadReadVo;
        }).collect(Collectors.toList());

        //调用推送
        String jsonStr = null;
        try {
            jsonStr = pushDataToPortal.push(businessType.getVal(), JSONUtil.toJsonStr(pushProToreadReadVos));
            log.error("****pushDataToPortal-推送数据结果：{}",jsonStr);
        } catch (Exception e) {
            log.error("****pushDataToPortal-推送数据异常：{}",e.getMessage());
            e.printStackTrace();
        }

//        LambdaUpdateWrapper<PushProToreadRead> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.set(PushProToreadRead::getPushStatus,PushStatus.PUSHED.getStatus())
//                .in(PushProToreadRead::getToreadId,pushProToreadReads.parallelStream()
//                        .map(PushProToreadRead::getToreadId).collect(Collectors.toList()))
//                .allEq(Maps.of(PushProToreadRead::getDataType,businessType.getCode(),
//                        PushProToreadRead::getPushStatus, PushStatus.NO_PUSH.getStatus()));

        if(StringUtils.isNotEmpty(jsonStr)) {
            com.alibaba.fastjson.JSONObject jsonObject = JSONUtil.toBean(jsonStr, com.alibaba.fastjson.JSONObject.class);
            String code = jsonObject.getString("code");
            if("000".equals(code)) {
                //成功
                //修改推送数据的 pushStatus 改为PushStatus.PUSHED
                //待定

                //pushProToreadReadMapper.updatePushProToreadRead(new PushProToreadRead());
//                boolean updateFlag = this.update(updateWrapper);
//                if(!updateFlag) {
//                    log.error("****推送数据修改为：PushStatus.PUSHED 失败");
//                }
                return true;
            }
            String message = jsonObject.getString("message");
            log.error("****pushDataToPortal-推送数据失败：{}",message);
        }

        //重推次数自增
        //pushProToreadReadMapper.retryCountIncreByCondi(updateWrapper);

        return false;
    }
}
