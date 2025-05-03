package com.yabushan.quartz.mapper;

import com.yabushan.quartz.domain.XunchaCheckedApplyInfoVo;
import com.yabushan.quartz.domain.XunchaCheckedFinePerDetailVo;
import com.yabushan.quartz.domain.XunchaCheckedProResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Copyright: ...
 * Company: ...
 * Version: 1.0.0
 * Author: ...
 * Date: 2022/7/21
 * Description:
 */
public interface XunchaResultMapper {

    public List<Map<String,Object>> getNeedCheckedList(Map<String,Object> params);

    public List<Map<String,Object>> getRelationList(Map<String,Object> params);

    public List<Map<String,Object>> getLeaderResultList(Map<String,Object> params);

    public XunchaCheckedApplyInfoVo getApplyInfoById(@Param("applyId") String applyId);

    public int insetResult(XunchaCheckedProResultVo result);

    public int updateDetailById(Map<String,Object> params);

    public int updateResult(XunchaCheckedProResultVo result);

    public XunchaCheckedProResultVo getResult(Map<String,Object> params);

    public Map<String,Object> getGroupResult(Map<String,Object> params);

    public int insetFineDetail(XunchaCheckedFinePerDetailVo detail);

    public int deleteResult();

    public int deleteFine();

    public XunchaCheckedProResultVo  getByTouserIdAndGroupId(XunchaCheckedProResultVo vo);

    List<Map<String, Object>> selectCanRecycle();

    public int updateExamRelationStatus(String[] ids);

    public List<String> getFileIds();

    public void updateXunchaProcessFileByIds(String[] ids);
}
