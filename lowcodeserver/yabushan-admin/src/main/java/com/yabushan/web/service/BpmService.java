package com.yabushan.web.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yabushan.common.bpm.BpmProcessService;
import com.yabushan.common.utils.http.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BpmService implements BpmProcessService {


    @Value("${domain.bpm}")
    private String bpmUrl;

    @Override
    public JSONObject draft(String service, String user) {
        Map<String, Object> params = new HashMap<>();
        params.put("service",service);
        params.put("user",user);
        String result = HttpClientUtil.doPost(bpmUrl, params);
        JSONObject tokenJson = JSONUtil.parseObj(result);
        return tokenJson;
    }

    @Override
    public JSONObject start(String service, String title, String user, String nextActivityId, String nextUsers, String approveResult, String approveNote, String chain) {
        Map<String, Object> params = new HashMap<>();
        params.put("service",service);
        params.put("title",title);
        params.put("user",user);
        params.put("nextActivityId",nextActivityId);
        params.put("nextUsers",nextUsers);
        params.put("approveResult",approveResult);
        params.put("approveNote",approveNote);
        params.put("chain",chain);
        String result = HttpClientUtil.doPost(bpmUrl, params);
        JSONObject tokenJson = JSONUtil.parseObj(result);

        return tokenJson;
    }

    @Override
    public JSONObject commit(String documentId, String title, String user, String nextActivityId, String nextUsers, String approveResult, String approveNote, boolean breaked, String chain) {
        return null;
    }

    @Override
    public JSONObject open(String documentId, String user, boolean returnData) {
        return null;
    }

    @Override
    public JSONObject update(String documentId, String title, String user) {
        return null;
    }

    @Override
    public JSONObject query(String documentId) {
        return null;
    }

    @Override
    public JSONObject create(String service, String title, String user, String data, String attribute, String corpId) {
        return null;
    }

    @Override
    public JSONObject withdraw(String documentId, String currentUser) {
        return null;
    }

    @Override
    public JSONObject cancel(String documentId, String user) {
        return null;
    }

    @Override
    public JSONObject assign(String documentId, String currentUser, String targetUser) {
        return null;
    }

    @Override
    public JSONObject reject(String documentId, String currentUser) {
        return null;
    }

    @Override
    public JSONObject geUserMsg(String user) {
        return null;
    }

    @Override
    public JSONObject createRelationDocument(String fromDocumentId, String toDocumentId, String createdBy) {
        return null;
    }

    @Override
    public JSONObject getUserTask(String documentId, String user) {
        return null;
    }

    @Override
    public String getNextActivityByProcessType(String processType) {
        return null;
    }

    @Override
    public JSONObject queryProcessLineAndRecords(String service, String documentId, String processType, String user) {
        return null;
    }

    @Override
    public JSONObject queryActivityList(String documentId) {
        return null;
    }

    @Override
    public JSONObject queryRecords(String documentId, String user) {
        return null;
    }

    @Override
    public String getNextActivityByModule(String evaluationerChooseModule, String currentActivityId) {
        return null;
    }

    @Override
    public JSONObject close(String documentId, String user) {
        return null;
    }

    @Override
    public Boolean processAdmin(String service, String userCode) {
        return null;
    }

    @Override
    public JSONObject queryWorkOnTasks(String user, String serviceCode, String activityId, Integer page, Integer size) {
        return null;
    }

    @Override
    public JSONObject queryCompletedTasks(String user, String serviceCode, Integer page, Integer size) {
        return null;
    }

    @Override
    public JSONObject getPreAvtivity(String documentId, String user) {
        return null;
    }

    @Override
    public String getCreatorUserName(String documentId, String user) {
        return null;
    }
}
