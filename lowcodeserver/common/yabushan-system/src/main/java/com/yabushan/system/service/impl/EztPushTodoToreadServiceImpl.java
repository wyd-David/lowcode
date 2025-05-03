package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.EztPushTodoToread;
import com.yabushan.system.mapper.EztPushTodoToreadMapper;
import com.yabushan.system.service.IEztPushTodoToreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 推送统一待办消息（包括待办、待阅）Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-28
 */
@Service
public class EztPushTodoToreadServiceImpl implements IEztPushTodoToreadService {
    private static final Logger log = LoggerFactory.getLogger(EztPushTodoToreadServiceImpl.class);
    @Autowired
    private EztPushTodoToreadMapper eztPushTodoToreadMapper;

    //存放导入所有的成功失败数据
    private static List<EztPushTodoToread> eztPushTodoToreadInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void cleareztPushTodoToreadInfo() {
        if (StringUtils.isNotNull(eztPushTodoToreadInfo) && eztPushTodoToreadInfo.size() > 0) {
            eztPushTodoToreadInfo.clear();
        }
    }

    /**
     * 查询推送统一待办消息（包括待办、待阅）
     *
     * @param id 推送统一待办消息（包括待办、待阅）ID
     * @return 推送统一待办消息（包括待办、待阅）
     */
    @Override
    public EztPushTodoToread selectEztPushTodoToreadById(String id) {
        return eztPushTodoToreadMapper.selectEztPushTodoToreadById(id);
    }

    /**
     * 查询推送统一待办消息（包括待办、待阅）列表
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 推送统一待办消息（包括待办、待阅）
     */
    @Override
    public List<EztPushTodoToread> selectEztPushTodoToreadList(EztPushTodoToread eztPushTodoToread) {
        return eztPushTodoToreadMapper.selectEztPushTodoToreadList(eztPushTodoToread);
    }

    /**
     * 新增推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    @Override
    public int insertEztPushTodoToread(EztPushTodoToread eztPushTodoToread) {
        eztPushTodoToread.setCreateTime(DateUtils.getNowDate());
        return eztPushTodoToreadMapper.insertEztPushTodoToread(eztPushTodoToread);
    }

    /**
     * 批量新增推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    @Override
    public int bathInsertEztPushTodoToread(List<EztPushTodoToread> eztPushTodoToread) {
        return eztPushTodoToreadMapper.bathInsertEztPushTodoToread(eztPushTodoToread);
    }


    /**
     * 修改推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    @Override
    public int updateEztPushTodoToread(EztPushTodoToread eztPushTodoToread) {
        return eztPushTodoToreadMapper.updateEztPushTodoToread(eztPushTodoToread);
    }

    /**
     * 批量删除推送统一待办消息（包括待办、待阅）
     *
     * @param ids 需要删除的推送统一待办消息（包括待办、待阅）ID
     * @return 结果
     */
    @Override
    public int deleteEztPushTodoToreadByIds(String[] ids) {
        return eztPushTodoToreadMapper.deleteEztPushTodoToreadByIds(ids);
    }

    /**
     * 删除推送统一待办消息（包括待办、待阅）信息
     *
     * @param id 推送统一待办消息（包括待办、待阅）ID
     * @return 结果
     */
    @Override
    public int deleteEztPushTodoToreadById(String id) {
        return eztPushTodoToreadMapper.deleteEztPushTodoToreadById(id);
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
                EztPushTodoToread eztPushTodoToread = eztPushTodoToreadMapper.selectEztPushTodoToreadById(idslist[i]);
                eztPushTodoToread.setDisableEnableState(disableEnableState);
                int isappinfo = eztPushTodoToreadMapper.updateEztPushTodoToread(eztPushTodoToread);
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
     * 导入推送统一待办消息（包括待办、待阅）Excel数据
     *
     * @param eztPushTodoToreadList 数据列表
     * @param isUpdateSupport       是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<EztPushTodoToread> importEztPushTodoToread(
            List<EztPushTodoToread> eztPushTodoToreadList, Boolean
            isUpdateSupport,
            String operName) {
        for (int i = 0; i < eztPushTodoToreadList.size(); i++) {
            EztPushTodoToread eztPushTodoToread = eztPushTodoToreadList.get(i);
            try {
                // 验证是否存在这个用户
                EztPushTodoToread u = eztPushTodoToreadMapper.selectEztPushTodoToreadById(eztPushTodoToread.getId());
                if (StringUtils.isNull(u)) {
                    eztPushTodoToreadMapper.insertEztPushTodoToread(eztPushTodoToread);
                    eztPushTodoToread.setImportStatus("导入成功");
                    eztPushTodoToreadInfo.add(eztPushTodoToread);
                } else if (isUpdateSupport) {
                    eztPushTodoToreadMapper.updateEztPushTodoToread(eztPushTodoToread);
                    eztPushTodoToread.setImportStatus("更新成功");
                    eztPushTodoToreadInfo.add(eztPushTodoToread);
                } else {
                    eztPushTodoToread.setImportStatus("已存在当前数据");
                    eztPushTodoToreadInfo.add(eztPushTodoToread);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                eztPushTodoToread.setImportStatus("数据格式有问题，请认证检查数据！");
                eztPushTodoToreadInfo.add(eztPushTodoToread);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importEztPushTodoToread(eztPushTodoToreadList.subList(i + 1, eztPushTodoToreadList.size()), isUpdateSupport, operName);
                return eztPushTodoToreadInfo;
            }

        }
        return eztPushTodoToreadInfo;
    }
}
